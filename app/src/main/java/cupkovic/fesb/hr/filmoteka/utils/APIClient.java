package cupkovic.fesb.hr.filmoteka.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cupkovic.fesb.hr.filmoteka.data.ActorsProvider;
import cupkovic.fesb.hr.filmoteka.data.DataStorage;
import cupkovic.fesb.hr.filmoteka.data.MoviesProvider;
import cupkovic.fesb.hr.filmoteka.data.models.Movie;
import cupkovic.fesb.hr.filmoteka.data.models.Person;
import cupkovic.fesb.hr.filmoteka.interfaces.IApiSubscriber;
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
    private static Gson gson = new Gson();

    private IApiSubscriber responseSubscriber;

    // Private constructor to prevent instantiation
    public APIClient(IApiSubscriber responseSubscriber) {
        this.responseSubscriber = responseSubscriber;
    }

    /**
     * Response subscriber getter
     * @return response subscriber - a class which is currently subscribed to this class
     */
    public IApiSubscriber getResponseSubscriber() {
        return responseSubscriber;
    }

    /**
     * Sets the response subscriber class - a class which uses this class to get any data from the API.
     * @param responseSubscriber class which uses APIClient to get data from the API
     */
    public void setResponseSubscriber(IApiSubscriber responseSubscriber) {
        this.responseSubscriber = responseSubscriber;
    }

    /**
     * Gets the list of movies from the API.
     * @param orderCriteria defines whether to get movies sorted by popularity, date or rate
     * @param page page of the movies list to download
     * @param storage MovieProvider object to which movie list should be stored
     */
    public void fetchMovieList(final OrderCriteria orderCriteria, int page, final MoviesProvider storage) {
        String url = API_URL + MOVIE + orderCriteria.toString() + AUTH_QUERY + AND + PAGE_QUERY + page;

        httpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                JsonElement results = getJSONElement("results", responseString);
                ArrayList<Movie> movies = gson.fromJson(results, new TypeToken<ArrayList<Movie>>(){}.getType());
                storage.setData(movies);
                // Response handler method callback. For now, response is null, since everything needed is stored in DataStorage
                getResponseSubscriber().handleAPISuccessResponse(null);
            }
        });
    }

    /**
     * Gets the list of movies from the API. Downloads the first page of the results
     * @param orderCriteria defines whether to get movies sorted by popularity, date or rate
     */
    public void fetchMovieList(OrderCriteria orderCriteria, MoviesProvider storage) {
        fetchMovieList(orderCriteria, 1, storage);
    }

    /**
     * Gets the list of movies from the API. Uses POPULAR as a default order criteria
     * @param page page of the movies list to download
     */
    public void fetchMovieList(int page, MoviesProvider storage) {
        fetchMovieList(OrderCriteria.POPULAR, page, storage);
    }

    /**
     * Gets the list of movies from the API. Downloads the first page of the results.
     * Uses POPULAR as a default order criteria
     */
    public void fetchMovieList(MoviesProvider storage) {
        fetchMovieList(OrderCriteria.POPULAR, 1, storage);
    }

    /**
     * Gets the movie from API based on it's ID
     * @param movieId identification of the movie to download
     */
    public void fetchMovie(String movieId) {
        String url = API_URL + MOVIE + SLASH + movieId + AUTH_QUERY;

        httpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Movie movie = gson.fromJson(responseString, Movie.class);
                // Return movie to the caller class via callback method
                getResponseSubscriber().handleAPISuccessResponse(movie);
            }
        });
    }

    /**
     * Gets the list of actors. By default, it downloads actors sorted by popularity
     * @param page page of the actors list to download
     */
    public void fetchActorList(int page, final ActorsProvider storage) {
        String url = API_URL + ACTOR_LIST + AUTH_QUERY + AND + PAGE_QUERY + page;

        httpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // display an error or some warning
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // fill data storage with movies from the response
                JsonElement results = getJSONElement("results", responseString);
                ArrayList<Person> actors = gson.fromJson(results, new TypeToken<ArrayList<Person>>(){}.getType());
                storage.setData(actors);
                getResponseSubscriber().handleAPISuccessResponse(null);
            }
        });
    }

    /**
     * Gets the first page of the actors list. By default, it downloads actors sorted by popularity.
     */
    public void fetchActorList(ActorsProvider storage) {
        fetchActorList(1, storage);
    }

    public void fetchActor(String actorId) {
        String url = API_URL + ACTOR + SLASH + actorId + AUTH_QUERY;

        httpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // fill movie details (pending solution)
                Person actor = gson.fromJson(responseString, Person.class);
                getResponseSubscriber().handleAPISuccessResponse(actor);
            }
        });
    }

    /**
     * Gets the specific element from the JSON object.
     * @param elementName requested element (object or array)
     * @param jsonString JSON object containing the requested element
     * @return requested object as a JsonElement
     */
    private JsonElement getJSONElement(String elementName, String jsonString) {
        JsonElement requestedElement = new JsonObject();
        JsonParser parser = new JsonParser();

        JsonElement rootElement = parser.parse(jsonString);

        if (rootElement.isJsonObject()){
            requestedElement = rootElement.getAsJsonObject().get(elementName);
        }

        return requestedElement;
    }
}
