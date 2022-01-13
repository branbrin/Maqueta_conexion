package com.example.maqueta_conexion;

//import android.support.constraint.ConstraintLayout;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jjoe64.graphview.GraphView;

import static com.example.maqueta_conexion.MainActivity.mViewViewModel;
import static com.example.maqueta_conexion.Mando4.botones_actuales;
import static com.example.maqueta_conexion.Mando4.graficas_actuales;
import static com.example.maqueta_conexion.Mando4.numeroPanel;
import static com.example.maqueta_conexion.Mando4.pantallas_actuales;
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
      switch (numeroPanel){
          case 0:
              Panel0 elemento = (Panel0) v.getTag();
              switch (elemento.getBoton()){
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
                          mViewViewModel.borraEnPanel0(elemento);
                      }
                      break;
                  case "terminal":
                      ConstraintLayout c = (ConstraintLayout) v;

                      TerminalesEditar  editarTerminal=new TerminalesEditar(c);
                      FragmentTransaction fragmentTransaction2 = mManag.beginTransaction();
                      fragmentTransaction2.replace(R.id.layouteditar, editarTerminal );
                      fragmentTransaction2.commit();
                      if(c.getX()>760&c.getY()>230){
                          c.setVisibility(View.INVISIBLE);
                          terminales_actuales.remove(c);
                          mViewViewModel.borraEnPanel0(elemento);
                      }
                      break;
                  case "pantalla":
                      TextView t = (TextView) v;
                      EditarPantalla  editarPantalla=new EditarPantalla(t);
                      FragmentTransaction fragmentTransaction3 = mManag.beginTransaction();
                      fragmentTransaction3.replace(R.id.layouteditar, editarPantalla );
                      fragmentTransaction3.commit();

                      if(t.getX()>760&t.getY()>230){
                          t.setVisibility(View.INVISIBLE);
                          pantallas_actuales.remove(t);
                          mViewViewModel.borraEnPanel0(elemento);
                      }
                      break;

                  case "terminalBT":
                      ConstraintLayout d = (ConstraintLayout) v;

                      TerminalesEditar  editarTerminalBT=new TerminalesEditar(d);
                      FragmentTransaction fragmentTransaction4 = mManag.beginTransaction();
                      fragmentTransaction4.replace(R.id.layouteditar, editarTerminalBT );
                      fragmentTransaction4.commit();
                      if(d.getX()>760&d.getY()>230){
                          d.setVisibility(View.INVISIBLE);
                          Mando4.terminalesBT_actuales.remove(d);
                          mViewViewModel.borraEnPanel0(elemento);
                      }
                      break;

                  case "interruptor":
                      Switch e = (Switch) v;
                      EditarInterruptor  editarInterruptor=new EditarInterruptor(e);
                      FragmentTransaction fragmentTransaction5 = mManag.beginTransaction();
                      fragmentTransaction5.replace(R.id.layouteditar, editarInterruptor);
                      fragmentTransaction5.commit();
                      if(e.getX()>760&e.getY()>230){
                          e.setVisibility(View.INVISIBLE);
                          Mando4.interruptores_actuales.remove(e);
                          mViewViewModel.borraEnPanel0(elemento);
                      }
                      break;
                  case "slider":
                      SeekBar sldr = (SeekBar) v;
                      EditarSlider  editarSlider=new EditarSlider(sldr);
                      FragmentTransaction fragmentTransaction6 = mManag.beginTransaction();
                      fragmentTransaction6.replace(R.id.layouteditar, editarSlider);
                      fragmentTransaction6.commit();
                      if(sldr.getX()>760&sldr.getY()>230){
                          sldr.setVisibility(View.INVISIBLE);
                          Mando4.sliders_actuales.remove(sldr);
                          mViewViewModel.borraEnPanel0(elemento);
                      }
                      break;

                  case "grafica":
                      ConstraintLayout graf = (ConstraintLayout) v;
                      EditarGrafica  editarGrafica=new EditarGrafica(graf);
                      FragmentTransaction fragmentTransaction7 = mManag.beginTransaction();
                      fragmentTransaction7.replace(R.id.layouteditar, editarGrafica );
                      fragmentTransaction7.commit();

                      if(graf.getX()>760&graf.getY()>230){
                          graf.setVisibility(View.INVISIBLE);
                          graficas_actuales.remove(graf);
                          mViewViewModel.borraEnPanel0(elemento);
                      }
                      break;

              }

              break;
          case 4:
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
                          mViewViewModel.borraBoton(bot);
                      }
                      break;
                  case "terminal":
                      ConstraintLayout c = (ConstraintLayout) v;

                      TerminalesEditar  editarTerminal=new TerminalesEditar(c);
                      FragmentTransaction fragmentTransaction2 = mManag.beginTransaction();
                      fragmentTransaction2.replace(R.id.layouteditar, editarTerminal );
                      fragmentTransaction2.commit();
                      if(c.getX()>760&c.getY()>230){
                          c.setVisibility(View.INVISIBLE);
                          terminales_actuales.remove(c);
                          mViewViewModel.borraBoton(bot);
                      }
                      break;
                  case "pantalla":
                      TextView t = (TextView) v;
                      EditarPantalla  editarPantalla=new EditarPantalla(t);
                      FragmentTransaction fragmentTransaction3 = mManag.beginTransaction();
                      fragmentTransaction3.replace(R.id.layouteditar, editarPantalla );
                      fragmentTransaction3.commit();

                      if(t.getX()>760&t.getY()>230){
                          t.setVisibility(View.INVISIBLE);
                          pantallas_actuales.remove(t);
                          mViewViewModel.borraBoton(bot);
                      }
                      break;

                  case "terminalBT":
                      ConstraintLayout d = (ConstraintLayout) v;

                      TerminalesEditar  editarTerminalBT=new TerminalesEditar(d);
                      FragmentTransaction fragmentTransaction4 = mManag.beginTransaction();
                      fragmentTransaction4.replace(R.id.layouteditar, editarTerminalBT );
                      fragmentTransaction4.commit();
                      if(d.getX()>760&d.getY()>230){
                          d.setVisibility(View.INVISIBLE);
                          Mando4.terminalesBT_actuales.remove(d);
                          mViewViewModel.borraBoton(bot);
                      }
                      break;

                  case "interruptor":
                      Switch e = (Switch) v;

                      EditarInterruptor  editarInterruptor=new EditarInterruptor(e);
                      FragmentTransaction fragmentTransaction5 = mManag.beginTransaction();
                      fragmentTransaction5.replace(R.id.layouteditar, editarInterruptor);
                      fragmentTransaction5.commit();
                      if(e.getX()>760&e.getY()>230){
                          e.setVisibility(View.INVISIBLE);
                          Mando4.interruptores_actuales.remove(e);
                          mViewViewModel.borraBoton(bot);
                      }
                      break;
                  case "slider":
                      SeekBar sldr = (SeekBar) v;

                      EditarSlider  editarSlider=new EditarSlider(sldr);
                      FragmentTransaction fragmentTransaction6 = mManag.beginTransaction();
                      fragmentTransaction6.replace(R.id.layouteditar, editarSlider);
                      fragmentTransaction6.commit();
                      if(sldr.getX()>760&sldr.getY()>230){
                          sldr.setVisibility(View.INVISIBLE);
                          Mando4.sliders_actuales.remove(sldr);
                          mViewViewModel.borraBoton(bot);
                      }
                      break;
                  case "grafica":
                      ConstraintLayout graf = (ConstraintLayout) v;
                      EditarGrafica  editarGrafica=new EditarGrafica(graf);
                      FragmentTransaction fragmentTransaction7 = mManag.beginTransaction();
                      fragmentTransaction7.replace(R.id.layouteditar, editarGrafica );
                      fragmentTransaction7.commit();

                      if(graf.getX()>760&graf.getY()>230){
                          graf.setVisibility(View.INVISIBLE);
                          graficas_actuales.remove(graf);
                          mViewViewModel.borraBoton(bot);
                      }
                      break;

              }

              break;
      }


            if (event.getActionMasked()==MotionEvent.ACTION_UP){
                Fmando4.borrar.setVisibility(View.INVISIBLE);
            }
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
