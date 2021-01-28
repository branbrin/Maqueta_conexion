package com.example.maqueta_conexion.fragmentosMandos;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maqueta_conexion.BluetoothMainActivity;
import com.example.maqueta_conexion.Botones;
import com.example.maqueta_conexion.MoveViewTouchListener;
import com.example.maqueta_conexion.PressViewTouchListener;
import com.example.maqueta_conexion.R;
import com.example.maqueta_conexion.botonClick;
import com.example.maqueta_conexion.Fmando4;

import android.os.Handler;

import static android.view.LayoutInflater.from;
import static com.example.maqueta_conexion.Mando4.botones_actuales;
import static com.example.maqueta_conexion.Mando4.terminales_actuales;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fmando4Activado extends Fragment {

    public Fmando4Activado() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fmando4_activado, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Fmando4.botones!=null){
            for (int i = 0;i<Fmando4.botones.size();i++){
                com.example.maqueta_conexion.Botones actual = Fmando4.botones.get(i);
                switch (actual.getBoton()){
                    case "boton":
                        //MainActivity.mViewViewModel.borraBoton(botones[i]);
                        View vista = from(getContext()).inflate(actual.getTipo(), null);
                        ConstraintLayout container=view.findViewById(R.id.fragmen_fmando4_activado);
                        container.addView(vista, 50, 50);
                        Button bton=(Button)vista;

                        bton.setText(""+actual.getCabecero());
                        if (actual.getFloatX()!=null){
                            bton.setX(actual.getFloatX());
                            bton.setY(actual.getFloatY());
                        }
                        //  actual.cambiaNombre("boton"+i);
                        bton.setTag(actual);
                        bton.setOnClickListener(new botonClick());
                        //  bton.setOnTouchListener(new PressViewTouchListener(bton));
                        bton.setOnTouchListener(new View.OnTouchListener() {
                            private Handler mHandler;
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch(event.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        if (mHandler != null) return true;
                                        mHandler = new Handler();
                                        mHandler.postDelayed(mAction, 500);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        if (mHandler == null) return true;
                                        mHandler.removeCallbacks(mAction);
                                        mHandler = null;
                                        break;
                                }
                                return false;
                            }
                            Runnable mAction = new Runnable() {
                                @Override public void run() {
                                    System.out.println("Performing action...");
                                    Botones botones= (Botones) bton.getTag();
                                    String a= botones.getCabecero();
                                    String b = botones.getMensaje();
                                    String c =a+b;
                                    BluetoothMainActivity.gestionBoton( c.getBytes());
                                    mHandler.postDelayed(this, 500);
                                }
                            };
                        });
                        break;
                    case "terminal":
                        View vista2 = from(getContext()).inflate(actual.getTipo(), null);
                        ConstraintLayout container2=view.findViewById(R.id.fragmen_fmando4_activado);
                        container2.addView(vista2, 400, 50);
                        ConstraintLayout termi=(ConstraintLayout) vista2;
                        from(getContext()).inflate(actual.getTipo(), null);
                        if (actual.getFloatX()!=null){
                            termi.setX(actual.getFloatX());
                            termi.setY(actual.getFloatY());
                        }
                        View aux=((ConstraintLayout) vista2).getChildAt(0);
                        ConstraintLayout auxaux=(ConstraintLayout)aux;
                       // auxaux.getChildAt(0).setBackgroundColor(Color.LTGRAY);
                        auxaux.getChildAt(0).setVisibility(View.VISIBLE);
                       // termi.getChildAt(0).setBackgroundColor(Color.LTGRAY);
                        //view.findViewById(R.id.msg).setVisibility(View.VISIBLE);
                       // view.findViewById(R.id.msg).setBackgroundColor(Color.LTGRAY);
                       break;
                }
            }
        }
    }
}
