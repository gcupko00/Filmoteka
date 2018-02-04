package cupkovic.fesb.hr.filmoteka.data;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import cupkovic.fesb.hr.filmoteka.data.models.CrewMember;
import cupkovic.fesb.hr.filmoteka.interfaces.IDataProvider;

/**
 * Created by gcupk on 2/4/2018.
 */

public class CrewProvider implements IDataProvider<CrewMember> {
    private ArrayList<CrewMember> crewPeople = new ArrayList<>();

    @Override
    public CrewMember getById(String id) throws NoSuchElementException {
        for (CrewMember crewMember : this.crewPeople) {
            if (crewMember.getId() == Integer.parseInt(id)) {
                return crewMember;
            }
        }

        throw new NoSuchElementException("Requested entry not found in the list");
    }

    @Override
    public void putData(ArrayList<CrewMember> newData) {
        this.crewPeople.addAll(newData);
    }

    @Override
    public void setData(ArrayList<CrewMember> value) {
        this.crewPeople = value;
    }

    @Override
    public CrewMember getAtPosition(int position) throws NoSuchElementException {
        if (position < this.crewPeople.size()) {
            return this.crewPeople.get(position);
        }

        throw new NoSuchElementException("Out of bounds");
    }

    @Override
    public ArrayList<CrewMember> getAllData() {
        return this.crewPeople;
    }
}
