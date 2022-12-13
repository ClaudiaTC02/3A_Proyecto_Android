package ctorcru.upv.techcommit_3a.Modelo;
// ---------------------------------------------------------------------------------------------

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * @brief Esta clase se encarga de montar el objeto que se subirá a la base de datos
 * Autora: Claudia Torres Cruz
 * Archivo: Usuario.java
 **/
// ---------------------------------------------------------------------------------------------
public class Usuario {
    //Atributos
    private String Id;
    private String Nombre;
    private String Correo;
    private String Contrasena;
    private String EsAdmin="0";//cambiar cuando se use, ahora esta en predefinido
    //private String Foto;

// ---------------------------------------------------------------------------------------------
    /**
     * @brief Constructor de la clase VACIO
     * @return objeto Usuario
     * Diseño: String, String, String, String --> Usuario() --> Usuario
     **/
    // ---------------------------------------------------------------------------------------------
    public Usuario() {

    }
    /*public Usuario(String foto) {
        Foto= foto;
    }*/
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Constructor de la clase
     * @param id_,
     * @param nombre
     * @param correo
     * @param contrasena
     * @return objeto Usuario
     * Diseño: String, String, String, String --> Usuario() --> Usuario
     **/
    // ---------------------------------------------------------------------------------------------
    public Usuario(String id_,String nombre, String correo, String contrasena) {
        Id = id_;
        Nombre = nombre;
        Correo = correo;
        Contrasena = contrasena;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Constructor de la clase
     * @param correo
     * @param contrasena
     * @return objeto Usuario
     * Diseño: String, String --> Usuario() --> Usuario
     **/
    // ---------------------------------------------------------------------------------------------
    public Usuario(String correo, String contrasena) {
        Correo = correo;
        Contrasena = contrasena;
    }
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Constructor de la clase
     * @param nombre
     * @param correo
     * @param contrasena
     * @return objeto Usuario
     * Diseño: String,String, String --> Usuario() --> Usuario
     **/
    // ---------------------------------------------------------------------------------------------
    public Usuario(String nombre, String correo, String contrasena) {
        Nombre = nombre;
        Correo = correo;
        Contrasena = contrasena;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se da el id
     * Diseño:String --> setId()
     **/
    // ---------------------------------------------------------------------------------------------
    public void setId(String id) {
        Id = id;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se da el nombre
     * Diseño: String--> setNombre()
     **/
    // ---------------------------------------------------------------------------------------------
    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se da el correo
     * Diseño: String -->setCorreo()
     **/
    // ---------------------------------------------------------------------------------------------
    public void setCorreo(String correo) {
        Correo = correo;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se da la contrasenya
     * Diseño: String --> setContrasena()
     **/
    // ---------------------------------------------------------------------------------------------
    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método da su rol
     * Diseño: String-->setEsAdmin()
     **/
    // ---------------------------------------------------------------------------------------------
    public void setEsAdmin(String admin) {
        EsAdmin = admin;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene el id
     * @return Id
     * Diseño: --> getId() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    public String getId() {
        return Id;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene el nombre
     * @return Nombre
     * Diseño: --> getNombre() --> Nombre
     **/
    // ---------------------------------------------------------------------------------------------

    public String getNombre() {
        return Nombre;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene el correo
     * @return Correo
     * Diseño: --> getCorreo() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    public String getCorreo() {
        return Correo;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene la contrasena
     * @return Contrasena
     * Diseño: --> getContrasena() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    public String getContrasena() {
        return Contrasena;
    }

    /**
     * @brief Con este método se obtiene su rol
     * @return EsAdmin
     * Diseño: --> getEsAdmin() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    public String getEsAdmin() {
        return EsAdmin;
    }

    /**
     * @brief Con este método se pasa a string el usuario
     * @return String
     * Diseño: --> toString() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "Usuario{" +
                "Id=" + Id +  '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Contrasena='" + Contrasena + '\'' +
                ", EsAdmin='" + EsAdmin + '\'' +
                //", Foto='" + Foto + '\'' +
                '}';
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método convoerte el objeto a formato json
     * @return res
     * Diseño: --> toJSON() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    public String toJSON(){
        String res = "{" +
                "\"Id\":\""+this.getId()+"\", " +
                "\"Nombre\":\""+this.getNombre()+"\", " +
                "\"Contrasena\":\""+this.getContrasena()+"\", "+
                "\"Correo\":\""+this.getCorreo()+"\", " +
                "\"EsAdmin\":\""+this.getEsAdmin()+"\"" +
                //"\"Foto\":\""+this.getFoto()+"\"" +
                "}";
        return res;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método convierte el json en un usuario
     * @return usuariof
     * Diseño: String --> JsonToString() --> usuariof
     **/
    // ---------------------------------------------------------------------------------------------
    public Usuario JsonToString(String txt) throws JSONException {
        Usuario usariof = new Usuario();
        //creamos un objeto json con el string que recibimos
        JSONObject obj = new JSONObject(txt);
        //recogemos sus respectivos datos
        //usariof.setId(obj.getString("id"));
        usariof.setNombre(obj.getString("Nombre"));
        usariof.setContrasena(obj.getString("Contrasena"));
        usariof.setCorreo(obj.getString("Correo"));
        usariof.setEsAdmin("0");

        //devolvemos el usuario
        String comprov = usariof.toString();
        Log.d ("en la clase","clase " + comprov);

        return  usariof;
    }

}
