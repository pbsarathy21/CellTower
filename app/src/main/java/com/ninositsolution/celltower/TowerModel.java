package com.ninositsolution.celltower;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parthasarathy D on 1/29/2019.
 * Ninos IT Solution Pvt Ltd
 * ben@ninositsolution.com
 */
public class TowerModel {

    private Context context;
    private Activity activity;

    public TowerModel() {
    }

    public TowerModel(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)  {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 777);

            return false;

        } else {
            return true;
        }
    }

    public List<NeighboringCellInfo> getTowerList()
    {
        TelephonyManager telephonyManager =(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        @SuppressLint("MissingPermission")
        List<NeighboringCellInfo> neighboringCellInfoList = telephonyManager.getNeighboringCellInfo();

        return neighboringCellInfoList;
    }

    public ArrayList<String> updateSignal(int netType, int rssi) {

        ArrayList<String> signal = new ArrayList<>();
        signal.clear();

        switch (netType)
        {
            case TelephonyManager.NETWORK_TYPE_1xRTT :

                signal = get3GSignal(rssi);
                signal.add("1xRTT");

                return signal;

            case TelephonyManager.NETWORK_TYPE_CDMA :

                signal = get3GSignal(rssi);
                signal.add("CDMA");

                return signal;

            case TelephonyManager.NETWORK_TYPE_EDGE :

                signal = get2GSignal(rssi);
                signal.add("EDGE");

                return signal;

            case TelephonyManager.NETWORK_TYPE_EHRPD :

                signal = get4GSignal(rssi);
                signal.add("EHRPD");

                return signal;

            case TelephonyManager.NETWORK_TYPE_EVDO_0 :

                signal = get3GSignal(rssi);
                signal.add("EVDO_0");

                return signal;

            case TelephonyManager.NETWORK_TYPE_EVDO_A :

                signal = get3GSignal(rssi);
                signal.add("EVDO_A");

                return signal;

            case TelephonyManager.NETWORK_TYPE_EVDO_B :

                signal = get3GSignal(rssi);
                signal.add("EVDO_B");

                return signal;

            case TelephonyManager.NETWORK_TYPE_GPRS :

                signal = get2GSignal(rssi);
                signal.add("GPRS");

                return signal;

            case TelephonyManager.NETWORK_TYPE_GSM :

                signal = get2GSignal(rssi);
                signal.add("GSM");

                return signal;

            case TelephonyManager.NETWORK_TYPE_HSDPA :

                signal = get3GSignal(rssi);
                signal.add("HSDPA");

                return signal;

            case TelephonyManager.NETWORK_TYPE_HSPA :

                signal = get3GSignal(rssi);
                signal.add("HSPA");

                return signal;

            case TelephonyManager.NETWORK_TYPE_HSPAP :

                signal = get3GSignal(rssi);
                signal.add("HSPAP");

                return signal;

            case TelephonyManager.NETWORK_TYPE_HSUPA :

                signal = get3GSignal(rssi);
                signal.add("HSUPA");

                return signal;

            case TelephonyManager.NETWORK_TYPE_IDEN :

                signal = get4GSignal(rssi);
                signal.add("IDEN");

                return signal;

            case TelephonyManager.NETWORK_TYPE_IWLAN :

                signal = get4GSignal(rssi);
                signal.add("IWLAN");

                return signal;

            case TelephonyManager.NETWORK_TYPE_LTE :

                signal = get4GSignal(rssi);
                signal.add("LTE");

                return signal;

            case TelephonyManager.NETWORK_TYPE_TD_SCDMA :

                signal = get4GSignal(rssi);
                signal.add("SCDMA");

                return signal;

            case TelephonyManager.NETWORK_TYPE_UMTS :

                signal = get3GSignal(rssi);
                signal.add("UMTS");

                return signal;

            case TelephonyManager.NETWORK_TYPE_UNKNOWN :

                signal = get4GSignal(rssi);
                signal.add("UNKNOWN");

                return signal;

            default:
                signal.add("NA");
                signal.add("NA");
                signal.add("NA");

                return signal;

        }

    }

    private ArrayList<String> get2GSignal(int rssi)
    {
        String dbm_text,milli_text,milli_text1,milli_text2, milliWatt, dBmSignal;

        if (rssi >= 0 && rssi <= 31)
        {
            long dbm = (rssi*2)-113;

            float dbmFloat = (rssi*2)-113;

            double exp = Math.pow(10, dbmFloat/10);

            dbm_text = String.valueOf(dbm);

            milli_text = String.valueOf(exp);

            milli_text1 = milli_text.substring(0,4);

            milli_text2 = milli_text.substring(milli_text.lastIndexOf("E")+1);

            dBmSignal = dbm_text+" dBm";
            milliWatt = milli_text1 + " * 10^(" + milli_text2 + ")";

            ArrayList<String> signal = new ArrayList<>();
            signal.clear();
            signal.add(dBmSignal);
            signal.add(milliWatt);
            signal.add(milli_text);

            return signal;

        } else
        {
            ArrayList<String> signal = new ArrayList<>();
            signal.clear();
            signal.add("NA");
            signal.add("NA");
            signal.add("NA");

            return signal;
        }
    }

    private ArrayList<String> get3GSignal(int rssi)
    {
        String dbm_text,milli_text,milli_text1,milli_text2, milliWatt, dBmSignal;

        if (rssi >= -5 && rssi <= 91)
        {
            long dbm = rssi-116;

            float dbmFloat = rssi-116;

            double exp = Math.pow(10, dbmFloat/10);

            dbm_text = String.valueOf(dbm);

            milli_text = String.valueOf(exp);

            milli_text1 = milli_text.substring(0,4);

            milli_text2 = milli_text.substring(milli_text.lastIndexOf("E")+1);

            dBmSignal = dbm_text+" dBm";
            milliWatt = milli_text1 + " * 10^(" + milli_text2 + ")";

            ArrayList<String> signal = new ArrayList<>();
            signal.clear();
            signal.add(dBmSignal);
            signal.add(milliWatt);
            signal.add(milli_text);

            return signal;

        } else
        {
            ArrayList<String> signal = new ArrayList<>();
            signal.clear();
            signal.add("NA");
            signal.add("NA");
            signal.add("NA");

            return signal;
        }
    }

    private ArrayList<String> get4GSignal(int rssi)
    {
        String dbm_text,milli_text,milli_text1,milli_text2, milliWatt, dBmSignal;

        if (rssi >= 0 && rssi <= 97)
        {
            long dbm = rssi-140;

            float dbmFloat = rssi-140;

            double exp = Math.pow(10, dbmFloat/10);

            dbm_text = String.valueOf(dbm);

            milli_text = String.valueOf(exp);

            milli_text1 = milli_text.substring(0,4);

            milli_text2 = milli_text.substring(milli_text.lastIndexOf("E")+1);

            dBmSignal = dbm_text+" dBm";
            milliWatt = milli_text1 + " * 10^(" + milli_text2 + ")";

            ArrayList<String> signal = new ArrayList<>();
            signal.clear();
            signal.add(dBmSignal);
            signal.add(milliWatt);
            signal.add(milli_text);

            return signal;

        } else
        {
            ArrayList<String> signal = new ArrayList<>();
            signal.clear();
            signal.add("NA");
            signal.add("NA");
            signal.add("NA");

            return signal;
        }
    }

}