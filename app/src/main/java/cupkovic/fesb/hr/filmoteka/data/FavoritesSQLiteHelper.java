package cupkovic.fesb.hr.filmoteka.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mskor on 30/01/2018.
 */

public class FavoritesSQLiteHelper extends SQLiteOpenHelper {
    private	static final int DATABASE_VERSION =	1;

    //	Create two tables when creating database
    private	static final String	MOVIE_FAVS_CREATE = "CREATE TABLE MovieFavorites"
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "	movieIdAPI INTEGER NOT NULL);";

    private static final String ACTOR_FAVS_CREATE =  "CREATE TABLE ActorFavorites"
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "	actorIdAPI INTEGER NOT NULL);";

    private static final String WATCHLIST_CREATE = "CREATE TABLE Watchlist"
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "	movieIdAPI INTEGER NOT NULL);";

    public FavoritesSQLiteHelper(Context context) {
        super(context, "filmotekadatabase.db",	null, DATABASE_VERSION);
    }

    @Override
    public void	onCreate(SQLiteDatabase database) {
        database.execSQL(MOVIE_FAVS_CREATE);
        database.execSQL(ACTOR_FAVS_CREATE);
        database.execSQL(WATCHLIST_CREATE);
    }

    @Override
    public void	onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(FavoritesSQLiteHelper.class.getName(),
                "Upgrading database	from version " + oldVersion + "	to	"
                        + newVersion + ",	which	will	destroy	all	old	data");

        db.execSQL("DROP TABLE IF EXISTS MovieFavorites, ActorFavorites");
        onCreate(db);
    }
}
