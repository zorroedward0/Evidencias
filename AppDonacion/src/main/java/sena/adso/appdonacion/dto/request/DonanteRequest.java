package sena.adso.appdonacion.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import sena.adso.appdonacion.model.TipoSangre;

import java.time.LocalDate;

@Getter
@Setter
public class DonanteRequest {

    @NotBlank
    @NotNull
    private String nombres;
    @NotBlank
    @NotNull
    private String apellidos;
    @NotBlank
    @NotNull
    private String documento;
    @NotNull
    @Past
    private LocalDate fechaNacimiento;
    @NotNull
    private TipoSangre tipoSangre;
    @NotNull
    @Positive
    private Double peso;
    @NotNull
    @NotBlank
    private String telefono;
    @NotNull
    @NotBlank
    @Email
    private String correo;
    @NotBlank
    @NotNull
    private String direccion;



}
