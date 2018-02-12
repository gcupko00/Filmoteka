package cupkovic.fesb.hr.filmoteka.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import cupkovic.fesb.hr.filmoteka.R;
import cupkovic.fesb.hr.filmoteka.adapters.CreditsListAdapter;
import cupkovic.fesb.hr.filmoteka.data.CreditsProvider;
import cupkovic.fesb.hr.filmoteka.data.FavoritesDataSource;
import cupkovic.fesb.hr.filmoteka.data.models.MovieCredit;
import cupkovic.fesb.hr.filmoteka.data.models.Person;
import cupkovic.fesb.hr.filmoteka.interfaces.IApiSubscriber;
import cupkovic.fesb.hr.filmoteka.utils.APIClient;

public class IndividualActorActivity extends AppCompatActivity implements IApiSubscriber {

    private ImageView profile;
    private TextView name;
    private TextView gender;
    private TextView birthday;
    private TextView deathday;
    private TextView biography;
    private Button addToFavouritesButton;
    private Button removeFromFavouritesButton;
    private Button homeButton;

    private ListView creditsListView;
    private CreditsProvider creditsProvider;
    private CreditsListAdapter creditsListAdapter;

    private APIClient apiClient;
    private Person currentActor;
    private FavoritesDataSource favoritesDataSource;
    private String IMG_BASE_URL = "https://image.tmdb.org/t/p/w300";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_actor);
        setup();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int actorId = extras.getInt("CURRENT_ACTOR_ID");
            this.apiClient.fetchActor(String.valueOf(actorId));
        }

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent favourites = new Intent(IndividualActorActivity.this, MainActivity.class);
                startActivity(favourites);
            }
        });

        addToFavouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person dbActor = favoritesDataSource.getActorByApiId(currentActor.getId());
                if (dbActor.getId() == currentActor.getId()) {
                    Toast.makeText(getApplicationContext(),
                            "Already added!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    favoritesDataSource.addActorFavoriteToDb(currentActor.getId());

                    Toast.makeText(getApplicationContext(),
                            currentActor.getName() + " added",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        removeFromFavouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoritesDataSource.deleteActorFavoriteByApiId(currentActor.getId());

                Toast.makeText(getApplicationContext(),
                        currentActor.getName() + " removed",
                        Toast.LENGTH_LONG).show();
            }
        });


        this.creditsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent movieIntent = new Intent(IndividualActorActivity.this, IndividualMovieActivity.class);
                MovieCredit currentCredit = creditsProvider.getAtPosition(position);
                movieIntent.putExtra("CURRENT_MOVIE_ID", currentCredit.getId());
                startActivity(movieIntent);
            }
        });
    }

    @Override
    public void handleAPISuccessResponse(Object response) {
        if (response instanceof Person) {
            this.currentActor = (Person) response;
            apiClient.fetchMovieCredits(String.valueOf(this.currentActor.getId()), this.creditsProvider);
        }
        else if (response instanceof String && response.equals("MOVIE_CREDITS")) {
            if (this.currentActor != null)
                displayActorData();
        }
    }

    @Override
    public void handleAPIErrorResponse(Object response) {
        Toast.makeText(getApplicationContext(), "API error", Toast.LENGTH_LONG).show();
    }

    private void setup() {
        this.apiClient = new APIClient(this);
        this.favoritesDataSource = new FavoritesDataSource(getApplicationContext());
        this.creditsProvider = new CreditsProvider();

        this.profile = (ImageView) findViewById(R.id.individual_actor_image);
        this.name = (TextView) findViewById(R.id.individual_actor_name);
        this.gender = (TextView) findViewById(R.id.individual_actor_gender);
        this.birthday = (TextView) findViewById(R.id.individual_actor_birthday);
        this.deathday = (TextView) findViewById(R.id.individual_actor_deathday);
        this.biography = (TextView) findViewById(R.id.individual_actor_biography);
        this.homeButton = (Button) findViewById(R.id.homeBtn);
        this.addToFavouritesButton = (Button) findViewById(R.id.addActorToFavouritesBtn);
        this.removeFromFavouritesButton = (Button) findViewById(R.id.removeActorFromFavouritesBtn);
        this.creditsListView = (ListView) findViewById(R.id.creditsListView);

        this.creditsListAdapter = new CreditsListAdapter(getApplicationContext(), this.creditsProvider);
    }

    private void displayActorData() {
        String profilePath = IMG_BASE_URL + currentActor.getProfile_path();
        Ion.with(this.profile).load(profilePath);

        this.name.setText(currentActor.getName());
        this.gender.setText(currentActor.getGender());
        this.birthday.setText(currentActor.getBirthday());
        this.deathday.setText(currentActor.getDeathday());
        this.biography.setText(currentActor.getBiography());

        //if an actor is not added to favourites, do not display remove button
        //otherwise the button is displayed
        Person favActor = favoritesDataSource.getActorByApiId(currentActor.getId());
        if (favActor.getId() == this.currentActor.getId()) {
            this.removeFromFavouritesButton.setVisibility(View.VISIBLE);
        }
        else {

            this.removeFromFavouritesButton.setVisibility(View.INVISIBLE);
        }

        this.creditsListView.setAdapter(this.creditsListAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.creditsListView.setNestedScrollingEnabled(true);
        }
    }
}