package cupkovic.fesb.hr.filmoteka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements IApiSubscribe {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        APIClient apiClient = new APIClient(this);

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

    @Override
    public void handleAPISuccessResponse(Object result) {
    }

    @Override
    public void handleAPIErrorResponse(Object result) {

    }
}
