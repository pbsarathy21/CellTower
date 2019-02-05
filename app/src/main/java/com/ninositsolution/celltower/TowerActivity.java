package com.ninositsolution.celltower;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
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
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tower);
        binding.setTower(new TowerVM(getApplicationContext(), TowerActivity.this, this));

        LocationUpdate locationUpdate = new LocationUpdate(this);
        session = new Session(this);

    }


        @Override
    public void setRecyclerViewAdapter(TowerAdapter towerAdapter) {

        if (binding.recyclerView.getVisibility() == View.GONE)
            binding.recyclerView.setVisibility(View.VISIBLE);

        if (binding.emptyList.getVisibility() == View.VISIBLE)
            binding.emptyList.setVisibility(View.GONE);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(towerAdapter);
    }

    @Override
    public void listEmpty() {
        binding.recyclerView.setVisibility(View.GONE);
        binding.emptyList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onExportClicked() {

        databaseHelper = new DatabaseHelper(this);
        final Cursor cursor = databaseHelper.getAllData();

        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "List is empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            int count = session.getFile_count();

            count++;

            session.setFile_count(count++);

            String newFile = "mySignal"+count+".xls";

            File sd = Environment.getExternalStorageDirectory();
            //String csvFile = "mySignal.xls";

            File directory = new File(sd.getAbsolutePath());
            //create directory if not exist
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }

            try {
                //file path
                File file = new File(directory, newFile);
                WorkbookSettings wbSettings = new WorkbookSettings();
                wbSettings.setLocale(new Locale("en", "EN"));
                WritableWorkbook workbook;
                workbook = Workbook.createWorkbook(file, wbSettings);
                //Excel sheet name. 0 represents first sheet
                WritableSheet sheet = workbook.createSheet("towersList", 0);
                // column and row
          /*  sheet.addCell(new Label(0, 0, "ID"));
            sheet.addCell(new Label(1, 0, "CL"));
            sheet.addCell(new Label(2, 0, "LAC"));
            sheet.addCell(new Label(3, 0, "RSSI"));
            sheet.addCell(new Label(4, 0, "PSC"));
            sheet.addCell(new Label(5, 0, "NETWORK_TYPE"));
            sheet.addCell(new Label(6, 0, "DBM"));
            sheet.addCell(new Label(7, 0, "LATITUDE"));
            sheet.addCell(new Label(8, 0, "LONGITUDE"));*/

                sheet.addCell(new Label(0, 0, "Latitude"));
                sheet.addCell(new Label(1, 0, "Longitude"));

                int last_col = cursor.getCount()+2;

                sheet.addCell(new Label(last_col, 0, "Net Power(mW)"));


                for (int i = 2; i<cursor.getCount()+2; i++)
                {
                    String Tower = "Tower "+ (i-1);
                    sheet.addCell(new Label(i, 0, Tower));
                }

                if (cursor.moveToFirst())
                {
                    do {
                        String type = cursor.getString(cursor.getColumnIndex("TYPE"));

                        int i = cursor.getPosition() + 2;
                        sheet.addCell(new Label(i, 0, type));
                    } while (cursor.moveToNext());
                }

                if (cursor.moveToFirst())
                {
                    do {
                        String type = cursor.getString(cursor.getColumnIndex("POWER"));

                        int i = cursor.getPosition() + 2;
                        sheet.addCell(new Label(i, 1, type));
                    } while (cursor.moveToNext());
                }

                double wattSum = 0;

                if (cursor.moveToFirst())
                {
                    do {
                        String watt = cursor.getString(cursor.getColumnIndex("WATT"));

                        int i = cursor.getPosition();

                        double watts = Double.parseDouble(watt);

                        wattSum = wattSum + watts;

                    } while (cursor.moveToNext());
                }

                String Total = String.valueOf(wattSum);
                String Total1 = Total.substring(0,4);
                String Total2 = Total.substring(Total.lastIndexOf("E")+1);
                String Total_value = Total1 + " * 10^(" + Total2 + ")";

                sheet.addCell(new Label(last_col, 1, Total_value));

                String lat = session.getLatitude_key();
                String lon = session.getLongitude_key();

                sheet.addCell(new Label(0, 1, lat));
                sheet.addCell(new Label(1, 1, lon));


   /*         if (cursor.moveToFirst()) {
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
*/
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

            session.setLatitude_key("NA");
            session.setLongitude_key("NA");
        }


    }

    @Override
    public void onRefreshingLists() {

        Toast.makeText(this, "Updating...", Toast.LENGTH_SHORT).show();

    }
}
