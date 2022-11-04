package ctorcru.upv.techcommit_3a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    private Button botonBusqueda;
    private Button botonDetenerBusqueda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        botonBusqueda = findViewById(R.id.botonBusqueda);
        botonDetenerBusqueda = findViewById(R.id.botonDetenerBusqueda);

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
    }
}