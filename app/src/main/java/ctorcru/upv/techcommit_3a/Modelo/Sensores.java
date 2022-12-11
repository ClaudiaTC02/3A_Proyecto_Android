package ctorcru.upv.techcommit_3a.Modelo;
/**
 * @brief Esta clase se encarga de montar el objeto que administrará los dispositivos pertenencientes al usuario
 * Autora: Enrique Ferre Carbonell
 * Archivo: Sensores.java
 **/
// ---------------------------------------------------------------------------------------------

public class Sensores {
    //Atributos
    private String nombre;
    private String correoUsuario;
    private Float medida;

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Constructor de la clase
     * @param nombre
     * @return objeto Sensores
     * Diseño: String --> Sensores() --> Sensores
     **/
    // ---------------------------------------------------------------------------------------------
    public Sensores(String nombre){
        this.nombre = nombre;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Constructor de la clase
     * @param correoUsuario,
     * @param nombre
     * @return objeto Sensores
     * Diseño: String, String -->  Sensores() --> Sensores
     **/
    // ---------------------------------------------------------------------------------------------
    public Sensores(String nombre, String correoUsuario) {
        this.nombre = nombre;
        this.correoUsuario = correoUsuario;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Constructor de la clase
     * @param correoUsuario,
     * @param nombre
     * @param medida
     * @return objeto Sensores
     * Diseño: String, String -->  Sensores() --> Sensores
     **/
    // ---------------------------------------------------------------------------------------------
    public Sensores(String nombre, String correoUsuario, Float medida) {
        this.nombre = nombre;
        this.correoUsuario = correoUsuario;
        this.medida = medida;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene la medida
     * @return medida
     * Diseño: --> getMedida() --> Float
     **/
    // ---------------------------------------------------------------------------------------------
    public Float getMedida() {
        return medida;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene el nombre del dispositivo
     * @return nombre
     * Diseño: --> getNombre() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    public String getNombre() {
        return nombre;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene el correo del usuario al que pertenece el dispositivo
     * @return correousuario
     * Diseño: --> getCorreoUsuario() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    public String getCorreoUsuario() {
        return correoUsuario;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se da la medida
     * Diseño:Float --> setMedida()
     **/
    // ---------------------------------------------------------------------------------------------
    public void setMedida(Float medida) {
        this.medida = medida;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se da el nombre del dispositivo
     * Diseño:String --> setNombre()
     **/
    // ---------------------------------------------------------------------------------------------
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se da el correo del usuario
     * Diseño:String --> setCorreoUsuario()
     **/
    // ---------------------------------------------------------------------------------------------
    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }
}