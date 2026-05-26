package sena.adso.appdonacion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> manejarResourceNotFound(
            ResourceNotFoundException ex) {

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("fecha", LocalDateTime.now());
        respuesta.put("error", ex.getMessage());
        respuesta.put("estado", HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> manejarBusinessException(
            BusinessException ex) {

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("fecha", LocalDateTime.now());
        respuesta.put("error", ex.getMessage());
        respuesta.put("estado", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConsentimientoNoFirmadoException.class)
    public ResponseEntity<Object> manejarConsentimientoNoFirmado(
            ConsentimientoNoFirmadoException ex) {

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("fecha", LocalDateTime.now());
        respuesta.put("error", ex.getMessage());
        respuesta.put("estado", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DonanteNoAptoException.class)
    public ResponseEntity<Object> manejarDonanteNoApto(
            DonanteNoAptoException ex) {

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("fecha", LocalDateTime.now());
        respuesta.put("error", ex.getMessage());
        respuesta.put("estado", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> manejarValidaciones(
            MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {

            errores.put(
                    error.getField(),
                    error.getDefaultMessage()
            );
        }

        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> manejarErroresGenerales(
            Exception ex) {

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("fecha", LocalDateTime.now());
        respuesta.put("error", ex.getMessage());
        respuesta.put("estado", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(
                respuesta,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}