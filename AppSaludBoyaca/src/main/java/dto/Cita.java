package dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Cita {

    private int id;
    private int idPaciente;
    private int idMedico;
    private int idEspecialidad;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private String motivo;
    private String estado;
    private String observaciones;
    private LocalDateTime fechaRegistro;
    private int idRegistradoPor;
    private String nombrePaciente;
    private String nombreMedico;
    private String nombreEspecialidad;

    public Cita() {
    }

    public Cita(int id, int idPaciente, int idMedico, int idEspecialidad, LocalDate fechaCita, LocalTime horaCita, String motivo, String estado, String observaciones, LocalDateTime fechaRegistro, int idRegistradoPor, String nombrePaciente, String nombreMedico, String nombreEspecialidad) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.idEspecialidad = idEspecialidad;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.motivo = motivo;
        this.estado = estado;
        this.observaciones = observaciones;
        this.fechaRegistro = fechaRegistro;
        this.idRegistradoPor = idRegistradoPor;
        this.nombrePaciente = nombrePaciente;
        this.nombreMedico = nombreMedico;
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public Cita(int id, int idPaciente, int idMedico, int idEspecialidad, LocalDate fechaCita,
            LocalTime horaCita, String motivo, String estado, String observaciones,
            LocalDateTime fechaRegistro, int idRegistradoPor) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.idEspecialidad = idEspecialidad;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.motivo = motivo;
        this.estado = estado;
        this.observaciones = observaciones;
        this.fechaRegistro = fechaRegistro;
        this.idRegistradoPor = idRegistradoPor;
    }

    public Cita(int id, int idPaciente, int idMedico, int idEspecialidad, LocalDate fechaCita,
            LocalTime horaCita, String motivo, String estado, String observaciones,
            LocalDateTime fechaRegistro, int idRegistradoPor, String nombrePaciente, String nombreMedico) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.idEspecialidad = idEspecialidad;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.motivo = motivo;
        this.estado = estado;
        this.observaciones = observaciones;
        this.fechaRegistro = fechaRegistro;
        this.idRegistradoPor = idRegistradoPor;
        this.nombrePaciente = nombrePaciente;
        this.nombreMedico = nombreMedico;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public LocalTime getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(LocalTime horaCita) {
        this.horaCita = horaCita;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser null");
        }
        
        if (estadosValidos.contains(estado)) {
            this.estado = estado;
        } else {
            throw new IllegalArgumentException("Estado de cita invalido");
        }
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdRegistradoPor() {
        return idRegistradoPor;
    }

    public void setIdRegistradoPor(int idRegistradoPor) {
        this.idRegistradoPor = idRegistradoPor;
    }

    @Override
    public String toString() {
        return "Cita{"
                + "id=" + id
                + ", idPaciente=" + idPaciente
                + ", idMedico=" + idMedico
                + ", idEspecialidad=" + idEspecialidad
                + ", fechaCita=" + fechaCita
                + ", horaCita=" + horaCita
                + ", motivo='" + motivo + '\''
                + ", estado='" + estado + '\''
                + ", observaciones='" + observaciones + '\''
                + ", fechaRegistro=" + fechaRegistro
                + ", idRegistradoPor=" + idRegistradoPor
                + '}';
    }

    private List<String> estadosValidos = List.of("PROGRAMADA", "CONFIRMADA", "ATENDIDA", "CANCELADA");

}
