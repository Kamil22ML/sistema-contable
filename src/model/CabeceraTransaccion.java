package model;

public class CabeceraTransaccion {

    private String nroDocu;
    private String fechaDocu;          // yyyy-MM-dd
    private int tipoDocu;
    private String descripcion;
    private String hechoPor;
    private double montoTransaccion;
    private String fechaActualizacion; // yyyy-MM-dd
    private boolean statusActualizacion;

    public CabeceraTransaccion(String nroDocu, String fechaDocu, int tipoDocu,
                               String descripcion, String hechoPor,
                               double montoTransaccion,
                               String fechaActualizacion,
                               boolean statusActualizacion) {
        this.nroDocu = nroDocu;
        this.fechaDocu = fechaDocu;
        this.tipoDocu = tipoDocu;
        this.descripcion = descripcion;
        this.hechoPor = hechoPor;
        this.montoTransaccion = montoTransaccion;
        this.fechaActualizacion = fechaActualizacion;
        this.statusActualizacion = statusActualizacion;
    }

    public String getNroDocu() { return nroDocu; }
    public String getFechaDocu() { return fechaDocu; }
    public int getTipoDocu() { return tipoDocu; }
    public String getDescripcion() { return descripcion; }
    public String getHechoPor() { return hechoPor; }
    public double getMontoTransaccion() { return montoTransaccion; }
    public String getFechaActualizacion() { return fechaActualizacion; }
    public boolean isStatusActualizacion() { return statusActualizacion; }

    public void setMontoTransaccion(double montoTransaccion) {
        this.montoTransaccion = montoTransaccion;
    }
    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    public void setStatusActualizacion(boolean statusActualizacion) {
        this.statusActualizacion = statusActualizacion;
    }

    public static CabeceraTransaccion fromLine(String line) {
        String[] x = line.split("\\|");
        if (x.length < 8) return null;

        String nroDocu = x[0];
        String fechaDocu = x[1];
        int tipoDocu = Integer.parseInt(x[2]);
        String descripcion = x[3];
        String hechoPor = x[4];
        double monto = Double.parseDouble(x[5]);
        String fechaAct = x[6];
        boolean status = Boolean.parseBoolean(x[7]);

        return new CabeceraTransaccion(nroDocu, fechaDocu, tipoDocu,
                descripcion, hechoPor, monto, fechaAct, status);
    }

    public String toLine() {
        return String.join("|",
                nroDocu,
                fechaDocu,
                String.valueOf(tipoDocu),
                descripcion,
                hechoPor,
                String.valueOf(montoTransaccion),
                fechaActualizacion,
                String.valueOf(statusActualizacion)
        );
    }
}
