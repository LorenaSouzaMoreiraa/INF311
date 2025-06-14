package com.example.pratica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class P4Control extends AppCompatActivity implements SensorEventListener {

    public static final String ACTION_CLASSIFY_SENSORS = "com.example.ACTION_CLASSIFY_SENSORS";
    public static final String EXTRA_LUMINOSITY = "EXTRA_LUMINOSITY";
    public static final String EXTRA_PROXIMITY = "EXTRA_PROXIMITY";
    public static final String RESULT_IS_LOW_LIGHT = "RESULT_IS_LOW_LIGHT";
    public static final String RESULT_IS_FAR = "RESULT_IS_FAR";


    // Sensores e variáveis
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor proximitySensor;
    private float currentLuminosity = -1;
    private float currentProximity = -1;

    // Componentes da UI e Helpers
    private SwitchMaterial switchLanterna;
    private SwitchMaterial switchVibracao;
    private Button buttonClassificar;
    private LanternaHelper lanternaHelper;
    private MotorHelper motorHelper;

    // Launcher para iniciar o segundo app e aguardar o resultado
    private ActivityResultLauncher<Intent> classificationLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p4control);

        // ... (inicialização de UI, sensores e helpers) ...
        switchLanterna = findViewById(R.id.lanterna);
        switchVibracao = findViewById(R.id.vibracao);
        buttonClassificar = findViewById(R.id.btClassificar);
        switchLanterna.setClickable(false);
        switchVibracao.setClickable(false);
        lanternaHelper = new LanternaHelper(this);
        motorHelper = new MotorHelper(this);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        // --- PONTO DE VERIFICAÇÃO 2: REGISTRO DO LAUNCHER ---
        // O ActivityResultLauncher é a forma moderna e correta de esperar
        // um resultado de outra activity. O código aqui está pronto para
        // chamar o método 'handleClassificationResult' quando a resposta chegar.
        classificationLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            handleClassificationResult(data);
                        }
                    } else {
                        Toast.makeText(this, "Classificação cancelada.", Toast.LENGTH_SHORT).show();
                    }
                });

        buttonClassificar.setOnClickListener(v -> sendSensorDataForClassification());
    }

    private void sendSensorDataForClassification() {
        // --- PONTO DE VERIFICAÇÃO 3: ENVIO CORRETO DA INTENT ---
        // A Intent é criada com a AÇÃO correta.
        Intent intent = new Intent(ACTION_CLASSIFY_SENSORS);
        // Os dados dos sensores são adicionados usando as CHAVES corretas.
        intent.putExtra(EXTRA_LUMINOSITY, currentLuminosity);
        intent.putExtra(EXTRA_PROXIMITY, currentProximity);

        if (intent.resolveActivity(getPackageManager()) != null) {
            classificationLauncher.launch(intent);
        } else {
            Toast.makeText(this, "App de classificação não encontrado.", Toast.LENGTH_LONG).show();
        }
    }

    private void handleClassificationResult(Intent data) {
        // --- PONTO DE VERIFICAÇÃO 4: RECEBIMENTO CORRETO DO RESULTADO ---
        // O método extrai os booleanos da Intent de resposta usando as CHAVES
        // corretas que o classificador enviou.
        boolean isLowLight = data.getBooleanExtra(RESULT_IS_LOW_LIGHT, false);
        boolean isFar = data.getBooleanExtra(RESULT_IS_FAR, false);

        // Lógica para controlar lanterna e vibração com base na resposta.
        if (isLowLight) {
            lanternaHelper.ligar();
            switchLanterna.setChecked(true);
        } else {
            lanternaHelper.desligar();
            switchLanterna.setChecked(false);
        }

        if (isFar) {
            motorHelper.iniciarVibracao();
            switchVibracao.setChecked(true);
        } else {
            motorHelper.pararVibracao();
            switchVibracao.setChecked(false);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            currentLuminosity = event.values[0];
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            currentProximity = event.values[0];
        }
    }

    // ... (onAccuracyChanged, onResume, onPause, onDestroy) ...
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lanternaHelper.desligar();
        motorHelper.pararVibracao();
    }
}
