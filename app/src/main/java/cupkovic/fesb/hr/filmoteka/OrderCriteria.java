package cupkovic.fesb.hr.filmoteka;

/**
 * Created by gcupk on 1/14/2018.
 */

public enum OrderCriteria {
    POPULAR("/popular"),
    TOP_RATED("top_rated"),
    LATEST("/latest");

    private final String text;

    private OrderCriteria(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
