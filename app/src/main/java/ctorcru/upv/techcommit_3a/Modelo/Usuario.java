package ctorcru.upv.techcommit_3a.Modelo;

public class Usuario {
    private String Id;
    private String Nombre;
    private String Correo;
    private String Contrasena;

    public Usuario(String id_,String nombre, String correo, String contrasena) {
        Id = id_;
        Nombre = nombre;
        Correo = correo;
        Contrasena = contrasena;
    }
    public Usuario(String correo, String contrasena) {
        Correo = correo;
        Contrasena = contrasena;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
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

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "Id=" + Id +
                ", Nombre='" + Nombre + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Contrasena='" + Contrasena + '\'' +
                '}';
    }
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
