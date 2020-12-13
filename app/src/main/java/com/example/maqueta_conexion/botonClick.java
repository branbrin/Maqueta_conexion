package com.example.maqueta_conexion;

import android.view.View;
import android.widget.Button;

public class botonClick implements View.OnClickListener {
    @Override
    public void onClick(View v) {
       Button botonsete = (Button)v;
       Botones botones= (Botones) botonsete.getTag();
      String a= botones.getCabecero();
      String b = botones.getMensaje();
      String c =a+b;

      BluetoothMainActivity.gestionBoton( c.getBytes());


    }
}
