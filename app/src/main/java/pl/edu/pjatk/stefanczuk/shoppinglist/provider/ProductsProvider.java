package pl.edu.pjatk.stefanczuk.shoppinglist.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Objects;

import pl.edu.pjatk.stefanczuk.shoppinglist.db.DBHelper;
import pl.edu.pjatk.stefanczuk.shoppinglist.db.DBManager;

public class ProductsProvider extends ContentProvider {

    static final String PROVIDER_NAME = "pl.edu.pjatk.stefanczuk.shoppinglist.provider.Products";
    static final String URL = "content://" + PROVIDER_NAME + "/products";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final int PRODUCTS = 1;
    static final int PRODUCTS_ID = 2;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "products", PRODUCTS);
        uriMatcher.addURI(PROVIDER_NAME, "products/#", PRODUCTS_ID);
    }

    private DBManager dbManager;

    private static HashMap<String, String> ProductMap;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbManager = new DBManager(context);
        dbManager.open();
        return dbManager.isDatabaseAvailable();
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(DBHelper.PRODUCTS_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case PRODUCTS:
                sqLiteQueryBuilder.setProjectionMap(ProductMap);
                break;
            case PRODUCTS_ID:
                sqLiteQueryBuilder.appendWhere(DBHelper.ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = DBHelper.NAME;
        }

        Cursor cursor = sqLiteQueryBuilder.query(dbManager.getDatabase(), projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case PRODUCTS:
                return "vnd.android.cursor.dir/vnd.example.products";
            case PRODUCTS_ID:
                return "vnd.android.cursor.item/vnd.example.products";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long row = dbManager.getDatabase().insert(DBHelper.PRODUCTS_TABLE_NAME, "", values);

        if (row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
