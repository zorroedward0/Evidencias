package sena.adso.appdonacion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sena.adso.appdonacion.dto.request.DonacionRequest;
import sena.adso.appdonacion.dto.response.DonacionResponse;
import sena.adso.appdonacion.service.DonacionService;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
@RequiredArgsConstructor
public class DonacionController {

    private final DonacionService donacionService;

    @PostMapping
    public ResponseEntity<DonacionResponse>
    registrarDonacion(
            @Valid @RequestBody DonacionRequest request) {

        return new ResponseEntity<>(
                donacionService.registrarDonacion(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<DonacionResponse>>
    listarDonaciones() {

        return ResponseEntity.ok(
                donacionService.listarDonaciones()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonacionResponse>
    obtenerDonacionPorId(@PathVariable Long id) {

        return ResponseEntity.ok(
                donacionService.obtenerDonacionPorId(id)
        );
    }

    @GetMapping("/donante/{donanteId}")
    public ResponseEntity<List<DonacionResponse>>
    listarDonacionesPorDonante(
            @PathVariable Long donanteId) {

        return ResponseEntity.ok(
                donacionService
                        .listarDonacionesPorDonante(
                                donanteId
                        )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    eliminarDonacion(@PathVariable Long id) {

        donacionService.eliminarDonacion(id);

        return ResponseEntity.noContent().build();
    }

}