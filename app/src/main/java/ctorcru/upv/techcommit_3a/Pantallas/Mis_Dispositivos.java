package ctorcru.upv.techcommit_3a.Pantallas;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ctorcru.upv.techcommit_3a.Modelo.Usuario;
import ctorcru.upv.techcommit_3a.R;
import ctorcru.upv.techcommit_3a.ServicioEscuchaBeacons;
// -----------------------------------------------------------------------------------------
/**
 * @brief Aquí se encuentra el código que configura la funcionalidad de la app
 * Autora: Roberto Matilla Augustinus
 * Archivo: Mis_Dispositivos.java
 **/
// -----------------------------------------------------------------------------------------



public class Mis_Dispositivos extends AppCompatActivity {
    // -----------------------------------------------------------------------------------------
    // DECLARACIONES DE VARIABLES
    // -------------------------------------------------------------------
    private String datosUsuario;
    private Usuario infoUsuario = new Usuario();
    private  Usuario dtosdef= new Usuario();
    private SharedPreferences preferencias;
    private TextView nombreUsuario;
    private Button botonBusqueda;
    private Button botonCerrarSesion;
    private static Mis_Dispositivos myContext;
    private Button botonDetenerBusqueda;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    public static Activity fa;
    private AlertDialog.Builder cerrarSesioon;
    public ImageView sinsenal,mediasenal,buenaSenal,malaSenal;
    boolean notificacionMostrada = false;
    private Button botonMaximoExcedido;
    FloatingActionButton fab;
    BottomNavigationView bottomNavigationView;
    // -------------------------------------------------------------------


    // -------------------------------------------------------------------
    //MÉTODO ONCREATE
    @Override
    protected void onCreate(Bundle savedInstanceState){
        preferencias = getSharedPreferences("label", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_dispositivos);
        ScrollView scrollView = findViewById(R.id.scroll);
        ConstraintLayout container = findViewById(R.id.contenedor);

// Obtenemos la posición del contenedor en la pantalla
        int[] location = new int[2];
        container.getLocationOnScreen(location);
        int containerTop = location[1];

// Establecemos el margen superior del ScrollView en la posición del contenedor
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) scrollView.getLayoutParams();
        params.topMargin = containerTop;
        scrollView.setLayoutParams(params);

        // ----------------------------------------------------------
        //Aquí creamos la barra de navegación
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().hide();

        // ----------------------------------------------------------
        // ----------------------------------------------------------
        //Enlazamos los objetos con los elementos
        try {
            cargarDatos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //-----------------------------------------------
        //Asignamos sus respectivas variables los editext y lo que contendrán
        botonBusqueda = findViewById(R.id.botonBusqueda);
        //botonMaximoExcedido = findViewById(R.id.maximoexcedido);
        botonDetenerBusqueda = findViewById(R.id.botonDetenerBusqueda);
        botonCerrarSesion = findViewById(R.id.cerrar_sesion);
        nombreUsuario = findViewById(R.id.txtNombreh);
        sinsenal = findViewById(R.id.sinconexion);
        mediasenal = findViewById(R.id.mediaconexion);
        buenaSenal = findViewById(R.id.totalconexion);
        malaSenal = findViewById(R.id.pocaconexion);
        datosUsuario= getIntent().getStringExtra("infoUsuario");
        String userpref= preferencias.getString("allinfoUser","");
        //-----------------------------------------------

        //-----------------------------------------------
        //Declaración de variables para el BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);
        bottomNavigationView.setSelectedItemId(R.id.Inicio);
        fab = findViewById(R.id.fab);

        try {
            dtosdef= infoUsuario.JsonToString(userpref);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            infoUsuario.JsonToString(userpref);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        nombreUsuario.setText("Hola "+ dtosdef.getNombre()+", Te damos la bienvenida a TechCommit");
        fa = this;
        //-----------------------------------------------

        //-----------------------------------------------
        //Acciones del BottomNavigationView
        bottomNavigationView.getMenu().getItem(2).setOnMenuItemClickListener(item -> {
            Intent intent2 = new Intent(Mis_Dispositivos.this, Mi_Perfil.class);
            startActivity(intent2);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        });

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(Mis_Dispositivos.this, Vista_Mapa.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
        //-----------------------------------------------


        //-----------------------------------------------
        //Indicamos los listeners de los botones
        botonBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificacionMostrada = false;
                Toast.makeText(getApplicationContext(), "La búsqueda comenzará", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Mis_Dispositivos.this, ServicioEscuchaBeacons.class);
                cambiarvalor();
                comprobacion();
                String nombreSensor = preferencias.getString("CodigoDispositivo","");
                i.putExtra("nombreSensor", nombreSensor);
                    startService(i);
            }
        });

        botonDetenerBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "La búsqueda se detendrá", Toast.LENGTH_SHORT).show();
                stopService(new Intent(Mis_Dispositivos.this, ServicioEscuchaBeacons.class));
                sinsenal.setVisibility(View.VISIBLE);
                mediasenal.setVisibility(View.INVISIBLE);
                buenaSenal.setVisibility(View.INVISIBLE);
                malaSenal.setVisibility(View.INVISIBLE);
            }
        });

//        botonMaximoExcedido.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Simulador de notificación", Toast.LENGTH_SHORT).show();
//                lanzarNotificacionMaximoExcedido();
//            }
//        });
        //-----------------------------------------------

        //-----------------------------------------------
        // Crear una nueva instancia de ScheduledExecutorService
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        // Programar la tarea para que se ejecute cada segundo
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // Llamar a la función aquí
               comprobarEstadoBluetooth();
            }
        }, 0, 1, TimeUnit.SECONDS);

        //-----------------------------------------------
        // Pedimos permisos para acceder al bluetooth si no los tuviéramos
        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(Mis_Dispositivos.getInstance(), new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.BLUETOOTH_SCAN,Manifest.permission.BLUETOOTH_ADVERTISE,Manifest.permission.BLUETOOTH_CONNECT}, 0);
        }
        else {
            Log.d("PERMISOS", "Ya teníamos permisos");
        }
        //-----------------------------------------------
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief La funcion onBackPressed() se encarga de cerrar el menú lateral si está abierto dando atrás
     **/
    // ---------------------------------------------------------------------------------------------
    @Override
    public void onBackPressed() {
        //No hacemos nada
    }
    // ---------------------------------------------------------------------------------------------
    @Override
    public void onResume() {
        super.onResume();
        try {
            cargarDatos();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onRestart() {
        super.onRestart();
        try {
            cargarDatos();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onStart() {
        super.onStart();

        try {
            cargarDatos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Esta función se encarga de crear las opciones del menú lateral
     * @param menu
     * @return true
     **/
    // ---------------------------------------------------------------------------------------------

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.mis__dispositivos, menu);
//        return true;
//    }
    // ---------------------------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Esta función se encarga de poder seleccionar las opciones del menú lateral y saber la seleccionada
     * @param item
     * @return true
     **/
    // ---------------------------------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cerrar_sesion) {
            cerrarSesioon = new AlertDialog.Builder(this);
            cerrarSesioon.setTitle("Cerrar Sesión");
            cerrarSesioon.setMessage("¿Estás seguro de que quieres cerrar sesión?");
            cerrarSesioon.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    cerrarSesion(null);
                    SharedPreferences.Editor mEditor = preferencias.edit();
                    mEditor.putString("usuarioIniciado", "ninguno");
                    mEditor.putString("allinfoUser","ninguno");
                    mEditor.putString("allinfosensores","ninguno");
                    mEditor.apply();
                    finish();
                }
            });
            cerrarSesioon.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            cerrarSesioon.show();
        }

        return super.onOptionsItemSelected(item);
    }
    // ---------------------------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------------------
    private void cargarDatos()throws JSONException{
        String userpref= preferencias.getString("allinfoUser","");
        Usuario dtosdef;
        dtosdef= infoUsuario.JsonToString(userpref);
    }
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    public void cerrarSesion(View view){
        Log.d("cerrarSesion", "llego aqui");
        SharedPreferences.Editor mEditor = preferencias.edit();
        mEditor.putString("usuarioIniciado", "ninguno");
        mEditor.putString("allinfoUser","ninguno");
        mEditor.putString("allinfosensores","ninguno");
        mEditor.putString("infoUsuario","ninguno");
        mEditor.apply();
        Intent i = new Intent(this, Pre_Login_Registro.class);
        startActivity(i);
    }
    // ---------------------------------------------------------------------------------------------

    //getInstance
    public Mis_Dispositivos() {
        myContext =  this;
    }

    public static Mis_Dispositivos getInstance() {
        return myContext;
    }



    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Esta función se encarga de lanzar una notificación.
     * Diseño de la notificación: https://developer.android.com/guide/topics/ui/notifiers/notifications.html
     **/
    public void lanzarNotificacionSinConexionBluetooth(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "notify_001");
        Intent ii = new Intent(this, Mis_Dispositivos.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("El bluetooth está desactivado. Activalo para poder iniciar el servicio.");
        bigText.setBigContentTitle("Aviso");
        bigText.setSummaryText("Incidencia Bluetooth");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_sensor);
        mBuilder.setContentTitle("Aviso");
        mBuilder.setContentText("El bluetooth está desactivado. Activalo para poder iniciar el servicio.");
        mBuilder.setPriority(Notification.PRIORITY_MIN);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }
        mNotificationManager.notify(0, mBuilder.build());
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Esta función se encarga de lanzar una notificación.
     * Diseño de la notificación: https://developer.android.com/guide/topics/ui/notifiers/notifications.html
     **/
    public void lanzarNotificacionMaximoExcedido(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "notify_001");
        Intent ii = new Intent(this, Mis_Dispositivos.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Estás midiendo mucha cantidad de Ozono");
        bigText.setBigContentTitle("Aviso");
        bigText.setSummaryText("Límite de Ozono Excedido");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_sensor);
        mBuilder.setContentTitle("Aviso");
        mBuilder.setContentText("Estás midiendo mucha cantidad de Ozono");
        mBuilder.setPriority(Notification.PRIORITY_MIN);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }
        mNotificationManager.notify(0, mBuilder.build());
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Esta función se encarga de lanzar una notificación.
     * Diseño de la notificación: https://developer.android.com/guide/topics/ui/notifiers/notifications.html
     **/
    public void lanzarNotificacionAltaDistanciaSensor(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "notify_001");
        Intent ii = new Intent(this, Mis_Dispositivos.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("El dispositivo está muy lejos. Es posible que se desconecte.");
        bigText.setBigContentTitle("Aviso");
        bigText.setSummaryText("Distancia entre el teléfono y el sensor");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_sensor);
        mBuilder.setContentTitle("Aviso");
        mBuilder.setContentText("El dispositivo está muy lejos. Es posible que se desconecte.");
        mBuilder.setPriority(Notification.PRIORITY_MIN);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }
        mNotificationManager.notify(0, mBuilder.build());
    }
    // ---------------------------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Esta función se encarga de lanzar una notificación.
     * Diseño de la notificación: https://developer.android.com/guide/topics/ui/notifiers/notifications.html
     **/
    public void lanzarNotificacionYaNoReciboBeacons(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "notify_001");
        Intent ii = new Intent(this, Mis_Dispositivos.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Has dejado de recibir información de tu dispositivo.");
        bigText.setBigContentTitle("Aviso");
        bigText.setSummaryText("Conexión con el dispositivo");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_sensor);
        mBuilder.setContentTitle("Aviso");
        mBuilder.setContentText("Has dejado de recibir información de tu dispositivo.");
        mBuilder.setPriority(Notification.PRIORITY_MIN);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }
        mNotificationManager.notify(0, mBuilder.build());
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Esta función se encarga de comprobar si el bluetooth está activado.
     * Diseño de la función: https://developer.android.com/guide/topics/connectivity/bluetooth.html
     **/
    public void comprobarEstadoBluetooth(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // El dispositivo no tiene Bluetooth
                lanzarNotificacionSinConexionBluetooth();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                // Bluetooth está deshabilitado, se puede pedir al usuario que lo habilite
                lanzarNotificacionSinConexionBluetooth();
            } else {
                // Bluetooth está habilitado
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Esta función se encarga de lanzar una notificación.
     * Diseño de la notificación: https://developer.android.com/guide/topics/ui/notifiers/notifications.html
     **/
    public void lanzarNotificacionDispositivoEncontrado(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "notify_001");
        Intent ii = new Intent(this, Mis_Dispositivos.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("La conexión con tu llavero se ha realizado con éxito.");
        bigText.setBigContentTitle("Conexión establecida");
        bigText.setSummaryText("Conexión con el dispositivo");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_sensor);
        mBuilder.setContentTitle("Conexión establecida");
        mBuilder.setContentText("La conexión con tu llavero se ha realizado con éxito.");
        mBuilder.setPriority(Notification.PRIORITY_MIN);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }
        mNotificationManager.notify(0, mBuilder.build());
    }
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Esta función se encarga de comprobar si el teléfono deja de recibir beacons tras un periodo de tiempo con Handler.
     * Diseño de la función: https://developer.android.com/guide/topics/connectivity/bluetooth.html
     **/
    public void comprobacion(){
        final Handler handler = new Handler();
        final int delay = 10000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                if(ServicioEscuchaBeacons.getInstance().contadorX==ServicioEscuchaBeacons.getInstance().contador){
                    stopService(new Intent(Mis_Dispositivos.this, ServicioEscuchaBeacons.class));
                    if (!notificacionMostrada) {
                        Mis_Dispositivos.getInstance().lanzarNotificacionYaNoReciboBeacons();
                        notificacionMostrada = true;
                    }
                    sinsenal.setVisibility(View.VISIBLE);
                    buenaSenal.setVisibility(View.INVISIBLE);
                    mediasenal.setVisibility(View.INVISIBLE);
                    malaSenal.setVisibility(View.INVISIBLE);
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    // ---------------------------------------------------------------------------------------------
    public void cambiarvalor(){
        final Handler handler = new Handler();
        final int delay = 9500; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                ServicioEscuchaBeacons.getInstance().contadorX=ServicioEscuchaBeacons.getInstance().contador;
                handler.postDelayed(this, delay);
            }
        }, delay);
    }
    // ---------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mis__dispositivos, menu);
        return true;
    }

}