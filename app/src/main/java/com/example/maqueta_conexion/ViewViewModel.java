package com.example.maqueta_conexion;

import android.app.Application;
//import android.arch.lifecycle.AndroidViewModel;
//import android.arch.lifecycle.LiveData;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewViewModel extends AndroidViewModel {
    private  ViewRepository mRepository;
    private LiveData<List<Botones>> mAllViews;
    private  Botones[] mAllBotones;

    public  ViewViewModel (Application application){
        super(application);
        mRepository = new ViewRepository(application);
        mAllViews=mRepository.getAllObservedBotones();
        mAllBotones=mRepository.getAllBotones();
    }
    //MANDO4-------------------------------------------------------------------
    public LiveData<List<Botones>> getAllObservedBotones(){ return mAllViews; }

    public Botones[] getAllBotones(){return mAllBotones;}
    public Botones getBoton(int id){return mRepository.getBoton(id);}
    public Botones getTitulo(String titulo){return mRepository.getTitulo(titulo);}
    public Botones getDescripcion(String titulo){return mRepository.getTitulo(titulo);}

    public long añadeBoton(Botones boton){return mRepository.añadeBoton(boton);}
    public void borraBoton(Botones boton){mRepository.borraBoton(boton);}
    public void borrarTodo(){mRepository.borrarTodo();}


    //MANDO0-----------------------------------------------------------------------
    public LiveData<List<Panel0>> getPanel0LiveData(){ return mRepository.getPanel0LiveData(); }

    public Panel0[] getPanel0(){return mRepository.getPanel0();}
    public Panel0 getViewByIDPanel0(int id){return mRepository.getViewByIDPanel0(id);}
    public Panel0 getTitulo0(String titulo){return mRepository.getTitulo0(titulo);}

    public long añadeElementoPanel0(Panel0 elemento){return mRepository.añadeElementoPanel0(elemento);}
    public void borraEnPanel0(Panel0 elemento){mRepository.borraEnPanel0(elemento);}
    public void borrarTodoPanel0(){mRepository.borrarTodoPanel0();}

}
