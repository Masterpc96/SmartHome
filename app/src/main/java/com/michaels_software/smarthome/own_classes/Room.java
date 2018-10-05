package com.michaels_software.smarthome.own_classes;

import android.graphics.drawable.Drawable;

public class Room {
    private String name;
    private int ImageRes;

    public Room(String name, int imageRes) {
        this.name = name;
        ImageRes = imageRes;
    }

    public String getName() {
        return name;
    }

    public int getImageRes() {
        return ImageRes;
    }
}
