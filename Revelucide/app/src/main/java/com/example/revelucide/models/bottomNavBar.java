package com.example.revelucide.models;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.revelucide.MainActivity;
import com.example.revelucide.ObjectifActivity;
import com.example.revelucide.R;
import com.example.revelucide.RappelActivity;
import com.example.revelucide.ObjectifActivity;
import com.example.revelucide.StatistiqueActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class bottomNavBar {

    public static void createBottomNavBar(int page, BottomNavigationView bottomNavigationView, Context context) {
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(page);
        menuItem.setChecked(true);

        bottomNavigationView.setBackground(null);
        menu.getItem(2).setEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent0;
            switch (item.getItemId()) {
                case R.id.journal:
                    intent0 = new Intent(context, MainActivity.class);
                    context.startActivity(intent0);
                    break;
                case R.id.statistique:
                    intent0 = new Intent(context, StatistiqueActivity.class);
                    context.startActivity(intent0);
                    break;
                case R.id.recherche:
                    intent0 = new Intent(context, ObjectifActivity.class);
                    context.startActivity(intent0);
                    break;
                case R.id.rappel:
                    intent0 = new Intent(context, RappelActivity.class);
                    context.startActivity(intent0);
                    break;
            }
            return true;
        });
    }
}
