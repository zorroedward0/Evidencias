package sena.adso.appdonacion.dto.response;

import lombok.Getter;
import lombok.Setter;
import sena.adso.appdonacion.model.TipoSangre;

import java.time.LocalDate;

@Getter
@Setter
public class InventarioResponse {

    private Long id;
    private TipoSangre tipoSangre;
    private Double cantidadMl;
    private LocalDate ultimaActualizacion;

}