package sena.adso.appdonacion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sena.adso.appdonacion.dto.request.ConsentimientoRequest;
import sena.adso.appdonacion.dto.response.ConsentimientoResponse;
import sena.adso.appdonacion.model.Consentimiento;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsentimientoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaFirma", ignore = true)
    @Mapping(target = "donante", ignore = true)
    Consentimiento toEntity(ConsentimientoRequest request);

    @Mapping(source = "donante.id", target = "donanteId")
    ConsentimientoResponse toResponse(Consentimiento consentimiento);

    List<ConsentimientoResponse> toResponseList(List<Consentimiento> consentimientos);

}