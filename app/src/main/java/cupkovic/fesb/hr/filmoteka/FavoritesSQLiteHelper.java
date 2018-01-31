package cupkovic.fesb.hr.filmoteka;

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
    private	static final String	DATABASE_CREATE	= "CREATE TABLE	MovieFavorites"
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "	movieIdAPI INTEGER NOT NULL);"
            + "CREATE TABLE ActorFavorites"
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "	actorIdAPI INTEGER NOT NULL);";


    public FavoritesSQLiteHelper(Context context) {
        super(context, "filmotekadatabase.db",	null, DATABASE_VERSION);
    }

    @Override
    public void	onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
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
