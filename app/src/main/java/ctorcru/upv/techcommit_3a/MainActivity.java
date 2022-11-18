package ctorcru.upv.techcommit_3a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ctorcru.upv.techcommit_3a.Logica.Logica;
import ctorcru.upv.techcommit_3a.Modelo.Usuario;
import ctorcru.upv.techcommit_3a.Pantallas.Mis_Dispositivos;
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
    private static MainActivity myContext;
    private EditText contraseya;
    private SharedPreferences preferencias;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencias = getSharedPreferences("label", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Enlazamos los objetos con los elementos
        correo = findViewById(R.id.correo);
        contraseya = findViewById(R.id.contrasenya);
        String usuarioIniciado = preferencias.getString("usuarioIniciado", "ninguno");

        //Si hay un usuario iniciado, se va a la pantalla de inicio
        if(!usuarioIniciado.equals("ninguno")){
            Intent myIntent = new Intent(MainActivity.this, Home.class);
            MainActivity.this.startActivity(myIntent);
        }

    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Esta función se ejecuta cuando pulsas el botón para iniciar sesión
     * @param v
     * Diseño: View --> botonIniciarSesion() -->
     **/
    // ---------------------------------------------------------------------------------------------
    public void botonIniciarSesion(View v){
        if(correo.getText().equals(null) || contraseya.getText().equals(null)){
            Toast.makeText(this, "Introduzca por favor sus datos", Toast.LENGTH_SHORT).show();
        } else{
            new Logica().buscarUsuario(new Usuario(correo.getText().toString(),contraseya.getText().toString()));
            //new Logica().insertarUsuario(new Usuario("","Pepe","uwu@uwu.com","1234"));
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
        mEditor.putString("usuarioIniciado", cuerpo).commit();
        Intent myIntent = new Intent(MainActivity.this, Mis_Dispositivos.class);
        /*String[] textoeparado = cuerpo.split("[:{}]");

        String datosB= Arrays.toString(textoeparado);
        Log.d (ETIQUETA_LOG,"lista" + datosB);
        datosB.split("[:{}]");

        String res= "";

        Log.d (ETIQUETA_LOG,"tamano" + textoeparado.length);

        for(int i = 0; i < textoeparado.length - 1; i++){
            Log.d (ETIQUETA_LOG,"orden " + textoeparado[i]);
            if(i==2){
                String correct= textoeparado[i].split(",")[0];
                res=correct+";";

                Log.d (ETIQUETA_LOG,"ideaso " + res);
               //usariof.setId(textoeparado[i+]);
            }
            if(i==3){
               // usariof.setNombre(i+1);
                String correct= textoeparado[i].split(",")[0];
                String end1= correct.substring(1,correct.length()-1);
                res=res+end1+";";
            }
            if(i==4){
                Log.d (ETIQUETA_LOG,"contra: " + res);
                //usariof.setCorreo(i+1);
                String correct= textoeparado[i].split(",")[0];
                String end1= correct.substring(1,correct.length()-1);
                res=res+end1+";";

            }
            if(i==5){
                Log.d (ETIQUETA_LOG,"correaso " + res);
               // usariof.setContrasena(i+1);
                String correct= textoeparado[i].split(",")[0];
                String end1= correct.substring(1,correct.length()-1);
                res=res+end1+";";
            }
        }
        Log.d (ETIQUETA_LOG,"sol" + res);*/
        mEditor.putString("allinfoUser",cuerpo);
        mEditor.apply();
        myIntent.putExtra("infoUsuario",cuerpo);
        MainActivity.this.startActivity(myIntent);
    }
}