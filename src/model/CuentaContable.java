package model;

public class CuentaContable {
    private String numero;
    private String descripcion;
    private String tipo;      // G = General, D = Detalle
    private int nivel;
    private String padre;
    private String grupo;

    public CuentaContable(String numero, String descripcion, String tipo,
                          int nivel, String padre, String grupo) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.nivel = nivel;
        this.padre = padre;
        this.grupo = grupo;
    }

    public String getNumero() { return numero; }
    public String getDescripcion() { return descripcion; }
    public String getTipo() { return tipo; }
    public int getNivel() { return nivel; }
    public String getPadre() { return padre; }
    public String getGrupo() { return grupo; }

    public static CuentaContable fromLine(String line) {
        String[] parts = line.split("\\|");

        // Línea vacía o demasiado corta
        if (parts.length < 6) return null;

        // Si es la línea de encabezados, la ignoramos
        if (parts[0].trim().equalsIgnoreCase("NO. CUENTA")) {
            return null;
        }

        // parts[0] = NO. CUENTA
        // parts[1] = NOMBRE
        // parts[2] = TIPO (GENERAL O DETALLE)
        // parts[3] = NIVEL
        // parts[4] = PADRE
        // parts[5] = GRUPO
        // parts[6] = CONCILIABLE (lo ignoramos)
        // parts[7] = GRUPO DEL FLUJO DE EFECTIVO (lo ignoramos)

        int nivel;
        try {
            nivel = Integer.parseInt(parts[3].trim());
        } catch (NumberFormatException e) {
            // Si el NIVEL no es numérico, consideramos la línea inválida
            return null;
        }

        String numero      = parts[0].trim();
        String descripcion = parts[1].trim();
        String tipoTexto   = parts[2].trim();
        String padre       = parts[4].trim();
        String grupo       = parts[5].trim();

        // convertir GENERAL/DETALLE a G/D
        String tipo;
        if (tipoTexto.toUpperCase().startsWith("G")) {
            tipo = "G";
        } else if (tipoTexto.toUpperCase().startsWith("D")) {
            tipo = "D";
        } else {
            tipo = tipoTexto;
        }

        return new CuentaContable(numero, descripcion, tipo, nivel, padre, grupo);
    }


    public String toLine() {
        return String.join("|",
            numero,
            descripcion,
            tipo,
            String.valueOf(nivel),
            padre,
            grupo
        );
    }
}
