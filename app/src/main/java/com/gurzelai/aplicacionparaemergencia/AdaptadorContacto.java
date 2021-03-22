package com.gurzelai.aplicacionparaemergencia;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AdaptadorContacto extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Contacto> contactos;
    int[] colores = {Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW, Color.LTGRAY, Color.CYAN};
    public AdaptadorContacto(Context context, int layout, List<Contacto> contactos){
        this.context = context;
        this.layout = layout;
        this.contactos = contactos;
    }

    @Override
    public int getCount() {
        return this.contactos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.contactos.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        // Copiamos la vista
        View v = convertView;

        //Inflamos la vista con nuestro propio layout
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);

        v= layoutInflater.inflate(R.layout.item_adaptador_contacto, null);
        // Valor actual según la posición
        LinearLayout layout = v.findViewById(R.id.layoutContacto);
        TextView id = v.findViewById(R.id.tvId);
        TextView nombre = v.findViewById(R.id.tvNombre);
        id.setText(String.valueOf(position+1));
        nombre.setText(contactos.get(position).getNombre().toString());

        int color;
        if(position >= colores.length){
            color = colores[position% colores.length];
        }
        else {
            color = colores[position];
        }
        id.setBackgroundColor(color);
        nombre.setBackgroundColor(color);
        layout.setBackgroundColor(color);

        //Devolvemos la vista inflada
        return v;
    }
}
