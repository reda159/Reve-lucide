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

public class AstuceAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private int mResource;

    public AstuceAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView astuce = convertView.findViewById(R.id.textView);

        astuce.setText(getItem(position));

        return convertView;
    }
}
