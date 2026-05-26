package sena.adso.appdonacion.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsentimientoRequest {

    @NotNull
    private Boolean aceptaConsentimiento;
    @NotBlank
    @NotNull
    private String firmaConsentimiento;


}