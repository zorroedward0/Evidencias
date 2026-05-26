package sena.adso.appdonacion.validation;

import org.springframework.stereotype.Component;
import sena.adso.appdonacion.exception.DonanteNoAptoException;
import sena.adso.appdonacion.model.Donante;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class ValidacionTiempoUltimaDonacion
        implements ValidacionDonante {

    @Override
    public void validar(Donante donante) {

        if (donante.getFechaUltimaDonacion() == null) {
            return;
        }

        long meses = ChronoUnit.MONTHS.between(
                donante.getFechaUltimaDonacion(),
                LocalDate.now()
        );

        if (meses < 3) {

            throw new DonanteNoAptoException(
                    "Deben pasar mínimo 3 meses entre donaciones"
            );
        }

    }

}