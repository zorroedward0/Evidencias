package co.sena.cimm.adso.vacunacionspring.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "vacunas",
        uniqueConstraints = @UniqueConstraint(columnNames = {"lote", "nombre"}))
public class Vacuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String lote;

    @Column(nullable = false, length = 100)
    private String laboratorio;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    public Vacuna() {
    }

    public Vacuna(String nombre, String lote,
                  String laboratorio, LocalDate fechaVencimiento) {
        this.nombre = nombre;
        this.lote = lote;
        this.laboratorio = laboratorio;
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean estaVencida() {
        return LocalDate.now().isAfter(fechaVencimiento);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        this.nombre = n;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String l) {
        this.lote = l;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String l) {
        this.laboratorio = l;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate f) {
        this.fechaVencimiento = f;
    }

    public void setId(int id) {
        this.id = id;
    }
}