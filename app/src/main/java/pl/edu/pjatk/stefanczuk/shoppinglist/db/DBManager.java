package pl.edu.pjatk.stefanczuk.shoppinglist.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
        long productId = database.insert(DBHelper.PRODUCTS_TABLE_NAME, null, contentValues);
        sendBroadcastWithIntent(productId, name, quantity, price);
    }

    public Cursor fetchById(long id) {
        String[] columns = new String[]{DBHelper.ID, DBHelper.NAME,
                DBHelper.QUANTITY, DBHelper.PRICE, DBHelper.IS_BOUGHT};
        Cursor cursor = database.query(DBHelper.PRODUCTS_TABLE_NAME, columns, DBHelper.ID + "=" + id,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
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

    public void update(long id, String name, int quantity, double price, boolean isBought) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.NAME, name);
        contentValues.put(DBHelper.QUANTITY, quantity);
        contentValues.put(DBHelper.PRICE, price);
        contentValues.put(DBHelper.IS_BOUGHT, isBought);
        database.update(DBHelper.PRODUCTS_TABLE_NAME, contentValues,
                DBHelper.ID + " = " + id, null);
    }

    public void delete(long id) {
        database.delete(DBHelper.PRODUCTS_TABLE_NAME,
                DBHelper.ID + " = " + id, null);
    }

    private void sendBroadcastWithIntent(long id, String name, int quantity, double price) {
        Intent intent = new Intent();
        intent.putExtra("productId", id);
        intent.putExtra("productName", name);
        intent.putExtra("productQuantity", quantity);
        intent.putExtra("productPrice", price);
        intent.putExtra("productIsBought", false);
        intent.setAction("shoppinglist.notification");
        context.sendBroadcast(intent);
    }
}
