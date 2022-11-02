package ctorcru.upv.techcommit_3a.Modelo;

public class Usuario {
    private int id;
    private String Nombre;
    private String Correo;
    private String Contraseña;

    public Usuario(String nombre, String correo, String contraseña) {
        Nombre = nombre;
        Correo = correo;
        Contraseña = contraseña;
    }
    public Usuario(String correo, String contraseña) {
        Correo = correo;
        Contraseña = contraseña;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", Nombre='" + Nombre + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Contraseña='" + Contraseña + '\'' +
                '}';
    }
}
