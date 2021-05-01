package com.example.revelucide;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.revelucide.models.bottomNavBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class RappelActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rappel);

        // initialisation de firebase authentification
        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavBar.createBottomNavBar(4, bottomNavigationView, RappelActivity.this);

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
                Intent intent0 = new Intent(RappelActivity.this, LoginActivity.class);
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
