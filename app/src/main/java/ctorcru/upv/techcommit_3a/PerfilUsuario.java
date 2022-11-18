package ctorcru.upv.techcommit_3a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ctorcru.upv.techcommit_3a.Modelo.Usuario;

public class PerfilUsuario extends AppCompatActivity {
    private TextView nombrePerfil,correoPerfil, idPerfil ;
    private EditText contrasenaPerfil;
    private Button btnBack;
    private Usuario infoUsuario = new Usuario();
    private SharedPreferences preferencias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencias = getSharedPreferences("label", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        btnBack = findViewById(R.id.btn_back);

        String userpref= preferencias.getString("allinfoUser","");
        Usuario dtosdef;
        dtosdef= infoUsuario.JsonToString(userpref);
        infoUsuario.JsonToString(userpref);
        nombrePerfil = findViewById(R.id.txtUsername);
        nombrePerfil.setText(dtosdef.getNombre());
        correoPerfil = findViewById(R.id.txtmail);
        correoPerfil.setText(dtosdef.getCorreo());
        contrasenaPerfil = findViewById(R.id.editTextTextPassword);
        contrasenaPerfil.setText(dtosdef.getContrasena());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(PerfilUsuario.this, Home.class);
                PerfilUsuario.this.startActivity(myIntent);
                finish();
            }
        });


    }
}