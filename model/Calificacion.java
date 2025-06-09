package model;

public class Calificacion {
    private int idEstudiante=0;
    private String idMateria=null;
    private double nota;

    public Calificacion(int idEstudiante, String idMateria, double nota) {
        this.idEstudiante = idEstudiante;
        this.idMateria = idMateria;
        this.nota = nota;
    }

    public int getIdEstudiante() { return idEstudiante; }
    public String getIdMateria() { return idMateria; }
    public double getNota() { return nota; }

    public void setNota(double nota) { this.nota = nota; }
}
