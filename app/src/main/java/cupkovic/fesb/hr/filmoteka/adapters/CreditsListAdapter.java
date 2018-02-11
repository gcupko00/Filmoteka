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
import cupkovic.fesb.hr.filmoteka.data.CastProvider;
import cupkovic.fesb.hr.filmoteka.data.CreditsProvider;
import cupkovic.fesb.hr.filmoteka.data.models.CastMember;
import cupkovic.fesb.hr.filmoteka.data.models.MovieCredit;

/**
 * Created by gcupk on 2/11/2018.
 */

public class CreditsListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private CreditsProvider creditsProvider;
    private String THUMB_BASE_URL = "https://image.tmdb.org/t/p/w45";

    public CreditsListAdapter(Context context, CreditsProvider creditsProvider) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.creditsProvider = creditsProvider;
    }

    @Override
    public int getCount() {
        return creditsProvider.getAllData().size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.movie_credit_item, parent, false);
        }

        ImageView posterImage = convertView.findViewById(R.id.posterImage);
        TextView movieTitle = convertView.findViewById(R.id.movieName);
        TextView characterName = convertView.findViewById(R.id.characterName);

        MovieCredit currentCredit = this.creditsProvider.getAtPosition(position);

        String posterPath = THUMB_BASE_URL + currentCredit.getPoster_path();
        Ion.with(posterImage).load(posterPath);

        movieTitle.setText(currentCredit.getOriginal_title());
        characterName.setText(currentCredit.getCharacter());

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
