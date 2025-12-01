package model;

public class DetalleTransaccion {

    private String nroDoc;
    private int secuencia;
    private String cuentaContable;
    private double valorDebito;
    private double valorCredito;
    private String comentario;

    public DetalleTransaccion(String nroDoc, int secuencia, String cuentaContable,
                              double valorDebito, double valorCredito,
                              String comentario) {
        this.nroDoc = nroDoc;
        this.secuencia = secuencia;
        this.cuentaContable = cuentaContable;
        this.valorDebito = valorDebito;
        this.valorCredito = valorCredito;
        this.comentario = comentario;
    }

    public String getNroDoc() { return nroDoc; }
    public int getSecuencia() { return secuencia; }
    public String getCuentaContable() { return cuentaContable; }
    public double getValorDebito() { return valorDebito; }
    public double getValorCredito() { return valorCredito; }
    public String getComentario() { return comentario; }

    public static DetalleTransaccion fromLine(String line) {
        String[] x = line.split("\\|");
        if (x.length < 6) return null;

        String nroDoc = x[0];
        int secuencia = Integer.parseInt(x[1]);
        String cuenta = x[2];
        double debito = Double.parseDouble(x[3]);
        double credito = Double.parseDouble(x[4]);
        String comentario = x[5];

        return new DetalleTransaccion(nroDoc, secuencia, cuenta, debito, credito, comentario);
    }

    public String toLine() {
        return String.join("|",
                nroDoc,
                String.valueOf(secuencia),
                cuentaContable,
                String.valueOf(valorDebito),
                String.valueOf(valorCredito),
                comentario
        );
    }
}
