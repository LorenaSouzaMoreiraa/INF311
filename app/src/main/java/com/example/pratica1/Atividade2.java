package com.example.pratica1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Atividade2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atividade2);

        Button btsum = (Button) findViewById(R.id.sum);
        Button btminus = (Button) findViewById(R.id.minus);
        Button btmultiply = (Button) findViewById(R.id.multiply);
        Button btdivide = (Button) findViewById(R.id.divide);
        Button btdot = (Button) findViewById(R.id.dot);
        Button btclear = (Button) findViewById(R.id.clear);
        Button btbackspace = (Button) findViewById(R.id.backspace);
        Button btequal = (Button) findViewById(R.id.equal);

        EditText visor = (EditText) findViewById(R.id.visor);

        Button bt9 = (Button) findViewById(R.id.number9);
        Button bt8 = (Button) findViewById(R.id.number8);
        Button bt7 = (Button) findViewById(R.id.number7);
        Button bt6 = (Button) findViewById(R.id.number6);
        Button bt5 = (Button) findViewById(R.id.number5);
        Button bt4 = (Button) findViewById(R.id.number4);
        Button bt3 = (Button) findViewById(R.id.number3);
        Button bt2 = (Button) findViewById(R.id.number2);
        Button bt1 = (Button) findViewById(R.id.number1);
        Button bt0 = (Button) findViewById(R.id.number0);

        bt0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("0");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("1");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("2");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("3");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("4");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("5");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("6");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("7");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("8");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("9");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btsum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("1");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btminus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("1");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btdivide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("1");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btmultiply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("1");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btbackspace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("1");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btdot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("1");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btclear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("1");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btequal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    visor.append("1");
                } catch (NumberFormatException e) {
                    Toast.makeText(Atividade2.this, "Por favor, insira números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}