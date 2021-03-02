package com.example.revelucide;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class RappelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rappel);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setBackground(null);
        menu.getItem(2).setEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent0;
            switch (item.getItemId()) {
                case R.id.journal:
                    intent0 = new Intent(RappelActivity.this, MainActivity.class);
                    startActivity(intent0);
                    break;
                case R.id.statistique:
                    intent0 = new Intent(RappelActivity.this, StatistiqueActivity.class);
                    startActivity(intent0);
                    break;
                case R.id.recherche:
                    intent0 = new Intent(RappelActivity.this, RechercheActivity.class);
                    startActivity(intent0);
                    break;
                case R.id.rappel:
                    break;
            }
            return true;
        });

        FloatingActionButton buton = findViewById(R.id.boutonAjout);

        buton.setOnClickListener(v -> {
            Intent intent0 = new Intent(RappelActivity.this, AjoutActivity.class);
            startActivity(intent0);
        });

        Button setTime = findViewById(R.id.button2);
        Button setAlarm = findViewById(R.id.button);
        EditText timeHour = findViewById(R.id.Hour);
        EditText timeMinute = findViewById(R.id.Minute);

        setTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(RappelActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    timeHour.setText(String.format("%02d", hourOfDay));
                    timeMinute.setText(String.format("%02d", minute));
                }
            }, currentHour, currentMinute, false);

            timePickerDialog.show();
        });

        setAlarm.setOnClickListener(v -> {
            if (!this.verifyForm(timeHour, timeMinute)) {
                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(timeHour.getText().toString()));
                intent.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(timeMinute.getText().toString()));
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.rappel_question_reve));  //CHANGE

                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }else{
                    this.sendMessage(getString(R.string.rappel_alert_message_notSupported));
                }
            }
        });
    }

    private boolean verifyForm(EditText timeHour, EditText timeMinute) {
        boolean check = false;
        if(timeHour.getText().toString().isEmpty() || timeMinute.getText().toString().isEmpty()) {
            this.sendMessage(getString(R.string.rappel_alert_message_setTime));
            check = true;
        }
        return check;
    }

    private void sendMessage(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(RappelActivity.this);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(getString(R.string.yes), (dialog, id) -> dialog.cancel());

        AlertDialog alert1 = builder1.create();
        alert1.show();
    }
}
