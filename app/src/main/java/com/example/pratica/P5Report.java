package com.example.pratica;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class P5Report extends AppCompatActivity {

    private DBHelperP5 dbHelper;
    private SQLiteDatabase db;
    private LinearLayout layoutLocais;
    private LinearLayout layoutVisitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p5report);

        dbHelper = DBHelperP5.getInstance(this);
        db = dbHelper.getWritableDatabase();  // writable para inserir no Logs
        layoutLocais = findViewById(R.id.layoutLocais);
        layoutVisitas = findViewById(R.id.layoutVisitas);

        populateReportList();
    }

    private void populateReportList() {
        // Busca os dados já ordenados do banco
        List<ReportData> reportDataList = dbHelper.getReportData();

        for (ReportData data : reportDataList) {
            // Cria e adiciona o TextView para o nome do local
            TextView localTextView = createTextView(data.local, Gravity.START);
            layoutLocais.addView(localTextView);

            // Cria e adiciona o TextView para a quantidade de visitas
            TextView visitasTextView = createTextView(String.valueOf(data.qtdVisitas), Gravity.END);
            layoutVisitas.addView(visitasTextView);
        }
    }

    private TextView createTextView(String text, int gravity) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        textView.setTextSize(18f);
        textView.setPadding(8, 16, 8, 16);
        textView.setGravity(gravity); // Alinha o texto (esquerda para nomes, direita para números)
        return textView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.p5report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_voltar_report) {
            finish(); // Fecha a tela atual e volta para a anterior
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
