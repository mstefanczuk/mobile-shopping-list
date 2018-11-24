package pl.edu.pjatk.stefanczuk.shoppinglist.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pjatk.stefanczuk.shoppinglist.R;
import pl.edu.pjatk.stefanczuk.shoppinglist.activity.EditProductActivity;
import pl.edu.pjatk.stefanczuk.shoppinglist.db.DBManager;
import pl.edu.pjatk.stefanczuk.shoppinglist.db.DBHelper;
import pl.edu.pjatk.stefanczuk.shoppinglist.model.Product;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private List<Product> productList;
    private Cursor cursor;
    private Context context;
    private DBManager dbManager;

    public ProductListAdapter(Cursor cursor, Context context) {
        this.cursor = cursor;
        this.context = context;
        dbManager = new DBManager(context);
        dbManager.open();
        retrieveDataFromCursor();
    }

    @NonNull
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Product product = productList.get(i);
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

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private void retrieveDataFromCursor() {
        productList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            addProductToList(cursor);
        }
    }

    private void addProductToList(Cursor cursor) {
        Product product = new Product();
        product.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.ID)));
        product.setName(cursor.getString(cursor.getColumnIndex(DBHelper.NAME)));
        product.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.QUANTITY)));
        product.setPrice(cursor.getDouble(cursor.getColumnIndex(DBHelper.PRICE)));
        product.setBought(cursor.getInt(cursor.getColumnIndex(DBHelper.IS_BOUGHT)) == 1);
        productList.add(product);
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
                    dbManager.update(product.getId(), product.getName(), product.getQuantity(),
                            product.getPrice(), product.isBought());
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
