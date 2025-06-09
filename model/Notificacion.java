package model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Notificacion {

    private int idEstudiante;
    private String mensaje;
    private LocalDate fecha;

    public Notificacion() {}

    public Notificacion(int idEstudiante, String mensaje, LocalDate fecha) {
        this.idEstudiante = idEstudiante;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public static Notificacion desdeJson(String jsonLinea) {
        try {
            jsonLinea = jsonLinea.replace("{", "").replace("}", "").replace("\"", "");
            String[] partes = jsonLinea.split(",");

            int idEst = 0;
            String mensaje = null;
            LocalDate fecha = null;

            for (String parte : partes) {
                String[] kv = parte.split(":");
                if (kv.length != 2) continue;
                String clave = kv[0].trim();
                String valor = kv[1].trim();

                switch (clave) {
                    case "idEstudiante" -> idEst = Integer.parseInt(valor);
                    case "mensaje" -> mensaje = valor;
                    case "fecha" -> fecha = LocalDate.parse(valor);
                }
            }

            return new Notificacion(idEst, mensaje, fecha);
        } catch (NumberFormatException | DateTimeParseException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error al parsear notificación: " + e.getMessage());
            return null;
        }
    }

    public String aJson() {
        return String.format("{\"idEstudiante\":%d,\"mensaje\":\"%s\",\"fecha\":\"%s\"}",
                idEstudiante, mensaje, fecha);
    }
}
