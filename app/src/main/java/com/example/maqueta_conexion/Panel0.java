package com.example.maqueta_conexion;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "panel0")

public class Panel0 {

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
    @ColumnInfo(name="final")
    public String mUltimo;
    @ColumnInfo(name="IDaux")
    public int mIDaux;
    @ColumnInfo(name="StringArg")
    public String mStringArg;
    @ColumnInfo(name="IntArg")
    public int mIntArg;
    @ColumnInfo(name="IntArg2")
    public int mIntArg2;
    @ColumnInfo(name="IntArg3")
    public int mIntArg3;
    @ColumnInfo(name="IntArg4")
    public int mIntArg4;



    //Constructor de clase:
    public Panel0(String boton) {
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

    public String getUltimo(){return this.mUltimo;}
    public void setFinal(String ultimo){mUltimo=ultimo;}

    public int getId(){return this.mID;}
    public void setId(int Id){mID=Id;}

    public int getIDaux(){return this.mIDaux;}
    public void setIDaux(int Idaux){mIDaux=Idaux;}

    public String getStringArg(){return this.mStringArg;}
    public void setStringArg(String stringArg){mStringArg=stringArg;}

    public int getIntArg(){return this.mIntArg;}
    public void setIntArg(int intArg){mIntArg=intArg;}

    public int getIntArg2(){return this.mIntArg2;}
    public void setIntArg2(int intArg2){mIntArg2=intArg2;}

    public int getIntArg3(){return this.mIntArg3;}
    public void setIntArg3(int intArg3){mIntArg3=intArg3;}

    public int getIntArg4(){return this.mIntArg4;}
    public void setIntArg4(int intArg4){mIntArg4=intArg4;}

}
