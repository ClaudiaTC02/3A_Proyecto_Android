package ctorcru.upv.techcommit_3a.Pantallas;

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
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
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



public class Mis_Dispositivos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //variables y elementos para la vinculación del usuario
    private String datosUsuario;
    private Usuario infoUsuario = new Usuario();
    private  Usuario dtosdef= new Usuario();
    private SharedPreferences preferencias;
    private TextView nombreUsuario;
    private Button botonBusqueda;
    private Button botonCerrarSesion;
    private static Mis_Dispositivos myContext;
    private Button botonDetenerBusqueda;
    private ImageView ImagenMisDispositivos;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    public static Activity fa;
    private AlertDialog.Builder cerrarSesioon;
    public ImageView sinsenal;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        preferencias = getSharedPreferences("label", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_dispositivos);
        // ----------------------------------------------------------
        //Aquí creamos la barra de navegación
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // ----------------------------------------------------------



        // ----------------------------------------------------------
        //Enlazamos los objetos con los elementos
        try {
            cargarDatos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        botonBusqueda = findViewById(R.id.botonBusqueda);
        botonDetenerBusqueda = findViewById(R.id.botonDetenerBusqueda);
        ImagenMisDispositivos = findViewById(R.id.ImagenMisDispositivos);
        botonCerrarSesion = findViewById(R.id.cerrar_sesion);
        nombreUsuario = findViewById(R.id.txtNombreh);
        sinsenal = findViewById(R.id.sinconexion);
        datosUsuario= getIntent().getStringExtra("infoUsuario");
        String userpref= preferencias.getString("allinfoUser","");

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

        nombreUsuario.setText("Hola "+ dtosdef.getNombre()+", bienvenido a TechCommit");
        fa = this;

        // ----------------------------------------------------------

        //-----------------------------------------------------------
        //Botón que tenemos disponible para realizar alguna opción. De momento no se utiliza
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());
        //-----------------------------------------------------------

        // ----------------------------------------------------------
        //Aquí creamos el menú lateral
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // ----------------------------------------------------------
        //Aquí creamos la navegación entre las diferentes pantallas
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // ----------------------------------------------------------



        // ----------------------------------------------------------
        //Indicamos los listeners de los botones
        botonBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "La búsqueda comenzará", Toast.LENGTH_SHORT).show();
                    startService(new Intent(Mis_Dispositivos.this, ServicioEscuchaBeacons.class));
            }
        });

        botonDetenerBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "La búsqueda se detendrá", Toast.LENGTH_SHORT).show();
                stopService(new Intent(Mis_Dispositivos.this, ServicioEscuchaBeacons.class));
            }
        });

        ImagenMisDispositivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Imagen del dispositivo", Toast.LENGTH_SHORT).show();

            }
        });
        // ----------------------------------------------------------

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


    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief La funcion onBackPressed() se encarga de cerrar el menú lateral si está abierto dando atrás
     **/
    // ---------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mis__dispositivos, menu);
        return true;
    }
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

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
    /**
     * @brief Esta función se encarga de saber que opción hemos elegido para dirigirnos a la pantalla correspondiente
     * @param item
     * @return true
     **/
    // ---------------------------------------------------------------------------------------------

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //Aquí se añaden tantas opciones como actividades tengamos
        switch (id){
            case R.id.nav_Mis_Dispositivos:
                Intent intent = new Intent(this, Mis_Dispositivos.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.nav_Mi_Perfil:
                Intent intent2 = new Intent(this, Mi_Perfil.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent2.putExtra("id",dtosdef.getId());
                startActivity(intent2);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.nav_Mapa:
                Intent intent3 = new Intent(this, Vista_Mapa.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent3);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    // ---------------------------------------------------------------------------------------------
    private void cargarDatos()throws JSONException{
        String userpref= preferencias.getString("allinfoUser","");
        Usuario dtosdef;
        dtosdef= infoUsuario.JsonToString(userpref);


    }


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
    public void lanzarNotificacion(){
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
     * @brief Esta función se encarga de comprobar si el bluetooth está activado.
     * Diseño de la función: https://developer.android.com/guide/topics/connectivity/bluetooth.html
     **/
    public void comprobarEstadoBluetooth(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // El dispositivo no tiene Bluetooth
                lanzarNotificacion();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                // Bluetooth está deshabilitado, se puede pedir al usuario que lo habilite
                lanzarNotificacion();
            } else {
                // Bluetooth está habilitado
            }
        }
    }
}