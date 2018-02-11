package cupkovic.fesb.hr.filmoteka.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Arrays;

import cupkovic.fesb.hr.filmoteka.R;
import cupkovic.fesb.hr.filmoteka.data.PersonsProvider;
import cupkovic.fesb.hr.filmoteka.data.models.Movie;
import cupkovic.fesb.hr.filmoteka.data.models.Person;

public class ActorsListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private PersonsProvider personsProvider;
    private String THUMB_BASE_URL = "https://image.tmdb.org/t/p/w45";

    public ActorsListAdapter(Context context, PersonsProvider personsProvider) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.personsProvider = personsProvider;
    }

    @Override
    public int getCount() {
        return personsProvider.getAllData().size();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.actors_activity_item, parent, false);
        }

        ImageView profileImage = convertView.findViewById(R.id.profileImage);
        TextView actorsName = convertView.findViewById(R.id.actorsName);
        TextView knownFor = convertView.findViewById(R.id.knownFor);

        Person currentPerson = personsProvider.getAtPosition(position);

        String profilePath = THUMB_BASE_URL + currentPerson.getProfile_path();
        Ion.with(profileImage).load(profilePath);

        actorsName.setText(currentPerson.getName());

        ArrayList<Movie> theirMovies = currentPerson.getKnown_for();
        knownFor.setText(createCreditsString(theirMovies));

        return convertView;
    }

    private String createCreditsString(ArrayList<Movie> movies) {
        String credits = "";

        if (movies != null) {
            for (int i = 0; i < movies.size(); i++) {
                String title = movies.get(i).getOriginal_title();
                if (title != null && !title.equals("null") && !title.equals("")) {
                    if (!credits.equals("")) credits += ", ";
                    credits += title;
                }
            }
        }

        return credits;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}