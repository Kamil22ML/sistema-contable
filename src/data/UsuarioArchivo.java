package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import model.Usuario;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class UsuarioArchivo {

    // Ruta relativa al proyecto
    private static final String RUTA = "data/Usuarios.txt";

    public static Usuario buscarPorLogin(String loginBuscado) {
        File file = new File(RUTA);
        if (!file.exists()) {
            System.out.println("No se encuentra el archivo de usuarios: " + file.getAbsolutePath());
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                Usuario u = Usuario.fromLine(linea);
                if (u != null && u.getLogin().equalsIgnoreCase(loginBuscado)) {
                    return u;
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo Usuarios.txt: " + e.getMessage());
        }
        return null;
    }
    // Cargar todos los usuarios del archivo
    public static List<model.Usuario> cargarTodos() {
        List<model.Usuario> lista = new ArrayList<>();
        File file = new File(RUTA);

        if (!file.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                model.Usuario u = model.Usuario.fromLine(linea);
                if (u != null) lista.add(u);
            }
        } catch (IOException e) {
            System.out.println("Error leyendo Usuarios.txt: " + e.getMessage());
        }
        return lista;
    }

    // Guardar lista completa en el archivo (sobreescribe)
    private static void guardarLista(List<model.Usuario> lista) {
        File file = new File(RUTA);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (model.Usuario u : lista) {
                bw.write(u.toLine());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo Usuarios.txt: " + e.getMessage());
        }
    }

    // Agregar nuevo usuario (si no existe el login)
    public static boolean agregar(model.Usuario nuevo) {
        List<model.Usuario> lista = cargarTodos();

        for (model.Usuario u : lista) {
            if (u.getLogin().equalsIgnoreCase(nuevo.getLogin())) {
                return false;  // ya existe
            }
        }

        lista.add(nuevo);
        guardarLista(lista);
        return true;
    }

    // Actualizar usuario existente (mismo login)
    public static boolean actualizar(model.Usuario modificado) {
        List<model.Usuario> lista = cargarTodos();
        boolean encontrado = false;

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getLogin().equalsIgnoreCase(modificado.getLogin())) {
                lista.set(i, modificado);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            guardarLista(lista);
        }
        return encontrado;
    }

}
