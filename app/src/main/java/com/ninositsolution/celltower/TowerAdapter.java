package com.ninositsolution.celltower;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parthasarathy D on 1/29/2019.
 * Ninos IT Solution Pvt Ltd
 * ben@ninositsolution.com
 */
public class TowerAdapter extends RecyclerView.Adapter<TowerAdapter.TowerViewHolder> {

    private DatabaseHelper databaseHelper;
    private Session session;

    private Context context;
    private List<NeighboringCellInfo> neighboringCellInfoList;

    public TowerAdapter(Context context, List<NeighboringCellInfo> neighboringCellInfoList) {
        this.context = context;
        this.neighboringCellInfoList = neighboringCellInfoList;
        session = new Session(context);
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

        ArrayList<String> signal;

        TowerModel towerModel = new TowerModel();

        long cl = neighboringCellInfoList.get(i).getCid();
        int netType = neighboringCellInfoList.get(i).getNetworkType();
        int rssi = neighboringCellInfoList.get(i).getRssi();

        signal = towerModel.updateSignal(netType, rssi);

        if (cl == -1)
        towerViewHolder.carrier_id.setText("0");

        else
        towerViewHolder.carrier_id.setText(String.valueOf(cl));

        towerViewHolder.dbm.setText(signal.get(0));
        towerViewHolder.milli_watt.setText(signal.get(1));
        towerViewHolder.type.setText(signal.get(3));
        //towerViewHolder.type.setText(String.valueOf(netType));

        databaseHelper.insertData(signal.get(3), signal.get(1), signal.get(0), signal.get(2));


    }



    @Override
    public int getItemCount() {
        return neighboringCellInfoList.size();
    }

    public class TowerViewHolder extends RecyclerView.ViewHolder{

        TextView type, carrier_id, milli_watt, dbm;

        public TowerViewHolder(@NonNull View itemView) {
            super(itemView);

            dbm = itemView.findViewById(R.id.dBm);
            type = itemView.findViewById(R.id.type);
            carrier_id = itemView.findViewById(R.id.carrier_id);
            milli_watt = itemView.findViewById(R.id.milliWatt);
        }
    }
}
