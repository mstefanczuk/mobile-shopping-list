package pl.edu.pjatk.stefanczuk.shoppinglist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pl.edu.pjatk.stefanczuk.shoppinglist.R;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String PRODUCTS_TABLE_NAME = "PRODUCTS";

    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String QUANTITY = "QUANTITY";
    public static final String PRICE = "PRICE";
    public static final String IS_BOUGHT = "IS_BOUGHT";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + PRODUCTS_TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " TEXT, " +
                    QUANTITY + " INTEGER, " +
                    PRICE + " REAL, " +
                    IS_BOUGHT + " INTEGER);";

    DBHelper(Context context) {
        super(context, context.getString(R.string.db_name), null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS_TABLE_NAME);
        onCreate(db);
    }
}
