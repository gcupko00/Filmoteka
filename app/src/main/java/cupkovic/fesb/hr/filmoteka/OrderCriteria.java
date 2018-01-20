package cupkovic.fesb.hr.filmoteka;

/**
 * Created by gcupk on 1/14/2018.
 */

public enum OrderCriteria {
    POPULAR("/popular"),
    TOP_RATED("top_rated"),
    LATEST("/latest");

    private OrderCriteria(String s) { }
}
