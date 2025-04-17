package com.example.pratica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

public class P2Form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p2form);

        // Referências dos elementos
        Button btcal = findViewById(R.id.btCalcular);

        EditText input_nome = findViewById(R.id.nome);
        EditText input_idade = findViewById(R.id.idade);
        EditText input_peso = findViewById(R.id.peso);      // Corrigido: era TextView
        EditText input_altura = findViewById(R.id.altura);  // Corrigido: era TextView

        btcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Coleta dos dados
                    String nome = input_nome.getText().toString().trim();
                    int idade = Integer.parseInt(input_idade.getText().toString());
                    double peso = Double.parseDouble(input_peso.getText().toString());
                    double altura = Double.parseDouble(input_altura.getText().toString());

                    // Validação
                    if (nome.isEmpty()) {
                        Toast.makeText(P2Form.this, "Preencha o nome", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (idade < 0 || altura < 0 || peso < 0) {
                        throw new NumberFormatException();
                    }

                    Intent intent = new Intent(P2Form.this, P2Sheet.class);
                    intent.putExtra("nome", nome);
                    intent.putExtra("idade", idade);
                    intent.putExtra("peso", peso);
                    intent.putExtra("altura", altura);

                    //Toast.makeText(P2Form.this, "peso: " + peso + ", altura: " + altura + "idade:" + idade, Toast.LENGTH_LONG).show();

                    startActivity(intent);

                } catch (NumberFormatException e) {
                    Toast.makeText(P2Form.this, "Por favor, insira dados válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CICLO", "onResume da P2Form");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CICLO", "onPause da P2Form");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CICLO", "onStop da P2Form");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("CICLO", "onRestart da P2Form");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CICLO", "onDestroy da P2Form");
    }
}
