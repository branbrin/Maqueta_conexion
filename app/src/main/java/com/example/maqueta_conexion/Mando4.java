package com.example.maqueta_conexion;

//import android.arch.lifecycle.ViewModelProvider;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
//import android.support.constraint.ConstraintLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.maqueta_conexion.fragmentosMandos.Fmando4Activado;
import com.example.maqueta_conexion.fragmentosPaneles.Acelerometro;
import com.example.maqueta_conexion.fragmentosPaneles.Borrar;
import com.example.maqueta_conexion.fragmentosPaneles.Botones;
import com.example.maqueta_conexion.fragmentosPaneles.Cosas;
import com.example.maqueta_conexion.fragmentosPaneles.Graficos;
import com.example.maqueta_conexion.fragmentosPaneles.Indicadores;
import com.example.maqueta_conexion.fragmentosPaneles.Interruptores;
import com.example.maqueta_conexion.fragmentosPaneles.Malla;
import com.example.maqueta_conexion.fragmentosPaneles.Mandos;
import com.example.maqueta_conexion.fragmentosPaneles.Sliders;
import com.example.maqueta_conexion.fragmentosPaneles.Terminales;
import com.example.maqueta_conexion.fragmentosPaneles.Texto;
import com.jjoe64.graphview.GraphView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static com.example.maqueta_conexion.BluetoothMainActivity.*;
import static com.example.maqueta_conexion.Fmando4.botones;
import static com.example.maqueta_conexion.Fmando4.panel0;
import static com.example.maqueta_conexion.MainActivity.mViewViewModel;


public class Mando4 extends AppCompatActivity {

    static public ArrayList<Button> botones_actuales;
    static public ArrayList<ConstraintLayout> terminales_actuales;
    static public ArrayList<TextView> pantallas_actuales;
    static public ArrayList<ConstraintLayout> terminalesBT_actuales;
    static public ArrayList<Switch> interruptores_actuales;
    static public ArrayList<SeekBar> sliders_actuales;
    static public ArrayList<ConstraintLayout> graficas_actuales;

    static public int numeroPanel;
    public static int conectar_mando=-1;
    FragmentManager fragmentManager = getSupportFragmentManager();
    public static boolean attachMando4=true;
    public static TextView pruebas;
    public static AlertDialog dialog;

    TextView panelTexto;
    TextView panelBotones;
    TextView panelInterruptores;
    TextView panelSlider;
    TextView panelMandos;
    TextView panelIndicadores;
    TextView panelGraficos;
    TextView panelAcelerometro;
    TextView panelTerminales;
    TextView panelTamañomalla;
    TextView panelMalla;
    TextView panelCosas;
    ConstraintLayout constraintLayout;
    ViewGroup guardado;
    static View botonview;
    public  static  ConstraintLayout popupContainer;
    public static AlertDialog.Builder builderBotones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mando4);
        Intent intent=getIntent();
        numeroPanel= intent.getIntExtra("numeroPanel",-1);
        boolean activar = intent.getBooleanExtra("ejecutar",false);


        pruebas=findViewById(R.id.TVpruebas);
        pruebas.setText(""+numeroPanel);
        panelTexto= findViewById(R.id.panelTexto);
        panelBotones=findViewById(R.id.textView3);
        panelInterruptores=findViewById(R.id.textView4);
        panelSlider=findViewById(R.id.textView5);
        panelMandos=findViewById(R.id.textView6);
        panelIndicadores=findViewById(R.id.textView7);
        panelGraficos=findViewById(R.id.textView8);
        panelAcelerometro=findViewById(R.id.textView9);
        panelTerminales=findViewById(R.id.textView10);
        panelTamañomalla=findViewById(R.id.textView11);
        panelMalla=findViewById(R.id.textView12);
        panelCosas=findViewById(R.id.textView13);
        View customLayout =  LayoutInflater.from(this).inflate(R.layout.popup_botones, null);

       /* builderBotones = new AlertDialog.Builder(this);
        builderBotones.setView(customLayout);
        builderBotones.setTitle("CONFIGURAR BOTÓN");

            // Add the buttons
        builderBotones.setPositiveButton(R.string.Aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button

            }
        });
        builderBotones.setNegativeButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        dialog = Mando4.builderBotones.create();



        */


       // final View testItem = findViewById(R.id.button3);
       // testItem.setOnTouchListener(new MoveViewTouchListener(testItem));
       // constraintLayout=findViewById(R.id.constraintLayout4);
       // constraintLayout.setOnDragListener(new MyDragListener());
       //   View cosa = new View(this);


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        fragmentTransaction.add(R.id.constraintLayout4, new Fmando4(numeroPanel),"fmando4" );
        fragmentTransaction.commit();
        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
        if (activar){
            findViewById(R.id.constraintLayout4).setVisibility(View.INVISIBLE);
            findViewById(R.id.paneles).setVisibility(View.INVISIBLE);
            findViewById(R.id.layouteditar).setVisibility(View.INVISIBLE);
            fragmentTransaction2.replace(R.id.constraint4, new Fmando4Activado(),"Fmando4");
            fragmentTransaction2.commit();
        }





    }


    //-----------------------------MENU----------------------------------------------------------------
    public boolean onCreateOptionsMenu(Menu menu){
        //Uso método getMenuInflater de AppCompactActivity para controlar la creación  del menu
        MenuInflater inflater = getMenuInflater();
        //Asigno mi menu_conectar al creador
        inflater.inflate(R.menu.menu_mando4, menu);



        return true;

    }

    public class TryMeActivity extends
            FragmentActivity implements GeneralDialogFragment.OnDialogFragmentClickListener {

        @Override
        public void onOkClicked(GeneralDialogFragment dialog) {
            // do your stuff
        }

        @Override
        public void onCancelClicked(GeneralDialogFragment dialog) {
            // do your stuff
        }
    }
/*
   // public class DialogFragment extends DialogFragment {
  //      @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplication());
            // Get the layout inflater
            LayoutInflater inflater = getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.popup_botones, null))
                    // Add action buttons
                    .setPositiveButton(R.string.Aceptar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // sign in the user ...
                        }
                    })
                    .setNegativeButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            return builder.create();
        }
   // }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Fmando4Activado fmando4Activado=new Fmando4Activado();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Se pulsó boton del menu "Habilitar bluetooth"
        if (id == R.id.activar_mando){
            findViewById(R.id.constraintLayout4).setVisibility(View.INVISIBLE);
            findViewById(R.id.paneles).setVisibility(View.INVISIBLE);
            findViewById(R.id.layouteditar).setVisibility(View.INVISIBLE);
            fragmentTransaction.replace(R.id.constraint4, fmando4Activado,"Fmando4");
            fragmentTransaction.commit();
            pruebas.setText("Holi!");
            switch (numeroPanel){
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
                                    Panel0 auxiliar = (com.example.maqueta_conexion.Panel0) pantallas_actuales.get(j).getTag();
                                    if (auxiliar.getId() == actualizador.getId()) {
                                        actualizador.setX(pantallas_actuales.get(j).getX());
                                        actualizador.setY(pantallas_actuales.get(j).getY());
                                        mViewViewModel.añadeElementoPanel0(actualizador);
                                    }
                                }
                                break;

                            case "terminalBT":
                                for (int j = 0; j < terminalesBT_actuales.size(); j++) {
                                    com.example.maqueta_conexion.Panel0 auxiliar = (com.example.maqueta_conexion.Panel0) terminalesBT_actuales.get(j).getTag();
                                    if (auxiliar.getId() == actualizador.getId()) {
                                        actualizador.setX(terminalesBT_actuales.get(j).getX());
                                        actualizador.setY(terminalesBT_actuales.get(j).getY());
                                        mViewViewModel.añadeElementoPanel0(actualizador);
                                    }
                                }
                                break;

                            case "interruptor":
                                for (int j = 0; j < interruptores_actuales.size(); j++) {
                                    com.example.maqueta_conexion.Panel0 auxiliar = (com.example.maqueta_conexion.Panel0) interruptores_actuales.get(j).getTag();
                                    if (auxiliar.getId() == actualizador.getId()) {
                                        actualizador.setX(interruptores_actuales.get(j).getX());
                                        actualizador.setY(interruptores_actuales.get(j).getY());
                                        mViewViewModel.añadeElementoPanel0(actualizador);
                                    }
                                }
                                break;

                            case "grafica":
                                for (int j = 0; j < graficas_actuales.size(); j++) {
                                    com.example.maqueta_conexion.Panel0 auxiliar = (com.example.maqueta_conexion.Panel0) graficas_actuales.get(j).getTag();
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
                        com.example.maqueta_conexion.Botones actualizador = botones.get(i);
                        switch (actualizador.mBoton) {
                            case "boton":
                                for (int j = 0; j < botones_actuales.size(); j++) {
                                    com.example.maqueta_conexion.Botones auxiliar = (com.example.maqueta_conexion.Botones) botones_actuales.get(j).getTag();
                                    if (auxiliar.getId() == actualizador.getId()) {
                                        actualizador.setX(botones_actuales.get(j).getX());
                                        actualizador.setY(botones_actuales.get(j).getY());
                                        mViewViewModel.añadeBoton(actualizador);
                                    }
                                }
                                break;
                            case "terminal":
                                for (int j = 0; j < terminales_actuales.size(); j++) {
                                    com.example.maqueta_conexion.Botones auxiliar = (com.example.maqueta_conexion.Botones) terminales_actuales.get(j).getTag();
                                    if (auxiliar.getId() == actualizador.getId()) {
                                        actualizador.setX(terminales_actuales.get(j).getX());
                                        actualizador.setY(terminales_actuales.get(j).getY());
                                        mViewViewModel.añadeBoton(actualizador);
                                    }
                                }
                                break;

                            case "pantalla":
                                for (int j = 0; j < pantallas_actuales.size(); j++) {
                                    com.example.maqueta_conexion.Botones auxiliar = (com.example.maqueta_conexion.Botones) pantallas_actuales.get(j).getTag();
                                    if (auxiliar.getId() == actualizador.getId()) {
                                        actualizador.setX(pantallas_actuales.get(j).getX());
                                        actualizador.setY(pantallas_actuales.get(j).getY());
                                        mViewViewModel.añadeBoton(actualizador);
                                    }
                                }
                                break;

                            case "terminalBT":
                                for (int j = 0; j < terminalesBT_actuales.size(); j++) {
                                    com.example.maqueta_conexion.Botones auxiliar = (com.example.maqueta_conexion.Botones) terminalesBT_actuales.get(j).getTag();
                                    if (auxiliar.getId() == actualizador.getId()) {
                                        actualizador.setX(terminalesBT_actuales.get(j).getX());
                                        actualizador.setY(terminalesBT_actuales.get(j).getY());
                                        mViewViewModel.añadeBoton(actualizador);
                                    }
                                }
                                break;

                            case "interruptor":
                                for (int j = 0; j < interruptores_actuales.size(); j++) {
                                    com.example.maqueta_conexion.Botones auxiliar = (com.example.maqueta_conexion.Botones) interruptores_actuales.get(j).getTag();
                                    if (auxiliar.getId() == actualizador.getId()) {
                                        actualizador.setX(interruptores_actuales.get(j).getX());
                                        actualizador.setY(interruptores_actuales.get(j).getY());
                                        mViewViewModel.añadeBoton(actualizador);
                                    }
                                }
                                break;

                            case "grafica":
                                for (int j = 0; j < graficas_actuales.size(); j++) {
                                    com.example.maqueta_conexion.Botones auxiliar = (com.example.maqueta_conexion.Botones) graficas_actuales.get(j).getTag();
                                    if (auxiliar.getId() == actualizador.getId()) {
                                        actualizador.setX(graficas_actuales.get(j).getX());
                                        actualizador.setY(graficas_actuales.get(j).getY());
                                        mViewViewModel.añadeBoton(actualizador);
                                    }
                                }
                                break;
                        }

                    }
                    break;
            }

          /*  if (botones_actuales.size()>0){
                    for(int i=0;i<botones_actuales.size();i++){
                    com.example.maqueta_conexion.Botones actualizador=Fmando4.botones.get(i);
                    actualizador.setX(botones_actuales.get(i).getX());
                    actualizador.setY(botones_actuales.get(i).getY());
                    Fmando4.mViewViewModel.añadeBoton(actualizador);
                }
            }*/
        }
        if (id == R.id.modo_editar){
            findViewById(R.id.constraintLayout4).setVisibility(View.VISIBLE);
            findViewById(R.id.paneles).setVisibility(View.VISIBLE);
            findViewById(R.id.layouteditar).setVisibility(View.VISIBLE);
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("Fmando4");
            if(fragment != null) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
        if (id==R.id.conectar){

              /*  primerInicio=false;
                Intent intentreset = getIntent();
                finish();

                startActivity(intentreset);
*/


            conectar_mando=4;
           Intent intentBluetoothActivity =new Intent(this, BluetoothMainActivity.class);
            startActivity(intentBluetoothActivity);
        }
        return super.onOptionsItemSelected(item);

    }
    //--------------------------------FIN MENU----------------------------------------------------

    public  void resetFmando4(){
       Fragment fragment = getSupportFragmentManager().findFragmentByTag("Fmando4");
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.constraintLayout4, new Fmando4(),"Fmando4" );
        fragmentTransaction.commit();
    }

   /* public void onStart(){
        super.onStart();
        if (primerInicio){
            primerInicio=false;
            Intent intentreset = getIntent();
            finish();


            startActivity(intentreset);

           // resetFmando4();
        }
    }*/
    /*
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


                    switch (oview.getId()){
                        case R.id.button:

                            View view = LayoutInflater.from(Mando4.this).inflate(R.layout.boton_rojo, null);
                            ConstraintLayout container = (ConstraintLayout) v;
                            container.addView(view, 50, 50);

                            view.setOnTouchListener(new MoveViewTouchListener(view));

                            view.setX(event.getX());
                            view.setY(event.getY());
                            guardado.addView(constraintLayout);

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

     */
    protected void onPause(){
        super.onPause();



        //obtener bitmap
        ConstraintLayout layout= findViewById(R.id.constraintLayout4);
        Bitmap bmp = screenShot(layout);
        switch (numeroPanel){
            case  0:
                MainActivity.imageView0.setImageBitmap(bmp);
                break;
            case 4:
                MainActivity.imageView4.setImageBitmap(bmp);
                break;
        }

        //convertir bitmap a Byte[]
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        //guardar en almacenamiento interno
        try {
            FileOutputStream outputStream = getApplicationContext().openFileOutput("imagen"+numeroPanel+".png", Context.MODE_PRIVATE);
            outputStream.write(byteArray);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }



    }
    public  Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public void mostrar_botones(View view){
        Botones botones = new Botones();
        TextView textView=(TextView)view;
        selector(textView,botones);



    }
  /*  protected void onResume(){
        super.onResume();
        resetFmando4();
    }*/


    public void mostrar_texto(View view){
        TextView textView=(TextView)view;
        Texto texto = new Texto();
        selector(textView,texto);


        //fragmentTransaction.commitAllowingStateLoss();
    }
    public void mostrar_interruptores(View view){
        Interruptores interruptores = new Interruptores();
        TextView textView=(TextView)view;
        selector(textView,interruptores);

    }
    public void mostrar_sliders(View view){
        Sliders sliders = new Sliders();
        TextView textView=(TextView)view;
        selector(textView,sliders);

    }
    public void mostrar_mandos(View view){
        Mandos mandos = new Mandos();
        TextView textView=(TextView)view;
        selector(textView,mandos);

    }
    public void mostrar_indicadores(View view){
        Indicadores indicadores = new Indicadores();
        TextView textView=(TextView)view;
        selector(textView,indicadores);

    }
    public void mostrar_graficos(View view){
        Graficos graficos = new Graficos();
        TextView textView=(TextView)view;
        selector(textView,graficos);

    }
    public void mostrar_acelerometro(View view){
        Acelerometro acelerometro = new Acelerometro();
        TextView textView=(TextView)view;
        selector(textView,acelerometro);

    }
    public void mostrar_terminales(View view){
        Terminales terminales = new Terminales();
        TextView textView=(TextView)view;
        selector(textView,terminales);

    }
    public void mostrar_tamaño(View view){
        Malla malla = new Malla();
        TextView textView=(TextView)view;
        selector(textView,malla);

    }
    public void mostrar_borrar(View view){
        Borrar borrar = new Borrar();
        TextView textView=(TextView)view;
        selector(textView,borrar);

    }
    public void mostrar_cosas(View view){
        Cosas cosas = new Cosas();
        TextView textView=(TextView)view;
        selector(textView,cosas);

    }



    public  void selector (TextView textView, Fragment fragment){

        panelTexto.setBackgroundColor(0x80C9CC);
        panelBotones.setBackgroundColor(0x80C9CC);
        panelInterruptores.setBackgroundColor(0x80C9CC);
        panelSlider.setBackgroundColor(0x80C9CC);
        panelMandos.setBackgroundColor(0x80C9CC);
        panelIndicadores.setBackgroundColor(0x80C9CC);
        panelGraficos.setBackgroundColor(0x80C9CC);
        panelAcelerometro.setBackgroundColor(0x80C9CC);
        panelTerminales.setBackgroundColor(0x80C9CC);
        panelTamañomalla.setBackgroundColor(0x80C9CC);
        panelMalla.setBackgroundColor(0x80C9CC);
        panelCosas.setBackgroundColor(0x80C9CC);
        textView.setBackgroundColor(Color.YELLOW);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.paneles, fragment );
        fragmentTransaction.commit();
    }





}
