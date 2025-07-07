package com.example.pratica;
public class Checkin {
    String local;
    String categoria;
    int qtdVisitas;
    double latitude;
    double longitude;

    public Checkin(String local, String categoria, int qtdVisitas, double latitude, double longitude) {
        this.local = local;
        this.categoria = categoria;
        this.qtdVisitas = qtdVisitas;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}