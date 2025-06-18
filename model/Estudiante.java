package model;

import db.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Estudiante {
    private int id;
    private String nombre;
    private String apellido;
    private int matricula;
    private String contacto;

    public Estudiante(String nombre, String apellido, int matricula, String contacto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
        this.contacto = contacto;
    }

    public Estudiante(int id, String nombre, String apellido, int matricula, String contacto) {
        this(nombre, apellido, matricula, contacto);
        this.id = id;
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

    public int getMatricula() {
        return matricula;
    }

    public String getContacto() {
        return contacto;
    }

    public boolean guardarEnBD() {
        String sql = "INSERT INTO estudiante (nombre, apellido, matricula, contacto) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setInt(3, matricula);
            stmt.setString(4, contacto);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar estudiante: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarDeBD() {
        String sql = "DELETE FROM estudiante WHERE id_estudiante = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar estudiante: " + e.getMessage());
            return false;
        }
    }

    public static List<Estudiante> obtenerTodos() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiante";

        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Estudiante e = new Estudiante(
                    rs.getInt("id_estudiante"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("matricula"),
                    rs.getString("contacto")
                );
                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener estudiantes: " + e.getMessage());
        }
        return lista;
    }

    public static Estudiante buscarPorMatricula(int matricula) {
        String sql = "SELECT * FROM estudiante WHERE matricula = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
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
}
