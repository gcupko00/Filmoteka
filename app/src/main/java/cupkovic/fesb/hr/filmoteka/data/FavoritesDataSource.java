package cupkovic.fesb.hr.filmoteka.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cupkovic.fesb.hr.filmoteka.data.models.Movie;
import cupkovic.fesb.hr.filmoteka.data.models.Person;

/**
 * Created by mskor on 30/01/2018.
 */

public class FavoritesDataSource {
    private SQLiteDatabase database;
    private	FavoritesSQLiteHelper dbHelper;

    public FavoritesDataSource(Context context)	{
        dbHelper = new FavoritesSQLiteHelper(context);

        try {
            this.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void	open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void	close()	{
        database.close();
    }

    /**
     * Adds a movie to the movie favorites table in database
     * @param movieApiId API Id of the movie to be added - id returned from the API
     */
    public void addMovieFavoriteToDb(int movieApiId) {
        ContentValues values = new ContentValues();
        values.put("movieIdAPI", movieApiId);
        database.insert("MovieFavorites", null, values);
    }

    /**
     * Adds an actor to the actor favorites table in database
     * @param actorApiId API Id of the actor to be added - id returned from the API
     */
    public void addActorFavoriteToDb(int actorApiId) {
        ContentValues values = new ContentValues();
        values.put("actorIdAPI", actorApiId);
        database.insert("ActorFavorites", null, values);
    }

    /**
     * Gets a movie from the movie favorites table in database
     * @param id API Id of the movie
     * @return movie object with only API id set, other properties need to be fetched
     */
    public Movie getMovieByApiId(int id) {
        Movie movie = new Movie();

        Cursor cursor = database.rawQuery("SELECT * FROM MovieFavorites WHERE movieIdAPI = '" + String.valueOf(id) + "'", null);

        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            movie.setId(cursor.getInt(1));
        }

        return movie;
    }

    /**
     * Gets an actor from the actor favorites table in database
     * @param id API Id of the actor
     * @return actor object with only API id set, other properties need to be fetched
     */
    public Person getActorByApiId(int id) {
        Person actor = new Person();

        Cursor cursor = database.rawQuery("SELECT * FROM ActorFavorites WHERE actorIdAPI = '" + String.valueOf(id) + "'", null);

        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            actor.setId(cursor.getInt(1));
        }

        return actor;
    }

    /**
     * Gets all movie favorites from the database
     * @return list with movie objects, each object has only API id set, other props need to be fetched
     */
    public ArrayList<Movie> getAllMovieFavorites()	{
        ArrayList<Movie> movies = new ArrayList<Movie>();

        Cursor cursor =	database.rawQuery("SELECT *	FROM MovieFavorites", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            Movie currentMovie = new Movie();
            currentMovie.setId(cursor.getInt(1));
            movies.add(currentMovie);
            cursor.moveToNext();
        }

        cursor.close();
        return movies;
    }

    /**
     * Gets all actor favorites from the database
     * @return list with actor objects, each object has only API id set, other props need to be fetched
     */
    public ArrayList<Person> getAllActorFavorites()	{
        ArrayList<Person> actors = new ArrayList<Person>();

        Cursor cursor =	database.rawQuery("SELECT *	FROM ActorFavorites", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            Person currentActor = new Person();
            currentActor.setId(cursor.getInt(1));
            actors.add(currentActor);
            cursor.moveToNext();
        }

        cursor.close();
        return actors;
    }

    /**
     * Adds a movie to the movie watchlist table in database
     * @param movieApiId API Id of the movie to be added - id returned from the API
     */
    public void addMovieToWatchlist(int movieApiId) {
        ContentValues values = new ContentValues();
        values.put("movieIdAPI", movieApiId);
        database.insert("Watchlist", null, values);
    }

    /**
     * Gets a movie from the movie favorites table in database
     * @param id API Id of the movie
     * @return movie object with only API id set, other properties need to be fetched
     */
    public Movie getMovieFromWatchlistByApiId(int id) {
        Movie movie = new Movie();

        Cursor cursor = database.rawQuery("SELECT * FROM Watchlist WHERE movieIdAPI = '" + String.valueOf(id) + "'", null);

        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            movie.setId(cursor.getInt(1));
        }

        return movie;
    }

    /**
     * Gets all movies from watchlist
     * @return list with movie objects, each object has only API id set, other props need to be fetched
     */
    public ArrayList<Movie> getAllMoviesFromWatchlist()	{
        ArrayList<Movie> movies = new ArrayList<Movie>();

        Cursor cursor =	database.rawQuery("SELECT *	FROM Watchlist", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            Movie currentMovie = new Movie();
            currentMovie.setId(cursor.getInt(1));
            movies.add(currentMovie);
            cursor.moveToNext();
        }

        cursor.close();
        return movies;
    }

    /**
     * Saves guest session Id fro the API to the database, this Id is used for movie rating
     * This Id is retrieved only once per device and stored for later uses
     * @param guestSessionId session Id that needs to be retrieved from the API
     */
    public void saveGuestSessionId(String guestSessionId) {
        ContentValues values = new ContentValues();
        values.put("guestSessionId", guestSessionId);
        database.insert("GuestSession", null, values);
    }

    /**
     * Retrieves guest session Id stored in the database
     * @return session Id from the database
     */
    public String getGuestSessionId() {
        String guestSessionId = "";

        Cursor cursor = database.rawQuery("SELECT * FROM GuestSession", null);

        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            guestSessionId = cursor.getString(1);
        }

        return guestSessionId;
    }

    /**
     * Deletes a movie from the movie favorites table in database
     * @param movieApiId API Id of the movie, the one which was returned from API
     * @return boolean value indicating whether a certain movie was deleted, true if it was
     */
    public boolean deleteMovieFavoriteByApiId(int movieApiId) {
        return database.delete("MovieFavorites", "movieIdAPI = " + movieApiId, null) > 0;
    }

    /**
     * Deletes an actor from the actor favorites table in database
     * @param actorApiId API Id of the actor, the one which was returned from API
     * @return boolean value indicating whether a certain actor was deleted, true if it was
     */
    public boolean deleteActorFavoriteByApiId(int actorApiId) {
        return database.delete("ActorFavorites", "actorIdAPI = " + actorApiId, null) > 0;
    }

    /**
     * Deletes a movie from the movie watchlist table in database
     * @param movieApiId API Id of the movie, the one which was returned from API
     * @return boolean value indicating whether a certain movie was deleted, true if it was
     */
    public boolean deleteMovieFromWatchlistByApiId(int movieApiId) {
        return database.delete("Watchlist", "movieIdAPI = " + movieApiId, null) > 0;
    }
}
