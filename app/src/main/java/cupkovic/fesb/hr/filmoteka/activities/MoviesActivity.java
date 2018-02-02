package cupkovic.fesb.hr.filmoteka.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import cupkovic.fesb.hr.filmoteka.adapters.MoviesListAdapter;
import cupkovic.fesb.hr.filmoteka.R;
import cupkovic.fesb.hr.filmoteka.data.MoviesProvider;
import cupkovic.fesb.hr.filmoteka.interfaces.IApiSubscriber;
import cupkovic.fesb.hr.filmoteka.utils.APIClient;

/**
 * Created by JureSedlar on 22-Jan-18.
 */

public class MoviesActivity extends AppCompatActivity implements IApiSubscriber {
    MoviesProvider moviesProvider;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_activity_layout);

        moviesProvider = new MoviesProvider();
        APIClient apiClient = new APIClient(this);

        apiClient.fetchMovieList(moviesProvider);

        ListView moviesListView = (ListView) findViewById(R.id.movies_ListView);

        moviesListView.setAdapter(new MoviesListAdapter(getApplicationContext()));

        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void handleAPISuccessResponse(Object response) {
        //test
        //Toast.makeText(getApplicationContext(), moviesProvider.getAllData().get(0).getOriginal_title(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleAPIErrorResponse(Object response) {

    }
}