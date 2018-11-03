package pl.edu.pjatk.stefanczuk.shoppinglist.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pl.edu.pjatk.stefanczuk.shoppinglist.db.DBManager;
import pl.edu.pjatk.stefanczuk.shoppinglist.R;

public class AddProductActivity extends Activity {

    private DBManager dbManager;
    private EditText addedNameEditText;
    private EditText addedQuantityEditText;
    private EditText addedPriceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initViewComponents();
        initDbManager();
    }

    public void handleSaveButtonClicked(View view) {
        if (isAnyFieldEmpty()) {
            showFieldsWarning();
            return;
        }
        createNewProduct();
        returnToProductListActivity();
    }

    private void initDbManager() {
        dbManager = new DBManager(this);
        dbManager.open();
    }

    private void initViewComponents() {
        addedNameEditText = findViewById(R.id.addedNameEditText);
        addedQuantityEditText = findViewById(R.id.addedQuantityEditText);
        addedPriceEditText = findViewById(R.id.addedPriceEditText);
    }

    private boolean isAnyFieldEmpty() {
        return addedNameEditText.getText().toString().isEmpty()
                || addedQuantityEditText.getText().toString().isEmpty()
                || addedPriceEditText.getText().toString().isEmpty();
    }

    private void showFieldsWarning() {
        Toast.makeText(this, R.string.fields_warning_message, Toast.LENGTH_SHORT).show();
    }

    private void createNewProduct() {
        String name = addedNameEditText.getText().toString();
        int quantity = Integer.parseInt(addedQuantityEditText.getText().toString());
        double price = Double.parseDouble(addedPriceEditText.getText().toString());
        dbManager.insert(name, quantity, price, false);
    }

    private void returnToProductListActivity() {
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }
}
