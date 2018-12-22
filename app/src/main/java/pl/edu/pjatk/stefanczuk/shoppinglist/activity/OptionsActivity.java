package pl.edu.pjatk.stefanczuk.shoppinglist.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

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
        setShops();
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

    private void setShops() {
        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("shops")
                .child(currentUserUid)
                .push();
        Map<String, Object> map = new HashMap<>();
        map.put("id", databaseReference.getKey());
        map.put("name", "Empik");
        map.put("helloMessage", "Witamy w Empik!");
        map.put("goodbyeMessage", "Wróć jeszcze do Empik!");
        map.put("promoMessage", "Promocja dnia: wszystkie książki za 0zł");
        map.put("radius", 200);
        map.put("latitude", 52.230220);
        map.put("longitude", 21.002887);
        map.put("favourite", false);
        databaseReference.setValue(map);

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("shops")
                .child(currentUserUid)
                .push();
        map = new HashMap<>();
        map.put("id", databaseReference.getKey());
        map.put("name", "H&M");
        map.put("helloMessage", "Witamy w H&M!");
        map.put("goodbyeMessage", "Wróć jeszcze do H&M!");
        map.put("promoMessage", "Promocja dnia: 10 koszulek w cenie 1");
        map.put("radius", 200);
        map.put("latitude", 52.213477);
        map.put("longitude", 21.021086);
        map.put("favourite", false);
        databaseReference.setValue(map);

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("shops")
                .child(currentUserUid)
                .push();
        map = new HashMap<>();
        map.put("id", databaseReference.getKey());
        map.put("name", "Vistula");
        map.put("helloMessage", "Witamy w Vistula!");
        map.put("goodbyeMessage", "Wróć jeszcze do Vistula!");
        map.put("promoMessage", "Promocja dnia: Kup krawat - garnitur odbierz za darmo");
        map.put("radius", 200);
        map.put("latitude", 52.212774);
        map.put("longitude", 20.954553);
        map.put("favourite", false);
        databaseReference.setValue(map);
    }
}
