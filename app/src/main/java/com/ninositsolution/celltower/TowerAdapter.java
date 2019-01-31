package com.ninositsolution.celltower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telephony.CellInfo;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Parthasarathy D on 1/29/2019.
 * Ninos IT Solution Pvt Ltd
 * ben@ninositsolution.com
 */
public class TowerAdapter extends RecyclerView.Adapter<TowerAdapter.TowerViewHolder> {

    private DatabaseHelper databaseHelper;

    private Context context;
    private List<NeighboringCellInfo> neighboringCellInfoList;

    public TowerAdapter(Context context, List<NeighboringCellInfo> neighboringCellInfoList) {
        this.context = context;
        this.neighboringCellInfoList = neighboringCellInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TowerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_towers, viewGroup, false);
        return new TowerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TowerViewHolder towerViewHolder, int i) {

        databaseHelper = new DatabaseHelper(context);

        String cl_text,lac_text,netType_text,rssi_text,psc_text, dbm_text;

        long cl = neighboringCellInfoList.get(i).getCid();
        long tac = neighboringCellInfoList.get(i).getLac();
        long netType = neighboringCellInfoList.get(i).getNetworkType();
        long rssi = neighboringCellInfoList.get(i).getRssi();
        long psc = neighboringCellInfoList.get(i).getPsc();

        if (cl == TelephonyManager.UNKNOWN_CARRIER_ID)
        {
            cl_text = "NA";
            towerViewHolder.cl.setText(cl_text);
        }
        else
        {
             cl_text = String.valueOf(cl);
            towerViewHolder.cl.setText(cl_text);
        }
        if (tac == TelephonyManager.UNKNOWN_CARRIER_ID)
        {
             lac_text = "NA";
            towerViewHolder.tac.setText(lac_text);
        }
        else
        {
             lac_text = String.valueOf(tac);
            towerViewHolder.tac.setText(lac_text);
        }
        if (netType == TelephonyManager.NETWORK_TYPE_UNKNOWN)
        {
             netType_text = "NA";
            towerViewHolder.mcc.setText(netType_text);
        }
        else if (netType == TelephonyManager.NETWORK_TYPE_EDGE || netType == TelephonyManager.NETWORK_TYPE_GPRS)
        {
             netType_text = "GSM";
            towerViewHolder.mcc.setText(netType_text);
        }
        else if (netType == TelephonyManager.NETWORK_TYPE_UMTS || netType == TelephonyManager.NETWORK_TYPE_HSDPA || netType == TelephonyManager.NETWORK_TYPE_HSPA
         || netType == TelephonyManager.NETWORK_TYPE_HSUPA)
        {
             netType_text = "UMTS";
            towerViewHolder.mcc.setText(netType_text);
        }

        else if (netType == TelephonyManager.NETWORK_TYPE_LTE)
        {
             netType_text = "LTE";
            towerViewHolder.mcc.setText(netType_text);
        }

        else
        {
            netType_text = "NA";
            towerViewHolder.mcc.setText(netType_text);
        }

        if (rssi >= 0 && rssi <= 31)
        {
            long dbm = (rssi*2)-113;

             dbm_text = String.valueOf(dbm);
             rssi_text = String.valueOf(rssi);

            towerViewHolder.dbm.setText(dbm_text+" dBm");
            towerViewHolder.mcc.setText(rssi_text);
        } else
        {
            dbm_text = "NA";
            rssi_text = String.valueOf(rssi);
            towerViewHolder.dbm.setText(dbm_text+" dBm");
            towerViewHolder.mcc.setText(rssi_text);
        }

        if (psc == TelephonyManager.UNKNOWN_CARRIER_ID)
        {
             psc_text = "NA";
            towerViewHolder.mnc.setText(psc_text);
        }
        else
        {
             psc_text = String.valueOf(psc);
            towerViewHolder.mnc.setText(psc_text);
        }

        LocationUpdate locationUpdate = new LocationUpdate(context);

        SharedPreferences preferences = context.getSharedPreferences("LOCATION", Context.MODE_PRIVATE);
        String lat = preferences.getString("lat", "NA");
        String lon = preferences.getString("lon", "NA");

        databaseHelper.insertData(cl_text,lac_text,rssi_text,psc_text,netType_text, dbm_text, lat, lon);
    }

    @Override
    public int getItemCount() {
        return neighboringCellInfoList.size();
    }

    public class TowerViewHolder extends RecyclerView.ViewHolder{

        TextView cl, tac, mcc, mnc, dbm;

        public TowerViewHolder(@NonNull View itemView) {
            super(itemView);

            cl = itemView.findViewById(R.id.cl);
            tac = itemView.findViewById(R.id.tac);
            mcc = itemView.findViewById(R.id.mcc);
            mnc = itemView.findViewById(R.id.mnc);
            dbm = itemView.findViewById(R.id.dBm);
        }
    }
}
