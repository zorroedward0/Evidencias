package sena.adso.appdonacion.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "blood_inventory")
public class InventarioSangre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSangre tipoSangre;

    @Column(nullable = false)
    private Double  cantidadMl;

    @Column(nullable = false)
    private LocalDate ultimaActualizacion;

}
