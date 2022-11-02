package ctorcru.upv.techcommit_3a.Logica;

import android.util.Log;

import ctorcru.upv.techcommit_3a.Modelo.Usuario;

public class Logica {
    private static final String ETIQUETA_LOG = "Logica_REST";
    private static final String restEndpoint = "http://192.168.84.137:8080";
    public Logica(){
    }
    public void buscarUsuario(Usuario usuario){
        PeticionarioREST elPeticionarioREST = new PeticionarioREST();
        String nuevoEndpoint = new String(restEndpoint+"/buscarUsuario?Correo="+usuario.getCorreo()+"&Contrasena="+usuario.getContraseña()).replaceAll(" ","%20");
        Log.d(ETIQUETA_LOG, "obtener usuario en "+nuevoEndpoint);
        elPeticionarioREST.hacerPeticionREST("GET", restEndpoint,
                null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d (ETIQUETA_LOG,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                    }
                });
    }
}
