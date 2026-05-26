package sena.adso.appdonacion.service;

import sena.adso.appdonacion.dto.request.ConsentimientoRequest;
import sena.adso.appdonacion.dto.response.ConsentimientoResponse;

import java.util.List;

public interface ConsentimientoService {

    ConsentimientoResponse registrarConsentimiento(
            Long donanteId,
            ConsentimientoRequest request
    );

    List<ConsentimientoResponse> listarConsentimientos();

    ConsentimientoResponse obtenerConsentimientoPorId(Long id);

    void eliminarConsentimiento(Long id);

}