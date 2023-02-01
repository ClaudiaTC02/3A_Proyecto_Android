package ctorcru.upv.techcommit_3a.Pantallas;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ctorcru.upv.techcommit_3a.Logica.Logica;
import ctorcru.upv.techcommit_3a.Modelo.DatosClimaOWM;
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
    public TextView SbotonBusqueda;
    public TextView RbotonConectando;
    public TextView SmensajeNoConectado;
    public TextView EmensajeSiConectado;
    public TextView EmensajeDistancia;
    public TextView mediaMedidas;
    private Button botonCerrarSesion;
    private static Mis_Dispositivos myContext;
    public TextView EbotonDetenerBusqueda;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    public static Activity fa;
    private AlertDialog.Builder cerrarSesioon;
    public TextView mediasenal,buenaSenal,malaSenal;
    boolean notificacionMostrada = false;
    private Button botonMaximoExcedido;
    FloatingActionButton fab;
    BottomNavigationView bottomNavigationView;
    // TextView donde se mostrará la temperatura
    TextView temperatureTextView,humiditytextview;
    // ImageView donde se mostrará la imagen del tiempo
    ImageView weatherIconImageView,infoicono,infoicono2,infoicono3,infoicono4;

    ImageView ImagenMuyBuenAire, ImagenAireNormal, ImagenCuidadoAire;
    TextView TextoMuyBuenAire, TextoAireNormal, TextoCuidadoAire, TextoInformacionAire;
    CardView CardviewCaraOzono, CardviewAire;
    private ServicioEscuchaBeacons mService;
    private boolean mBound = false;
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
        ServicioEscuchaBeacons servicioEscuchaBeacons = new ServicioEscuchaBeacons();

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
        //botonMaximoExcedido = findViewById(R.id.maximoexcedido);
        SbotonBusqueda = findViewById(R.id.SbotonBusqueda);
        botonCerrarSesion = findViewById(R.id.cerrar_sesion);
        RbotonConectando = findViewById(R.id.RbotonConectando);
        EbotonDetenerBusqueda = findViewById(R.id.EbotonDetenerBusqueda);
        SmensajeNoConectado = findViewById(R.id.SmensajeNoConectado);
        EmensajeSiConectado = findViewById(R.id.EmensajeSiConectado);
        EmensajeDistancia = findViewById(R.id.EmensajeDistancia);
        temperatureTextView = findViewById(R.id.temperature_text_view);
        humiditytextview = findViewById(R.id.humidity_text_view);
        weatherIconImageView = findViewById(R.id.weather_icon);
        nombreUsuario = findViewById(R.id.txtNombreh);
        mediasenal = findViewById(R.id.mediaconexion);
        mediaMedidas = findViewById(R.id.mediaMedidas);
        buenaSenal = findViewById(R.id.totalconexion);
        malaSenal = findViewById(R.id.pocaconexion);
        infoicono = findViewById(R.id.info_icon);
        infoicono2 = findViewById(R.id.info_icon2);
        infoicono3 = findViewById(R.id.info_icon3);
        infoicono4 = findViewById(R.id.info_icon4);
        datosUsuario= getIntent().getStringExtra("infoUsuario");
        String userpref= preferencias.getString("allinfoUser","");


        ImagenMuyBuenAire = findViewById(R.id.hombreContento);
        ImagenAireNormal = findViewById(R.id.hombreFeliz);
        ImagenCuidadoAire = findViewById(R.id.imagenConMascarilla);
        TextoMuyBuenAire = findViewById(R.id.conTranquilidad);
        TextoAireNormal = findViewById(R.id.MedioMdio);
        TextoCuidadoAire = findViewById(R.id.CuidadoAire);
        TextoInformacionAire = findViewById(R.id.InfoRelacionAire);
        CardviewCaraOzono = findViewById(R.id.CardviewCaraOzono);
        CardviewAire = findViewById(R.id.CardviewAire);
        //-----------------------------------------------

        //-----------------------------------------------
        //Declaración de variables para el BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);
        bottomNavigationView.setSelectedItemId(R.id.Inicio);
        fab = findViewById(R.id.fab);
        requestLocationPermission();


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
        SbotonBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IniciarBusqueda();
            }
        });

        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir un popup con la información de donde se obtiene la temperatura
                AlertDialog.Builder builder = new AlertDialog.Builder(Mis_Dispositivos.this);
                builder.setTitle("Información");
                builder.setMessage("La información mostrada ha sido proporcionada por OpenWeatherMap.");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        };
        infoicono.setOnClickListener(myOnClickListener);
        infoicono2.setOnClickListener(myOnClickListener);


        View.OnClickListener myOnClickListener2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir un popup con la información de donde se obtiene la temperatura
                AlertDialog.Builder builder = new AlertDialog.Builder(Mis_Dispositivos.this);
                builder.setTitle("Información Ozono");
                builder.setMessage("Según fuentes oficiales de toda España como 'agroambient.gva.es', 'larioja.org', 'granada.org'... Entre otros, se marcan los siguientes límites de ozono: " +
                        "\n\r\n Nivel de ozono normal: 0.06 - 0.1 ppm " +
                        "\n\r\n Nivel de ozono Moderado: 0.09 - 0.1 ppm " +
                        "\n\r\n Nivel de ozono alto: 0.24 ppm");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        };
        infoicono3.setOnClickListener(myOnClickListener2);
        infoicono4.setOnClickListener(myOnClickListener2);


        EbotonDetenerBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "La conexión se ha detenido", Toast.LENGTH_SHORT).show();
                buenaSenal.setVisibility(View.INVISIBLE);
                malaSenal.setVisibility(View.INVISIBLE);
                mediasenal.setVisibility(View.INVISIBLE);
                SbotonBusqueda.setVisibility(View.VISIBLE);
                SmensajeNoConectado.setVisibility(View.VISIBLE);
                RbotonConectando.setVisibility(View.INVISIBLE);
                EbotonDetenerBusqueda.setVisibility(View.INVISIBLE);
                EmensajeSiConectado.setVisibility(View.INVISIBLE);
                EmensajeDistancia.setVisibility(View.INVISIBLE);
                stopService(new Intent(Mis_Dispositivos.this, ServicioEscuchaBeacons.class));
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
        }, 0, 10, TimeUnit.SECONDS);

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
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.BLUETOOTH_SCAN,Manifest.permission.BLUETOOTH_ADVERTISE,Manifest.permission.BLUETOOTH_CONNECT}, 0);
            servicioEscuchaBeacons.inicializarBlueTooth();
        }
        //-----------------------------------------------
        new Logica().obtenerMediaMedidas(dtosdef.getCorreo());
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief La funcion onBackPressed() se encarga de cerrar el menú lateral si está abierto dando atrás
     **/
    // ---------------------------------------------------------------------------------------------
    @Override
    public void onBackPressed() {
        finish();
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
    // ---------------------------------------------------------------------------------------------
    public void imprimirMedia(String media){
        if(media != "*"){
            //Redondear media a 2 decimales
            double mediaRedondeada = Math.round(Double.parseDouble(media) * 1000.0) / 1000.0;
            String mediaRedondeadaString = String.valueOf(mediaRedondeada);

            if(Double.parseDouble(media) >= 0.2){
                // horrible
                mediaMedidas.setText(mediaRedondeadaString + " ppm");
                CardviewCaraOzono.setCardBackgroundColor(Color.parseColor("#72F44336"));
                CardviewAire.setCardBackgroundColor(Color.parseColor("#72F44336"));
                TextoInformacionAire.setVisibility(View.INVISIBLE);
                TextoAireNormal.setVisibility(View.INVISIBLE);
                TextoCuidadoAire.setVisibility(View.VISIBLE);
                TextoMuyBuenAire.setVisibility(View.INVISIBLE);
                ImagenAireNormal.setVisibility(View.INVISIBLE);
                ImagenCuidadoAire.setVisibility(View.VISIBLE);
                ImagenMuyBuenAire.setVisibility(View.INVISIBLE);
            } else if(Double.parseDouble(media) >= 0 && Double.parseDouble(media) < 0.09){
                // nais
                mediaMedidas.setText(mediaRedondeadaString + " ppm");
                CardviewCaraOzono.setCardBackgroundColor(Color.parseColor("#5295D529"));
                CardviewAire.setCardBackgroundColor(Color.parseColor("#5295D529"));
                TextoInformacionAire.setVisibility(View.INVISIBLE);
                TextoAireNormal.setVisibility(View.INVISIBLE);
                TextoCuidadoAire.setVisibility(View.INVISIBLE);
                TextoMuyBuenAire.setVisibility(View.VISIBLE);
                ImagenAireNormal.setVisibility(View.INVISIBLE);
                ImagenCuidadoAire.setVisibility(View.INVISIBLE);
                ImagenMuyBuenAire.setVisibility(View.VISIBLE);
            } else if(Double.parseDouble(media) >= 0.09 && Double.parseDouble(media) < 0.2){
                // medio
                mediaMedidas.setText(mediaRedondeadaString + " ppm");
                CardviewCaraOzono.setCardBackgroundColor(Color.parseColor("#6DFEDC46"));
                CardviewAire.setCardBackgroundColor(Color.parseColor("#6DFEDC46"));
                TextoInformacionAire.setVisibility(View.INVISIBLE);
                TextoAireNormal.setVisibility(View.VISIBLE);
                TextoCuidadoAire.setVisibility(View.INVISIBLE);
                TextoMuyBuenAire.setVisibility(View.INVISIBLE);
                ImagenAireNormal.setVisibility(View.VISIBLE);
                ImagenCuidadoAire.setVisibility(View.INVISIBLE);
                ImagenMuyBuenAire.setVisibility(View.INVISIBLE);
            }
        } else{
            mediaMedidas.setText("-");
        }
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
        bigText.bigText("El dispositivo se ha desconectado.");
        bigText.setBigContentTitle("Aviso");
        bigText.setSummaryText("Conexión con el dispositivo");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_sensor);
        mBuilder.setContentTitle("Aviso");
        mBuilder.setContentText("El dispositivo se ha desconectado.");
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
                    mediasenal.setVisibility(View.INVISIBLE);
                    buenaSenal.setVisibility(View.INVISIBLE);
                    malaSenal.setVisibility(View.INVISIBLE);
                    SbotonBusqueda.setVisibility(View.VISIBLE);
                    EbotonDetenerBusqueda.setVisibility(View.INVISIBLE);
                    RbotonConectando.setVisibility(View.INVISIBLE);
                    EmensajeDistancia.setVisibility(View.INVISIBLE);
                    EmensajeSiConectado.setVisibility(View.INVISIBLE);
                    SmensajeNoConectado.setVisibility(View.VISIBLE);
                    CardviewCaraOzono.setCardBackgroundColor(Color.parseColor("#2A4A4A48"));
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

    // ---------------------------------------------------------------------------------------------
    private void getDeviceLocation() {
        // Obtener la ubicación del dispositivo
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Obtener la última ubicación conocida del dispositivo
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Si no se encontró ninguna ubicación conocida, utilizar el proveedor de red
        if (lastKnownLocation == null) {
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        // Si todavía no se encontró ninguna ubicación, mostrar un mensaje de error
        if (lastKnownLocation == null) {
            Toast.makeText(this, "No se pudo obtener la ubicación del dispositivo", Toast.LENGTH_SHORT).show();
            return;
        }

// Obtener la latitud y longitud de la ubicación
        double latitude = lastKnownLocation.getLatitude();
        double longitude = lastKnownLocation.getLongitude();

// Crear una instancia de la clase Logica
        DatosClimaOWM datosClimaOWM = new DatosClimaOWM();

// Obtener la información meteorológica y mostrarla en la interfaz de usuario
        datosClimaOWM.getWeather(latitude, longitude, temperatureTextView,humiditytextview, weatherIconImageView);
    }

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private void requestLocationPermission() {
        // Verificar si ya se han concedido los permisos de ubicación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Solicitar los permisos de ubicación al usuario
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            // Si ya se han concedido los permisos, obtener la ubicación del dispositivo
            getDeviceLocation();
        }
    }

    public void IniciarBusqueda(){
        notificacionMostrada = false;
        Toast.makeText(getApplicationContext(), "La conexión va a comenzar.", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Mis_Dispositivos.this, ServicioEscuchaBeacons.class);
        cambiarvalor();
        SbotonBusqueda.setVisibility(View.INVISIBLE);
        RbotonConectando.setVisibility(View.VISIBLE);
        comprobacion();
        String nombreSensor = preferencias.getString("CodigoDispositivo","");
        i.putExtra("nombreSensor", nombreSensor);
        startService(i);
    }


}