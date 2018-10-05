package com.michaels_software.smarthome.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michaels_software.smarthome.R;
import com.michaels_software.smarthome.own_classes.Room;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private ArrayList<Room> data;
    private Context context;

    public GridAdapter(ArrayList<Room> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v;
        if (view == null) {
            v = inflater.inflate(R.layout.adapter_view, viewGroup, false);
            ImageView imageView = v.findViewById(R.id.image);
            TextView title = v.findViewById(R.id.title);

            imageView.setImageResource(data.get(i).getImageRes());

            title.setText(data.get(i).getName());


        } else {
            v = view;
        }

        return v;
    }
}
