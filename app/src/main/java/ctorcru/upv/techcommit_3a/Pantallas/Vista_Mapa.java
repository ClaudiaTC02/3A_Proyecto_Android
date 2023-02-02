package ctorcru.upv.techcommit_3a.Pantallas;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.webkit.WebView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import ctorcru.upv.techcommit_3a.R;
import ctorcru.upv.techcommit_3a.ServicioEscuchaBeacons;

// -----------------------------------------------------------------------------------------
/**
 * @brief Aquí se encuentra el código que configura la funcionalidad de la app
 * Autora: Roberto Matilla Augustinus
 * Archivo: Vista_Mapa.java
 **/
// -----------------------------------------------------------------------------------------

public class Vista_Mapa extends AppCompatActivity  {
    // -----------------------------------------------------------------------------------------
    // DECLARACIONES DE VARIABLES
    // -------------------------------------------------------------------
    private SharedPreferences preferencias;
    private AlertDialog.Builder cerrarSesioon;
    FloatingActionButton fab;
    BottomNavigationView bottomNavigationView;
    Button btnInfo;
    ImageButton infobocadillo;

    // -------------------------------------------------------------------
    //MÉTODO ONCREATE
    @Override
    protected void onCreate(Bundle savedInstanceState){
        preferencias = getSharedPreferences("label", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_mapa);
        ServicioEscuchaBeacons servicioEscuchaBeacons = new ServicioEscuchaBeacons();
        //-----------------------------------------------
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("http://192.168.137.1/GTI_3A_Backend/src/ux/mapas.html");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());



        //Declaración de variables para el BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setSelectedItemId(R.id.vacio);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);
        fab = findViewById(R.id.fab);
        btnInfo = findViewById(R.id.btnInfo);
        infobocadillo = findViewById(R.id.infobocadillo);
        //-----------------------------------------------

        //-----------------------------------------------
        //Acciones del BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Inicio:
                    Intent intent = new Intent(Vista_Mapa.this, Mis_Dispositivos.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
                case R.id.Mi_Perfil:
                    Intent intent2 = new Intent(Vista_Mapa.this, Mi_Perfil.class);
                    startActivity(intent2);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
            }
            return true;
        });
        //-----------------------------------------------

        //-----------------------------------------------
        // ----------------------------------------------------------
        //Aquí creamos la barra de navegación
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().hide();
        //-----------------------------------------------

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

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("https://download1519.mediafire.com/j0zcoejbnngg/1bknn8djkpx8a7u/documento.pdf");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle("Información de los contaminantes");
                request.setDescription("Descargando archivo PDF...");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
                Toast.makeText(Vista_Mapa.this, "Ha comenzado la descarga del archivo de información.", Toast.LENGTH_SHORT).show();
            }
        });

        infobocadillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("https://download1519.mediafire.com/j0zcoejbnngg/1bknn8djkpx8a7u/documento.pdf");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle("Información de los contaminantes");
                request.setDescription("Descargando archivo PDF...");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
                Toast.makeText(Vista_Mapa.this, "Se va a descargar el archivo de información sobre los contaminantes.", Toast.LENGTH_SHORT).show();
            }
        });


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


    // ---------------------------------------------------------------------------------------------

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
        int id = item.getItemId();
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
    public void cerrarSesion(View view){
        Log.d("cerrarSesion", "llego aqui");
        SharedPreferences.Editor mEditor = preferencias.edit();
        mEditor.putString("usuarioIniciado", "ninguno");
        mEditor.putString("allinfoUser","ninguno");
        mEditor.apply();
        Intent i = new Intent(this, Pre_Login_Registro.class);
        startActivity(i);
    }
    // ---------------------------------------------------------------------------------------------

}
