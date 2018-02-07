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

public class WatchlistActivity extends AppCompatActivity implements IApiSubscriber {

    private APIClient apiClient;
    private MoviesListAdapter moviesListAdapter;
    private MoviesProvider moviesProvider;
    private FavoritesDataSource watchDataSource;
    private ListView watchListView;
    private ArrayList<Movie> watchMoviesFromDS;
    private ArrayList<Movie> watchMoviesWithProps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);
        setup();

        loadWatchlistFromDataSource();
        fetchWatchistDataFromAPI();

        watchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent movieIntent = new Intent(WatchlistActivity.this, IndividualMovieActivity.class);
                Movie currentMovie = moviesProvider.getAtPosition(position);
                movieIntent.putExtra("CURRENT_MOVIE_ID", currentMovie.getId());
                startActivity(movieIntent);
            }
        });
    }

    @Override
    public void handleAPISuccessResponse(Object response) {
        if (response instanceof  Movie) {
            this.watchMoviesWithProps.add((Movie) response);
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
        this.watchDataSource = new FavoritesDataSource(getApplicationContext());
        this.watchListView = (ListView) findViewById(R.id.movieWatchlistListView);
        this.watchMoviesFromDS = new ArrayList<Movie>();
        this.watchMoviesWithProps = new ArrayList<Movie>();
    }

    /**
     * Gets array of movie favourites from the database
     */
    private void loadWatchlistFromDataSource() {
        this.watchMoviesFromDS = this.watchDataSource.getAllMoviesFromWatchlist();
    }

    /**
     * Each movie has only API id, other properties need to fetched from the API
     */
    private void fetchWatchistDataFromAPI() {
        for (Movie watchMovie : this.watchMoviesFromDS) {
            this.apiClient.fetchMovie(String.valueOf(watchMovie.getId()));
        }
    }

    private void displayData() {
        this.moviesProvider.setData(this.watchMoviesWithProps);
        this.watchListView.setAdapter(this.moviesListAdapter);
    }
}
