package sena.adso.appdonacion.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ConsentimientoResponse {


    private Long id;
    private Boolean aceptaConsentimiento;
    private String firmaConsentimiento;
    private LocalDate fechaFirma;
    private Long  donanteId;

}
