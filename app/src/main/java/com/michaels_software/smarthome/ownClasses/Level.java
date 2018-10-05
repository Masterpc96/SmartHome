package com.michaels_software.smarthome.ownClasses;

import com.google.gson.annotations.SerializedName;

public class Level {
    @SerializedName("level")
    private int level;

    public Level(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
