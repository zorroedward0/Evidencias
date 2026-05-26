package sena.adso.appdonacion.mapper;

import org.mapstruct.Mapper;
import sena.adso.appdonacion.dto.request.DonanteRequest;
import sena.adso.appdonacion.dto.response.DonanteResponse;
import sena.adso.appdonacion.model.Donante;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DonanteMapper {


    Donante toEntity(DonanteRequest request);

    DonanteResponse toResponse(Donante donante);

    List<DonanteResponse> toResponseList(List<Donante> donantes);
}

