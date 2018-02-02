package cupkovic.fesb.hr.filmoteka.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cupkovic.fesb.hr.filmoteka.utils.APIClient;
import cupkovic.fesb.hr.filmoteka.interfaces.IApiSubscriber;
import cupkovic.fesb.hr.filmoteka.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Movie_Button = (Button) findViewById(R.id.MoviesBtn);
        Button Actors_Button = (Button) findViewById(R.id.ActorsBtn);

        Movie_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newMoviesActivity = new Intent(MainActivity.this, MoviesActivity.class);
                startActivity(newMoviesActivity);
            }
        });

        Actors_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActorsActivity = new Intent(MainActivity.this, ActorsActivity.class);
                startActivity(newActorsActivity);
            }
        });
    }
}
