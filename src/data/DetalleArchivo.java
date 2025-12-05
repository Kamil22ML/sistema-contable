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
    
   // 👉 Agregar una línea nueva (append al archivo)
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

    // 👉 Guardar TODA la lista (sobrescribe el archivo)
    private static void guardarLista(List<DetalleTransaccion> lista) {
        File file = new File(RUTA);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (DetalleTransaccion d : lista) {
                bw.write(d.toLine());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo Transacciones.txt: " + e.getMessage());
        }
    }

    // 👉 Actualizar una línea existente (mismo nroDoc + secuencia)
    public static boolean actualizar(DetalleTransaccion modificado) {
        List<DetalleTransaccion> todos = cargarTodos();
        boolean encontrado = false;

        for (int i = 0; i < todos.size(); i++) {
            DetalleTransaccion d = todos.get(i);
            if (d.getNroDoc().equalsIgnoreCase(modificado.getNroDoc())
                    && d.getSecuencia() == modificado.getSecuencia()) {
                todos.set(i, modificado);
                encontrado = true;
                break;
            }
        }
        if (encontrado) {
            guardarLista(todos);   // ✅ ahora sí recibe una List<DetalleTransaccion>
        }
        return encontrado;
    }
}
