package cupkovic.fesb.hr.filmoteka.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cupkovic.fesb.hr.filmoteka.R;

/**
 * Created by JureSedlar on 22-Jan-18.
 */

public class MoviesListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    public MoviesListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 1;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.movies_activity_item, parent, false);
        }


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