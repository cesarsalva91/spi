package model;

import db.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Materia {
    private int id;
    private String nombre;
    private String descripcion;

    public Materia(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Materia(int id, String nombre, String descripcion) {
        this(nombre, descripcion);
        this.id = id;
    }

    // Getters y setters omitidos por brevedad

    public boolean guardarEnBD() {
        String sql = "INSERT INTO materia (nombre, descripcion) VALUES (?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar materia: " + e.getMessage());
            return false;
        }
    }

    public static List<Materia> obtenerTodas() {
        List<Materia> lista = new ArrayList<>();
        String sql = "SELECT * FROM materia";

        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Materia m = new Materia(
                    rs.getInt("id_materia"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                lista.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener materias: " + e.getMessage());
        }
        return lista;
    }

    public static Materia buscarPorNombre(String nombreBuscado) {
        String sql = "SELECT * FROM materia WHERE nombre = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreBuscado);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Materia(
                    rs.getInt("id_materia"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar materia: " + e.getMessage());
        }
        return null;
    }
}
