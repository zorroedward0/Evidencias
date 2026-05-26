package sena.adso.appdonacion.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.adso.appdonacion.dto.request.InventarioRequest;
import sena.adso.appdonacion.dto.response.InventarioResponse;
import sena.adso.appdonacion.exception.ResourceNotFoundException;
import sena.adso.appdonacion.mapper.InventarioMapper;
import sena.adso.appdonacion.model.InventarioSangre;
import sena.adso.appdonacion.model.TipoSangre;
import sena.adso.appdonacion.repository.InventarioSangreRepository;
import sena.adso.appdonacion.service.InventarioService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventarioServiceImpl implements InventarioService {

    private final InventarioSangreRepository inventarioRepository;
    private final InventarioMapper inventarioMapper;

    @Override
    public List<InventarioResponse> listarInventario() {

        List<InventarioSangre> inventarios = inventarioRepository.findAll();

        return inventarioMapper.toResponseList(inventarios);
    }

    @Override
    public InventarioResponse obtenerPorTipoSangre(TipoSangre tipoSangre) {

        InventarioSangre inventario = inventarioRepository.findByTipoSangre(tipoSangre).orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado"));

        return inventarioMapper.toResponse(inventario);
    }

    @Transactional
    @Override
    public InventarioResponse retirarSangre(InventarioRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("La solicitud no puede ser null");
        }

        double cantidadRetirar = request.getCantidadMl();

        if (cantidadRetirar <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad a retirar debe ser mayor a 0");
        }

        InventarioSangre inventario = inventarioRepository
                .findByTipoSangre(request.getTipoSangre())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No existe inventario para este tipo de sangre"));

        if (inventario.getCantidadMl() < cantidadRetirar) {
            throw new IllegalStateException(
                    "Cantidad insuficiente en inventario");
        }

        inventario.setCantidadMl(
                inventario.getCantidadMl() - cantidadRetirar
        );

        inventario.setUltimaActualizacion(LocalDate.now());

        InventarioSangre actualizado = inventarioRepository.save(inventario);

        return inventarioMapper.toResponse(actualizado);
    }

}