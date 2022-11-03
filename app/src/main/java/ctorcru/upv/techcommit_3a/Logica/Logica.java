package ctorcru.upv.techcommit_3a.Logica;

import android.util.Log;

import ctorcru.upv.techcommit_3a.MainActivity;
import ctorcru.upv.techcommit_3a.Modelo.Usuario;

public class Logica {
    private static final String ETIQUETA_LOG = "Logica_REST";
    private static final String restEndpoint = "http://192.168.137.1:8080";
    public Logica(){
    }
    public void buscarUsuario(Usuario usuario){
        PeticionarioREST elPeticionarioREST = new PeticionarioREST();
        String nuevoEndpoint = new String(restEndpoint+"/buscarUsuario?Correo="+usuario.getCorreo()+"&Contrasena="+usuario.getContrasena()).replaceAll(" ","%20");

        Log.d(ETIQUETA_LOG, "obtener usuario en "+nuevoEndpoint);
        elPeticionarioREST.hacerPeticionREST("GET", nuevoEndpoint,
                null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d (ETIQUETA_LOG,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                        if(codigo == 200 && !cuerpo.isEmpty()){
                            MainActivity.getInstance().cambiarActivity();
                        }
                    }
                });
    }
    public void insertarUsuario(Usuario usuario){
        PeticionarioREST elPeticionarioREST = new PeticionarioREST();
        // 192.168.1.114 ip portatil
        // 192.168.85.84 ip pc sobremesa
        String nuevoEndpoint = new String(restEndpoint+"/usuario").replaceAll(" ","%20");
        Log.d("PRUEBA", "publicarMediciones endpoint: "+nuevoEndpoint);
        elPeticionarioREST.hacerPeticionREST("POST", "http://192.168.85.93:8080/usuario",
                usuario.toJSON(),
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d ("PRUEBA",usuario.toJSON());
                        Log.d ("PRUEBA","codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                    }
                });
    }
}
