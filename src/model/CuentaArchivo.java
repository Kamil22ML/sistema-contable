package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.CuentaContable;

public class CuentaArchivo {

    private static final String RUTA = "data/CatalogoCuentas.txt";

    // Buscar una cuenta por su número
    public static CuentaContable buscarPorNumero(String numeroBuscado) {
        File file = new File(RUTA);
        if (!file.exists()) {
            System.out.println("No se encuentra CatalogoCuentas.txt: " + file.getAbsolutePath());
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                CuentaContable c = CuentaContable.fromLine(linea);
                if (c != null && c.getNumero().equalsIgnoreCase(numeroBuscado)) {
                    return c;
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo CatalogoCuentas.txt: " + e.getMessage());
        }
        return null;
    }

    // Cargar todas las cuentas
    public static List<CuentaContable> cargarTodas() {
        List<CuentaContable> lista = new ArrayList<>();
        File file = new File(RUTA);

        if (!file.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                CuentaContable c = CuentaContable.fromLine(linea);
                if (c != null) lista.add(c);
            }
        } catch (IOException e) {
            System.out.println("Error leyendo CatalogoCuentas.txt: " + e.getMessage());
        }
        return lista;
    }

    // Guardar lista completa (sobrescribe archivo)
    private static void guardarLista(List<CuentaContable> lista) {
        File file = new File(RUTA);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (CuentaContable c : lista) {
                bw.write(c.toLine());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo CatalogoCuentas.txt: " + e.getMessage());
        }
    }

    // Agregar nueva cuenta (si no existe el número)
    public static boolean agregar(CuentaContable nueva) {
        List<CuentaContable> lista = cargarTodas();

        for (CuentaContable c : lista) {
            if (c.getNumero().equalsIgnoreCase(nueva.getNumero())) {
                return false; // ya existe
            }
        }
        lista.add(nueva);
        guardarLista(lista);
        return true;
    }

    // Actualizar cuenta existente
    public static boolean actualizar(CuentaContable modificada) {
        List<CuentaContable> lista = cargarTodas();
        boolean encontrada = false;

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNumero().equalsIgnoreCase(modificada.getNumero())) {
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
}
