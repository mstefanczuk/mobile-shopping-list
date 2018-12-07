package pl.edu.pjatk.stefanczuk.shoppinglist.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

import pl.edu.pjatk.stefanczuk.shoppinglist.R;
import pl.edu.pjatk.stefanczuk.shoppinglist.adapter.ProductListAdapter;
import pl.edu.pjatk.stefanczuk.shoppinglist.model.Product;

public class ProductListActivity extends Activity {

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter productListAdapter;

    public ProductListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        initViewComponents();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        productListAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        productListAdapter.stopListening();
    }

    public void goToAddProductActivity(View view) {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivity(intent);
    }

    private void initViewComponents() {
        recyclerView = findViewById(R.id.productsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        productListAdapter = new ProductListAdapter(getFirebaseRecyclerOptions(), this);
        recyclerView.setAdapter(productListAdapter);
    }

    private FirebaseRecyclerOptions<Product> getFirebaseRecyclerOptions() {
        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("products")
                .child(currentUserUid);

        return new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(query, new SnapshotParser<Product>() {
                    @NonNull
                    @Override
                    public Product parseSnapshot(@NonNull DataSnapshot snapshot) {
                        Product product = new Product();
                        product.setId(snapshot.child("id").getValue().toString());
                        product.setName(Objects.requireNonNull(snapshot.child("name").getValue()).toString());
                        product.setQuantity(Integer.valueOf(snapshot.child("quantity").getValue().toString()));
                        product.setPrice(Double.valueOf(snapshot.child("price").getValue().toString()));
                        product.setBought(Boolean.valueOf(snapshot.child("bought").getValue().toString()));

                        return product;
                    }
                })
                .build();
    }
}
