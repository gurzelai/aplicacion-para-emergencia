package com.gurzelai.aplicacionparaemergencia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Configuracion extends AppCompatActivity {

    Button btnTema, btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        setTitle("Configuracion");

        btnTema = findViewById(R.id.btnTema);
        btnTema.setOnClickListener(view -> cambiarTema());
        btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(v ->finish());
    }

    public void cambiarTema() {
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;
        }
    }
}