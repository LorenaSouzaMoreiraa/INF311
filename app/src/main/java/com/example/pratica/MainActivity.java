package com.example.pratica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        DBHelper.getInstance(this).getWritableDatabase();

        final Button bt1= (Button) findViewById(R.id.p1);
        final Button bt2= (Button) findViewById(R.id.p2);
        final Button bt3= (Button) findViewById(R.id.p3);
        final Button bt4= (Button) findViewById(R.id.p4);
        final Button bt5= (Button) findViewById(R.id.p5);


        bt1.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, P1Menu.class);
                startActivity(intent);
            }

        });

        bt2.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, P2Form.class);
                startActivity(intent);
            }

        });

        bt3.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, P3Menu.class);
                startActivity(intent);
            }

        });

        bt4.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, P4Control.class);
                startActivity(intent);
            }

        });

        bt5.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, P5Form.class);
                startActivity(intent);
            }

        });
    }
}