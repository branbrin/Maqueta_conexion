package com.example.maqueta_conexion;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "lista_botones")
public class Botones {

    @PrimaryKey (autoGenerate = true)
    public int mID;
    @NonNull
    @ColumnInfo(name = "boton")
    public String mBoton;
    @ColumnInfo(name = "tipo")
    public int mTipo;
    @ColumnInfo(name = "floatX")
    public Float mFloatX;
    @ColumnInfo(name = "floatY")
    public Float mFloatY;
    @ColumnInfo(name="cabecero")
    public String mCabecero;
    @ColumnInfo(name="mensaje")
    public String mMensaje;

    //Constructor de clase:
    public Botones(String boton) {
        this.mBoton = boton;
    }

    public String getBoton(){return this.mBoton;}

    public int getTipo() {return this.mTipo;}
    public  void setTipo(int tipo){mTipo=tipo;}

    public Float getFloatX(){return this.mFloatX;}
    public void setX(Float x ){mFloatX=x;}

    public Float getFloatY(){return this.mFloatY;}
    public void setY(Float y ){mFloatY=y;}

    public String getCabecero(){return this.mCabecero;}
    public void setCabecero(String cabecero){mCabecero=cabecero;}

    public String getMensaje(){return this.mMensaje;}
    public void setMensaje(String mensaje){mMensaje=mensaje;}

    public int getId(){return this.mID;}
    public void setId(int Id){mID=Id;}
}
