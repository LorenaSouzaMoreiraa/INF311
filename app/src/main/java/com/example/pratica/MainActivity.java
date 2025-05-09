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

        final Button bt1= (Button) findViewById(R.id.p1);
        final Button bt2= (Button) findViewById(R.id.p2);
        final Button bt3= (Button) findViewById(R.id.p3);


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
    }
}