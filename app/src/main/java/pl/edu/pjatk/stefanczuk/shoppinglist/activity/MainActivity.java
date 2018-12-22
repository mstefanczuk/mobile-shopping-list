package pl.edu.pjatk.stefanczuk.shoppinglist.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import pl.edu.pjatk.stefanczuk.shoppinglist.R;
import pl.edu.pjatk.stefanczuk.shoppinglist.constant.Color;
import pl.edu.pjatk.stefanczuk.shoppinglist.constant.Size;
import pl.edu.pjatk.stefanczuk.shoppinglist.db.DBHelper;

public class MainActivity extends Activity {

    private TextView titleTextView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        titleTextView = findViewById(R.id.titleTextView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitleTextViewAttributes();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void goToOptionsActivity(View view) {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    public void goToMapsActivity(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void goToProductListActivity(View view) {
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }

    private void setTitleTextViewAttributes() {
        String titleSize = sharedPreferences.getString("titleSize",
                getResources().getStringArray(R.array.title_sizes_array)[0]);
        String titleColor = sharedPreferences.getString("titleColor",
                getResources().getStringArray(R.array.title_colors_array)[0]);

        if (titleSize != null) {
            setTitleTextViewSize(titleSize);
        }
        if (titleColor != null) {
            setTitleTextViewColor(titleColor);
        }
    }

    private void setTitleTextViewSize(String size) {
        ViewGroup.LayoutParams params = titleTextView.getLayoutParams();
        if (size.equals(Size.SMALL.getValue())) {
            params.height = getResources().getDimensionPixelSize(R.dimen.title_height_small);
        } else if (size.equals(Size.BIG.getValue())) {
            params.height = getResources().getDimensionPixelSize(R.dimen.title_height_big);
        }

        titleTextView.setLayoutParams(params);
    }

    private void setTitleTextViewColor(String color) {
        int newColor = getResources().getColor(R.color.colorAccent);
        if (color.equals(Color.BLACK.getValue())) {
            newColor = getResources().getColor(R.color.colorBlack);
        }
        titleTextView.setTextColor(newColor);
    }
}
