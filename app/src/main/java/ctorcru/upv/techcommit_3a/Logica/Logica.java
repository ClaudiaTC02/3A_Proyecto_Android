package ctorcru.upv.techcommit_3a.Logica;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

import ctorcru.upv.techcommit_3a.MainActivity;
import ctorcru.upv.techcommit_3a.Modelo.Usuario;
// -----------------------------------------------------------------------------------------
/**
 * @brief Aquí se encuentra el código que llama a las peticiones para realizar la consulta
 * al servidor
 * Autora: Claudia Torres Cruz
 * Archivo: Logica.java
 **/
// -----------------------------------------------------------------------------------------
public class Logica {
    private static final String ETIQUETA_LOG = "Logica_REST";
    private static final String restEndpoint = "http://192.168.0.20:8080";
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Constructor de la clase para poder ser llamado desde otra de forma simple
     * @return objeto Logica
     * Diseño:  --> Logica() --> Logica
     **/
    // ---------------------------------------------------------------------------------------------
    public Logica(){
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método se encarga de comprobar si un usuario existe
     * @param usuario
     * Diseño: Usuario --> insertarMedida() -->
     **/
    // ---------------------------------------------------------------------------------------------
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
                            Toast.makeText(MainActivity.getInstance(), "Es necesario reiniciar la aplicación para continuar.", Toast.LENGTH_SHORT).show();
                            String[] textoeparado = cuerpo.split("[:{}]");

                            String datosB= Arrays.toString(textoeparado);
                            Log.d (ETIQUETA_LOG,"lista" + datosB);
                            datosB.split("[:{}]");

                            String res= "";

                            Log.d (ETIQUETA_LOG,"tamano" + textoeparado.length);

                            for(int i = 0; i < textoeparado.length - 1; i++){
                                Log.d (ETIQUETA_LOG,"orden " + textoeparado[i]);
                                if(i==2){
                                    String correct= textoeparado[i].split(",")[0];
                                    res=correct+";";

                                    Log.d (ETIQUETA_LOG,"ideaso " + res);
                                    //usariof.setId(textoeparado[i+]);
                                }
                                if(i==3){
                                    // usariof.setNombre(i+1);
                                    String correct= textoeparado[i].split(",")[0];
                                    String end1= correct.substring(1,correct.length()-1);
                                    res=res+end1+";";
                                }
                                if(i==4){
                                    Log.d (ETIQUETA_LOG,"contra: " + res);
                                    //usariof.setCorreo(i+1);
                                    String correct= textoeparado[i].split(",")[0];
                                    String end1= correct.substring(1,correct.length()-1);
                                    res=res+end1+";";

                                }
                                if(i==5){
                                    Log.d (ETIQUETA_LOG,"correaso " + res);
                                    // usariof.setContrasena(i+1);
                                    String correct= textoeparado[i].split(",")[0];
                                    String end1= correct.substring(1,correct.length()-1);
                                    res=res+end1+";";
                                }
                            }
                            Log.d (ETIQUETA_LOG,"sol" + res);

                            MainActivity.getInstance().cambiarActivity(res);
                        }
                    }
                });
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Este método se encarga de insertar un usuario
     * @param usuario
     * Diseño: Usuario --> insertarMedida() -->
     **/
    // ---------------------------------------------------------------------------------------------
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
