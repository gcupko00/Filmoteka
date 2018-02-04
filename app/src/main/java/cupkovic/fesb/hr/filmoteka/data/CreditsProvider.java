package cupkovic.fesb.hr.filmoteka.data;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import cupkovic.fesb.hr.filmoteka.data.models.MovieCredit;
import cupkovic.fesb.hr.filmoteka.interfaces.IDataProvider;

/**
 * This class is used to store a list of movie credits and access its contents
 */
public class CreditsProvider implements IDataProvider<MovieCredit> {
    private ArrayList<MovieCredit> credits = new ArrayList<>();

    /**
     * Gets a movie credit from the stored movie credits list by id
     * @param id Requested movie's id number (the one from API)
     * @return MovieCredit object if it has been found in the list
     * @throws NoSuchElementException in case the movie credit with the specified id is not in the list
     */
    @Override
    public MovieCredit getById(String id) throws NoSuchElementException {
        for (MovieCredit credit : this.credits) {
            if (credit.getId() == Integer.parseInt(id)) {
                return credit;
            }
        }

        throw new NoSuchElementException("Requested entry not found in the list");
    }

    /**
     * Adds a list of movie credits to already stored ones
     * @param newData List of movie credits to add
     */
    @Override
    public void putData(ArrayList<MovieCredit> newData) {
        this.credits.addAll(newData);
    }

    /**
     * Sets the list of movie credits that will be used as a storage list
     * @param value The list that will be used as a movie credits storage list
     */
    @Override
    public void setData(ArrayList<MovieCredit> value) {
        this.credits = value;
    }

    /**
     * Returns movie credit stored at the specified position in the list of movies
     * @param position Position of the object in the list
     * @return Movie credit stored at the specified position
     * @throws NoSuchElementException Raised if requested position is out of bounds of the list
     */
    @Override
    public MovieCredit getAtPosition(int position) throws NoSuchElementException {
        if (position < this.credits.size()) {
            return this.credits.get(position);
        }

        throw new NoSuchElementException("Out of bounds");
    }

    /**
     * Gets the list of all stored movie credits
     * @return The list of all stored movie credits
     */
    @Override
    public ArrayList<MovieCredit> getAllData() {
        return this.credits;
    }
}
