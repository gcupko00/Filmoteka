package cupkovic.fesb.hr.filmoteka;

/**
 * Created by EliteBook40 on 1/21/2018.
 */

public class MovieCredit extends Movie {
    private String character;
    private String credit_id;

    public String getCharacter() {
        return character;
    }
    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCredit_id() {
        return credit_id;
    }
    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }
}