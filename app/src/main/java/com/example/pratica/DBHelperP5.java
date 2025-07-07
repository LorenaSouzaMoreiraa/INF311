package com.example.pratica;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
public class DBHelperP5 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CheckIn.db";
    private static final int DATABASE_VERSION = 1;

    // Nomes das tabelas
    private static final String TABLE_CHECKIN = "Checkin";
    private static final String TABLE_CATEGORIA = "Categoria";

    // Colunas da tabela Categoria
    private static final String KEY_CAT_ID = "idCategoria";
    private static final String KEY_CAT_NOME = "nome";

    // Colunas da tabela Checkin
    private static final String KEY_CHK_LOCAL = "Local";
    private static final String KEY_CHK_QTD_VISITAS = "qtdVisitas";
    private static final String KEY_CHK_CAT_FK = "cat";
    private static final String KEY_CHK_LATITUDE = "latitude";
    private static final String KEY_CHK_LONGITUDE = "longitude";

    private static DBHelperP5 instance;
    public static synchronized DBHelperP5 getInstance(Context context) {
        if (instance == null) {
            // Usa ApplicationContext para evitar memory leak
            instance = new DBHelperP5(context.getApplicationContext());
        }
        return instance;
    }
    private DBHelperP5(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Script para criar a tabela Categoria
        String CREATE_CATEGORIA_TABLE = "CREATE TABLE " + TABLE_CATEGORIA + "("
                + KEY_CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CAT_NOME + " TEXT NOT NULL)";
        db.execSQL(CREATE_CATEGORIA_TABLE);

        // Script para criar a tabela Checkin
        String CREATE_CHECKIN_TABLE = "CREATE TABLE " + TABLE_CHECKIN + "("
                + KEY_CHK_LOCAL + " TEXT PRIMARY KEY,"
                + KEY_CHK_QTD_VISITAS + " INTEGER NOT NULL,"
                + KEY_CHK_CAT_FK + " INTEGER NOT NULL,"
                + KEY_CHK_LATITUDE + " TEXT NOT NULL,"
                + KEY_CHK_LONGITUDE + " TEXT NOT NULL,"
                + "CONSTRAINT fkey0 FOREIGN KEY (" + KEY_CHK_CAT_FK + ") REFERENCES "
                + TABLE_CATEGORIA + "(" + KEY_CAT_ID + "))";
        db.execSQL(CREATE_CHECKIN_TABLE);

        // Insere as categorias iniciais
        populateCategorias(db);
    }

    private void populateCategorias(SQLiteDatabase db) {
        String[] categorias = {"Restaurante", "Bar", "Cinema", "Universidade", "Estádio", "Parque", "Outros"};
        for (String nome : categorias) {
            ContentValues values = new ContentValues();
            values.put(KEY_CAT_NOME, nome);
            db.insert(TABLE_CATEGORIA, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se houver uma atualização do banco, apaga as tabelas antigas e cria de novo
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIA);
        onCreate(db);
    }

    // --- MÉTODOS DE MANIPULAÇÃO DE DADOS ---

    public List<String> getAllCategoryNames() {
        List<String> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Ordena pelo ID para manter a ordem de inserção
        Cursor cursor = db.query(TABLE_CATEGORIA, new String[]{KEY_CAT_NOME}, null, null, null, null, KEY_CAT_ID + " ASC");

        if (cursor.moveToFirst()) {
            do {
                categories.add(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CAT_NOME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }

    public List<String> getAllLocationNames() {
        List<String> locations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CHECKIN, new String[]{KEY_CHK_LOCAL}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                locations.add(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CHK_LOCAL)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return locations;
    }

    public boolean checkInExists(String local) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CHECKIN, new String[]{KEY_CHK_LOCAL}, KEY_CHK_LOCAL + "=?", new String[]{local}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public int getCategoryIdByName(String categoryName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORIA, new String[]{KEY_CAT_ID}, KEY_CAT_NOME + "=?", new String[]{categoryName}, null, null, null);
        if (cursor.moveToFirst()) {
            int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CAT_ID));
            cursor.close();
            db.close();
            return categoryId;
        }
        cursor.close();
        db.close();
        return -1; // Retorna -1 se não encontrar
    }

    public void insertCheckin(String local, int categoryId, String latitude, String longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CHK_LOCAL, local);
        values.put(KEY_CHK_QTD_VISITAS, 1);
        values.put(KEY_CHK_CAT_FK, categoryId);
        values.put(KEY_CHK_LATITUDE, latitude);
        values.put(KEY_CHK_LONGITUDE, longitude);
        db.insert(TABLE_CHECKIN, null, values);
        db.close();
    }

    public void updateCheckin(String local) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Incrementa a quantidade de visitas em 1
        db.execSQL("UPDATE " + TABLE_CHECKIN + " SET " + KEY_CHK_QTD_VISITAS + " = " + KEY_CHK_QTD_VISITAS + " + 1 WHERE " + KEY_CHK_LOCAL + " = ?", new String[]{local});
        db.close();
    }

    public List<Checkin> getAllCheckinsForMap() {
        List<Checkin> checkins = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query SQL que junta as tabelas Checkin e Categoria para pegar o nome da categoria.
        String query = "SELECT T1." + KEY_CHK_LOCAL + ", T1." + KEY_CHK_QTD_VISITAS + ", T1." + KEY_CHK_LATITUDE + ", T1." + KEY_CHK_LONGITUDE + ", T2." + KEY_CAT_NOME
                + " FROM " + TABLE_CHECKIN + " T1"
                + " JOIN " + TABLE_CATEGORIA + " T2 ON T1." + KEY_CHK_CAT_FK + " = T2." + KEY_CAT_ID;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String local = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CHK_LOCAL));
                int qtdVisitas = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CHK_QTD_VISITAS));
                double latitude = Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CHK_LATITUDE)));
                double longitude = Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CHK_LONGITUDE)));
                String categoria = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CAT_NOME));

                checkins.add(new Checkin(local, categoria, qtdVisitas, latitude, longitude));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return checkins;
    }

    public void deleteCheckin(String local) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // A cláusula WHERE especifica qual registro deletar.
            db.delete(TABLE_CHECKIN, KEY_CHK_LOCAL + " = ?", new String[]{local});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
    public List<ReportData> getReportData() {
        List<ReportData> reportList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // A cláusula ORDER BY é a chave para a funcionalidade do relatório.
        String query = "SELECT " + KEY_CHK_LOCAL + ", " + KEY_CHK_QTD_VISITAS
                + " FROM " + TABLE_CHECKIN
                + " ORDER BY " + KEY_CHK_QTD_VISITAS + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String local = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CHK_LOCAL));
                int qtdVisitas = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CHK_QTD_VISITAS));
                reportList.add(new ReportData(local, qtdVisitas));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return reportList;
    }
}