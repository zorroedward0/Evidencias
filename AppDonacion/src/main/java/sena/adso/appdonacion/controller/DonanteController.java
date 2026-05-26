package sena.adso.appdonacion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sena.adso.appdonacion.dto.request.DonanteRequest;
import sena.adso.appdonacion.dto.response.DonanteResponse;
import sena.adso.appdonacion.service.DonanteService;

import java.util.List;

@RestController
@RequestMapping("/api/donantes")
@RequiredArgsConstructor
public class DonanteController {
    private final DonanteService donanteService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DonanteResponse> crearDonante(

            @RequestPart("donante")
            @Valid
            DonanteRequest request,

            @RequestPart("firma")
            MultipartFile firmaConsentimiento,

            @RequestParam
            boolean aceptaConsentimiento

    ) {

        return new ResponseEntity<>(
                donanteService.crearDonante(
                        request,
                        aceptaConsentimiento,
                        firmaConsentimiento
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<DonanteResponse>> listarDonantes() {
        return ResponseEntity.ok(donanteService.listarDonantes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonanteResponse> obtenerDonantePorId(@PathVariable Long id) {
        return ResponseEntity.ok(donanteService.obtenerDonantePorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonanteResponse> actualizarDonante(@PathVariable Long id, @Valid @RequestBody DonanteRequest request) {
        return ResponseEntity.ok(donanteService.actualizarDonante(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDonante(@PathVariable Long id) {
        donanteService.eliminarDonante(id);
        return ResponseEntity.noContent().build();
    }
}