package sena.adso.appdonacion.validation;

import org.springframework.stereotype.Component;
import sena.adso.appdonacion.exception.DonanteNoAptoException;
import sena.adso.appdonacion.model.Donante;

import java.time.LocalDate;
import java.time.Period;

@Component
public class ValidacionEdad implements ValidacionDonante {

    @Override
    public void validar(Donante donante) {

        int edad = Period.between(
                donante.getFechaNacimiento(),
                LocalDate.now()
        ).getYears();

        if (edad < 18) {

            throw new DonanteNoAptoException(
                    "El donante debe ser mayor de edad"
            );
        }

    }

}