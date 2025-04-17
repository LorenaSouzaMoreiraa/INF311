package com.example.pratica;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class P1Atividade2 extends AppCompatActivity {
    private static final double EPSILON = 1e-10; // Margem segura para considerar zero

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p1atividade2);

        Button btsum = (Button) findViewById(R.id.sum);
        Button btminus = (Button) findViewById(R.id.minus);
        Button btmultiply = (Button) findViewById(R.id.multiply);
        Button btdivide = (Button) findViewById(R.id.divide);
        Button btdot = (Button) findViewById(R.id.dot);
        Button btclear = (Button) findViewById(R.id.clear);
        Button btbackspace = (Button) findViewById(R.id.backspace);
        Button btequal = (Button) findViewById(R.id.equal);

        EditText visor = (EditText) findViewById(R.id.visor);
        final Boolean[] result = {false};

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
                    String text = visor.getText().toString();
                    if(text.equals("ERROR")){
                        visor.setText("0");
                        return;
                    }
                    // bloqueia '00'
                    else if (text.isEmpty() || !text.equals("0")) {
                        if(result[0]) {
                            result[0] = false;
                            visor.setText("0");
                            return;
                        }else
                            visor.append("0");
                    }
                } catch (NumberFormatException e) {
                   visor.setText("ERROR");
                }
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String text = visor.getText().toString();
                    if(text.equals("ERROR")) visor.setText("1");
                    // bloqueia '01'
                    else if (text.isEmpty() || !text.equals("0")) {
                        if(result[0]) {
                            result[0] = false;
                            visor.setText("1");
                        }else
                            visor.append("1");
                    }else{
                        String[] parts = text.split("[+\\-*/]");
                        String lastPart = parts[parts.length - 1]; // Último número da expressão

                        if (lastPart.equals("0")) {
                            // Substitui "0" por "1" para evitar "01"
                            visor.setText(text.substring(0, text.length() - 1) + "1");
                        } else {
                            visor.append("1");
                        }
                    }
                } catch (NumberFormatException e) {
                   visor.setText("ERROR");
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String text = visor.getText().toString();
                    if(text.equals("ERROR")) visor.setText("2");
                    else if (text.isEmpty() || !text.equals("0")) {
                        if(result[0]) {
                            result[0] = false;
                            visor.setText("2");
                        }else
                            visor.append("2");
                    }else{
                        String[] parts = text.split("[+\\-*/]");
                        String lastPart = parts[parts.length - 1]; // Último número da expressão

                        if (lastPart.equals("0")) {
                            visor.setText(text.substring(0, text.length() - 1) + "2");
                        } else {
                            visor.append("2");
                        }
                    }
                } catch (NumberFormatException e) {
                   visor.setText("ERROR");
                }
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String text = visor.getText().toString();
                    if(text.equals("ERROR")) visor.setText("3");
                    else if (text.isEmpty() || !text.equals("0")) {
                        if(result[0]) {
                            result[0] = false;
                            visor.setText("3");
                        }else
                            visor.append("3");
                    }else{
                        String[] parts = text.split("[+\\-*/]");
                        String lastPart = parts[parts.length - 1]; // Último número da expressão

                        if (lastPart.equals("0")) {
                            visor.setText(text.substring(0, text.length() - 1) + "3");
                        } else {
                            visor.append("3");
                        }
                    }
                } catch (NumberFormatException e) {
                   visor.setText("ERROR");
                }
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String text = visor.getText().toString();
                    if(text.equals("ERROR")) visor.setText("4");
                    else if (text.isEmpty() || !text.equals("0")) {
                        if(result[0]) {
                            result[0] = false;
                            visor.setText("4");
                        }else
                            visor.append("4");
                    }else{
                        String[] parts = text.split("[+\\-*/]");
                        String lastPart = parts[parts.length - 1]; // Último número da expressão

                        if (lastPart.equals("0")) {
                            visor.setText(text.substring(0, text.length() - 1) + "2");
                        } else {
                            visor.append("4");
                        }
                    }
                } catch (NumberFormatException e) {
                   visor.setText("ERROR");
                }
            }
        });

        bt5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String text = visor.getText().toString();
                    if(text.equals("ERROR")) visor.setText("5");
                    else if (text.isEmpty() || !text.equals("0")) {
                        if(result[0]) {
                            result[0] = false;
                            visor.setText("5");
                        }else
                            visor.append("5");
                    }else{
                        String[] parts = text.split("[+\\-*/]");
                        String lastPart = parts[parts.length - 1]; // Último número da expressão

                        if (lastPart.equals("0")) {
                            visor.setText(text.substring(0, text.length() - 1) + "5");
                        } else {
                            visor.append("5");
                        }
                    }
                } catch (NumberFormatException e) {
                   visor.setText("ERROR");
                }
            }
        });

        bt6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String text = visor.getText().toString();
                    if(text.equals("ERROR")) visor.setText("6");
                    else if (text.isEmpty() || !text.equals("0")) {
                        if(result[0]) {
                            result[0] = false;
                            visor.setText("6");
                        }else
                            visor.append("6");
                    }else{
                        String[] parts = text.split("[+\\-*/]");
                        String lastPart = parts[parts.length - 1]; // Último número da expressão

                        if (lastPart.equals("0")) {
                            visor.setText(text.substring(0, text.length() - 1) + "6");
                        } else {
                            visor.append("6");
                        }
                    }
                } catch (NumberFormatException e) {
                   visor.setText("ERROR");
                }
            }
        });

        bt7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String text = visor.getText().toString();
                    if(text.equals("ERROR")) visor.setText("7");
                    else if (text.isEmpty() || !text.equals("0")) {
                        if(result[0]) {
                            result[0] = false;
                            visor.setText("7");
                        }else
                            visor.append("7");
                    }else{
                        String[] parts = text.split("[+\\-*/]");
                        String lastPart = parts[parts.length - 1]; // Último número da expressão

                        if (lastPart.equals("0")) {
                            visor.setText(text.substring(0, text.length() - 1) + "7");
                        } else {
                            visor.append("7");
                        }
                    }
                } catch (NumberFormatException e) {
                   visor.setText("ERROR");
                }
            }
        });

        bt8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String text = visor.getText().toString();
                    if(text.equals("ERROR")) visor.setText("8");
                    else if (text.isEmpty() || !text.equals("0")) {
                        if(result[0]) {
                            result[0] = false;
                            visor.setText("8");
                        }else
                            visor.append("8");
                    }else{
                        String[] parts = text.split("[+\\-*/]");
                        String lastPart = parts[parts.length - 1]; // Último número da expressão

                        if (lastPart.equals("0")) {
                            visor.setText(text.substring(0, text.length() - 1) + "8");
                        } else {
                            visor.append("8");
                        }
                    }
                } catch (NumberFormatException e) {
                   visor.setText("ERROR");
                }
            }
        });

        bt9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String text = visor.getText().toString();
                    if(text.equals("ERROR")) visor.setText("9");
                    else if (text.isEmpty() || !text.equals("0") ) {
                        if(result[0]) {
                            result[0] = false;
                            visor.setText("9");
                        }else
                            visor.append("9");
                    }else{
                        String[] parts = text.split("[+\\-*/]");
                        String lastPart = parts[parts.length - 1]; // Último número da expressão

                        if (lastPart.equals("0")) {
                            visor.setText(text.substring(0, text.length() - 1) + "9");
                        } else {
                            visor.append("9");
                        }
                    }
                } catch (NumberFormatException e) {
                   visor.setText("ERROR");
                }
            }
        });

        btsum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String text = visor.getText().toString();
                    if (result[0]) result[0] = false;
                    if (!text.endsWith("+") && !text.endsWith("-") && !text.endsWith("*") && !text.endsWith("/") && !text.endsWith(".")) {
                        visor.append("+");
                    }else throw new NumberFormatException();
                } catch (NumberFormatException e) {
                   visor.setText("ERROR");
                }
            }
        });

        btminus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String text = visor.getText().toString();
                    if (result[0]) result[0] = false;
                    if (!text.endsWith("+") && !text.endsWith("-") && !text.endsWith("*") && !text.endsWith("/") && !text.endsWith(".")) {
                        visor.append("-");
                    }else throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    visor.setText("ERROR");
                }
            }
        });

        btdivide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String text = visor.getText().toString();
                    if (result[0]) result[0] = false;
                    if (!text.isEmpty() && !text.endsWith("+") && !text.endsWith("-") && !text.endsWith("*") && !text.endsWith("/") && !text.endsWith(".")) {
                        visor.append("/");
                    }else throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    visor.setText("ERROR");
                }
            }
        });

        btmultiply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String text = visor.getText().toString();
                    if (result[0]) result[0] = false;
                    if (!text.isEmpty() && !text.endsWith("+") && !text.endsWith("-") && !text.endsWith("*") && !text.endsWith("/") && !text.endsWith(".")) {
                        visor.append("*");
                    }else throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    visor.setText("ERROR");
                }
            }
        });

        // TODO: add operacoes visor
        btbackspace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String text = visor.getText().toString();
                    if (result[0]) result[0] = false;
                    if(text.equals("ERROR")){
                        visor.setText("");
                    }else if(text.length() > 0){
                        visor.setText(text.substring(0, text.length()-1));
                    }else{
                        visor.setText("0");
                    }

                } catch (NumberFormatException e) {
                   visor.setText("ERROR");
                }
            }
        });

        btdot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (result[0]) result[0] = false;

                    String text = visor.getText().toString();
                    String[] parts = text.split("[+\\-*/]");
                    String end = String.valueOf(text.charAt(text.length() - 1));

                    //Toast.makeText(Atividade2.this, end, Toast.LENGTH_SHORT).show();

                    // Verifica se o último número contém apenas dígitos e não possui ponto decimal ainda
                    if (end.matches("\\d+") && !parts[parts.length-1].contains(".")) {
                        visor.append(".");
                    } else {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                   visor.setText("ERROR");
                }
            }
        });

        btclear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (result[0]) result[0] = false;
                    visor.setText("");
                } catch (NumberFormatException e) {
                   visor.setText("ERROR");
                }
            }
        });
        // TODO: bug calc -1-1 = -2 -1 =-3!!! -> multiplos valores negatiovs seguidos
        btequal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (result[0]) result[0] = false;
                    String expression = visor.getText().toString().trim();

                    if (expression.isEmpty() || expression.matches(".*[+\\-*/]$"))
                        throw new NumberFormatException();

                    // Avalia a expressão e exibe o resultado
                    double calc = evaluateExpression(expression);
                    visor.setText(String.valueOf(calc));
                    result[0] = true;

                } catch (NumberFormatException e) {
                   visor.setText("ERROR");
                }
            }
        });
    }

    // Converte expressão infixa para pós-fixa (RPN)
    private static List<String> infixToPostfix(String expression) {
        List<String> output = new ArrayList<>();
        Stack<Character> operators = new Stack<>();
        StringBuilder number = new StringBuilder();
        char prevChar = ' ';

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch) || ch == '.') {
                number.append(ch);
            } else if (ch == '-' && (i == 0 || "+-*/".indexOf(prevChar) >= 0)) {
                // Trata número negativo no início ou após um operador
                number.append(ch);
            } else {
                if (number.length() > 0) {
                    output.add(number.toString());
                    number.setLength(0);
                }

                if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                    while (!operators.isEmpty() && precedence.get(operators.peek()) >= precedence.get(ch)) {
                        output.add(String.valueOf(operators.pop()));
                    }
                    operators.push(ch);
                }
            }
            prevChar = ch;
        }

        if (number.length() > 0) {
            output.add(number.toString());
        }
        while (!operators.isEmpty()) {
            output.add(String.valueOf(operators.pop()));
        }
        return output;
    }
    // Avalia expressão pós-fixa
    private static double evaluatePostfix(List<String> postfix) {
        Stack<Double> stack = new Stack<>();

        for (String token : postfix) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token)); // Número
            } else {
                double b = stack.pop();
                double a = stack.pop();
                switch (token.charAt(0)) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/':
                        Log.d("CALC_DEBUG", "a = " + a + ", b = " + b); // imprime no Logcat
                        if (Math.abs(b) < EPSILON) throw new ArithmeticException("Divisão por zero!");
                        stack.push(a / b);
                        break;
                }
            }
        }
        return stack.pop();
    }

    // Função principal que avalia uma expressão matemática
    public static double evaluateExpression(String expression) {
        List<String> postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }

    // Mapa de precedência dos operadores
    private static final Map<Character, Integer> precedence = new HashMap<>();
    static {
        precedence.put('+', 1);
        precedence.put('-', 1);
        precedence.put('*', 2);
        precedence.put('/', 2);
    }


}