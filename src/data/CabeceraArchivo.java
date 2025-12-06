package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.CabeceraTransaccion;

public class CabeceraArchivo {

    private static final String RUTA = "data/CabeceraTransacciones.txt";

    // Buscar una cabecera por número de documento
    public static CabeceraTransaccion buscarPorNumero(String nroDocuBuscado) {
        File file = new File(RUTA);
        if (!file.exists()) {
            System.out.println("No se encuentra CabeceraTransacciones.txt: " + file.getAbsolutePath());
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                CabeceraTransaccion c = CabeceraTransaccion.fromLine(linea);
                if (c != null && c.getNroDocu().equalsIgnoreCase(nroDocuBuscado)) {
                    return c;
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo CabeceraTransacciones.txt: " + e.getMessage());
        }
        return null;
    }

    // Cargar todas las cabeceras
    public static List<CabeceraTransaccion> cargarTodas() {
        List<CabeceraTransaccion> lista = new ArrayList<>();
        File file = new File(RUTA);

        if (!file.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                CabeceraTransaccion c = CabeceraTransaccion.fromLine(linea);
                if (c != null) lista.add(c);
            }
        } catch (IOException e) {
            System.out.println("Error leyendo CabeceraTransacciones.txt: " + e.getMessage());
        }
        return lista;
    }

    // Guardar lista completa (sobreescribe archivo)
    private static void guardarLista(List<CabeceraTransaccion> lista) {
        File file = new File(RUTA);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (CabeceraTransaccion c : lista) {
                bw.write(c.toLine());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo CabeceraTransacciones.txt: " + e.getMessage());
        }
    }

    // Agregar nueva cabecera (si no existe el número)
    public static boolean agregar(CabeceraTransaccion nueva) {
        List<CabeceraTransaccion> lista = cargarTodas();

        for (CabeceraTransaccion c : lista) {
            if (c.getNroDocu().equalsIgnoreCase(nueva.getNroDocu())) {
                return false; // ya existe
            }
        }

        lista.add(nueva);
        guardarLista(lista);
        return true;
    }

    // Actualizar cabecera existente (mismo nroDocu)
    public static boolean actualizar(CabeceraTransaccion modificada) {
        List<CabeceraTransaccion> lista = cargarTodas();
        boolean encontrada = false;

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNroDocu().equalsIgnoreCase(modificada.getNroDocu())) {
                lista.set(i, modificada);
                encontrada = true;
                break;
            }
        }

        if (encontrada) {
            guardarLista(lista);
        }
        return encontrada;
    }
    
    public static List<CabeceraTransaccion> cargarTodos() {
        List<CabeceraTransaccion> lista = new ArrayList<>();
        File file = new File(RUTA);
        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                CabeceraTransaccion c = CabeceraTransaccion.fromLine(linea);
                if (c != null) lista.add(c);
            }
        } catch (IOException e) {
            System.out.println("Error leyendo CabeceraTransacciones.txt: " + e.getMessage());
        }
        return lista;
    }

    public static List<CabeceraTransaccion> cargarPorFecha(String fecha) {
        List<CabeceraTransaccion> lista = new ArrayList<>();
        for (CabeceraTransaccion c : cargarTodos()) {
            if (fecha.equals(c.getFechaDocu())) {
                lista.add(c);
            }
        }
        return lista;
    }

    public static List<CabeceraTransaccion> cargarPorRango(String desde, String hasta) {
        List<CabeceraTransaccion> lista = new ArrayList<>();
        // asumiendo formato dd/MM/yyyy
        java.time.format.DateTimeFormatter f = java.time.format.DateTimeFormatter.ofPattern("d/M/yyyy");
        java.time.LocalDate d1 = java.time.LocalDate.parse(desde, f);
        java.time.LocalDate d2 = java.time.LocalDate.parse(hasta, f);

        for (CabeceraTransaccion c : cargarTodos()) {
            java.time.LocalDate dCab = java.time.LocalDate.parse(c.getFechaDocu(), f);
            if (!dCab.isBefore(d1) && !dCab.isAfter(d2)) {
                lista.add(c);
            }
        }
        return lista;
    }

    public static List<CabeceraTransaccion> cargarPorTipo(int tipoDoc) {
        List<CabeceraTransaccion> lista = new ArrayList<>();
        for (CabeceraTransaccion c : cargarTodos()) {
            if (c.getTipoDocu() == tipoDoc) {
                lista.add(c);
            }
        }
        return lista;
    }

}
