package com.michaels_software.smarthome.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


import com.michaels_software.smarthome.R;
import com.michaels_software.smarthome.activities.MainActivity;
import com.michaels_software.smarthome.ownClasses.Day;
import com.michaels_software.smarthome.services.ESP8266;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.michaels_software.smarthome.fragments.FragmentPlan.days;

public class AddTime extends Dialog implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @OnClick(R.id.dayEditText)
    void chooseDay() {
        datePickerDialog.show();
    }

    @OnClick(R.id.hourDownEditText)
    void chooseHourDown() {
        hourDown = true;
        hourDownPicker.show();

    }

    @OnClick(R.id.hourUpEditText)
    void chooseHourUp() {
        hourDown = false;
        hourDownPicker.show();
    }

    @BindView(R.id.checkBox)
    CheckBox box;

    @BindView(R.id.hourUpEditText)
    EditText hourUpE;

    @BindView(R.id.hourDownEditText)
    EditText hourDownE;

    @BindView(R.id.dayEditText)
    EditText dayNumberE;

    @OnClick(R.id.planButton)
    void accept() {
        if (day != null) {
            if (box.isChecked()) {
                copyDays(day.getDayNumber() - 1);
            }
            esp8266.sendDays(days).enqueue(new Callback<ArrayList<Day>>() {
                @Override
                public void onResponse(Call<ArrayList<Day>> call, Response<ArrayList<Day>> response) {
                    Toast.makeText(getContext(), response.message(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ArrayList<Day>> call, Throwable t) {
                    Toast.makeText(getContext(), t.toString(),Toast.LENGTH_LONG).show();

                }
            });
        }
        dismiss();
    }

    private DatePickerDialog datePickerDialog;

    private TimePickerDialog hourDownPicker;

    private Day day;

    private boolean hourDown = false;

    private Retrofit retrofit;

    private ESP8266 esp8266;

    public AddTime(@NonNull Context context) {
        super(context, R.style.myDialogTheme);
        setContentView(R.layout.dialog_add_time);
        ButterKnife.bind(this);


        Calendar currentTime = Calendar.getInstance();
        int startYear = currentTime.get(Calendar.YEAR);
        int starthMonth = currentTime.get(Calendar.MONTH);
        int startDay = currentTime.get(Calendar.DAY_OF_MONTH);

        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        day = new Day(0, 0, 0, 0, 0);

        datePickerDialog = new DatePickerDialog(getContext(), this, startYear, starthMonth, startDay);

        hourDownPicker = new TimePickerDialog(getContext(), this, hour, minute, true);

        retrofit = new Retrofit.Builder().baseUrl(MainActivity.ipAddress).addConverterFactory(GsonConverterFactory.create()).build();
        esp8266 = retrofit.create(ESP8266.class);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        day.setDayNumber(i2);
        dayNumberE.setText(String.format("%02d-%02d-%d", i2, i1, i));

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        if(day.getDayNumber()!=0){
            if (hourDown) {
                day.setHourDown(i);
                day.setMinuteDown(i1);
                hourDownE.setText(String.format("%02d:%02d", i, i1));
            } else {
                day.setHourUp(i);
                day.setMinuteUp(i1);
                hourUpE.setText(String.format("%02d:%02d", i, i1));
            }
            days.set(day.getDayNumber() - 1, day);
        }else{
            Toast.makeText(getContext(), R.string.chooseDay, Toast.LENGTH_SHORT).show();
        }
    }

    public void copyDays(int position) {
        Day d1 = days.get(position);
        for (Day d : days) {

            d.setHourDown(d1.getHourDown());
            d.setMinuteDown(d1.getMinuteDown());

            d.setHourUp(d1.getHourUp());
            d.setMinuteUp(d1.getMinuteUp());
        }
    }
}
