package com.michaels_software.smarthome.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.michaels_software.smarthome.R;
import com.michaels_software.smarthome.activities.MainActivity;
import com.michaels_software.smarthome.ownClasses.Level;
import com.michaels_software.smarthome.services.ESP8266;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentMove extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @BindView(R.id.level)
    SeekBar levelSeekBar;

    @BindView(R.id.windowLevel)
    TextView currentProgress;

    @OnClick(R.id.button)
    public void showCalendar() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment, new FragmentPlan());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private Retrofit retrofit;

    private ESP8266 esp8266;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_move, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        levelSeekBar.setOnSeekBarChangeListener(this);

        retrofit = new Retrofit.Builder().baseUrl(MainActivity.ipAddress).addConverterFactory(GsonConverterFactory.create()).build();
        esp8266 = retrofit.create(ESP8266.class);
        readProgress();
    }

    public void readProgress() {
        esp8266.getCalibrated().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null && response.body().string().equals("1")) {
                        esp8266.getLevel().enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.body() != null) {
                                    try {
                                        int val = Integer.valueOf(response.body().string());
                                        currentProgress.setEnabled(true);
                                        currentProgress.setText(getString(R.string.progress) + String.format(" %1d", val) + " %");
                                        levelSeekBar.setProgress(val);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    } else {
                        currentProgress.setText(R.string.noCal);
                        currentProgress.setEnabled(false);
                    }
                } catch (IOException e) {
                    currentProgress.setText(R.string.noCon);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    // if seekbar change progress
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        currentProgress.setText(getString(R.string.progress) + " " + i + " %");
    }

    // if seekbar start changing progress
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    // if seekbar stopped changing progress;
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // todo send progress
        esp8266.sendLevel(new Level(seekBar.getProgress())).enqueue(new Callback<Level>() {
            @Override
            public void onResponse(Call<Level> call, Response<Level> response) {
            }

            @Override
            public void onFailure(Call<Level> call, Throwable t) {
            }
        });


    }
}
