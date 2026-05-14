
package dto;

import java.time.LocalDateTime;

public class LogAcceso {

    private int id;
    private int idUsuario;
    private String username;
    private String accion;
    private String ip;
    private String resultado;
    private LocalDateTime fecha;

    public LogAcceso() {
    }

    public LogAcceso(int id, int idUsuario, String username, String accion, String ip,
                     String resultado, LocalDateTime fecha) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.username = username;
        this.accion = accion;
        this.ip = ip;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "LogAcceso{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", username='" + username + '\'' +
                ", accion='" + accion + '\'' +
                ", ip='" + ip + '\'' +
                ", resultado='" + resultado + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}