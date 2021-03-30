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
 * Use the {@link EditarPantalla#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarPantalla extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView mPantalla;
   static TextView pantallaIdentificador;
   static TextView pantallaModo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarPantalla() {
        // Required empty public constructor
    }
    public EditarPantalla(TextView textView) {
        mPantalla = textView;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarPantalla.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarPantalla newInstance(String param1, String param2) {
        EditarPantalla fragment = new EditarPantalla();
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
        return inflater.inflate(R.layout.fragment_editar_pantalla, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button boton = view.findViewById(R.id.boton_configpantalla);
        pantallaIdentificador = view.findViewById(R.id.pant_id);
        pantallaModo = view.findViewById(R.id.modo_pantalla);
        Botones caux = (Botones) mPantalla.getTag();
        Botones c=Fmando4.mViewViewModel.getBoton(caux.getId());
        pantallaIdentificador.setText(""+c.getMensaje());
        if (c.getIDaux() == R.id.radioRecibido) {
            pantallaModo.setText("RECIBIR");
        } else if (c.getIDaux() == R.id.radioEnviado) {
            pantallaModo.setText("ENVIAR");
        }
        else if(c.getIDaux() == R.id.radioAmbos){
            pantallaModo.setText("AMBOS");
        }






        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralDialogFragment generalDialogFragment =
                        GeneralDialogFragment.newInstance("CONFIGURAR PANTALLA", "message", c.getId(), R.layout.popup_pantalla);
                generalDialogFragment.show(getParentFragmentManager(), "dialog");


            }
        });
    }
    static public void actualizarPantalla(int IDaux, String identificador) {
        if (IDaux == R.id.radioRecibido) {
            pantallaModo.setText("RECIBIR");
        } else if (IDaux == R.id.radioEnviado) {
            pantallaModo.setText("ENVIAR");
        }else if(IDaux==R.id.radioAmbos){
            pantallaModo.setText("AMBOS");
        }

        pantallaIdentificador.setText(identificador);

    }
}