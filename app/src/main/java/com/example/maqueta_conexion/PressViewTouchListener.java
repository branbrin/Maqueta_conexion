package com.example.maqueta_conexion;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.example.maqueta_conexion.Mando4.botones_actuales;

public class PressViewTouchListener  implements View.OnTouchListener{


    private GestureDetector mGestureDetector;
    private View mView;
    FragmentManager mManager;

    FragmentManager mManag;


    public PressViewTouchListener(View view)
    {

        mGestureDetector = new GestureDetector(view.getContext(), mGestureListener);
        mView = view;



    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (mGestureDetector.onTouchEvent(event)) {
            // single tap
            return true;
        }

        else {
            // your code for move and drag
            return mGestureDetector.onTouchEvent(event);

        }

    }

    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener()
    {
        private float mMotionDownX, mMotionDownY;

        @Override
        public boolean onDown(MotionEvent e)
        {

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {

            return true;
        }
        @Override
        public boolean onSingleTapUp(MotionEvent event) {

            return true;
        }
        @Override
        public void onShowPress(MotionEvent e){
            Button botonsete=(Button)mView;
            Botones botones= (Botones) botonsete.getTag();
            String a= botones.getCabecero();
            String b = botones.getMensaje();
            String c =a+b;

            BluetoothMainActivity.gestionBoton( c.getBytes());

        }
    };
}
