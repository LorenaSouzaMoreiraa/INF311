package com.example.pratica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.graphics.Typeface;
import android.util.Log;


public class P2Sheet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p2sheet);

        Button btcal = findViewById(R.id.btRecalcular);

        btcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });

        // Recuperando dados da Intent
        Intent intent = getIntent();
        String nome = intent.getStringExtra("nome");
        int idade = intent.getIntExtra("idade", 0);
        double peso = intent.getDoubleExtra("peso", 0.0);
        double altura = intent.getDoubleExtra("altura", 0.0);

        Toast.makeText(this, "peso: " + peso + ", altura: " + altura + "idade:" + altura, Toast.LENGTH_LONG).show();

        // Definindo as TextViews para exibição
        TextView nomeTxt = findViewById(R.id.resultadoNome);
        TextView idadeTxt = findViewById(R.id.resultadoIdade);
        TextView pesoTxt = findViewById(R.id.resultadoPeso);
        TextView alturaTxt = findViewById(R.id.resultadoAltura);
        TextView imcTxt = findViewById(R.id.resultadoIMC);
        TextView classificacaoTxt = findViewById(R.id.resultadoClassificacao);

        // Calculando IMC
        double imcVal = peso / (altura * altura);
        String classificacao = "";
        if (imcVal < 18.5) classificacao = "Abaixo do peso";
        else if (imcVal < 25) classificacao = "Saudável";
        else if (imcVal < 30) classificacao = "Sobrepeso";
        else if (imcVal < 35) classificacao = "Obesidade Grau I";
        else if (imcVal < 40) classificacao = "Obesidade Grau II";
        else classificacao = "Obesidade Grau III";

        setSpannable(nomeTxt, "Nome: ", nome);
        setSpannable(idadeTxt, "Idade: ", String.format("%d anos", idade)); // Corrigido
        setSpannable(pesoTxt, "Peso (Kg): ", String.format("%.2f Kg", peso));
        setSpannable(alturaTxt, "Altura (m): ", String.format("%.2f m", altura));
        setSpannable(imcTxt, "IMC: ", String.format("%.2f kg/m²", imcVal));
        setSpannable(classificacaoTxt, "Classificação: ", classificacao);

    }

    private void setSpannable(TextView textView, String label, String value) {
        SpannableString spannable = new SpannableString(label + value);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, label.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CICLO", "onResume da P2Sheet");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CICLO", "onPause da P2Sheet");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CICLO", "onStop da P2Sheet");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("CICLO", "onRestart da P2Sheet");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CICLO", "onDestroy da P2Sheet");
    }
}
