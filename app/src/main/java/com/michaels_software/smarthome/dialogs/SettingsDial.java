package com.michaels_software.smarthome.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.EditText;

import com.michaels_software.smarthome.R;
import com.michaels_software.smarthome.observers.IpObserver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingsDial extends Dialog {
    private IpObserver observer;
    @BindView(R.id.ipAdressText)
    EditText ip;

    @OnClick(R.id.saveSettings)
    public void save() {
        observer.setIp(ip.getText().toString());
        dismiss();
    }

    public SettingsDial(@NonNull Context context) {
        super(context, R.style.myDialogTheme);
        setContentView(R.layout.dialog_settings);
        setCancelable(true);
        ButterKnife.bind(this);
    }

    public void setObserver(IpObserver observer){
        this.observer = observer;
    }
}
