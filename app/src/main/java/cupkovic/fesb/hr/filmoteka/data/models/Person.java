package cupkovic.fesb.hr.filmoteka.data.models;

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
    private String profile_path;

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

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

    public String getGender() {
        if(gender == 1)
            return "female";
        else if(gender == 2)
            return "male";
        else
            return "unknown";
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