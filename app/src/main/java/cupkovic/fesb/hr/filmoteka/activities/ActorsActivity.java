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

import cupkovic.fesb.hr.filmoteka.adapters.ActorsListAdapter;
import cupkovic.fesb.hr.filmoteka.R;
import cupkovic.fesb.hr.filmoteka.data.FavoritesDataSource;
import cupkovic.fesb.hr.filmoteka.data.PersonsProvider;
import cupkovic.fesb.hr.filmoteka.data.models.Person;
import cupkovic.fesb.hr.filmoteka.interfaces.IApiSubscriber;
import cupkovic.fesb.hr.filmoteka.utils.APIClient;

public class ActorsActivity extends AppCompatActivity implements IApiSubscriber {
    private PersonsProvider personsProvider;
    private ActorsListAdapter actorsListAdapter;
    private APIClient apiClient;
    private FavoritesDataSource favoritesDataSource;

    private ListView actorsListView;
    private SearchView actorsSearchView;
    private Button favouritesButton;
    private Button homeButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actors_activity_layout);
        setup();

        checkIfGuestSessionIdExists();
        apiClient.fetchActorList(personsProvider);

        actorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent actorIntent = new Intent(ActorsActivity.this, IndividualActorActivity.class);
                Person currentActor = personsProvider.getAtPosition(position);
                actorIntent.putExtra("CURRENT_ACTOR_ID", currentActor.getId());
                startActivity(actorIntent);
            }
        });

        actorsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                apiClient.searchActors(query,1,personsProvider);
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
                Intent favourites = new Intent(ActorsActivity.this, MainActivity.class);
                startActivity(favourites);
            }
        });

        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent favourites = new Intent(ActorsActivity.this, ActorFavouritesActivity.class);
                startActivity(favourites);
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
        this.personsProvider = new PersonsProvider();
        this.apiClient = new APIClient(this);
        this.favoritesDataSource = new FavoritesDataSource(getApplicationContext());

        this.actorsListAdapter = new ActorsListAdapter(getApplicationContext(), this.personsProvider);
        this.actorsListView = (ListView) findViewById(R.id.actors_ListView);
        this.actorsSearchView = (SearchView) findViewById(R.id.actors_SearchView);
        this.homeButton = (Button) findViewById(R.id.homeBtn);
        this.favouritesButton = (Button) findViewById(R.id.FavouritesBtn);
    }

    private void displayData() {
        actorsListView.setAdapter(this.actorsListAdapter);
    }

    private void checkIfGuestSessionIdExists() {
        if (favoritesDataSource.getGuestSessionId().isEmpty()) {
            this.apiClient.fetchGuestSessionId();
        }
    }
}