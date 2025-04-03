package com.example.pratica1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final Button bt1= (Button) findViewById(R.id.atividade1);
        final Button bt2= (Button) findViewById(R.id.atividade2);


        bt1.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Atividade1.class);
                startActivity(intent);
            }

        });

        bt2.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Atividade2.class);
                startActivity(intent);
            }

        });
    }
}