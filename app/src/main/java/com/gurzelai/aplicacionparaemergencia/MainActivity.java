package com.gurzelai.aplicacionparaemergencia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

    Button boton;
    EditText etTexto;
    TextView etResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        etResultado = findViewById(R.id.tvResultado);
        etTexto = findViewById(R.id.etTexto);
        boton = (Button) findViewById(R.id.boton);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitarTeclado();
                String texto = etTexto.getText().toString();
                String codificado = codificar(texto);
                etResultado.setText(codificado);
            }
        });
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