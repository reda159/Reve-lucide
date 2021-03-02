package com.example.revelucide;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.revelucide.models.Reve;
import com.example.revelucide.models.ReveAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RechercheActivity extends AppCompatActivity {

    private ListView listView;
    ReveAdapter reveAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        listView = findViewById(R.id.listView);
        EditText filter = findViewById(R.id.searchBar);

        reveAdapter = new ReveAdapter(this, R.layout.listview_row, Reve.getReveLog());

        listView.setAdapter(reveAdapter);

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (RechercheActivity.this).reveAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setBackground(null);
        menu.getItem(2).setEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent0;
            switch (item.getItemId()) {
                case R.id.journal:
                    intent0 = new Intent(RechercheActivity.this, MainActivity.class);
                    startActivity(intent0);
                    break;
                case R.id.statistique:
                    intent0 = new Intent(RechercheActivity.this, StatistiqueActivity.class);
                    startActivity(intent0);
                    break;
                case R.id.recherche:
                    break;
                case R.id.rappel:
                    intent0 = new Intent(RechercheActivity.this, RappelActivity.class);
                    startActivity(intent0);
                    break;
            }
            return true;
        });

        FloatingActionButton buton = findViewById(R.id.boutonAjout);

        buton.setOnClickListener(v -> {
            Intent intent0 = new Intent(RechercheActivity.this, AjoutActivity.class);
            startActivity(intent0);
        });
    }
}
