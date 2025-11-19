package model;

public class Usuario {
    private String login;
    private String password;
    private String tipo;      // A = Admin, N = Normal
    private String nombre;
    private String apellidos;
    private String email;

    public Usuario(String login, String password, String tipo,
                   String nombre, String apellidos, String email) {
        this.login = login;
        this.password = password;
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
    }

    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getTipo() { return tipo; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getEmail() { return email; }

    public static Usuario fromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 6) return null;

        String login = parts[0];
        String password = parts[1];
        String tipo = parts[2];
        String nombre = parts[3];
        String apellidos = parts[4];
        String email = parts[5];

        return new Usuario(login, password, tipo, nombre, apellidos, email);
    }
    public String toLine() {
        return String.join("|",
                login,
                password,
                tipo,
                nombre,
                apellidos,
                email
        );
    }
}
