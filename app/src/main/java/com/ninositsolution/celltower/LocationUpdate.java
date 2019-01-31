package com.ninositsolution.celltower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by Parthasarathy D on 1/31/2019.
 * Ninos IT Solution Pvt Ltd
 * ben@ninositsolution.com
 */
public class LocationUpdate  {

    private Context context;

    private String latitude = "NA";
    private String longitude = "NA";


    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationListener locationListener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */


    @SuppressLint("MissingPermission")
    public LocationUpdate(final Context context) {
        this.context = context;

        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, UPDATE_INTERVAL, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());

                SharedPreferences preferences = context.getSharedPreferences("LOCATION", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("lat", latitude);
                editor.putString("lon", longitude);
                editor.commit();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }




}
