package com.ninositsolution.celltower;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.ninositsolution.celltower.databinding.ActivityTowerBinding;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class TowerActivity extends AppCompatActivity implements ITower{

    ActivityTowerBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tower);
        binding.setTower(new TowerVM(getApplicationContext(), TowerActivity.this, this));

        LocationUpdate locationUpdate = new LocationUpdate(this);


    }


        @Override
    public void setRecyclerViewAdapter(TowerAdapter towerAdapter) {

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(towerAdapter);
    }

    @Override
    public void listEmpty() {
        Toast.makeText(this, "List Empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onExportClicked() {

        databaseHelper = new DatabaseHelper(this);
        final Cursor cursor = databaseHelper.getAllData();

        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "myData.xls";

        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }

        try {
            //file path
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("towersList", 0);
            // column and row
            sheet.addCell(new Label(0, 0, "ID"));
            sheet.addCell(new Label(1, 0, "CL"));
            sheet.addCell(new Label(2, 0, "LAC"));
            sheet.addCell(new Label(3, 0, "RSSI"));
            sheet.addCell(new Label(4, 0, "PSC"));
            sheet.addCell(new Label(5, 0, "NETWORK_TYPE"));
            sheet.addCell(new Label(6, 0, "DBM"));
            sheet.addCell(new Label(7, 0, "LATITUDE"));
            sheet.addCell(new Label(8, 0, "LONGITUDE"));

            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex("ID"));
                    String cl = cursor.getString(cursor.getColumnIndex("CL"));
                    String lac = cursor.getString(cursor.getColumnIndex("LAC"));
                    String rssi = cursor.getString(cursor.getColumnIndex("RSSI"));
                    String psc = cursor.getString(cursor.getColumnIndex("PSC"));
                    String network_type = cursor.getString(cursor.getColumnIndex("NETWORK_TYPE"));
                    String dbm = cursor.getString(cursor.getColumnIndex("DBM"));
                    String lat = cursor.getString(cursor.getColumnIndex("LATITUDE"));
                    String lon = cursor.getString(cursor.getColumnIndex("LONGITUDE"));

                    int i = cursor.getPosition() + 1;
                    sheet.addCell(new Label(0, i, id));
                    sheet.addCell(new Label(1, i, cl));
                    sheet.addCell(new Label(2, i, lac));
                    sheet.addCell(new Label(3, i, rssi));
                    sheet.addCell(new Label(4, i, psc));
                    sheet.addCell(new Label(5, i, network_type));
                    sheet.addCell(new Label(6, i, dbm));
                    sheet.addCell(new Label(7, i, lat));
                    sheet.addCell(new Label(8, i, lon));
                } while (cursor.moveToNext());
            }

            //closing cursor
            cursor.close();
            workbook.write();
            workbook.close();
            Toast.makeText(this,
                    "Data Exported in "+ sd.getAbsolutePath(), Toast.LENGTH_SHORT).show();


        } catch (WriteException e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}
