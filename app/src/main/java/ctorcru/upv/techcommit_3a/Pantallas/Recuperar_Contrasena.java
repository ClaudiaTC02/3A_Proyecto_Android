package ctorcru.upv.techcommit_3a.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import ctorcru.upv.techcommit_3a.R;
// -----------------------------------------------------------------------------------------
/**
 * @brief Aquí se encuentra el código que configura la funcionalidad de la app
 * Autora: Roberto Matilla Augustinus
 * Archivo: Recuperar_Contrasena.java
 **/
// -----------------------------------------------------------------------------------------

public class Recuperar_Contrasena extends AppCompatActivity {
    //Objetos
    private ImageView flechaatras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);
        // ----------------------------------------------------------
        //Enlazamos los objetos con los elementos
        flechaatras = findViewById(R.id.flecha_atrasRegistro);
        // ----------------------------------------------------------

        // ----------------------------------------------------------
        //Añadimos los listeners a los botones
        flechaatras.setOnClickListener(v -> {
            irPrincipio(null);
        });
        // ----------------------------------------------------------
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
}