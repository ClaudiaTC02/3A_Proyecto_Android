package ctorcru.upv.techcommit_3a.Logica;

import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

import ctorcru.upv.techcommit_3a.Modelo.Dispositivo;
import ctorcru.upv.techcommit_3a.Pantallas.MainActivity;
import ctorcru.upv.techcommit_3a.Modelo.Usuario;
import ctorcru.upv.techcommit_3a.Pantallas.Mi_Perfil;
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
    private static final String restEndpoint = "http://192.168.68.100:8080";

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
                            //Toast.makeText(MainActivity.getInstance(), "Es necesario reiniciar la aplicación para continuar.", Toast.LENGTH_SHORT).show();
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
     * @param usuario
     * Diseño: actualizarUsuario --> iactualizarUsuario() --> actualizarUsuario
     **/

    public void actualizarUsuario(Usuario usuario, String a){
        PeticionarioREST elPeticionarioREST = new PeticionarioREST();
        //int id=Integer.parseInt(usuario.getId());
        String nuevoEndpoint = new String(restEndpoint+"/actualizarUsuario?correoa="+a+"&Correo="+usuario.getCorreo()+"&Contrasena="+usuario.getContrasena()+"&EsAdmin="+usuario.getEsAdmin()+"&Nombre="+usuario.getNombre()).replaceAll(" ","%20");
        Log.d(ETIQUETA_LOG, "getCorreo  en "+a);

        Log.d(ETIQUETA_LOG, "actualizar  en "+nuevoEndpoint);
        elPeticionarioREST.hacerPeticionREST("GET", nuevoEndpoint,
                null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d (ETIQUETA_LOG,"codigo usuario: " + codigo + " <-> \n" + cuerpo);
                        if(codigo == 200 && !cuerpo.isEmpty()){
                            //Toast.makeText(MainActivity.getInstance(), "Es necesario reiniciar la aplicación para continuar.", Toast.LENGTH_SHORT).show();
                            String[] textoeparado = cuerpo.split("[:{}]");

                            String datosB= Arrays.toString(textoeparado);
                            Log.d (ETIQUETA_LOG,"lista" + datosB);
                            datosB.split("[:{}]");

                            String res= "";

                            Log.d (ETIQUETA_LOG,"tamano" + textoeparado.length);

                            for(int i = 0; i < textoeparado.length; i++){
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
                                /*if(i==7){
                                    Log.d (ETIQUETA_LOG,"foto " + res);
                                    // usariof.setContrasena(i+1);
                                    String correct= textoeparado[i].split(",")[0];
                                    String end1= correct.substring(1,correct.length()-1);
                                    res=res+end1+";";
                                }*/
                            }
                            Log.d (ETIQUETA_LOG,"sol" + res);
                            Mi_Perfil.getInstance().actualizarUsuario(res);


                        }
                    }
                });
    }
    /**
     * @brief Este método se encarga de catualizar un usuario
     * @param usuario
     * Diseño: actualizarUsuario --> iactualizarUsuario() --> actualizarUsuario
     **/

    public void buscarDispositivoUsuario(String usuario){
        PeticionarioREST elPeticionarioREST = new PeticionarioREST();

        String nuevoEndpoint = new String(restEndpoint+"/buscarDispositivoUsuario?Id_Usuario="+usuario).replaceAll(" ","%20");

        Log.d(ETIQUETA_LOG, "obtener dispositivo en "+nuevoEndpoint);
        elPeticionarioREST.hacerPeticionREST("GET", nuevoEndpoint,
                null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d (ETIQUETA_LOG,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                        Log.d (ETIQUETA_LOG,"llega:  "+cuerpo);
                        if(codigo == 200 && !cuerpo.isEmpty()){
                            //Toast.makeText(MainActivity.getInstance(), "Es necesario reiniciar la aplicación para continuar.", Toast.LENGTH_SHORT).show();
                            //creamos una lista de strings que alacenará lo que obtengamos del json la peticion pero sin : y {}
                            String[] textoeparado = cuerpo.split("[:{}]");
                            String checked = Arrays.toString(textoeparado);
                            Log.d (ETIQUETA_LOG,"conversion:  "+checked);
                            String[] recuperacion = checked.split("[:{}]");
                            Log.d (ETIQUETA_LOG,"conversion2:  "+textoeparado[3].split(",")[0]);
                            String checked2 = Arrays.toString(recuperacion);
                            Log.d (ETIQUETA_LOG,"conversion3:  "+checked2);
                            //esta sera la variable encargada de almacenar el resultado
                            String res= "";
                            //recorrremos la lista de strings anterior
                            for(int i = 0; i < textoeparado.length; i++){
                                //en esta posición está el id del sensor
                                if(i==3){
                                    //nos guardamos el id del sensor
                                    String correct= textoeparado[i].split(",")[0];
                                    //lo anyadimos a la variable encargada de almacenar el resultado
                                    res=res+correct;
                                }


                            }

                            Log.d (ETIQUETA_LOG,"disposol" + res);
                            //enviamos el resultado anterior sacado del Json y se lo enviamos a la función cambiarActivity

                            Mi_Perfil.getInstance().buscarDispositivoUsuario(res);
                        }
                    }
                });
    }
    public void obtenerNombrePorId(String id){
        PeticionarioREST elPeticionarioREST = new PeticionarioREST();

        String nuevoEndpoint = new String(restEndpoint+"/buscarDispositivoPorId?Id_Dispositivo="+id).replaceAll(" ","%20");

        Log.d(ETIQUETA_LOG, "obtener dispositivo en "+nuevoEndpoint);
        elPeticionarioREST.hacerPeticionREST("GET", nuevoEndpoint,
                null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d (ETIQUETA_LOG,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                        Log.d (ETIQUETA_LOG,"llega:  "+cuerpo);
                        if(codigo == 200 && !cuerpo.isEmpty()){
                            //creamos una lista de strings que alacenará lo que obtengamos del json la peticion pero sin : y {}
                            String[] textoeparado = cuerpo.split("[:{}]");
                            //esta sera la variable encargada de almacenar el resultado
                            String res= "";
                            //recorrremos la lista de strings anterior
                            for(int i = 0; i < textoeparado.length; i++){
                                //en esta posición está almacenado el id del sensor
                                //en esta posición está el nombre del sensor
                                if(i==3){
                                    //nos guardamos el nombre del sensor
                                    String correct= textoeparado[i].split(",")[0];
                                    //lo anyadimos a la variable encargada de almacenar el resultado
                                    res=res+correct;
                                }


                            }
                            Log.d (ETIQUETA_LOG,"disposol" + res);
                            //enviamos el resultado anterior sacado del Json y se lo enviamos a la función buscardispositivo
                            Dispositivo sensor= new Dispositivo();
                            Dispositivo sensorfinal=new Dispositivo();
                            sensorfinal=sensor.JsonToString(res);
                            Mi_Perfil.getInstance().obtenerNombreporId(res);
                        }
                    }
                });
    }
    public void buscarSenorCiudadporId(String id){
        PeticionarioREST elPeticionarioREST = new PeticionarioREST();

        String nuevoEndpoint = new String(restEndpoint+"/buscarDispositivoPorId?Id_Dispositivo="+id).replaceAll(" ","%20");

        Log.d(ETIQUETA_LOG, "obtener dispositivo en "+nuevoEndpoint);
        elPeticionarioREST.hacerPeticionREST("GET", nuevoEndpoint,
                null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        Log.d (ETIQUETA_LOG,"codigo respuesta: " + codigo + " <-> \n" + cuerpo);
                        Log.d (ETIQUETA_LOG,"llega:  "+cuerpo);
                        if(codigo == 200 && !cuerpo.isEmpty()){
                            //creamos una lista de strings que alacenará lo que obtengamos del json la peticion pero sin : y {}
                            String[] textoeparado = cuerpo.split("[:{}]");
                            //esta sera la variable encargada de almacenar el resultado
                            String res= "";
                            //recorrremos la lista de strings anterior
                            for(int i = 0; i < textoeparado.length; i++){
                                //en esta posición está almacenado el id del sensor
                                //en esta posición está el nombre del sensor
                                if(i==4){
                                    //nos guardamos el id de la ciudad del sensor
                                    String correct= textoeparado[i].split(",")[0];
                                    //lo anyadimos a la variable encargada de almacenar el resultado
                                    res=res+correct;
                                }


                            }
                            Log.d (ETIQUETA_LOG,"disposol" + res);
                            //enviamos el resultado anterior sacado del Json y se lo enviamos a la función buscardispositivo
                            Dispositivo sensor= new Dispositivo();
                            Dispositivo sensorfinal=new Dispositivo();
                            sensorfinal=sensor.JsonToString(res);
                            Mi_Perfil.getInstance().obtenerciudad(res);
                        }
                    }
                });
    }
}
