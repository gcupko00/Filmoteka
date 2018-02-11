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
import cupkovic.fesb.hr.filmoteka.adapters.ActorsListAdapter;
import cupkovic.fesb.hr.filmoteka.data.FavoritesDataSource;
import cupkovic.fesb.hr.filmoteka.data.PersonsProvider;
import cupkovic.fesb.hr.filmoteka.data.models.Person;
import cupkovic.fesb.hr.filmoteka.interfaces.IApiSubscriber;
import cupkovic.fesb.hr.filmoteka.utils.APIClient;

public class ActorFavouritesActivity extends AppCompatActivity implements IApiSubscriber {

    private APIClient apiClient;
    private ActorsListAdapter actorsListAdapter;
    private PersonsProvider personsProvider;
    private FavoritesDataSource favoritesDataSource;
    private ListView actorsListView;
    private ArrayList<Person> favouritesFromDS;
    private ArrayList<Person> favouritesWithAllProps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_favourites);
        setup();

        loadFavouriteActorsFromDataSource();
        fetchFavouritesDataFromAPI();

        actorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent actorIntent = new Intent(ActorFavouritesActivity.this, IndividualActorActivity.class);
                Person currentActor = personsProvider.getAtPosition(position);
                actorIntent.putExtra("CURRENT_ACTOR_ID", currentActor.getId());
                startActivity(actorIntent);
            }
        });
    }

    @Override
    public void handleAPISuccessResponse(Object response) {
        if (response instanceof  Person) {
            this.favouritesWithAllProps.add((Person) response);
            displayData();
        }
    }

    @Override
    public void handleAPIErrorResponse(Object response) {
        Toast.makeText(getApplicationContext(), "API error", Toast.LENGTH_LONG).show();
    }

    private void setup() {
        this.apiClient = new APIClient(this);
        this.personsProvider = new PersonsProvider();
        this.actorsListAdapter = new ActorsListAdapter(getApplicationContext(), this.personsProvider);
        this.favoritesDataSource = new FavoritesDataSource(getApplicationContext());
        this.actorsListView = (ListView) findViewById(R.id.actorFavouritesListView);
        this.favouritesFromDS = new ArrayList<Person>();
        this.favouritesWithAllProps = new ArrayList<Person>();
    }

    /**
     * Gets array of actor favourites from the database
     */
    private void loadFavouriteActorsFromDataSource() {
        this.favouritesFromDS = this.favoritesDataSource.getAllActorFavorites();
    }

    /**
     * Each actor has only API id, other properties need to fetched from the API
     */
    private void fetchFavouritesDataFromAPI() {
        for (Person favourite : this.favouritesFromDS) {
            this.apiClient.fetchActor(String.valueOf(favourite.getId()));
        }
    }

    private void displayData() {
        this.personsProvider.setData(this.favouritesWithAllProps);
        this.actorsListView.setAdapter(this.actorsListAdapter);
    }
}
