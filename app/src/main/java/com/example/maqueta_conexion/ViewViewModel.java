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

    public LiveData<List<Botones>> getAllObservedBotones(){ return mAllViews; }

   public Botones[] getAllBotones(){return mAllBotones;}
    public Botones getBoton(int id){return mRepository.getBoton(id);}
    public long añadeBoton(Botones boton){return mRepository.añadeBoton(boton);}
    public void borraBoton(Botones boton){mRepository.borraBoton(boton);}
    public void borrarTodo(){mRepository.borrarTodo();}
}
