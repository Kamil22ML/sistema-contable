package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.DetalleTransaccion;

public class DetalleArchivo {

    private static final String RUTA = "data/Transacciones.txt";

    // Cargar todos los detalles de un documento específico
    public static List<DetalleTransaccion> cargarPorDocumento(String nroDocBuscado) {
        List<DetalleTransaccion> lista = new ArrayList<>();
        File file = new File(RUTA);

        if (!file.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                DetalleTransaccion d = DetalleTransaccion.fromLine(linea);
                if (d != null && d.getNroDoc().equalsIgnoreCase(nroDocBuscado)) {
                    lista.add(d);
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo Transacciones.txt: " + e.getMessage());
        }
        return lista;
    }

    // Cargar TODOS los detalles (por si lo necesitas luego)
    public static List<DetalleTransaccion> cargarTodos() {
        List<DetalleTransaccion> lista = new ArrayList<>();
        File file = new File(RUTA);

        if (!file.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                DetalleTransaccion d = DetalleTransaccion.fromLine(linea);
                if (d != null) lista.add(d);
            }
        } catch (IOException e) {
            System.out.println("Error leyendo Transacciones.txt: " + e.getMessage());
        }
        return lista;
    }

    // Agregar una línea de detalle (append al archivo)
    public static boolean agregar(DetalleTransaccion nuevo) {
        File file = new File(RUTA);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(nuevo.toLine());
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Error escribiendo Transacciones.txt: " + e.getMessage());
            return false;
        }
    }
}
