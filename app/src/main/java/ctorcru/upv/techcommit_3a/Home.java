package ctorcru.upv.techcommit_3a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ctorcru.upv.techcommit_3a.Modelo.Usuario;

public class Home extends AppCompatActivity {
    private Button botonBusqueda;
    private Button botonDetenerBusqueda, botonPerfil;
    private String datosUsuario;
    private TextView nombreUsuario, correoUsuario;
    private Usuario infoUsuario = new Usuario();
    private SharedPreferences preferencias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencias = getSharedPreferences("label", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        botonBusqueda = findViewById(R.id.botonBusqueda);
        botonDetenerBusqueda = findViewById(R.id.botonDetenerBusqueda);
        botonPerfil= findViewById(R.id.btnPerfil);

        nombreUsuario = findViewById(R.id.txtNombreh);
        datosUsuario= getIntent().getStringExtra("infoUsuario");
        String userpref= preferencias.getString("allinfoUser","");

        Usuario dtosdef;
        dtosdef= infoUsuario.JsonToString(userpref);
        infoUsuario.JsonToString(userpref);

       nombreUsuario.setText("Hola! "+ dtosdef.getNombre()+", te damos la bienvenida");
        //guardarEstado();
        botonBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "La búsqueda comenzará", Toast.LENGTH_SHORT).show();
                startService(new Intent(Home.this, ServicioEscuchaBeacons.class));
            }
        });

        botonDetenerBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "La búsqueda se detendrá", Toast.LENGTH_SHORT).show();
                stopService(new Intent(Home.this, ServicioEscuchaBeacons.class));
            }
        });
        botonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Home.this, PerfilUsuario.class);
                Home.this.startActivity(myIntent);
            }
        });
    }
   /* public void guardarEstado(){
        String[] textoSeparado = datosUsuario.split(";");
        SharedPreferences preferences= getSharedPreferences("sesion", Context.MODE_PRIVATE);
        boolean estado = true;
        SharedPreferences.Editor editor= preferences.edit();
        editor.putBoolean("conectado",estado);
        editor.putString("id", textoSeparado[0]);
        editor.putString("nombre", textoSeparado[1]);
        editor.putString("constrasena", textoSeparado[2]);
        editor.putString("correo", textoSeparado[3]);
        editor.apply();
    }*/
}