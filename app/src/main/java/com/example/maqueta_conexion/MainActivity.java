package com.example.maqueta_conexion;

import android.annotation.SuppressLint;
import android.app.Application;
//import android.arch.lifecycle.ViewModelProvider;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
//import android.support.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.maqueta_conexion.fragmentosMandos.Fmando4Activado;
import com.google.android.material.snackbar.Snackbar;
import com.jjoe64.graphview.GraphView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.maqueta_conexion.BluetoothMainActivity.BT_id;


public class MainActivity extends AppCompatActivity {
   public static ViewViewModel mViewViewModel;
   static int maquetaID;
   static HorizontalScrollView horizontalScrollView;
   static ImageView imageView0;
   static ImageView imageView1;
   static ImageView imageView2;
   static ImageView imageView3;
   static ImageView imageView4;
   static ImageView imageView5;
   static ImageView imageView6;
   static TextView tituloPanel;
   static TextView descripcionPanel;
   static TextView estadoBT;
   //static Botones titulo;
   //static Panel0 titulo0;
   TextView numeroPanel;
   static boolean primerInicio0=true;
   static boolean primerInicio4=true;
   static Button botonConectar;
    FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_main);
        mViewViewModel = new ViewModelProvider(this).get(ViewViewModel.class);
        horizontalScrollView=findViewById(R.id.pantallas);
        imageView0=findViewById(R.id.image0);
        imageView1=findViewById(R.id.image1);
        imageView2=findViewById(R.id.image2);
        imageView3=findViewById(R.id.image3);
        imageView4=findViewById(R.id.image4);
        imageView5=findViewById(R.id.image5);
        imageView6=findViewById(R.id.image6);

        numeroPanel=findViewById(R.id.panel_numero);
        tituloPanel=findViewById(R.id.panel_titulo);
        descripcionPanel=findViewById(R.id.texto_descriptivo);
        estadoBT=findViewById(R.id.estadoBT);
        botonConectar=findViewById(R.id.botonBluetooth);
        Botones titulo =mViewViewModel.getTitulo("titulo");
        if (titulo==null){
            titulo=new Botones ("titulo");
            titulo.setStringArg("Titulo sin establecer");
            titulo.setMensaje("Introduce una descripción");
            mViewViewModel.añadeBoton(titulo);
        }
        Panel0 titulo0 =mViewViewModel.getTitulo0("titulo");
        if (titulo0==null){
            titulo0=new Panel0 ("titulo");
            titulo0.setStringArg("Titulo sin establecer");
            titulo0.setMensaje("Introduce una descripción");
            mViewViewModel.añadeElementoPanel0(titulo0);
        }
        Bitmap bitmap = null;
        try{
            FileInputStream fileInputStream6 =
                    new FileInputStream(getApplicationContext().getFilesDir().getPath()+ "/imagen6.png");
            imageView6.setImageBitmap(BitmapFactory.decodeStream(fileInputStream6));
            FileInputStream fileInputStream5 =
                    new FileInputStream(getApplicationContext().getFilesDir().getPath()+ "/imagen5.png");
            imageView5.setImageBitmap(BitmapFactory.decodeStream(fileInputStream5));
            FileInputStream fileInputStream4 =
                    new FileInputStream(getApplicationContext().getFilesDir().getPath()+ "/imagen4.png");
            imageView4.setImageBitmap(BitmapFactory.decodeStream(fileInputStream4));
            FileInputStream fileInputStream3 =
                    new FileInputStream(getApplicationContext().getFilesDir().getPath()+ "/imagen3.png");
            imageView3.setImageBitmap(BitmapFactory.decodeStream(fileInputStream3));
            FileInputStream fileInputStream2 =
                    new FileInputStream(getApplicationContext().getFilesDir().getPath()+ "/imagen2.png");
            imageView2.setImageBitmap(BitmapFactory.decodeStream(fileInputStream2));
            FileInputStream fileInputStream1 =
                    new FileInputStream(getApplicationContext().getFilesDir().getPath()+ "/imagen1.png");
            imageView1.setImageBitmap(BitmapFactory.decodeStream(fileInputStream1));
            FileInputStream fileInputStream0 =
                    new FileInputStream(getApplicationContext().getFilesDir().getPath()+ "/imagen0.png");
            imageView0.setImageBitmap(BitmapFactory.decodeStream(fileInputStream0));

        }catch (IOException io){
            io.printStackTrace();
        }
        try{

            FileInputStream fileInputStream4 =
                    new FileInputStream(getApplicationContext().getFilesDir().getPath()+ "/imagen4.png");
            imageView4.setImageBitmap(BitmapFactory.decodeStream(fileInputStream4));

        }catch (IOException io){
            io.printStackTrace();
        }




    }
    public void onResume(){
        super.onResume();
            Bitmap bitmap = null;
            try{
                FileInputStream fileInputStream =
                        new FileInputStream(getApplicationContext().getFilesDir().getPath()+ "/imagen"+maquetaID+".png");
                bitmap = BitmapFactory.decodeStream(fileInputStream);
            }catch (IOException io){
                io.printStackTrace();
            }
            switch (maquetaID){
                case 0:
                    imageView0.setImageBitmap(bitmap);
                    imageView0.setScaleType(ImageView.ScaleType.FIT_XY);
                    break;
                case 1:
                    imageView1.setImageBitmap(bitmap);
                    break;
                case 2:
                    imageView2.setImageBitmap(bitmap);
                    break;
                case 3:
                    imageView3.setImageBitmap(bitmap);
                    break;
                case 4:
                    imageView4.setImageBitmap(bitmap);
                    imageView4.setScaleType(ImageView.ScaleType.FIT_XY);
                    break;
                case 5:
                    imageView5.setImageBitmap(bitmap);
                    break;
                case 6:
                    imageView6.setImageBitmap(bitmap);
                    break;
            }
    }

    public class SnackbarConectarListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intentBluetoothActivity =new Intent(MainActivity.this, BluetoothMainActivity.class);
            startActivity(intentBluetoothActivity);
        }
    }
    public void botonBluetooth(View view){
        if (estadoBT.getText().equals("CONECTADO")){
            if (BluetoothMainActivity.connectThread !=null){
                BluetoothMainActivity.connectThread.cancel();
            }
        }else{
            Intent intentBluetoothActivity =new Intent(MainActivity.this,
                    BluetoothMainActivity.class);
            startActivity(intentBluetoothActivity);
        }
    }
    public void editardescripcion0(View view){
        GeneralDialogFragment generalDialogFragment =
                GeneralDialogFragment.newInstance("CONFIGURAR PANTALLA", "message",
                        0, R.layout.popup_paneldetalles);
        generalDialogFragment.show(getSupportFragmentManager(), "dialog");


    }

    public void editardescripcion4(View view){
        GeneralDialogFragment generalDialogFragment =
                GeneralDialogFragment.newInstance("CONFIGURAR PANTALLA", "message", 4, R.layout.popup_paneldetalles);
        generalDialogFragment.show(getSupportFragmentManager(), "dialog");


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void seleccionar0(View view){
        maquetaID=0;
        numeroPanel.setText("PANEL "+maquetaID+" :");
        Panel0 titulo0=mViewViewModel.getTitulo0("titulo");
        tituloPanel.setText(titulo0.getStringArg());
        descripcionPanel.setText(titulo0.getMensaje());
        Button ejecutar =(Button)findViewById(R.id.boton_ejecutar0);

        if (estadoBT.getText().equals("CONECTADO")){
            ejecutar.setTextColor(getResources().getColor(R.color.negro));
        }else{
            ejecutar.setTextColor(getResources().getColor(R.color.botonDesactivado));
        }

        imageView0.setBackground(getDrawable(R.drawable.selector_paneles));
        imageView1.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView2.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView3.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView4.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView5.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView6.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        findViewById(R.id.boton_editar0).setVisibility(View.VISIBLE);
        findViewById(R.id.boton_ejecutar0).setVisibility(View.VISIBLE);


        findViewById(R.id.boton_editar1).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar1).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar2).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar2).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar3).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar3).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar4).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar4).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar5).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar5).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar6).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar6).setVisibility(View.INVISIBLE);

        findViewById(R.id.barrita0).setVisibility(View.VISIBLE);
        findViewById(R.id.detalles0).setVisibility(View.VISIBLE);
        findViewById(R.id.barrita1).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles1).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita2).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles2).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita3).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles3).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita4).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles4).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita5).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles5).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita6).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles6).setVisibility(View.INVISIBLE);


        findViewById(R.id.panel_descriptivo).setVisibility(View.VISIBLE);
        findViewById(R.id.pantallas).setBackground(getDrawable(R.drawable.cuadro_paneles_seleccionado));



    }

    public void editarPanel0(View view){
        Intent intentMotor =new Intent(MainActivity.this, Mando4.class);
        intentMotor.putExtra("numeroPanel",maquetaID);
        //intentMotor.putExtra("ejecutar",false);
        startActivity(intentMotor);
    }

    public void ejecutarPanel0(View view){
        Intent intent =new Intent(MainActivity.this, Mando4.class);
        intent.putExtra("numeroPanel",maquetaID);
        intent.putExtra("ejecutar",true);
        if (estadoBT.getText().equals("CONECTADO")){
            startActivity(intent);
        }else{
            Button btn=(Button) view;
            btn.setTextColor(getResources().getColor(R.color.botonDesactivado));
            Snackbar conectar = Snackbar.make(view, "Primero debes conectarte", Snackbar.LENGTH_LONG);
            conectar.setAction("CONECTAR",new SnackbarConectarListener());
            conectar.show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void seleccionar1(View view){
        maquetaID=1;
        //Intent intentMotor =new Intent(MainActivity.this, Mando1.class);
        //startActivity(intentMotor);
        Button ejecutar =(Button)findViewById(R.id.boton_ejecutar1);

        if (estadoBT.getText().equals("CONECTADO")){
            ejecutar.setTextColor(getResources().getColor(R.color.negro));
        }else{
            ejecutar.setTextColor(getResources().getColor(R.color.botonDesactivado));
        }

        numeroPanel.setText("PANEL "+maquetaID+" :");

        imageView0.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView1.setBackground(getDrawable(R.drawable.selector_paneles));
        imageView2.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView3.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView4.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView5.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView6.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        findViewById(R.id.boton_editar1).setVisibility(View.VISIBLE);
        findViewById(R.id.boton_ejecutar1).setVisibility(View.VISIBLE);

        findViewById(R.id.boton_editar0).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar0).setVisibility(View.INVISIBLE);

        findViewById(R.id.boton_editar2).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar2).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar3).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar3).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar4).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar4).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar5).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar5).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar6).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar6).setVisibility(View.INVISIBLE);

        findViewById(R.id.barrita0).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles0).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita1).setVisibility(View.VISIBLE);
        findViewById(R.id.detalles1).setVisibility(View.VISIBLE);
        findViewById(R.id.barrita2).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles2).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita3).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles3).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita4).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles4).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita5).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles5).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita6).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles6).setVisibility(View.INVISIBLE);


        findViewById(R.id.panel_descriptivo).setVisibility(View.VISIBLE);
        findViewById(R.id.pantallas).setBackground(getDrawable(R.drawable.cuadro_paneles_seleccionado));




    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void seleccionar2(View view){
        maquetaID=2;
       // Intent intentMotor =new Intent(MainActivity.this, Mando2.class);
        //startActivity(intentMotor);
        Button ejecutar =(Button)findViewById(R.id.boton_ejecutar2);

        if (estadoBT.getText().equals("CONECTADO")){
            ejecutar.setTextColor(getResources().getColor(R.color.negro));
        }else{
            ejecutar.setTextColor(getResources().getColor(R.color.botonDesactivado));
        }
        numeroPanel.setText("PANEL "+maquetaID+" :");

        imageView0.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView1.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView2.setBackground(getDrawable(R.drawable.selector_paneles));
        imageView3.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView4.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView5.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView6.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        findViewById(R.id.boton_editar2).setVisibility(View.VISIBLE);
        findViewById(R.id.boton_ejecutar2).setVisibility(View.VISIBLE);

        findViewById(R.id.boton_editar0).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar0).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar1).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar1).setVisibility(View.INVISIBLE);

        findViewById(R.id.boton_editar3).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar3).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar4).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar4).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar5).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar5).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar6).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar6).setVisibility(View.INVISIBLE);

        findViewById(R.id.barrita0).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles0).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita1).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles1).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita2).setVisibility(View.VISIBLE);
        findViewById(R.id.detalles2).setVisibility(View.VISIBLE);
        findViewById(R.id.barrita3).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles3).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita4).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles4).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita5).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles5).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita6).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles6).setVisibility(View.INVISIBLE);


        findViewById(R.id.panel_descriptivo).setVisibility(View.VISIBLE);
        findViewById(R.id.pantallas).setBackground(getDrawable(R.drawable.cuadro_paneles_seleccionado));

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void seleccionar3(View view){
        maquetaID=3;
        //Intent intentMotor =new Intent(MainActivity.this, mando3.class);
       // startActivity(intentMotor);
        Button ejecutar =(Button)findViewById(R.id.boton_ejecutar3);

        if (estadoBT.getText().equals("CONECTADO")){
            ejecutar.setTextColor(getResources().getColor(R.color.negro));
        }else{
            ejecutar.setTextColor(getResources().getColor(R.color.botonDesactivado));
        }
        numeroPanel.setText("PANEL "+maquetaID+" :");

        imageView0.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView1.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView2.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView3.setBackground(getDrawable(R.drawable.selector_paneles));
        imageView4.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView5.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView6.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));

        findViewById(R.id.boton_editar3).setVisibility(View.VISIBLE);
        findViewById(R.id.boton_ejecutar3).setVisibility(View.VISIBLE);

        findViewById(R.id.boton_editar0).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar0).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar1).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar1).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar2).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar2).setVisibility(View.INVISIBLE);

        findViewById(R.id.boton_editar4).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar4).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar5).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar5).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar6).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar6).setVisibility(View.INVISIBLE);

        findViewById(R.id.barrita0).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles0).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita1).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles1).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita2).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles2).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita3).setVisibility(View.VISIBLE);
        findViewById(R.id.detalles3).setVisibility(View.VISIBLE);
        findViewById(R.id.barrita4).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles4).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita5).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles5).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita6).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles6).setVisibility(View.INVISIBLE);


        findViewById(R.id.panel_descriptivo).setVisibility(View.VISIBLE);
        findViewById(R.id.pantallas).setBackground(getDrawable(R.drawable.cuadro_paneles_seleccionado));

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void seleccionar4(View view){
        maquetaID=4;
        //Intent intentMotor =new Intent(MainActivity.this, Mando4.class);
        //startActivity(intentMotor);
        Button ejecutar =(Button)findViewById(R.id.boton_ejecutar4);

        if (estadoBT.getText().equals("CONECTADO")){
            ejecutar.setTextColor(getResources().getColor(R.color.negro));
        }else{
            ejecutar.setTextColor(getResources().getColor(R.color.botonDesactivado));
        }
        numeroPanel.setText("PANEL "+maquetaID+" :");
        Botones titulo=mViewViewModel.getTitulo("titulo");
        tituloPanel.setText(titulo.getStringArg());
        descripcionPanel.setText(titulo.getMensaje());
       // tituloPanel.setText("Maqueta con motor y encoder");
       // descripcionPanel.setText("Encendido apagado de motor\n control de velocidad\n lectura de velocidad");

        imageView0.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView1.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView2.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView3.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView4.setBackground(getDrawable(R.drawable.selector_paneles));
        imageView5.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView6.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        findViewById(R.id.boton_editar4).setVisibility(View.VISIBLE);
        findViewById(R.id.boton_ejecutar4).setVisibility(View.VISIBLE);

        findViewById(R.id.boton_editar0).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar0).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar1).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar1).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar2).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar2).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar3).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar3).setVisibility(View.INVISIBLE);

        findViewById(R.id.boton_editar5).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar5).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar6).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar6).setVisibility(View.INVISIBLE);

        findViewById(R.id.barrita0).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles0).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita1).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles1).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita2).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles2).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita3).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles3).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita4).setVisibility(View.VISIBLE);
        findViewById(R.id.detalles4).setVisibility(View.VISIBLE);
        findViewById(R.id.barrita5).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles5).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita6).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles6).setVisibility(View.INVISIBLE);


        findViewById(R.id.panel_descriptivo).setVisibility(View.VISIBLE);
        findViewById(R.id.pantallas).setBackground(getDrawable(R.drawable.cuadro_paneles_seleccionado));

    }

    public void editarPanel4(View view){
        maquetaID=4;
        Intent intentMotor =new Intent(MainActivity.this, Mando4.class);
        intentMotor.putExtra("numeroPanel",4);
        startActivity(intentMotor);
    }

    public void ejecutarPanel4(View view){


        Intent intent =new Intent(MainActivity.this, Mando4.class);
        intent.putExtra("numeroPanel",maquetaID);
        intent.putExtra("ejecutar",true);

        if (estadoBT.getText().equals("CONECTADO")){
            startActivity(intent);
        }else{
            Button btn=(Button) view;
            btn.setTextColor(getResources().getColor(R.color.botonDesactivado));
            Snackbar conectar = Snackbar.make(view, "Primero debes conectarte", Snackbar.LENGTH_LONG);
            conectar.setAction("CONECTAR",new SnackbarConectarListener());
            conectar.show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void seleccionar5(View view){
        maquetaID=5;
       // Intent intentMotor =new Intent(MainActivity.this, Mando5.class);
        //startActivity(intentMotor);
        Button ejecutar =(Button)findViewById(R.id.boton_ejecutar5);

        if (estadoBT.getText().equals("CONECTADO")){
            ejecutar.setTextColor(getResources().getColor(R.color.negro));
        }else{
            ejecutar.setTextColor(getResources().getColor(R.color.botonDesactivado));
        }
        numeroPanel.setText("PANEL "+maquetaID+" :");

        imageView0.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView1.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView2.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView3.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView4.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView5.setBackground(getDrawable(R.drawable.selector_paneles));
        imageView6.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        findViewById(R.id.boton_editar5).setVisibility(View.VISIBLE);
        findViewById(R.id.boton_ejecutar5).setVisibility(View.VISIBLE);

        findViewById(R.id.boton_editar0).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar0).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar1).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar1).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar2).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar2).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar3).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar3).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar4).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar4).setVisibility(View.INVISIBLE);

        findViewById(R.id.boton_editar6).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar6).setVisibility(View.INVISIBLE);

        findViewById(R.id.barrita0).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles0).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita1).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles1).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita2).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles2).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita3).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles3).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita4).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles4).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita5).setVisibility(View.VISIBLE);
        findViewById(R.id.detalles5).setVisibility(View.VISIBLE);
        findViewById(R.id.barrita6).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles6).setVisibility(View.INVISIBLE);


        findViewById(R.id.panel_descriptivo).setVisibility(View.VISIBLE);
        findViewById(R.id.pantallas).setBackground(getDrawable(R.drawable.cuadro_paneles_seleccionado));
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void seleccionar6(View view){
        maquetaID=6;
        //Intent intentMotor =new Intent(MainActivity.this, Mando6.class);
        //startActivity(intentMotor);
        Button ejecutar =(Button)findViewById(R.id.boton_ejecutar0);

        if (estadoBT.getText().equals("CONECTADO")){
            ejecutar.setTextColor(getResources().getColor(R.color.negro));
        }else{
            ejecutar.setTextColor(getResources().getColor(R.color.botonDesactivado));
        }
        numeroPanel.setText("PANEL "+maquetaID+" :");

        imageView0.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView1.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView2.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView3.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView4.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView5.setBackground(getDrawable(R.drawable.black_shape_fondo_blanco_cuadrado));
        imageView6.setBackground(getDrawable(R.drawable.selector_paneles));
        findViewById(R.id.boton_editar6).setVisibility(View.VISIBLE);
        findViewById(R.id.boton_ejecutar6).setVisibility(View.VISIBLE);

        findViewById(R.id.boton_editar0).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar0).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar1).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar1).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar2).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar2).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar3).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar3).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar4).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar4).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_editar5).setVisibility(View.INVISIBLE);
        findViewById(R.id.boton_ejecutar5).setVisibility(View.INVISIBLE);

        findViewById(R.id.barrita0).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles0).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita1).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles1).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita2).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles2).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita3).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles3).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita4).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles4).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita5).setVisibility(View.INVISIBLE);
        findViewById(R.id.detalles5).setVisibility(View.INVISIBLE);
        findViewById(R.id.barrita6).setVisibility(View.VISIBLE);
        findViewById(R.id.detalles6).setVisibility(View.VISIBLE);


        findViewById(R.id.panel_descriptivo).setVisibility(View.VISIBLE);
        findViewById(R.id.pantallas).setBackground(getDrawable(R.drawable.cuadro_paneles_seleccionado));

    }



}


