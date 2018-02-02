package cupkovic.fesb.hr.filmoteka.data;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import cupkovic.fesb.hr.filmoteka.data.models.Person;
import cupkovic.fesb.hr.filmoteka.interfaces.IDataProvider;

/**
 * This class is used to store a list of actors and access its contents
 */
public class ActorsProvider implements IDataProvider<Person> {
    private ArrayList<Person> actors = new ArrayList<>();

    /**
     * Gets an actor from the stored actors list by id
     * @param id Requested actor's id number (the one from API)
     * @return Actor object if it has been found in the list
     * @throws NoSuchElementException in case the actor with the specified id is not in the list
     */
    @Override
    public Person getById(String id) throws NoSuchElementException{
        for (Person actor : this.actors) {
            if (actor.getId() == Integer.parseInt(id)) {
                return actor;
            }
        }

        throw new NoSuchElementException("Requested entry not found in the list");
    }

    /**
     * Adds a list of actors to already stored ones
     * @param newData List of actors to add
     */
    @Override
    public void putData(ArrayList<Person> newData) {
        this.actors.addAll(newData);
    }

    /**
     * Sets the list of actors that will be used as a storage list
     * @param value The list that will be used as an actors storage list
     */
    @Override
    public void setData(ArrayList<Person> value) {
        this.actors = value;
    }

    /**
     * Gets the list of all stored actors
     * @return The list of all stored actors
     */
    @Override
    public ArrayList<Person> getAllData() {
        return this.actors;
    }
}
