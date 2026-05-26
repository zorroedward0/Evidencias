package sena.adso.appdonacion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sena.adso.appdonacion.dto.request.DonacionRequest;
import sena.adso.appdonacion.dto.response.DonacionResponse;
import sena.adso.appdonacion.model.Donacion;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DonacionMapper {

    Donacion toEntity(DonacionRequest request);

    @Mapping(target = "donante", ignore = true)
    @Mapping(target = "donanteId", source = "donante.id")
    @Mapping(target = "donanteNombreCompleto",
            expression = "java(donacion.getDonante().getNombres() + \" \" + donacion.getDonante().getApellidos())"
    )
    DonacionResponse toResponse(Donacion donacion);

    List<DonacionResponse> toResponseList(List<Donacion> donaciones);

}
