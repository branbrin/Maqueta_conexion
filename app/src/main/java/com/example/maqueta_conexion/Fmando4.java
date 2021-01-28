package com.example.maqueta_conexion;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
//import android.support.constraint.ConstraintLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.maqueta_conexion.Botones;
import com.example.maqueta_conexion.Mando4;
import com.example.maqueta_conexion.MoveViewTouchListener;
import com.example.maqueta_conexion.R;
import com.example.maqueta_conexion.ViewViewModel;
import com.example.maqueta_conexion.botonClick;

import java.util.ArrayList;
import java.util.List;

import static com.example.maqueta_conexion.Mando4.botones_actuales;
import static com.example.maqueta_conexion.Mando4.terminales_actuales;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fmando4 extends Fragment {

    FragmentManager manag;
    ConstraintLayout container;
    static public ConstraintLayout borrar;
    public static ViewViewModel mViewViewModel;
    public static Botones ultimoBoton;
    public static List<Botones> botones;
    boolean crearBoton = false;

    public Fmando4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_fmando4, container, false);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.constraintFmando4).setOnDragListener(new MyDragListener());
        container = view.findViewById(R.id.constraintFmando4);
        borrar = view.findViewById(R.id.borrar);
        manag = getParentFragmentManager();
        mViewViewModel = new ViewModelProvider(this).get(ViewViewModel.class);
        mViewViewModel.getAllObservedBotones().observe(getViewLifecycleOwner(), new Observer<List<Botones>>() {
            @Override
            public void onChanged(@Nullable final List<Botones> botones_actualizados) {
                // Update the cached copy of the words in the adapter.
                botones = botones_actualizados;
                if (botones.size() > 0 & crearBoton) {
                    ultimoBoton = botones.get(botones.size() - 1);
                    switch (ultimoBoton.getBoton()) {
                        case "boton":
                            View view = LayoutInflater.from(getContext()).inflate(ultimoBoton.getTipo(), null);
                            container.addView(view, 50, 50);

                            view.setOnTouchListener(new MoveViewTouchListener(view, manag));
                            if (ultimoBoton.getFloatX() != null) {
                                view.setX(ultimoBoton.getFloatX());
                                view.setY(ultimoBoton.getFloatY());
                            }
                            view.setOnClickListener(new botonClick());
                            Button bton = (Button) view;
                            bton.setText("" + ultimoBoton.getId());
                            bton.setOnTouchListener(new MoveViewTouchListener(bton, manag));
                            bton.setTag(ultimoBoton);
                            botones_actuales.add(bton);

                            break;
                        case "terminal":
                            View terminal = LayoutInflater.from(getContext()).inflate(ultimoBoton.getTipo(), null);
                            container.addView(terminal, 400, 70);
                            // terminal.setOnTouchListener(new MoveViewTouchListener(view, manag));
                            if (ultimoBoton.getFloatX() != null) {
                                terminal.setX(ultimoBoton.getFloatX());
                                terminal.setY(ultimoBoton.getFloatY());
                            }
                            ConstraintLayout termi = (ConstraintLayout) terminal;
                            termi.setOnTouchListener(new MoveViewTouchListener(termi, manag));
                            termi.setTag(ultimoBoton);
                            terminales_actuales.add(termi);
                            break;
                    }
                    crearBoton = false;
                }
            }
        });

        // botones = mViewViewModel.getAllBotones();
        botones_actuales = new ArrayList<Button>();
        terminales_actuales = new ArrayList<ConstraintLayout>();
        if (botones != null & Mando4.attachMando4 == true) {
            for (int i = 0; i < botones.size(); i++) {
                com.example.maqueta_conexion.Botones actual = botones.get(i);
                //MainActivity.mViewViewModel.borraBoton(botones[i]);
                switch (actual.getBoton()) {
                    case "boton":
                        View vista = LayoutInflater.from(getContext()).inflate(actual.getTipo(), null);
                        container.addView(vista, 50, 50);
                        Button bton = (Button) vista;
                        if (actual.getFloatX() != null) {
                            bton.setX(actual.getFloatX());
                            bton.setY(actual.getFloatY());
                        }
                        bton.setText("" + actual.getId());
                        bton.setOnTouchListener(new MoveViewTouchListener(bton, manag));
                        bton.setTag(actual);
                        botones_actuales.add(bton);
                        break;
                    case "terminal":
                        View vista2 = LayoutInflater.from(getContext()).inflate(actual.getTipo(), null);

                        container.addView(vista2, 400, 70);
                        ConstraintLayout terminal = (ConstraintLayout) vista2;
                        if (actual.getFloatX() != null) {
                            vista2.setX(actual.getFloatX());
                            vista2.setY(actual.getFloatY());

                        }

                        //  actual.cambiaNombre("boton"+i);

                        terminal.setOnTouchListener(new MoveViewTouchListener(terminal, manag));
                        terminal.setTag(actual);
                        terminales_actuales.add(terminal);
                }
                Mando4.attachMando4 = false;
            }
        }
    }

    public void onPause() {
        super.onPause();
        Mando4.attachMando4 = true;
        Mando4.pruebas.setText("" + botones_actuales.size());
//botones=mViewViewModel.getAllBotones();
        for (int i = 0; i < botones.size(); i++) {
            Botones actualizador = botones.get(i);
            switch (actualizador.mBoton) {
                case "boton":
                    for (int j = 0; j < botones_actuales.size(); j++) {
                        Botones auxiliar = (Botones) botones_actuales.get(j).getTag();
                        if (auxiliar.getId() == actualizador.getId()) {
                                actualizador.setX(botones_actuales.get(j).getX());
                                actualizador.setY(botones_actuales.get(j).getY());
                                mViewViewModel.añadeBoton(actualizador);
                        }
                    }
                    break;
                case "terminal":
                    for (int j = 0; j < terminales_actuales.size(); j++) {
                        Botones auxiliar = (Botones) terminales_actuales.get(j).getTag();
                        if (auxiliar.getId() == actualizador.getId()) {

                            actualizador.setX(terminales_actuales.get(j).getX());
                            actualizador.setY(terminales_actuales.get(j).getY());
                            mViewViewModel.añadeBoton(actualizador);

                        }
                    }
                    break;
            }

        }



       /*
        if (botones_actuales.size()>0){
            //Mando4.mViewViewModel.borrarTodo();
            for(int i=0;i<botones_actuales.size();i++){
                Botones actualizador=botones.get(i);
                if (actualizador.mBoton.equals("boton")){
                    actualizador.setX(botones_actuales.get(i).getX());
                    actualizador.setY(botones_actuales.get(i).getY());
                    mViewViewModel.añadeBoton(actualizador);
                }
            }
        }
       if (terminales_actuales.size()>0){
           //Mando4.mViewViewModel.borrarTodo();
           for(int i=0;i<terminales_actuales.size();i++){
               Botones actualizador=botones.get(i);
               if (actualizador.mBoton.equals("terminal")){
                   actualizador.setX(terminales_actuales.get(i).getX());
                   actualizador.setY(terminales_actuales.get(i).getY());
                   mViewViewModel.añadeBoton(actualizador);
               }
           }
       }*/
    }
    class MyDragListener implements View.OnDragListener {

        Drawable enterShape = getResources().getDrawable(
                R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroupe
                    View oview = (View) event.getLocalState();
                    crearBoton=true;
                    Botones nuevoBoton;
                    switch (oview.getId()){
                        case R.id.button:

                            nuevoBoton = new Botones("boton");
                            nuevoBoton.setMensaje("R");
                            nuevoBoton.setX(event.getX());
                            nuevoBoton.setY(event.getY());
                            nuevoBoton.setCabecero("r");
                            nuevoBoton.setTipo(R.layout.boton_rojo);
                            long ID= mViewViewModel.añadeBoton(nuevoBoton);
                            break;
                        case R.id.boton_azul:

                            nuevoBoton = new Botones("boton");
                            nuevoBoton.setMensaje("A");
                            nuevoBoton.setX(event.getX());
                            nuevoBoton.setY(event.getY());
                            nuevoBoton.setCabecero("a");
                            nuevoBoton.setTipo(R.layout.boton_azul);
                            mViewViewModel.añadeBoton(nuevoBoton);
                            break;
                        case R.id.boton_verde:
                            nuevoBoton = new Botones("boton");
                            nuevoBoton.setMensaje("V");
                            nuevoBoton.setX(event.getX());
                            nuevoBoton.setY(event.getY());
                            nuevoBoton.setCabecero("v");
                            nuevoBoton.setTipo(R.layout.boton_verde);
                            mViewViewModel.añadeBoton(nuevoBoton);
                            break;

                        case R.id.boton_amarillo:
                            nuevoBoton = new Botones("boton");
                            nuevoBoton.setMensaje("Y");
                            nuevoBoton.setX(event.getX());
                            nuevoBoton.setY(event.getY());
                            nuevoBoton.setCabecero("y");
                            nuevoBoton.setTipo(R.layout.boton_amarillo);
                            mViewViewModel.añadeBoton(nuevoBoton);
                            break;
                        case R.id.boton_arriba:
                            nuevoBoton = new Botones("boton");
                            nuevoBoton.setMensaje("U");
                            nuevoBoton.setX(event.getX());
                            nuevoBoton.setY(event.getY());
                            nuevoBoton.setCabecero("u");
                            nuevoBoton.setTipo(R.layout.boton_arriba);
                            mViewViewModel.añadeBoton(nuevoBoton);
                            break;
                        case R.id.boton_derecha:
                            nuevoBoton = new Botones("boton");
                            nuevoBoton.setMensaje("D");
                            nuevoBoton.setX(event.getX());
                            nuevoBoton.setY(event.getY());
                            nuevoBoton.setCabecero("d");
                            nuevoBoton.setTipo(R.layout.boton_derecha);
                            mViewViewModel.añadeBoton(nuevoBoton);
                            break;
                        case R.id.boton_abajo:
                            nuevoBoton = new Botones("boton");
                            nuevoBoton.setMensaje("B");
                            nuevoBoton.setX(event.getX());
                            nuevoBoton.setY(event.getY());
                            nuevoBoton.setCabecero("b");
                            nuevoBoton.setTipo(R.layout.boton_abajo);
                            mViewViewModel.añadeBoton(nuevoBoton);
                            break;
                        case R.id.boton_izquierda:
                            nuevoBoton = new Botones("boton");
                            nuevoBoton.setMensaje("Z");
                            nuevoBoton.setX(event.getX());
                            nuevoBoton.setY(event.getY());
                            nuevoBoton.setCabecero("z");
                            nuevoBoton.setTipo(R.layout.boton_izquierda);
                            mViewViewModel.añadeBoton(nuevoBoton);
                            break;

                        case R.id.terminal:
                            nuevoBoton = new Botones("terminal");
                            nuevoBoton.setMensaje("T");
                            nuevoBoton.setX(event.getX());
                            nuevoBoton.setY(event.getY());
                            nuevoBoton.setCabecero("t");
                            nuevoBoton.setTipo(R.layout.enviar_texto);
                            mViewViewModel.añadeBoton(nuevoBoton);
                            break;


                    }









                    // ViewGroup owner = (ViewGroup) view.getParent();
                    // owner.removeView(view);
                    // ConstraintLayout container = (ConstraintLayout) v;
                    // container.addView(view2);
                    //  view2.setVisibility(View.VISIBLE);
                    //  view2.setOnTouchListener(new MoveViewTouchListener(view2));
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }
}
