package ctorcru.upv.techcommit_3a.Modelo;

import android.util.Log;

import java.util.Arrays;

public class Dispositivo {
    private String IdUsuario;
    private String IdSensor;

    public Dispositivo(String idUsuario, String idSensor) {
        IdUsuario = idUsuario;
        IdSensor = idSensor;
    }
    public Dispositivo() {

    }

    public String getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getIdSensor() {
        return IdSensor;
    }

    public void setIdSensor(String idSensor) {
        IdSensor = idSensor;
    }

    @Override
    public String toString() {
        return "Dispositivo{" +
                "IdUsuario='" + IdUsuario + '\'' +
                ", IdSensor='" + IdSensor + '\'' +
                '}';
    }
    public String toJSON(){
        String res = "{" +
                "\"Id\":\""+this.getIdUsuario()+"\", " +
                "\"Nombre\":\""+this.getIdSensor()+"\", " +
                "}";
        return res;
    }
    public Dispositivo JsonToString(String txt) {
        Dispositivo dispositivof = new Dispositivo();
        String[] textoSeparado = txt.split(";");
        Log.d ("en la clase","c.Dispositivo " + Arrays.toString( textoSeparado));
        dispositivof.setIdUsuario(textoSeparado[0]);
        dispositivof.setIdSensor(textoSeparado[1]);
        String comprov = textoSeparado.toString();
        Log.d ("en la clase","clase " + comprov);

        return  dispositivof;
    }
}