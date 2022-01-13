package com.example.maqueta_conexion.fragmentosMandos;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maqueta_conexion.BluetoothMainActivity;
import com.example.maqueta_conexion.Botones;
import com.example.maqueta_conexion.Mando4;
import com.example.maqueta_conexion.MoveViewTouchListener;
import com.example.maqueta_conexion.Panel0;
import com.example.maqueta_conexion.PressViewTouchListener;
import com.example.maqueta_conexion.R;
import com.example.maqueta_conexion.botonClick;
import com.example.maqueta_conexion.Fmando4;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.os.Handler;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.LayoutInflater.from;
import static com.example.maqueta_conexion.BluetoothMainActivity.BT_conectado;
import static com.example.maqueta_conexion.BluetoothMainActivity.BT_id;
import static com.example.maqueta_conexion.MainActivity.mViewViewModel;
import static com.example.maqueta_conexion.Mando4.botones_actuales;
import static com.example.maqueta_conexion.Mando4.pantallas_actuales;
import static com.example.maqueta_conexion.Mando4.terminales_actuales;
import static java.lang.Thread.sleep;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fmando4Activado extends Fragment {
    static public ArrayList<TextView> pantallasActivas;
    static public ArrayList<ConstraintLayout> graficasActivas;
   static public LineGraphSeries<DataPoint> serie0 = new LineGraphSeries<>(new DataPoint[] {
    });
    private final Handler mHandler = new Handler();
    private Runnable mTimer;
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
        graficasActivas=new ArrayList<ConstraintLayout>();
        switch (Mando4.numeroPanel){
            case 0:
                if (Fmando4.panel0!=null){
                    for (int i = 0;i<Fmando4.panel0.size();i++){
                        Panel0 actual = Fmando4.panel0.get(i);
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
                                            Panel0 botones= (Panel0) bton.getTag();
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
                                mViewViewModel.añadeElementoPanel0(actual);
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
                                            sleep(12000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();

                                        }
                                        String fin ="IF";
                                        BluetoothMainActivity.envioTrama(fin.getBytes());

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
                            case "slider":
                                //MainActivity.mViewViewModel.borraBoton(botones[i]);
                                View vista6 = from(getContext()).inflate(actual.getTipo(), null);
                                ConstraintLayout container6=view.findViewById(R.id.fragmen_fmando4_activado);
                                container6.addView(vista6, 200, 50);
                                SeekBar sldr=(SeekBar) vista6;
                                sldr.setMax(100);

                                if (actual.getFloatX()!=null){
                                    sldr.setX(actual.getFloatX());
                                    sldr.setY(actual.getFloatY());
                                }
                                //  actual.cambiaNombre("boton"+i);
                                sldr.setTag(actual);
                                sldr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        if (actual.getIDaux()==R.id.radiocambio){
                                            sldr.setMax(actual.mIntArg);
                                            String a= actual.getCabecero();
                                            String b= actual.getMensaje();
                                            String e=String.valueOf(sldr.getProgress());
                                            String c= actual.getUltimo();
                                            String d = a+b+e+c;
                                            BluetoothMainActivity.envioTrama(d.getBytes());

                                        }

                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {
                                        if (actual.getIDaux()==R.id.radiosoltar){
                                            sldr.setMax(actual.mIntArg);
                                            String a= actual.getCabecero();
                                            String b= actual.getMensaje();
                                            String e=String.valueOf(sldr.getProgress());
                                            String c= actual.getUltimo();
                                            String d = a+b+e+c;
                                            BluetoothMainActivity.envioTrama(d.getBytes());
                                            //Log.i("TAG","slider: "+sldr.getProgress());
                                        }

                                    }
                                });

                                break;

                            case "grafica":
                                View vista7 = from(getContext()).inflate(actual.getTipo(), null);
                                ConstraintLayout container7=view.findViewById(R.id.fragmen_fmando4_activado);
                                container7.addView(vista7, 400, 200);
                                ConstraintLayout graflayout=( ConstraintLayout) vista7;
                                from(getContext()).inflate(actual.getTipo(), null);
                                GraphView  graf=(GraphView)graflayout.getChildAt(0);
                                graflayout.getChildAt(1).setVisibility(View.VISIBLE);
                                if (actual.getFloatX()!=null){
                                    graflayout.setX(actual.getFloatX());
                                    graflayout.setY(actual.getFloatY());
                                }
                                char[] chars=actual.getMensaje().toCharArray();
                                if (chars[0]==0x73){                                                            //s scroll
                                    graf.getViewport().setScrollable(true);
                                }else if(chars[0]==0x6E){                                                       //n
                                    graf.getViewport().setScrollable(false);
                                }
                                if (chars[1]==0x73){
                                    graf.getViewport().setScalable(true);
                                }else if(chars[1]==0x6E){
                                    graf.getViewport().setScalable(false);
                                }
                                if (chars[2]==0x73){
                                    graf.getViewport().setXAxisBoundsManual(true);
                                }else if(chars[2]==0x6E){
                                    graf.getViewport().setXAxisBoundsManual(false);
                                }
                                if (chars[3]==0x73){
                                    graf.getViewport().setYAxisBoundsManual(true);
                                }else if(chars[3]==0x6E){
                                    graf.getViewport().setYAxisBoundsManual(false);
                                }
                                if (chars[4]==0x73) {
                                    graf.getViewport().scrollToEnd();
                                }

                                graf.getViewport().setMinX(actual.getIntArg());
                                graf.getViewport().setMaxX(actual.getIntArg2());
                                graf.getViewport().setMinY(actual.getIntArg3());
                                graf.getViewport().setMaxY(actual.getIntArg4());
                                graf.getViewport().setMaxXAxisSize(Integer.valueOf(actual.getCabecero()));
                                graf.getViewport().setMaxYAxisSize(Integer.valueOf(actual.getUltimo()));

                                graf.setTag(actual);
                                //graf.getViewport().setScalable(true);
                                //graf.getViewport().setScrollable(false);

                                //graf.getViewport().scrollToEnd();

                                //graf.getViewport().setXAxisBoundsManual(true);
                                //graf.getViewport().setMinX(0);
                                //graf.getViewport().setMaxX(20);
                                // graf.getViewport().setMinimalViewport(50,50,50,50);
                                //graf.getViewport().setMaxXAxisSize(200);
                                //graf.getViewport().setScrollable(true);

                                graficasActivas.add(graflayout);
                                break;



                        }
                    }
                }
                break;
            case 4:
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
                                mViewViewModel.añadeBoton(actual);
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
                            case "slider":
                                //MainActivity.mViewViewModel.borraBoton(botones[i]);
                                View vista6 = from(getContext()).inflate(actual.getTipo(), null);
                                ConstraintLayout container6=view.findViewById(R.id.fragmen_fmando4_activado);
                                container6.addView(vista6, 200, 50);
                                SeekBar sldr=(SeekBar) vista6;
                                sldr.setMax(100);

                                if (actual.getFloatX()!=null){
                                    sldr.setX(actual.getFloatX());
                                    sldr.setY(actual.getFloatY());
                                }
                                //  actual.cambiaNombre("boton"+i);
                                sldr.setTag(actual);
                                sldr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        if (actual.getIDaux()==R.id.radiocambio){
                                            sldr.setMax(actual.mIntArg);
                                            String a= actual.getCabecero();
                                            String b= actual.getMensaje();
                                            String e=String.valueOf(sldr.getProgress());
                                            String c= actual.getUltimo();
                                            String d = a+b+e+c;
                                            BluetoothMainActivity.envioTrama(d.getBytes());

                                        }

                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {
                                        if (actual.getIDaux()==R.id.radiosoltar){
                                            sldr.setMax(actual.mIntArg);
                                            String a= actual.getCabecero();
                                            String b= actual.getMensaje();
                                            String e=String.valueOf(sldr.getProgress());
                                            String c= actual.getUltimo();
                                            String d = a+b+e+c;
                                            BluetoothMainActivity.envioTrama(d.getBytes());

                                        }

                                    }
                                });

                                break;

                            case "grafica":
                                View vista7 = from(getContext()).inflate(actual.getTipo(), null);
                                ConstraintLayout container7=view.findViewById(R.id.fragmen_fmando4_activado);
                                container7.addView(vista7, 400, 300);
                                ConstraintLayout graflayout=(ConstraintLayout) vista7;
                               // from(getContext()).inflate(actual.getTipo(), null);
                                LinearLayoutCompat barra= (LinearLayoutCompat) graflayout.getChildAt(1);
                                barra.setVisibility(View.VISIBLE);
                                LinearLayoutCompat lin1 =(LinearLayoutCompat) barra.getChildAt(0);
                                CheckBox checkBox1=(CheckBox) lin1.getChildAt(1);

                                LinearLayoutCompat lin2 =(LinearLayoutCompat) barra.getChildAt(1);
                                CheckBox checkBox2=(CheckBox) lin2.getChildAt(1);

                                LinearLayoutCompat lin3 =(LinearLayoutCompat) barra.getChildAt(2);
                                CheckBox checkBox3=(CheckBox) lin3.getChildAt(1);
                                LinearLayoutCompat lin4 =(LinearLayoutCompat) barra.getChildAt(3);
                                CheckBox checkBox4=(CheckBox) lin4.getChildAt(1);
                                LinearLayoutCompat lin5 =(LinearLayoutCompat) barra.getChildAt(4);
                                CheckBox checkBox5=(CheckBox) lin5.getChildAt(1);


                                GraphView  graf=(GraphView)graflayout.getChildAt(0);
                                if (actual.getFloatX()!=null){
                                    graflayout.setX(actual.getFloatX());
                                    graflayout.setY(actual.getFloatY());
                                }
                                char[] chars=actual.getMensaje().toCharArray();
                                if (chars[0]==0x73){           //s?                                                 //s scroll
                                    graf.getViewport().setScrollable(true);
                                    checkBox1.setChecked(true);
                                }else if(chars[0]==0x6E){      //n?                                                 //n
                                    graf.getViewport().setScrollable(false);
                                    checkBox1.setChecked(false);
                                }
                                if (chars[1]==0x73){
                                    graf.getViewport().setScalable(true);
                                    checkBox2.setChecked(true);
                                }else if(chars[1]==0x6E){
                                    graf.getViewport().setScalable(false);
                                    checkBox2.setChecked(false);
                                }
                                if (chars[2]==0x73){
                                    graf.getViewport().setXAxisBoundsManual(true);
                                    checkBox3.setChecked(true);
                                }else if(chars[2]==0x6E){
                                    graf.getViewport().setXAxisBoundsManual(false);
                                    checkBox3.setChecked(false);
                                }
                                if (chars[3]==0x73){
                                    graf.getViewport().setYAxisBoundsManual(true);
                                    checkBox4.setChecked(true);
                                }else if(chars[3]==0x6E){
                                    graf.getViewport().setYAxisBoundsManual(false);
                                    checkBox4.setChecked(false);
                                }
                                if (chars[4]==0x73) {
                                    checkBox1.setChecked(true);
                                    graf.getViewport().scrollToEnd();
                                }else if(chars[4]==0x6E){
                                    checkBox5.setChecked(false);
                                }


                                graf.getViewport().setMinX(actual.getIntArg());
                                graf.getViewport().setMaxX(actual.getIntArg2());
                                graf.getViewport().setMinY(actual.getIntArg3());
                                graf.getViewport().setMaxY(actual.getIntArg4());
                                graf.getViewport().setMaxXAxisSize(Integer.valueOf(actual.getCabecero()));
                                graf.getViewport().setMaxYAxisSize(Integer.valueOf(actual.getUltimo()));
                                graf.addSeries(serie0);

                                graflayout.setTag(actual);



                                //graf.getViewport().setScalable(true);
                                //graf.getViewport().setScrollable(false);

                                //graf.getViewport().scrollToEnd();

                                //graf.getViewport().setXAxisBoundsManual(true);
                                //graf.getViewport().setMinX(0);
                                //graf.getViewport().setMaxX(20);
                               // graf.getViewport().setMinimalViewport(50,50,50,50);
                                //graf.getViewport().setMaxXAxisSize(200);
                                //graf.getViewport().setScrollable(true);

                                graficasActivas.add(graflayout);
                                break;
                        }
                    }
                }
                break;
        }


    }
    @Override
    public void onResume() {

        super.onResume();
        mTimer = new Runnable() {
            @Override
            public void run() {
                if (Fmando4Activado.graficasActivas!=null){
                    for (int i =0; i<Fmando4Activado.graficasActivas.size();i++) {
                        ConstraintLayout graflayout = Fmando4Activado.graficasActivas.get(i);
                        LinearLayoutCompat barra= (LinearLayoutCompat) graflayout.getChildAt(1);
                        barra.setVisibility(View.VISIBLE);
                        LinearLayoutCompat lin1 =(LinearLayoutCompat) barra.getChildAt(0);
                        CheckBox scroll=(CheckBox) lin1.getChildAt(1);

                        LinearLayoutCompat lin2 =(LinearLayoutCompat) barra.getChildAt(1);
                        CheckBox zoom=(CheckBox) lin2.getChildAt(1);

                        LinearLayoutCompat lin3 =(LinearLayoutCompat) barra.getChildAt(2);
                        CheckBox autox=(CheckBox) lin3.getChildAt(1);
                        LinearLayoutCompat lin4 =(LinearLayoutCompat) barra.getChildAt(3);
                        CheckBox autoy=(CheckBox) lin4.getChildAt(1);
                        LinearLayoutCompat lin5 =(LinearLayoutCompat) barra.getChildAt(4);
                        CheckBox STE=(CheckBox) lin5.getChildAt(1);
                        GraphView grafica=(GraphView) graflayout.getChildAt(0);
                        switch (Mando4.numeroPanel){
                            case 0:
                                Panel0 grafaux =(Panel0) Fmando4Activado.graficasActivas.get(i).getTag();
                                if (grafaux!=null){
                                    Panel0 graf=mViewViewModel.getViewByIDPanel0(grafaux.getId());
                                }

                                break;
                            case 4:

                                Botones graf4aux =(Botones) Fmando4Activado.graficasActivas.get(i).getTag();
                                if (graf4aux!=null){
                                    Botones graf4=mViewViewModel.getBoton(graf4aux.getId());

                                }

                                //serie0.appendData(new DataPoint(grafica, getRandom()), true, 40);
                                //grafica.addSeries(serie0);
                                break;
                        }
                    }
                }

                mHandler.postDelayed(this, 1000);
            }
        };
        mHandler.postDelayed(mTimer, 1000);
    }

    @Override
    public void onPause() {

        mHandler.removeCallbacks(mTimer);
        super.onPause();
    }
}
