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
import cupkovic.fesb.hr.filmoteka.data.MoviesProvider;
import cupkovic.fesb.hr.filmoteka.data.models.Movie;

public class MoviesListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private MoviesProvider moviesProvider;
    private String THUMB_BASE_URL = "https://image.tmdb.org/t/p/w45";

    public MoviesListAdapter(Context context, MoviesProvider moviesProvider) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.moviesProvider = moviesProvider;
    }

    @Override
    public int getCount() {
        return moviesProvider.getAllData().size();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.movies_activity_item, parent, false);
        }

        ImageView posterImage = (ImageView) convertView.findViewById(R.id.profileImage);
        TextView movieTitle = (TextView) convertView.findViewById(R.id.movieTitle);
        TextView averageVote = (TextView) convertView.findViewById(R.id.averageVote);
        TextView releaseDate = (TextView) convertView.findViewById(R.id.releaseDate);

        Movie currentMovie = moviesProvider.getAtPosition(position);

        String posterPath = THUMB_BASE_URL + currentMovie.getPoster_path();
        Ion.with(posterImage).load(posterPath);

        movieTitle.setText(currentMovie.getOriginal_title());
        averageVote.setText(String.valueOf(currentMovie.getVote_average()));
        releaseDate.setText(currentMovie.getRelease_date());

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