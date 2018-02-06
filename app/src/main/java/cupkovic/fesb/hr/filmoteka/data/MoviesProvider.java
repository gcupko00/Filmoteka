package cupkovic.fesb.hr.filmoteka.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import cupkovic.fesb.hr.filmoteka.data.models.Movie;
import cupkovic.fesb.hr.filmoteka.interfaces.IDataProvider;

/**
 * This class is used to store a list of movies and access its contents
 */
public class MoviesProvider implements IDataProvider<Movie> {
    private ArrayList<Movie> movies = new ArrayList<>();

    /**
     * Gets a movie from the stored movies list by id
     * @param id Requested movie's id number (the one from API)
     * @return Movie object if it has been found in the list
     * @throws NoSuchElementException in case the movie with the specified id is not in the list
     */
    @Override
    public Movie getById(final String id) throws NoSuchElementException {
        for (Movie movie : this.movies) {
            if (movie.getId() == Integer.parseInt(id)) {
                return movie;
            }
        }

        throw new NoSuchElementException("Requested entry not found in the list");
    }

    /**
     * Adds a list of movies to already stored ones
     * @param newData List of movies to add
     */
    @Override
    public void putData(ArrayList<Movie> newData) {
        this.movies.addAll(newData);
    }

    /**
     * Sets the list of movies that will be used as a storage list
     * @param value The list that will be used as a movies storage list
     */
    @Override
    public void setData(ArrayList<Movie> value) {
        this.movies = value;
    }

    /**
     * Returns movie stored at the specified position in the list of movies
     * @param position Position of the object in the list
     * @return Movie stored at the specified position
     * @throws NoSuchElementException Raised if requested position is out of bounds of the list
     */
    @Override
    public Movie getAtPosition(int position) throws NoSuchElementException {
        if (position < this.movies.size()) {
            return this.movies.get(position);
        }

        throw new NoSuchElementException("Out of bounds");
    }

    /**
     * Gets the list of all stored movies
     * @return The list of all stored movies
     */
    @Override
    public ArrayList<Movie> getAllData() {
        return this.movies;
    }
}
