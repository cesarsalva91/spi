package model;

import java.time.LocalDate;

public class Asistencia {
    private int idEstudiante=0;
    private String idMateria=null;
    private LocalDate fecha=null;
    private String estado; // "Presente", "Ausente", "Justificada"

    public Asistencia(int idEstudiante, String idMateria, LocalDate fecha, String estado) {
        this.idEstudiante = idEstudiante;
        this.idMateria = idMateria;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getIdEstudiante() { return idEstudiante; }
    public String getIdMateria() { return idMateria; }
    public LocalDate getFecha() { return fecha; }
    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }
}
