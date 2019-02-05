package com.ninositsolution.celltower;

/**
 * Created by Parthasarathy D on 1/29/2019.
 * Ninos IT Solution Pvt Ltd
 * ben@ninositsolution.com
 */
public interface ITower {

    void setRecyclerViewAdapter(TowerAdapter towerAdapter);
    void listEmpty();
    void onExportClicked();
    void onRefreshingLists();
}
