package com.example.revelucide.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.revelucide.R;
import java.util.ArrayList;

public class ObjectifAdapter extends ArrayAdapter<Objectif> {

    private Context mContext;
    private int mResource;

    public ObjectifAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Objectif> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView rowTitle = convertView.findViewById(R.id.rowTitle);
        TextView rowNbExperience = convertView.findViewById(R.id.rowNbExperience);

        rowTitle.setText(getItem(position).getTitle());
        String message = "Nombre d'expériences : " + getItem(position).getNbExperience();
        rowNbExperience.setText(message);

        return convertView;
    }
}
