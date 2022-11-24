package ctorcru.upv.techcommit_3a.Pantallas;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView politica;
    private AlertDialog.Builder dialogo_politica_privacidad;

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
        politica = findViewById(R.id.politica);
        Intent intent = getIntent();

        // ----------------------------------------------------------
        //Si el se a scaneado un qr añadirlo al textbox
        if(intent.getStringExtra("Codigo_QR") != null){
            String codigo = intent.getStringExtra("Codigo_QR");
            editTextCodigo.setText(codigo);
        }
        // ----------------------------------------------------------

        // ----------------------------------------------------------
        //Si el checkbox esta marcado y edittext con código se activa el boton
        checkBoxTerminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkBoxTerminos.isEnabled()){
                    Toast.makeText(Pagina_QR.this, "ss", Toast.LENGTH_SHORT).show();
                }
                if(checkBoxTerminos.isChecked() && !editTextCodigo.getText().toString().isEmpty()){
                    botonVincularSensor.setEnabled(true);
                }
                else if(!checkBoxTerminos.isChecked() && editTextCodigo.getText().toString().isEmpty()){
                    botonVincularSensor.setEnabled(false);
                }
                else if(!checkBoxTerminos.isChecked() && !editTextCodigo.getText().toString().isEmpty()) {
                    botonVincularSensor.setEnabled(false);
                }
                else if(checkBoxTerminos.isChecked() && editTextCodigo.getText().toString().isEmpty()){
                    botonVincularSensor.setEnabled(false);
                }
                else{
                    botonVincularSensor.setEnabled(false);
                }
            }

        });
        // ----------------------------------------------------------


        // ----------------------------------------------------------
        //Abrir dialogo de politica de privacidad al pulsar en el texto
        politica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo_politica_privacidad = new AlertDialog.Builder(Pagina_QR.this);
                //Mostrar xml
                dialogo_politica_privacidad.setView(R.layout.dialogo_politica_privacidad);
                //Boton aceptar
                dialogo_politica_privacidad.setPositiveButton("Aceptar", null);
                dialogo_politica_privacidad.show();
            }
        });
        // ----------------------------------------------------------

        // ----------------------------------------------------------
        //Subrayar texto de politica de privacidad
        politica.setPaintFlags(politica.getPaintFlags() |   android.graphics.Paint.UNDERLINE_TEXT_FLAG);
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