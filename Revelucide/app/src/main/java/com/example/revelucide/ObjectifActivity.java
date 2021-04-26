package com.example.revelucide;

import android.content.Intent;
import android.os.Bundle;
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

public class ObjectifActivity extends AppCompatActivity {

    private ListView listView;
    private ObjectifAdapter objectifAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectif);

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
        });
    }
}
