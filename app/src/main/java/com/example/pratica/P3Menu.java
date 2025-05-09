package com.example.pratica;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class P3Menu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p3menu);

        ListView listView = findViewById(R.id.list);

        String[] menu = {
                "Minha casa na cidade Natal",
                "Minha casa em Viçosa",
                "Meu Departamento",
                "Minha casa em Viçosa"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                menu
        );

        listView.setAdapter(adapter);
        // Clique nos itens
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(P3Menu.this, P3Map.class);
            intent.putExtra("option", position);
            startActivity(intent);
        });
    }
}
