package pl.edu.pjatk.stefanczuk.shoppinglist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper databaseHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context context) {
        this.context = context;
    }

    public void open() {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    public void insert(String name, int quantity, double price, boolean isBought) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.QUANTITY, quantity);
        contentValues.put(DatabaseHelper.PRICE, price);
        contentValues.put(DatabaseHelper.IS_BOUGHT, isBought);
        database.insert(DatabaseHelper.PRODUCTS_TABLE_NAME, null, contentValues);
    }

    public Cursor fetchAll() {
        String[] columns = new String[]{DatabaseHelper.ID, DatabaseHelper.NAME,
                DatabaseHelper.QUANTITY, DatabaseHelper.PRICE, DatabaseHelper.IS_BOUGHT};
        Cursor cursor = database.query(DatabaseHelper.PRODUCTS_TABLE_NAME, columns, null,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void update(int id, String name, int quantity, double price, boolean isBought) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.QUANTITY, quantity);
        contentValues.put(DatabaseHelper.PRICE, price);
        contentValues.put(DatabaseHelper.IS_BOUGHT, isBought);
        database.update(DatabaseHelper.PRODUCTS_TABLE_NAME, contentValues,
                DatabaseHelper.ID + " = " + id, null);
    }

    public void delete(int id) {
        database.delete(DatabaseHelper.PRODUCTS_TABLE_NAME,
                DatabaseHelper.ID + " = " + id, null);
    }
}
