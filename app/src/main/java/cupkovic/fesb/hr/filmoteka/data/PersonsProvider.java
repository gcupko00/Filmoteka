package cupkovic.fesb.hr.filmoteka.data;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import cupkovic.fesb.hr.filmoteka.data.models.Person;
import cupkovic.fesb.hr.filmoteka.interfaces.IDataProvider;

/**
 * This class is used to store a list of persons and access its contents
 */
public class PersonsProvider implements IDataProvider<Person> {
    private ArrayList<Person> persons = new ArrayList<>();

    /**
     * Gets an actor from the stored persons list by id
     * @param id Requested actor's id number (the one from API)
     * @return Actor object if it has been found in the list
     * @throws NoSuchElementException in case the actor with the specified id is not in the list
     */
    @Override
    public Person getById(String id) throws NoSuchElementException{
        for (Person actor : this.persons) {
            if (actor.getId() == Integer.parseInt(id)) {
                return actor;
            }
        }

        throw new NoSuchElementException("Requested entry not found in the list");
    }

    /**
     * Adds a list of persons to already stored ones
     * @param newData List of persons to add
     */
    @Override
    public void putData(ArrayList<Person> newData) {
        this.persons.addAll(newData);
    }

    /**
     * Sets the list of persons that will be used as a storage list
     * @param value The list that will be used as an persons storage list
     */
    @Override
    public void setData(ArrayList<Person> value) {
        this.persons = value;
    }

    /**
     * Returns actor stored at the specified position in the list of persons
     * @param position Position of the object in the list
     * @return Actor object stored at the specified position
     * @throws NoSuchElementException Raised if requested position is out of bounds of the list
     */
    @Override
    public Person getAtPosition(int position) throws NoSuchElementException {
        if (position < this.persons.size()) {
            return this.persons.get(position);
        }

        throw new NoSuchElementException("Out of bounds");
    }

    /**
     * Gets the list of all stored persons
     * @return The list of all stored persons
     */
    @Override
    public ArrayList<Person> getAllData() {
        return this.persons;
    }
}
