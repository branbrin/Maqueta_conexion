package com.example.maqueta_conexion;

import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

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
import com.jjoe64.graphview.GraphView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.maqueta_conexion.MainActivity.mViewViewModel;

import static com.example.maqueta_conexion.MainActivity.primerInicio0;
import static com.example.maqueta_conexion.MainActivity.primerInicio4;
import static com.example.maqueta_conexion.Mando4.botones_actuales;
import static com.example.maqueta_conexion.Mando4.graficas_actuales;
import static com.example.maqueta_conexion.Mando4.interruptores_actuales;
import static com.example.maqueta_conexion.Mando4.pantallas_actuales;
import static com.example.maqueta_conexion.Mando4.sliders_actuales;
import static com.example.maqueta_conexion.Mando4.terminalesBT_actuales;
import static com.example.maqueta_conexion.Mando4.terminales_actuales;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fmando4 extends Fragment {

    FragmentManager manag;
    ConstraintLayout container;
    static public ConstraintLayout borrar;
    public static Botones ultimoBoton;
    public static List<Botones> botones;
    public static Panel0 ultimoelemento0;
    public static List<Panel0> panel0;
    boolean crearBoton = false;
    int mNumeroPanel;

    public Fmando4() {
        // Required empty public constructor
    }
    public  Fmando4(int numeroPanel){
        mNumeroPanel = numeroPanel;
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

        switch (mNumeroPanel){
            case 4:
                MainActivity.mViewViewModel.getAllObservedBotones().observe(getViewLifecycleOwner(), new Observer<List<Botones>>() {
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

                                    if (ultimoBoton.getFloatX() != null) {
                                        terminal.setX(ultimoBoton.getFloatX());
                                        terminal.setY(ultimoBoton.getFloatY());
                                    }
                                    ConstraintLayout termi = (ConstraintLayout) terminal;
                                    termi.setOnTouchListener(new MoveViewTouchListener(termi, manag));
                                    termi.setTag(ultimoBoton);
                                    terminales_actuales.add(termi);
                                    break;
                                case "pantalla":
                                    View pantalla = LayoutInflater.from(getContext()).inflate(ultimoBoton.getTipo(), null);
                                    container.addView(pantalla, 250, 100);

                                    if (ultimoBoton.getFloatX() != null) {
                                        pantalla.setX(ultimoBoton.getFloatX());
                                        pantalla.setY(ultimoBoton.getFloatY());
                                    }
                                    TextView pant = (TextView) pantalla;
                                    pant.setOnTouchListener(new MoveViewTouchListener(pant, manag));
                                    pant.setTag(ultimoBoton);
                                    pantallas_actuales.add(pant);
                                    break;

                                case "terminalBT":
                                    View terminalBT = LayoutInflater.from(getContext()).inflate(ultimoBoton.getTipo(), null);
                                    container.addView(terminalBT, 400, 70);

                                    if (ultimoBoton.getFloatX() != null) {
                                        terminalBT.setX(ultimoBoton.getFloatX());
                                        terminalBT.setY(ultimoBoton.getFloatY());
                                    }

                                    ConstraintLayout termibt = (ConstraintLayout) terminalBT;
                                    View aux=((ConstraintLayout) termibt).getChildAt(0);
                                    ConstraintLayout auxaux=(ConstraintLayout)aux;

                                    Button button= (Button) auxaux.getChildAt(1);


                                    termibt.setOnTouchListener(new MoveViewTouchListener(termibt, manag));
                                    termibt.setTag(ultimoBoton);
                                    terminalesBT_actuales.add(termibt);
                                    break;
                                case "interruptor":
                                    View interruptor = LayoutInflater.from(getContext()).inflate(ultimoBoton.getTipo(), null);
                                    container.addView(interruptor, 80, 40);
                                    if (ultimoBoton.getFloatX() != null) {
                                        interruptor.setX(ultimoBoton.getFloatX());
                                        interruptor.setY(ultimoBoton.getFloatY());
                                    }
                                    Switch switch1 = (Switch) interruptor;
                                    switch1.setOnTouchListener(new MoveViewTouchListener(switch1, manag));
                                    switch1.setTag(ultimoBoton);
                                    interruptores_actuales.add(switch1);
                                    break;
                                case "slider":
                                    View slider = LayoutInflater.from(getContext()).inflate(ultimoBoton.getTipo(), null);
                                    container.addView(slider, 200, 50);
                                    if (ultimoBoton.getFloatX() != null) {
                                        slider.setX(ultimoBoton.getFloatX());
                                        slider.setY(ultimoBoton.getFloatY());
                                    }
                                    SeekBar sldr = (SeekBar) slider;
                                    sldr.setOnTouchListener(new MoveViewTouchListener(sldr, manag));
                                    sldr.setTag(ultimoBoton);
                                    sliders_actuales.add(sldr);
                                    break;
                                case "grafica":
                                    View grafica = LayoutInflater.from(getContext()).inflate(ultimoBoton.getTipo(), null);
                                    container.addView(grafica, 400, 200);
                                    if (ultimoBoton.getFloatX() != null) {
                                        grafica.setX(ultimoBoton.getFloatX());
                                        grafica.setY(ultimoBoton.getFloatY());
                                    }
                                    ConstraintLayout grf = (ConstraintLayout) grafica;
                                    grf.setOnTouchListener(new MoveViewTouchListener(grf, manag));
                                    grf.setTag(ultimoBoton);
                                    Mando4.graficas_actuales.add(grf);
                                    break;



                            }
                            crearBoton = false;
                        }
                    }
                });

                if (primerInicio4){
                    primerInicio4=false;
                    botones = Arrays.asList(mViewViewModel.getAllBotones());

                }

                botones_actuales = new ArrayList<Button>();
                terminales_actuales = new ArrayList<ConstraintLayout>();
                pantallas_actuales = new ArrayList<TextView>();
                terminalesBT_actuales =new ArrayList<ConstraintLayout>();
                interruptores_actuales = new ArrayList<Switch>();
                sliders_actuales= new ArrayList<SeekBar>();
                graficas_actuales=new ArrayList<ConstraintLayout>();

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
                                terminal.setOnTouchListener(new MoveViewTouchListener(terminal, manag));
                                terminal.setTag(actual);
                                terminales_actuales.add(terminal);
                                break;
                            case "pantalla":
                                View vista3 = LayoutInflater.from(getContext()).inflate(actual.getTipo(), null);

                                container.addView(vista3, 250, 100);
                                TextView pantalla = (TextView) vista3;
                                if (actual.getFloatX() != null) {
                                    vista3.setX(actual.getFloatX());
                                    vista3.setY(actual.getFloatY());
                                }
                                //  actual.cambiaNombre("boton"+i);
                                pantalla.setTag(actual);
                                pantalla.setOnTouchListener(new MoveViewTouchListener(pantalla, manag));
                                pantallas_actuales.add(pantalla);
                                break;

                            case "terminalBT":
                                View vista4 = LayoutInflater.from(getContext()).inflate(actual.getTipo(), null);

                                container.addView(vista4, 400, 70);
                                ConstraintLayout terminalBT = (ConstraintLayout) vista4;
                                if (actual.getFloatX() != null) {
                                    vista4.setX(actual.getFloatX());
                                    vista4.setY(actual.getFloatY());

                                }
                                View aux=((ConstraintLayout) terminalBT).getChildAt(0);
                                ConstraintLayout auxaux=(ConstraintLayout)aux;

                                Button button= (Button) auxaux.getChildAt(1);
                                button.setBackgroundColor(0xFFFF0000);
                                //  actual.cambiaNombre("boton"+i);

                                terminalBT.setOnTouchListener(new MoveViewTouchListener(terminalBT, manag));
                                terminalBT.setTag(actual);
                                terminalesBT_actuales.add(terminalBT);
                                break;

                            case "interruptor":
                                View vista5 = LayoutInflater.from(getContext()).inflate(actual.getTipo(), null);
                                container.addView(vista5, 80, 40);
                                Switch interruptor = (Switch) vista5;
                                if (actual.getFloatX() != null) {
                                    interruptor.setX(actual.getFloatX());
                                    interruptor.setY(actual.getFloatY());
                                }

                                interruptor.setOnTouchListener(new MoveViewTouchListener(interruptor, manag));
                                interruptor.setTag(actual);
                                interruptores_actuales.add(interruptor);
                                break;

                            case "slider":
                                View vista6 = LayoutInflater.from(getContext()).inflate(actual.getTipo(), null);
                                container.addView(vista6, 200, 50);
                                SeekBar slider = (SeekBar) vista6;
                                if (actual.getFloatX() != null) {
                                    slider.setX(actual.getFloatX());
                                    slider.setY(actual.getFloatY());
                                }

                                slider.setOnTouchListener(new MoveViewTouchListener(slider, manag));
                                slider.setTag(actual);
                                sliders_actuales.add(slider);
                                break;
                            case "grafica":
                                View vista7 = LayoutInflater.from(getContext()).inflate(actual.getTipo(), null);
                                container.addView(vista7, 400, 200);
                                ConstraintLayout grafica = (ConstraintLayout) vista7;
                                if (actual.getFloatX() != null) {
                                    grafica.setX(actual.getFloatX());
                                    grafica.setY(actual.getFloatY());
                                }

                                grafica.setOnTouchListener(new MoveViewTouchListener(grafica, manag));
                                grafica.setTag(actual);
                                graficas_actuales.add(grafica);
                                break;


                        }
                        Mando4.attachMando4 = false;
                    }
                }
                break;

            case 0:
                MainActivity.mViewViewModel.getPanel0LiveData().observe(getViewLifecycleOwner(), new Observer<List<Panel0>>() {
                    @Override
                    public void onChanged(@Nullable final List<Panel0> panel0_actualizado) {
                        // Update the cached copy of the words in the adapter.
                        panel0 = panel0_actualizado;
                        if (panel0.size() > 0 & crearBoton) {
                            ultimoelemento0 = panel0.get(panel0.size() - 1);
                            switch (ultimoelemento0.getBoton()) {
                                case "boton":
                                    View view = LayoutInflater.from(getContext()).inflate(ultimoelemento0.getTipo(), null);
                                    container.addView(view, 50, 50);

                                    view.setOnTouchListener(new MoveViewTouchListener(view, manag));
                                    if (ultimoelemento0.getFloatX() != null) {
                                        view.setX(ultimoelemento0.getFloatX());
                                        view.setY(ultimoelemento0.getFloatY());
                                    }
                                    view.setOnClickListener(new botonClick());
                                    Button bton = (Button) view;
                                    bton.setText("" + ultimoelemento0.getId());
                                    bton.setOnTouchListener(new MoveViewTouchListener(bton, manag));
                                    bton.setTag(ultimoelemento0);
                                    botones_actuales.add(bton);
                                    break;
                                case "terminal":
                                    View terminal = LayoutInflater.from(getContext()).inflate(ultimoelemento0.getTipo(), null);
                                    container.addView(terminal, 400, 70);

                                    if (ultimoelemento0.getFloatX() != null) {
                                        terminal.setX(ultimoelemento0.getFloatX());
                                        terminal.setY(ultimoelemento0.getFloatY());
                                    }
                                    ConstraintLayout termi = (ConstraintLayout) terminal;
                                    termi.setOnTouchListener(new MoveViewTouchListener(termi, manag));
                                    termi.setTag(ultimoelemento0);
                                    terminales_actuales.add(termi);
                                    break;
                                case "pantalla":
                                    View pantalla = LayoutInflater.from(getContext()).inflate(ultimoelemento0.getTipo(), null);
                                    container.addView(pantalla, 250, 100);

                                    if (ultimoelemento0.getFloatX() != null) {
                                        pantalla.setX(ultimoelemento0.getFloatX());
                                        pantalla.setY(ultimoelemento0.getFloatY());
                                    }
                                    TextView pant = (TextView) pantalla;
                                    pant.setOnTouchListener(new MoveViewTouchListener(pant, manag));
                                    pant.setTag(ultimoelemento0);
                                    pantallas_actuales.add(pant);
                                    break;

                                case "terminalBT":
                                    View terminalBT = LayoutInflater.from(getContext()).inflate(ultimoelemento0.getTipo(), null);
                                    container.addView(terminalBT, 400, 70);

                                    if (ultimoelemento0.getFloatX() != null) {
                                        terminalBT.setX(ultimoelemento0.getFloatX());
                                        terminalBT.setY(ultimoelemento0.getFloatY());
                                    }

                                    ConstraintLayout termibt = (ConstraintLayout) terminalBT;
                                    View aux=((ConstraintLayout) termibt).getChildAt(0);
                                    ConstraintLayout auxaux=(ConstraintLayout)aux;

                                    Button button= (Button) auxaux.getChildAt(1);
                                    button.setBackgroundColor(0xFFFF0000);

                                    termibt.setOnTouchListener(new MoveViewTouchListener(termibt, manag));
                                    termibt.setTag(ultimoelemento0);
                                    terminalesBT_actuales.add(termibt);
                                    break;
                                case "interruptor":
                                    View interruptor = LayoutInflater.from(getContext()).inflate(ultimoelemento0.getTipo(), null);
                                    container.addView(interruptor, 80, 40);
                                    if (ultimoelemento0.getFloatX() != null) {
                                        interruptor.setX(ultimoelemento0.getFloatX());
                                        interruptor.setY(ultimoelemento0.getFloatY());
                                    }
                                    Switch switch1 = (Switch) interruptor;
                                    switch1.setOnTouchListener(new MoveViewTouchListener(switch1, manag));
                                    switch1.setTag(ultimoelemento0);
                                    interruptores_actuales.add(switch1);
                                    break;
                                case "slider":
                                    View slider = LayoutInflater.from(getContext()).inflate(ultimoelemento0.getTipo(), null);
                                    container.addView(slider, 200, 50);
                                    if (ultimoelemento0.getFloatX() != null) {
                                        slider.setX(ultimoelemento0.getFloatX());
                                        slider.setY(ultimoelemento0.getFloatY());
                                    }
                                    SeekBar sldr = (SeekBar) slider;
                                    sldr.setOnTouchListener(new MoveViewTouchListener(sldr, manag));
                                    sldr.setTag(ultimoelemento0);
                                    sliders_actuales.add(sldr);
                                    break;

                                case "grafica":
                                    View grafica = LayoutInflater.from(getContext()).inflate(ultimoelemento0.getTipo(), null);
                                    container.addView(grafica, 400, 300);
                                    if (ultimoelemento0.getFloatX() != null) {
                                        grafica.setX(ultimoelemento0.getFloatX());
                                        grafica.setY(ultimoelemento0.getFloatY());
                                    }
                                    ConstraintLayout grf = (ConstraintLayout) grafica;
                                    grf.setOnTouchListener(new MoveViewTouchListener(grf, manag));
                                    grf.setTag(ultimoelemento0);
                                    Mando4.graficas_actuales.add(grf);
                                    break;



                            }
                            crearBoton = false;
                        }
                    }
                });
                if (primerInicio0){
                    primerInicio0=false;
                    panel0 = Arrays.asList(mViewViewModel.getPanel0());

                }

                botones_actuales = new ArrayList<Button>();
                terminales_actuales = new ArrayList<ConstraintLayout>();
                pantallas_actuales = new ArrayList<TextView>();
                terminalesBT_actuales =new ArrayList<ConstraintLayout>();
                interruptores_actuales = new ArrayList<Switch>();
                sliders_actuales= new ArrayList<SeekBar>();
                graficas_actuales=new ArrayList<ConstraintLayout>();

                if (panel0 != null & Mando4.attachMando4 == true) {
                    for (int i = 0; i < panel0.size(); i++) {
                        Panel0 actual = panel0.get(i);

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
                                terminal.setOnTouchListener(new MoveViewTouchListener(terminal, manag));
                                terminal.setTag(actual);
                                terminales_actuales.add(terminal);
                                break;
                            case "pantalla":
                                View vista3 = LayoutInflater.from(getContext()).inflate(actual.getTipo(), null);

                                container.addView(vista3, 250, 100);
                                TextView pantalla = (TextView) vista3;
                                if (actual.getFloatX() != null) {
                                    vista3.setX(actual.getFloatX());
                                    vista3.setY(actual.getFloatY());
                                }
                                //  actual.cambiaNombre("boton"+i);
                                pantalla.setTag(actual);
                                pantalla.setOnTouchListener(new MoveViewTouchListener(pantalla, manag));
                                pantallas_actuales.add(pantalla);
                                break;

                            case "terminalBT":
                                View vista4 = LayoutInflater.from(getContext()).inflate(actual.getTipo(), null);

                                container.addView(vista4, 400, 70);
                                ConstraintLayout terminalBT = (ConstraintLayout) vista4;
                                if (actual.getFloatX() != null) {
                                    vista4.setX(actual.getFloatX());
                                    vista4.setY(actual.getFloatY());

                                }
                                View aux=((ConstraintLayout) terminalBT).getChildAt(0);
                                ConstraintLayout auxaux=(ConstraintLayout)aux;

                                Button button= (Button) auxaux.getChildAt(1);
                                button.setBackgroundColor(0xFFFF0000);
                                //  actual.cambiaNombre("boton"+i);

                                terminalBT.setOnTouchListener(new MoveViewTouchListener(terminalBT, manag));
                                terminalBT.setTag(actual);
                                terminalesBT_actuales.add(terminalBT);
                                break;

                            case "interruptor":
                                View vista5 = LayoutInflater.from(getContext()).inflate(actual.getTipo(), null);
                                container.addView(vista5, 80, 40);
                                Switch interruptor = (Switch) vista5;
                                if (actual.getFloatX() != null) {
                                    interruptor.setX(actual.getFloatX());
                                    interruptor.setY(actual.getFloatY());
                                }

                                interruptor.setOnTouchListener(new MoveViewTouchListener(interruptor, manag));
                                interruptor.setTag(actual);
                                interruptores_actuales.add(interruptor);
                                break;

                            case "slider":
                                View vista6 = LayoutInflater.from(getContext()).inflate(actual.getTipo(), null);
                                container.addView(vista6, 200, 50);
                                SeekBar slider = (SeekBar) vista6;
                                if (actual.getFloatX() != null) {
                                    slider.setX(actual.getFloatX());
                                    slider.setY(actual.getFloatY());
                                }

                                slider.setOnTouchListener(new MoveViewTouchListener(slider, manag));
                                slider.setTag(actual);
                                sliders_actuales.add(slider);
                                break;

                            case "grafica":
                                View vista7 = LayoutInflater.from(getContext()).inflate(actual.getTipo(), null);
                                container.addView(vista7, 400, 300);
                                ConstraintLayout grafica = (ConstraintLayout) vista7;
                                if (actual.getFloatX() != null) {
                                    grafica.setX(actual.getFloatX());
                                    grafica.setY(actual.getFloatY());
                                }

                                grafica.setOnTouchListener(new MoveViewTouchListener(grafica, manag));
                                grafica.setTag(actual);
                                graficas_actuales.add(grafica);
                                break;


                        }
                        Mando4.attachMando4 = false;
                    }
                }
                break;
        }






    }

    public void onPause() {
        super.onPause();
        Mando4.attachMando4 = true;
        Mando4.pruebas.setText("" + botones_actuales.size());
        //botones=mViewViewModel.getAllBotones();
        switch (mNumeroPanel){
            case 0:
                for (int i = 0; i < panel0.size(); i++) {
                    Panel0 actualizador = panel0.get(i);
                    switch (actualizador.mBoton) {
                        case "boton":
                            for (int j = 0; j < botones_actuales.size(); j++) {
                                Panel0 auxiliar = (Panel0) botones_actuales.get(j).getTag();
                                if (auxiliar.getId() == actualizador.getId()) {
                                    actualizador.setX(botones_actuales.get(j).getX());
                                    actualizador.setY(botones_actuales.get(j).getY());
                                    mViewViewModel.añadeElementoPanel0(actualizador);
                                }
                            }
                            break;
                        case "terminal":
                            for (int j = 0; j < terminales_actuales.size(); j++) {
                                Panel0 auxiliar = (Panel0) terminales_actuales.get(j).getTag();
                                if (auxiliar.getId() == actualizador.getId()) {
                                    actualizador.setX(terminales_actuales.get(j).getX());
                                    actualizador.setY(terminales_actuales.get(j).getY());
                                    mViewViewModel.añadeElementoPanel0(actualizador);
                                }
                            }
                            break;
                        case "pantalla":
                            for (int j = 0; j < pantallas_actuales.size(); j++) {
                                Panel0 auxiliar = (Panel0) pantallas_actuales.get(j).getTag();
                                if (auxiliar.getId() == actualizador.getId()) {
                                    actualizador.setX(pantallas_actuales.get(j).getX());
                                    actualizador.setY(pantallas_actuales.get(j).getY());
                                    mViewViewModel.añadeElementoPanel0(actualizador);
                                }
                            }
                            break;
                        case "terminalBT":
                            for (int j = 0; j < terminalesBT_actuales.size(); j++) {
                                Panel0 auxiliar = (Panel0) terminalesBT_actuales.get(j).getTag();
                                if (auxiliar.getId() == actualizador.getId()) {
                                    actualizador.setX(terminalesBT_actuales.get(j).getX());
                                    actualizador.setY(terminalesBT_actuales.get(j).getY());
                                    mViewViewModel.añadeElementoPanel0(actualizador);
                                }
                            }
                            break;
                        case "interruptor":
                            for (int j = 0; j < interruptores_actuales.size(); j++) {
                                Panel0 auxiliar = (Panel0) interruptores_actuales.get(j).getTag();
                                if (auxiliar.getId() == actualizador.getId()) {
                                    actualizador.setX(interruptores_actuales.get(j).getX());
                                    actualizador.setY(interruptores_actuales.get(j).getY());
                                    mViewViewModel.añadeElementoPanel0(actualizador);
                                }
                            }
                            break;
                        case "slider":
                            for (int j = 0; j < sliders_actuales.size(); j++) {
                                Panel0 auxiliar = (Panel0) sliders_actuales.get(j).getTag();
                                if (auxiliar.getId() == actualizador.getId()) {
                                    actualizador.setX(sliders_actuales.get(j).getX());
                                    actualizador.setY(sliders_actuales.get(j).getY());
                                    mViewViewModel.añadeElementoPanel0(actualizador);
                                }
                            }
                            break;
                        case "grafica":
                            for (int j = 0; j < graficas_actuales.size(); j++) {
                                Panel0 auxiliar = (Panel0) graficas_actuales.get(j).getTag();
                                if (auxiliar.getId() == actualizador.getId()) {
                                    actualizador.setX(graficas_actuales.get(j).getX());
                                    actualizador.setY(graficas_actuales.get(j).getY());
                                    mViewViewModel.añadeElementoPanel0(actualizador);
                                }
                            }
                            break;

                    }

                }
                break;
            case 4:
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
                        case "pantalla":
                            for (int j = 0; j < pantallas_actuales.size(); j++) {
                                Botones auxiliar = (Botones) pantallas_actuales.get(j).getTag();
                                if (auxiliar.getId() == actualizador.getId()) {
                                    actualizador.setX(pantallas_actuales.get(j).getX());
                                    actualizador.setY(pantallas_actuales.get(j).getY());
                                    mViewViewModel.añadeBoton(actualizador);
                                }
                            }
                            break;
                        case "terminalBT":
                            for (int j = 0; j < terminalesBT_actuales.size(); j++) {
                                Botones auxiliar = (Botones) terminalesBT_actuales.get(j).getTag();
                                if (auxiliar.getId() == actualizador.getId()) {
                                    actualizador.setX(terminalesBT_actuales.get(j).getX());
                                    actualizador.setY(terminalesBT_actuales.get(j).getY());
                                    mViewViewModel.añadeBoton(actualizador);
                                }
                            }
                            break;
                        case "interruptor":
                            for (int j = 0; j < interruptores_actuales.size(); j++) {
                                Botones auxiliar = (Botones) interruptores_actuales.get(j).getTag();
                                if (auxiliar.getId() == actualizador.getId()) {
                                    actualizador.setX(interruptores_actuales.get(j).getX());
                                    actualizador.setY(interruptores_actuales.get(j).getY());
                                    mViewViewModel.añadeBoton(actualizador);
                                }
                            }
                            break;
                        case "slider":
                            for (int j = 0; j < sliders_actuales.size(); j++) {
                                Botones auxiliar = (Botones) sliders_actuales.get(j).getTag();
                                if (auxiliar.getId() == actualizador.getId()) {
                                    actualizador.setX(sliders_actuales.get(j).getX());
                                    actualizador.setY(sliders_actuales.get(j).getY());
                                    mViewViewModel.añadeBoton(actualizador);
                                }
                            }
                            break;

                        case "grafica":
                            for (int j = 0; j < graficas_actuales.size(); j++) {
                                Botones auxiliar = (Botones) graficas_actuales.get(j).getTag();
                                if (auxiliar.getId() == actualizador.getId()) {
                                    actualizador.setX(graficas_actuales.get(j).getX());
                                    actualizador.setY(graficas_actuales.get(j).getY());
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
                break;
        }

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
                    switch (mNumeroPanel){
                        case 0:
                            Panel0 nuevoElementoPanel0;
                            switch (oview.getId()){
                                case R.id.button:
                                    nuevoElementoPanel0 = new Panel0("boton");
                                    nuevoElementoPanel0.setMensaje("R");
                                    nuevoElementoPanel0.setX(event.getX());
                                    nuevoElementoPanel0.setY(event.getY());
                                    nuevoElementoPanel0.setCabecero("r");
                                    nuevoElementoPanel0.setFinal("F");
                                    nuevoElementoPanel0.setTipo(R.layout.boton_rojo);
                                    long ID= mViewViewModel.añadeElementoPanel0(nuevoElementoPanel0);
                                    break;
                                case R.id.boton_azul:

                                    nuevoElementoPanel0 = new Panel0("boton");
                                    nuevoElementoPanel0.setMensaje("A");
                                    nuevoElementoPanel0.setX(event.getX());
                                    nuevoElementoPanel0.setY(event.getY());
                                    nuevoElementoPanel0.setCabecero("a");
                                    nuevoElementoPanel0.setFinal("F");
                                    nuevoElementoPanel0.setTipo(R.layout.boton_azul);
                                    mViewViewModel.añadeElementoPanel0(nuevoElementoPanel0);
                                    break;
                                case R.id.boton_verde:
                                    nuevoElementoPanel0 = new Panel0("boton");
                                    nuevoElementoPanel0.setMensaje("V");
                                    nuevoElementoPanel0.setX(event.getX());
                                    nuevoElementoPanel0.setY(event.getY());
                                    nuevoElementoPanel0.setCabecero("v");
                                    nuevoElementoPanel0.setFinal("F");
                                    nuevoElementoPanel0.setTipo(R.layout.boton_verde);
                                    mViewViewModel.añadeElementoPanel0(nuevoElementoPanel0);
                                    break;

                                case R.id.boton_amarillo:
                                    nuevoElementoPanel0 = new Panel0("boton");
                                    nuevoElementoPanel0.setMensaje("Y");
                                    nuevoElementoPanel0.setX(event.getX());
                                    nuevoElementoPanel0.setY(event.getY());
                                    nuevoElementoPanel0.setCabecero("y");
                                    nuevoElementoPanel0.setFinal("F");
                                    nuevoElementoPanel0.setTipo(R.layout.boton_amarillo);
                                    mViewViewModel.añadeElementoPanel0(nuevoElementoPanel0);
                                    break;
                                case R.id.boton_arriba:
                                    nuevoElementoPanel0 = new Panel0("boton");
                                    nuevoElementoPanel0.setMensaje("U");
                                    nuevoElementoPanel0.setX(event.getX());
                                    nuevoElementoPanel0.setY(event.getY());
                                    nuevoElementoPanel0.setCabecero("u");
                                    nuevoElementoPanel0.setTipo(R.layout.boton_arriba);
                                    nuevoElementoPanel0.setFinal("F");
                                    mViewViewModel.añadeElementoPanel0(nuevoElementoPanel0);
                                    break;
                                case R.id.boton_derecha:
                                    nuevoElementoPanel0 = new Panel0("boton");
                                    nuevoElementoPanel0.setMensaje("D");
                                    nuevoElementoPanel0.setX(event.getX());
                                    nuevoElementoPanel0.setY(event.getY());
                                    nuevoElementoPanel0.setCabecero("d");
                                    nuevoElementoPanel0.setFinal("F");
                                    nuevoElementoPanel0.setTipo(R.layout.boton_derecha);
                                    mViewViewModel.añadeElementoPanel0(nuevoElementoPanel0);
                                    break;
                                case R.id.boton_abajo:
                                    nuevoElementoPanel0 = new Panel0("boton");
                                    nuevoElementoPanel0.setMensaje("B");
                                    nuevoElementoPanel0.setX(event.getX());
                                    nuevoElementoPanel0.setY(event.getY());
                                    nuevoElementoPanel0.setCabecero("b");
                                    nuevoElementoPanel0.setFinal("F");
                                    nuevoElementoPanel0.setTipo(R.layout.boton_abajo);
                                    mViewViewModel.añadeElementoPanel0(nuevoElementoPanel0);
                                    break;
                                case R.id.boton_izquierda:
                                    nuevoElementoPanel0 = new Panel0("boton");
                                    nuevoElementoPanel0.setMensaje("Z");
                                    nuevoElementoPanel0.setX(event.getX());
                                    nuevoElementoPanel0.setY(event.getY());
                                    nuevoElementoPanel0.setCabecero("z");
                                    nuevoElementoPanel0.setFinal("F");
                                    nuevoElementoPanel0.setTipo(R.layout.boton_izquierda);
                                    mViewViewModel.añadeElementoPanel0(nuevoElementoPanel0);
                                    break;

                                case R.id.terminal:
                                    nuevoElementoPanel0 = new Panel0("terminal");
                                    nuevoElementoPanel0.setMensaje("T");
                                    nuevoElementoPanel0.setX(event.getX());
                                    nuevoElementoPanel0.setY(event.getY());
                                    nuevoElementoPanel0.setCabecero("I");
                                    nuevoElementoPanel0.setFinal("F");
                                    nuevoElementoPanel0.setTipo(R.layout.enviar_texto);
                                    mViewViewModel.añadeElementoPanel0(nuevoElementoPanel0);
                                    break;

                                case R.id.pantalla:
                                    nuevoElementoPanel0 = new Panel0("pantalla");
                                    nuevoElementoPanel0.setMensaje("P");
                                    nuevoElementoPanel0.setX(event.getX());
                                    nuevoElementoPanel0.setY(event.getY());
                                    nuevoElementoPanel0.setCabecero("noRECIBIR");
                                    nuevoElementoPanel0.setFinal("F");
                                    nuevoElementoPanel0.setTipo(R.layout.pantalla);
                                    mViewViewModel.añadeElementoPanel0(nuevoElementoPanel0);
                                    break;

                                case R.id.terminalBT:
                                    nuevoElementoPanel0 = new Panel0("terminalBT");
                                    nuevoElementoPanel0.setMensaje("bAT");
                                    nuevoElementoPanel0.setX(event.getX());
                                    nuevoElementoPanel0.setY(event.getY());
                                    nuevoElementoPanel0.setCabecero("I");
                                    nuevoElementoPanel0.setFinal("F");
                                    nuevoElementoPanel0.setTipo(R.layout.terminal_buetooth);
                                    mViewViewModel.añadeElementoPanel0(nuevoElementoPanel0);
                                    break;
                                case R.id.switch1:
                                    nuevoElementoPanel0 = new Panel0("interruptor");
                                    nuevoElementoPanel0.setMensaje("i");
                                    nuevoElementoPanel0.setX(event.getX());
                                    nuevoElementoPanel0.setY(event.getY());
                                    nuevoElementoPanel0.setCabecero("I");
                                    nuevoElementoPanel0.setFinal("F");
                                    nuevoElementoPanel0.setTipo(R.layout.interruptor);
                                    mViewViewModel.añadeElementoPanel0(nuevoElementoPanel0);
                                    break;
                                case R.id.switch2:
                                    nuevoElementoPanel0 = new Panel0("interruptor");
                                    nuevoElementoPanel0.setMensaje("i");
                                    nuevoElementoPanel0.setX(event.getX());
                                    nuevoElementoPanel0.setY(event.getY());
                                    nuevoElementoPanel0.setCabecero("I");
                                    nuevoElementoPanel0.setFinal("F");
                                    nuevoElementoPanel0.setTipo(R.layout.interruptor_rojo);
                                    mViewViewModel.añadeElementoPanel0(nuevoElementoPanel0);
                                    break;
                                case R.id.slider:
                                    nuevoElementoPanel0 = new Panel0("slider");
                                    nuevoElementoPanel0.setMensaje("G");
                                    nuevoElementoPanel0.setX(event.getX());
                                    nuevoElementoPanel0.setY(event.getY());
                                    nuevoElementoPanel0.setCabecero("I");
                                    nuevoElementoPanel0.setFinal("F");
                                    nuevoElementoPanel0.setTipo(R.layout.slider);
                                    mViewViewModel.añadeElementoPanel0(nuevoElementoPanel0);
                                    break;
                                case R.id.grafica:
                                    nuevoElementoPanel0=new Panel0("grafica");
                                    nuevoElementoPanel0.setMensaje("ssnnn");                                 //Scrollable-escalable-autosizeX-autosizeY-scrolltoend
                                    nuevoElementoPanel0.setX(event.getX());
                                    nuevoElementoPanel0.setY(event.getY());
                                    nuevoElementoPanel0.setCabecero("");
                                    nuevoElementoPanel0.setFinal("");
                                    nuevoElementoPanel0.setIDaux(100);                                       //nº de puntos
                                    nuevoElementoPanel0.setIntArg(0);                                        //minX
                                    nuevoElementoPanel0.setIntArg2(100);                                     //maxX
                                    nuevoElementoPanel0.setIntArg3(0);                                       //minY
                                    nuevoElementoPanel0.setIntArg4(100);                                     //maxY
                                    nuevoElementoPanel0.setTipo(R.layout.grafica);
                                    nuevoElementoPanel0.setCabecero("100");                                  //Tamaño eje X
                                    nuevoElementoPanel0.setFinal("100");                                     //tamaño eje Y
                                    mViewViewModel.añadeElementoPanel0(nuevoElementoPanel0);
                                    break;





                            }
                            break;
                        case 4:
                            Botones nuevoBoton;
                            switch (oview.getId()){
                                case R.id.button:
                                    nuevoBoton = new Botones("boton");
                                    nuevoBoton.setMensaje("R");
                                    nuevoBoton.setX(event.getX());
                                    nuevoBoton.setY(event.getY());
                                    nuevoBoton.setCabecero("r");
                                    nuevoBoton.setFinal("F");
                                    nuevoBoton.setTipo(R.layout.boton_rojo);
                                    long ID= mViewViewModel.añadeBoton(nuevoBoton);
                                    break;
                                case R.id.boton_azul:

                                    nuevoBoton = new Botones("boton");
                                    nuevoBoton.setMensaje("A");
                                    nuevoBoton.setX(event.getX());
                                    nuevoBoton.setY(event.getY());
                                    nuevoBoton.setCabecero("a");
                                    nuevoBoton.setFinal("F");
                                    nuevoBoton.setTipo(R.layout.boton_azul);
                                    mViewViewModel.añadeBoton(nuevoBoton);
                                    break;
                                case R.id.boton_verde:
                                    nuevoBoton = new Botones("boton");
                                    nuevoBoton.setMensaje("V");
                                    nuevoBoton.setX(event.getX());
                                    nuevoBoton.setY(event.getY());
                                    nuevoBoton.setCabecero("v");
                                    nuevoBoton.setFinal("F");
                                    nuevoBoton.setTipo(R.layout.boton_verde);
                                    mViewViewModel.añadeBoton(nuevoBoton);
                                    break;

                                case R.id.boton_amarillo:
                                    nuevoBoton = new Botones("boton");
                                    nuevoBoton.setMensaje("Y");
                                    nuevoBoton.setX(event.getX());
                                    nuevoBoton.setY(event.getY());
                                    nuevoBoton.setCabecero("y");
                                    nuevoBoton.setFinal("F");
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
                                    nuevoBoton.setFinal("F");
                                    mViewViewModel.añadeBoton(nuevoBoton);
                                    break;
                                case R.id.boton_derecha:
                                    nuevoBoton = new Botones("boton");
                                    nuevoBoton.setMensaje("D");
                                    nuevoBoton.setX(event.getX());
                                    nuevoBoton.setY(event.getY());
                                    nuevoBoton.setCabecero("d");
                                    nuevoBoton.setFinal("F");
                                    nuevoBoton.setTipo(R.layout.boton_derecha);
                                    mViewViewModel.añadeBoton(nuevoBoton);
                                    break;
                                case R.id.boton_abajo:
                                    nuevoBoton = new Botones("boton");
                                    nuevoBoton.setMensaje("B");
                                    nuevoBoton.setX(event.getX());
                                    nuevoBoton.setY(event.getY());
                                    nuevoBoton.setCabecero("b");
                                    nuevoBoton.setFinal("F");
                                    nuevoBoton.setTipo(R.layout.boton_abajo);
                                    mViewViewModel.añadeBoton(nuevoBoton);
                                    break;
                                case R.id.boton_izquierda:
                                    nuevoBoton = new Botones("boton");
                                    nuevoBoton.setMensaje("Z");
                                    nuevoBoton.setX(event.getX());
                                    nuevoBoton.setY(event.getY());
                                    nuevoBoton.setCabecero("z");
                                    nuevoBoton.setFinal("F");
                                    nuevoBoton.setTipo(R.layout.boton_izquierda);
                                    mViewViewModel.añadeBoton(nuevoBoton);
                                    break;

                                case R.id.terminal:
                                    nuevoBoton = new Botones("terminal");
                                    nuevoBoton.setMensaje("T");
                                    nuevoBoton.setX(event.getX());
                                    nuevoBoton.setY(event.getY());
                                    nuevoBoton.setCabecero("I");
                                    nuevoBoton.setFinal("F");
                                    nuevoBoton.setTipo(R.layout.enviar_texto);
                                    mViewViewModel.añadeBoton(nuevoBoton);
                                    break;

                                case R.id.pantalla:
                                    nuevoBoton = new Botones("pantalla");
                                    nuevoBoton.setMensaje("P");
                                    nuevoBoton.setX(event.getX());
                                    nuevoBoton.setY(event.getY());
                                    nuevoBoton.setCabecero("noRECIBIR");
                                    nuevoBoton.setFinal("F");
                                    nuevoBoton.setTipo(R.layout.pantalla);
                                    mViewViewModel.añadeBoton(nuevoBoton);
                                    break;

                                case R.id.terminalBT:
                                    nuevoBoton = new Botones("terminalBT");
                                    nuevoBoton.setMensaje("bAT");
                                    nuevoBoton.setX(event.getX());
                                    nuevoBoton.setY(event.getY());
                                    nuevoBoton.setCabecero("I");
                                    nuevoBoton.setFinal("F");
                                    nuevoBoton.setTipo(R.layout.terminal_buetooth);
                                    mViewViewModel.añadeBoton(nuevoBoton);
                                    break;
                                case R.id.switch1:
                                    nuevoBoton = new Botones("interruptor");
                                    nuevoBoton.setMensaje("i");
                                    nuevoBoton.setX(event.getX());
                                    nuevoBoton.setY(event.getY());
                                    nuevoBoton.setCabecero("I");
                                    nuevoBoton.setFinal("F");
                                    nuevoBoton.setTipo(R.layout.interruptor);
                                    mViewViewModel.añadeBoton(nuevoBoton);
                                    break;
                                case R.id.switch2:
                                    nuevoBoton = new Botones("interruptor");
                                    nuevoBoton.setMensaje("i");
                                    nuevoBoton.setX(event.getX());
                                    nuevoBoton.setY(event.getY());
                                    nuevoBoton.setCabecero("I");
                                    nuevoBoton.setFinal("F");
                                    nuevoBoton.setTipo(R.layout.interruptor_rojo);
                                    mViewViewModel.añadeBoton(nuevoBoton);
                                    break;
                                case R.id.slider:
                                    nuevoBoton = new Botones("slider");
                                    nuevoBoton.setMensaje("G");
                                    nuevoBoton.setX(event.getX());
                                    nuevoBoton.setY(event.getY());
                                    nuevoBoton.setCabecero("I");
                                    nuevoBoton.setFinal("F");
                                    nuevoBoton.setTipo(R.layout.slider);
                                    mViewViewModel.añadeBoton(nuevoBoton);
                                    break;
                                case R.id.grafica:
                                    nuevoBoton = new Botones("grafica");
                                    nuevoBoton.setMensaje("ssnnn");                                 //Scrollable-escalable-autosizeX-autosizeY-scrolltoend
                                    nuevoBoton.setX(event.getX());
                                    nuevoBoton.setY(event.getY());
                                    nuevoBoton.setCabecero("");
                                    nuevoBoton.setFinal("");
                                    nuevoBoton.setIDaux(100);                                       //nº de puntos
                                    nuevoBoton.setIntArg(0);                                        //minX
                                    nuevoBoton.setIntArg2(100);                                     //maxX
                                    nuevoBoton.setIntArg3(0);                                       //minY
                                    nuevoBoton.setIntArg4(100);                                     //maxY
                                    nuevoBoton.setCabecero("100");                                  //Tamaño eje X
                                    nuevoBoton.setFinal("100");                                     //tamaño eje Y
                                    nuevoBoton.setTipo(R.layout.grafica);
                                    mViewViewModel.añadeBoton(nuevoBoton);
                                    break;





                            }
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
