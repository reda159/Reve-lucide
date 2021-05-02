package com.example.revelucide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.revelucide.models.Objectif;
import com.example.revelucide.models.ObjectifAdapter;
import com.example.revelucide.models.bottomNavBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ObjectifActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private ListView listView;
    private ObjectifAdapter objectifAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectif);

        // initialisation de firebase authentification
        mAuth = FirebaseAuth.getInstance();
        this.loadData();

        listView = findViewById(R.id.listView);
        Button addButton = findViewById(R.id.AddObjectifButton);
        EditText GetValue = findViewById(R.id.AddObjectifText);

        objectifAdapter = new ObjectifAdapter(this, R.layout.listview_objectif_row, Objectif.getListObjectif());

        listView.setAdapter(objectifAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavBar.createBottomNavBar(3, bottomNavigationView, ObjectifActivity.this);

        FloatingActionButton button = findViewById(R.id.boutonAjout);

        // bouton d'ajout dans la bottom bar
        button.setOnClickListener(v -> {
            Intent intent0 = new Intent(ObjectifActivity.this, AjoutActivity.class);
            startActivity(intent0);
        });

        // Ajout d'un objectif a la liste
        addButton.setOnClickListener(v -> {
            // que si le champs n'est pas vide
            if (!GetValue.getText().toString().isEmpty()) {
                Objectif.getListObjectif().add(new Objectif(GetValue.getText().toString()));
                GetValue.setText("");
                objectifAdapter.notifyDataSetChanged();
                this.saveData();
            }

        });

        // On ouvre un intent dans lequel on trouvera toute les informations de l'objetif cliquer
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent0 = new Intent(ObjectifActivity.this, ExperienceActivity.class);
            intent0.putExtra("position", position);
            startActivity(intent0);
        });
    }

    @Override
    protected void onStart() { // Refresh les données a la sortie de l'intent Experiences via le bouton back de android
        super.onStart();
        objectifAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        MenuItem itemLogin = menu.findItem(R.id.login);
        MenuItem itemLogout = menu.findItem(R.id.logout);
        if (currentUser != null) {
            itemLogin.setVisible(false);
            itemLogout.setVisible(true);
        } else {
            itemLogin.setVisible(true);
            itemLogout.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        MenuItem itemLogin = menu.findItem(R.id.login);
        MenuItem itemLogout = menu.findItem(R.id.logout);
        if (currentUser != null) {
            itemLogin.setVisible(false);
            itemLogout.setVisible(true);
        } else {
            itemLogin.setVisible(true);
            itemLogout.setVisible(false);
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.login:
                Intent intent0 = new Intent(ObjectifActivity.this, LoginActivity.class);
                startActivity(intent0);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
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
