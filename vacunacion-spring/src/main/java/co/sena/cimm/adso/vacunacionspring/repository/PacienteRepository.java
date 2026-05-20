package co.sena.cimm.adso.vacunacionspring.repository;

import co.sena.cimm.adso.vacunacionspring.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;


@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByDocumento(String documento);

    List<Paciente> findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(
            String nombres,
            String apellidos
    );

    @org.springframework.data.jpa.repository.Query("SELECT p FROM Paciente p WHERE p.documento = :doc")
    Optional<Paciente> buscarPorDocumento(@org.springframework.data.repository.query.Param("doc") String doc);

}