package cupkovic.fesb.hr.filmoteka;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by JureSedlar on 22-Jan-18.
 */

public class ActorsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actors_activity_layout);



        ListView actorsListView = (ListView) findViewById(R.id.actors_ListView);

        actorsListView.setAdapter(new ActorsListAdapter(getApplicationContext()));

        actorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}