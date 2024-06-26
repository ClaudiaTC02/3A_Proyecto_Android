package ctorcru.upv.techcommit_3a.Pantallas;



import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ctorcru.upv.techcommit_3a.Logica.Logica;
import ctorcru.upv.techcommit_3a.Modelo.RecyclerAdapter;
import ctorcru.upv.techcommit_3a.Modelo.Sensores;
import ctorcru.upv.techcommit_3a.Modelo.Usuario;
import ctorcru.upv.techcommit_3a.R;
import ctorcru.upv.techcommit_3a.ServicioEscuchaBeacons;
// -----------------------------------------------------------------------------------------
/**
 * @brief Aquí se encuentra el código de la pantalla de Mi Perfil
 * Autora: Roberto Matilla Augustinus
 * Archivo: Mi_Perfil.java
 **/
// -----------------------------------------------------------------------------------------

public class Mi_Perfil extends AppCompatActivity  {

    // -----------------------------------------------------------------------------------------
    // DECLARACIONES DE VARIABLES
    // -------------------------------------------------------------------
    //Declaración de variables de la pantalla
    boolean notificacionMostrada = false;
    private EditText nombrePerfil,correoPerfil;
    private EditText contrasenaPerfil,oldcontrasena;
    private EditText confirmarcontrasena;
    private TextView  tusdispositivos;
    private SharedPreferences preferencias;
    private Button btnactualizar,btnComprov,btnCancelar,btnCerrarSesion;
    private ImageView btnEditar;
    private  int enable=0;
    private  int comprov=0;
    String correo;
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //Declaración de variables para el recycler view
    private RecyclerView mRecyclerView;
    private List<Object> viewItems= new ArrayList<>();
    private  RecyclerView.Adapter mAdapter;
    private  RecyclerView.LayoutManager layoutManager;
    private String jsonDataString;
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //Declaración de variables para las alertas
    private AlertDialog.Builder cerrarSesioon;
    private AlertDialog.Builder editar;
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    //Declaración de variables para interaccionar con las clases
    private Usuario infoUsuario = new Usuario();
    private  Usuario dtosdef= new Usuario();
    private static Mi_Perfil myContext;
    private String userpref;
    FloatingActionButton fab;
    BottomNavigationView bottomNavigationView;
    // -------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------
    // -------------------------------------------------------------------
    //Constructor de la clase
    /**
     * @brief Constructor de la clase
     * @return objeto MainActivity
     * Diseño: --> MainActivity() --> MainActivity
     **/
    // -----------------------------
    public Mi_Perfil() {
        myContext =  this;
    }
    // -------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------
    // -------------------------------------------------------------------
    //Método para obtener el contexto de la clase
    /**
     * @brief Método que devuelve el contexto de la actividad
     * @return myContext
     * Diseño: --> MainActivity() --> MainActivity
     **/
    // -----------------------------
    public static Mi_Perfil getInstance() {
        return myContext;
    }
    // -------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------



    // -------------------------------------------------------------------
    //MÉTODO ONCREATE
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        //-----------------------------------------------
        //Preferencias
        preferencias = getSharedPreferences("label", 0);
        //-----------------------------------------------
        ServicioEscuchaBeacons servicioEscuchaBeacons = new ServicioEscuchaBeacons();

        //-----------------------------------------------
        //Llamada al método onCreate de la clase padre
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        enable=0;
        comprov=0;
        //-----------------------------------------------

        //-----------------------------------------------
        //Declaración de variables para el BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);
        bottomNavigationView.setSelectedItemId(R.id.Mi_Perfil);
        fab = findViewById(R.id.fab);
        //-----------------------------------------------

        //-----------------------------------------------
        // ----------------------------------------------------------
        //Aquí creamos la barra de navegación
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().hide();
        //-----------------------------------------------

        //-----------------------------------------------
        //Acciones del BottomNavigationView
        bottomNavigationView.getMenu().getItem(0).setOnMenuItemClickListener(item -> {
            Intent intent2 = new Intent(Mi_Perfil.this, Mis_Dispositivos.class);
            startActivity(intent2);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        });
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(Mi_Perfil.this, Vista_Mapa.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
        //-----------------------------------------------

        //-----------------------------------------------
        //Llamada al método para cargar los datos del usuario
        try {
            cargarDatos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //-----------------------------------------------

        //-----------------------------------------------
        //Enlazamos los objetos con los elementos
        String id= dtosdef.getId();
        String contra=dtosdef.getContrasena();
        //-----------------------------------------------

        //-----------------------------------------------
        //Asociamos al objeto de tipo usuario la asignación del nuevo usuario
        try {
            dtosdef= infoUsuario.JsonToString(userpref);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //-----------------------------------------------

        //-----------------------------------------------
        //Asignamos sus respectivas variables los editext y lo que contendrán
        nombrePerfil = findViewById(R.id.perfilnombre);
        nombrePerfil.setText(dtosdef.getNombre());
        correoPerfil = findViewById(R.id.perfilcorreo);
        correoPerfil.setText(dtosdef.getCorreo());
        contrasenaPerfil = findViewById(R.id.perfilcontrasena);
        contrasenaPerfil.setText(dtosdef.getContrasena());
        btnEditar= findViewById(R.id.btnEditar);
        tusdispositivos = (TextView) findViewById(R.id.tusdispositivos);
        confirmarcontrasena=findViewById(R.id.editConfirmp);
        oldcontrasena = findViewById(R.id.editOldContra);
        btnComprov=(Button)findViewById(R.id.btnComprov);
        btnCancelar=(Button)findViewById(R.id.btnCancel);
        //-----------------------------------------------

        //-----------------------------------------------
        //Asociamos el texview y le agregamos el span bajo él
        TextView txtTitulo = (TextView)findViewById(R.id.textView13);
        TextView textInfoDIspositivo = (TextView)findViewById(R.id.tusdispositivos);
        SpannableString content = new SpannableString("Datos Personales");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txtTitulo.setText(content);
        textInfoDIspositivo.setPaintFlags(textInfoDIspositivo.getPaintFlags() |   android.graphics.Paint.UNDERLINE_TEXT_FLAG);
        //-----------------------------------------------

        //-----------------------------------------------
        //Para el recyclerview
        mRecyclerView= (RecyclerView) findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerAdapter(this, viewItems);
        mRecyclerView.setAdapter(mAdapter);
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

        //-----------------------------------------------
        //Si pulsamos el botón comprobar contraseña
        btnComprov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contraOld = String.valueOf(oldcontrasena.getText());
                //si coincide con la contrasenya ctual que tiene el usuario
                if(contraOld.equals(contra)){
                    //desbloqueamos todas las opciones para poder editar la contrasenya
                    btnComprov.setVisibility(View.GONE);
                    oldcontrasena.setVisibility(View.GONE);
                    contrasenaPerfil.setVisibility(View.VISIBLE);
                    confirmarcontrasena.setVisibility(View.VISIBLE);
                    contrasenaPerfil.setEnabled(true);
                    confirmarcontrasena.setEnabled(true);
                    enable++;
                }else{
                    //si no coinciden hacemos altar una alerta
                    oldcontrasena.setText("");
                    final  CharSequence[] opciones={"Aceptar"};
                    final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(Mi_Perfil.this);
                    alertOpciones.setTitle("La contraseña que has introducido no es correcta");
                    alertOpciones.setCancelable(false);
                    alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(opciones[i].equals("Aceptar")){
                                dialogInterface.dismiss();
                            }
                        }
                    });
                    alertOpciones.show();
                }
            }
        });
        //-----------------------------------------------

        //-----------------------------------------------
        //Si pulsamos el boton cancelar la edición del perfil
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lanzamos una alerta
                final  CharSequence[] opciones={"Aceptar","Seguir editando"};
                final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(Mi_Perfil.this);
                alertOpciones.setTitle("Deseas cancelar la edición de tu perfil?");
                alertOpciones.setCancelable(false);
                alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //si se pulsa aceptar
                        if(opciones[i].equals("Aceptar")){
                            Intent myIntent = new Intent(Mi_Perfil.this, Mi_Perfil.class);
                            Mi_Perfil.this.startActivity(myIntent);
                            Mi_Perfil.this.finish();
                        }
                        //si se pulsa seguir editando
                        if(opciones[i].equals("Seguir editando")){
                            dialogInterface.dismiss();
                        }

                    }
                });
                alertOpciones.show();

            }
        });
        //-----------------------------------------------

        //-----------------------------------------------
        //Si pulsamos el boton editar
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lanzamos una alerta
                editar = new AlertDialog.Builder(Mi_Perfil.this);
                editar.setTitle("Editar información de usuario");
                editar.setMessage("¿Desea editar su información de usuario?");
                //si se pulsa si
                editar.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Desplegamos las opciones para poder editar los parametros poniendolos interactuables
                        btnCancelar.setVisibility(View.VISIBLE);
                        btnEditar.setVisibility(View.GONE);
                        btnComprov.setVisibility(View.VISIBLE);
                        oldcontrasena.setVisibility(View.VISIBLE);
                        nombrePerfil.setEnabled(true);
                        correoPerfil.setEnabled(true);
                        correoPerfil.setEnabled(true);
                        contrasenaPerfil.setEnabled(false);
                        tusdispositivos.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.GONE);
                        btnactualizar.setVisibility(View.VISIBLE);
                        confirmarcontrasena.setVisibility(View.INVISIBLE);
                        confirmarcontrasena.setText(dtosdef.getContrasena());
                        correo= correoPerfil.getText().toString();
                    }
                });
                //si se pulsa no
                editar.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                editar.show();
            }
        });
        //-----------------------------------------------

        //-----------------------------------------------
        //Pulsamos el boton actualizar
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


                String txtCorreo  =correoPerfil.getText().toString();
                if(txtCorreo.contains(".com")||txtCorreo.contains(".es")||txtCorreo.contains(".us") || txtCorreo.contains(".uk")){
                    comprov++;
                }

                //si las dos contrasenyas coinciden
                if(nombrePerfil.getText().toString().matches("") || correoPerfil.getText().toString().matches("")
                        || txtcontrasena.matches("") ||!correoPerfil.getText().toString().contains("@") || !txtcontrasena.matches(txtconfirmcontra) || comprov==0){
                    //hasta que las contrasenyas no coincidan saltará una alerta
                    btnEditar.setVisibility(View.GONE);
                    nombrePerfil.setEnabled(true);
                    correoPerfil.setEnabled(true);
                    correoPerfil.setEnabled(true);
                    contrasenaPerfil.setEnabled(true);
                    btnactualizar.setVisibility(View.VISIBLE);
                    confirmarcontrasena.setVisibility(View.VISIBLE);
                    if(enable==0){
                        confirmarcontrasena.setVisibility(View.INVISIBLE);
                        contrasenaPerfil.setEnabled(false);
                    }
                    String error="";
                    String to="";
                    // ahora sus respectivas alertas
                    if(nombrePerfil.getText().toString().matches("")){
                        error="";
                        error=error+"el campo del nombre esta vacío";
                        to="";
                        to=to+"Porfavor rellene el campo Nombre de usario";
                        crearAlerta(error,to);
                    }else if(correoPerfil.getText().toString().matches("")){
                        error="";
                        error=error+"Correo electrónico esta vacío";
                        to="";
                        to=to+"Porfavor rellene el campo Correo";
                        crearAlerta(error,to);
                    }
                    else if(txtcontrasena.matches("")){
                        error="";
                        error=error+"el campo de la Contraseña nueva esta vacío";
                        to="";
                        to=to+"Porfavor rellene el campo de la contraseña";
                        crearAlerta(error,to);
                    }
                    else if(!correoPerfil.getText().toString().contains("@") ){
                        error="";
                        error=error+"el correo introducido no es valido, debe contener @";
                        to="";
                        to=to+"Porfavor rellene el campo correctamente";
                        crearAlerta(error,to);
                    }
                    else if(comprov==0 ){
                        error="";
                        error=error+"el correo introducido no es valido, debe contener un dominio correcto";
                        to="";
                        to=to+"Porfavor rellene el campo correctamente";
                        crearAlerta(error,to);
                    }
                    else if(!txtcontrasena.matches(txtconfirmcontra) ){
                        error="";
                        error=error+"las contraseñas no coinciden";
                        to="";
                        to=to+"Porfavor revise las contraseñas";
                        crearAlerta(error,to);
                    }


                }else if(txtcontrasena.equals(txtconfirmcontra)){

                    //texto donde almacenaremos el resultado de la contraenya
                    String confirmacion="";
                    confirmacion = confirmacion +  txtconfirmcontra;
                    //llenamos el usuario con los cambios de atributos
                    Usuario actualizado= new Usuario(id,nombrePerfil.getText().toString(),correoPerfil.getText().toString().toLowerCase(Locale.ROOT),confirmacion);
                    //actualizado.setFoto(dtosdef.getFoto());
                    actualizado.setEsAdmin(dtosdef.getEsAdmin());

                    //le enviamos el usuario pendiente de actualizar a la funcion de la logica res
                    new Logica().actualizarUsuario(actualizado,correo);
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
                                Intent myIntent = new Intent(Mi_Perfil.this, Mi_Perfil.class);

                                Mi_Perfil.this.startActivity(myIntent);

                                Mi_Perfil.this.finish();

                                dialogInterface.dismiss();
                            }
                        }
                    });
                    alertOpciones.show();
                }
            }
        });
        //-----------------------------------------------
        addItemsFromJSON();
    }
    //-----------------------------------------------

    //-----------------------------------------------
    /**
     * @brief La funcion onBackPressed() se encarga de no poder volver atras en la aplicacion
     */
    //-----------------------------------------------
    @Override
    public void onBackPressed() {
        finish();
    }
    //-----------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mis__dispositivos, menu);
        return true;
    }

    //-----------------------------------------------
    /**
     * @brief Esta función se encarga de poder seleccionar las opciones del menú lateral y saber la seleccionada
     * @param item
     * @return true
     **/
    //-----------------------------------------------
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
    //-----------------------------------------------

    //-----------------------------------------------
    /**
     * @brief Esta función se encarga de recibir la info del usuario actualizado
     * @param res
     * @return 0
     **/
    //-----------------------------------------------
    //Recibimos la respuesta del servidor
    public void actualizarUsuario(String res) {
        //De la función logica obtenemos el json y actualizamos las preferencias para que se actualicen los paramtros en la aplicacion
        SharedPreferences.Editor mEditor = preferencias.edit();
        mEditor.putString("allinfoUser",res);
        mEditor.apply();
        SharedPreferences.Editor usuario = preferencias.edit();
        usuario.putString("usuarioIniciado", res);
        usuario.apply();
    }
    //-----------------------------------------------


    //-----------------------------------------------
    /**
     * @brief Esta función se encarga de cargar los datos en la activity
     * @return 0
     **/
    //-----------------------------------------------
    private void cargarDatos() throws JSONException{
        userpref= preferencias.getString("allinfoUser","");
        dtosdef= infoUsuario.JsonToString(userpref);

    }
    //-----------------------------------------------

    //-----------------------------------------------
    /**
     * @brief Esta función se encarga de llamar a cargar datos en caso de que el usuario regrese de habrla dejado en segundo plano
     * @return 0
     **/
    //-----------------------------------------------
    @Override
    public void onResume() {
        super.onResume();
        try {
            cargarDatos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //-----------------------------------------------

    //-----------------------------------------------
    /**
     * @brief Esta función se encarga de cerrar sesion
     * @param view
     * @return 0
     **/
    //-----------------------------------------------
    public void cerrarSesion(View view){
        Log.d("cerrarSesion", "llego aqui");
        SharedPreferences.Editor mEditor = preferencias.edit();
        mEditor.putString("usuarioIniciado", "ninguno");
        mEditor.putString("allinfoUser","ninguno");
        mEditor.putString("allinfosensores","ninguno");
        mEditor.putString("infoUsuario","ninguno");
        mEditor.apply();
        Mis_Dispositivos.fa.finish();
        Intent i = new Intent(this, Pre_Login_Registro.class);
        startActivity(i);
    }
    //-----------------------------------------------

    //-----------------------------------------------
    /**
     * @brief Esta función se encarga de cargar los datos al recyclerview
     * @return 0
     **/
    //-----------------------------------------------
    private void addItemsFromJSON() {
        //Llamamos a la variable de sharedpreferences que llenamos al iniciar sesion con los nombres de los json de los nombres de los dispositivos
        jsonDataString=preferencias.getString("allinfosensores","");
        Log.d("CodigoDispositivo", jsonDataString);
        //Procedemos a quitar los [] sobrantes obtenidas en la peticion
        StringBuilder sb = new StringBuilder(jsonDataString);
        sb.deleteCharAt(jsonDataString.length() - 1);
        sb.deleteCharAt(0);
        //Almacenamos el resultado de la eliminacion en una variable
        String res1= sb.toString();
        String res2= res1.replace("],[",",");//quitamos lo restante de la cadena para poder convertirse enun jsonArray

        Log.d("addItemsFormJson", "jsona "+ res2);
        try {
            //Convertimos elstring definitivamente limpio en un jsonArray
            JSONArray jsonArray= new JSONArray(res2);
            //Recorremos el jsonArray buscando los nombres en cada casilla
            for(int i=0;i<=jsonArray.length();i++){
                JSONObject itemObj = jsonArray.getJSONObject(i);
                String name = itemObj.getString("Nombre");
                Log.d("addItemsFormJson", "json "+ name);
                //los anyadimos al viewItem
                Sensores sensores= new Sensores(name);
                viewItems.add(sensores);
            }
        } catch ( JSONException e) {

        }
    }
    //-----------------------------------------------

    //-----------------------------------------------
    /**
     * @brief Esta función se encarga de crear una alerta
     * @return 0
     **/
    private void crearAlerta(String error, String toaest){
        final  CharSequence[] opciones={"Aceptar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(Mi_Perfil.this);
        alertOpciones.setTitle("Error al actualizar usuario, "+ error);
        String finalTo =toaest;
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(opciones[i].equals("Aceptar")){
                    Toast.makeText(getApplicationContext(), finalTo,Toast.LENGTH_SHORT).show();
                    if(enable!=0){
                        contrasenaPerfil.setText("");
                        confirmarcontrasena.setText("");
                    }
                    if(comprov==0){
                        correoPerfil.setText("");
                    }

                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }
    //-----------------------------------------------


}