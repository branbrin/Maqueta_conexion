package com.example.maqueta_conexion;

//import android.arch.lifecycle.LiveData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BotonesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long añadeBoton(Botones boton);



    @Query("DELETE FROM lista_botones")
    void borrarTodo();

    @Query("SELECT * from lista_botones ORDER BY mID ASC")
    LiveData<List<Botones>> getBotonesOrdenAlfabetico();

    @Delete
    void delete(Botones boton);

    @Query("SELECT * FROM lista_botones")
    Botones[] getBotones();

    @Query("SELECT * FROM lista_botones WHERE mID=:id ")
    Botones getBoton(int id);

}
