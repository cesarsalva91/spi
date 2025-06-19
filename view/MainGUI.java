package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.List;
import javax.swing.*;
import model.Estudiante;
import service.SchoolService;

public class MainGUI extends JFrame {

    private SchoolService service;

    public MainGUI() {
        service = new SchoolService();
        initUI();
    }

    private void initUI() {
        setTitle("Gestión Escolar");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));

        JButton btnMostrarEstudiantes = new JButton("Mostrar Estudiantes");
        btnMostrarEstudiantes.addActionListener((ActionEvent e) -> mostrarEstudiantes());

        JButton btnAgregarEstudiante = new JButton("Agregar Estudiante");
        btnAgregarEstudiante.addActionListener((ActionEvent e) -> agregarEstudiante());

        JButton btnEliminarEstudiante = new JButton("Eliminar Estudiante");
        btnEliminarEstudiante.addActionListener((ActionEvent e) -> eliminarEstudiante());

        JButton btnRegistrarAsistencia = new JButton("Registrar Asistencia");
        btnRegistrarAsistencia.addActionListener((ActionEvent e) -> registrarAsistencia());

        panel.add(btnMostrarEstudiantes);
        panel.add(btnAgregarEstudiante);
        panel.add(btnEliminarEstudiante);
        panel.add(btnRegistrarAsistencia);

        add(panel, BorderLayout.CENTER);
    }

    private void mostrarEstudiantes() {
        List<Estudiante> estudiantes = service.obtenerEstudiantes();
        StringBuilder sb = new StringBuilder("Estudiantes registrados:\n");
        for (Estudiante e : estudiantes) {
            sb.append("ID: ").append(e.getId()).append(" - ")
              .append(e.getNombre()).append(" ").append(e.getApellido()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void agregarEstudiante() {
        String nombre = JOptionPane.showInputDialog("Nombre:");
        String apellido = JOptionPane.showInputDialog("Apellido:");
        String matriculaStr = JOptionPane.showInputDialog("Matrícula:");
        String contacto = JOptionPane.showInputDialog("Contacto:");

        if (nombre == null || apellido == null || matriculaStr == null || contacto == null ||
            nombre.isBlank() || apellido.isBlank() || matriculaStr.isBlank() || contacto.isBlank()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        try {
            int matricula = Integer.parseInt(matriculaStr);
            Estudiante estudiante = new Estudiante(nombre, apellido, matricula, contacto);
            if (service.registrarEstudiante(estudiante)) {
                JOptionPane.showMessageDialog(this, "Estudiante registrado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar estudiante.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Matrícula inválida.");
        }
    }

    private void eliminarEstudiante() {
        String idStr = JOptionPane.showInputDialog("ID del estudiante a eliminar:");
        try {
            int id = Integer.parseInt(idStr);
            Estudiante est = service.obtenerEstudiantes().stream()
                                    .filter(e -> e.getId() == id)
                                    .findFirst().orElse(null);
            if (est == null) {
                JOptionPane.showMessageDialog(this, "El estudiante no existe.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de eliminar al estudiante " + est.getNombre() + " " + est.getApellido() + "?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = service.eliminarEstudiante(id);
                JOptionPane.showMessageDialog(this, ok ? "Estudiante eliminado." : "Error al eliminar.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void registrarAsistencia() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField idField = new JTextField();
        JCheckBox chkPresente = new JCheckBox("Presente");
        JCheckBox chkJustificada = new JCheckBox("Justificada");

        panel.add(new JLabel("ID del estudiante:"));
        panel.add(idField);
        panel.add(chkPresente);
        panel.add(chkJustificada);

        int result = JOptionPane.showConfirmDialog(this, panel, "Registrar Asistencia", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                Estudiante est = service.obtenerEstudiantes().stream()
                                        .filter(e -> e.getId() == id)
                                        .findFirst().orElse(null);
                if (est == null) {
                    JOptionPane.showMessageDialog(this, "El estudiante no existe.");
                    return;
                }
                String estado = chkPresente.isSelected() ? "Presente" : "Ausente";
                boolean justificada = chkJustificada.isSelected();
                Date fechaHoy = new Date(System.currentTimeMillis());
                boolean ok = service.registrarAsistencia(id, fechaHoy, estado, justificada);
                JOptionPane.showMessageDialog(this, ok ? "Asistencia registrada." : "Error al registrar.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainGUI ex = new MainGUI();
            ex.setVisible(true);
        });
    }
}
