package cupkovic.fesb.hr.filmoteka.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;


import java.util.ArrayList;

import cupkovic.fesb.hr.filmoteka.data.CastProvider;
import cupkovic.fesb.hr.filmoteka.data.CrewProvider;
import cupkovic.fesb.hr.filmoteka.data.PersonsProvider;
import cupkovic.fesb.hr.filmoteka.data.CreditsProvider;
import cupkovic.fesb.hr.filmoteka.data.MoviesProvider;
import cupkovic.fesb.hr.filmoteka.data.models.CastMember;
import cupkovic.fesb.hr.filmoteka.data.models.CrewMember;
import cupkovic.fesb.hr.filmoteka.data.models.Movie;
import cupkovic.fesb.hr.filmoteka.data.models.MovieCredit;
import cupkovic.fesb.hr.filmoteka.data.models.Person;
import cupkovic.fesb.hr.filmoteka.interfaces.IApiSubscriber;
import cz.msebera.android.httpclient.Header;

/**
 * This class provides an access to The Movie Database API. It contains methods which use HTTP
 * requests to get JSON encoded data from the API.
 * @see <a href="https://developers.themoviedb.org/3">The Movie Database API</a>
 */
public class APIClient {
    public static final String API_URL = "https://api.themoviedb.org/3";
    public static final String API_KEY = "f0e80754f643ca84a2b11614f3b5eadd";
    public static final String KEY_QUERY = "?api_key=";
    public static final String PAGE_QUERY = "page=";
    public static final String QUERY_QUERY = "query=";
    public static final String SLASH = "/";
    public static final String AND = "&";

    public static final String AUTH_QUERY = KEY_QUERY + API_KEY;

    // API request paths
    public static final String MOVIE = "/movie";
    public static final String PERSON = "/person";
    public static final String ACTOR_LIST = "/person/popular";
    public static final String CREDITS = "/credits";
    public static final String SEARCH = "/search";
    public static final String RATING = "/rating";

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
     * @param orderCriteria Defines whether to get movies sorted by popularity, date or rate
     * @param page Page of the movies list to download
     * @param storage A {@link MoviesProvider} object to which movie list should be stored
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
     * @param orderCriteria Defines whether to get movies sorted by popularity, date or rate
     * @param storage A {@link MoviesProvider} object to which movie list should be stored
     */
    public void fetchMovieList(OrderCriteria orderCriteria, MoviesProvider storage) {
        fetchMovieList(orderCriteria, 1, storage);
    }

    /**
     * Gets the list of movies from the API. Uses POPULAR as a default order criteria
     * @param page Page of the movies list to download
     * @param storage A {@link MoviesProvider} object to which movie list should be stored
     */
    public void fetchMovieList(int page, MoviesProvider storage) {
        fetchMovieList(OrderCriteria.POPULAR, page, storage);
    }

    /**
     * Gets the list of movies from the API. Downloads the first page of the results.
     * Uses POPULAR as a default order criteria
     * @param storage A {@link MoviesProvider} object to which movie list should be stored
     */
    public void fetchMovieList(MoviesProvider storage) {
        fetchMovieList(OrderCriteria.POPULAR, 1, storage);
    }

    /**
     * Gets the movie from API based on it's ID and returns the result to the caller method
     * @param movieId Identification of the movie to download
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
     * @param page Page of the actors list to download
     * @param storage An {@link PersonsProvider} object to which the actors list should be stored
     */
    public void fetchActorList(int page, final PersonsProvider storage) {
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
     * @param storage An {@link PersonsProvider} object to which the actors list should be stored
     */
    public void fetchActorList(PersonsProvider storage) {
        fetchActorList(1, storage);
    }

    /**
     * Gets the actor from API based on their ID and returns the result to the caller method
     * @param actorId Identification of the actor to download
     */
    public void fetchActor(String actorId) {
        String url = API_URL + PERSON + SLASH + actorId + AUTH_QUERY;

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
     * Gets movie credits for a certain actor.
     * @param actorId ID of an actor for which credits are being requested
     * @param storage A {@link CreditsProvider} object to store movie credits to
     */
    public void fetchMovieCredits(String actorId, final CreditsProvider storage) {
        String url = API_URL + PERSON + SLASH + actorId + CREDITS + AUTH_QUERY;

        httpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                JsonElement results = getJSONElement("cast", responseString);
                ArrayList<MovieCredit> credits = gson.fromJson(results, new TypeToken<ArrayList<MovieCredit>>(){}.getType());
                storage.setData(credits);
                getResponseSubscriber().handleAPISuccessResponse(null);
            }
        });

    }

    /**
     * Gets the lists of crew and cast members from the API
     * @param movieId ID of a movie for which personnel is being requested
     * @param castStorage A {@link CastProvider} object to which cast members will be stored
     * @param crewStorage A {@link CrewProvider} object to which crew members will be stored
     */
    public void fetchMovieCastAndCrew(String movieId, final CastProvider castStorage, final CrewProvider crewStorage) {
        String url = API_URL + MOVIE + SLASH + movieId + CREDITS + AUTH_QUERY;

        httpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                JsonElement castResult = getJSONElement("cast", responseString);
                JsonElement crewResult = getJSONElement("crew", responseString);
                ArrayList<CastMember> castMembers = gson.fromJson(castResult, new TypeToken<ArrayList<CastMember>>(){}.getType());
                ArrayList<CrewMember> crewMembers = gson.fromJson(crewResult, new TypeToken<ArrayList<CrewMember>>(){}.getType());
                castStorage.setData(castMembers);
                crewStorage.setData(crewMembers);
                getResponseSubscriber().handleAPISuccessResponse(null);
            }
        });
    }

    /**
     * Gets the list of movies from the API based on a search query.
     * @param query Search query
     * @param page Page of the query results list
     * @param storage A {@link MoviesProvider} object to which movies from results list should be stored
     */
    public void searchMovies(String query, int page, final MoviesProvider storage) {
        String url = API_URL + SEARCH + MOVIE + AUTH_QUERY + AND + QUERY_QUERY + query
                + AND + PAGE_QUERY + String.valueOf(page);

        httpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                JsonElement results = getJSONElement("results", responseString);
                ArrayList<Movie> movies = gson.fromJson(results, new TypeToken<ArrayList<Movie>>(){}.getType());
                storage.setData(movies);
                getResponseSubscriber().handleAPISuccessResponse(null);
            }
        });
    }

    /**
     * Gets the list of people from the API based on a search query.
     * @param query Search query
     * @param page Page of the query results list
     * @param storage An {@link PersonsProvider} object to which actors from results list should be stored
     */
    public void searchActors(String query, int page, final PersonsProvider storage) {
        String url = API_URL + SEARCH + PERSON + AUTH_QUERY + AND + QUERY_QUERY + query
                + AND + PAGE_QUERY + String.valueOf(page);

        httpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                JsonElement results = getJSONElement("results", responseString);
                ArrayList<Person> actors = gson.fromJson(results, new TypeToken<ArrayList<Person>>(){}.getType());
                storage.setData(actors);
                getResponseSubscriber().handleAPISuccessResponse(null);
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
