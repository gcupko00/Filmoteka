package cupkovic.fesb.hr.filmoteka;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by gcupk on 1/13/2018.
 */

public class APIClient {
    public static final String API_URL = "https://api.themoviedb.org/3";
    public static final String API_KEY = "f0e80754f643ca84a2b11614f3b5eadd";
    public static final String KEY_QUERY = "?api_key=";
    public static final String PAGE_QUERY = "page=";
    public static final String SLASH = "/";
    public static final String AND = "&";

    public static final String AUTH_QUERY = KEY_QUERY + API_KEY;

    // API request paths
    public static final String MOVIE = "/movie";
    public static final String ACTOR = "/person";
    public static final String ACTOR_LIST = "/person/popular";

    private static AsyncHttpClient httpClient = new AsyncHttpClient();

    // Private constructor to prevent instantiation
    private APIClient() {
    }

    public static void fetchMovieList(OrderCriteria orderCriteria, int page) {
        String url = API_URL + MOVIE + orderCriteria + AUTH_QUERY + AND + PAGE_QUERY + page;

        httpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // display an error or some warning
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // fill data storage with movies from the response
            }
        });
    }

    public static void fetchMovieList(OrderCriteria orderCriteria) {
        fetchMovieList(orderCriteria, 1);
    }

    public static void fetchMovieList(int page) {
        fetchMovieList(OrderCriteria.POPULAR, page);
    }

    public static void fetchMovieList() {
        fetchMovieList(OrderCriteria.POPULAR, 1);
    }

    public static void fetchMovie(String movieId) {
        String url = API_URL + MOVIE + SLASH + movieId + AUTH_QUERY;

        httpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // fill movie details (pending solution)
            }
        });
    }

    public static void fetchActorList(int page) {
        String url = API_URL + ACTOR_LIST + AUTH_QUERY + AND + PAGE_QUERY + page;

        httpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // display an error or some warning
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // fill data storage with movies from the response
            }
        });
    }

    public static void fetchActorList() {
        fetchActorList(1);
    }

    public static void fetchActor(String actorId) {
        String url = API_URL + ACTOR + SLASH + actorId + AUTH_QUERY;

        httpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // fill movie details (pending solution)
            }
        });
    }
}
