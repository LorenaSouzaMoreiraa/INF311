package com.example.pratica1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Atividade1 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atividade1);

        Button btsum1= (Button) findViewById(R.id.sum1);
        Button btminus1= (Button) findViewById(R.id.minus1);
        Button btmultiply1= (Button) findViewById(R.id.multiply1);
        Button btdivide1 = (Button) findViewById(R.id.divide1);

        EditText input1 = (EditText) findViewById(R.id.value1);
        EditText input2 = (EditText) findViewById(R.id.value2);
        TextView result = (TextView) findViewById(R.id.result1);


        btsum1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Captura os valores inseridos
                    double num1 = Double.parseDouble(input1.getText().toString());
                    double num2 = Double.parseDouble(input2.getText().toString());

                    // Realiza a soma
                    double calc = num1 + num2;
                    result.setText("O resultado é: " + calc); // Exibe o resultado
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade1.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Ação para o botão de subtração
        btminus1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    // Captura os valores inseridos
                    double num1 = Double.parseDouble(input1.getText().toString());
                    double num2 = Double.parseDouble(input2.getText().toString());

                    // Realiza a subtração
                    double cal = num1 - num2;
                    result.setText("O resultado é: "  + cal); // Exibe o resultado
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade1.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Ação para o botão de multiplicação
        btmultiply1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    // Captura os valores inseridos
                    double num1 = Double.parseDouble(input1.getText().toString());
                    double num2 = Double.parseDouble(input2.getText().toString());

                    // Realiza a multiplicação
                    double cal = num1 * num2;
                    result.setText("O resultado é: "  + cal); // Exibe o resultado
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade1.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Ação para o botão de divisão
        btdivide1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    // Captura os valores inseridos
                    double num1 = Double.parseDouble(input1.getText().toString());
                    double num2 = Double.parseDouble(input2.getText().toString());

                    // Verifica se a divisão por zero foi tentada
                    if (num2 == 0) {
                        Toast.makeText(Atividade1.this, "Não é possível dividir por ZERO", Toast.LENGTH_SHORT).show();
                    } else {
                        // Realiza a divisão
                        double calc = num1 / num2;
                        result.setText("O resultado é: "  + calc); // Exibe o resultado
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade1.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Fecha a atividade e volta para a anterior
        return true;
    }
}