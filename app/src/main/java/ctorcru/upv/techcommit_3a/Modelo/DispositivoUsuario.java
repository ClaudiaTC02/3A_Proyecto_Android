package ctorcru.upv.techcommit_3a.Modelo;

import android.util.Log;

import java.util.Arrays;

public class DispositivoUsuario {
    private String IdUsuario;
    private String IdSensor;

    public DispositivoUsuario(String idUsuario, String idSensor) {
        IdUsuario = idUsuario;
        IdSensor = idSensor;
    }
    public DispositivoUsuario() {

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
        return "DispositivoUsuario{" +
                "IdUsuario='" + IdUsuario + '\'' +
                ", IdSensor='" + IdSensor + '\'' +
                '}';
    }
    public String toJSON(){
        String res = "{" +
                "\"Id_Sensor\":\""+this.getIdUsuario()+"\", " +
                "\"Id_Dispositivo\":\""+this.getIdSensor()+"\", " +
                "}";
        return res;
    }

    public DispositivoUsuario JsonToString(String txt) {
        DispositivoUsuario dispositivof = new DispositivoUsuario();
        Log.d ("en la clase","c.Dispositivov " + txt);
        String[] textoSeparado = txt.split(";");
        Log.d ("en la clase","c.DispositivoUsuario " + Arrays.toString( textoSeparado));
        dispositivof.setIdSensor(textoSeparado[0]);
        Log.d ("en la clase","clasedisp " +dispositivof.toString() );
        return  dispositivof;
    }


}