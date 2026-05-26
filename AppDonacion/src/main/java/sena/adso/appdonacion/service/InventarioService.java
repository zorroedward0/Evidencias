package sena.adso.appdonacion.service;

import sena.adso.appdonacion.dto.request.InventarioRequest;
import sena.adso.appdonacion.dto.response.InventarioResponse;
import sena.adso.appdonacion.model.TipoSangre;

import java.util.List;

public interface InventarioService {

    List<InventarioResponse> listarInventario();

    InventarioResponse obtenerPorTipoSangre(
            TipoSangre tipoSangre
    );

    InventarioResponse retirarSangre(InventarioRequest inventarioRequest);

}