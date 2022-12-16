package ctorcru.upv.techcommit_3a.Modelo;

import java.text.SimpleDateFormat;
import java.util.Date;
// ---------------------------------------------------------------------------------------------
/**
 * @brief Esta clase se encarga de montar el objeto que se subirá a la base de datos
 * Autora: Claudia Torres Cruz
 * Archivo: Medicion.java
 **/
// ---------------------------------------------------------------------------------------------
public class Medicion {
    // Atributos
    private String medida;
    private String fecha;



    private String latitud;
    private String longitud;
    private String dispositivo;



    public Medicion(String medida, String latitud, String longitud, String dispositivo) {
        this.medida = medida;
        this.fecha = fechaDB();
        this.latitud = latitud;
        this.longitud = longitud;
        this.dispositivo = dispositivo;
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Constructor de la clase
     * @param medida
     * @return objeto Medicion
     * Diseño: int --> Medicion() --> Medicion
     **/
    // ---------------------------------------------------------------------------------------------
    public Medicion(int medida) {
        this.medida =String.valueOf(medida);
        this.fecha = fechaDB();

    } //()
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene la medida
     * @return medida
     * Diseño: --> getMedida() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    public String getMedida() {
        return medida;
    } //()
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se obtiene la fecha
     * @return fecha
     * Diseño: --> getFecha() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    public String getFecha() {
        return fecha;
    } //()
    // ---------------------------------------------------------------------------------------------
    /**
     * @brief Con este método se establece la fecha a la hora de crear el objeto
     * @return fechaActual
     * Diseño: --> fechaDB() --> String
     **/
    // ---------------------------------------------------------------------------------------------
    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String toJSON(){
        String res = "{" +

                "\"medida\":\""+this.getMedida()+"\", " +
                "\"fecha\":\""+this.getFecha()+"\", "+
                "\"latitud\":\""+this.getLatitud()+"\", "+
                "\"longitud\":\""+this.getLongitud()+"\", "+
                "\"dispositivo\":\""+this.getDispositivo()+"\""+
                "}";
        return res;
    }

    // ---------------------------------------------------------------------------------------------
    private String fechaDB(){
        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaActual = formato.format(fecha);
        return fechaActual;
    } //()
} //()
