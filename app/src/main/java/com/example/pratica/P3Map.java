package com.example.pratica;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.Instant;

public class P3Map extends FragmentActivity implements OnMapReadyCallback {

    private static final float ZOOM_LEVEL = 15f;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private GoogleMap map;
    private LatLng selectedLocation;
    private String selectedTitle;
    private Marker atualMarker;
    private FusedLocationProviderClient fusedLocationClient;
    private int option;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p3map);

        dbHelper = DBHelper.getInstance(this);
        db = dbHelper.getReadableDatabase();

        Intent intent = getIntent();
        option = intent.getIntExtra("option", 0);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Button btnLocal = findViewById(R.id.btnLocal);
        btnLocal.setOnClickListener(v -> whereAmI());

        switch (option) {
            case 0:
                selectedTitle = "Minha casa na cidade Natal";
                break;
            case 1:
                selectedTitle = "Minha casa em Viçosa";
                break;
            case 2:
            default:
                selectedTitle = "Meu Departamento";
                break;
        }

        selectedLocation = getLocationFromDB(selectedTitle);

        if (selectedLocation == null) {
            Toast.makeText(this, "Erro: local não encontrado no banco", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Selecionado: " + selectedTitle, Toast.LENGTH_SHORT).show();
        }

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this, "Erro ao carregar o mapa", Toast.LENGTH_SHORT).show();
        }

        Button btnDpi = findViewById(R.id.p1);
        Button btnTimoteo = findViewById(R.id.p2);
        Button btnVicosa = findViewById(R.id.p3);

        btnDpi.setOnClickListener(v -> focusMap(getLocationFromDB("Minha casa na cidade Natal"), "Minha casa na cidade Natal", GoogleMap.MAP_TYPE_TERRAIN));
        btnTimoteo.setOnClickListener(v -> focusMap(getLocationFromDB("Minha casa em Viçosa"), "Minha casa em Viçosa", GoogleMap.MAP_TYPE_SATELLITE));
        btnVicosa.setOnClickListener(v -> focusMap(getLocationFromDB("Meu Departamento"), "Meu Departamento", GoogleMap.MAP_TYPE_HYBRID));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        Cursor cursor = db.rawQuery("SELECT * FROM Location", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"));
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));

            LatLng local = new LatLng(latitude, longitude);
            map.addMarker(new MarkerOptions().position(local).title(descricao));

            if (descricao.equals(selectedTitle)) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(local, ZOOM_LEVEL));
            }
        }

        cursor.close();
    }

    private LatLng getLocationFromDB(String descricao) {
        Cursor cursor = db.rawQuery("SELECT latitude, longitude FROM Location WHERE descricao = ?", new String[]{descricao});

        LatLng result = null;
        if (cursor.moveToFirst()) {
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));
            result = new LatLng(latitude, longitude);
        }

        cursor.close();
        return result;
    }

    private void whereAmI() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null && map != null) {
                        LatLng now = new LatLng(location.getLatitude(), location.getLongitude());

                        if (atualMarker != null) {
                            atualMarker.remove();
                        }

                        atualMarker = map.addMarker(new MarkerOptions()
                                .position(now)
                                .title("Minha localização atual")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(now, ZOOM_LEVEL));

                        // Calcula distância até sua cidade natal (Timóteo)
                        LatLng cidadeNatal = getLocationFromDB("Timóteo");
                        if (cidadeNatal != null) {
                            Location locNow = new Location("");
                            locNow.setLatitude(now.latitude);
                            locNow.setLongitude(now.longitude);

                            Location locNatal = new Location("");
                            locNatal.setLatitude(cidadeNatal.latitude);
                            locNatal.setLongitude(cidadeNatal.longitude);

                            float distancia = locNow.distanceTo(locNatal);

                            String texto = String.format("Distância até Timóteo: %.2f m", distancia);
                            Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void focusMap(LatLng location, String title, int mapType) {
        if (map != null && location != null) {
            map.clear();
            map.setMapType(mapType);

            map.addMarker(new MarkerOptions()
                    .position(location)
                    .title(title)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, ZOOM_LEVEL));
        }
    }
}
