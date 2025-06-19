package model;

import db.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Asistencia {
    private int id;
    private Date fecha;
    private String estado; // Ej: "Presente", "Ausente"
    private boolean justificada;
    private int idEstudiante;

    // Arreglo clásico para los posibles estados (cumple con el requisito del TP)
    public static final String[] ESTADOS_VALIDOS = { "Presente", "Ausente" };

    public Asistencia(Date fecha, String estado, boolean justificada, int idEstudiante) {
        this.fecha = fecha;
        this.estado = estado;
        this.justificada = justificada;
        this.idEstudiante = idEstudiante;
    }

    public Asistencia(int id, Date fecha, String estado, boolean justificada, int idEstudiante) {
        this(fecha, estado, justificada, idEstudiante);
        this.id = id;
    }

    // Getters y setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public boolean isJustificada() { return justificada; }
    public void setJustificada(boolean justificada) { this.justificada = justificada; }

    public int getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(int idEstudiante) { this.idEstudiante = idEstudiante; }

    public boolean guardarEnBD() {
        String sql = "INSERT INTO asistencia (fecha, estado, justificada, id_estudiante) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, fecha);
            stmt.setString(2, estado);
            stmt.setBoolean(3, justificada);
            stmt.setInt(4, idEstudiante);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar asistencia: " + e.getMessage());
            return false;
        }
    }

    public static List<Asistencia> obtenerPorEstudiante(int idEstudiante) {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM asistencia WHERE id_estudiante = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Asistencia a = new Asistencia(
                    rs.getInt("id_asistencia"),
                    rs.getDate("fecha"),
                    rs.getString("estado"),
                    rs.getBoolean("justificada"),
                    rs.getInt("id_estudiante")
                );
                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener asistencias: " + e.getMessage());
        }

        return lista;
    }
}
