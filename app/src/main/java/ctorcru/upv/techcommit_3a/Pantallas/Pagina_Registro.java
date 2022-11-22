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
 * Archivo: Pagina_Registro.java
 **/
// -----------------------------------------------------------------------------------------

public class Pagina_Registro extends AppCompatActivity {
    //Objetos
    private ImageView flecha_atras,ImagenLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_registro);
        // ----------------------------------------------------------
        //Enlazamos los objetos con los elementos
        flecha_atras = findViewById(R.id.flecha_atrasRegistro);
        ImagenLogo = findViewById(R.id.imagenLogoReg);

        // ----------------------------------------------------------
        //Añadimos los listeners a los botones
        flecha_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irQR(null);
            }
        });

        ImagenLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irPrincipio(null);
            }
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

    public void irQR(View view) {
        Intent i = new Intent(this, Pagina_QR.class);
        startActivity(i);
    }
    // ---------------------------------------------------------------------------------------------

}