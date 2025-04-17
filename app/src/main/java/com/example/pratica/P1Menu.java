package com.example.pratica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class P1Menu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p1menu);

        final Button bt1= (Button) findViewById(R.id.p1atividade1);
        final Button bt2= (Button) findViewById(R.id.p2atividade2);


        bt1.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(P1Menu.this, P1Atividade1.class);
                startActivity(intent);
            }

        });

        bt2.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(P1Menu.this, P1Atividade2.class);
                startActivity(intent);
            }

        });
    }
}
