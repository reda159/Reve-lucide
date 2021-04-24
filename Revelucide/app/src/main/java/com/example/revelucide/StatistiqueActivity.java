package com.example.revelucide;

import android.content.Intent;
import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.List;

public class StatistiqueActivity extends AppCompatActivity {

    AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);

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
}
