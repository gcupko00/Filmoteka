package cupkovic.fesb.hr.filmoteka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements IApiSubscribe {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        APIClient apiClient = new APIClient(this);
    }

    @Override
    public void handleAPISuccessResponse(Object result) {
    }

    @Override
    public void handleAPIErrorResponse(Object result) {

    }
}
