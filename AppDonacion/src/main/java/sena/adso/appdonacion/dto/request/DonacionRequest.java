package sena.adso.appdonacion.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonacionRequest {

    @Positive
    @NotNull
    private Long donanteId;

    @NotNull
    @Positive
    private Double cantidadMl;
    @NotBlank
    @NotNull
    private String observaciones;



}
