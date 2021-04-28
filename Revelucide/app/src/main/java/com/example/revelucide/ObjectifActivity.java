package com.example.revelucide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.revelucide.models.Objectif;
import com.example.revelucide.models.ObjectifAdapter;
import com.example.revelucide.models.Reve;
import com.example.revelucide.models.ReveAdapter;
import com.example.revelucide.models.bottomNavBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ObjectifActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private ListView listView;
    private ObjectifAdapter objectifAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectif);

        this.loadData();

        listView = findViewById(R.id.listView);
        Button addButton = findViewById(R.id.AddObjectifButton);
        EditText GetValue = findViewById(R.id.AddObjectifText);

        objectifAdapter = new ObjectifAdapter(this, R.layout.listview_objectif_row, Objectif.getListObjectif());

        listView.setAdapter(objectifAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavBar.createBottomNavBar(3, bottomNavigationView, ObjectifActivity.this);

        FloatingActionButton button = findViewById(R.id.boutonAjout);

        button.setOnClickListener(v -> {
            Intent intent0 = new Intent(ObjectifActivity.this, AjoutActivity.class);
            startActivity(intent0);
        });

        addButton.setOnClickListener(v -> {
            Objectif.getListObjectif().add(new Objectif(GetValue.getText().toString()));
            GetValue.setText("");
            objectifAdapter.notifyDataSetChanged();
            this.saveData();
        });

        // On ouvre un intent dans lequel on trouvera toute les informations de l'objetif cliquer
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent0 = new Intent(ObjectifActivity.this, AjoutActivity.class);
            intent0.getIntExtra("position", position);
            startActivity(intent0);
        });


    }

    private void loadData() {
        sp = getSharedPreferences("ReveLucidePref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("listObjectif", null);
        Type type = new TypeToken<ArrayList<Objectif>>() {}.getType();
        Objectif.setListObjectif( gson.fromJson(json, type));
        if (Objectif.getListObjectif() == null) {
            Objectif.setListObjectif(new ArrayList<>());
        }
    }

    private void saveData() {
        sp = getSharedPreferences("ReveLucidePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Objectif.getListObjectif());
        editor.putString("listObjectif", json);
        editor.apply();
    }


}
