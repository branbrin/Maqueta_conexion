package com.example.maqueta_conexion.fragmentosMandos;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maqueta_conexion.Mando4;
import com.example.maqueta_conexion.MotorActivity;
import com.example.maqueta_conexion.MoveViewTouchListener;
import com.example.maqueta_conexion.R;
import com.example.maqueta_conexion.botonClick;

import org.xmlpull.v1.XmlPullParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fmando4 extends Fragment {

    public Fmando4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

            return inflater.inflate(R.layout.fragment_fmando4, container, false);


    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.constraintFmando4).setOnDragListener(new MyDragListener());


    }
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

                            View view = LayoutInflater.from(getContext()).inflate(R.layout.boton_rojo, null);
                            ConstraintLayout container = (ConstraintLayout) v;
                            container.addView(view, 50, 50);
                            FragmentManager manag =getFragmentManager();
                            view.setOnTouchListener(new MoveViewTouchListener(view,manag));

                            view.setX(event.getX());
                            view.setY(event.getY());
                            view.setOnClickListener(new botonClick());



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
}
