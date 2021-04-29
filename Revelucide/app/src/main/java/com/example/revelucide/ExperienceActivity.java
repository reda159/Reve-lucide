package com.example.revelucide;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.revelucide.models.AstuceAdapter;
import com.example.revelucide.models.Objectif;
import com.example.revelucide.models.ObjectifAdapter;

public class ExperienceActivity extends AppCompatActivity {

    private ListView listView;
    private AstuceAdapter astuceAdapter;

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
        }

    }
}