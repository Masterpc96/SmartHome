package com.michaels_software.smarthome.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.michaels_software.smarthome.R;
import com.michaels_software.smarthome.fragments.FragmentMove;

import butterknife.ButterKnife;

public class RoomActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.add(R.id.fragment, new FragmentMove());

        transaction.commit();
    }
}
