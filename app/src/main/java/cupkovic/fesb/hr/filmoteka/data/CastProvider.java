package cupkovic.fesb.hr.filmoteka.data;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import cupkovic.fesb.hr.filmoteka.data.models.CastMember;
import cupkovic.fesb.hr.filmoteka.interfaces.IDataProvider;

/**
 * Created by gcupk on 2/4/2018.
 */

public class CastProvider implements IDataProvider<CastMember> {
    private ArrayList<CastMember> castPeople = new ArrayList<>();

    @Override
    public CastMember getById(String id) throws NoSuchElementException {
        for (CastMember castMember : this.castPeople) {
            if (castMember.getId() == Integer.parseInt(id)) {
                return castMember;
            }
        }

        throw new NoSuchElementException("Requested entry not found in the list");
    }

    @Override
    public void putData(ArrayList<CastMember> newData) {
        this.castPeople.addAll(newData);
    }

    @Override
    public void setData(ArrayList<CastMember> value) {
        this.castPeople = value;
    }

    @Override
    public CastMember getAtPosition(int position) throws NoSuchElementException {
        if (position < this.castPeople.size()) {
            return this.castPeople.get(position);
        }

        throw new NoSuchElementException("Out of bounds");
    }

    @Override
    public ArrayList<CastMember> getAllData() {
        return this.castPeople;
    }
}
