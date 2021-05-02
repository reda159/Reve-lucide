package com.example.revelucide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.revelucide.models.Reve;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

public class AjoutActivity extends AppCompatActivity {

    Spinner spinner;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        spinner = findViewById(R.id.spinnerClarete);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.clarete, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button buton = findViewById(R.id.buttonAjout);
        buton.setOnClickListener(v -> {
            DatePicker datePicker = findViewById(R.id.date);
            Date date1 = this.getDate(datePicker);

            EditText titleEdit = findViewById(R.id.title);
            String title = titleEdit.getText().toString();

            EditText descriptionEdit = findViewById(R.id.description);
            String description = descriptionEdit.getText().toString();

            Switch lucid = findViewById(R.id.switchLucidite);
            Spinner clarete = findViewById(R.id.spinnerClarete);

            if (this.verifyForm(title, description)) {
                Reve reve = new Reve(title, description, date1, clarete.getSelectedItem().toString(),lucid.isChecked());
                Reve.addReveLog(reve);
                this.saveReve();
                Intent intent0 = new Intent(AjoutActivity.this, MainActivity.class);
                startActivity(intent0);
            }
        });
    }

    private void saveReve() {
        sp = getSharedPreferences("ReveLucidePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Reve.getReveLog());
        editor.putString("reveLog", json);
        editor.apply();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) { // si un utilisateur est connecté faire un envoie a la base
            // je dois spécifier l'url car sinon elle n'est pas bonne
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://reve-lucide-default-rtdb.europe-west1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference();
            // je crée un noeu unique pour chaque utilisateur
            myRef.child(currentUser.getUid()).child("reveList").setValue(json);
            myRef.push();
        }
    }

    private boolean verifyForm(String title, String description) {
        boolean check = true;
        if (title.equals("")) {
            this.sendMessage(getString(R.string.ajouter_alert_message_title));
            check = false;
        }
        if (description.equals("")) {
            this.sendMessage(getString(R.string.ajouter_alert_message_story));
            check = false;
        }
        return check;
    }

    private Date getDate(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    private void sendMessage(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AjoutActivity.this);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(getString(R.string.yes), (dialog, id) -> dialog.cancel());

        AlertDialog alert1 = builder1.create();
        alert1.show();
    }
}
