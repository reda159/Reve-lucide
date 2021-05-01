package com.example.revelucide;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.revelucide.models.Reve;
import com.example.revelucide.models.bottomNavBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class StatistiqueActivity extends AppCompatActivity {

    private AnyChartView anyChartView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);

        // initialisation de firebase authentification
        mAuth = FirebaseAuth.getInstance();

        anyChartView = findViewById(R.id.piechart);
        this.setupPieChart();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavBar.createBottomNavBar(1, bottomNavigationView, StatistiqueActivity.this);

        FloatingActionButton buton = findViewById(R.id.boutonAjout);

        buton.setOnClickListener(v -> {
            Intent intent0 = new Intent(StatistiqueActivity.this, AjoutActivity.class);
            startActivity(intent0);
        });
    }

    private void setupPieChart() {
        Pie pieChart = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        int reveLucid = 0;
        String[] lucide = {getString(R.string.chart_lucidDream), getString(R.string.chart_normalDream)};
        for (int i = 0; i < Reve.getReveLog().size(); i++) {
            if (Reve.getReveLog().get(i).getLucide())
                reveLucid++;
        }
        int[] stats = {reveLucid, Reve.getReveLog().size() - reveLucid};

        for (int i = 0; i < 2; i++) {
            dataEntries.add(new ValueDataEntry(lucide[i], stats[i]));
        }

        pieChart.data(dataEntries);
        pieChart.title(getString(R.string.chart_title));
        anyChartView.setChart(pieChart);
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
                Intent intent0 = new Intent(StatistiqueActivity.this, LoginActivity.class);
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
}
