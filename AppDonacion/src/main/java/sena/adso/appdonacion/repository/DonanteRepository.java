package sena.adso.appdonacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sena.adso.appdonacion.model.Donante;

import java.util.Optional;

public interface DonanteRepository extends JpaRepository<Donante, Long> {
    Optional<Donante> findByDocumento(String documento);
    boolean existsByDocumento(String documento);
}
