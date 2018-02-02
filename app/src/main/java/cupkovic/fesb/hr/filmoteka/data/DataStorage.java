package cupkovic.fesb.hr.filmoteka.data;

import java.util.ArrayList;

import cupkovic.fesb.hr.filmoteka.data.models.Movie;
import cupkovic.fesb.hr.filmoteka.data.models.MovieCredit;
import cupkovic.fesb.hr.filmoteka.data.models.Person;

/**
 * Created by EliteBook40 on 1/21/2018.
 */

@Deprecated
public class DataStorage {
    public static ArrayList<Movie> moviesList = new ArrayList<Movie>();
    public static ArrayList<Person> personsList = new ArrayList<Person>();
    public static ArrayList<MovieCredit> movieCreditsList = new ArrayList<MovieCredit>();

    public static Movie getMovieById(Integer id) {
        for (int i = 0; i < moviesList.size(); i++) {
            if (moviesList.get(i).getId() == id) {
                return moviesList.get(i);
            }
        }
        return null;
    }

    public static Person getPersonById(Integer id) {
        for (int i = 0; i < personsList.size(); i++) {
            if (personsList.get(i).getId() == id) {
                return personsList.get(i);
            }
        }
        return null;
    }

    public static MovieCredit getMovieCreditById(String credit_id) {
        for (int i = 0; i < movieCreditsList.size(); i++) {
            if (String.valueOf(movieCreditsList.get(i).getId()) == credit_id) {
                return movieCreditsList.get(i);
            }
        }
        return null;
    }
}