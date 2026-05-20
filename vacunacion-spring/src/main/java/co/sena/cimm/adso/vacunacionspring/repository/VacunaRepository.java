package co.sena.cimm.adso.vacunacionspring.repository;

import co.sena.cimm.adso.vacunacionspring.model.Vacuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VacunaRepository extends JpaRepository<Vacuna, Long> {

    Optional<Vacuna> findByLoteAndNombre(String lote, String nombre);

    List<Vacuna> findByFechaVencimientoAfter(LocalDate fecha);

    List<Vacuna> findByLaboratorio(String laboratorio);
}