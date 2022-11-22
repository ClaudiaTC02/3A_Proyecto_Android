package ctorcru.upv.techcommit_3a.Pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import ctorcru.upv.techcommit_3a.R;
// -----------------------------------------------------------------------------------------
/**
 * @brief Aquí se encuentra el código que configura la funcionalidad de la splash screen
 * Autora: Roberto Matilla Augustinus
 * Archivo: SplasScreen.java
 **/
// -----------------------------------------------------------------------------------------

public class SplashScreen extends AppCompatActivity {
    //Objetos
    private SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencias = getSharedPreferences("label", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // ----------------------------------------------------------
        //Con este código hacemos que la pantalla de splash se muestre el tiempo que queramos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String usuarioIniciado = preferencias.getString("usuarioIniciado", "ninguno");

                //Si hay un usuario iniciado, se va a la pantalla de inicio
                if(!usuarioIniciado.equals("ninguno")){
                    Intent myIntent = new Intent(SplashScreen.this, Mis_Dispositivos.class);
                    SplashScreen.this.startActivity(myIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                else {
                    Intent myIntent = new Intent(SplashScreen.this, Pre_Login_Registro.class);
                    SplashScreen.this.startActivity(myIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        }, 1500);
        // ----------------------------------------------------------
    }
}