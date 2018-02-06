package cupkovic.fesb.hr.filmoteka.data.models;

/**
 * Created by gcupk on 2/4/2018.
 */

public class CastMember extends Person {
    private int cast_id;
    private String credit_id;
    private String character;

    public int getCast_id() {
        return cast_id;
    }

    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

}
