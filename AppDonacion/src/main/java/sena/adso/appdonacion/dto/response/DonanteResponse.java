package sena.adso.appdonacion.dto.response;

import lombok.Getter;
import lombok.Setter;
import sena.adso.appdonacion.model.TipoSangre;

import java.time.LocalDate;

@Getter
@Setter
public class DonanteResponse {

    private Long id;
    private String nombres;
    private String apellidos;
    private String documento;
    private TipoSangre tipoSangre;
    private Double peso;
    private String telefono;
    private String correo;
    private String direccion;
    private LocalDate fechaNacimiento;

}