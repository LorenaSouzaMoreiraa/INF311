package com.example.pratica;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class P3Menu extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    String[] menu = {
            "Minha casa na cidade Natal",  // → Timóteo
            "Minha casa em Viçosa",        // → Viçosa
            "Meu Departamento",            // → DPI/UFV
            "Relatório",
            "Fechar aplicação"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p3menu);

        dbHelper = DBHelper.getInstance(this);
        db = dbHelper.getWritableDatabase();  // writable para inserir no Logs
        logAllLocations();

        ListView listView = findViewById(R.id.list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                menu
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position == menu.length - 1) {
                // Fechar aplicação
                db.close();
                finishAffinity();
            } else if (position == 3) {
                // Relatório
                Intent intent = new Intent(P3Menu.this, Report.class);
                startActivity(intent);
            } else {
                // Persistir log
                addLog(position);

                // Abrir mapa com opção
                Intent intent = new Intent(P3Menu.this, P3Map.class);
                intent.putExtra("option", position);
                startActivity(intent);
            }
        });
    }

    private void addLog(int position) {
        String descricao;

        // Mapear opção para descrição correspondente na tabela Location
        switch (position) {
            case 0:
                descricao = "Minha casa na cidade Natal";
                break;
            case 1:
                descricao = "Minha casa em Viçosa";
                break;
            case 2:
                descricao = "Meu Departamento";
                break;
            default:
                return; // Não registra log para Relatório ou Fechar aplicação
        }

        // Buscar id_location correspondente
        Cursor cursor = db.rawQuery("SELECT id FROM Location WHERE descricao = ?", new String[]{descricao});

        int id_location = -1;
        if (cursor.moveToFirst()) {
            id_location = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        }
        cursor.close();

        if (id_location != -1) {
            ContentValues values = new ContentValues();
            values.put("msg", descricao);

            // Formatando timestamp no padrão: 2025-05-05 21:23:40
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTimestamp = dateTime.format(formatter);

            values.put("timestamp", formattedTimestamp);
            values.put("id_location", id_location);

            db.insert("Logs", null, values);
        }
    }

    private void logAllLocations() {
        Cursor cursor = db.rawQuery("SELECT * FROM Location", null);
        Log.d("P3Map", "==== Listando todas as localizações no banco ====");

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"));
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));

            Log.d("P3Map", "ID: " + id + ", Descricao: " + descricao +
                    ", Latitude: " + latitude + ", Longitude: " + longitude);
        }

        cursor.close();
    }

}
