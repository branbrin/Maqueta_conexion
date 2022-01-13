package com.example.maqueta_conexion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.example.maqueta_conexion.MainActivity.mViewViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarSlider#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarSlider extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SeekBar mSlider;
    static TextView TVprimer;
    static TextView TVcab;
    static TextView TVfin;
    static TextView TVrango;
    static TextView TVmodo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarSlider() {
        // Required empty public constructor
    }
    public EditarSlider(SeekBar sldr){
        mSlider=sldr;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarSlider.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarSlider newInstance(String param1, String param2) {
        EditarSlider fragment = new EditarSlider();
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
        return inflater.inflate(R.layout.fragment_editar_slider, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button boton = view.findViewById(R.id.boton_configslider);
        TVcab = view.findViewById(R.id.menslider);
        TVprimer = view.findViewById(R.id.cabslider);
        TVfin = view.findViewById(R.id.finslider);
        TVmodo= view.findViewById(R.id.modo_slider);
        TVrango= view.findViewById(R.id.rango_slider);

        switch (Mando4.numeroPanel){
            case 0:
                Panel0 slidaux = (Panel0) mSlider.getTag();
                Panel0 slider = mViewViewModel.getViewByIDPanel0(slidaux.getId());

                if (slider != null) {
                    TVcab.setText(""+slider.getMensaje());
                    TVprimer.setText("" + slider.getCabecero());
                    TVfin.setText("" + slider.getUltimo());
                    TVrango.setText("" + slider.getIntArg());
                    if (slider.getIDaux()==R.id.radiosoltar){
                        TVmodo.setText("Al soltar");
                    }
                    if (slider.getIDaux()==R.id.radiocambio){
                        TVmodo.setText("Al cambiar");
                    }
                }


                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GeneralDialogFragment generalDialogFragment =
                                GeneralDialogFragment.newInstance("CONFIGURAR PANTALLA", "message", slider.getId(), R.layout.popup_slider);
                        generalDialogFragment.show(getParentFragmentManager(), "dialog");


                    }
                });
                break;
            case 4:
                Botones caux = (Botones) mSlider.getTag();
                Botones c = mViewViewModel.getBoton(caux.getId());

                if (c != null) {
                    TVcab.setText(""+c.getMensaje());
                    TVprimer.setText("" + c.getCabecero());
                    TVfin.setText("" + c.getUltimo());
                    TVrango.setText("" + c.getIntArg());
                    if (c.getIDaux()==R.id.radiosoltar){
                        TVmodo.setText("Al soltar");
                    }
                    if (c.getIDaux()==R.id.radiocambio){
                        TVmodo.setText("Al cambiar");
                    }
                }


                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GeneralDialogFragment generalDialogFragment =
                                GeneralDialogFragment.newInstance("CONFIGURAR PANTALLA", "message", c.getId(), R.layout.popup_slider);
                        generalDialogFragment.show(getParentFragmentManager(), "dialog");


                    }
                });
                break;
        }


    }

    static public void actualizarSlider(int modo, String prim, String cab, String fin,int rango) {

        TVprimer.setText(prim);
        TVcab.setText(cab);
        TVfin.setText(fin);
        TVrango.setText(""+rango);
        if (modo==R.id.radiosoltar){
            TVmodo.setText("Al soltar");
        }
        if (modo==R.id.radiocambio){
            TVmodo.setText("Al cambiar");
        }

    }
}