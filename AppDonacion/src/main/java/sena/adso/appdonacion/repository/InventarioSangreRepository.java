package sena.adso.appdonacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sena.adso.appdonacion.model.InventarioSangre;
import sena.adso.appdonacion.model.TipoSangre;

import java.util.Optional;

public interface InventarioSangreRepository extends JpaRepository<InventarioSangre, Long> {
    Optional<InventarioSangre> findByTipoSangre(TipoSangre tipoSangre);
}
