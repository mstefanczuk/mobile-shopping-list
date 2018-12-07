package pl.edu.pjatk.stefanczuk.shoppinglist.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import pl.edu.pjatk.stefanczuk.shoppinglist.R;

public class AddProductActivity extends Activity {

    private EditText addedNameEditText;
    private EditText addedQuantityEditText;
    private EditText addedPriceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initViewComponents();
    }

    public void handleSaveButtonClicked(View view) {
        if (isAnyFieldEmpty()) {
            showFieldsWarning();
            return;
        }
        createNewProduct();
        returnToProductListActivity();
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
        String quantity = addedQuantityEditText.getText().toString();
        String price = addedPriceEditText.getText().toString();
        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("products")
                .child(currentUserUid)
                .push();
        Map<String, Object> map = new HashMap<>();
        map.put("id", databaseReference.getKey());
        map.put("name", name);
        map.put("quantity", quantity);
        map.put("price", price);
        map.put("bought", false);

        databaseReference.setValue(map);
    }

    private void returnToProductListActivity() {
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }
}
