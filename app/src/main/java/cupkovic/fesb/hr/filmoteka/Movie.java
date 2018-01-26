package cupkovic.fesb.hr.filmoteka;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by EliteBook40 on 1/21/2018.
 */

public class Movie {
    private int id;
    private ArrayList<Genre> genres;
    private ArrayList<Integer> genre_ids;
    private String original_title;
    private String overview;        // string or null
    private double popularity;
    private String poster_path;     // string or null
    private String release_date;    // format: date
    private int runtime;            // integer or null
    private double vote_average;
    private int vote_count;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }
    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getOriginal_title() {
        return original_title;
    }
    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getRuntime() {
        return runtime;
    }
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public double getVote_average() {
        return vote_average;
    }
    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }
    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}