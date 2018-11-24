package pl.edu.pjatk.stefanczuk.shoppinglist.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import pl.edu.pjatk.stefanczuk.shoppinglist.R;
import pl.edu.pjatk.stefanczuk.shoppinglist.db.DBHelper;
import pl.edu.pjatk.stefanczuk.shoppinglist.db.DBManager;

public class NotificationHandlerActivity extends Activity {

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_handler);
        initDbManager();
    }

    public void goToProductListActivity(View view) {
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }

    public void goToEditProductActivity(View view) {
        Cursor cursor = getCursorWithProduct();
        if (cursor.getCount() < 1) {
            Toast.makeText(this, R.string.nonexistent_product_message, Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(buildIntent(cursor));
    }

    private Cursor getCursorWithProduct() {
        return dbManager.fetchById(getIntent().getLongExtra("productId", -1));
    }

    private Intent buildIntent(Cursor cursor) {
        Intent intent = new Intent(this, EditProductActivity.class);
        intent.putExtra("productId", cursor.getLong(cursor.getColumnIndex(DBHelper.ID)));
        intent.putExtra("productName", cursor.getString(cursor.getColumnIndex(DBHelper.NAME)));
        intent.putExtra("productQuantity", cursor.getInt(cursor.getColumnIndex(DBHelper.QUANTITY)));
        intent.putExtra("productPrice", cursor.getDouble(cursor.getColumnIndex(DBHelper.PRICE)));
        intent.putExtra("productIsBought", cursor.getInt(cursor.getColumnIndex(DBHelper.IS_BOUGHT)) == 1);
        return intent;
    }

    private void initDbManager() {
        dbManager = new DBManager(this);
        dbManager.open();
    }
}
