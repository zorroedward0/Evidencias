package sena.adso.appdonacion.service;

import org.springframework.web.multipart.MultipartFile;
import sena.adso.appdonacion.dto.request.DonanteRequest;
import sena.adso.appdonacion.dto.response.DonanteResponse;

import java.util.List;

public interface DonanteService {

    DonanteResponse crearDonante(
            DonanteRequest request,
            boolean aceptaConsentimiento,
            MultipartFile firmaConsentimiento
    );

    List<DonanteResponse> listarDonantes();

    DonanteResponse obtenerDonantePorId(Long id);

    DonanteResponse actualizarDonante(Long id,
                                      DonanteRequest request);

    void eliminarDonante(Long id);

}