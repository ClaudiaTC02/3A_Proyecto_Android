package ctorcru.upv.techcommit_3a.Modelo;
// ---------------------------------------------------------------------------------------------
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
    @Override
    public String toString() {
        return "Usuario{" +
                "Id=" + Id +
                ", Nombre='" + Nombre + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Contrasena='" + Contrasena + '\'' +
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
                "\"Id\":\""+""+"\", " +
                "\"Nombre\":\""+this.getNombre()+"\", " +
                "\"Contrasena\":\""+this.getContrasena()+"\", " +
                "\"Correo\":\""+this.getCorreo()+"\"" +
                "}";
        return res;
    }
}
