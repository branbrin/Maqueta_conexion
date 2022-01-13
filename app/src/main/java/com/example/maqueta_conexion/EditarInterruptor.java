package com.example.maqueta_conexion;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import static com.example.maqueta_conexion.MainActivity.mViewViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarInterruptor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarInterruptor extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Switch mSwitch;
    static TextView TVposIni;
    static TextView TVenvioON;
    static TextView TVenvioOff;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarInterruptor() {
        // Required empty public constructor
    }

    public EditarInterruptor(Switch switch1) {
        mSwitch = switch1;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarInterruptor.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarInterruptor newInstance(String param1, String param2) {
        EditarInterruptor fragment = new EditarInterruptor();
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
        return inflater.inflate(R.layout.fragment_editar_interruptor, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button boton = view.findViewById(R.id.boton_configinterruptor);

        switch (Mando4.numeroPanel){
            case 0:
                Panel0 interruptorAux = (Panel0) mSwitch.getTag();
                Panel0 interruptor = mViewViewModel.getViewByIDPanel0(interruptorAux.getId());
                TVposIni = view.findViewById(R.id.tv_posini);
                TVenvioON = view.findViewById(R.id.tv_envio_on);
                TVenvioOff = view.findViewById(R.id.tv_envio_off);
                if (interruptor != null) {
                    if (interruptor.getIDaux() == R.id.radioOn) {
                        TVposIni.setText("ON");
                    } else if (interruptor.getIDaux() == R.id.radioOff) {
                        TVposIni.setText("OFF");
                    }

                    TVenvioON.setText("" + interruptor.getMensaje());
                    TVenvioOff.setText("" + interruptor.getCabecero());
                }
                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GeneralDialogFragment generalDialogFragment =
                                GeneralDialogFragment.newInstance("CONFIGURAR PANTALLA", "message", interruptor.getId(), R.layout.popup_interruptor);
                        generalDialogFragment.show(getParentFragmentManager(), "dialog");


                    }
                });
                break;
            case  4:
                Botones caux = (Botones) mSwitch.getTag();
                Botones c = mViewViewModel.getBoton(caux.getId());
                TVposIni = view.findViewById(R.id.tv_posini);
                TVenvioON = view.findViewById(R.id.tv_envio_on);
                TVenvioOff = view.findViewById(R.id.tv_envio_off);
                if (c != null) {
                    if (c.getIDaux() == R.id.radioOn) {
                        TVposIni.setText("ON");
                    } else if (c.getIDaux() == R.id.radioOff) {
                        TVposIni.setText("OFF");
                    }

                    TVenvioON.setText("" + c.getMensaje());
                    TVenvioOff.setText("" + c.getCabecero());
                }
                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GeneralDialogFragment generalDialogFragment =
                                GeneralDialogFragment.newInstance("CONFIGURAR PANTALLA", "message", c.getId(), R.layout.popup_interruptor);
                        generalDialogFragment.show(getParentFragmentManager(), "dialog");


                    }
                });
                break;
        }




    }

    static public void actualizarInterruptor(int IDaux, String on, String off) {
        if (IDaux == R.id.radioOn) {
            TVposIni.setText("ON");
        } else if (IDaux == R.id.radioOff) {
            TVposIni.setText("OFF");
        }

        TVenvioON.setText(on);
        TVenvioOff.setText(off);
    }
}