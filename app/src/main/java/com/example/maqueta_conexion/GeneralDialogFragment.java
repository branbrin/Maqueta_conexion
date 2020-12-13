package com.example.maqueta_conexion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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



                b.setCabecero(cab);
                b.setMensaje(men);
                Fmando4.mViewViewModel.añadeBoton(b);



              /*  Fragment fragment = getParentFragment().getParentFragmentManager().findFragmentByTag("Fmando4");
                if(fragment != null) {
                    getParentFragment().getParentFragmentManager().beginTransaction().remove(fragment).commit();
                }
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.constraintLayout4, new Fmando4(),"Fmando4" );
                fragmentTransaction.commit();
*/

                dismiss();
            }
        });
       /*         .setMessage(getArguments().getString("msg"))
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Positive button clicked
                                // getActivityInstance().onOkClicked(GeneralDialogFragment.this);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // negative button clicked
                                //  getActivityInstance().onCancelClicked(GeneralDialogFragment.this);
                            }
                        }
                )
                .create();

        */
        return view;
    }
    // Create a Dialog using default AlertDialog builder , if not inflate custom view in onCreateView
  /*  @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString("title"))
                .setMessage(getArguments().getString("msg"))
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                    @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Positive button clicked
                               // getActivityInstance().onOkClicked(GeneralDialogFragment.this);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                    @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // negative button clicked
                              //  getActivityInstance().onCancelClicked(GeneralDialogFragment.this);
                            }
                        }
                )
             .create();
   }
*/
}
