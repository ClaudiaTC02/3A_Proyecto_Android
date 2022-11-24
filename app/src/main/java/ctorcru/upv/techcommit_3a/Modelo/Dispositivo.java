package ctorcru.upv.techcommit_3a.Modelo;

import android.util.Log;

import java.util.Arrays;

public class Dispositivo {

    private String IdSensor;
    private String Nombre;
    private String Ciudad;

    public Dispositivo( String idSensor, String nombre, String ciudad) {
        IdSensor = idSensor;
        Nombre = nombre;
        Ciudad= ciudad;
    }
    public Dispositivo() {

    }

    public String getIdSensor() {
        return IdSensor;
    }

    public void setIdSensor(String idSensor) {
        IdSensor = idSensor;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String ciudad) {
        Ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Dispositivo{" +
                "IdSensor='" + IdSensor + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Ciudad='" + Ciudad + '\'' +
                '}';
    }

    public String toJSON(){
        String res = "{" +
                "\"Id_Sensor\":\""+this.getIdSensor()+"\", " +
                "\"Nombre\":\""+this.getNombre()+"\", " +
                "\"Ciudad\":\""+this.getCiudad()+"\", " +
                "}";
        return res;
    }
    public Dispositivo JsonToString(String txt) {
        Dispositivo dispositivof = new Dispositivo();
        Log.d ("en la clase","c.Dispositivov " + txt);
        String[] textoSeparado = txt.split(";");
        Log.d ("en la clase","c.DispositivoUsuario " + Arrays.toString( textoSeparado));
        dispositivof.setNombre(textoSeparado[0]);
        //dispositivof.setCiudad(textoSeparado[1]);
        Log.d ("en la clase","clasedisp " +dispositivof.toString() );

        return  dispositivof;
    }
}
