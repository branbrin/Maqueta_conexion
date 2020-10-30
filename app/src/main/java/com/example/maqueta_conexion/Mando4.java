package com.example.maqueta_conexion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.maqueta_conexion.fragmentosMandos.Fmando4;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Mando4 extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();



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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mando4);
        Intent intent=getIntent();
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
       // final View testItem = findViewById(R.id.button3);
       // testItem.setOnTouchListener(new MoveViewTouchListener(testItem));
       // constraintLayout=findViewById(R.id.constraintLayout4);
       // constraintLayout.setOnDragListener(new MyDragListener());
     //   View cosa = new View(this);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.constraintLayout4, new Fmando4() );
        fragmentTransaction.commit();
    }
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
        ConstraintLayout layout= findViewById(R.id.constraint4);
        Bitmap bmp = screenShot(layout);
        MainActivity.imageView4.setImageBitmap(bmp);
        //convertir bitmap a Byte[]
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        //guardar en almacenamiento interno
        try {
            FileOutputStream outputStream = getApplicationContext().openFileOutput("imagen"+MainActivity.maquetaID+".png", Context.MODE_PRIVATE);
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

        panelTexto.setBackgroundColor(Color.WHITE);
        panelBotones.setBackgroundColor(Color.WHITE);
        panelInterruptores.setBackgroundColor(Color.WHITE);
        panelSlider.setBackgroundColor(Color.WHITE);
        panelMandos.setBackgroundColor(Color.WHITE);
        panelIndicadores.setBackgroundColor(Color.WHITE);
        panelGraficos.setBackgroundColor(Color.WHITE);
        panelAcelerometro.setBackgroundColor(Color.WHITE);
        panelTerminales.setBackgroundColor(Color.WHITE);
        panelTamañomalla.setBackgroundColor(Color.WHITE);
        panelMalla.setBackgroundColor(Color.WHITE);
        panelCosas.setBackgroundColor(Color.WHITE);
        textView.setBackgroundColor(Color.YELLOW);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.paneles, fragment );
        fragmentTransaction.commit();
    }




}
