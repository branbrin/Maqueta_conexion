package com.example.maqueta_conexion;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.maqueta_conexion.fragmentosPaneles.Fbotones;

public class MoveViewTouchListener  implements View.OnTouchListener{


    private GestureDetector mGestureDetector;
    private View mView;
    FragmentManager mManag;


    public MoveViewTouchListener(View view,FragmentManager manag)
    {

        mGestureDetector = new GestureDetector(view.getContext(), mGestureListener);
        mView = view;
        mManag = manag;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (mGestureDetector.onTouchEvent(event)) {
            // single tap

            Button b = (Button) v;
            b.setText("sapulsao");
            Fbotones editar=new Fbotones();
            FragmentTransaction fragmentTransaction = mManag.beginTransaction();
            fragmentTransaction.replace(R.id.layouteditar, editar );
            fragmentTransaction.commit();

           // mensaje.setText();
            return true;
        } else {
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
            mMotionDownX = e.getRawX() - mView.getTranslationX();
            mMotionDownY = e.getRawY() - mView.getTranslationY();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            mView.setTranslationX(e2.getRawX() - mMotionDownX);
            mView.setTranslationY(e2.getRawY() - mMotionDownY);
            return true;
        }
        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    };
}
