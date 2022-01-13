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
import android.widget.Switch;
import android.widget.TextView;

import static com.example.maqueta_conexion.MainActivity.mViewViewModel;

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
        View view = inflater.inflate(getArguments().getInt("layout"), container, true);
               getDialog()
                .setTitle(getArguments().getString("title"));


               switch (Mando4.numeroPanel){
                   case 0:
                       switch (getArguments().getInt("layout")){
                           case R.layout.popup_botones:

                               Button aceptar =view.findViewById(R.id.botonaceptar);
                               Panel0 b = mViewViewModel.getViewByIDPanel0(getArguments().getInt("id"));
                               EditText etcab=view.findViewById(R.id.et_cabecero);
                               EditText etmen=view.findViewById(R.id.et_mensaje);
                               EditText etfinal=view.findViewById(R.id.et_final);

                               etcab.setText(b.getCabecero());
                               etmen.setText(b.getMensaje());
                               etfinal.setText(b.getUltimo());

                               aceptar.setOnClickListener(new View.OnClickListener() {

                                   @Override
                                   public void onClick(View v) {
                                       Panel0 b = mViewViewModel.getViewByIDPanel0(getArguments().getInt("id"));

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

                                       mViewViewModel.añadeElementoPanel0(b);
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
                               Panel0 p = mViewViewModel.getViewByIDPanel0(getArguments().getInt("id"));
                               radiogrouppantalla.check(p.getIDaux());
                               editText.setText(p.getMensaje());
                               aceptarPantalla.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       RadioGroup radiogroup = view.findViewById(R.id.radiogroup);
                                       EditText editText =view.findViewById(R.id.identificador);
                                       int radioID=radiogroup.getCheckedRadioButtonId();
                                       Panel0 p = mViewViewModel.getViewByIDPanel0(getArguments().getInt("id"));
                                       if ( editText.getText()!=null) {
                                           p.setMensaje(editText.getText().toString());
                                           if (editText.getText().toString().equals("")){
                                               p.setMensaje("SINCARACTER");
                                           }
                                       }

                                       p.setIDaux(radioID);
                                       mViewViewModel.añadeElementoPanel0(p);
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
                               Panel0 i = mViewViewModel.getViewByIDPanel0(getArguments().getInt("id"));
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
                                       Panel0 p = mViewViewModel.getViewByIDPanel0(getArguments().getInt("id"));
                                       if ( activado.getText()!=null) {
                                           p.setMensaje(activado.getText().toString());
                                       }

                                       if ( activado.getText()!=null) {
                                           p.setCabecero(desactivado.getText().toString());
                                       }

                                       p.setIDaux(radioID);

                                       mViewViewModel.añadeElementoPanel0(p);
                                       EditarInterruptor.actualizarInterruptor(radioID,activado.getText().toString(),desactivado.getText().toString());
                                       dismiss();
                                   }
                               });

                               break;

                           case R.layout.popup_slider:

                               Button aceptarSlider =view.findViewById(R.id.aceptar_slider);
                               Panel0 s = mViewViewModel.getViewByIDPanel0(getArguments().getInt("id"));
                               EditText etSldrPrimer=view.findViewById(R.id.et_sldr_primer);
                               EditText etSldrCab=view.findViewById(R.id.et_sldr_cabecero);
                               EditText etSldrFinal=view.findViewById(R.id.et_sldr_final);
                               EditText etRango=view.findViewById(R.id.et_rango);
                               RadioGroup radioSlider = view.findViewById(R.id.radiogroupslider);

                               etSldrPrimer.setText(s.getCabecero());
                               etSldrCab.setText(s.getMensaje());
                               etSldrFinal.setText(s.getUltimo());
                               etRango.setText(""+s.getIntArg());
                               radioSlider.check(s.getIDaux());


                               aceptarSlider.setOnClickListener(new View.OnClickListener() {

                                   @Override
                                   public void onClick(View v) {
                                       Panel0 s = mViewViewModel.getViewByIDPanel0(getArguments().getInt("id"));

                                       EditText etSldrPrimer=view.findViewById(R.id.et_sldr_primer);
                                       EditText etSldrCab=view.findViewById(R.id.et_sldr_cabecero);
                                       EditText etSldrFinal=view.findViewById(R.id.et_sldr_final);
                                       EditText etRango=view.findViewById(R.id.et_rango);
                                       RadioGroup radiogroup = view.findViewById(R.id.radiogroupslider);
                                       int radioID=radiogroup.getCheckedRadioButtonId();

                                       String fin=etSldrFinal.getText().toString();
                                       String primer=etSldrPrimer.getText().toString();
                                       String cab=etSldrCab.getText().toString();
                                       String rango =etRango.getText().toString();
                                       //Fbotones.actualizCab=cab;
                                       //Fbotones.actualizMen=men;

                                       s.setCabecero(primer);
                                       s.setMensaje(cab);
                                       s.setFinal(fin);
                                       s.setIDaux(radioID);
                                       s.setIntArg(Integer.valueOf(rango));


                                       mViewViewModel.añadeElementoPanel0(s);
                                       EditarSlider.actualizarSlider(radioID,primer,cab,fin,Integer.valueOf(rango));

                                       dismiss();
                                   }
                               });
                               break;

                           case R.layout.popup_paneldetalles:

                               Button aceptarPanel =view.findViewById(R.id.aceptar_panel);
                               Button cancelarPanel =view.findViewById(R.id.cancelar_panel);

                               EditText ETtituloPanel=view.findViewById(R.id.ETtitulo_panel);
                               EditText ETdescripcionPanel=view.findViewById(R.id.ETdescripcion_panel);
                               ETtituloPanel.setText(MainActivity.tituloPanel.getText());
                               ETdescripcionPanel.setText(MainActivity.descripcionPanel.getText());

                               aceptarPanel.setOnClickListener(new View.OnClickListener() {

                                   @Override
                                   public void onClick(View v) {
                                       EditText ETtituloPanel=view.findViewById(R.id.ETtitulo_panel);
                                       EditText ETdescripcionPanel=view.findViewById(R.id.ETdescripcion_panel);
                                       MainActivity.tituloPanel.setText(ETtituloPanel.getText());
                                       MainActivity.descripcionPanel.setText(ETdescripcionPanel.getText());

                                       /*Panel0 actualizador0=mViewViewModel.getTitulo0("titulo");
                                       actualizador0.setStringArg(ETtituloPanel.getText().toString());
                                       actualizador0.setMensaje(ETdescripcionPanel.getText().toString());
                                       mViewViewModel.añadeElementoPanel0(actualizador0);
                                        */

                                       int numeroPanel =getArguments().getInt("id");
                                       switch (numeroPanel){
                                           case 0:
                                               Panel0 actualizador0=mViewViewModel.getTitulo0("titulo");
                                               actualizador0.setStringArg(ETtituloPanel.getText().toString());
                                               actualizador0.setMensaje(ETdescripcionPanel.getText().toString());
                                               mViewViewModel.añadeElementoPanel0(actualizador0);
                                               break;
                                           case 4:
                                               Botones actualizador=mViewViewModel.getTitulo("titulo");
                                               actualizador.setStringArg(ETtituloPanel.getText().toString());
                                               actualizador.setMensaje(ETdescripcionPanel.getText().toString());
                                               mViewViewModel.añadeBoton(actualizador);
                                               break;
                                       }









                                       //EditarSlider.actualizarSlider(radioID,primer,cab,fin,Integer.valueOf(rango));

                                       dismiss();
                                   }
                               });
                               cancelarPanel.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       dismiss();
                                   }
                               });
                               break;


                       }
                       break;
                   case 4:
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

                           case R.layout.popup_slider:

                               Button aceptarSlider =view.findViewById(R.id.aceptar_slider);
                               Botones s = mViewViewModel.getBoton(getArguments().getInt("id"));
                               EditText etSldrPrimer=view.findViewById(R.id.et_sldr_primer);
                               EditText etSldrCab=view.findViewById(R.id.et_sldr_cabecero);
                               EditText etSldrFinal=view.findViewById(R.id.et_sldr_final);
                               EditText etRango=view.findViewById(R.id.et_rango);
                               RadioGroup radioSlider = view.findViewById(R.id.radiogroupslider);

                               etSldrPrimer.setText(s.getCabecero());
                               etSldrCab.setText(s.getMensaje());
                               etSldrFinal.setText(s.getUltimo());
                               etRango.setText(""+s.getIntArg());
                               radioSlider.check(s.getIDaux());


                               aceptarSlider.setOnClickListener(new View.OnClickListener() {

                                   @Override
                                   public void onClick(View v) {
                                       Botones s = mViewViewModel.getBoton(getArguments().getInt("id"));

                                       EditText etSldrPrimer=view.findViewById(R.id.et_sldr_primer);
                                       EditText etSldrCab=view.findViewById(R.id.et_sldr_cabecero);
                                       EditText etSldrFinal=view.findViewById(R.id.et_sldr_final);
                                       EditText etRango=view.findViewById(R.id.et_rango);
                                       RadioGroup radiogroup = view.findViewById(R.id.radiogroupslider);
                                       int radioID=radiogroup.getCheckedRadioButtonId();

                                       String fin=etSldrFinal.getText().toString();
                                       String primer=etSldrPrimer.getText().toString();
                                       String cab=etSldrCab.getText().toString();
                                       String rango =etRango.getText().toString();
                                       //Fbotones.actualizCab=cab;
                                       //Fbotones.actualizMen=men;

                                       s.setCabecero(primer);
                                       s.setMensaje(cab);
                                       s.setFinal(fin);
                                       s.setIDaux(radioID);
                                       s.setIntArg(Integer.valueOf(rango));


                                       mViewViewModel.añadeBoton(s);
                                       EditarSlider.actualizarSlider(radioID,primer,cab,fin,Integer.valueOf(rango));

                                       dismiss();
                                   }
                               });
                               break;

                           case R.layout.popup_paneldetalles:

                               Button aceptarPanel2 =view.findViewById(R.id.aceptar_panel);
                               Button cancelarPanel =view.findViewById(R.id.cancelar_panel);


                               EditText ETtituloPanel=view.findViewById(R.id.ETtitulo_panel);
                               EditText ETdescripcionPanel=view.findViewById(R.id.ETdescripcion_panel);
                               ETtituloPanel.setText(MainActivity.tituloPanel.getText());
                               ETdescripcionPanel.setText(MainActivity.descripcionPanel.getText());

                               aceptarPanel2.setOnClickListener(new View.OnClickListener() {

                                   @Override
                                   public void onClick(View v) {
                                       EditText ETtituloPanel=view.findViewById(R.id.ETtitulo_panel);
                                       EditText ETdescripcionPanel=view.findViewById(R.id.ETdescripcion_panel);
                                       MainActivity.tituloPanel.setText(ETtituloPanel.getText());
                                       MainActivity.descripcionPanel.setText(ETdescripcionPanel.getText());

                                      /* Botones actualizador=mViewViewModel.getTitulo("titulo");
                                       actualizador.setStringArg(ETtituloPanel.getText().toString());
                                       actualizador.setMensaje(ETdescripcionPanel.getText().toString());
                                       mViewViewModel.añadeBoton(actualizador);

                                       */
                                      int numeroPanel =getArguments().getInt("id");
                                       switch (numeroPanel){
                                           case 0:
                                               Panel0 actualizador0=mViewViewModel.getTitulo0("titulo");
                                               actualizador0.setStringArg(ETtituloPanel.getText().toString());
                                               actualizador0.setMensaje(ETdescripcionPanel.getText().toString());
                                               mViewViewModel.añadeElementoPanel0(actualizador0);
                                               break;
                                           case 4:
                                               Botones actualizador=mViewViewModel.getTitulo("titulo");
                                               actualizador.setStringArg(ETtituloPanel.getText().toString());
                                               actualizador.setMensaje(ETdescripcionPanel.getText().toString());
                                               mViewViewModel.añadeBoton(actualizador);
                                               break;
                                       }









                                       //EditarSlider.actualizarSlider(radioID,primer,cab,fin,Integer.valueOf(rango));

                                       dismiss();
                                   }


                               });
                               cancelarPanel.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       dismiss();
                                   }
                               });
                               break;

                           case R.layout.popup_grafica:
                               Button aceptarGrafica =view.findViewById(R.id.aceptar_grafica);
                               CheckBox ch_scroll = view.findViewById(R.id.checkscroll);
                               CheckBox ch_escalable = view.findViewById(R.id.checkescalable);
                               CheckBox ch_autox = view.findViewById(R.id.checkauotox);
                               CheckBox ch_autoy = view.findViewById(R.id.checkautoy);
                               CheckBox ch_scrolltoend = view.findViewById(R.id.checkste);


                               Botones grafica = mViewViewModel.getBoton(getArguments().getInt("id"));
                               char[] chars =grafica.getMensaje().toCharArray();
                               if (chars[0]==0x73){                                                            //s
                                   ch_scroll.setChecked(true);
                               }else if(chars[0]==0x6E){                                                       //n
                                   ch_scroll.setChecked(false);
                               }
                               if (chars[1]==0x73){
                                   ch_escalable.setChecked(true);
                               }else if(chars[1]==0x6E){
                                   ch_escalable.setChecked(false);
                               }
                               if (chars[2]==0x73){
                                   ch_autox.setChecked(true);
                               }else if(chars[2]==0x6E){
                                   ch_autox.setChecked(false);
                               }
                               if (chars[3]==0x73){
                                   ch_autoy.setChecked(true);
                               }else if(chars[3]==0x6E){
                                   ch_autoy.setChecked(false);

                               }
                               if (chars[4]==0x73){
                                   ch_scrolltoend.setChecked(true);
                               }else if(chars[4]==0x6E){
                                   ch_scrolltoend.setChecked(false);
                               }
                               EditText et_tamañox =view.findViewById(R.id.et_tamañox);
                               EditText et_tamañoy =view.findViewById(R.id.et_tamañoy);
                               EditText et_npuntos =view.findViewById(R.id.et_npuntos);
                               EditText et_xmin =view.findViewById(R.id.et_xmin);
                               EditText et_xmax =view.findViewById(R.id.et_xmax);
                               EditText et_ymin =view.findViewById(R.id.et_ymin);
                               EditText et_ymax =view.findViewById(R.id.et_ymax);
                               et_tamañox.setText(grafica.getCabecero());
                               et_tamañoy.setText(grafica.getUltimo());
                               et_npuntos.setText(""+grafica.getIDaux());
                               et_xmin.setText(""+grafica.getIntArg());
                               et_xmax.setText(""+grafica.getIntArg2());
                               et_ymin.setText(""+grafica.getIntArg3());
                               et_ymax.setText(""+grafica.getIntArg4());

                               aceptarGrafica.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Botones grafica = mViewViewModel.getBoton(getArguments().getInt("id"));
                                       grafica.setCabecero(et_tamañox.getText().toString());
                                       grafica.setFinal(et_tamañoy.getText().toString());
                                       grafica.setIDaux(Integer.valueOf(et_npuntos.getText().toString()));
                                       grafica.setIntArg(Integer.valueOf(et_xmin.getText().toString()));
                                       grafica.setIntArg2(Integer.valueOf(et_xmax.getText().toString()));
                                       grafica.setIntArg3(Integer.valueOf(et_ymin.getText().toString()));
                                       grafica.setIntArg4(Integer.valueOf(et_ymax.getText().toString()));


                                       CheckBox ch_scroll = view.findViewById(R.id.checkscroll);
                                       CheckBox ch_escalable = view.findViewById(R.id.checkescalable);
                                       CheckBox ch_autox = view.findViewById(R.id.checkauotox);
                                       CheckBox ch_autoy = view.findViewById(R.id.checkautoy);
                                       CheckBox ch_scrolltoend = view.findViewById(R.id.checkste);


                                       grafica.setMensaje("");
                                       if (ch_scroll.isChecked()){
                                           grafica.setMensaje("s");
                                       }else{
                                           grafica.setMensaje("n");
                                       }
                                       if (ch_escalable.isChecked()){
                                           grafica.setMensaje(grafica.getMensaje()+"s");
                                       }else{
                                           grafica.setMensaje(grafica.getMensaje()+"n");
                                       }
                                       if (ch_autox.isChecked()){
                                           grafica.setMensaje(grafica.getMensaje()+"s");
                                       }else{
                                           grafica.setMensaje(grafica.getMensaje()+"n");
                                       }
                                       if (ch_autoy.isChecked()){
                                           grafica.setMensaje(grafica.getMensaje()+"s");
                                       }else{
                                           grafica.setMensaje(grafica.getMensaje()+"n");
                                       }
                                       if (ch_scrolltoend.isChecked()){
                                           grafica.setMensaje(grafica.getMensaje()+"s");
                                       }else{
                                           grafica.setMensaje(grafica.getMensaje()+"n");
                                       }


                                       mViewViewModel.añadeBoton(grafica);
                                       EditarGrafica.actualizarGrafica(Integer.valueOf(et_npuntos.getText().toString()),Integer.valueOf(et_xmin.getText().toString()),Integer.valueOf(et_xmax.getText().toString()), Integer.valueOf(et_ymin.getText().toString()),Integer.valueOf(et_ymax.getText().toString()),grafica.getMensaje());

                                       dismiss();
                                   }
                               });

                               break;


                       }
                       break;


               }





        return view;
    }
    @Override
    public void onStart()
    {
        super.onStart();

        if (getDialog() == null)
            return;
        if (getArguments().getInt("layout")==R.layout.popup_grafica){
            int dialogWidth = 900; // specify a value here
            int dialogHeight = 400; // specify a value here
            getDialog().getWindow().setLayout(dialogWidth, dialogHeight);

        }


        // ... other stuff you want to do in your onStart() method
    }

}
