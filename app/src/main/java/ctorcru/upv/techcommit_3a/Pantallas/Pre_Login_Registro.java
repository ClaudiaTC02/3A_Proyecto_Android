package ctorcru.upv.techcommit_3a.Pantallas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import ctorcru.upv.techcommit_3a.R;
import ctorcru.upv.techcommit_3a.ServicioEscuchaBeacons;
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
        ServicioEscuchaBeacons servicioEscuchaBeacons = new ServicioEscuchaBeacons();

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
        //-----------------------------------------------
        // Pedimos permisos para necesarios en la aplicación
        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.BLUETOOTH_SCAN,Manifest.permission.BLUETOOTH_ADVERTISE,Manifest.permission.BLUETOOTH_CONNECT}, 0);
            servicioEscuchaBeacons.inicializarBlueTooth();
        }
        else {
            Log.d("PERMISOS", "Ya teníamos permisos");
        }
    }
}