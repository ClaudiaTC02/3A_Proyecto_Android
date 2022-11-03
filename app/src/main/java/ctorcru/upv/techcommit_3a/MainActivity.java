package ctorcru.upv.techcommit_3a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ctorcru.upv.techcommit_3a.Logica.Logica;
import ctorcru.upv.techcommit_3a.Modelo.Usuario;

public class MainActivity extends AppCompatActivity {
    //Atributos
    private static final String ETIQUETA_LOG = "TechCommit_Log";
    //Objetos
    private EditText correo;
    private static MainActivity myContext;
    private EditText contraseya;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Enlazamos los objetos con los elementos
        correo = findViewById(R.id.correo);
        contraseya = findViewById(R.id.contrasenya);
    }

    public void botonIniciarSesion(View v){
        if(correo.getText().equals(null) || contraseya.getText().equals(null)){
            Toast.makeText(this, "Introduzca por favor sus datos", Toast.LENGTH_SHORT).show();
        } else{
            new Logica().buscarUsuario(new Usuario(correo.getText().toString(),contraseya.getText().toString()));
            //new Logica().insertarUsuario(new Usuario("","Pepe","uwu@uwu.com","1234"));
        }
    }
    public void cambiarActivity(){
        setContentView(R.layout.activity_home);
    }
}