package cupkovic.fesb.hr.filmoteka.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cupkovic.fesb.hr.filmoteka.R;
import cupkovic.fesb.hr.filmoteka.adapters.MoviesListAdapter;
import cupkovic.fesb.hr.filmoteka.data.FavoritesDataSource;
import cupkovic.fesb.hr.filmoteka.data.MoviesProvider;
import cupkovic.fesb.hr.filmoteka.data.models.Movie;
import cupkovic.fesb.hr.filmoteka.interfaces.IApiSubscriber;
import cupkovic.fesb.hr.filmoteka.utils.APIClient;

public class MovieFavouritesActivity extends AppCompatActivity implements IApiSubscriber {

    private APIClient apiClient;
    private MoviesListAdapter moviesListAdapter;
    private MoviesProvider moviesProvider;
    private FavoritesDataSource favoritesDataSource;
    private ListView moviesListView;
    private ArrayList<Movie> favouritesFromDS;
    private ArrayList<Movie> favouritesWithAllProps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_favourites);
        setup();

        loadFavouriteMoviesFromDataSource();
        fetchFavouritesDataFromAPI();

        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent movieIntent = new Intent(MovieFavouritesActivity.this, IndividualMovieActivity.class);
                Movie currentMovie = moviesProvider.getAtPosition(position);
                movieIntent.putExtra("CURRENT_MOVIE_ID", currentMovie.getId());
                startActivity(movieIntent);
            }
        });
    }

    @Override
    public void handleAPISuccessResponse(Object response) {
        if (response instanceof  Movie) {
            this.favouritesWithAllProps.add((Movie) response);
            displayData();
        }
    }

    @Override
    public void handleAPIErrorResponse(Object response) {
        Toast.makeText(getApplicationContext(), "API error", Toast.LENGTH_LONG).show();
    }

    private void setup() {
        this.apiClient = new APIClient(this);
        this.moviesProvider = new MoviesProvider();
        this.moviesListAdapter = new MoviesListAdapter(getApplicationContext(), this.moviesProvider);
        this.favoritesDataSource = new FavoritesDataSource(getApplicationContext());
        this.moviesListView = (ListView) findViewById(R.id.movieFavouritesListView);
        this.favouritesFromDS = new ArrayList<Movie>();
        this.favouritesWithAllProps = new ArrayList<Movie>();
    }

    /**
     * Gets array of movie favourites from the database
     */
    private void loadFavouriteMoviesFromDataSource() {
        this.favouritesFromDS = this.favoritesDataSource.getAllMovieFavorites();
    }

    /**
     * Each movie has only API id, other properties need to fetched from the API
     */
    private void fetchFavouritesDataFromAPI() {
        for (Movie favourite : this.favouritesFromDS) {
            this.apiClient.fetchMovie(String.valueOf(favourite.getId()));
        }
    }

    private void displayData() {
        this.moviesProvider.setData(this.favouritesWithAllProps);
        this.moviesListView.setAdapter(this.moviesListAdapter);
    }
}
