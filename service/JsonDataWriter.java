package service;

import java.io.*;
import java.util.List;

public class JsonDataWriter {

    public static boolean guardarTexto(String rutaArchivo, String contenido) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            bw.write(contenido);
            return true;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public static boolean guardarLineas(String rutaArchivo, List<String> lineas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
