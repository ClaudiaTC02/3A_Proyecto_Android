package ctorcru.upv.techcommit_3a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ctorcru.upv.techcommit_3a.Logica.Logica;
import ctorcru.upv.techcommit_3a.Modelo.Usuario;
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
        if(!usuarioIniciado.equals("ninguno")){
            setContentView(R.layout.activity_home);
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
        setContentView(R.layout.activity_home);
    }
}