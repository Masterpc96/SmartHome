package com.michaels_software.smarthome.ownClasses;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

public class Day {

    private int dayNumber;

    private int hourUp;
    private int minuteUp;

    private int hourDown;
    private int minuteDown;

    public Day(int dayNumber, int hourUp, int minuteUp, int hourDown, int minuteDown) {
        this.dayNumber = dayNumber;
        this.hourUp = hourUp;
        this.minuteUp = minuteUp;
        this.hourDown = hourDown;
        this.minuteDown = minuteDown;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public int getHourUp() {
        return hourUp;
    }

    public void setHourUp(int hourUp) {
        this.hourUp = hourUp;
    }

    public int getMinuteUp() {
        return minuteUp;
    }

    public void setMinuteUp(int minuteUp) {
        this.minuteUp = minuteUp;
    }

    public int getHourDown() {
        return hourDown;
    }

    public void setHourDown(int hourDown) {
        this.hourDown = hourDown;
    }

    public int getMinuteDown() {
        return minuteDown;
    }

    public void setMinuteDown(int minuteDown) {
        this.minuteDown = minuteDown;
    }
}