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
import cupkovic.fesb.hr.filmoteka.data.PersonsProvider;
import cupkovic.fesb.hr.filmoteka.data.models.CastMember;
import cupkovic.fesb.hr.filmoteka.data.models.Person;

/**
 * Created by gcupk on 2/11/2018.
 */

public class CastListAdapter extends BaseAdapter {private Context context;
    private LayoutInflater layoutInflater;
    private CastProvider castProvider;
    private String THUMB_BASE_URL = "https://image.tmdb.org/t/p/w45";

    public CastListAdapter(Context context, CastProvider castProvider) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.castProvider = castProvider;
    }

    @Override
    public int getCount() {
        return castProvider.getAllData().size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.cast_item, parent, false);
        }

        ImageView profileImage = convertView.findViewById(R.id.profileImage);
        TextView actorsName = convertView.findViewById(R.id.actorsName);
        TextView characterName = convertView.findViewById(R.id.characterName);

        CastMember currentCastMember = castProvider.getAtPosition(position);

        String profilePath = THUMB_BASE_URL + currentCastMember.getProfile_path();
        Ion.with(profileImage).load(profilePath);

        actorsName.setText(currentCastMember.getName());
        characterName.setText(currentCastMember.getCharacter());

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
