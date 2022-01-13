package com.example.maqueta_conexion;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.maqueta_conexion.MainActivity.mViewViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TerminalesEditar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TerminalesEditar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ConstraintLayout mTermi;
    static TextView mensaje;
    static TextView cabecero;
    static TextView ultimo;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TerminalesEditar() {
        // Required empty public constructor
    }

    public TerminalesEditar(ConstraintLayout constraintLayout) {
        mTermi = constraintLayout;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TerminalesEditar.
     */
    // TODO: Rename and change types and number of parameters
    public static TerminalesEditar newInstance(String param1, String param2) {
        TerminalesEditar fragment = new TerminalesEditar();
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
        return inflater.inflate(R.layout.fragment_terminales_editar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button boton = view.findViewById(R.id.boton_configtermi);
        EditText etcab = view.findViewById(R.id.popup_botones);

        mensaje = view.findViewById(R.id.terminal_mensaje);
        cabecero = view.findViewById(R.id.terminal_cabecero);
        ultimo = view.findViewById(R.id.terminal_final);

        switch (Mando4.numeroPanel){
            case 0:
                Panel0 termaux = (Panel0) mTermi.getTag();
                Panel0 terminal=mViewViewModel.getViewByIDPanel0(termaux.getId());
                if (terminal!=null){
                    mensaje.setText("" + terminal.getMensaje());
                    cabecero.setText("" + terminal.getCabecero());
                    ultimo.setText("" + terminal.getUltimo());
                }




                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GeneralDialogFragment generalDialogFragment =
                                GeneralDialogFragment.newInstance("CONFIGURAR BOTON", "message", terminal.getId(),R.layout.popup_botones);
                        generalDialogFragment.show(getParentFragmentManager(), "dialog");
                    }
                });
                break;
            case 4:
                Botones caux = (Botones) mTermi.getTag();
                Botones c=mViewViewModel.getBoton(caux.getId());
                if (c!=null){
                    mensaje.setText("" + c.getMensaje());
                    cabecero.setText("" + c.getCabecero());
                    ultimo.setText("" + c.getUltimo());
                }




                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GeneralDialogFragment generalDialogFragment =
                                GeneralDialogFragment.newInstance("CONFIGURAR BOTON", "message", c.getId(),R.layout.popup_botones);
                        generalDialogFragment.show(getParentFragmentManager(), "dialog");
                    }
                });
                break;

        }


    }
    static public void actualizarTermi(String cabecer,String mensaj, String ultim){
        mensaje.setText(""+mensaj);
        cabecero.setText(""+cabecer);
        ultimo.setText("" +ultim);
    }
}