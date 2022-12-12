package ctorcru.upv.techcommit_3a.Logica;

import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;


import ctorcru.upv.techcommit_3a.Pantallas.MainActivity;
import ctorcru.upv.techcommit_3a.Modelo.Usuario;
import ctorcru.upv.techcommit_3a.Pantallas.Mi_Perfil;
import ctorcru.upv.techcommit_3a.Pantallas.Mis_Dispositivos;
import ctorcru.upv.techcommit_3a.Pantallas.Pagina_Registro;
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
    private static final String restEndpoint = "http://192.168.137.1:8080";

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
        String nuevoEndpoint = new String(restEndpoint+"/verificarUsuario?Correo="+usuario.getCorreo()+"&Contrasena="+usuario.getContrasena()).replaceAll(" ","%20");

        Log.d(ETIQUETA_LOG, "obtener usuario en "+nuevoEndpoint);
        elPeticionarioREST.hacerPeticionREST("GET", nuevoEndpoint,
                null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d (ETIQUETA_LOG,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                        if(codigo == 200 && !cuerpo.isEmpty()){

                            MainActivity.getInstance().cambiarActivity(cuerpo);
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
        Log.d("PRUEBA",usuario.toJSON());
        elPeticionarioREST.hacerPeticionREST("POST", nuevoEndpoint,
                usuario.toJSON(),
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d ("PRUEBA",usuario.toJSON());
                        Log.d ("PRUEBA","codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                    }
                });
    }
    /**
     * @brief Este método se encarga de insertar un usuario_dispositivo
     * @param correo
     * @param nombre
     * Diseño: int,int --> insertarUsuario_Dispositivo() -->
     **/
    // ---------------------------------------------------------------------------------------------
    public void insertarUsuario_Dispositivo(String correo, String nombre){
        PeticionarioREST elPeticionarioREST = new PeticionarioREST();
        String res = "{" +
                "\"Correo\":\""+correo+"\", " +
                "\"Nombre\":\""+nombre+"\"" +
                "}";
        String nuevoEndpoint = new String(restEndpoint+"/usuario_dispositivo").replaceAll(" ","%20");
        Log.d("PRUEBA", "publicarMediciones endpoint: "+nuevoEndpoint);
        Log.d("PRUEBA",res);
        elPeticionarioREST.hacerPeticionREST("POST", nuevoEndpoint,
                res,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d ("PRUEBA",res);
                        Log.d ("PRUEBA","codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                    }
                });
    }
    /**
     * @brief Este método se encarga de catualizar un usuario
     * @param usuario, a
     * Diseño: usuario, string correoantiguo --> iactualizarUsuario() --> actualizarUsuario
     **/

    public void actualizarUsuario(Usuario usuario, String a){
        PeticionarioREST elPeticionarioREST = new PeticionarioREST();
        //variable creada para almacenar la info que le pasaremos en el cuerpo
        String res = "{" +
                "\"Id\":\""+usuario.getId()+"\", " +
                "\"Nombre\":\""+usuario.getNombre()+"\", " +
                "\"Contrasena\":\""+usuario.getContrasena()+"\", "+
                "\"Correo\":\""+usuario.getCorreo()+"\", " +
                "\"EsAdmin\":\""+usuario.getEsAdmin()+"\"" +
                "}";
        String nuevoEndpoint = new String(restEndpoint+"/actualizarUsuario?correoa="+a).replaceAll(" ","%20");//creamos el endpoint pasandole el correo antiguo
        Log.d("PRUEBA", "publicarMediciones endpoint: "+nuevoEndpoint);
        Log.d("PRUEBA",res);
        elPeticionarioREST.hacerPeticionREST("POST", nuevoEndpoint,
                res,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d ("PRUEBA",res);
                        Log.d ("PRUEBA","codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                        if(codigo == 200 && !cuerpo.isEmpty()){
                            Mi_Perfil.getInstance().actualizarUsuario(cuerpo);//llamamos a la funcion de mi perfil con los datos obtenidos de la peticion

                        }
                    }
                });
    }

    /**
     * @brief Este método se encarga de buscar los dispositivos del usuario mediante su correo
     * @param  correo
     * Diseño: String correo --> buscarDispositivosDelUsuario() --> buscarDispositivosDelUsuario
     **/
    public void buscarDispositivosDelUsuario(String correo){
        PeticionarioREST elPeticionarioREST = new PeticionarioREST();
        String Endpoint1 = new String(restEndpoint+"/buscarDispositivosUsuarioPorCorreo?Correo="+correo).replaceAll(" ","%20");//creamos el endpoint pasandole el correo
        Log.d(ETIQUETA_LOG, "obtener id de los sensores en "+Endpoint1);
        elPeticionarioREST.hacerPeticionREST("GET", Endpoint1,
                null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d(ETIQUETA_LOG, "codigo respuesta: " + codigo + " <-> \n" + cuerpo);

                        if (codigo == 200 && !cuerpo.isEmpty()) {
                            Log.d(ETIQUETA_LOG, "obtener nombres "+ cuerpo );

                            MainActivity.getInstance().obtenerNombresSensor(cuerpo);
                        }
                    }
                });

    }
}
