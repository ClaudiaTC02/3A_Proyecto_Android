package ctorcru.upv.techcommit_3a.Pantallas;



import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
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

import ctorcru.upv.techcommit_3a.Logica.Logica;
import ctorcru.upv.techcommit_3a.Modelo.Dispositivo;
import ctorcru.upv.techcommit_3a.Modelo.DispositivoUsuario;
import ctorcru.upv.techcommit_3a.Modelo.Usuario;
import ctorcru.upv.techcommit_3a.R;
// -----------------------------------------------------------------------------------------
/**
 * @brief Aquí se encuentra el código que configura la funcionalidad de la app
 * Autora: Roberto Matilla Augustinus
 * Archivo: Mi_Perfil.java
 **/
// -----------------------------------------------------------------------------------------

public class Mi_Perfil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText nombrePerfil,correoPerfil;
    //Objetos
    private EditText contrasenaPerfil,oldcontrasena;
    private EditText confirmarcontrasena;
    private TextView dispositivos,sensornombre,sensorciudad;
    private ImageView fotoperfil;
    private Usuario infoUsuario = new Usuario();
    private  Usuario dtosdef= new Usuario();
    private  Usuario actualizador = new Usuario();
    private Usuario antiguo = new Usuario();
    private SharedPreferences preferencias;
    private Button btnactualizar,btnEditar,btnComprov,btnCancelar;
    private static Mi_Perfil myContext;
    private String userpref;
    private String dipositivopref,datosusuario;
    String correo;
    private DispositivoUsuario dispositivo = new DispositivoUsuario();
    private DispositivoUsuario didef = new DispositivoUsuario();
    private int cont=0;
    //estavariable va dedicada para obtener el sensor por la id del usuario
    private Logica logica= new Logica();
    private String sensorpref;
    private Dispositivo sensordefinitivo= new Dispositivo();
    private Dispositivo sensor= new Dispositivo();
    private String resultadoCiudad;
    private String resultadoNombreDispositivo;

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
        // ----------------------------------------------------------
        //Aquí creamos la barra de navegación
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // ----------------------------------------------------------


        //-----------------------------------------------------------
        //Botón que tenemos disponible para realizar alguna opción. De momento no se utiliza
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());
        //-----------------------------------------------------------

        // ----------------------------------------------------------
        //Aquí creamos el menú lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        // ----------------------------------------------------------

        // ----------------------------------------------------------
        //Aquí creamos la navegación entre las diferentes pantallas
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        cargarDatos();



        dipositivopref= preferencias.getString("allinfoDispositivo","");
        resultadoCiudad= preferencias.getString("ciudad","");
        resultadoNombreDispositivo= preferencias.getString("nombresensor","");


        // ----------------------------------------------------------
        //Enlazamos los objetos con los elementos
        datosusuario= getIntent().getStringExtra("id");
        logica.buscarDispositivoUsuario(datosusuario );
        String id= dtosdef.getId();
        String contra=dtosdef.getContrasena();

        dtosdef= infoUsuario.JsonToString(userpref);
        didef=dispositivo.JsonToString(dipositivopref);

        nombrePerfil = findViewById(R.id.perfilnombre);
        nombrePerfil.setText(dtosdef.getNombre());
        correoPerfil = findViewById(R.id.perfilcorreo);
        correoPerfil.setText(dtosdef.getCorreo());
        contrasenaPerfil = findViewById(R.id.perfilcontrasena);
        contrasenaPerfil.setText(dtosdef.getContrasena());
        btnEditar=(Button)findViewById(R.id.btnEditar);
        confirmarcontrasena=findViewById(R.id.editConfirmp);
        oldcontrasena = findViewById(R.id.editOldContra);
        btnComprov=(Button)findViewById(R.id.btnComprov);
        btnCancelar=(Button)findViewById(R.id.btnCancel);

        dispositivos=findViewById(R.id.txtdispositivos);
        didef.setIdUsuario(dtosdef.getId());
        dispositivos.setText("Id del dispositivo:"+didef.getIdSensor());

        sensornombre=findViewById(R.id.sensornametxt);
        sensorciudad=findViewById(R.id.ciudadsensortxt);


        sensornombre.setText("Nombre del sensor: "+resultadoNombreDispositivo);
        sensorciudad.setText("Ciudad del sensor: "+resultadoCiudad);

        //variable que almacenará el texto del editTex de nuestra contrasenya actual
        btnComprov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contraOld = String.valueOf(oldcontrasena.getText());

                if(contraOld.equals(contra)){
                    btnComprov.setVisibility(View.GONE);
                    oldcontrasena.setVisibility(View.GONE);
                    contrasenaPerfil.setVisibility(View.VISIBLE);
                    confirmarcontrasena.setVisibility(View.VISIBLE);
                    contrasenaPerfil.setEnabled(true);
                    confirmarcontrasena.setEnabled(true);
                }else{
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


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  CharSequence[] opciones={"Aceptar","Seguir editando"};
                final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(Mi_Perfil.this);
                alertOpciones.setTitle("Deseas cancelar la edición de tu perfil?");
                alertOpciones.setCancelable(false);
                alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(opciones[i].equals("Aceptar")){
                            Intent myIntent = new Intent(Mi_Perfil.this, Mis_Dispositivos.class);
                            Mi_Perfil.this.startActivity(myIntent);
                            Mi_Perfil.this.finish();
                        }
                        if(opciones[i].equals("Seguir editando")){
                            dialogInterface.dismiss();
                        }

                    }
                });
                alertOpciones.show();

            }
        });

        //si pulsamos el boton editar
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCancelar.setVisibility(View.VISIBLE);
                btnEditar.setVisibility(View.GONE);
                btnComprov.setVisibility(View.VISIBLE);
                oldcontrasena.setVisibility(View.VISIBLE);
                nombrePerfil.setEnabled(true);
                correoPerfil.setEnabled(true);
                correoPerfil.setEnabled(true);
                contrasenaPerfil.setEnabled(false);
                btnactualizar.setVisibility(View.VISIBLE);
                confirmarcontrasena.setVisibility(View.INVISIBLE);
                confirmarcontrasena.setText(dtosdef.getContrasena());


                 correo= correoPerfil.getText().toString();


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
        if (id == R.id.action_settings) {
            return true;
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
        // Handle navigation view item clicks here.
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

    public void actualizarUsuario(String res) {
        SharedPreferences.Editor mEditor = preferencias.edit();
        mEditor.putString("allinfoUser",res);
        mEditor.apply();
        SharedPreferences.Editor usuario = preferencias.edit();
        usuario.putString("usuarioIniciado", res);
        usuario.apply();
    }
    private void cargarDatos(){
        userpref= preferencias.getString("allinfoUser","");
        dtosdef= infoUsuario.JsonToString(userpref);
        dipositivopref =  preferencias.getString("allinfoDispositivo","");
        didef=dispositivo.JsonToString(dipositivopref);
        sensorpref= preferencias.getString("allinfoSensor","");
        sensordefinitivo= sensor.JsonToString(sensorpref);

    }
    @Override
    public void onResume() {
        super.onResume();
        cargarDatos();

    }

    public void buscarDispositivoUsuario(String res) {
        SharedPreferences.Editor mEditor = preferencias.edit();
        mEditor.putString("allinfoDispositivo",res);

        dipositivopref =  preferencias.getString("allinfoDispositivo","");
        didef=dispositivo.JsonToString(dipositivopref);
        sensordefinitivo.setIdSensor(didef.getIdSensor());
        logica.buscarSenorCiudadporId(didef.getIdSensor());
        logica.obtenerNombrePorId(didef.getIdSensor());
        mEditor.apply();
        if(didef.getIdSensor().isEmpty()){
            Intent intent = getIntent();
            finish();

            startActivity(intent);
        }




    }


    public void obtenerciudad(String res) {
        SharedPreferences.Editor mEditor = preferencias.edit();

        mEditor.putString("ciudad",res);
        mEditor.apply();
        resultadoCiudad=res;
        sensordefinitivo.setCiudad(res);

    }

    public void obtenerNombreporId(String res) {
        SharedPreferences.Editor mEditor = preferencias.edit();

        mEditor.putString("nombresensor",res);
        mEditor.apply();
        resultadoNombreDispositivo=res;
        sensordefinitivo.setNombre(res);
    }
}