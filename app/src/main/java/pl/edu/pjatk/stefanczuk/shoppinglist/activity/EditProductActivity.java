package pl.edu.pjatk.stefanczuk.shoppinglist.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import pl.edu.pjatk.stefanczuk.shoppinglist.R;

public class EditProductActivity extends Activity {

    private EditText editedNameEditText;
    private EditText editedQuantityEditText;
    private EditText editedPriceEditText;
    private Switch editedIsBoughtSwitch;

    private String editedProductId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        initViewComponents();
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
        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("products")
                .child(currentUserUid)
                .child(editedProductId)
                .setValue(null);
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
        editedProductId = intent.getStringExtra("productId");
        if (editedProductId == null) {
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

    private boolean isAnyFieldEmpty() {
        return editedNameEditText.getText().toString().isEmpty()
                || editedQuantityEditText.getText().toString().isEmpty()
                || editedPriceEditText.getText().toString().isEmpty();
    }

    private void showFieldsWarningMessage() {
        Toast.makeText(this, R.string.fields_warning_message, Toast.LENGTH_SHORT).show();
    }

    private void updateProduct() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", editedProductId);
        map.put("name", editedNameEditText.getText().toString());
        map.put("quantity", editedQuantityEditText.getText().toString());
        map.put("price", editedPriceEditText.getText().toString());
        map.put("bought", editedIsBoughtSwitch.isChecked());
        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("products")
                .child(currentUserUid)
                .child(editedProductId)
                .setValue(map);
    }
}
