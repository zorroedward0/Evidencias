package sena.adso.appdonacion.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import sena.adso.appdonacion.model.Donante;

import java.time.LocalDate;

@Getter
@Setter
public class DonacionResponse {

    private Long id;
    private String codigoDonacion;
    private Double cantidadMl;
    private LocalDate fechaDonacion;
    private String observaciones;

    @JsonIgnore
    private Donante donante;

    private Long donanteId;
    private String donanteNombreCompleto;



}
