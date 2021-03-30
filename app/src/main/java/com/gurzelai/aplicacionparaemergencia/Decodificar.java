package com.gurzelai.aplicacionparaemergencia;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Hashtable;
import java.util.Set;

import static com.gurzelai.aplicacionparaemergencia.Codificar.obtenerEquivalencias;

public class Decodificar extends AppCompatActivity {

    EditText etTexto;
    TextView tvResultado;
    Button btnCodificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decodificar);
        setTitle("Decodificar");
        tvResultado = findViewById(R.id.tvResultado);
        etTexto = findViewById(R.id.etTexto);
        btnCodificar = (Button) findViewById(R.id.btncodificar);
        btnCodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitarTeclado();
                String texto = etTexto.getText().toString();
                String decodificado = decodificar(texto);
                tvResultado.setText(decodificado);
            }
        });
    }

    public static String decodificar(String codificado) {
        StringBuilder decodificado = new StringBuilder();
        String[] morse = codificado.split(" ");
        for (String morseActual : morse) {
            String equivalencia = morseAAscii(morseActual);
            decodificado.append(equivalencia);
        }
        return decodificado.toString();
    }
    public static String morseAAscii(String morseBuscado) {
        Hashtable<String, String> equivalencias = obtenerEquivalencias();
        Set<String> claves = equivalencias.keySet();
        // La clave es la letra ASCII
        for (String clave : claves) {
            String morse = equivalencias.get(clave);
            if (morse.equals(morseBuscado)) {
                return clave;
            }
        }
        return "";
    }

    public void quitarTeclado() {
        InputMethodManager imm;
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //para quitar el teclado
        imm.hideSoftInputFromWindow(etTexto.getWindowToken(), 0); //quitar teclado
    }
}
