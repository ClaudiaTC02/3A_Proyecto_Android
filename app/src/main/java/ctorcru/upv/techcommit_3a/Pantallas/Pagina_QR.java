package ctorcru.upv.techcommit_3a.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import ctorcru.upv.techcommit_3a.R;
// -----------------------------------------------------------------------------------------
/**
 * @brief Aquí se encuentra el código que configura la funcionalidad de la app
 * Autora: Roberto Matilla Augustinus
 * Archivo: Pagina_QR.java
 **/
// -----------------------------------------------------------------------------------------

public class Pagina_QR extends AppCompatActivity {
    //Objetos
    private Button botonVincularSensor;
    private ImageView flecha_atras,ImagenLogo,ImagenQR;
    private TextView irAIniciarSesion;
    private EditText editTextCodigo;
    private CheckBox checkBoxTerminos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_qr);
        // ----------------------------------------------------------
        //Enlazamos los objetos con los elementos
        botonVincularSensor = findViewById(R.id.scan_qr);
        flecha_atras = findViewById(R.id.flecha_atrasQR);
        ImagenLogo = findViewById(R.id.imagenLogoReg);
        irAIniciarSesion = findViewById(R.id.irAIniciarSesion);
        ImagenQR = findViewById(R.id.imagenparaqr);
        editTextCodigo = findViewById(R.id.escaneo_QR);
        checkBoxTerminos = findViewById(R.id.checkBox);
        Intent intent = getIntent();

        // ----------------------------------------------------------
        //Si el se a scaneado un qr añadirlo al textbox
        if(intent.getStringExtra("Codigo_QR") != null){
            String codigo = intent.getStringExtra("Codigo_QR");
            editTextCodigo.setText(codigo);
        }
        // ----------------------------------------------------------

        // ----------------------------------------------------------
        //Si el checkbox esta marcado se activa el boton
        checkBoxTerminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxTerminos.isChecked()){
                    botonVincularSensor.setEnabled(true);
                }else{
                    botonVincularSensor.setEnabled(false);
                }
            }
        });
        // ----------------------------------------------------------

        //Añadimos los listeners a los botones
        botonVincularSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irRegistro(null);
            }
        });

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

        ImagenQR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AbrirScaner(null);
            }
        });

        irAIniciarSesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                irIniciarSesion(null);
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
    public void irRegistro(View view) {
        Intent i = new Intent(this, Pagina_Registro.class);
        i.putExtra("Codigo_QR", editTextCodigo.getText().toString());
        startActivity(i);
    }

    public void irPrincipio(View view) {
        Intent i = new Intent(this, Pre_Login_Registro.class);
        startActivity(i);
    }

    public void irIniciarSesion(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void AbrirScaner(View view) {
        Intent i = new Intent(this, Scanner_QR.class);
        startActivity(i);
    }
    // ---------------------------------------------------------------------------------------------

}