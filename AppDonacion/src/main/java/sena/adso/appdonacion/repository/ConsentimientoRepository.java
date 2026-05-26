package sena.adso.appdonacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sena.adso.appdonacion.model.Consentimiento;

public interface ConsentimientoRepository extends JpaRepository<Consentimiento, Long> {
}
