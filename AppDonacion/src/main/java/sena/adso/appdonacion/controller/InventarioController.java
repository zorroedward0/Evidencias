package sena.adso.appdonacion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sena.adso.appdonacion.dto.request.DonacionRequest;
import sena.adso.appdonacion.dto.request.InventarioRequest;
import sena.adso.appdonacion.dto.response.DonacionResponse;
import sena.adso.appdonacion.dto.response.InventarioResponse;
import sena.adso.appdonacion.model.TipoSangre;
import sena.adso.appdonacion.service.InventarioService;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<InventarioResponse>>
    listarInventario() {

        return ResponseEntity.ok(
                inventarioService.listarInventario()
        );
    }

    @GetMapping("/{tipoSangre}")
    public ResponseEntity<InventarioResponse>
    obtenerPorTipoSangre(
            @PathVariable TipoSangre tipoSangre) {

        return ResponseEntity.ok(
                inventarioService
                        .obtenerPorTipoSangre(tipoSangre)
        );
    }

    @PostMapping
    public ResponseEntity<InventarioResponse>
    registrarDonacion(
            @Valid @RequestBody InventarioRequest request) {

        return new ResponseEntity<>(
                inventarioService.retirarSangre(request),
                HttpStatus.ACCEPTED
        );
    }

}