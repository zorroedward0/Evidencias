package sena.adso.appdonacion.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.adso.appdonacion.dto.request.ConsentimientoRequest;
import sena.adso.appdonacion.dto.response.ConsentimientoResponse;
import sena.adso.appdonacion.exception.ResourceNotFoundException;
import sena.adso.appdonacion.mapper.ConsentimientoMapper;
import sena.adso.appdonacion.model.Consentimiento;
import sena.adso.appdonacion.model.Donante;
import sena.adso.appdonacion.repository.ConsentimientoRepository;
import sena.adso.appdonacion.repository.DonanteRepository;
import sena.adso.appdonacion.service.ConsentimientoService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsentimientoServiceImpl implements ConsentimientoService {

    private final ConsentimientoRepository consentimientoRepository;
    private final ConsentimientoMapper consentimientoMapper;
    private final DonanteRepository donanteRepository;

    @Override
    public ConsentimientoResponse registrarConsentimiento(Long donanteId, ConsentimientoRequest request) {

        Donante donante = donanteRepository.findById(donanteId)
                .orElseThrow(() -> new ResourceNotFoundException("Donante no encontrado"));

        Consentimiento consentimiento = consentimientoMapper.toEntity(request);

        consentimiento.setFechaFirma(LocalDate.now());

        consentimiento.setDonante(donante);

        Consentimiento consentimientoGuardado =
                consentimientoRepository.save(consentimiento);

        log.info("Consentimiento registrado correctamente");

        return consentimientoMapper.toResponse(consentimientoGuardado);
    }

    @Override
    public List<ConsentimientoResponse> listarConsentimientos() {

        List<Consentimiento> consentimientos = consentimientoRepository.findAll();

        return consentimientoMapper.toResponseList(consentimientos);
    }

    @Override
    public ConsentimientoResponse obtenerConsentimientoPorId(Long id) {

        Consentimiento consentimiento = consentimientoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Consentimiento no encontrado"));

        return consentimientoMapper.toResponse(consentimiento);
    }

    @Override
    public void eliminarConsentimiento(Long id) {

        Consentimiento consentimiento = consentimientoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Consentimiento no encontrado"));

        consentimientoRepository.delete(consentimiento);

        log.info("Consentimiento eliminado correctamente");
    }

}