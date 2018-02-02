package cupkovic.fesb.hr.filmoteka.data.models;

/**
 * Created by gcupk on 1/25/2018.
 */

public class Genre {
    private int id;
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
