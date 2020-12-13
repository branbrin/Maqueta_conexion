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
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;





public class BluetoothMainActivity extends AppCompatActivity {
    //CONSTANTES



    //DECLARACIÓN DE VARIABLES
    static BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    static ListView listViewEmparejados;
    static ListView listViewBuscados;
    static ListaPulsable listaPulsable;
    static ListaBuscarDispositivos listaBuscarDispositivos;
    static MenuItem item_hab;
    static MenuItem item_serv;
    static ConnectedThread connectedThread;
    static Handler bluetoothHandler;
    static Handler estadoHandler;
    static TextView datos;
    static TextView estado;
    static ConnectThread connectThread;
    static AcceptThread acceptThread;
    boolean espera_consigna=false;
    String consigna="";
    static byte[] recibido;
    ArrayList trama = new ArrayList<Character>();

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


        //-->Utilidades
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_UUID);
        registerReceiver(receiver, filter); // Don't forget to unregister during onDestroy
        Intent intent = getIntent();
        bluetoothHandler= new BluetoothHandler();
        estadoHandler= new Handler();
        actualizaestado.run();
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
        }
    };
    //---------------FIN BROADCASTER RECEIVER----------------------------------------------------

    //-----------------ON ACTIVITY RESULT------------------------------------------------------------
    //Método que es llamado cuando se usa StartActivityForResult. Contiene el resultado de alguna
    //interaccion con el usuario
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // Se cambiara el nombre del boton del menu Habilitar/Deshabilitar, según lo que pulse
        //el usuario (permitir o denegar)
        if (requestCode==Constantes.REQUEST_ENABLE_BT){
            //REQUEST_ENABLE-BT: codigo que identifica de que acción se trata
            //RESULT_OK: el usuario permitió la habilitación del bluetooth
            //RESULT_CANCELED: el usuario denegó la habilitación del bluetooth
            if (resultCode==RESULT_OK){
                item_hab.setTitle("Deshabilitar Bluetooth");
            }
            if (resultCode==RESULT_CANCELED){
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
                        if (MotorActivity.estado!=null){
                            MotorActivity.estado.setText("CONECTADO CLIENTE");
                        }

                    }else{
                        estado.setText("DESCONECTADO CLIENTE");
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
            convertView.setBackgroundColor(position*Color.YELLOW*10);

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
                    if (Mando4.conectar_mando==4){
                        Intent intentMando4 =new Intent(BluetoothMainActivity.this, Mando4.class);
                        Mando4.conectar_mando=-1;
                        startActivity(intentMando4);
                    }else {
                        Intent intentMotor =new Intent(BluetoothMainActivity.this, MotorActivity.class);
                        intentMotor.putExtra("bluto",user.id);
                        startActivity(intentMotor);
                    }



                }
            });

            return convertView;
        }
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
            convertView.setBackgroundColor(position*Color.green(3)*10);

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
            byte[] buffer = new byte[512];  // buffer store for the stream
            int bytes; // bytes returned from read()

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
        //Parámetros: mmsocket y mmDevice son los que se usan realmete para la conexión
        //mmIndex y mmUser solo son para activar/desactivar la progressBar del arraAdapter
        public   BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        // private final int mmIndex;
        //  private final MainActivity.User mmUser;
        //Constructor de la clase: se crea el soket para la conexión, con la id
        //del dispositivo y un UUID.
        public  ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            //UUID es un coódigo que identifica el dispositivo al que uno se quiere conectar
            //Si no se conoce se puede obtener con el método de la clase BluetoothDevice
            //fetchUuidsWithSdp()
            String uuidString ="00001101-0000-1000-8000-00805f9b34fb";
            UUID uuid = UUID.fromString(uuidString);

            //Asigno a los argumentos a los parámetros
            mmDevice = device;
            //  mmIndex= index;
            //   mmUser= user;

            //Intento crear un socket, que se usará para conectar
            try{
                tmp = mmDevice.createRfcommSocketToServiceRecord(uuid);
            } catch(IOException e){
                Log.e("movidas","Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }
        //Método heredado de la clase Thread. Se inicia cuando se llama al metodo .Start
        //cuando se pulsa en alguna de las filas del ArrrayAdapter
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
                 //   datos.setText("--FalloCliente--");

                    //cargo en el log la excepción si no se dio desconectado
                }catch (IOException closeException){
                    Log.e("movidas","Could not close the client socket", closeException);
                }
                return;
            }
            if (mmSocket.isConnected()){
               // datos.setText("--ConectadoCliente--");
                connectedThread=new ConnectedThread(mmSocket);
                connectedThread.start();
            }

            //manageMyConnectedSocket(mmSocket);
        }


        //método que cancela la conexión creada por este hilo
        public void cancel(){
            //intento desconectar
            try {
                mmSocket.close();
                //método que interactua con el hilo principal


                // si no se conecta lo cargo en el log
            }catch (IOException e){
                Log.e("movidas", "Could not close the client socket", e);
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

                gestionar_mensaje(mensaje.arg1);
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
        }
    }
    public void intento(View view){
        Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
    }



}
