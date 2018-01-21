package cupkovic.fesb.hr.filmoteka;

/**
 * Created by EliteBook40 on 1/21/2018.
 */

public class Person {
    private String birthday;    // string or null
    private String deathday;    // string or null
    private String biography;
    private int gender;         // only allowed values 0: not set, 1: Female, 2: Male
    private int id;
    private String name;

    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }
    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public String getBiography() {
        return biography;
    }
    public void setBiography(String biography) {
        this.biography = biography;
    }

    public int getGender() {
        return gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}