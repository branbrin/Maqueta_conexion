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
        Botones b=(Botones) mButon.getTag();
        mensaje.setText("Mensaje:  "+ b.getMensaje());
        cabecero.setText("Cabecero:  "+ b.getCabecero());

boton.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view1, MotionEvent motionEvent){
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            GeneralDialogFragment generalDialogFragment =
                    GeneralDialogFragment.newInstance("CONFIGURAR BOTON", "message",b.getId());
            generalDialogFragment.show(getParentFragmentManager(),"dialog");
            return true;
        } else {
            return false;
        }
    }
});

       /*
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FragmentManager fragmentManager = getParentFragmentManager();
              //  FragPopupBotones fragPopupBotones=new FragPopupBotones(mButon);
             //   FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //etcab.setText(b.getCabecero());
                GeneralDialogFragment generalDialogFragment =
                        GeneralDialogFragment.newInstance("CONFIGURAR BOTON", "message",b.getId());
                generalDialogFragment.show(getParentFragmentManager(),"dialog");


               // Mando4.dialog.show();
               // Dialog dialog_botones=new Dialog(getActivity());
              //  dialog_botones.show();

              //  Mando4.popupContainer.setVisibility(View.VISIBLE);
               // fragmentTransaction.replace(R.id.paneles, fragPopupBotones );
               // fragmentTransaction.commit();
            }


        });
        mensaje.setText("Mensaje:  "+ actualizMen);
        cabecero.setText("Cabecero:  "+ actualizCab);
*/

    }
   static public void actualizar(String cabecer,String mensaj){
        mensaje.setText(mensaj);
        cabecero.setText(cabecer);


    }

    @Override
    public void onResume() {
        super.onResume();


    }
}
