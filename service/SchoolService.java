package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Asistencia;
import model.Calificacion;
import model.Estudiante;
import model.Materia;
import model.Notificacion;

public class SchoolService {

    private final String archivoEstudiantes = "data/estudiantes.json";
    private final String archivoAsistencias = "data/asistencias.json";
    private final String archivoCalificaciones = "data/calificaciones.json";
    private final String archivoNotificaciones = "data/notificaciones.json";
    private final String archivoMaterias = "data/materias.json";

    private List<Estudiante> estudiantes = cargarEstudiantes();
    private List<Asistencia> asistencias = cargarAsistencias();
    private List<Calificacion> calificaciones = cargarCalificaciones();
    private List<Notificacion> notificaciones = cargarNotificaciones();
    private List<Materia> materias = cargarMaterias();

    // ------------------- ESTUDIANTES -------------------

    private List<Estudiante> cargarEstudiantes() {
        List<Estudiante> lista = new ArrayList<>();
        List<String> lineas = JsonDataReader.leerLineas(archivoEstudiantes);
        for (String linea : lineas) {
            Estudiante e = parsearEstudiante(linea);
            if (e != null) lista.add(e);
        }
        return lista;
    }

    private Estudiante parsearEstudiante(String json) {
        try {
            json = json.replace("{", "").replace("}", "").replace("\"", "");
            String[] partes = json.split(",");
            int id = 0;
            String nombre = null, apellido = null, matricula = null, contacto = null;
            for (String parte : partes) {
                String[] kv = parte.split(":");
                switch (kv[0].trim()) {
                    case "id" -> id = Integer.parseInt(kv[1].trim());
                    case "nombre" -> nombre = kv[1].trim();
                    case "apellido" -> apellido = kv[1].trim();
                    case "matricula" -> matricula = kv[1].trim();
                    case "contacto" -> contacto = kv[1].trim();
                }
            }
            return new Estudiante(id, nombre, apellido, matricula, contacto, null);
        } catch (Exception e) {
            System.out.println("Error parseando estudiante: " + e.getMessage());
            return null;
        }
    }

    private String serializarEstudiante(Estudiante e) {
        return String.format("{\"id\":%d,\"nombre\":\"%s\",\"apellido\":\"%s\",\"matricula\":\"%s\",\"contacto\":\"%s\"}",
                e.getId(), e.getNombre(), e.getApellido(), e.getMatricula(), e.getContacto());
    }

    public boolean agregarEstudiante(Estudiante e) {
        estudiantes.add(e);
        return guardarEstudiantes();
    }

    private boolean guardarEstudiantes() {
        List<String> serializados = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            serializados.add(serializarEstudiante(e));
        }
        return JsonDataWriter.guardarLineas(archivoEstudiantes, serializados);
    }

    public List<Estudiante> obtenerTodosLosEstudiantes() {
        return estudiantes;
    }

    public List<Estudiante> obtenerEstudiantesOrdenadosPorNombre() {
        return estudiantes.stream()
                .sorted(Comparator.comparing(Estudiante::getNombre))
                .toList();
    }

    // ------------------- ASISTENCIAS -------------------

    private List<Asistencia> cargarAsistencias() {
        List<Asistencia> lista = new ArrayList<>();
        List<String> lineas = JsonDataReader.leerLineas(archivoAsistencias);
        for (String linea : lineas) {
            Asistencia a = parsearAsistencia(linea);
            if (a != null) lista.add(a);
        }
        return lista;
    }

    private Asistencia parsearAsistencia(String json) {
        try {
            json = json.replace("{", "").replace("}", "").replace("\"", "");
            String[] partes = json.split(",");
            int id = 0;
            String materia = null, estado = null;
            LocalDate fecha = null;
            for (String parte : partes) {
                String[] kv = parte.split(":");
                switch (kv[0].trim()) {
                    case "idEstudiante" -> id = Integer.parseInt(kv[1].trim());
                    case "idMateria" -> materia = kv[1].trim();
                    case "fecha" -> fecha = LocalDate.parse(kv[1].trim());
                    case "estado" -> estado = kv[1].trim();
                }
            }
            return new Asistencia(id, materia, fecha, estado);
        } catch (Exception e) {
            System.out.println("Error parseando asistencia: " + e.getMessage());
            return null;
        }
    }

    private String serializarAsistencia(Asistencia a) {
        return String.format("{\"idEstudiante\":%d,\"idMateria\":\"%s\",\"fecha\":\"%s\",\"estado\":\"%s\"}",
                a.getIdEstudiante(), a.getIdMateria(), a.getFecha(), a.getEstado());
    }

    public boolean registrarAsistencia(Asistencia a) {
        asistencias.add(a);
        return guardarAsistencias();
    }

    private boolean guardarAsistencias() {
        List<String> serializados = new ArrayList<>();
        for (Asistencia a : asistencias) {
            serializados.add(serializarAsistencia(a));
        }
        return JsonDataWriter.guardarLineas(archivoAsistencias, serializados);
    }

    public List<Asistencia> obtenerAsistenciasPorEstudiante(int idEstudiante) {
        List<Asistencia> resultado = new ArrayList<>();
        for (Asistencia a : asistencias) {
            if (a.getIdEstudiante() == idEstudiante) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    // ------------------- CALIFICACIONES -------------------

    private List<Calificacion> cargarCalificaciones() {
        List<Calificacion> lista = new ArrayList<>();
        List<String> lineas = JsonDataReader.leerLineas(archivoCalificaciones);
        for (String linea : lineas) {
            Calificacion c = parsearCalificacion(linea);
            if (c != null) lista.add(c);
        }
        return lista;
    }

    private Calificacion parsearCalificacion(String json) {
        try {
            json = json.replace("{", "").replace("}", "").replace("\"", "");
            String[] partes = json.split(",");
            int id = 0;
            String materia = null;
            double nota = 0;
            for (String parte : partes) {
                String[] kv = parte.split(":");
                switch (kv[0].trim()) {
                    case "idEstudiante" -> id = Integer.parseInt(kv[1].trim());
                    case "idMateria" -> materia = kv[1].trim();
                    case "nota" -> nota = Double.parseDouble(kv[1].trim());
                }
            }
            return new Calificacion(id, materia, nota);
        } catch (Exception e) {
            System.out.println("Error parseando calificación: " + e.getMessage());
            return null;
        }
    }

    private String serializarCalificacion(Calificacion c) {
        return String.format("{\"idEstudiante\":%d,\"idMateria\":\"%s\",\"nota\":%.2f}",
                c.getIdEstudiante(), c.getIdMateria(), c.getNota());
    }

    public boolean registrarCalificacion(Calificacion c) {
        calificaciones.add(c);
        return guardarCalificaciones();
    }

    private boolean guardarCalificaciones() {
        List<String> serializados = new ArrayList<>();
        for (Calificacion c : calificaciones) {
            serializados.add(serializarCalificacion(c));
        }
        return JsonDataWriter.guardarLineas(archivoCalificaciones, serializados);
    }

    public List<Calificacion> obtenerCalificacionesPorEstudiante(int idEstudiante) {
        List<Calificacion> resultado = new ArrayList<>();
        for (Calificacion c : calificaciones) {
            if (c.getIdEstudiante() == idEstudiante) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    // ------------------- NOTIFICACIONES -------------------

    private List<Notificacion> cargarNotificaciones() {
        List<Notificacion> lista = new ArrayList<>();
        List<String> lineas = JsonDataReader.leerLineas(archivoNotificaciones);
        for (String linea : lineas) {
            Notificacion n = Notificacion.desdeJson(linea);
            if (n != null) lista.add(n);
        }
        return lista;
    }

    public boolean registrarNotificacion(Notificacion n) {
        notificaciones.add(n);
        return guardarNotificaciones();
    }

    private boolean guardarNotificaciones() {
        List<String> serializados = new ArrayList<>();
        for (Notificacion n : notificaciones) {
            serializados.add(n.aJson());
        }
        return JsonDataWriter.guardarLineas(archivoNotificaciones, serializados);
    }

    public List<Notificacion> obtenerNotificacionesPorEstudiante(int idEstudiante) {
        List<Notificacion> resultado = new ArrayList<>();
        for (Notificacion n : notificaciones) {
            if (n.getIdEstudiante() == idEstudiante) {
                resultado.add(n);
            }
        }
        return resultado;
    }

    // ------------------- MATERIAS -------------------

    private List<Materia> cargarMaterias() {
        List<Materia> lista = new ArrayList<>();
        List<String> lineas = JsonDataReader.leerLineas(archivoMaterias);
        for (String linea : lineas) {
            Materia m = parsearMateria(linea);
            if (m != null) lista.add(m);
        }
        return lista;
    }

    private Materia parsearMateria(String json) {
        try {
            json = json.replace("{", "").replace("}", "").replace("\"", "");
            String[] partes = json.split(",");
            String id = null, nombre = null;
            for (String parte : partes) {
                String[] kv = parte.split(":");
                switch (kv[0].trim()) {
                    case "id" -> id = kv[1].trim();
                    case "nombre" -> nombre = kv[1].trim();
                }
            }
            return new Materia(id, nombre);
        } catch (Exception e) {
            System.out.println("Error parseando materia: " + e.getMessage());
            return null;
        }
    }

    private String serializarMateria(Materia m) {
        return String.format("{\"id\":\"%s\",\"nombre\":\"%s\"}", m.getId(), m.getNombre());
    }

    public boolean registrarMateria(Materia m) {
        materias.add(m);
        return guardarMaterias();
    }

    private boolean guardarMaterias() {
        List<String> serializados = new ArrayList<>();
        for (Materia m : materias) {
            serializados.add(serializarMateria(m));
        }
        return JsonDataWriter.guardarLineas(archivoMaterias, serializados);
    }

    public List<Materia> obtenerTodasLasMaterias() {
        return materias;
    }
}
