package ctorcru.upv.techcommit_3a.Pantallas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import ctorcru.upv.techcommit_3a.R;

// -----------------------------------------------------------------------------------------
/**
 * @brief Aquí se encuentra el código que configura la funcionalidad de la app
 * Autora: Roberto Matilla Augustinus
 * Archivo: Vista_Mapa.java
 **/
// -----------------------------------------------------------------------------------------

public class Vista_Mapa extends AppCompatActivity  {

    private SharedPreferences preferencias;
    private AlertDialog.Builder cerrarSesioon;
    FloatingActionButton fab;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        preferencias = getSharedPreferences("label", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_mapa);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setSelectedItemId(R.id.vacio);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);
        fab = findViewById(R.id.fab);


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

        // ----------------------------------------------------------
        //Aquí creamos la barra de navegación
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // ----------------------------------------------------------
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief La funcion onBackPressed() se encarga de cerrar el menú lateral si está abierto dando atrás
     **/
    // ---------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        //No hace nada
    }
    // ---------------------------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------------------

//    // ---------------------------------------------------------------------------------------------
//
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
}
