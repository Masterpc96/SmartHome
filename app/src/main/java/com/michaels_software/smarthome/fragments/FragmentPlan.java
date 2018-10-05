package com.michaels_software.smarthome.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.michaels_software.smarthome.R;

import com.michaels_software.smarthome.activities.MainActivity;
import com.michaels_software.smarthome.dialogs.AddTime;
import com.michaels_software.smarthome.ownClasses.Day;
import com.michaels_software.smarthome.services.ESP8266;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentPlan extends Fragment implements CalendarView.OnDateChangeListener {

    private Retrofit retrofit;

    private ESP8266 esp8266;

    @BindView(R.id.sheme)
    TextView scheme;

    @BindView(R.id.calendar)
    CalendarView calendar;

    @OnClick(R.id.addTimes)
    void openAddTime() {
        AddTime time = new AddTime(getActivity());
        time.show();
    }


    public static ArrayList<Day> days = new ArrayList<>(31);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        ButterKnife.bind(this, view);

        retrofit = new Retrofit.Builder().baseUrl(MainActivity.ipAddress).addConverterFactory(GsonConverterFactory.create()).build();
        esp8266 = retrofit.create(ESP8266.class);

        if (days.size() == 0) {
            for (int i = 1; i < 32; i++) {
                days.add(new Day(i, 0, 0, 0, 0));
            }
        }


        esp8266.getPlan().enqueue(new Callback<ArrayList<Day>>() {
            @Override
            public void onResponse(Call<ArrayList<Day>> call, Response<ArrayList<Day>> response) {
                if (response.body().size() != 0) {
                  //  days = response.body();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Day>> call, Throwable t) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendar.setFirstDayOfWeek(2);
        calendar.setOnDateChangeListener(this);
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

        final Day day = days.get(i2 - 1);
        if (day.getHourDown() != 0 || day.getMinuteDown() != 0) {
            scheme.setText(String.format("W tym dniu roleta zostanie zapuszczona o %2d.%2d", day.getHourDown(), day.getMinuteDown()));
            if (day.getHourUp() != 0 || day.getMinuteUp() != 0) {
                scheme.setText(String.format("W tym dniu roleta zostanie zapuszczona o %2d.%2d a podniesiona o %2d.%2d", day.getHourDown(), day.getMinuteDown(), day.getHourUp(), day.getMinuteUp()));
            }
        } else if (day.getHourUp() != 0 || day.getMinuteUp() != 0) {
            scheme.setText(String.format("W tym dniu roleta zostanie podniesiona o %2d.%2d", day.getHourUp(), day.getMinuteUp()));
        } else scheme.setText("");

    }
}

