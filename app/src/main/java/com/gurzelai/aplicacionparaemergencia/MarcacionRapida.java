package com.gurzelai.aplicacionparaemergencia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MarcacionRapida extends AppCompatActivity {

    final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    ListView lvMarcacion;
    List<Integer> contactos;
    Button volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcacion_rapida);

        volver = findViewById(R.id.button);
        volver.setOnClickListener(v -> finish());

        inicializar();

        lvMarcacion = findViewById(R.id.lvMarcacion);
        lvMarcacion.setAdapter(new AdaptadorContacto(this, R.layout.item_adaptador_contacto, contactos));

        lvMarcacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pedirPermiso(position);

            }
        });
    }

    private void inicializar() {


        contactos = new ArrayList<>();
        contactos.add(660261424);
        contactos.add(634421434);
    }

    public void pedirPermiso(int position) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            llamar(position);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }
    }

    private void llamar(int position) {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:" + contactos.get(position)));
        startActivity(i);
    }
}