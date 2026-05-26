package sena.adso.appdonacion.validation;

import org.springframework.stereotype.Component;
import sena.adso.appdonacion.exception.DonanteNoAptoException;
import sena.adso.appdonacion.model.Donante;

@Component
public class ValidacionPeso implements ValidacionDonante {

    @Override
    public void validar(Donante donante) {

        if (donante.getPeso() < 50) {

            throw new DonanteNoAptoException(
                    "El donante debe pesar mínimo 50 kg"
            );
        }

    }

}