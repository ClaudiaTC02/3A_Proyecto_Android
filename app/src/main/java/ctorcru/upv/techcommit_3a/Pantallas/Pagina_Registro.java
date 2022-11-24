package ctorcru.upv.techcommit_3a.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import  java.util.Properties;

import ctorcru.upv.techcommit_3a.Logica.Logica;
import ctorcru.upv.techcommit_3a.Modelo.Usuario;
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
    private ImageView flecha_atras, ImagenLogo;
    private EditText nombre, correo, contraseña, contraseñaVerificada;
    private Button botonRegistrarse;
    private SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_registro);
        // ----------------------------------------------------------
        //Enlazamos los objetos con los elementos
        flecha_atras = findViewById(R.id.flecha_atrasRegistro);
        ImagenLogo = findViewById(R.id.imagenLogoReg);
        nombre = findViewById(R.id.text_nombre);
        correo = findViewById(R.id.correo2);
        contraseña = findViewById(R.id.contrasena2);
        contraseñaVerificada = findViewById(R.id.verificarContrasenya);
        botonRegistrarse = findViewById(R.id.botonRegistrarse);
        preferencias = getSharedPreferences("label", 0);

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
        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarRegistro(nombre, correo,contraseña,contraseñaVerificada);
            }
        });
        // ----------------------------------------------------------
    }


    // ---------------------------------------------------------------------------------------------

    /**
     * @param view Diseño: View --> irX() -->
     * @brief Estas funciones se ejecutan cuando pulsas los botones para ir a otra actividad
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
    // ---------------------------------------------------------------------------------------------

    /**
     * @param nombre, correo, contraseña, contraseñaVerificada
     *                Diseño: View --> irX() -->
     * @brief Esta funcion ejecuta la comprobacion de datos y registra al usuario
     **/
    // ---------------------------------------------------------------------------------------------
    public void realizarRegistro(EditText nombre, EditText correo, EditText contraseña, EditText contraseñaVerificada) {

        if (nombre.getText().toString().matches("") || correo.getText().toString().matches("")|| contraseña.getText().toString().matches("")) {
            Toast.makeText(Pagina_Registro.this, "Todos los campos han de ser rellenados", Toast.LENGTH_LONG).show();
        }
        else if(!correo.getText().toString().contains("@")){
            Toast.makeText(Pagina_Registro.this, "El formato del correo es incorrecto", Toast.LENGTH_LONG).show();
        }
        if(!contraseña.getText().toString().matches(contraseñaVerificada.getText().toString())){
            Toast.makeText(Pagina_Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            Log.d("registroUsuario", contraseña.getText() + "   " + contraseñaVerificada.getText() + "    " + nombre.getText());

        }
        else{
           Log.d("registroUsuario", nombre.getText().toString() +"  "+ correo.getText().toString() +"  "+ contraseña.getText().toString());
            Usuario usuario = new Usuario(nombre.getText().toString(), correo.getText().toString(), contraseña.getText().toString());
            new Logica().insertarUsuario(usuario);
            String cuerpo = "3;"+nombre.getText().toString()+";"+contraseña.getText().toString()+";"+correo.getText().toString()+";";
            Log.d("cuerpoRegistro", cuerpo);
            sendEmail(nombre.getText().toString(), correo.getText().toString());
            //MainActivity.getInstance().cambiarActivity(cuerpo);
            SharedPreferences.Editor mEditor = preferencias.edit();
            mEditor.putString("usuarioIniciado", cuerpo);
            Intent myIntent = new Intent(Pagina_Registro.this, Mis_Dispositivos.class);
            mEditor.putString("allinfoUser",cuerpo);
            mEditor.apply();
            myIntent.putExtra("infoUsuario",cuerpo);
            Pagina_Registro.this.startActivity(myIntent);
        }
    }

    protected void sendEmail(String nombre, String correo) {
        Log.i("Send email", "");
        String[] TO = {"davidlopez.gandia@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Pagina_Registro.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}