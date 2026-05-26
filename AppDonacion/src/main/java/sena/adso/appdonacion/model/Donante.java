package sena.adso.appdonacion.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "donors")
public class Donante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false,  unique = true)
    private String documento;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSangre tipoSangre;

    @Column(nullable = false)
    private Double peso;

    private String telefono;

    private String correo;

    @Column(nullable = false)
    private String direccion;

    private LocalDate fechaUltimaDonacion;


    @OneToMany(mappedBy = "donante", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Donacion> donaciones;

    @OneToOne(mappedBy = "donante",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Consentimiento consentimiento;


}
