package com.example.maqueta_conexion;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
   static int maquetaID;
   static HorizontalScrollView horizontalScrollView;
   static ImageView imageView0;
   static ImageView imageView1;
    static ImageView imageView2;
    static ImageView imageView3;
    static ImageView imageView4;
    static ImageView imageView5;
    static ImageView imageView6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_main);
        horizontalScrollView=findViewById(R.id.pantallas);
        imageView0=findViewById(R.id.image0);
        imageView1=findViewById(R.id.image1);
        imageView2=findViewById(R.id.image2);
        imageView3=findViewById(R.id.image3);
        imageView4=findViewById(R.id.image4);
        imageView5=findViewById(R.id.image5);
        imageView6=findViewById(R.id.image6);




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
                    break;
                case 5:
                    imageView5.setImageBitmap(bitmap);
                    break;
                case 6:
                    imageView6.setImageBitmap(bitmap);
                    break;
            }





    }


    public void botonBluetooth(View view){
        Intent intentBluetoothActivity =new Intent(MainActivity.this, BluetoothMainActivity.class);
        startActivity(intentBluetoothActivity);
    }
    public void botonMotor0(View view){
        maquetaID=0;
        Intent intentMotor =new Intent(MainActivity.this, mando0.class);
        startActivity(intentMotor);
    }
    public void botonMotor(View view){
        maquetaID=1;
        Intent intentMotor =new Intent(MainActivity.this, Mando1.class);
        startActivity(intentMotor);
    }
    public void botonMotor2(View view){
        maquetaID=2;
        Intent intentMotor =new Intent(MainActivity.this, Mando2.class);
        startActivity(intentMotor);
    }
    public void botonMotor3(View view){
        maquetaID=3;
        Intent intentMotor =new Intent(MainActivity.this, mando3.class);
        startActivity(intentMotor);
    }
    public void botonMotor4(View view){
        maquetaID=4;
        Intent intentMotor =new Intent(MainActivity.this, Mando4.class);
        startActivity(intentMotor);
    }
    public void botonMotor5(View view){
        maquetaID=5;
        Intent intentMotor =new Intent(MainActivity.this, Mando5.class);
        startActivity(intentMotor);
    }
    public void botonMotor6(View view){
        maquetaID=6;
        Intent intentMotor =new Intent(MainActivity.this, Mando6.class);
        startActivity(intentMotor);
    }



}


