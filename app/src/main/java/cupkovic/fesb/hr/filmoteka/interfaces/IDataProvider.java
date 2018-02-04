package cupkovic.fesb.hr.filmoteka.interfaces;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This interface should be implemented by all data provider classes. It specifies methods used
 * to modify and access data. All the methods do their operations over the list of objects of type T
 */
public interface IDataProvider<T> {
    /**
     * Gets the element from the data storage list
     * @param id Element identification number (or string)
     * @return Element of type T
     * @throws NoSuchElementException Raised if element is not found
     */
    T getById(String id) throws NoSuchElementException;

    /**
     * Adds new elements to the storage list
     * @param newData List of elements of type T to add
     */
    void putData(ArrayList<T> newData);

    /**
     * Sets the storage list
     * @param value List of objects of type T to which storage list should be set
     */
    void setData(ArrayList<T> value);

    /**
     * Returns object of type T stored at the specified position in the list
     * @param position Position of the object in the list
     * @return Element at the specified location
     * @throws NoSuchElementException Raised if requested position is out of bounds of the list
     */
    T getAtPosition(int position) throws NoSuchElementException;

    /**
     * Gets all the stored data
     * @return The list of the stored objects of type T
     */
    ArrayList<T> getAllData();
}
