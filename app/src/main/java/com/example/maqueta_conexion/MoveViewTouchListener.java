package com.example.maqueta_conexion;

//import android.support.constraint.ConstraintLayout;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.example.maqueta_conexion.Mando4.botones_actuales;
import static com.example.maqueta_conexion.Mando4.terminales_actuales;

public class MoveViewTouchListener  implements View.OnTouchListener{


    private GestureDetector mGestureDetector;
    private View mView;
    FragmentManager mManager;

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
      Botones bot = (Botones) v.getTag();
      switch (bot.getBoton()){
          case "boton":
              Button b = (Button) v;
              //Mando4.mViewViewModel.borrarTodo();
              // Fmando4.botones=Fmando4.mViewViewModel.getAllBotones();

           /* Mando4.pruebas.setText("");
            for (int i=0;i<Mando4.botones.length;i++){
                Mando4.pruebas.setText(""+Mando4.pruebas.getText()+Mando4.botones[i].getBoton());
            }

            */

              Fbotones editar=new Fbotones(b);
              FragmentTransaction fragmentTransaction = mManag.beginTransaction();
              fragmentTransaction.replace(R.id.layouteditar, editar );
              fragmentTransaction.commit();

              if(b.getX()>760&b.getY()>230){
                  b.setVisibility(View.INVISIBLE);
                  botones_actuales.remove(b);
                  Fmando4.mViewViewModel.borraBoton(bot);
              }
              break;
          case "terminal":
              ConstraintLayout c = (ConstraintLayout) v;
              //Mando4.mViewViewModel.borrarTodo();
              // Fmando4.botones=Fmando4.mViewViewModel.getAllBotones();

           /* Mando4.pruebas.setText("");
            for (int i=0;i<Mando4.botones.length;i++){
                Mando4.pruebas.setText(""+Mando4.pruebas.getText()+Mando4.botones[i].getBoton());
            }

            */

              TerminalesEditar  editarTerminal=new TerminalesEditar(c);
              FragmentTransaction fragmentTransaction2 = mManag.beginTransaction();
              fragmentTransaction2.replace(R.id.layouteditar, editarTerminal );

              fragmentTransaction2.commit();

              if(c.getX()>760&c.getY()>230){
                  c.setVisibility(View.INVISIBLE);
                  terminales_actuales.remove(c);
                  Fmando4.mViewViewModel.borraBoton(bot);
              }
              break;

      }


            if (event.getActionMasked()==MotionEvent.ACTION_UP){
                Fmando4.borrar.setVisibility(View.INVISIBLE);
            }
           // mensaje.setText();
            return true;

        }

        else {
            if (event.getActionMasked()==MotionEvent.ACTION_UP){
                Fmando4.borrar.setVisibility(View.INVISIBLE);
            }
            // your code for move and drag
            /*
            Fmando4.borrar.setVisibility(View.VISIBLE);
            if (event.getAction()==MotionEvent.ACTION_BUTTON_RELEASE){
                Fmando4.borrar.setVisibility(View.INVISIBLE);
            }

             */
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
            Fmando4.borrar.setVisibility(View.VISIBLE);
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
        @Override
        public void onShowPress(MotionEvent e){

        }
    };
}
