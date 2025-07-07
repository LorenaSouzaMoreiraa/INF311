package com.example.pratica;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class P5Map extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DBHelperP5 dbHelper;
    private SQLiteDatabase db;
    private double currentUserLatitude;
    private double currentUserLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p5map);

        // Recebe a localização atual do usuário passada pela MainActivity
        currentUserLatitude = getIntent().getDoubleExtra("CURRENT_LAT", 0.0);
        currentUserLongitude = getIntent().getDoubleExtra("CURRENT_LNG", 0.0);

        // Inicializa o Helper do Banco de Dados
        dbHelper = DBHelperP5.getInstance(this);
        db = dbHelper.getWritableDatabase();  // writable para inserir no Logs

        // Carrega o fragmento do mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Centraliza o mapa na localização atual do usuário
        LatLng userLocation = new LatLng(currentUserLatitude, currentUserLongitude);
        // O valor 15f é um bom nível de zoom para uma visualização de cidade
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f));

        // Carrega e exibe os marcadores dos check-ins
        loadCheckinMarkers();
    }

    private void loadCheckinMarkers() {
        List<Checkin> checkins = dbHelper.getAllCheckinsForMap();

        for (Checkin checkin : checkins) {
            LatLng markerPosition = new LatLng(checkin.latitude, checkin.longitude);
            String snippet = "Categoria: " + checkin.categoria + " Visitas: " + checkin.qtdVisitas;

            mMap.addMarker(new MarkerOptions()
                    .position(markerPosition)
                    .title(checkin.local)
                    .snippet(snippet));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.p5map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_voltar) {
            finish(); // Fecha a tela atual e volta para a anterior (MainActivity)
            return true;
        } else if (id == R.id.action_mapa_normal) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            return true;
        } else if (id == R.id.action_mapa_hibrido) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            return true;
        }
        // Adicionar lógica para gestão e relatório aqui...

        return super.onOptionsItemSelected(item);
    }
}
