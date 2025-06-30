package model;

import db.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Calificacion {
    private int id;
    private float nota;
    private int idEstudiante;
    private int idMateria;

    // Arreglo
    public static final float[] ESCALAS_POSIBLES = { 1.0f, 2.0f, 4.0f, 6.0f, 8.0f, 10.0f };

    public Calificacion(float nota, int idEstudiante, int idMateria) {
        this.nota = nota;
        this.idEstudiante = idEstudiante;
        this.idMateria = idMateria;
    }

    public Calificacion(int id, float nota, int idEstudiante, int idMateria) {
        this(nota, idEstudiante, idMateria);
        this.id = id;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public boolean guardarEnBD() {
        String sql = "INSERT INTO calificacion (nota, id_estudiante, id_materia) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setFloat(1, nota);
            stmt.setInt(2, idEstudiante);
            stmt.setInt(3, idMateria);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar calificación: " + e.getMessage());
            return false;
        }
    }

    public static List<Calificacion> obtenerPorEstudiante(int idEstudiante) {
        List<Calificacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM calificacion WHERE id_estudiante = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Calificacion c = new Calificacion(
                    rs.getInt("id_calificacion"),
                    rs.getFloat("nota"),
                    rs.getInt("id_estudiante"),
                    rs.getInt("id_materia")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener calificaciones: " + e.getMessage());
        }

        return lista;
    }
}
