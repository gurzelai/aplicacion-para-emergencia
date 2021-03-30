package com.gurzelai.aplicacionparaemergencia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        btnLlamar.setOnClickListener(view -> pedirPermiso(false));
        pedirPermiso(true);
    }

    private void abrirIntent(String nomIntent) {
        Intent intent = null;
        switch (nomIntent) {
            case "codificacion":
                intent = new Intent(getApplicationContext(), Codificar.class);
                startActivity(intent);
                break;
            case "decodificacion":
                intent = new Intent(getApplicationContext(), Decodificar.class);
                startActivity(intent);
                break;
            case "configuracion":
                intent = new Intent(getApplicationContext(), Configuracion.class);
                startActivity(intent);
                break;
            case "marcacion rapida":
                intent = new Intent(getApplicationContext(), MarcacionRapida.class);
                startActivity(intent);
                break;
        }
    }

    public void pedirPermiso(boolean esLaPrimeraVez) {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            if (!esLaPrimeraVez) llamarEmergencias();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }
    }

    private void llamarEmergencias() {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:+34 112"));
        startActivity(i);
    }
}