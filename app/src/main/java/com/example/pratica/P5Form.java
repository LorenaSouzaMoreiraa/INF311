package com.example.pratica;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;

public class P5Form extends AppCompatActivity implements LocationListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    // Componentes da UI
    private AutoCompleteTextView autoCompleteLocal;
    private Spinner spinnerCategoria;
    private TextView textLatitude;
    private TextView textLongitude;
    private Button buttonCheckin;

    // Banco de Dados
    private DBHelperP5 dbHelper;
    private SQLiteDatabase db;

    // Localização
    private LocationManager locationManager;
    private String currentLatitude = "";
    private String currentLongitude = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p5form);

        // Inicializa o Helper do Banco de Dados
        dbHelper = DBHelperP5.getInstance(this);
        db = dbHelper.getWritableDatabase();  // writable para inserir no Logs

        // Referencia os componentes da UI
        autoCompleteLocal = findViewById(R.id.autoCompleteLocal);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        textLatitude = findViewById(R.id.textLatitude);
        textLongitude = findViewById(R.id.textLongitude);
        buttonCheckin = findViewById(R.id.buttonCheckin);

        // Popula os componentes com dados do banco
        populateAutoComplete();
        populateSpinner();

        // Configura o serviço de localização
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        requestLocationUpdates();

        // Configura o listener do botão de check-in
        buttonCheckin.setOnClickListener(v -> handleCheckin());
    }

    private void populateAutoComplete() {
        List<String> locations = dbHelper.getAllLocationNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, locations);
        autoCompleteLocal.setAdapter(adapter);
    }

    private void populateSpinner() {
        List<String> categories = dbHelper.getAllCategoryNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
    }

    private void requestLocationUpdates() {
        // Verifica se a permissão de localização foi concedida
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Se não, solicita a permissão ao usuário
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        // Se a permissão foi concedida, começa a escutar por atualizações de localização
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        // Método chamado sempre que uma nova localização é obtida
        currentLatitude = String.valueOf(location.getLatitude());
        currentLongitude = String.valueOf(location.getLongitude());

        // Atualiza os TextViews na tela
        textLatitude.setText(currentLatitude);
        textLongitude.setText(currentLongitude);
    }

    private void handleCheckin() {
        String local = autoCompleteLocal.getText().toString().trim();

        // Validação: Verifica se o nome do local foi preenchido
        if (TextUtils.isEmpty(local)) {
            Toast.makeText(this, "Por favor, informe o nome do local.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se o local já existe no banco de dados
        if (dbHelper.checkInExists(local)) {
            // Se existe, atualiza o contador de visitas
            dbHelper.updateCheckin(local);
            Toast.makeText(this, "Check-in atualizado para '" + local + "'!", Toast.LENGTH_SHORT).show();
        } else {
            // Se não existe, faz um novo check-in
            // Validação: Verifica se a localização foi obtida
            if (currentLatitude.isEmpty() || currentLongitude.isEmpty()) {
                Toast.makeText(this, "Aguardando obter a localização...", Toast.LENGTH_SHORT).show();
                return;
            }
            String categoria = spinnerCategoria.getSelectedItem().toString();
            int categoryId = dbHelper.getCategoryIdByName(categoria);

            dbHelper.insertCheckin(local, categoryId, currentLatitude, currentLongitude);
            Toast.makeText(this, "Novo check-in realizado em '" + local + "'!", Toast.LENGTH_SHORT).show();
        }

        // Reinicia a Activity para atualizar a lista do AutoComplete, como solicitado
        recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.p5menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_mapa) {
            // Verifica se a localização já foi obtida
            if (currentLatitude.isEmpty() || currentLongitude.isEmpty()) {
                Toast.makeText(this, "Aguardando obter a localização para abrir o mapa.", Toast.LENGTH_SHORT).show();
                return true; // Impede a navegação
            }

            // Se a localização foi obtida, cria a Intent para a MapActivity
            Intent intent = new Intent(this, P5Map.class);
            // Passa a latitude e longitude como extras
            intent.putExtra("CURRENT_LAT", Double.parseDouble(currentLatitude));
            intent.putExtra("CURRENT_LNG", Double.parseDouble(currentLongitude));
            startActivity(intent);

            return true;
        } else if (id == R.id.action_gestao) {
            Intent intent = new Intent(this, P5Gestao.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_relatorio) {
            Intent intent = new Intent(this, P5Report.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, tenta obter a localização novamente
                requestLocationUpdates();
            } else {
                // Permissão negada
                Toast.makeText(this, "Permissão de localização é necessária para fazer check-in.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
