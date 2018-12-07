package pl.edu.pjatk.stefanczuk.shoppinglist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pjatk.stefanczuk.shoppinglist.R;
import pl.edu.pjatk.stefanczuk.shoppinglist.activity.EditProductActivity;
import pl.edu.pjatk.stefanczuk.shoppinglist.model.Product;

public class ProductListAdapter extends FirebaseRecyclerAdapter<Product, ProductListAdapter.ViewHolder> {

    private Context context;

    public ProductListAdapter(FirebaseRecyclerOptions<Product> options, Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull Product product) {
        viewHolder.setProduct(product);
        viewHolder.getPriceTextView().setText(String.format(context.getString(R.string.price_pattern),
                product.getPrice()));
        viewHolder.getNameTextView().setText(String.valueOf(product.getName()));
        viewHolder.getQuantityTextView().setText(String.valueOf(product.getQuantity()));
        if (product.isBought()) {
            viewHolder.getIsBoughtCheckbox().setChecked(true);
        } else {
            viewHolder.getIsBoughtCheckbox().setChecked(false);
        }
    }

    @Getter
    @Setter
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Product product;
        private TextView nameTextView;
        private TextView priceTextView;
        private TextView quantityTextView;
        private CheckBox isBoughtCheckbox;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViewComponents();
            setOnItemClickListener();
            setCheckboxOnCheckedChangeListener();
        }

        @Override
        public void onClick(View v) {
            goToEditProductActivity();
        }

        private void initViewComponents() {
            nameTextView = itemView.findViewById(R.id.productNameTextView);
            priceTextView = itemView.findViewById(R.id.productPriceTextView);
            quantityTextView = itemView.findViewById(R.id.productQuantityTextView);
            isBoughtCheckbox = itemView.findViewById(R.id.productBoughtCheckbox);
        }

        private void setOnItemClickListener() {
            itemView.setOnClickListener(this);
        }

        private void setCheckboxOnCheckedChangeListener() {
            isBoughtCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    product.setBought(!product.isBought());
                    String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("products")
                            .child(currentUserUid)
                            .child(product.getId())
                            .child("bought")
                            .setValue(product.isBought());
                }
            });
        }

        private void goToEditProductActivity() {
            Intent intent = new Intent(context, EditProductActivity.class);
            intent.putExtra("productId", product.getId());
            intent.putExtra("productName", product.getName());
            intent.putExtra("productQuantity", product.getQuantity());
            intent.putExtra("productPrice", product.getPrice());
            intent.putExtra("productIsBought", product.isBought());
            context.startActivity(intent);
        }
    }
}
