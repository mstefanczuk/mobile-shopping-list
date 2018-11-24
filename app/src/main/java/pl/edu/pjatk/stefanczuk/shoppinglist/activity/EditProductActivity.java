package pl.edu.pjatk.stefanczuk.shoppinglist.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import pl.edu.pjatk.stefanczuk.shoppinglist.db.DBManager;
import pl.edu.pjatk.stefanczuk.shoppinglist.R;

public class EditProductActivity extends Activity {

    private EditText editedNameEditText;
    private EditText editedQuantityEditText;
    private EditText editedPriceEditText;
    private Switch editedIsBoughtSwitch;

    private DBManager dbManager;

    private long editedProductId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        initViewComponents();
        initDbManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fillViewComponentsWithData();
    }

    public void handleSaveButtonClicked(View view) {
        if (isAnyFieldEmpty()) {
            showFieldsWarningMessage();
            return;
        }
        updateProduct();
        finish();
    }

    public void deleteProduct(View view) {
        dbManager.delete(editedProductId);
        finish();
    }

    private void initViewComponents() {
        editedNameEditText = findViewById(R.id.editedNameEditText);
        editedQuantityEditText = findViewById(R.id.editedQuantityEditText);
        editedPriceEditText = findViewById(R.id.editedPriceEditText);
        editedIsBoughtSwitch = findViewById(R.id.editedIsBoughtSwitch);
    }

    private void fillViewComponentsWithData() {
        Intent intent = getIntent();
        editedProductId = intent.getLongExtra("productId", -1);
        if (editedProductId == -1) {
            showErrorMessageAndFinishActivity();
        }
        editedNameEditText.setText(intent.getStringExtra("productName"));
        editedQuantityEditText.setText(String.valueOf(intent.getIntExtra("productQuantity", 0)));
        editedPriceEditText.setText(String.valueOf(intent.getDoubleExtra("productPrice", 0)));
        editedIsBoughtSwitch.setChecked(intent.getBooleanExtra("productIsBought", false));
    }

    private void showErrorMessageAndFinishActivity() {
        Toast.makeText(this, R.string.loading_product_error_message, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void initDbManager() {
        dbManager = new DBManager(this);
        dbManager.open();
    }

    private boolean isAnyFieldEmpty() {
        return editedNameEditText.getText().toString().isEmpty()
                || editedQuantityEditText.getText().toString().isEmpty()
                || editedPriceEditText.getText().toString().isEmpty();
    }

    private void showFieldsWarningMessage() {
        Toast.makeText(this, R.string.fields_warning_message, Toast.LENGTH_SHORT).show();
    }

    private void updateProduct() {
        dbManager.update(editedProductId, editedNameEditText.getText().toString(),
                Integer.valueOf(editedQuantityEditText.getText().toString()),
                Double.valueOf(editedPriceEditText.getText().toString()),
                editedIsBoughtSwitch.isChecked());
    }
}
