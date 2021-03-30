package com.example.maqueta_conexion.fragmentosMandos;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maqueta_conexion.BluetoothMainActivity;
import com.example.maqueta_conexion.Botones;
import com.example.maqueta_conexion.Mando4;
import com.example.maqueta_conexion.MoveViewTouchListener;
import com.example.maqueta_conexion.PressViewTouchListener;
import com.example.maqueta_conexion.R;
import com.example.maqueta_conexion.botonClick;
import com.example.maqueta_conexion.Fmando4;

import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.LayoutInflater.from;
import static com.example.maqueta_conexion.BluetoothMainActivity.BT_conectado;
import static com.example.maqueta_conexion.BluetoothMainActivity.BT_id;
import static com.example.maqueta_conexion.Mando4.botones_actuales;
import static com.example.maqueta_conexion.Mando4.pantallas_actuales;
import static com.example.maqueta_conexion.Mando4.terminales_actuales;
import static java.lang.Thread.sleep;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fmando4Activado extends Fragment {
    static public ArrayList<TextView> pantallasActivas;
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
        Intent intent= getActivity().getIntent();
        pantallasActivas = new ArrayList<TextView>();
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
                        bton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String a= actual.getCabecero();
                                String b = actual.getMensaje();
                                String d = actual.getUltimo();
                                String c =a+b+d;
                                BluetoothMainActivity.envioTrama( c.getBytes());
                            }
                        });
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
                                    String d=botones.getUltimo();
                                    String c =a+b+d;
                                    BluetoothMainActivity.envioTrama( c.getBytes());
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
                        EditText editText= (EditText) auxaux.getChildAt(0);
                        editText.setVisibility(View.VISIBLE);
                        editText.setText(actual.getMensaje());
                        Button botonEnviar=(Button)auxaux.getChildAt(1);
                        botonEnviar.setTag(editText);
                        botonEnviar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Button btn= (Button)v;
                                String a= actual.getCabecero();
                                String b= String.valueOf(editText.getText());
                                String c= actual.getUltimo();
                                String d = a +b+c;
                                BluetoothMainActivity.envioTrama( d.getBytes());
                            }
                        });
                       // termi.getChildAt(0).setBackgroundColor(Color.LTGRAY);
                        //view.findViewById(R.id.msg).setVisibility(View.VISIBLE);
                       // view.findViewById(R.id.msg).setBackgroundColor(Color.LTGRAY);
                       break;

                    case "pantalla":
                        View vista3 = from(getContext()).inflate(actual.getTipo(), null);
                        ConstraintLayout container3=view.findViewById(R.id.fragmen_fmando4_activado);

                        container3.addView(vista3, 250, 100);
                        TextView panta=(TextView) vista3;
                        panta.setMovementMethod(new ScrollingMovementMethod());
                        from(getContext()).inflate(actual.getTipo(), null);
                        if (actual.getFloatX()!=null){
                            panta.setX(actual.getFloatX());
                            panta.setY(actual.getFloatY());
                        }
                        actual.setCabecero("noRECIBIR");
                        Fmando4.mViewViewModel.añadeBoton(actual);
                        panta.setTag(actual);
                        pantallasActivas.add(panta);



                        // termi.getChildAt(0).setBackgroundColor(Color.LTGRAY);
                        //view.findViewById(R.id.msg).setVisibility(View.VISIBLE);
                        // view.findViewById(R.id.msg).setBackgroundColor(Color.LTGRAY);
                        break;

                    case "terminalBT":
                        View vista4 = from(getContext()).inflate(actual.getTipo(), null);
                        ConstraintLayout container4=view.findViewById(R.id.fragmen_fmando4_activado);
                        container4.addView(vista4, 400, 50);
                        ConstraintLayout termiBT=(ConstraintLayout) vista4;
                        from(getContext()).inflate(actual.getTipo(), null);
                        if (actual.getFloatX()!=null){
                            termiBT.setX(actual.getFloatX());
                            termiBT.setY(actual.getFloatY());
                        }
                        View aux2=((ConstraintLayout) vista4).getChildAt(0);
                        ConstraintLayout auxaux2=(ConstraintLayout)aux2;
                        // auxaux.getChildAt(0).setBackgroundColor(Color.LTGRAY);
                        EditText editText2= (EditText) auxaux2.getChildAt(0);
                        editText2.setVisibility(View.VISIBLE);
                        editText2.setText(actual.getMensaje());
                        Button botonEnviarBT=(Button)auxaux2.getChildAt(1);
                        botonEnviarBT.setTag(editText2);
                        botonEnviarBT.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Button btn= (Button)v;
                                String a= actual.getCabecero();
                                String b= String.valueOf(editText2.getText());
                                String c= actual.getUltimo();
                                String d = a +b+c;
                                BluetoothMainActivity.envioTrama( d.getBytes());
                                BluetoothMainActivity.connectThread.cancel();

                             /*   Mando4.conectar_mando=11;
                                Intent intentBluetoothActivity =new Intent(getContext(), BluetoothMainActivity.class);
                                startActivity(intentBluetoothActivity);*/



                                BluetoothDevice device = BluetoothMainActivity.mBluetoothAdapter.getRemoteDevice(BT_id);
                                if (BluetoothMainActivity.connectThread !=null){
                                    BluetoothMainActivity.connectThread.cancel();
                                }
                                BluetoothMainActivity.connectThread = new BluetoothMainActivity.ConnectThread(device);

                                int loop =0;

                                try {
                                    sleep(7000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();

                                }

                                BluetoothMainActivity.connectThread.start();

                                try {
                                    sleep(7000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();

                                }
                                String fin ="F";
                                BluetoothMainActivity.gestionBoton(fin.getBytes());

                            }
                        });
                        // termi.getChildAt(0).setBackgroundColor(Color.LTGRAY);
                        //view.findViewById(R.id.msg).setVisibility(View.VISIBLE);
                        // view.findViewById(R.id.msg).setBackgroundColor(Color.LTGRAY);
                        break;

                    case "interruptor":
                        View vista5 = from(getContext()).inflate(actual.getTipo(), null);
                        ConstraintLayout container5=view.findViewById(R.id.fragmen_fmando4_activado);
                        container5.addView(vista5, 80, 40);
                        Switch interruptor=(Switch) vista5;
                       // from(getContext()).inflate(actual.getTipo(), null);
                        if (actual.getFloatX()!=null){
                            interruptor.setX(actual.getFloatX());
                            interruptor.setY(actual.getFloatY());
                        }
                        if (actual.getIDaux()==R.id.radioOn ){
                            interruptor.setChecked(true);
                        }
                        if (actual.getIDaux()==R.id.radioOff ){
                            interruptor.setChecked(false);
                        }
                        if (actual.getTipo()==R.layout.interruptor){
                            interruptor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked){
                                        BluetoothMainActivity.envioTrama( actual.getMensaje().getBytes());
                                        BluetoothDevice device = BluetoothMainActivity.mBluetoothAdapter.getRemoteDevice(BT_id);
                                        if (BluetoothMainActivity.connectThread !=null){
                                            BluetoothMainActivity.connectThread.cancel();
                                        }
                                        BluetoothMainActivity.connectThread = new BluetoothMainActivity.ConnectThread(device);
                                        BluetoothMainActivity.connectThread.start();
                                    }
                                    else{
                                        BluetoothMainActivity.envioTrama( actual.getCabecero().getBytes());
                                        BluetoothMainActivity.connectThread.cancel();
                                    }
                                }
                            });

                        }else{
                            interruptor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked){
                                        BluetoothMainActivity.envioTrama( actual.getMensaje().getBytes());

                                    }
                                    else{
                                        BluetoothMainActivity.envioTrama( actual.getCabecero().getBytes());

                                    }
                                }
                            });

                        }


                        break;



                }
            }
        }
    }
}
