package co.sena.cimm.adso.vacunacionspring.service;

import co.sena.cimm.adso.vacunacionspring.model.Vacuna;
import co.sena.cimm.adso.vacunacionspring.repository.VacunaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class VacunaService {

    private final VacunaRepository vacunaRepository;

    public VacunaService(VacunaRepository vacunaRepository) {
        this.vacunaRepository = vacunaRepository;
    }

    @Transactional(readOnly = true)
    public List<Vacuna> listarTodas() {
        return vacunaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Vacuna> listarDisponibles() {
        return vacunaRepository.findByFechaVencimientoAfter(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public Vacuna buscarPorId(Long id) {
        return vacunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacuna no encontrada"));
    }

    public Vacuna guardar(Vacuna vacuna) {
        return vacunaRepository.save(vacuna);
    }

    public void eliminar(Long id) {

        if (!vacunaRepository.existsById(id)) {
            throw new RuntimeException("Vacuna no encontrada");
        }

        vacunaRepository.deleteById(id);
    }
}