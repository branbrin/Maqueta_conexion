package com.example.maqueta_conexion;

import android.app.Application;
//import android.arch.lifecycle.LiveData;
import android.widget.Button;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewRepository {
    private BotonesDao mBotonesDao;
    private BotonesDao mBotonesDao2;
    private LiveData<List<Botones>> mAllViews;
    private Botones[] mAllBotones;
    private Botones mBoton;
    long id;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    ViewRepository(Application application) {
        ViewRoomDatabase db = ViewRoomDatabase.getDatabase(application);
        mBotonesDao = db.botonesDao();
        mAllViews = mBotonesDao.getBotonesOrdenAlfabetico();
        mAllBotones=mBotonesDao.getBotones();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.

    //PANEL4--------------------------------------------------------------------------------
    LiveData<List<Botones>> getAllObservedBotones() {
        return mAllViews;
    }

    Botones[]  getAllBotones(){
        return mAllBotones;
    }

    Botones getBoton (int idbusca){
        return mBotonesDao.getBoton(idbusca);
    }

    Botones getTitulo (String titulo){ return mBotonesDao.getTitulo(titulo); }

    Botones getDescripcion (String descripcion){ return mBotonesDao.getDescripcion(descripcion); }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    long añadeBoton(Botones botones) {
        ViewRoomDatabase.databaseWriteExecutor.execute(() -> {
            id= mBotonesDao.añadeBoton(botones);


        });
        return id;
    }

    void borraBoton(Botones boton) {
        ViewRoomDatabase.databaseWriteExecutor.execute(() -> {
            mBotonesDao.delete(boton);
        });
    }

    void borrarTodo() {
        ViewRoomDatabase.databaseWriteExecutor.execute(() -> {
            mBotonesDao.borrarTodo();
        });
    }
    //FIN PANEL4

    //PANEL0------------------------------------------------------------------------
    LiveData<List<Panel0>> getPanel0LiveData(){ return mBotonesDao.getPanel0LiveData(); }

    Panel0[]  getPanel0(){
        return mBotonesDao.getPanel0();
    }

    Panel0 getViewByIDPanel0 (int idbusca){
        return mBotonesDao.getViewByIDPanel0(idbusca);
    }

    Panel0 getTitulo0 (String titulo){ return mBotonesDao.getTitulo0(titulo); }



    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    long añadeElementoPanel0(Panel0 panel0) {
        ViewRoomDatabase.databaseWriteExecutor.execute(() -> {
            id= mBotonesDao.añadeElementoPanel0(panel0);


        });
        return id;
    }

    void borraEnPanel0(Panel0 panel0) {
        ViewRoomDatabase.databaseWriteExecutor.execute(() -> {
            mBotonesDao.borraEnPanel0(panel0);
        });
    }

    void borrarTodoPanel0() {
        ViewRoomDatabase.databaseWriteExecutor.execute(() -> {
            mBotonesDao.borrarTodoPanel0();
        });
    }

}
