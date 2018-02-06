package cupkovic.fesb.hr.filmoteka.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import cupkovic.fesb.hr.filmoteka.R;
import cupkovic.fesb.hr.filmoteka.data.CastProvider;
import cupkovic.fesb.hr.filmoteka.data.CrewProvider;
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
    private Button addToFavouritesButton;

    private CastProvider castProvider;
    private CrewProvider crewProvider;
    private APIClient apiClient;
    private Movie currentMovie;
    private String IMG_BASE_URL = "https://image.tmdb.org/t/p/w185";

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

    }

    @Override
    public void handleAPISuccessResponse(Object response) {
        if (response instanceof Movie) {
            this.currentMovie = (Movie) response;
            loadCrewAndCastData(this.currentMovie.getId());
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
        this.addToFavouritesButton = (Button) findViewById(R.id.addToFavouritesBtn);
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

        Log.i("cast", this.castProvider.getAllData().get(0).getName());

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

        for (CastMember member : cast.) {
            String memeberInfo = member.getName() + " - " + member.getCharacter();
            castIntoString += "- " + memeberInfo + "\n";
        }

        Log.i("cast", castIntoString);
        return castIntoString;
    }
}
