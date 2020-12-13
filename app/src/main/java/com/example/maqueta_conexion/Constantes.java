package com.example.maqueta_conexion;

import android.widget.Button;

import java.util.ArrayList;

public interface Constantes {
    static final int REQUEST_ENABLE_BT=5;
    static final int MESSAGE_READ=9;
    static final int TIEMPO_ACT=1000;
    static final byte[] MARCHA={(byte)0x49,(byte)109,(byte)0x46};
    static final byte[] PARO={(byte)0x49,(byte)112,(byte)0x46};
    static final byte[] INTERRUPTOR={(byte)0x49,(byte)105,(byte)0x46};
    static final byte[] SETA={(byte)0x49,(byte)0x54,0x46};
    static final byte[] VELOCIDAD={(byte)0x49,(byte)0x76,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x46};


    static String botonRojo="R";
    static String botonRojoMen="r";




}
