package com.example.maqueta_conexion;

import android.bluetooth.BluetoothAdapter;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.database.CharArrayBuffer;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.Parcelable;
//import android.support.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.service.autofill.CharSequenceTransformation;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.maqueta_conexion.fragmentosMandos.Fmando4Activado;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.example.maqueta_conexion.MainActivity.estadoBT;
import static com.example.maqueta_conexion.MainActivity.mViewViewModel;
import static com.example.maqueta_conexion.fragmentosMandos.Fmando4Activado.serie0;


public class BluetoothMainActivity extends AppCompatActivity {
    //CONSTANTES
    //DECLARACIÓN DE VARIABLES
    public static BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public static Boolean BT_conectado;
    public static String BT_id;
    static ListView listViewEmparejados;
    static ListaPulsable listaPulsable;
    static ListView listViewBuscados;
    static ListaBuscarDispositivos listaBuscarDispositivos;
    static MenuItem item_hab;
    static MenuItem item_serv;
    public static ConnectThread connectThread;
    static ConnectedThread connectedThread;
    static Handler bluetoothHandler;
    static Handler estadoHandler;
    static Handler pantallaHandler;
    static TextView datos;
    static TextView estado;

    static AcceptThread acceptThread;
    boolean espera_consigna=false;
    boolean recibir=false;
    boolean datosGrafica=false;
    boolean coma;
    int tiempo;
    String consigna="";
    static byte[] recibido;
    String xy;

    ArrayList trama = new ArrayList<Character>();
    FragmentManager fragmentManager= this.getSupportFragmentManager();
    char caracterAnterior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //INICIALIZACIONES
        //-->Intetaccion con usuario
        listaPulsable = new ListaPulsable(this,0);
        listViewEmparejados=findViewById(R.id.listView);
        listViewEmparejados.setAdapter(listaPulsable);
        listaBuscarDispositivos=new ListaBuscarDispositivos(this,0);
        listViewBuscados=findViewById(R.id.listBusca);
        listViewBuscados.setAdapter(listaBuscarDispositivos);
        estado= findViewById(R.id.estado2);
        datos= findViewById(R.id.tv);
        datos.setMovementMethod(new ScrollingMovementMethod());
        BT_conectado=false;
        tiempo=0;
        xy="";
        coma=false;
        //-->Utilidades
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_UUID);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(receiver, filter);
        // Don't forget to unregister during onDestroy
        Intent intent = getIntent();
        bluetoothHandler= new BluetoothHandler();
        estadoHandler= new Handler();
        pantallaHandler=new Handler();
        actualizaestado.run();
        listaPulsable.clear();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                Emparejados newUser=new Emparejados(device.getName(),device.getAddress(),false);
                // Add the name and address to an array adapter to show in a ListView

                listaPulsable.add(newUser);
            }
        }
    }


    //-----------------------------MENU----------------------------------------------------------------
    public boolean onCreateOptionsMenu(Menu menu){
        //Uso método getMenuInflater de AppCompactActivity para controlar la creación  del menu
        MenuInflater inflater = getMenuInflater();
        //Asigno mi menu_conectar al creador
        inflater.inflate(R.menu.menu_principal, menu);

        item_hab =menu.findItem(R.id.accion_habilitar);
        item_serv= menu.findItem(R.id.accion_servidor);
        if(mBluetoothAdapter.isEnabled()){
            item_hab.setTitle("Deshabilitar bluetooth");
        }
        else{
            item_hab.setTitle("Habilitar bluetooth");
        }

        return true;

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //Se pulsó boton del menu "Habilitar bluetooth"
        if (id == R.id.accion_habilitar){

            //TODO 6 Comprobar si el dispositivo dispone de bluetooth
            //compruebo si el dispositivo es compatible bluetooth
            if (mBluetoothAdapter == null) {

                Toast.makeText(this,
                        "El dispositivo no dispone de módulo Bluetooth",
                        Toast.LENGTH_SHORT)
                        .show();
            }




            if (!mBluetoothAdapter.isEnabled()){
                //REQUEST_ENABLE_BT =5... Inicializado en los parametros de MainActivity

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, Constantes.REQUEST_ENABLE_BT);
            }else{
                mBluetoothAdapter.disable();
                item.setTitle("Habilitar bluetooth");

            }
        }
        if (id == R.id.accion_emparejados){
            //chorrada=0;
            //TODO 8 Mostrar dispositivos sincronizados
            //Creo un arrayAdapter para mostrar los dispositivos.
            /*Uso el metodo del adaptador bluetooth getBondedDevices() para encontrar los
              dispositivos sincronizaos*/
            listaPulsable.clear();
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            // If there are paired devices
            if (pairedDevices.size() > 0) {
                // Loop through paired devices
                for (BluetoothDevice device : pairedDevices) {
                    Emparejados newUser=new Emparejados(device.getName(),device.getAddress(),false);
                    // Add the name and address to an array adapter to show in a ListView

                    listaPulsable.add(newUser);
                }
            }
        }
        if (id==R.id.accion_buscar){
            //TODO 9 Iniciar busqueda de dispositivos Bluetooth
            //Uso método startDiscovery(). Acordarse de llamar a cancelDiscovery al acabar
            //Es necesario crear un BoardcastReciever para recibir las intents que se van
            //a recibir por parte de los dispositivos

            //Por si acaso se habilta el adaptador bluetooth
            mBluetoothAdapter.enable();
            //Inicio de la búsqueda de dispositivos
            listaBuscarDispositivos.clear();
            mBluetoothAdapter.startDiscovery();

        }


        if (id == R.id.accion_servidor){
            if (acceptThread== null){
                acceptThread= new AcceptThread();
                acceptThread.start();
                item.setTitle("cancelar Servidor");
            }else{
                if (acceptThread.isAlive()){
                    acceptThread.cancel();
                    item.setTitle("Iniciar Servidor");
                }
            }



        }

        return super.onOptionsItemSelected(item);

    }
    //--------------------------------FIN MENU----------------------------------------------------




    //--------------------------BROADCASTRECEIVER------------------------------------------------

    BroadcastReceiver receiver = new BroadcastReceiver(){
        //Sobreescribo el método onRecieve que se activa cuando algun dipositivo se intenta
        //comunicar con la app
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            //Cuando se encuentra un dispositivo, se almacena en el arrayAdapter para mostrarlo
            //por pantalla
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // EXTRA_DEVICE me dá el dispositivo que me envía la intent ACTION_FOUND
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //Creo un nuevo item de ArrayAdapter y lo añado

                Emparejados newUser=new Emparejados(device.getName(),device.getAddress(),false);
                listaBuscarDispositivos.add(newUser);


                //Este método pide al dispositivo externo que enviíe su UUID
                device.fetchUuidsWithSdp();
            }

            // El dispositivo envio sus UUID
            // En realidad solo utilié esto para obtener un primer UUID válido, que DE MOMENTO parece//
            // que vale para todos los dispositivos.
            if (BluetoothDevice.ACTION_UUID.equals(action)){
                //Guardo la matriz de UUIDs y el dispositivo al que pertenecen
                Parcelable[] uuidExtra = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
                BluetoothDevice device = intent.getParcelableExtra((BluetoothDevice.EXTRA_DEVICE));
                //si exiten UUIDs, los muestro por el log
                if(uuidExtra!=null){
                    //Recorro cada elemento de la matriz de UUIDs
                    for (Parcelable parcelable : uuidExtra) {
                        Log.d("MyApp", "Service: "+parcelable.toString()+ "Device name:"+device.getName()+ "Address"+ device.getAddress());
                        ParcelUuid parcelUuid = (ParcelUuid) parcelable;

                        UUID uuid = parcelUuid.getUuid();
                        Log.d("MyApp", "Service: "+uuid);
                    }
                }
            }
            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
            //Device is now connected
                BT_conectado=true;
                estadoBT.setText("CONECTADO");
            }
            if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
            //Device has disconnected
                BT_conectado=false;
                estadoBT.setText("DESCONECTADO");
            }
        }
    };
    //---------------FIN BROADCASTER RECEIVER----------------------------------------------------

    //-----------------ON ACTIVITY RESULT------------------------------------------------------------
    //Método que es llamado cuando se usa StartActivityForResult. Contiene el resultado de alguna
    //interaccion con el usuario
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Se cambiara el nombre del boton del menu Habilitar/Deshabilitar, según lo que pulse
        //el usuario (permitir o denegar)
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constantes.REQUEST_ENABLE_BT) {
            //REQUEST_ENABLE-BT: codigo que identifica de que acción se trata
            //RESULT_OK: el usuario permitió la habilitación del bluetooth
            //RESULT_CANCELED: el usuario denegó la habilitación del bluetooth
            if (resultCode == RESULT_OK) {
                item_hab.setTitle("Deshabilitar Bluetooth");
            }
            if (resultCode == RESULT_CANCELED) {
                item_hab.setTitle("Habilitar Bluetooth");
            }

        }
    }
    //------------------------FIN ON ACTIVITY RESULTS------------------------------------------------

    //------------------------RUNNABLE--------------------------------------------------------------
    Runnable actualizaestado = new Runnable() {
        @Override
        public void run() {
            String estadoAux = "";
                if (connectThread != null){
                    if (connectThread.mmSocket.isConnected()){
                        estado.setText("CONECTADO CLIENTE");
                        //estadoBT.setText("CONECTADO");
                        estadoBT.setTag(R.color.negro);
                        MainActivity.botonConectar.setText("DESCONECTAR");
                        if (MotorActivity.estado!=null){
                            MotorActivity.estado.setText("CONECTADO CLIENTE");
                        }

                    }else{
                        estado.setText("DESCONECTADO CLIENTE");
                      // estadoBT.setText("DESCONECTADO");
                        estadoBT.setTag(R.color.botonDesactivado);
                        MainActivity.botonConectar.setText("CONECTAR");
                        if (MotorActivity.estado!=null){
                            MotorActivity.estado.setText("DESCONECTADO CLIENTE");
                        }

                    }
                    estadoAux= (String) estado.getText();
                }
                if (acceptThread != null){
                   
                    estado.setText(""+estadoAux);
                    if (acceptThread.isAlive()){
                        estado.setText(estado.getText()+" y Servidor esperando");
                        item_serv.setTitle("Desactivar Servidor");

                    }else{
                        estado.setText(estado.getText()+" y servidor desactivado");
                        item_serv.setTitle("Inicio Servidor");
                    }
                }

                estadoHandler.postDelayed(actualizaestado,Constantes.TIEMPO_ACT);
            }

    };
    //----------------FIN RUNNABLE-----------------------------------------------------------------


    //---------------------------ON DESTROY------------------------------------------------------------

    //TODO 9.4 Finalizar el registro del BroadcastReceiver
    //Para que la app no siga recibiendo de los dispositivos externos cuando esta en segundo plano
    //cancelo el BroadcastReceiver
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        estadoHandler.removeCallbacks(actualizaestado);
    }
    //-------------------FIN ON DESTROY-------------------------------------------------------------------



    // ------------------LISTAS DINÁMICAS-------------------------------------------------------------------



    public class ListaPulsable extends ArrayAdapter {
        public ListaPulsable(Context context, int resource ) {
            super(context, resource);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //Obtengo el item de la fila correspondiente
            final Emparejados user = (Emparejados) getItem(position);

            //Si la vista no estaba creada todavía la creo a partir del layout "fila"
            if (convertView==null){
                convertView= LayoutInflater.from(getContext()).inflate(R.layout.fila,parent,false);
            }
            //Asigno los xml correspondientes
            TextView tvNombre = convertView.findViewById(R.id.tv_nombre);
            final TextView tvId = convertView.findViewById(R.id.tv_id);
            final ProgressBar progressBar= convertView.findViewById(R.id.progressBar);

            //Relleno los datos
            tvNombre.setText(user.nombre);
            tvId.setText(user.id);


            //activo o desactivo el progresBar
            if (user.progresBar){
                progressBar.setVisibility(View.VISIBLE);
            }else{
                progressBar.setVisibility(View.INVISIBLE);
            }
            //cambio el color de cada fila gradualmente

            convertView.setBackgroundColor((position+1)*(Color.YELLOW)*20);



            //método para hacer pulsable cada fila del array adapter
            //si se pulsa se inicia la conexion con el dispositivo correspondiente
            //cuando el socket está intentando conectarse se muestra un loader
            convertView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    //Obtengo el dispositivo a través de los datos de la matriz
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(user.id);
                    progressBar.setVisibility(View.VISIBLE);
                    if (connectThread !=null){
                        connectThread.cancel();
                    }
                    connectThread = new ConnectThread(device);
                    connectThread.start();
                    BT_id=user.id;
                    if (Mando4.conectar_mando==4) {
                        Intent intentMando4 = new Intent(BluetoothMainActivity.this, Mando4.class);
                        Mando4.conectar_mando = -1;

                        startActivity(intentMando4);
                  /*  }else if(Mando4.conectar_mando==11){
                        Intent intentMando4 =new Intent(BluetoothMainActivity.this, Fmando4Activado.class);
                        Mando4.conectar_mando=-1;
                        startActivity(intentMando4);
                    }

*/
                    }
                    else {
                        Intent intentMotor =new Intent(BluetoothMainActivity.this, MainActivity.class);
                        intentMotor.putExtra("bluto",user.id);
                        startActivity(intentMotor);
                    }
                }
            });
            return convertView;
        }
    }
    public void refrescar(View view){
        listaPulsable.clear();  //borrar o que había na lista
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // Enchemos a lista pairredDevices cos dispositivos sincronizados
        // E recorremos esa lista para encher a nosa lista cos datos obtidos
        if (pairedDevices.size() > 0) {

            for (BluetoothDevice device : pairedDevices) {
             Emparejados dispositivo=new Emparejados(device.getName(),device.getAddress(),false);
             //Engadimos a información a nosa lista
             listaPulsable.add(dispositivo);
            }
        }
    }

    public void buscar(View view){
        mBluetoothAdapter.enable();
        listaBuscarDispositivos.clear();
        //Inicio da búsqueda de dispositivos
        mBluetoothAdapter.startDiscovery();
    }

    public  class Emparejados {
        //Esta clase define los elementos del listView que mostrará los dispositivos emparejados
        //Las vistas tienen 2 TextViews y una loadingBar
        //Un String para cada tv, y un booleano para controlar la visibilidad de progressBar

        //Parámetros:
        public String nombre;
        public String id;
        public boolean progresBar;
        //Constructor:
        public Emparejados(String nombre, String id,boolean estado) {
            this.nombre = nombre;
            this.id = id;
            this.progresBar=estado;
        }

    }



    public class ListaBuscarDispositivos extends ArrayAdapter {
        public ListaBuscarDispositivos(Context context, int resource ) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Obtengo el item de la fila correspondiente
            final Emparejados user = (Emparejados) getItem(position);

            //Si la vista no estaba creada todavía la creo a partir del layout "fila"
            if (convertView==null){
                convertView= LayoutInflater.from(getContext()).inflate(R.layout.fila,parent,false);
            }

            //Asigno los xml correspondientes
            TextView tvNombre = convertView.findViewById(R.id.tv_nombre);
            final TextView tvId = convertView.findViewById(R.id.tv_id);

            //Relleno los datos
            tvNombre.setText(user.nombre);
            tvId.setText(user.id);

            //llamo a un metodo para cambiar el color de cada vista de forma aleatoria
            convertView.setBackgroundColor((position+1)*(Color.YELLOW)*20);

            //método para hacer pulsable cada fila del array adapter
            //Si se pulsa se intenta sincronizar con ese dispositivo
            convertView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    //Obtengo el dispositivo a través de los datos de la matriz
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(user.id);
                    //Inicia sincronización
                    device.createBond();
                }
            });
            return convertView;
        }
    }

    //-------------FIN LISTA PULSABLE-----------------------------------------------------------


    public static class AcceptThread extends Thread {
        private  final BluetoothServerSocket mmServerSocket;

        String TAG= "yoquese";

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public AcceptThread(){
            BluetoothServerSocket tmp = null;
            String uuidd ="00001101-0000-1000-8000-00805f9b34fb" ;
            UUID uuid = UUID.fromString(uuidd);

            String NAME="com.example.maqueta_conexion";
            try {
                tmp=mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, uuid);
            } catch (IOException e){
                Log.e(TAG, "Socket's listen() method failed", e);
            }
            mmServerSocket= tmp;
        }

        public void run(){
            BluetoothSocket socket = null;
            while (true){
                try{
                    socket = mmServerSocket.accept();

                    final BluetoothDevice device =socket.getRemoteDevice();

                }catch (IOException e){
                    Log.e(TAG, "Socket's accept() method failed", e);
                    break;

                }
                if (socket!=null){
                    // manageMyconnectedSocket(socket);
                    //  datos.setText("--ConectadoServidor--");
                    connectedThread = new ConnectedThread(socket);
                    connectedThread.start();



                    try {
                        mmServerSocket.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Could not close the connect socket", e);
                    }
                    break;
                }
            }
        }

        public void cancel(){
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the connect socket", e);
            }
        }
    }

    public static class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void run() {
           // byte[] buffer = new byte[512];  // buffer store for the stream
            // int bytes; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    // bytes = mmInStream.read(buffer);
                    int data=mmInStream.read();
                    //recibido=buffer;
                    // Send the obtained bytes to the UI activity
                    bluetoothHandler.obtainMessage(Constantes.MESSAGE_READ, data, -1).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {

            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }
    public static class ConnectThread extends Thread {
        // Parámetros: mmsocket y mmDevice son los que se usan realmete para la conexión
        public   BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        // Constructor de la clase: se crea el soket para la conexión, con la id
        // del dispositivo y un UUID.
        public  ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            // UUID es un coódigo que identifica el dispositivo al que uno se quiere conectar
            // Si no se conoce se puede obtener con el método de la clase BluetoothDevice
            // fetchUuidsWithSdp()
            String uuidString ="00001101-0000-1000-8000-00805f9b34fb";
            UUID uuid = UUID.fromString(uuidString);
            // Asigno a los argumentos a los parámetros
            mmDevice = device;
            //Intento crear un socket, que se usará para conectar
            try{
                tmp = mmDevice.createRfcommSocketToServiceRecord(uuid);
            } catch(IOException e){
                Log.e("movidas","Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void run(){
            //por si acaso hay activa una busqueda de dispositivos, se para, ya que tanto
            //conectarse como buscar son procesos que consumen muchos recursos
            mBluetoothAdapter.cancelDiscovery();
            final String Sdev=mmDevice.toString();
            //intento conexión
            try{
                mmSocket.connect();
                //método para interactuar con el hilo principal
                //actualizo datos de la lista de dispositivos
                //Si no se da conectado, intento desconectar y actualizo lista de dispositivos
            }catch  (IOException connectException){
                try {
                    mmSocket.close();
                    //datos.setText("--FalloCliente--");
                    //cargo en el log la excepción si no se dio desconectado
                }catch (IOException closeException){
                    Log.e("movidas","Could not close the client socket", closeException);
                }
                return;
            }
            if (mmSocket.isConnected()){
                connectedThread=new ConnectedThread(mmSocket);
                connectedThread.start();
            }
        }
        //método que cancela la conexión creada por este hilo
        public void cancel(){
            //intento desconectar
            try {
                mmSocket.close();
            }catch (IOException e){
                Log.e("issue", "Could not close the client socket", e);
            }
        }
    }

    //---------------HANDLER---------------------------------------------------------------------
    public class BluetoothHandler extends Handler{
        public void handleMessage(Message mensaje){
            if (mensaje.what==Constantes.MESSAGE_READ){
                //byte[] arrayByte=(byte[]) mensaje.obj;
                if (MotorActivity.pantalla!=null){
                    MotorActivity.pantalla.setText(""+MotorActivity.pantalla.getText()+(char)mensaje.arg1+" ");
                }
               char msg=(char)mensaje.arg1;
                if (Fmando4Activado.pantallasActivas!=null){
                    for (int i =0; i<Fmando4Activado.pantallasActivas.size();i++) {
                        TextView pantalla = Fmando4Activado.pantallasActivas.get(i);
                        switch (Mando4.numeroPanel){
                            case 0:
                                Panel0 pantaux =(Panel0) Fmando4Activado.pantallasActivas.get(i).getTag();
                                Panel0 panta=mViewViewModel.getViewByIDPanel0(pantaux.getId());
                                if (panta.getIDaux()==R.id.radioRecibido || panta.getIDaux() ==R.id.radioAmbos){
                                    if (panta.getMensaje().equals("SINCARACTER")){
                                        if (msg==1){
                                            pantalla.setTextColor(Color.RED);
                                            pantalla.append("\n"+"In:  ");
                                            pantalla.setTextColor(Color.BLACK);
                                        }
                                        pantalla.append(String.valueOf(msg));
                                    }else {
                                        if (recibir){
                                            if (panta.getMensaje().equals(String.valueOf(msg))){
                                                recibir=false;
                                                mViewViewModel.añadeElementoPanel0(panta);
                                            }else{
                                                pantalla.append(String.valueOf(msg));
                                            }
                                        }
                                        else{
                                            if (panta.getMensaje().equals(String.valueOf(msg))){
                                                pantalla.setTextColor(Color.RED);
                                                pantalla.append("\n"+"In:  ");
                                                pantalla.setTextColor(Color.BLACK);
                                                recibir=true;
                                                mViewViewModel.añadeElementoPanel0(panta);
                                            }
                                        }
                                    }
                                }
                                break;
                            case 4:
                                Botones pAux =(Botones) Fmando4Activado.pantallasActivas.get(i).getTag();
                                Botones p=mViewViewModel.getBoton(pAux.getId());
                                if (p.getIDaux()==R.id.radioRecibido || p.getIDaux() ==R.id.radioAmbos){
                                    if (p.getMensaje().equals("SINCARACTER")){
                                        if (msg==1){
                                            pantalla.setTextColor(Color.RED);
                                            pantalla.append("\n"+"In:  ");
                                            pantalla.setTextColor(Color.BLACK);
                                        }
                                        pantalla.append(String.valueOf(msg));
                                    }else {
                                        if (recibir){
                                            if (p.getMensaje().equals(String.valueOf(msg))){
                                                recibir=false;
                                                mViewViewModel.añadeBoton(p);
                                            }else{
                                                pantalla.append(String.valueOf(msg));
                                            }
                                        }
                                        else{
                                            if (p.getMensaje().equals(String.valueOf(msg))){
                                                pantalla.setTextColor(Color.RED);
                                                pantalla.append("\n"+"In:  ");
                                                pantalla.setTextColor(Color.BLACK);
                                                recibir=true;
                                                mViewViewModel.añadeBoton(p);
                                            }
                                        }
                                    }
                                }
                                break;
                        }
                    }
                }

                if (Fmando4Activado.graficasActivas!=null){
                    for (int i =0; i<Fmando4Activado.graficasActivas.size();i++) {
                        ConstraintLayout graflayout = Fmando4Activado.graficasActivas.get(i);
                        LinearLayoutCompat barra= (LinearLayoutCompat) graflayout.getChildAt(1);
                        barra.setVisibility(View.VISIBLE);
                        LinearLayoutCompat lin1 =(LinearLayoutCompat) barra.getChildAt(0);
                        CheckBox scroll=(CheckBox) lin1.getChildAt(1);

                        LinearLayoutCompat lin2 =(LinearLayoutCompat) barra.getChildAt(1);
                        CheckBox zoom=(CheckBox) lin2.getChildAt(1);

                        LinearLayoutCompat lin3 =(LinearLayoutCompat) barra.getChildAt(2);
                        CheckBox autox=(CheckBox) lin3.getChildAt(1);
                        LinearLayoutCompat lin4 =(LinearLayoutCompat) barra.getChildAt(3);
                        CheckBox autoy=(CheckBox) lin4.getChildAt(1);
                        LinearLayoutCompat lin5 =(LinearLayoutCompat) barra.getChildAt(4);
                        CheckBox STE=(CheckBox) lin5.getChildAt(1);
                        switch (Mando4.numeroPanel){
                            case 0:
                                Panel0 grafaux =(Panel0) Fmando4Activado.graficasActivas.get(i).getTag();
                                if (grafaux!=null){
                                    Panel0 graf=mViewViewModel.getViewByIDPanel0(grafaux.getId());
                                }

                                break;
                            case 4:
                                Botones graf4aux =(Botones) Fmando4Activado.graficasActivas.get(i).getTag();
                                if (graf4aux!=null){
                                    Botones graf4=mViewViewModel.getBoton(graf4aux.getId());
                                    if (datosGrafica&msg!=0x47){
                                        if (msg!=0x00){
                                            xy = xy+msg;
                                        }
                                    }
                                    if (datosGrafica&msg==0x47){
                                        datosGrafica=false;
                                        int y =Integer.valueOf(xy);
                                        tiempo++;
                                        DataPoint coordenada=new DataPoint(tiempo,y);
                                        boolean ste=false;
                                        if(graf4.getMensaje().toCharArray()[4]==0x73){
                                            ste =true;
                                        }else if(graf4.getMensaje().toCharArray()[4]==0x6E){
                                            ste =false;
                                        }
                                        GraphView grafica=(GraphView) graflayout.getChildAt(0);
                                        if (scroll.isChecked()){
                                            grafica.getViewport().setScrollable(true);
                                        }else{
                                            grafica.getViewport().setScrollable(false);
                                        }
                                        if (zoom.isChecked()){
                                            grafica.getViewport().setScalable(true);
                                        }else{
                                            grafica.getViewport().setScalable(false);
                                        }
                                        if (autox.isChecked()){
                                            grafica.getViewport().setXAxisBoundsManual(true);
                                        }else{
                                            grafica.getViewport().setXAxisBoundsManual(false);
                                        }
                                        if (autoy.isChecked()){
                                            grafica.getViewport().setYAxisBoundsManual(true);
                                        }else{
                                            grafica.getViewport().setYAxisBoundsManual(false);
                                        }
                                        if (STE.isChecked()){
                                            ste=true;
                                        }else{
                                            ste=false;
                                        }
                                        serie0.appendData(coordenada,ste,graf4.getIDaux());
                                        grafica.getGridLabelRenderer().setHorizontalAxisTitle("número de medida");
                                        grafica.getGridLabelRenderer().setVerticalAxisTitle("velocidad");
                                        xy="";
                                    }

                                    if (caracterAnterior==1&msg==0x47){
                                        datosGrafica=true;
                                    }
                                }



                                break;
                        }
                    }
                }
                gestionar_mensaje(mensaje.arg1);
                caracterAnterior=msg;
            }
        }
    }
    //-------------------FIN HANDLER--------------------------------------------------------------
    public void gestionar_mensaje(int mensaje){
        if (MotorActivity.velocidad!=null){
            MotorActivity.velocidad.setText("VELOCIDAD: ");
        }

        //MotorActivity.pruebas.setText("Tamaño= "+tamaño);
       // ArrayList trama = new ArrayList<Character>();
        trama.add((char)mensaje);
        if (MotorActivity.pruebas!=null){
            MotorActivity.pruebas.setText("-"+MotorActivity.pruebas.getText()+(char)mensaje);
        }


        if (mensaje==0x04){
            if (MotorActivity.pruebas!=null){
                MotorActivity.pruebas.setText("entrei en fin mensaje e esta é a trama"+trama.get(0)+trama.get(1)+trama.get(2)+trama.get(3));
            }

            switch ((char)trama.get(0)){
                case 'e':
                    if (MotorActivity.pruebas!=null){
                        MotorActivity.velocidad.setText(""+MotorActivity.velocidad.getText()+trama.get(1)+trama.get(2)+trama.get(3)+" RPM");
                    }
                    trama.clear();
                    break;

                case 'O':

                    trama.clear();
                    break;

                case 't':
                    //  MotorActivity.pruebas.setText(""+MotorActivity.pruebas.getText()+trama[1]);
                    if (espera_consigna==true){

                        if ((char)trama.get(1)=='#'){
                            int a = Integer.parseInt(consigna);
                            MotorActivity.enviarConsigna(a);
                            if (MotorActivity.pruebas!=null){
                                MotorActivity.pruebas.setText(""+consigna);
                            }

                            consigna="";
                            espera_consigna = false;
                        }
                        else{
                            consigna=consigna+(char)trama.get(1);

                        }

                    }
                    if ((char)trama.get(1)=='*'){
                        espera_consigna=true;
                    }

                    if ((char)trama.get(1)=='a'){
                        gestionBoton(Constantes.MARCHA);
                    }
                    if ((char)trama.get(1)=='b'){
                        gestionBoton(Constantes.PARO);
                    }
                    trama.clear();
                    break;

            }
        }
    }

    public static void gestionBoton(byte[] tipo){
        if (BluetoothMainActivity.connectThread.mmSocket!=null){
            BluetoothMainActivity.connectedThread.write(tipo);
            for (int i =0; i<Mando4.pantallas_actuales.size();i++) {
                Mando4.pantallas_actuales.get(i).setText(tipo.toString());


            }

        }
    }

    public static void envioTrama(byte[] tipo){
        if (BluetoothMainActivity.connectThread.mmSocket!=null){
            BluetoothMainActivity.connectedThread.write(tipo);
            String a="";
            for (int i=0;i<tipo.length;i++) {
                a = a + (char) tipo[i];
            }
            for (int i =0; i<Fmando4Activado.pantallasActivas.size();i++) {
              // TextView a= (TextView) context.getResources().getLayout(R.layout.pantalla);
              // a.setText(tipo.toString());
                switch (Mando4.numeroPanel){
                    case 0:
                        Panel0 pantallaAux =(Panel0) Fmando4Activado.pantallasActivas.get(i).getTag();
                        Panel0 panta=mViewViewModel.getViewByIDPanel0(pantallaAux.getId());
                        if (panta.getIDaux()==R.id.radioEnviado || panta.getIDaux() ==R.id.radioAmbos){
                            TextView pantalla=Fmando4Activado.pantallasActivas.get(i);
                            pantalla.setTextColor(Color.RED);
                            // pantalla.setText(pantalla.getText()+"\n"+"Out: ");
                            pantalla.append("\n"+"Out: ");
                            pantalla.setTextColor(Color.BLACK);
                            // pantalla.setText(Fmando4Activado.pantallasActivas.get(i).getText()+a);
                            pantalla.append(a);

                        }
                        break;
                    case 4:
                        Botones pAux =(Botones) Fmando4Activado.pantallasActivas.get(i).getTag();
                        Botones p=mViewViewModel.getBoton(pAux.getId());
                        if (p.getIDaux()==R.id.radioEnviado || p.getIDaux() ==R.id.radioAmbos){
                            TextView pantalla=Fmando4Activado.pantallasActivas.get(i);
                            pantalla.setTextColor(Color.RED);
                            // pantalla.setText(pantalla.getText()+"\n"+"Out: ");
                            pantalla.append("\n"+"Out: ");
                            pantalla.setTextColor(Color.BLACK);
                            // pantalla.setText(Fmando4Activado.pantallasActivas.get(i).getText()+a);
                            pantalla.append(a);

                        }
                        break;
                }



                }


               // pantallaHandler.obtainMessage(Constantes.PANTALLA_READ, -1, -1,tipo).sendToTarget();
               // Mando4.pantallas_actuales.get(i).setText(tipo.toString());







        }
    }
    public void intento(View view){
        Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
    }



}
