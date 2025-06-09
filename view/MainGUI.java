package view;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import model.*;
import service.SchoolService;

public class MainGUI extends JFrame {
    private final SchoolService schoolService;
    private JTextArea outputArea;

    public MainGUI() {
        super("Classroom Manager");
        this.schoolService = new SchoolService();
        configurarGUI();
    }

    private void configurarGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel(new GridLayout(4, 2));

        JButton btnAgregarEstudiante = new JButton("Agregar Estudiante");
        JButton btnRegistrarAsistencia = new JButton("Registrar Asistencia");
        JButton btnRegistrarCalificacion = new JButton("Registrar Calificacion");
        JButton btnRegistrarNotificacion = new JButton("Registrar Notificación");
        JButton btnVerEstudiantes = new JButton("Ver Estudiantes");
        JButton btnVerAsistencias = new JButton("Ver Asistencias");
        JButton btnVerCalificaciones = new JButton("Ver Calificaciones");
        JButton btnVerNotificaciones = new JButton("Ver Notificaciones");

        panelBotones.add(btnAgregarEstudiante);
        panelBotones.add(btnRegistrarAsistencia);
        panelBotones.add(btnRegistrarCalificacion);
        panelBotones.add(btnRegistrarNotificacion);
        panelBotones.add(btnVerEstudiantes);
        panelBotones.add(btnVerAsistencias);
        panelBotones.add(btnVerCalificaciones);
        panelBotones.add(btnVerNotificaciones);

        add(panelBotones, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        btnAgregarEstudiante.addActionListener(e -> agregarEstudiante());
        btnRegistrarAsistencia.addActionListener(e -> registrarAsistencia());
        btnRegistrarCalificacion.addActionListener(e -> registrarCalificacion());
        btnRegistrarNotificacion.addActionListener(e -> registrarNotificacion());
        btnVerEstudiantes.addActionListener(e -> mostrarEstudiantes());
        btnVerAsistencias.addActionListener(e -> mostrarAsistencias());
        btnVerCalificaciones.addActionListener(e -> mostrarCalificaciones());
        btnVerNotificaciones.addActionListener(e -> mostrarNotificaciones());

        setVisible(true);
    }

    private void agregarEstudiante() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del estudiante:");
        String apellido = JOptionPane.showInputDialog(this, "Apellido del estudiante:");
        String matricula = JOptionPane.showInputDialog(this, "Matrícula:");
        String contacto = JOptionPane.showInputDialog(this, "Email de contacto:");

        Estudiante nuevo = new Estudiante(0, nombre, apellido, matricula, contacto, null);
        boolean resultado = schoolService.agregarEstudiante(nuevo);

        outputArea.setText(resultado ? "Estudiante agregado con éxito." : "Error al agregar estudiante.");
    }

    private void registrarAsistencia() {
        int id = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del estudiante:"));
        String materia = JOptionPane.showInputDialog(this, "ID de la materia:");
        String estado = JOptionPane.showInputDialog(this, "Estado (Presente/Ausente):");

        Asistencia nueva = new Asistencia(id, materia, LocalDate.now(), estado);
        boolean resultado = schoolService.registrarAsistencia(nueva);

        outputArea.setText(resultado ? "Asistencia registrada." : "Error al registrar asistencia.");
    }

    private void registrarCalificacion() {
        int id = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del estudiante:"));
        String materia = JOptionPane.showInputDialog(this, "ID de la materia:");
        double nota = Double.parseDouble(JOptionPane.showInputDialog(this, "Nota (0-10):"));

        Calificacion nueva = new Calificacion(id, materia, nota);
        boolean resultado = schoolService.registrarCalificacion(nueva);

        outputArea.setText(resultado ? "Calificación registrada." : "Error al registrar calificación.");
    }

    private void registrarNotificacion() {
        int id = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del estudiante:"));
        String mensaje = JOptionPane.showInputDialog(this, "Mensaje:");

        Notificacion n = new Notificacion(id, mensaje, LocalDate.now());
        boolean resultado = schoolService.registrarNotificacion(n);

        outputArea.setText(resultado ? "Notificación registrada." : "Error al registrar notificación.");
    }

    private void mostrarEstudiantes() {
        List<Estudiante> lista = schoolService.obtenerEstudiantesOrdenadosPorNombre();
        StringBuilder sb = new StringBuilder("Lista de estudiantes:\n");
        for (Estudiante e : lista) {
            sb.append(e.getId()).append(" - ")
              .append(e.getNombre()).append(" ")
              .append(e.getApellido()).append(" | Matricula: ")
              .append(e.getMatricula()).append("\n");
        }
        outputArea.setText(sb.toString());
    }

    private void mostrarAsistencias() {
        int id = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del estudiante:"));
        List<Asistencia> lista = schoolService.obtenerAsistenciasPorEstudiante(id);
        StringBuilder sb = new StringBuilder("Asistencias del estudiante:\n");
        for (Asistencia a : lista) {
            sb.append(a.getFecha()).append(" - ").append(a.getIdMateria()).append(" - ").append(a.getEstado()).append("\n");
        }
        outputArea.setText(sb.toString());
    }

    private void mostrarCalificaciones() {
        int id = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del estudiante:"));
        List<Calificacion> lista = schoolService.obtenerCalificacionesPorEstudiante(id);
        StringBuilder sb = new StringBuilder("Calificaciones del estudiante:\n");
        for (Calificacion c : lista) {
            sb.append(c.getIdMateria()).append(" - Nota: ").append(c.getNota()).append("\n");
        }
        outputArea.setText(sb.toString());
    }

    private void mostrarNotificaciones() {
        int id = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del estudiante:"));
        List<Notificacion> lista = schoolService.obtenerNotificacionesPorEstudiante(id);
        StringBuilder sb = new StringBuilder("Notificaciones:\n");
        for (Notificacion n : lista) {
            sb.append(n.getFecha()).append(" - ").append(n.getMensaje()).append("\n");
        }
        outputArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
