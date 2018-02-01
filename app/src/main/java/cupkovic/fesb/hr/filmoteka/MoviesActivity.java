package cupkovic.fesb.hr.filmoteka;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by JureSedlar on 22-Jan-18.
 */

public class MoviesActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_activity_layout);



        ListView moviesListView = (ListView) findViewById(R.id.movies_ListView);

        moviesListView.setAdapter(new MoviesListAdapter(getApplicationContext()));

        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}