package pl.edu.pjatk.stefanczuk.shoppinglist.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import pl.edu.pjatk.stefanczuk.shoppinglist.db.DBManager;
import pl.edu.pjatk.stefanczuk.shoppinglist.adapters.ProductListAdapter;
import pl.edu.pjatk.stefanczuk.shoppinglist.R;

public class ProductListActivity extends Activity {

    private RecyclerView recyclerView;
    private DBManager dbManager;

    public ProductListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        initViewComponents();
        initDbManager();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Cursor cursor = dbManager.fetchAll();
        ProductListAdapter productListAdapter = new ProductListAdapter(cursor, this);
        recyclerView.setAdapter(productListAdapter);
    }

    public void goToAddProductActivity(View view) {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivity(intent);
    }

    private void initViewComponents() {
        recyclerView = findViewById(R.id.productsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initDbManager() {
        dbManager = new DBManager(this);
        dbManager.open();
    }
}
