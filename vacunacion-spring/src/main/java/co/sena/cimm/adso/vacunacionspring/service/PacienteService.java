package co.sena.cimm.adso.vacunacionspring.service;

import co.sena.cimm.adso.vacunacionspring.model.Paciente;
import co.sena.cimm.adso.vacunacionspring.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }


    @Transactional(readOnly = true)
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll()
                .stream()
                .sorted((a, b) -> a.getApellidos().compareTo(b.getApellidos()))
                .toList();
    }

    @Transactional(readOnly = true)
    public Paciente buscarPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Paciente con ID " + id + " no encontrado"));
    }


    @Transactional(readOnly = true)
    public Optional<Paciente> buscarPorDocumento(String documento) {
        return pacienteRepository.findByDocumento(documento);
    }


    public Paciente guardar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }


    public void eliminar(Long id) {
        if (!pacienteRepository.existsById(id))
            throw new RuntimeException("Paciente no encontrado");
        pacienteRepository.deleteById(id);
    }
}