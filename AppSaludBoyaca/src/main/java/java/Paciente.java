
public class Paciente {

    private String nombres;
    private String apellidos;

    public Paciente(String nombres, String apellidos) {
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public String getNombreCompleto() {
        return nombres + " " +  apellidos;
    }

}
