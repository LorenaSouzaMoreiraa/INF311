package com.example.pratica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class P3Map extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p3map);

        Intent intent = getIntent();
        int option = intent.getIntExtra("option",0);
        Toast.makeText(P3Map.this, String.valueOf(option), Toast.LENGTH_SHORT).show();
        }
    }
