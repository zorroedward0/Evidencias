package dto;

import java.time.LocalTime;

public class Horario {

    private int id;
    private int idMedico;
    private int diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int maxCitas;
    private String nombreMedico;

    public Horario() {
    }

    public Horario(int id, int idMedico, int diaSemana, LocalTime horaInicio, LocalTime horaFin, int maxCitas) {
        this.id = id;
        this.idMedico = idMedico;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.maxCitas = maxCitas;
    }

    public int getId() {
        return id;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public int getMaxCitas() {
        return maxCitas;
    }

    public void setMaxCitas(int maxCitas) {
        this.maxCitas = maxCitas;
    }

    @Override
    public String toString() {
        return "Horario{"
                + "id=" + id
                + ", idMedico=" + idMedico
                + ", diaSemana=" + diaSemana
                + ", horaInicio=" + horaInicio
                + ", horaFin=" + horaFin
                + ", maxCitas=" + maxCitas
                + '}';
    }
}
