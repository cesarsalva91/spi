import db.ConexionDB;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try (Connection conexion = ConexionDB.conectar()) {
            if (conexion != null) {
                System.out.println("Conexión exitosa a la base de datos.");
            } else {
                System.out.println("La conexión es nula. Verificá la configuración.");
            }
        } catch (Exception e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }
}
