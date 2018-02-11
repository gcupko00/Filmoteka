package cupkovic.fesb.hr.filmoteka.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import cupkovic.fesb.hr.filmoteka.R;
import cupkovic.fesb.hr.filmoteka.data.PersonsProvider;
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

        ImageView profileImage = (ImageView) convertView.findViewById(R.id.profileImage);
        TextView actorsName = (TextView) convertView.findViewById(R.id.actorsName);
        TextView gender= (TextView) convertView.findViewById(R.id.gender);
        TextView birthday = (TextView) convertView.findViewById(R.id.birthday);

        Person currentPerson = personsProvider.getAtPosition(position);

        String profilePath = THUMB_BASE_URL + currentPerson.getProfile_path();
        Ion.with(profileImage).load(profilePath);

        actorsName.setText(currentPerson.getName());
        gender.setText(currentPerson.getGender());
        birthday.setText(currentPerson.getBirthday());

        return convertView;
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