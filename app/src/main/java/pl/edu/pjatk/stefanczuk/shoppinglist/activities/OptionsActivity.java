package pl.edu.pjatk.stefanczuk.shoppinglist.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import pl.edu.pjatk.stefanczuk.shoppinglist.R;

public class OptionsActivity extends AppCompatActivity {

    private Spinner titleSizeSpinner;
    private Spinner titleColorSpinner;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        initViewComponents();
    }

    public void handleSaveButtonClicked(View view) {
        saveOptions();
        finish();
    }

    private void initViewComponents() {
        titleSizeSpinner = findViewById(R.id.titleSizeSpinner);
        titleColorSpinner = findViewById(R.id.titleColorSpinner);
        ArrayAdapter<CharSequence> sizeSpinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.title_sizes_array,
                        android.R.layout.simple_spinner_item);
        sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> colorSpinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.title_colors_array,
                        android.R.layout.simple_spinner_item);
        colorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titleSizeSpinner.setAdapter(sizeSpinnerAdapter);
        titleColorSpinner.setAdapter(colorSpinnerAdapter);
        titleSizeSpinner.setSelection(sizeSpinnerAdapter
                .getPosition(sharedPreferences.getString("titleSize",
                        getResources().getStringArray(R.array.title_sizes_array)[0])));
        titleColorSpinner.setSelection(colorSpinnerAdapter
                .getPosition(sharedPreferences.getString("titleColor",
                        getResources().getStringArray(R.array.title_colors_array)[0])));
    }

    private void saveOptions() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("titleSize", titleSizeSpinner.getSelectedItem().toString());
        editor.putString("titleColor", titleColorSpinner.getSelectedItem().toString());
        editor.apply();
    }
}
