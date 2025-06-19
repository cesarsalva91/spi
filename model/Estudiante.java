package model;

import db.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Persona {
    private int matricula;

    public Estudiante(int id, String nombre, String apellido, int matricula, String contacto) {
        super(id, nombre, apellido, contacto);
        this.matricula = matricula;
    }

    public Estudiante(String nombre, String apellido, int matricula, String contacto) {
    super(0, nombre, apellido, contacto); // ID se ignora al momento de creación
    this.matricula = matricula;
}


    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    @Override
    public String getIdentificacion() {
        return "Matrícula: " + matricula;
    }

    public boolean guardarEnBD() {
        String sql = "INSERT INTO estudiante (nombre, apellido, matricula, contacto) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setInt(3, matricula);
            stmt.setString(4, contacto);

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al guardar estudiante: " + e.getMessage());
            return false;
        }
    }

    public static boolean eliminarPorId(int idEstudiante) {
        String sql = "DELETE FROM estudiante WHERE id_estudiante = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar estudiante: " + e.getMessage());
            return false;
        }
    }

    public static Estudiante buscarPorMatricula(int matricula) {
        String sql = "SELECT * FROM estudiante WHERE matricula = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Estudiante(
                    rs.getInt("id_estudiante"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("matricula"),
                    rs.getString("contacto")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar estudiante: " + e.getMessage());
        }
        return null;
    }

    public static List<Estudiante> obtenerTodos() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiante";

        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Estudiante estudiante = new Estudiante(
                    rs.getInt("id_estudiante"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("matricula"),
                    rs.getString("contacto")
                );
                lista.add(estudiante);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener estudiantes: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (Matrícula: " + matricula + ")";
    }
}
