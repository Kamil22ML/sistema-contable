/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author kamil
 */
public class Sesion {
        public static model.Usuario usuarioActual = null;

        public static String nombreUsuario() {
            if (usuarioActual == null) return "";
            // Ajusta si tu Usuario tiene otros getters
            return usuarioActual.getNombre() + " " + usuarioActual.getApellidos();
        }

        public static String loginUsuario() {
            if (usuarioActual == null) return "";
            return usuarioActual.getLogin();
        }
}

