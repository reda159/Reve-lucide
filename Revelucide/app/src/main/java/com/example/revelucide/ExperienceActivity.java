package com.example.revelucide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.revelucide.models.AstuceAdapter;
import com.example.revelucide.models.Objectif;
import com.example.revelucide.models.ObjectifAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExperienceActivity extends AppCompatActivity {

    private ListView listView;
    private AstuceAdapter astuceAdapter;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        int position = getIntent().getIntExtra("position", -1);
        Objectif obj = Objectif.getListObjectif().get(position);

        if (position != -1) {
            listView = findViewById(R.id.list_astuce);
            astuceAdapter = new AstuceAdapter(this, R.layout.listview_astuce_row, obj.getListAstuce());
            listView.setAdapter(astuceAdapter);

            EditText titre = findViewById(R.id.titreObjectif);
            titre.setText(Objectif.getListObjectif().get(position).getTitle());

            Button addAstuceButton = findViewById(R.id.addAstuceButton);
            addAstuceButton.setOnClickListener(v -> {
                EditText astuceMessage = findViewById(R.id.titreAstuce);
                Objectif.getListObjectif().get(position).getListAstuce().add(astuceMessage.getText().toString());
                astuceMessage.setText("");
                astuceAdapter.notifyDataSetChanged();
                this.saveData();
            });

            Button modifyTitleButton = findViewById(R.id.modifyTitleObj);
            modifyTitleButton.setOnClickListener(v -> {
                Objectif.getListObjectif().get(position).setTitle(titre.getText().toString());
                astuceAdapter.notifyDataSetChanged();
                this.saveData();
            });

            AstuceAdapter astuceAdapter2 = new AstuceAdapter(this, R.layout.listview_astuce_row, obj.getListExperience());
            ListView ListExperiences = findViewById(R.id.listView_Experiences);
            ListExperiences.setAdapter(astuceAdapter2);

            Button addExperienceButton = findViewById(R.id.addExperienceButton);
            addExperienceButton.setOnClickListener(v -> {
                EditText experienceMessage = findViewById(R.id.messageExperiences);
                Objectif.getListObjectif().get(position).getListExperience().add(experienceMessage.getText().toString());
                experienceMessage.setText("");
                astuceAdapter2.notifyDataSetChanged();
                this.saveData();
            });
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