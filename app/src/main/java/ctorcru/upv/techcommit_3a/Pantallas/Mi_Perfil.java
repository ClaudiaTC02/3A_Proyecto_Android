package ctorcru.upv.techcommit_3a.Pantallas;



import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ctorcru.upv.techcommit_3a.Logica.Logica;
import ctorcru.upv.techcommit_3a.Modelo.Usuario;
import ctorcru.upv.techcommit_3a.R;

public class Mi_Perfil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText nombrePerfil,correoPerfil;
    private EditText contrasenaPerfil;
    private EditText confirmarcontrasena;
    private ImageView fotoperfil;
    private Usuario infoUsuario = new Usuario();
    private  Usuario dtosdef= new Usuario();
    private  Usuario actualizador = new Usuario();
    private SharedPreferences preferencias;
    private Button btnactualizar,btnEditar;
    private static Mi_Perfil myContext;

    //para tomar las fotos

    //para editar nombre correo y contraseña

    /**
     * @brief Constructor de la clase
     * @return objeto MainActivity
     * Diseño: --> MainActivity() --> MainActivity
     **/
    // ---------------------------------------------------------------------------------------------
    public Mi_Perfil() {
        myContext =  this;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Método que devuelve el contexto de la actividad
     * @return myContext
     * Diseño: --> MainActivity() --> MainActivity
     **/
    // ---------------------------------------------------------------------------------------------
    public static Mi_Perfil getInstance() {
        return myContext;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        preferencias = getSharedPreferences("label", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        cargarDatos();
        String userpref= preferencias.getString("allinfoUser","");

        dtosdef= infoUsuario.JsonToString(userpref);
        String id= dtosdef.getId();

        nombrePerfil = findViewById(R.id.perfilnombre);
        nombrePerfil.setText(dtosdef.getNombre());
        correoPerfil = findViewById(R.id.perfilcorreo);
        correoPerfil.setText(dtosdef.getCorreo());
        contrasenaPerfil = findViewById(R.id.perfilcontrasena);
        contrasenaPerfil.setText(dtosdef.getContrasena());
        btnEditar=(Button)findViewById(R.id.btnEditar);
        confirmarcontrasena=findViewById(R.id.editConfirmp);
        //si pulsamos el boton editar
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEditar.setVisibility(View.GONE);
                nombrePerfil.setEnabled(true);
                correoPerfil.setEnabled(true);
                correoPerfil.setEnabled(true);
                contrasenaPerfil.setEnabled(true);
                btnactualizar.setVisibility(View.VISIBLE);
                confirmarcontrasena.setVisibility(View.VISIBLE);
                confirmarcontrasena.setText(dtosdef.getContrasena());

            }
        });

        //pulsamos el boton actualizar
        btnactualizar = (Button) findViewById(R.id.btnActualizarP);


        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //quitamos el boton actualizar y desactivamos todos los campos para que solo se puedan visualizar
                btnEditar.setVisibility(View.VISIBLE);
                nombrePerfil.setEnabled(false);
                correoPerfil.setEnabled(false);
                correoPerfil.setEnabled(false);
                contrasenaPerfil.setEnabled(false);

                confirmarcontrasena.setVisibility(View.INVISIBLE);
                //nos almacenamos el contenido de la contrasenya y la contrasenya repetida
                String txtcontrasena  = contrasenaPerfil.getText().toString();
                String txtconfirmcontra = confirmarcontrasena.getText().toString();


                //si las dos contrasenyas coinciden
                if(txtcontrasena.equals(txtconfirmcontra)){
                    //texto donde almacenaremos el resultado de la contraenya
                    String confirmacion="";
                    confirmacion = confirmacion +  txtconfirmcontra;
                    //llenamos el usuario con los cambios de atributos
                    Usuario actualizado= new Usuario(id,nombrePerfil.getText().toString(),correoPerfil.getText().toString(),confirmacion);
                    //actualizado.setFoto(dtosdef.getFoto());
                    actualizado.setEsAdmin(dtosdef.getEsAdmin());

                    //le enviamos el usuario pendiente de actualizar a la funcion de la logica res
                    new Logica().actualizarUsuario(actualizado);
                    btnactualizar.setVisibility(View.INVISIBLE);

                    final  CharSequence[] opciones={"Aceptar"};
                    final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(Mi_Perfil.this);
                    alertOpciones.setTitle("Usuario actualizado correctamente");
                    alertOpciones.setCancelable(false);
                    alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(opciones[i].equals("Aceptar")){
                                Mis_Dispositivos.fa.finish();
                                Intent myIntent = new Intent(Mi_Perfil.this, Mis_Dispositivos.class);
                                Mi_Perfil.this.startActivity(myIntent);

                                Mi_Perfil.this.finish();

                                dialogInterface.dismiss();
                            }
                        }
                    });
                    alertOpciones.show();


                }else{
                    //hasta que las contrasenyas no coincidan saltará una alerta
                    btnEditar.setVisibility(View.GONE);
                    nombrePerfil.setEnabled(true);
                    correoPerfil.setEnabled(true);
                    correoPerfil.setEnabled(true);
                    contrasenaPerfil.setEnabled(true);
                    btnactualizar.setVisibility(View.VISIBLE);
                    confirmarcontrasena.setVisibility(View.VISIBLE);
                    final  CharSequence[] opciones={"Aceptar"};
                    final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(Mi_Perfil.this);
                    alertOpciones.setTitle("Error al actualizar usuario, las contraseñas deben coincidir");
                    alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(opciones[i].equals("Aceptar")){
                                Toast.makeText(getApplicationContext(),"Compruebe las contraseñas",Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        }
                    });
                    alertOpciones.show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mis__dispositivos, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_Mis_Dispositivos:
                Intent intent = new Intent(this, Mis_Dispositivos.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.nav_Mi_Perfil:
                Intent intent2 = new Intent(this, Mi_Perfil.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent2);
                overridePendingTransition(0,0);
                break;
            case R.id.nav_Mapa:
                Intent intent3 = new Intent(this, Vista_Mapa.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent3);
                overridePendingTransition(0,0);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    private boolean validarPermisos(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
          return true;
        }
        if((checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)&&(checkSelfPermission(READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)) {
            return true;
        }
        if((shouldShowRequestPermissionRationale(CAMERA))||(shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE))) {
            AlertDialog.Builder dialogo=new AlertDialog.Builder(Mi_Perfil.this);
            dialogo.setTitle("Permisos desactivados");
            dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");
            dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    requestPermissions(new String[]{READ_EXTERNAL_STORAGE,CAMERA},100);
                }
            });
            dialogo.show();
        }else{
            requestPermissions(new String[]{READ_EXTERNAL_STORAGE,CAMERA},100);
        }
        return false;
    }

    public void actualizarUsuario(String res) {
        SharedPreferences.Editor mEditor = preferencias.edit();
        mEditor.putString("allinfoUser",res);
        mEditor.apply();
        SharedPreferences.Editor usuario = preferencias.edit();
        usuario.putString("usuarioIniciado", res);
        usuario.apply();
    }
    private void cargarDatos(){
        String userpref= preferencias.getString("allinfoUser","");
        dtosdef= infoUsuario.JsonToString(userpref);



    }
    @Override
    public void onResume() {
        super.onResume();
        cargarDatos();

    }
}