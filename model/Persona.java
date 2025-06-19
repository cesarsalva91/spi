// model/Persona.java
package model;

public abstract class Persona {
    protected int id;
    protected String nombre;
    protected String apellido;
    protected String contacto;

    public Persona(int id, String nombre, String apellido, String contacto) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contacto = contacto;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public abstract String getIdentificacion();
}
