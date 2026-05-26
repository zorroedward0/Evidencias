package sena.adso.appdonacion.validation;

import org.springframework.stereotype.Component;
import sena.adso.appdonacion.exception.ConsentimientoNoFirmadoException;
import sena.adso.appdonacion.model.Consentimiento;
import sena.adso.appdonacion.model.Donante;

@Component
public class ValidacionConsentimiento
        implements ValidacionDonante {

    @Override
    public void validar(Donante donante) {

        Consentimiento consentimiento =
                donante.getConsentimiento();

        if (consentimiento == null) {

            throw new ConsentimientoNoFirmadoException(
                    "El donante no tiene consentimiento registrado"
            );
        }

        if (!consentimiento.isAceptaConsentimiento()) {

            throw new ConsentimientoNoFirmadoException(
                    "El donante no aceptó el consentimiento"
            );
        }

        if (consentimiento.getFirmaConsentimiento() == null
                || consentimiento.getFirmaConsentimiento().isBlank()) {

            throw new ConsentimientoNoFirmadoException(
                    "El consentimiento no tiene firma registrada"
            );
        }

    }

}