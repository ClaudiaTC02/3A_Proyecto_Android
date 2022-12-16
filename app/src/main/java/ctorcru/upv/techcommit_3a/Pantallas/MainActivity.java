package ctorcru.upv.techcommit_3a.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ctorcru.upv.techcommit_3a.Logica.Logica;

import ctorcru.upv.techcommit_3a.Modelo.Sensores;
import ctorcru.upv.techcommit_3a.Modelo.Usuario;
import ctorcru.upv.techcommit_3a.R;
// -----------------------------------------------------------------------------------------
/**
 * @brief Aquí se encuentra el código que configura la funcionalidad de la app
 * Autora: Claudia Torres Cruz
 * Archivo: MainActivity.java
 **/
// -----------------------------------------------------------------------------------------
public class MainActivity extends AppCompatActivity {
    //Atributos
    private static final String ETIQUETA_LOG = "TechCommit_Log";
    //Objetos
    private EditText correo;
    private TextView registrate,olvidastecontrasena;
    private static MainActivity myContext;
    private Button botonIniciarSesion;
    private EditText contraseya;
    private ImageView flecha_atras,ImagenLogo;
    private SharedPreferences preferencias;
    private Logica logica= new Logica();
    private String jsonDataString;
    // ---------------------------------------------------------------------------------------------
    // Métodos para coger el contexto de esta actividad
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Constructor de la clase
     * @return objeto MainActivity
     * Diseño: --> MainActivity() --> MainActivity
     **/
    // ---------------------------------------------------------------------------------------------
    public MainActivity() {
        myContext =  this;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Método que devuelve el contexto de la actividad
     * @return myContext
     * Diseño: --> MainActivity() --> MainActivity
     **/
    // ---------------------------------------------------------------------------------------------
    public static MainActivity getInstance() {
        return myContext;
    }
    // -----------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencias = getSharedPreferences("label", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ----------------------------------------------------------
        //Enlazamos los objetos con los elementos
        correo = findViewById(R.id.correo);
        olvidastecontrasena = findViewById(R.id.olvidastecontrasena);
        registrate = findViewById(R.id.irAIniciarSesion);
        contraseya = findViewById(R.id.contrasenya);
        botonIniciarSesion = findViewById(R.id.botonIniciarSesion);
        flecha_atras = findViewById(R.id.flecha_atrasRegistro);
        ImagenLogo = findViewById(R.id.imagenLogoReg);
        // ----------------------------------------------------------

        // ----------------------------------------------------------
        //Añadimos los listeners a los botones
        flecha_atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                irPrincipio(null);
            }
        });

        ImagenLogo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                irPrincipio(null);
            }
        });

        registrate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                irRegistro(null);
            }
        });

        olvidastecontrasena.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                irRecuperarContrasena(null);
            }
        });


        // ----------------------------------------------------------
        String usuarioIniciado = preferencias.getString("usuarioIniciado", "ninguno");
        if(!usuarioIniciado.equals("ninguno")){
            Intent myIntent = new Intent(MainActivity.this, Mis_Dispositivos.class);
            MainActivity.this.startActivity(myIntent);
            MainActivity.this.finish();
        }
    }


    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Esta función se ejecuta cuando el cuerpo es recibido de la Logica y cambia de actividad
     * también agrega el parametro para guardar la sesion
     * @param cuerpo
     * Diseño: cuerpo: String --> cambiarActivity() -->
     **/
    // ---------------------------------------------------------------------------------------------
    public void cambiarActivity(String cuerpo){
        SharedPreferences.Editor mEditor = preferencias.edit();
        mEditor.putString("usuarioIniciado", cuerpo);
        Intent myIntent = new Intent(MainActivity.this, Mis_Dispositivos.class);


        mEditor.putString("allinfoUser",cuerpo);
        mEditor.apply();
        myIntent.putExtra("infoUsuario",cuerpo);
        MainActivity.this.startActivity(myIntent);
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Esta función se ejecuta cuando pulsas el botón para iniciar sesión
     * @param v
     * Diseño: View --> botonIniciarSesion() -->
     **/
    // ---------------------------------------------------------------------------------------------
    public void botonIniciarSesion(View v){
        if(correo.getText().toString().equals("") || contraseya.getText().toString().equals("")){
            Toast.makeText(this, (R.string.avisoNadaIniciarSesion), Toast.LENGTH_SHORT).show();
        } else{
            new Logica().buscarUsuario(new Usuario(correo.getText().toString(),contraseya.getText().toString()));
            logica.buscarDispositivosDelUsuario(correo.getText().toString());
            // Esto es para evitar iniciar sesión
            /*Intent myIntent = new Intent(MainActivity.this, Mis_Dispositivos.class);
            MainActivity.this.startActivity(myIntent);*/
        }
    }


    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Estas funciones se ejecutan cuando pulsas los botones para ir a otra actividad
     * @param view
     * Diseño: View --> irX() -->
     **/
    // ---------------------------------------------------------------------------------------------
    public void irPrincipio(View view) {
        Intent i = new Intent(this, Pre_Login_Registro.class);
        startActivity(i);
    }

    public void irRegistro(View view) {
        Intent i = new Intent(this, Pagina_QR.class);
        startActivity(i);
    }

    public void irRecuperarContrasena(View view) {
        Intent i = new Intent(this, Recuperar_Contrasena.class);
        startActivity(i);
    }

    public void obtenerNombresSensor(String cuerpo) {
        SharedPreferences.Editor mEditor = preferencias.edit();
        Log.d("obtenerNombre", "le paso "+ cuerpo);
        mEditor.putString("allinfosensores",cuerpo);
        mEditor.apply();

        jsonDataString=preferencias.getString("allinfosensores","");
        Log.d("CodigoDispositivo", jsonDataString);
        //procedemos a quitar los [] sobrantes obtenidas en la peticion
        StringBuilder sb = new StringBuilder(jsonDataString);
        sb.deleteCharAt(jsonDataString.length() - 1);
        sb.deleteCharAt(0);
        //almacenamos el resultado de la eliminacion en una variable
        String res1= sb.toString();
        String res2= res1.replace("],[",",");//quitamos lo restante de la cadena para poder convertirse enun jsonArray

        Log.d("addItemsFormJson", "jsona "+ res2);
        try {
            //convertimos elstring definitivamente limpio en un jsonArray
            JSONArray jsonArray= new JSONArray(res2);
            //recorremos el jsonArray buscando los nombres en cada casilla
            for(int i=0;i<=jsonArray.length();i++){
                JSONObject itemObj = jsonArray.getJSONObject(i);
                String name = itemObj.getString("Nombre");
                Log.d("addItemsFormJson", "json "+ name);
                //los anyadimos al viewItem
                mEditor.putString("CodigoDispositivo",name);
                mEditor.apply();
                Log.d("CodigoDispositivo",name);
            }
        } catch ( JSONException e) {

        }
    }

    // ---------------------------------------------------------------------------------------------
}