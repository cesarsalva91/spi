import db.ConexionDB;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try (Connection conexion = ConexionDB.obtenerConexion()) {
            if (conexion != null) {
                System.out.println("Conexión exitosa a la base de datos.");
            }
        } catch (Exception e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }
}
