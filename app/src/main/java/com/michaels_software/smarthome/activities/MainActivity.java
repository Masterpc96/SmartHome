package com.michaels_software.smarthome.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.michaels_software.smarthome.R;
import com.michaels_software.smarthome.adapters.GridAdapter;
import com.michaels_software.smarthome.dialogs.SettingsDial;
import com.michaels_software.smarthome.observers.IpObserver;
import com.michaels_software.smarthome.own_classes.Room;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity implements IpObserver, AdapterView.OnItemClickListener {

    private ArrayList<Room> rooms;

    private Context context = this;

    @BindView(R.id.roomsView)
    GridView roomsView;

    public static String ipAddress;

    @BindView(R.id.toolbar)
    Toolbar myToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        SharedPreferences preferences = getSharedPreferences("com.michaels-software.smarthome.PREFERENCES", MODE_PRIVATE);

        ipAddress = preferences.getString("IP", "http://10.0.1.2");

        setSupportActionBar(myToolbar);

        rooms = new ArrayList<>();

        rooms.add(new Room("Kuchnia",R.drawable.ic_cooking));


        getSupportActionBar().setDisplayShowTitleEnabled(false);

        roomsView.setAdapter(new GridAdapter(rooms, context));

        roomsView.setOnItemClickListener(this);
    }

    // create menu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    // menu listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            // todo create settings menu
            SettingsDial dial = new SettingsDial(this);
            dial.setObserver(this);
            dial.show();
            return true;
        }
        return false;
    }


    @Override
    public void setIp(String ipAddress) {
        this.ipAddress = ipAddress;

    }

    @Override
    protected void onStop() {
        super.onStop();
        // todo add save to share preferences ip address
        SharedPreferences preferences = getSharedPreferences("com.michaels-software.smarthome.PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("IP", ipAddress);
        editor.apply();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(context, RoomActivity.class);
        startActivity(intent);

    }
}
