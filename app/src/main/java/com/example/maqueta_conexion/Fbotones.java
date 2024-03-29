package com.example.maqueta_conexion;

import android.content.ClipData;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import static com.example.maqueta_conexion.MainActivity.mViewViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fbotones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fbotones extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button mButon;
    Button boton;
    static String actualizCab;
    static String actualizMen;
    static TextView mensaje;
    static  TextView cabecero;
    static TextView ultimo;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fbotones() {
        // Required empty public constructor
    }
    public Fbotones(Button b){
        mButon=b;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fbotones.
     */
    // TODO: Rename and change types and number of parameters
    public static Fbotones newInstance(String param1, String param2) {
        Fbotones fragment = new Fbotones();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_fbotones, container, false);
    }
    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boton = view.findViewById(R.id.configcab);
        EditText etcab=view.findViewById(R.id.popup_botones);

        mensaje=view.findViewById(R.id.mensaje);
        cabecero=view.findViewById(R.id.cabecero);
        ultimo=view.findViewById(R.id.ultimo);

        switch (Mando4.numeroPanel){
            case 0:
                Panel0 botaux=(Panel0) mButon.getTag();
                Panel0 bot = mViewViewModel.getViewByIDPanel0(botaux.mID);
                if (bot!=null){
                    mensaje.setText(""+ bot.getMensaje());
                    cabecero.setText(""+ bot.getCabecero());
                    ultimo.setText(""+ bot.getUltimo());
                }

                boton.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view1, MotionEvent motionEvent){
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            GeneralDialogFragment generalDialogFragment =
                                    GeneralDialogFragment.newInstance("CONFIGURAR BOTON", "message",bot.getId(),R.layout.popup_botones);
                            generalDialogFragment.show(getParentFragmentManager(),"dialog");
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                break;
            case 4:
                Botones baux=(Botones) mButon.getTag();
                Botones b = mViewViewModel.getBoton(baux.mID);
                if (b!=null){
                    mensaje.setText(""+ b.getMensaje());
                    cabecero.setText(""+ b.getCabecero());
                    ultimo.setText(""+ b.getUltimo());
                }

                boton.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view1, MotionEvent motionEvent){
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            GeneralDialogFragment generalDialogFragment =
                                    GeneralDialogFragment.newInstance("CONFIGURAR BOTON", "message",b.getId(),R.layout.popup_botones);
                            generalDialogFragment.show(getParentFragmentManager(),"dialog");
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                break;
        }






    }
   static public void actualizar(String cabecer,String mensaj,String ultim){
        mensaje.setText(""+mensaj);
        cabecero.setText(""+cabecer);
        ultimo.setText("" +ultim);
    }


}
