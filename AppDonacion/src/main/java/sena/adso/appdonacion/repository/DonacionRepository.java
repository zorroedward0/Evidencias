package sena.adso.appdonacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sena.adso.appdonacion.model.Donacion;

import java.util.List;

public interface DonacionRepository extends JpaRepository<Donacion, Long> {
    List<Donacion> findByDonanteId(Long donanteId);
}
