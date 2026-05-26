package sena.adso.appdonacion.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.adso.appdonacion.dto.request.DonacionRequest;
import sena.adso.appdonacion.dto.response.DonacionResponse;
import sena.adso.appdonacion.exception.ResourceNotFoundException;
import sena.adso.appdonacion.mapper.DonacionMapper;
import sena.adso.appdonacion.model.Donacion;
import sena.adso.appdonacion.model.Donante;
import sena.adso.appdonacion.model.InventarioSangre;
import sena.adso.appdonacion.model.TipoSangre;
import sena.adso.appdonacion.repository.DonacionRepository;
import sena.adso.appdonacion.repository.DonanteRepository;
import sena.adso.appdonacion.repository.InventarioSangreRepository;
import sena.adso.appdonacion.service.DonacionService;
import sena.adso.appdonacion.validation.ValidacionDonante;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonacionServiceImpl implements DonacionService {

    private final DonacionRepository donacionRepository;
    private final DonanteRepository donanteRepository;
    private final InventarioSangreRepository inventarioRepository;
    private final DonacionMapper donacionMapper;
    private final List<ValidacionDonante> validaciones;

    @Override
    public DonacionResponse registrarDonacion(DonacionRequest request) {

        Donante donante = donanteRepository.findById(request.getDonanteId()).orElseThrow(() -> new ResourceNotFoundException("Donante no encontrado"));

        for (ValidacionDonante validacion : validaciones) {
            validacion.validar(donante);
        }

        Donacion donacion = donacionMapper.toEntity(request);

        donacion.setCodigoDonacion(generarCodigo());

        donacion.setFechaDonacion(LocalDate.now());

        donacion.setDonante(donante);

        Donacion donacionGuardada = donacionRepository.save(donacion);

        donante.setFechaUltimaDonacion(LocalDate.now());

        donanteRepository.save(donante);

        actualizarInventario(donante.getTipoSangre(), request.getCantidadMl());

        log.info("Donacion registrada correctamente");

        return donacionMapper.toResponse(donacionGuardada);
    }

    @Override
    public List<DonacionResponse> listarDonaciones() {

        List<Donacion> donaciones = donacionRepository.findAll();

        return donacionMapper.toResponseList(donaciones);
    }

    @Override
    public DonacionResponse obtenerDonacionPorId(Long id) {

        Donacion donacion = donacionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Donacion no encontrada"));

        return donacionMapper.toResponse(donacion);
    }

    @Override
    public List<DonacionResponse> listarDonacionesPorDonante(Long donanteId) {

        List<Donacion> donaciones = donacionRepository.findByDonanteId(donanteId);

        return donacionMapper.toResponseList(donaciones);
    }

    @Override
    public void eliminarDonacion(Long id) {

        Donacion donacion = donacionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Donacion no encontrada"));

        donacionRepository.delete(donacion);

        log.info("Donacion eliminada correctamente");
    }

    private String generarCodigo() {

        return "DON-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private void actualizarInventario(TipoSangre tipoSangre, Double cantidadMl) {

        InventarioSangre inventario = inventarioRepository.findByTipoSangre(tipoSangre).orElse(null);

        if (inventario == null) {

            inventario = InventarioSangre.builder().tipoSangre(tipoSangre).cantidadMl(cantidadMl).ultimaActualizacion(LocalDate.now()).build();

        } else {

            inventario.setCantidadMl(inventario.getCantidadMl() + cantidadMl);

            inventario.setUltimaActualizacion(LocalDate.now());
        }

        inventarioRepository.save(inventario);
    }

}