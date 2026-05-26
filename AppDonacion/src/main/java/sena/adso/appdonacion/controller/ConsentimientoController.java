package sena.adso.appdonacion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sena.adso.appdonacion.dto.request.ConsentimientoRequest;
import sena.adso.appdonacion.dto.response.ConsentimientoResponse;
import sena.adso.appdonacion.service.ConsentimientoService;

import java.util.List;

@RestController
@RequestMapping("/api/consentimientos")
@RequiredArgsConstructor
public class ConsentimientoController {

    private final ConsentimientoService consentimientoService;

    @PostMapping("/{donanteId}")
    public ResponseEntity<ConsentimientoResponse>
    registrarConsentimiento(
            @PathVariable Long donanteId,
            @Valid @RequestBody ConsentimientoRequest request) {

        return new ResponseEntity<>(
                consentimientoService
                        .registrarConsentimiento(
                                donanteId,
                                request
                        ),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<ConsentimientoResponse>>
    listarConsentimientos() {

        return ResponseEntity.ok(
                consentimientoService.listarConsentimientos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsentimientoResponse>
    obtenerConsentimientoPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                consentimientoService
                        .obtenerConsentimientoPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    eliminarConsentimiento(@PathVariable Long id) {

        consentimientoService.eliminarConsentimiento(id);

        return ResponseEntity.noContent().build();
    }

}