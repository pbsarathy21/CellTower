package com.ninositsolution.celltower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private String latitude_key = "latitude";
    private String longitude_key = "longitude";
    private String NotAvailable = "NA";
    private String file_count = "file_count";

    @SuppressLint("CommitPrefEdits")
    public Session(Context context) {
        String preference_name = "CELLTOWER";
        preferences = context.getSharedPreferences(preference_name,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public String getLatitude_key() {
        return preferences.getString(latitude_key, NotAvailable);
    }

    public void setLatitude_key(String latitude) {
        editor.putString(latitude_key, latitude).apply();
    }

    public String getLongitude_key() {
        return preferences.getString(longitude_key, NotAvailable);
    }

    public void setLongitude_key(String longitude) {
        editor.putString(longitude_key, longitude).apply();
    }

    public int getFile_count() {
        return preferences.getInt(file_count, 0);
    }

    public void setFile_count(int count) {
        editor.putInt(file_count,count).apply();
    }
}
