package ctorcru.upv.techcommit_3a.Modelo;
// ---------------------------------------------------------------------------------------------

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

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
    public void setId(String id) {
        Id = id;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }
    /*public void setFoto(String foto) {
        Foto = foto;
    }*/
    public void setEsAdmin(String admin) {
        EsAdmin = admin;
    }

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
    // ---------------------------------------------------------------------------------------------
    public String getEsAdmin() {
        return EsAdmin;
    }
    /*public String getFoto() {
        return Foto;
    }*/
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
     * @brief Con este método convoerte el objeto a formatee json
     * @return res
     * Diseño: --> getContrasena() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    public String toJSON(){
        String res = "{" +
                "\"Id\":\""+this.getId()+"\", " +
                "\"Nombre\":\""+this.getNombre()+"\", " +
                "\"Contrasena\":\""+this.getContrasena()+"\", "+
                "\"Correo\":\""+this.getCorreo()+"\", " +
                "\"EsAdmin\":\""+this.getEsAdmin()+"\", " +
                //"\"Foto\":\""+this.getFoto()+"\"" +
                "}";
        return res;
    }
    public Usuario JsonToString(String txt) {
        Usuario usariof = new Usuario();
        String[] textoSeparado = txt.split(";");
        Log.d ("en la clase","clase " + Arrays.toString( textoSeparado));
        usariof.setId(textoSeparado[0]);
        usariof.setNombre(textoSeparado[1]);
        usariof.setContrasena(textoSeparado[2]);
        usariof.setCorreo(textoSeparado[3]);
        usariof.setEsAdmin("0");
        //usariof.setFoto(textoSeparado[4]);
        String comprov = usariof.toString();
        Log.d ("en la clase","clase " + comprov);

        return  usariof;
    }

}
