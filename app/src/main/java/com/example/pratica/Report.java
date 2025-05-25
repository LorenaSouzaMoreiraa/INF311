package com.example.pratica;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Report extends ListActivity {

    private DBHelper dbHelper;
    private ArrayList<Integer> logIds;  // Armazena os IDs dos Logs
    private ArrayList<String> items;    // Armazena as Strings para exibição

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        DBHelper dbHelper = DBHelper.getInstance(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        logIds = new ArrayList<>();
        items = new ArrayList<>();

        String query = "SELECT Logs.id, Logs.msg, Logs.timestamp " +
                "FROM Logs INNER JOIN Location ON Logs.id_location = Location.id " +
                "ORDER BY Logs.timestamp DESC";

        try (Cursor cursor = db.rawQuery(query, null)) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String msg = cursor.getString(cursor.getColumnIndexOrThrow("msg"));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));

                logIds.add(id);
                items.add(msg + " - " + timestamp);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                items
        );

        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        int logId = logIds.get(position);

        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {

            String query = "SELECT Location.latitude, Location.longitude " +
                    "FROM Logs INNER JOIN Location ON Logs.id_location = Location.id " +
                    "WHERE Logs.id = ?";

            try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(logId)})) {
                if (cursor.moveToFirst()) {
                    double lat = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
                    double lon = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));

                    String message = String.format("Latitude: %.6f, Longitude: %.6f", lat, lon);
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
