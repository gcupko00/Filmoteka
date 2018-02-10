package cupkovic.fesb.hr.filmoteka.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import cupkovic.fesb.hr.filmoteka.R;
import cupkovic.fesb.hr.filmoteka.data.CastProvider;
import cupkovic.fesb.hr.filmoteka.data.CrewProvider;
import cupkovic.fesb.hr.filmoteka.data.FavoritesDataSource;
import cupkovic.fesb.hr.filmoteka.data.MoviesProvider;
import cupkovic.fesb.hr.filmoteka.data.models.CastMember;
import cupkovic.fesb.hr.filmoteka.data.models.Genre;
import cupkovic.fesb.hr.filmoteka.data.models.Movie;
import cupkovic.fesb.hr.filmoteka.interfaces.IApiSubscriber;
import cupkovic.fesb.hr.filmoteka.utils.APIClient;

public class IndividualMovieActivity extends AppCompatActivity implements IApiSubscriber {

    private ImageView poster;
    private TextView title;
    private TextView overview;
    private TextView runtime;
    private TextView genres;
    private TextView releaseDate;
    private TextView popularity;
    private TextView averageVote;
    private TextView voteCount;
    private TextView cast;
    private EditText editRating;
    private Button addToFavouritesButton;
    private Button removeFromFavouritesButton;
    private Button addToWatchlistButton;
    private Button removeFromWatchlistButton;
    private Button rateButton;

    private CastProvider castProvider;
    private CrewProvider crewProvider;
    private APIClient apiClient;
    private Movie currentMovie;
    private FavoritesDataSource favoritesDataSource;
    private String IMG_BASE_URL = "https://image.tmdb.org/t/p/w300";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_movie);
        setup();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int movieId = extras.getInt("CURRENT_MOVIE_ID");
            this.apiClient.fetchMovie(String.valueOf(movieId));
        }

        addToFavouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie dbMovie = favoritesDataSource.getMovieByApiId(currentMovie.getId());
                if (dbMovie.getId() == currentMovie.getId()) {
                    Toast.makeText(getApplicationContext(),
                            "Already added!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    favoritesDataSource.addMovieFavoriteToDb(currentMovie.getId());

                    Toast.makeText(getApplicationContext(),
                            currentMovie.getOriginal_title() + " added",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        removeFromFavouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoritesDataSource.deleteMovieFavoriteByApiId(currentMovie.getId());

                Toast.makeText(getApplicationContext(),
                        currentMovie.getOriginal_title() + " removed",
                        Toast.LENGTH_LONG).show();
            }
        });

        addToWatchlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie dbMovie = favoritesDataSource.getMovieFromWatchlistByApiId(currentMovie.getId());
                if (dbMovie.getId() == currentMovie.getId()) {
                    Toast.makeText(getApplicationContext(),
                            "Already added!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    favoritesDataSource.addMovieToWatchlist(currentMovie.getId());

                    Toast.makeText(getApplicationContext(),
                            currentMovie.getOriginal_title() + " added to watchlist",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        removeFromWatchlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoritesDataSource.deleteMovieFromWatchlistByApiId(currentMovie.getId());

                Toast.makeText(getApplicationContext(),
                        currentMovie.getOriginal_title() + " removed from watchlist",
                        Toast.LENGTH_LONG).show();
            }
        });

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double rating = Double.valueOf(editRating.getText().toString());
                String guestSessionId = favoritesDataSource.getGuestSessionId();
                apiClient.rateMovie(String.valueOf(currentMovie.getId()), rating, guestSessionId);
            }
        });

    }

    @Override
    public void handleAPISuccessResponse(Object response) {
        if (response instanceof Movie) {
            this.currentMovie = (Movie) response;
            loadCrewAndCastData(this.currentMovie.getId());
        }
        else if (response instanceof  String) {
            Toast.makeText(getApplicationContext(),
                    this.currentMovie.getOriginal_title() + " rated",
                    Toast.LENGTH_LONG).show();
        }

        else {
            displayMovieData();
        }
    }

    @Override
    public void handleAPIErrorResponse(Object response) {
        Toast.makeText(getApplicationContext(), "API error", Toast.LENGTH_LONG).show();
    }

    private void setup() {
        this.apiClient = new APIClient(this);
        this.castProvider = new CastProvider();
        this.crewProvider = new CrewProvider();
        this.favoritesDataSource = new FavoritesDataSource(getApplicationContext());

        this.poster = (ImageView) findViewById(R.id.individual_movie_image);
        this.title = (TextView) findViewById(R.id.individual_movie_title);
        this.overview = (TextView) findViewById(R.id.individual_movie_overview);
        this.genres = (TextView) findViewById(R.id.individual_movie_genres);
        this.releaseDate = (TextView) findViewById(R.id.individual_movie_releaseDate);
        this.runtime = (TextView) findViewById(R.id.individual_movie_runtime);
        this.popularity = (TextView) findViewById(R.id.individual_movie_popularity);
        this.averageVote = (TextView) findViewById(R.id.individual_movie_averageVote);
        this.voteCount = (TextView) findViewById(R.id.individual_movie_voteCount);
        this.cast = (TextView) findViewById(R.id.individual_movie_cast);
        this.addToFavouritesButton = (Button) findViewById(R.id.addMovieToFavouritesBtn);
        this.removeFromFavouritesButton = (Button) findViewById(R.id.removeMovieFromFavouritesBtn);
        this.addToWatchlistButton = (Button) findViewById(R.id.addMovieToWatchlistBtn);
        this.removeFromWatchlistButton = (Button) findViewById(R.id.removeMovieFromWatchlistBtn);
        this.editRating = (EditText) findViewById(R.id.editMovieRate);
        this.rateButton = (Button) findViewById(R.id.rateMovieButton);
    }

    private void loadCrewAndCastData(int movieId) {

        this.apiClient.fetchMovieCastAndCrew(String.valueOf(movieId), this.castProvider, this.crewProvider);

    }

    private void displayMovieData() {
        String posterPath = IMG_BASE_URL + currentMovie.getPoster_path();
        Ion.with(this.poster).load(posterPath);
        String genreString = concatGenresIntoString(currentMovie.getGenres());
        String castString = concatCastIntoString(this.castProvider.getAllData());

        this.title.setText(currentMovie.getOriginal_title());
        this.overview.setText(currentMovie.getOverview());
        this.genres.setText(genreString);
        this.releaseDate.setText(currentMovie.getRelease_date());
        this.runtime.setText(String.valueOf(currentMovie.getRuntime()) + " mins");
        this.popularity.setText(String.valueOf(currentMovie.getPopularity()));
        this.averageVote.setText(String.valueOf(currentMovie.getVote_average()));
        this.voteCount.setText(String.valueOf(currentMovie.getVote_count()));
        this.cast.setText(castString);

        //if a movie is not added to favourites, do not display remove button
        //otherwise the button is displayed
        Movie favMovie = favoritesDataSource.getMovieByApiId(currentMovie.getId());
        if (favMovie.getId() == this.currentMovie.getId()) {
            this.removeFromFavouritesButton.setVisibility(View.VISIBLE);
        }
        else {

            this.removeFromFavouritesButton.setVisibility(View.INVISIBLE);
        }

        Movie watchMovie = favoritesDataSource.getMovieFromWatchlistByApiId(currentMovie.getId());
        if (watchMovie.getId() == this.currentMovie.getId()) {
            this.removeFromWatchlistButton.setVisibility(View.VISIBLE);
        }
        else {

            this.removeFromWatchlistButton.setVisibility(View.INVISIBLE);
        }
    }

    private String concatGenresIntoString(ArrayList<Genre> genres) {
        String genreNamesIntoString = "";

        for (Genre g : genres) {
            genreNamesIntoString += g.getName() + "\n";
        }

        return genreNamesIntoString;
    }

    private String concatCastIntoString(ArrayList<CastMember> cast) {
        String castIntoString = "";

        for (CastMember member : cast) {
            String memberInfo = member.getName() + " - " + member.getCharacter();
            castIntoString += "- " + memberInfo + "\n";
        }

        return castIntoString;
    }
}
