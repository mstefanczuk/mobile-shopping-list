package pl.edu.pjatk.stefanczuk.shoppinglist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import lombok.Getter;

public class DBManager {

    private DBHelper dbHelper;
    private Context context;
    @Getter
    private SQLiteDatabase database;

    public DBManager(Context context) {
        this.context = context;
    }

    public void open() {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean isDatabaseAvailable() {
         return database != null;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, int quantity, double price, boolean isBought) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.NAME, name);
        contentValues.put(DBHelper.QUANTITY, quantity);
        contentValues.put(DBHelper.PRICE, price);
        contentValues.put(DBHelper.IS_BOUGHT, isBought);
        database.insert(DBHelper.PRODUCTS_TABLE_NAME, null, contentValues);
    }

    public Cursor fetchAll() {
        String[] columns = new String[]{DBHelper.ID, DBHelper.NAME,
                DBHelper.QUANTITY, DBHelper.PRICE, DBHelper.IS_BOUGHT};
        Cursor cursor = database.query(DBHelper.PRODUCTS_TABLE_NAME, columns, null,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void update(int id, String name, int quantity, double price, boolean isBought) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.NAME, name);
        contentValues.put(DBHelper.QUANTITY, quantity);
        contentValues.put(DBHelper.PRICE, price);
        contentValues.put(DBHelper.IS_BOUGHT, isBought);
        database.update(DBHelper.PRODUCTS_TABLE_NAME, contentValues,
                DBHelper.ID + " = " + id, null);
    }

    public void delete(int id) {
        database.delete(DBHelper.PRODUCTS_TABLE_NAME,
                DBHelper.ID + " = " + id, null);
    }
}
