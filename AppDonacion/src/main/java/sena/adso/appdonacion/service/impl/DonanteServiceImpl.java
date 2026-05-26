package sena.adso.appdonacion.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sena.adso.appdonacion.dto.request.ConsentimientoRequest;
import sena.adso.appdonacion.dto.request.DonanteRequest;
import sena.adso.appdonacion.dto.response.DonanteResponse;
import sena.adso.appdonacion.exception.BusinessException;
import sena.adso.appdonacion.exception.ResourceNotFoundException;
import sena.adso.appdonacion.mapper.DonanteMapper;
import sena.adso.appdonacion.model.Donante;
import sena.adso.appdonacion.repository.DonanteRepository;
import sena.adso.appdonacion.service.DonanteService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonanteServiceImpl implements DonanteService {

    private final DonanteRepository donanteRepository;
    private final DonanteMapper donanteMapper;
    private final ConsentimientoServiceImpl consentimientoService;

    @Override
    public DonanteResponse crearDonante(
            DonanteRequest request,
            boolean aceptaConsentimiento,
            MultipartFile firmaConsentimiento
    ) {

        boolean existeDocumento =
                donanteRepository.existsByDocumento(
                        request.getDocumento()
                );

        if (existeDocumento) {

            throw new BusinessException(
                    "Ya existe un donante con ese documento"
            );
        }

        Donante donante =
                donanteMapper.toEntity(request);

        Donante donanteGuardado =
                donanteRepository.save(donante);

        log.info("Donante registrado correctamente");

        String nombreArchivo;

        try {

            String extension =
                    Objects.requireNonNull(
                                    firmaConsentimiento.getOriginalFilename()
                            )
                            .substring(
                                    firmaConsentimiento
                                            .getOriginalFilename()
                                            .lastIndexOf(".")
                            );

            nombreArchivo =
                    UUID.randomUUID() + extension;

            Path ruta =
                    Paths.get("uploads/firmas/" + nombreArchivo);

            Files.createDirectories(ruta.getParent());

            Files.copy(
                    firmaConsentimiento.getInputStream(),
                    ruta,
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error guardando firma"
            );
        }

        ConsentimientoRequest consentimiento =
                new ConsentimientoRequest();

        consentimiento.setFirmaConsentimiento(
                nombreArchivo
        );

        consentimiento.setAceptaConsentimiento(
                aceptaConsentimiento
        );

        consentimientoService.registrarConsentimiento(
                donanteGuardado.getId(),
                consentimiento
        );

        return donanteMapper.toResponse(
                donanteGuardado
        );
    }

    @Override
    public List<DonanteResponse> listarDonantes() {

        List<Donante> donantes = donanteRepository.findAll();

        return donanteMapper.toResponseList(donantes);
    }

    @Override
    public DonanteResponse obtenerDonantePorId(Long id) {

        Donante donante = donanteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Donante no encontrado"));

        return donanteMapper.toResponse(donante);
    }

    @Override
    public DonanteResponse actualizarDonante(Long id, DonanteRequest request) {

        Donante donante = donanteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Donante no encontrado"));

        donante.setNombres(request.getNombres());
        donante.setApellidos(request.getApellidos());
        donante.setDocumento(request.getDocumento());
        donante.setFechaNacimiento(request.getFechaNacimiento());
        donante.setTipoSangre(request.getTipoSangre());
        donante.setPeso(request.getPeso());
        donante.setTelefono(request.getTelefono());
        donante.setCorreo(request.getCorreo());
        donante.setDireccion(request.getDireccion());

        Donante donanteActualizado = donanteRepository.save(donante);

        log.info("Donante actualizado correctamente");

        return donanteMapper.toResponse(donanteActualizado);
    }

    @Override
    public void eliminarDonante(Long id) {

        Donante donante = donanteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Donante no encontrado"));

        donanteRepository.delete(donante);

        log.info("Donante eliminado correctamente");
    }

}