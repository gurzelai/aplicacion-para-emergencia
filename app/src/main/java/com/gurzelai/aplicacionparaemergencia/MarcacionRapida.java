package com.gurzelai.aplicacionparaemergencia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MarcacionRapida extends AppCompatActivity {

    private static final int PICK_CONTACT_REQUEST = 5;
    final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    ListView lvMarcacion;
    List<Contacto> contactos;
    Button volver, anadir;

    AdaptadorContacto adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcacion_rapida);
        setTitle("Marcación rápida");
        volver = findViewById(R.id.volver);
        volver.setOnClickListener(v -> finish());
        anadir = findViewById(R.id.añadir);
        anadir.setOnClickListener(v -> seleccionarContacto());

        contactos = new ArrayList<>();
        contactos.add(new Contacto("Gorka", 0));
        contactos.add(new Contacto("Mikel", 0));

        lvMarcacion = findViewById(R.id.lvMarcacion);
        lvMarcacion.setAdapter(adapter = new AdaptadorContacto(this, R.layout.item_adaptador_contacto, contactos));

        lvMarcacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pedirPermiso(position);
            }
        });
        lvMarcacion.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                contactos.remove(position);
                return true;
            }
        });
        //Toast.makeText(getApplicationContext(), "Manten para eliminar", Toast.LENGTH_SHORT).show();
    }

    private void seleccionarContacto() {
        Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, PICK_CONTACT_REQUEST);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor.moveToFirst()) {
                    int nombreColumna = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    String nombre = cursor.getString(nombreColumna);
                    int telefonoColumna = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String numero = cursor.getString(telefonoColumna).replace(" ", "");
                    int numeroInt;
                    if(numero.contains("+")){
                        numeroInt =  Integer.parseInt(numero.substring(3));
                    }
                    else {
                        numeroInt =  Integer.parseInt(numero);
                    }

                    contactos.add(new Contacto(nombre, numeroInt));
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}