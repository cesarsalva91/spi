package model;

import java.util.List;

public class Estudiante {
    private int id=0;
    private String nombre;
    private String apellido;
    private String matricula;
    private String contacto; // Email del tutor
    private List<String> materias; // Nombres o IDs de materias

    public Estudiante(int id, String nombre, String apellido, String matricula, String contacto,
            List<String> materias) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
        this.contacto = contacto;
        this.materias = materias;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getContacto() {
        return contacto;
    }

    public List<String> getMaterias() {
        return materias;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setMaterias(List<String> materias) {
        this.materias = materias;
    }
}
