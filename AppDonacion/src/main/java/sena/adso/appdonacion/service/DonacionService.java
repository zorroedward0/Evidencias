package sena.adso.appdonacion.service;

import sena.adso.appdonacion.dto.request.DonacionRequest;
import sena.adso.appdonacion.dto.response.DonacionResponse;

import java.util.List;

public interface DonacionService {

    DonacionResponse registrarDonacion(DonacionRequest request);

    List<DonacionResponse> listarDonaciones();

    DonacionResponse obtenerDonacionPorId(Long id);

    List<DonacionResponse> listarDonacionesPorDonante(Long donanteId);

    void eliminarDonacion(Long id);

}