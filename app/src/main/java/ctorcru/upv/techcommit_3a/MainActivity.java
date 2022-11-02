package ctorcru.upv.techcommit_3a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    private EditText contraseya;

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
        }
    }
}