package com.gurzelai.aplicacionparaemergencia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

    final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    FloatingActionButton btnLlamar;
    Button btnCodificacion, btnDecodificacion, btnConfiguracion, btnMarcacionRapida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCodificacion = findViewById(R.id.btnCodificar);
        btnCodificacion.setOnClickListener(view -> abrirIntent("codificacion"));
        btnDecodificacion = findViewById(R.id.btnDecodificar);
        btnDecodificacion.setOnClickListener(view -> abrirIntent("decodificacion"));
        btnConfiguracion = findViewById(R.id.btnConfiguracion);
        btnConfiguracion.setOnClickListener(view -> abrirIntent("configuracion"));
        btnMarcacionRapida = findViewById(R.id.btnMarcacionRapida);
        btnMarcacionRapida.setOnClickListener(view -> abrirIntent("marcacion rapida"));

        btnLlamar = findViewById(R.id.btnLlamar);
        btnLlamar.setOnClickListener(view -> pedirPermiso());

    }

    private void abrirIntent(String nomIntent) {
        switch (nomIntent) {
            case "codificacion":
                Intent intent = new Intent(getApplicationContext(), Codificar.class);
                startActivity(intent);
                break;
            case "decodificacion":
                break;
            case "configuracion":
                break;
            case "marcacion rapida":
                break;
        }
    }

    public void pedirPermiso() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            llamarEmergencias();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }
    }

    private void llamarEmergencias() {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:+34 112"));
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    llamarEmergencias();
                } else {
                    Toast.makeText(getApplicationContext(), "Esta app necesita tu permiso para llamar a emergencias", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}