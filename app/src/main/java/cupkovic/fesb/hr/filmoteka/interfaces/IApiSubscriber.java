package cupkovic.fesb.hr.filmoteka.interfaces;

/**
 * This interface defines callback methods intended for handling API responses.
 * Classes which implement this interface use the APIClient to fetch data from API
 */
public interface IApiSubscriber {
    /**
     * This method handles a successful response from the API.
     * @param response API response which can be any object
     */
    void handleAPISuccessResponse(Object response);

    /**
     * This method handles an unsuccessful response from the API.
     * @param response API error response which can be any object
     */
    void handleAPIErrorResponse(Object response);
}
