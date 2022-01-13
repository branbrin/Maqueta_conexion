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

    @Query("SELECT*FROM lista_botones WHERE boton=:titulo")
    Botones getTitulo(String titulo);

    @Query("SELECT*FROM lista_botones WHERE boton=:descripcion")
    Botones getDescripcion(String descripcion);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long añadeElementoPanel0(Panel0 panel0);

    @Query("DELETE FROM panel0")
    void borrarTodoPanel0();

    @Query("SELECT * from panel0 ORDER BY mID ASC")
    LiveData<List<Panel0>> getPanel0LiveData();

    @Delete
    void borraEnPanel0(Panel0 panel0);

    @Query("SELECT * FROM panel0")
    Panel0[] getPanel0();

    @Query("SELECT * FROM panel0 WHERE mID=:id ")
    Panel0 getViewByIDPanel0(int id);

    @Query("SELECT*FROM panel0 WHERE boton=:titulo")
    Panel0 getTitulo0(String titulo);

    @Query("SELECT*FROM panel0 WHERE boton=:descripcion")
    Panel0 getDescripcion0(String descripcion);


}
