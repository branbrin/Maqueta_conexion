package com.example.maqueta_conexion;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Environment;
//import android.support.annotation.RequiresApi;
//import android.support.constraint.ConstraintLayout;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import static java.lang.Thread.sleep;

public class MotorActivity extends AppCompatActivity {
    static TextView estado;
    static TextView velocidad;
    static TextView pruebas;
    static TextView pantalla;
    float porcentaje;
   static String id;
    static byte[] consigna;
    int valorConsigna;
    TextView TVconsigna;
    EditText ETconsigna;
    EditText ETbluto;
    static ConstraintLayout layout;
    ImageView imageView;
    FileOutputStream file;
    private Object File;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor);
        Intent intent = getIntent();
        id = intent.getStringExtra("bluto");
        TVconsigna=findViewById((R.id.TVconsigna));

        estado=findViewById(R.id.estado3);
        velocidad=findViewById(R.id.velocidad);
        pruebas= findViewById(R.id.pruebas);
        pantalla=findViewById(R.id.pantalla);
        pantalla.setMovementMethod(new ScrollingMovementMethod());
        ETconsigna=(EditText)findViewById(R.id.ETconsigna);
        ETbluto=(EditText)findViewById(R.id.ETbluto);
        layout=findViewById(R.id.parent);
        imageView=findViewById(R.id.imageView);




    }


    protected void onPause(){
        super.onPause();

        //obtener bitmap
        //MainActivity.imageView.setImageBitmap(screenShot(layout));
        Bitmap bmp = screenShot(layout);
        MainActivity.imageView0.setImageBitmap(bmp);
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

    public void botonMarcha(View view) {
        BluetoothMainActivity.gestionBoton(Constantes.MARCHA);


    }
    public static void botonParo(View view) {
        BluetoothMainActivity.gestionBoton(Constantes.PARO);

    }

    public static void botonReconectar(View view) {

        BluetoothDevice device = BluetoothMainActivity.mBluetoothAdapter.getRemoteDevice(id);

        if (BluetoothMainActivity.connectThread !=null){
           BluetoothMainActivity.connectThread.cancel();
        }
        BluetoothMainActivity.connectThread = new BluetoothMainActivity.ConnectThread(device);
        BluetoothMainActivity.connectThread.start();


    }
    public void seta(View view) {

       imageView.setImageBitmap(screenShot(layout));

    }
    public void interruptor(View view) {


    }

    public static void enviarConsigna (int valorConsigna){
        consigna= String.valueOf(valorConsigna).getBytes();
        if (consigna.length>2){
            Constantes.VELOCIDAD[2]=consigna[0];
            Constantes.VELOCIDAD[3]=consigna[1];
            Constantes.VELOCIDAD[4]=consigna[2];


        }else if (consigna.length>1){
            Constantes.VELOCIDAD[2]=(byte)0x30;
            Constantes.VELOCIDAD[3]=consigna[0];
            Constantes.VELOCIDAD[4]=consigna[1];
        }else {
            Constantes.VELOCIDAD[2]=(byte)0x30;
            Constantes.VELOCIDAD[3]=(byte)0x30;
            Constantes.VELOCIDAD[4]=consigna[0];
        }
        BluetoothMainActivity.gestionBoton(Constantes.VELOCIDAD);
    }
    public void botonConsigna(View view) {

        String consig = String.valueOf(ETconsigna.getText());

       Float consi = Float.parseFloat(consig);


       consi=consi*2;
        int redondeado= (int)consi.floatValue();
        porcentaje=(float)redondeado;
        TVconsigna.setText("CONSIGNA: "+porcentaje/2+" %");
        valorConsigna=(int)consi.floatValue();
       // velocidad.setText(""+ (int)consi.floatValue()+"//////"+enviable);

            enviarConsigna(valorConsigna);

        /*if (consi!=null){
           // byte[] consigna = consig.getBytes();
            consigna = String.valueOf((int)consi.floatValue()).getBytes();
            if (consigna.length>2){
                Constantes.VELOCIDAD[2]=consigna[0];
                Constantes.VELOCIDAD[3]=consigna[1];
                Constantes.VELOCIDAD[4]=consigna[2];


            }else if (consigna.length>1){
                Constantes.VELOCIDAD[2]=(byte)0x30;
                Constantes.VELOCIDAD[3]=consigna[0];
                Constantes.VELOCIDAD[4]=consigna[1];
            }else {
                Constantes.VELOCIDAD[2]=(byte)0x30;
                Constantes.VELOCIDAD[3]=(byte)0x30;
                Constantes.VELOCIDAD[4]=consigna[0];
            }

        }


        BluetoothMainActivity.gestionBoton(Constantes.VELOCIDAD);*/
    }

    public void botonMas(View view) {
        if (porcentaje<200){
            porcentaje++;
            TVconsigna.setText("CONSIGNA: "+porcentaje/2+" %");
        }
        if (valorConsigna<200) {
            valorConsigna++;
            enviarConsigna(valorConsigna);
        }
    }

    public void botonMenos(View view) {
        if (porcentaje>0){
            porcentaje --;
            TVconsigna.setText("CONSIGNA: "+porcentaje/2+" %");
        }
        if (valorConsigna>0) {
            valorConsigna--;
            enviarConsigna(valorConsigna);
        }

    }

    public void botonbluto(View view) {
        String fin ="F";
       String comAT = String.valueOf("bAT"+ETbluto.getText()+"F");
       // MotorActivity.pruebas.setText(""+comAT.getBytes());
       BluetoothMainActivity.gestionBoton(comAT.getBytes());
       BluetoothMainActivity.connectThread.cancel();
        BluetoothDevice device = BluetoothMainActivity.mBluetoothAdapter.getRemoteDevice(id);
        BluetoothMainActivity.connectThread = new BluetoothMainActivity.ConnectThread(device);

        int loop =0;

            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
                BluetoothMainActivity.connectThread.start();

        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        BluetoothMainActivity.gestionBoton(fin.getBytes());



    }
    public void botontrama(View view) {

        String comAT = String.valueOf(ETbluto.getText());
        // MotorActivity.pruebas.setText(""+comAT.getBytes());
        BluetoothMainActivity.gestionBoton(comAT.getBytes());




    }
}
