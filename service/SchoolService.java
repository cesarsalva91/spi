package service;

import java.sql.Date;
import java.util.List;
import model.Asistencia;
import model.Calificacion;
import model.Estudiante;
import model.Materia;
import model.Notificacion;

public class SchoolService {

    // === ESTUDIANTES ===

    public List<Estudiante> obtenerEstudiantes() {
        return Estudiante.obtenerTodos();
    }

    public Estudiante buscarEstudiantePorMatricula(int matricula) {
        return Estudiante.buscarPorMatricula(matricula);
    }

    public boolean registrarEstudiante(Estudiante estudiante) {
        System.out.println("Registrando estudiante: " + estudiante.getIdentificacion());
        return estudiante.guardarEnBD();
    }

    public boolean eliminarEstudiante(int idEstudiante) {
        return Estudiante.eliminarPorId(idEstudiante);
    }

    // === MATERIAS ===

    public List<Materia> obtenerMaterias() {
        return Materia.obtenerTodas();
    }

    public boolean registrarMateria(Materia materia) {
        return materia.guardarEnBD();
    }

    // === ASISTENCIA ===

    public boolean registrarAsistencia(int idEstudiante, Date fecha, String estado, boolean justificada) {
        Asistencia asistencia = new Asistencia(fecha, estado, justificada, idEstudiante);
        return asistencia.guardarEnBD();
    }

    public List<Asistencia> obtenerAsistenciasPorEstudiante(int idEstudiante) {
        return Asistencia.obtenerPorEstudiante(idEstudiante);
    }

    // === CALIFICACIONES ===

    public boolean registrarCalificacion(float nota, int idEstudiante, int idMateria) {
        Calificacion calificacion = new Calificacion(nota, idEstudiante, idMateria);
        return calificacion.guardarEnBD();
    }

    public List<Calificacion> obtenerCalificacionesPorEstudiante(int idEstudiante) {
        return Calificacion.obtenerPorEstudiante(idEstudiante);
    }

    // === NOTIFICACIONES ===

    public boolean enviarNotificacion(String mensaje, int idEstudiante) {
        Notificacion notificacion = new Notificacion(mensaje, idEstudiante);
        return notificacion.guardarEnBD();
    }

    public List<Notificacion> obtenerNotificacionesPorEstudiante(int idEstudiante) {
        return Notificacion.obtenerPorEstudiante(idEstudiante);
    }
}
