package ctorcru.upv.techcommit_3a.Pantallas;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

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
        cargarDatos();
        botonBusqueda = findViewById(R.id.botonBusqueda);
        botonDetenerBusqueda = findViewById(R.id.botonDetenerBusqueda);
        ImagenMisDispositivos = findViewById(R.id.ImagenMisDispositivos);
        botonCerrarSesion = findViewById(R.id.cerrar_sesion);
        nombreUsuario = findViewById(R.id.txtNombreh);
        datosUsuario= getIntent().getStringExtra("infoUsuario");
        String userpref= preferencias.getString("allinfoUser","");
        dtosdef= infoUsuario.JsonToString(userpref);
        infoUsuario.JsonToString(userpref);
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
    private void cargarDatos(){
        String userpref= preferencias.getString("allinfoUser","");
        Usuario dtosdef;
        dtosdef= infoUsuario.JsonToString(userpref);


    }

    @Override
    public void onResume() {
        super.onResume();
        cargarDatos();

    }
    @Override
    public void onRestart() {
        super.onRestart();
        cargarDatos();

    }
    @Override
    public void onStart() {
        super.onStart();

        cargarDatos();

    }
    public void cerrarSesion(View view){
        Log.d("cerrarSesion", "llego aqui");
        SharedPreferences.Editor mEditor = preferencias.edit();
        mEditor.putString("usuarioIniciado", "ninguno");
        mEditor.putString("allinfoUser","ninguno");
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
}