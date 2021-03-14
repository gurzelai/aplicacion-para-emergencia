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

import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

    final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    Button boton;
    EditText etTexto;
    TextView etResultado;
    Button btnLlamar, btnFlash;

    boolean flashAccesible = false;
    private CameraManager mCameraManager;
    private String mCameraId;
    boolean flashEncendido = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        etResultado = findViewById(R.id.tvResultado);
        etTexto = findViewById(R.id.etTexto);
        boton = (Button) findViewById(R.id.btncodificar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitarTeclado();
                String texto = etTexto.getText().toString();
                String codificado = codificar(texto);
                etResultado.setText(codificado);
            }
        });
        btnLlamar = findViewById(R.id.btnLlamar);
        btnLlamar.setOnClickListener(view -> pedirPermiso());
        btnFlash = findViewById(R.id.btnFlash);
        btnFlash.setOnClickListener(view -> cargarFlash());
    }

    private void cargarFlash() {
        inicializar();
        if (flashAccesible) actualizarFlash();
    }

    private void actualizarFlash() {
        try {
            if (flashEncendido) {
                mCameraManager.setTorchMode(mCameraId, false);
                flashEncendido = false;
            } else {
                try {
                    mCameraManager.setTorchMode(mCameraId, true);
                    flashEncendido = true;
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void inicializar() {
        if (flashAccesible) {
            mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try {
                mCameraId = mCameraManager.getCameraIdList()[0];
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            comprobarAccesibilidad();
            if(flashAccesible) inicializar(); //esto para cargar los ids...
        }
    }

    private void comprobarAccesibilidad() {
        flashAccesible = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!flashAccesible) {
            showNoFlashError();
        }
    }

    private void showNoFlashError() {
        AlertDialog alert = new AlertDialog.Builder(this)
                .create();
        alert.setTitle("Oops!");
        alert.setMessage("No se puede acceder al flash...");
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();
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
        i.setData(Uri.parse("tel:112"));
        startActivity(i);
    }

    public void quitarTeclado() {
        InputMethodManager imm;
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //para quitar el teclado
        imm.hideSoftInputFromWindow(etTexto.getWindowToken(), 0); //quitar teclado
    }

    private String codificar(String texto) {
        StringBuilder codificado = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            String charComoCadenaYEnMayusculas = String.valueOf(texto.charAt(i)).toUpperCase();
            String equivalencia = asciiAMorse(charComoCadenaYEnMayusculas);
            codificado
                    .append(equivalencia)
                    .append(" ");
        }
        return codificado.toString();
    }

    public static String asciiAMorse(String ascii) {
        Hashtable<String, String> equivalencias = obtenerEquivalencias();
        return equivalencias.getOrDefault(ascii, "");
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

    public static Hashtable<String, String> obtenerEquivalencias() {
        Hashtable<String, String> equivalencias = new Hashtable<>();
        equivalencias.put("A", ".-");
        equivalencias.put("B", "-...");
        equivalencias.put("C", "-.-.");
        equivalencias.put("CH", "----");
        equivalencias.put("D", "-..");
        equivalencias.put("E", ".");
        equivalencias.put("F", "..-.");
        equivalencias.put("G", "--.");
        equivalencias.put("H", "....");
        equivalencias.put("I", "..");
        equivalencias.put("J", ".---");
        equivalencias.put("K", "-.-");
        equivalencias.put("L", ".-..");
        equivalencias.put("M", "--");
        equivalencias.put("N", "-.");
        equivalencias.put("Ñ", "--.--");
        equivalencias.put("O", "---");
        equivalencias.put("P", ".--.");
        equivalencias.put("Q", "--.-");
        equivalencias.put("R", ".-.");
        equivalencias.put("S", "...");
        equivalencias.put("T", "-");
        equivalencias.put("U", "..-");
        equivalencias.put("V", "...-");
        equivalencias.put("W", ".--");
        equivalencias.put("X", "-..-");
        equivalencias.put("Y", "-.--");
        equivalencias.put("Z", "--..");
        equivalencias.put("0", "-----");
        equivalencias.put("1", ".----");
        equivalencias.put("2", "..---");
        equivalencias.put("3", "...--");
        equivalencias.put("4", "....-");
        equivalencias.put("5", ".....");
        equivalencias.put("6", "-....");
        equivalencias.put("7", "--...");
        equivalencias.put("8", "---..");
        equivalencias.put("9", "----.");
        equivalencias.put(".", ".-.-.-");
        equivalencias.put(",", "--..--");
        equivalencias.put(":", "---...");
        equivalencias.put("?", "..--..");
        equivalencias.put("'", ".----.");
        equivalencias.put("-", "-....-");
        equivalencias.put("/", "-..-.");
        equivalencias.put("\"", ".-..-.");
        equivalencias.put("@", ".--.-.");
        equivalencias.put("=", "-...-");
        equivalencias.put("!", "−.−.−−");
        return equivalencias;
    }
}