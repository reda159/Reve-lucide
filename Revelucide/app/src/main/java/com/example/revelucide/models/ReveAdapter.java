package com.example.revelucide.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.revelucide.R;

import java.util.ArrayList;

public class ReveAdapter extends ArrayAdapter<Reve> {

    private Context mContext;
    private int mResource;

    public ReveAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Reve> objects) {
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
        TextView rowDate = convertView.findViewById(R.id.rowDate);
        EditText rowDescription = convertView.findViewById(R.id.rowDescription);

        rowTitle.setText(getItem(position).getTitre());
        rowDate.setText(getItem(position).getDate().toString()); // Je n'arrive pas à sortir le jour, etc... d'un objet Date
        rowDescription.setText(getItem(position).getDescription());

        return convertView;
    }


}
