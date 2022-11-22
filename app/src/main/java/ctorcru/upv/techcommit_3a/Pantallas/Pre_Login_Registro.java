package ctorcru.upv.techcommit_3a.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import ctorcru.upv.techcommit_3a.R;
// -----------------------------------------------------------------------------------------
/**
 * @brief Aquí se encuentra el código que configura la funcionalidad de la app
 * Autora: Roberto Matilla Augustinus
 * Archivo: Pre_Login_Registro.java
 **/
// -----------------------------------------------------------------------------------------

public class Pre_Login_Registro extends AppCompatActivity {
    //Objetos
    private Button iniciarSesion;
    private Button registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login_registro);

        // ----------------------------------------------------------
        //Enlazamos los objetos con los elementos
        iniciarSesion = findViewById(R.id.botonIniciarSesion);
        registrarse = findViewById(R.id.botonregistrarse);
        // ----------------------------------------------------------

        // ----------------------------------------------------------
        //Añadimos los listeners a los botones
        iniciarSesion.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        registrarse.setOnClickListener(v -> {
            startActivity(new Intent(this, Pagina_QR.class));
        });
        // ----------------------------------------------------------
    }
}