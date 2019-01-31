package com.ninositsolution.celltower;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.telephony.NeighboringCellInfo;

import java.util.List;

/**
 * Created by Parthasarathy D on 1/29/2019.
 * Ninos IT Solution Pvt Ltd
 * ben@ninositsolution.com
 */
public class TowerVM {

    private TowerModel towerModel;
    private Context context;
    private Activity activity;
    private ITower iTower;

    private final int DURATION = 3000;

    public TowerVM(Context context, Activity activity,  ITower iTower) {
        this.context = context;
        this.iTower = iTower;
        this.activity = activity;
        towerModel = new TowerModel(context, activity);
        loadAdapter();
    }

    private void loadAdapter() {

        boolean permission;

        do {
            permission = towerModel.checkPermissions();
        }  while (!permission);

        scheduleTask();

    }

    public void scheduleTask() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                getLists();          // this method will contain your almost-finished HTTP calls
                handler.postDelayed(this, DURATION);
            }
        }, DURATION);

    }

    private void getLists() {

        List<NeighboringCellInfo> neighboringCellInfoList = towerModel.getTowerList();

        if (neighboringCellInfoList == null || neighboringCellInfoList.size()==0)
        {
            iTower.listEmpty();
        }

        else
        {
            TowerAdapter towerAdapter = new TowerAdapter(context, neighboringCellInfoList);
            iTower.setRecyclerViewAdapter(towerAdapter);
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.deleteAll();
    }



    //ClickListeners

    public void onExportClicked()
    {
        iTower.onExportClicked();
    }

}
