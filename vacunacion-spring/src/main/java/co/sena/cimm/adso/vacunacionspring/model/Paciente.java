package co.sena.cimm.adso.vacunacionspring.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;


    @Column(nullable = false, unique = true, length = 20)
    private String documento;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;


    public Paciente() {
    }


    public Paciente(String nombres, String apellidos,
                    String documento, LocalDate fechaNacimiento) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.fechaNacimiento = fechaNacimiento;
    }


    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public Long getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String n) {
        this.nombres = n;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String a) {
        this.apellidos = a;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String d) {
        this.documento = d;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate f) {
        this.fechaNacimiento = f;
    }

    public void setId(Long id) {
        this.id = id;
    }
}