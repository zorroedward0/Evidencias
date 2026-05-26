package sena.adso.appdonacion.mapper;

import org.mapstruct.Mapper;
import sena.adso.appdonacion.dto.request.InventarioRequest;
import sena.adso.appdonacion.dto.response.InventarioResponse;
import sena.adso.appdonacion.model.InventarioSangre;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventarioMapper {

    InventarioSangre toEntity(InventarioRequest request);

    InventarioResponse toResponse(InventarioSangre inventario);

    List<InventarioResponse> toResponseList(List<InventarioSangre> inventarios);

}
