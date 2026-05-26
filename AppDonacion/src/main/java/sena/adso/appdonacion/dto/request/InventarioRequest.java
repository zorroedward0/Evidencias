package sena.adso.appdonacion.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import sena.adso.appdonacion.model.TipoSangre;

@Getter
@Setter
public class InventarioRequest {

    @NotNull
    private TipoSangre tipoSangre;
    @NotNull
    @Positive
    private Double cantidadMl;



}
