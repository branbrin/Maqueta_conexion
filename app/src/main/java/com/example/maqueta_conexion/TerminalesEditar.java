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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TerminalesEditar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TerminalesEditar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ConstraintLayout mTermi;
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
        TextView mensaje = view.findViewById(R.id.terminal_mensaje);
        TextView cabecero = view.findViewById(R.id.terminal_cabecero);
        TextView ultimo = view.findViewById(R.id.terminal_final);
        Botones c = (Botones) mTermi.getTag();
        String s = c.getMensaje();
        if (s.length()>1){
            String m = s.substring(0, s.length() - 2);
            String u = s.substring(s.length() - 1);
            mensaje.setText("Mensaje:  " + m);
            cabecero.setText("Cabecero:  " + c.getCabecero());
            ultimo.setText("Final: " + u);
        }else {
            mensaje.setText("Mensaje: sin datos" );
            cabecero.setText("Cabecero:  " + c.getCabecero());
            ultimo.setText("Final: sin datos" );
        }




        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralDialogFragment generalDialogFragment =
                        GeneralDialogFragment.newInstance("CONFIGURAR BOTON", "message", c.getId());
                generalDialogFragment.show(getParentFragmentManager(), "dialog");


            }
        });
    }
}