package com.example.maqueta_conexion;

import android.view.View;
import android.widget.Button;

public class botonClick implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Button botonsete = (Button)v;
        botonsete.setText("sapulsao");
    }
}
