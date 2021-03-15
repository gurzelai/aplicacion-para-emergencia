package com.gurzelai.aplicacionparaemergencia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.Button;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Configuracion extends AppCompatActivity {

    ConstraintLayout layout;
    Button btnColor;
    int mColorPorDefecto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        setTitle("Configuracion");

        layout = findViewById(R.id.layout);
        mColorPorDefecto = ContextCompat.getColor(getApplicationContext(), R.color.design_default_color_on_primary);
        btnColor = findViewById(R.id.btnColor);
        btnColor.setOnClickListener(view -> abrirColorPicker());
    }

    private void abrirColorPicker() {

        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mColorPorDefecto, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                    mColorPorDefecto = color;
                    layout.setBackgroundColor(mColorPorDefecto);
            }
        });
        colorPicker.show();
    }
}