package cupkovic.fesb.hr.filmoteka.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import cupkovic.fesb.hr.filmoteka.adapters.MoviesListAdapter;
import cupkovic.fesb.hr.filmoteka.R;
import cupkovic.fesb.hr.filmoteka.data.FavoritesDataSource;
import cupkovic.fesb.hr.filmoteka.data.MoviesProvider;
import cupkovic.fesb.hr.filmoteka.data.models.Genre;
import cupkovic.fesb.hr.filmoteka.data.models.Movie;
import cupkovic.fesb.hr.filmoteka.interfaces.IApiSubscriber;
import cupkovic.fesb.hr.filmoteka.utils.APIClient;

public class MoviesActivity extends AppCompatActivity implements IApiSubscriber {
    private MoviesProvider moviesProvider;
    private MoviesListAdapter moviesListAdapter;
    private APIClient apiClient;
    private FavoritesDataSource favoritesDataSource;

    private ListView moviesListView;
    private SearchView moviesSearchView;
    private Spinner filtersSpinner;
    private EditText filtersEditText;
    private Button filtersButton;
    private Button resetButton;
    private Button favouritesButton;
    private Button watchlistButton;
    private Button homeButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_activity_layout);
        setup();

        checkIfGuestSessionIdExists();
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

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent favourites = new Intent(MoviesActivity.this, MainActivity.class);
                startActivity(favourites);
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

        filtersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedFilter = filtersSpinner.getSelectedItem().toString();
                String filterValue = filtersEditText.getText().toString();

                if (selectedFilter.isEmpty() || filterValue.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select filter and enter a value",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    filterMoviesWithSelectedFilter(selectedFilter, filterValue);
                }
            }
        });

        //clear all filter and search results and load default popular movies
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiClient.fetchMovieList(moviesProvider);
            }
        });
    }

    @Override
    public void handleAPISuccessResponse(Object response) {
        if (response instanceof String) {
            this.favoritesDataSource.saveGuestSessionId((String) response);
        }

        displayData();
    }

    @Override
    public void handleAPIErrorResponse(Object response) {
        Toast.makeText(getApplicationContext(), "API error", Toast.LENGTH_LONG).show();
    }

    private void setup() {
        this.moviesProvider = new MoviesProvider();
        this.apiClient = new APIClient(this);
        this.favoritesDataSource = new FavoritesDataSource(getApplicationContext());

        this.moviesListAdapter = new MoviesListAdapter(getApplicationContext(), this.moviesProvider);
        this.moviesListView = (ListView) findViewById(R.id.movies_ListView);
        this.moviesSearchView = (SearchView) findViewById(R.id.moviesSearchView);
        this.homeButton = (Button) findViewById(R.id.homeBtn);
        this.favouritesButton = (Button) findViewById(R.id.FavouritesBtn);
        this.watchlistButton = (Button) findViewById(R.id.WatchlistBtn);
        this.filtersSpinner = (Spinner) findViewById(R.id.filtersSpinner);
        this.filtersEditText = (EditText) findViewById(R.id.filtersEditText);
        this.filtersButton = (Button) findViewById(R.id.filtersButton);
        this.resetButton = (Button) findViewById(R.id.resetButton);
    }

    private void displayData() {
        moviesListView.setAdapter(this.moviesListAdapter);
    }

    private void checkIfGuestSessionIdExists() {
        if (favoritesDataSource.getGuestSessionId().isEmpty()) {
            this.apiClient.fetchGuestSessionId();
        }
    }

    private void filterMoviesWithSelectedFilter(String filter, String value) {
        switch (filter) {
            case "Year":
                filterMoviesBasedOnYear(value);
                break;
            case "Vote":
                filterMoviesBasedOnVote(value);
                break;
        }
    }

    private void filterMoviesBasedOnYear(String year) {
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();

        for (Movie currentMovie : this.moviesProvider.getAllData()) {
            if (currentMovie.getRelease_date().contains(year)) {
                filteredMovies.add(currentMovie);
            }
        }

        this.moviesProvider.setData(filteredMovies);
        displayData();
    }

    private void filterMoviesBasedOnVote(String vote) {
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();

        for (Movie currentMovie : this.moviesProvider.getAllData()) {
            if (currentMovie.getVote_average() >= Double.valueOf(vote)) {
                filteredMovies.add(currentMovie);
            }
        }

        this.moviesProvider.setData(filteredMovies);
        displayData();
    }
}