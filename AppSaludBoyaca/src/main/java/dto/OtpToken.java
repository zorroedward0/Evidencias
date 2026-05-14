
package dto;

import java.time.LocalDateTime;

public class OtpToken {

    private int id;
    private int idUsuario;
    private String codigo;
    private LocalDateTime fechaGen;
    private LocalDateTime expiraEn;
    private boolean usado;

    public OtpToken() {
    }

    public OtpToken(int id, int idUsuario, String codigo, LocalDateTime fechaGen,
                    LocalDateTime expiraEn, boolean usado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.codigo = codigo;
        this.fechaGen = fechaGen;
        this.expiraEn = expiraEn;
        this.usado = usado;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getFechaGen() {
        return fechaGen;
    }

    public void setFechaGen(LocalDateTime fechaGen) {
        this.fechaGen = fechaGen;
    }

    public LocalDateTime getExpiraEn() {
        return expiraEn;
    }

    public void setExpiraEn(LocalDateTime expiraEn) {
        this.expiraEn = expiraEn;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    @Override
    public String toString() {
        return "OtpToken{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", codigo='" + codigo + '\'' +
                ", fechaGen=" + fechaGen +
                ", expiraEn=" + expiraEn +
                ", usado=" + usado +
                '}';
    }
}