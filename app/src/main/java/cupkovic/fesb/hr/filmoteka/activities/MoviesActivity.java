package cupkovic.fesb.hr.filmoteka.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import cupkovic.fesb.hr.filmoteka.adapters.MoviesListAdapter;
import cupkovic.fesb.hr.filmoteka.R;
import cupkovic.fesb.hr.filmoteka.data.MoviesProvider;
import cupkovic.fesb.hr.filmoteka.data.models.Movie;
import cupkovic.fesb.hr.filmoteka.interfaces.IApiSubscriber;
import cupkovic.fesb.hr.filmoteka.utils.APIClient;

/**
 * Created by JureSedlar on 22-Jan-18.
 */

public class MoviesActivity extends AppCompatActivity implements IApiSubscriber {
    private MoviesProvider moviesProvider;
    private MoviesListAdapter moviesListAdapter;
    private ListView moviesListView;
    private SearchView moviesSearchView;
    private Button favouritesButton;
    private Button watchlistButton;
    private APIClient apiClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_activity_layout);
        setup();

        apiClient.fetchMovieList(moviesProvider);

        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent movieIntent = new Intent(MoviesActivity.this, IndividualMovieActivity.class);
                Movie currentMovie = moviesProvider.getAtPosition(position);
                movieIntent.putExtra("CURRENT_MOVIE_ID", currentMovie.getId());
                startActivity(movieIntent);
            }
        });

        moviesSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                apiClient.searchMovies(query,1,moviesProvider);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent favourites = new Intent(MoviesActivity.this, MovieFavouritesActivity.class);
                startActivity(favourites);
            }
        });

        watchlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent watchlist = new Intent(MoviesActivity.this, WatchlistActivity.class);
                startActivity(watchlist);
            }
        });
    }

    @Override
    public void handleAPISuccessResponse(Object response) {
        //test
        //Toast.makeText(getApplicationContext(), moviesProvider.getAllData().get(0).getOriginal_title(), Toast.LENGTH_LONG).show();
        moviesListView.setAdapter(this.moviesListAdapter);
    }

    @Override
    public void handleAPIErrorResponse(Object response) {
        Toast.makeText(getApplicationContext(), "API error", Toast.LENGTH_LONG).show();
    }

    private void setup() {
        this.moviesProvider = new MoviesProvider();
        this.apiClient = new APIClient(this);

        this.moviesListAdapter = new MoviesListAdapter(getApplicationContext(), this.moviesProvider);
        this.moviesListView = (ListView) findViewById(R.id.movies_ListView);
        this.moviesSearchView = (SearchView) findViewById(R.id.moviesSearchView);
        this.favouritesButton = (Button) findViewById(R.id.FavouritesBtn);
        this.watchlistButton = (Button) findViewById(R.id.WatchlistBtn);
    }
}