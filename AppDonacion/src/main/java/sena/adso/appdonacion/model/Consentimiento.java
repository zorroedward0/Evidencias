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
@Table(name = "consents")
public class Consentimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean aceptaConsentimiento;

    @Column(nullable = false)
    private LocalDate fechaFirma;

    @Column(nullable = false)
    private String firmaConsentimiento;

    @OneToOne
    @JoinColumn(name = "donante_id", unique = true)
    private Donante donante;


}
