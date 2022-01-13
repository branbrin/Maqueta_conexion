package com.example.maqueta_conexion;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

import static com.example.maqueta_conexion.MainActivity.mViewViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarGrafica#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarGrafica extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ConstraintLayout mGrafica;
    static TextView scrollGrafica;
    static TextView escalableGrafica;
    static TextView autosizeX;
    static TextView autosizeY;
    static TextView scrollToEnd;
    static TextView nPuntos;
    static TextView minX;
    static TextView maxX;
    static TextView minY;
    static TextView maxY;
    static TextView tamañoEjeX;
    static TextView tamañoEjeY;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarGrafica() {
        // Required empty public constructor
    }
    public EditarGrafica(ConstraintLayout graphView) {
        //  empty public constructor
        mGrafica=graphView;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarGrafica.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarGrafica newInstance(String param1, String param2) {
        EditarGrafica fragment = new EditarGrafica();
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
        return inflater.inflate(R.layout.fragment_editar_grafica, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button boton = view.findViewById(R.id.boton_configgrafica);
        scrollGrafica=view.findViewById(R.id.scroll_grafica);
        escalableGrafica=view.findViewById(R.id.escalable_grafica);
        autosizeX=view.findViewById(R.id.autosizex_grafica);
        autosizeY=view.findViewById(R.id.autosizey_grafica);
        tamañoEjeX=view.findViewById(R.id.tamañox);
        tamañoEjeY=view.findViewById(R.id.tamañoy);
        scrollToEnd=view.findViewById(R.id.scrolltoend);
        nPuntos=view.findViewById(R.id.npuntos);
        minX=view.findViewById(R.id.minx);
        maxX=view.findViewById(R.id.maxx);
        minY=view.findViewById(R.id.miny);
        maxY=view.findViewById(R.id.maxy);


        switch (Mando4.numeroPanel){
            case 0:
                Panel0 grafaux = (Panel0) mGrafica.getTag();
                Panel0 graf=mViewViewModel.getViewByIDPanel0(grafaux.getId());
                if (graf != null){

                    char[] chars =graf.getMensaje().toCharArray();
                    if (chars[0]==0x73){                                                            //s
                        scrollGrafica.setText("ACTIVADO");
                    }else if(chars[0]==0x6E){                                                       //n
                        scrollGrafica.setText("DESACTIVADO");
                    }
                    if (chars[1]==0x73){
                        escalableGrafica.setText("ACTIVADO");
                    }else if(chars[1]==0x6E){
                        escalableGrafica.setText("DESACTIVADO");
                    }
                    if (chars[2]==0x73){
                        autosizeX.setText("ACTIVADO");
                    }else if(chars[2]==0x6E){
                        autosizeX.setText("DESACTIVADO");
                    }
                    if (chars[3]==0x73){
                        autosizeY.setText("ACTIVADO");
                    }else if(chars[3]==0x6E){
                        autosizeY.setText("DESACTIVADO");

                    }
                    if (chars[4]==0x73){
                        scrollToEnd.setText("ACTIVADO");
                    }else if(chars[4]==0x6E){
                        scrollToEnd.setText("DESACTIVADO");
                    }
                    tamañoEjeX.setText(graf.getCabecero());
                    tamañoEjeY.setText(graf.getUltimo());
                    nPuntos.setText(""+graf.getIDaux());
                    minX.setText(""+graf.getIntArg());
                    maxX.setText(""+graf.getIntArg2());
                    minY.setText(""+graf.getIntArg3());
                    maxY.setText(""+graf.getIntArg4());


                }
                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GeneralDialogFragment generalDialogFragment =
                                GeneralDialogFragment.newInstance("CONFIGURAR GRÁFICA", "message", graf.getId(), R.layout.popup_grafica);
                        generalDialogFragment.show(getParentFragmentManager(), "dialog");
                    }
                });
                break;
            case 4:
                Botones caux = (Botones) mGrafica.getTag();
                Botones c=mViewViewModel.getBoton(caux.getId());
                if (c != null){
                    char[] chars =c.getMensaje().toCharArray();
                    if (chars[0]==0x73){                                                            //s
                        scrollGrafica.setText("ACTIVADO");
                    }else if(chars[0]==0x6E){                                                       //n
                        scrollGrafica.setText("DESACTIVADO");
                    }
                    if (chars[1]==0x73){
                        escalableGrafica.setText("ACTIVADO");
                    }else if(chars[1]==0x6E){
                        escalableGrafica.setText("DESACTIVADO");
                    }
                    if (chars[2]==0x73){
                        autosizeX.setText("ACTIVADO");
                    }else if(chars[2]==0x6E){
                        autosizeX.setText("DESACTIVADO");
                    }
                    if (chars[3]==0x73){
                        autosizeY.setText("ACTIVADO");
                    }else if(chars[3]==0x6E){
                        autosizeY.setText("DESACTIVADO");

                    }
                    if (chars[4]==0x73){
                        scrollToEnd.setText("ACTIVADO");
                    }else if(chars[4]==0x6E){
                        scrollToEnd.setText("DESACTIVADO");
                    }
                    tamañoEjeX.setText(c.getCabecero());
                    tamañoEjeY.setText(c.getUltimo());
                    nPuntos.setText(""+c.getIDaux());
                    minX.setText(""+c.getIntArg());
                    maxX.setText(""+c.getIntArg2());
                    minY.setText(""+c.getIntArg3());
                    maxY.setText(""+c.getIntArg4());
                }
                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GeneralDialogFragment generalDialogFragment =
                                GeneralDialogFragment.newInstance("CONFIGURAR GRÁFICA", "message", c.getId(), R.layout.popup_grafica);
                        generalDialogFragment.show(getParentFragmentManager(), "dialog");
                    }
                });
                break;
        }
    }
    static public void actualizarGrafica(int puntos,int minx,int maxx,int miny,int maxy, String mensaje) {
        char[] chars =mensaje.toCharArray();
        if (chars[0]==0x73){                                                            //s
            scrollGrafica.setText("ACTIVADO");
        }else if(chars[0]==0x6E){                                                       //n
            scrollGrafica.setText("DESACTIVADO");
        }
        if (chars[1]==0x73){
            escalableGrafica.setText("ACTIVADO");
        }else if(chars[1]==0x6E){
            escalableGrafica.setText("DESACTIVADO");
        }
        if (chars[2]==0x73){
            autosizeX.setText("ACTIVADO");
        }else if(chars[2]==0x6E){
            autosizeX.setText("DESACTIVADO");
        }
        if (chars[3]==0x73){
            autosizeY.setText("ACTIVADO");
        }else if(chars[3]==0x6E){
            autosizeY.setText("DESACTIVADO");

        }
        if (chars[4]==0x73){
            scrollToEnd.setText("ACTIVADO");
        }else if(chars[4]==0x6E){
            scrollToEnd.setText("DESACTIVADO");
        }




        nPuntos.setText(""+puntos);
        minX.setText(""+minx);
        maxX.setText(""+maxx);
        minY.setText(""+miny);
        maxY.setText(""+maxy);

    }
}