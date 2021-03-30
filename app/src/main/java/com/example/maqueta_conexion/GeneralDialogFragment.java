package com.example.maqueta_conexion;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import static com.example.maqueta_conexion.Fmando4.mViewViewModel;

public class GeneralDialogFragment extends BaseDialogFragment<GeneralDialogFragment.OnDialogFragmentClickListener> {

    // interface to handle the dialog click back to the Activity
    public interface OnDialogFragmentClickListener {
        public void onOkClicked(GeneralDialogFragment dialog);
        public void onCancelClicked(GeneralDialogFragment dialog);

    }

    // Create an instance of the Dialog with the input
    public static GeneralDialogFragment newInstance(String title, String message,int id,int layout) {

        GeneralDialogFragment frag = new GeneralDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("msg", message);
        args.putInt("id",id);
        args.putInt("layout",layout);
        frag.setArguments(args);


        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getArguments().getInt("layout"), container, false);
               getDialog()
                .setTitle(getArguments().getString("title"));

        switch (getArguments().getInt("layout")){
            case R.layout.popup_botones:

                Button aceptar =view.findViewById(R.id.botonaceptar);
                Botones b = mViewViewModel.getBoton(getArguments().getInt("id"));
                EditText etcab=view.findViewById(R.id.et_cabecero);
                EditText etmen=view.findViewById(R.id.et_mensaje);
                EditText etfinal=view.findViewById(R.id.et_final);

                etcab.setText(b.getCabecero());
                etmen.setText(b.getMensaje());
                etfinal.setText(b.getUltimo());

                aceptar.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Botones b = mViewViewModel.getBoton(getArguments().getInt("id"));

                        EditText etcab=view.findViewById(R.id.et_cabecero);
                        EditText etmen=view.findViewById(R.id.et_mensaje);
                        EditText etfinal=view.findViewById(R.id.et_final);

                        String fin=etfinal.getText().toString();
                        String cab=etcab.getText().toString();
                        String men=etmen.getText().toString();
                        Fbotones.actualizCab=cab;
                        Fbotones.actualizMen=men;

                        b.setCabecero(cab);
                        b.setMensaje(men);
                        b.setFinal(fin);

                        mViewViewModel.añadeBoton(b);
                        switch (b.getBoton()){
                            case "boton":
                                Fbotones.actualizar(cab,men,fin);
                                break;
                            case "terminal":
                                TerminalesEditar.actualizarTermi(cab,men,fin);
                                break;
                        }
                        //Fbotones.actualizar(cab,men);

                        dismiss();
                    }
                });
                break;


            case R.layout.popup_pantalla:
                Button aceptarPantalla =view.findViewById(R.id.aceptar_pantalla);
                RadioGroup radiogrouppantalla = view.findViewById(R.id.radiogroup);
                EditText editText =view.findViewById(R.id.identificador);
                Botones p = mViewViewModel.getBoton(getArguments().getInt("id"));
                radiogrouppantalla.check(p.getIDaux());
                editText.setText(p.getMensaje());
                aceptarPantalla.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RadioGroup radiogroup = view.findViewById(R.id.radiogroup);
                        EditText editText =view.findViewById(R.id.identificador);
                        int radioID=radiogroup.getCheckedRadioButtonId();
                        Botones p = mViewViewModel.getBoton(getArguments().getInt("id"));
                        if ( editText.getText()!=null) {
                            p.setMensaje(editText.getText().toString());
                            if (editText.getText().toString().equals("")){
                                p.setMensaje("SINCARACTER");
                            }
                        }

                        p.setIDaux(radioID);
                        mViewViewModel.añadeBoton(p);
                        EditarPantalla.actualizarPantalla(radioID,editText.getText().toString());

                        dismiss();
                    }
                });

                break;

            case R.layout.popup_interruptor:
                Button aceptarInterruptor =view.findViewById(R.id.aceptar_interruptor);
                RadioGroup radiogroup = view.findViewById(R.id.radiogroup2);
                EditText activado  =view.findViewById(R.id.et_activado);
                EditText desactivado  =view.findViewById(R.id.et_desactivado);
                Botones i = mViewViewModel.getBoton(getArguments().getInt("id"));
                radiogroup.check(i.getIDaux());
                activado.setText(i.getMensaje());
                desactivado.setText(i.getCabecero());
                aceptarInterruptor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RadioGroup radiogroup = view.findViewById(R.id.radiogroup2);
                        EditText activado  =view.findViewById(R.id.et_activado);
                        EditText desactivado  =view.findViewById(R.id.et_desactivado);
                        int radioID=radiogroup.getCheckedRadioButtonId();
                        Botones p = mViewViewModel.getBoton(getArguments().getInt("id"));
                        if ( activado.getText()!=null) {
                            p.setMensaje(activado.getText().toString());
                        }

                        if ( activado.getText()!=null) {
                            p.setCabecero(desactivado.getText().toString());
                        }

                        p.setIDaux(radioID);

                        mViewViewModel.añadeBoton(p);
                        EditarInterruptor.actualizarInterruptor(radioID,activado.getText().toString(),desactivado.getText().toString());
                        dismiss();
                    }
                });

                break;

        }



        return view;
    }

}
