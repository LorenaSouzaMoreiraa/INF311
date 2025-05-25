package com.example.pratica;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "localizacao.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_LOCATION = "Location";
    public static final String TABLE_LOGS = "Logs";

    // Singleton
    private static DBHelper instance;

    // Método para obter a instância Singleton
    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            // Usa ApplicationContext para evitar memory leak
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    // Construtor privado para impedir múltiplas instâncias
    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criar tabela Location
        String createLocationTable = "CREATE TABLE IF NOT EXISTS " + TABLE_LOCATION + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descricao TEXT," +
                "latitude REAL," +
                "longitude REAL" +
                ")";
        db.execSQL(createLocationTable);

        // Criar tabela Logs
        String createLogsTable = "CREATE TABLE IF NOT EXISTS " + TABLE_LOGS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "msg TEXT," +
                "timestamp TEXT," +
                "id_location INTEGER," +
                "FOREIGN KEY(id_location) REFERENCES " + TABLE_LOCATION + "(id)" +
                ")";
        db.execSQL(createLogsTable);

        // Inserir dados iniciais na tabela Location
        insertLocation(db, "Minha casa na cidade Natal", -19.538804784456225, -42.65005170934301);
        insertLocation(db, "Minha casa em Viçosa", -20.753416844031698, -42.88183255971028);
        insertLocation(db, "Meu Departamento", -20.764924106730916, -42.86830006674537);
    }

    private void insertLocation(SQLiteDatabase db, String descricao, double latitude, double longitude) {
        ContentValues values = new ContentValues();
        values.put("descricao", descricao);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        db.insert(TABLE_LOCATION, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se precisar atualizar o banco no futuro
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        onCreate(db);
    }
}
