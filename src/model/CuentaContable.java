package model;

    public class CuentaContable {
        private String numero;
        private String descripcion;
        private String tipo;      // G = General, D = Detalle
        private int nivel;
        private String padre;
        private String grupo;

        // ✅ Nuevos campos
        private double debitoAcum;
        private double creditoAcum;
        private double balance;

        public CuentaContable(String numero, String descripcion, String tipo,
                              int nivel, String padre, String grupo,
                              double debitoAcum, double creditoAcum, double balance) {
            this.numero = numero;
            this.descripcion = descripcion;
            this.tipo = tipo;
            this.nivel = nivel;
            this.padre = padre;
            this.grupo = grupo;
            this.debitoAcum = debitoAcum;
            this.creditoAcum = creditoAcum;
            this.balance = balance;
        }

        // Constructor “viejo” por compatibilidad (si lo usas en mantenimiento)
        public CuentaContable(String numero, String descripcion, String tipo,
                              int nivel, String padre, String grupo) {
            this(numero, descripcion, tipo, nivel, padre, grupo, 0.0, 0.0, 0.0);
        }

        public String getNumero() { return numero; }
        public String getDescripcion() { return descripcion; }
        public String getTipo() { return tipo; }
        public int getNivel() { return nivel; }
        public String getPadre() { return padre; }
        public String getGrupo() { return grupo; }

        public double getDebitoAcum() { return debitoAcum; }
        public double getCreditoAcum() { return creditoAcum; }
        public double getBalance() { return balance; }

        public void setDebitoAcum(double debitoAcum) { this.debitoAcum = debitoAcum; }
        public void setCreditoAcum(double creditoAcum) { this.creditoAcum = creditoAcum; }
        public void setBalance(double balance) { this.balance = balance; }

        public static CuentaContable fromLine(String line) {
            String[] parts = line.split("\\|");
            if (parts.length < 6) return null;

            // por si alguien mete encabezado en el futuro
            if (parts[0].trim().equalsIgnoreCase("NO. CUENTA")) return null;

            int nivel;
            try { 
                nivel = Integer.parseInt(parts[3].trim()); 
            } catch (NumberFormatException e) { 
                return null; 
            }

            String numero      = parts[0].trim();
            String descripcion = parts[1].trim();
            String tipoTexto   = parts[2].trim();
            String padre       = parts[4].trim();
            String grupo       = parts[5].trim();

            String tipo;
            if (tipoTexto.toUpperCase().startsWith("G")) tipo = "G";
            else if (tipoTexto.toUpperCase().startsWith("D")) tipo = "D";
            else tipo = tipoTexto;

            // ✅ Tu TXT tiene 9 campos: 6=DebAcum, 7=CreAcum, 8=Balance
            double debAcum = 0.0, creAcum = 0.0, bal = 0.0;
            if (parts.length >= 9) {
                debAcum = tryDouble(parts, 6);
                creAcum = tryDouble(parts, 7);
                bal     = tryDouble(parts, 8);
            }

            return new CuentaContable(numero, descripcion, tipo, nivel, padre, grupo, debAcum, creAcum, bal);
        }

        private static double tryDouble(String[] parts, int idx) {
            if (idx >= parts.length) return 0.0;
            try { return Double.parseDouble(parts[idx].trim()); }
            catch (Exception e) { return 0.0; }
        }

        public String toLine() {
            return String.join("|",
                numero,
                descripcion,
                tipo,
                String.valueOf(nivel),
                padre,
                grupo,
                String.valueOf(debitoAcum),
                String.valueOf(creditoAcum),
                String.valueOf(balance)
            );
        }
    }
