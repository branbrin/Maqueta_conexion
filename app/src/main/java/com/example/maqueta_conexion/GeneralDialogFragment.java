package com.example.maqueta_conexion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GeneralDialogFragment extends BaseDialogFragment<GeneralDialogFragment.OnDialogFragmentClickListener> {

    // interface to handle the dialog click back to the Activity
    public interface OnDialogFragmentClickListener {
        public void onOkClicked(GeneralDialogFragment dialog);
        public void onCancelClicked(GeneralDialogFragment dialog);

    }

    // Create an instance of the Dialog with the input
    public static GeneralDialogFragment newInstance(String title, String message,int id) {

        GeneralDialogFragment frag = new GeneralDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("msg", message);
        args.putInt("id",id);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_botones, container, false);
               getDialog()
                .setTitle(getArguments().getString("title"));

        Button aceptar =view.findViewById(R.id.botonaceptar);
        aceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Botones b =Fmando4.mViewViewModel.getBoton(getArguments().getInt("id"));
                EditText etcab=view.findViewById(R.id.et_cabecero);
                EditText etmen=view.findViewById(R.id.et_mensaje);
                EditText etfinal=view.findViewById(R.id.et_final);
                String fin=etfinal.getText().toString();
                String cab=etcab.getText().toString();
                String men=etmen.getText().toString()+fin;
                Fbotones.actualizCab=cab;
                Fbotones.actualizMen=men;

                b.setCabecero(cab);
                b.setMensaje(men);
                Fmando4.mViewViewModel.añadeBoton(b);
                Fbotones.actualizar(cab,men);
                dismiss();
            }
        });

        return view;
    }

}
