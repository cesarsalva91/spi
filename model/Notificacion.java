package model;

import db.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Notificacion {
    private int id;
    private String mensaje;
    private Timestamp fechaEnvio;
    private int idEstudiante;

    // === Constructores ===
    public Notificacion(String mensaje, int idEstudiante) {
        this.mensaje = mensaje;
        this.idEstudiante = idEstudiante;
    }

    public Notificacion(int id, String mensaje, Timestamp fechaEnvio, int idEstudiante) {
        this(mensaje, idEstudiante);
        this.id = id;
        this.fechaEnvio = fechaEnvio;
    }

    // === Getters y Setters ===
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Timestamp getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Timestamp fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    // === Persistencia ===
    public boolean guardarEnBD() {
        String sql = "INSERT INTO notificacion (mensaje, id_estudiante) VALUES (?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mensaje);
            stmt.setInt(2, idEstudiante);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar notificación: " + e.getMessage());
            return false;
        }
    }

    public static List<Notificacion> obtenerPorEstudiante(int idEstudiante) {
        List<Notificacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM notificacion WHERE id_estudiante = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Notificacion n = new Notificacion(
                    rs.getInt("id_notificacion"),
                    rs.getString("mensaje"),
                    rs.getTimestamp("fecha_envio"),
                    rs.getInt("id_estudiante")
                );
                lista.add(n);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener notificaciones: " + e.getMessage());
        }

        return lista;
    }
}
