package service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonDataReader {

    public static List<String> leerLineas(String rutaArchivo) {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea.trim());
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lineas;
    }

    public static String leerCompleto(String rutaArchivo) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return sb.toString();
    }
}
