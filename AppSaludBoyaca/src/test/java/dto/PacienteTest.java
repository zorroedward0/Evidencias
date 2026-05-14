package dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PacienteTest {

    @DisplayName("Comprobacion de datos Paciente.dto")
    @Nested
    class ValidacionCamposPacienteTest {

        @ParameterizedTest
        @CsvSource({
            "1, 'Juan', 'Perez', '12345678', '1990-05-10', '3001234567', 'juan.perez@gmail.com', 'Sura', 'Centro'",
            "2, 'Maria', 'Gomez', '87654321', '1985-08-15', '3019876543', 'maria.gomez@hotmail.com', 'Nueva EPS', 'San Jose'",
            "3, 'Carlos', 'Lopez', '11223344', '1992-11-20', '3024567890', 'carlos.lopez@yahoo.com', 'Sanitas', 'La Esperanza'",
            "4, 'Ana', 'Rodriguez', '55667788', '2000-01-05', '3201112233', 'ana.rodriguez@gmail.com', 'Compensar', 'El Progreso'",
            "5, 'Luis', 'Martinez', '99887766', '1978-07-30', '3154445566', 'luis.martinez@outlook.com', 'Coomeva', 'Villa Nueva'",
            "6, 'Elena', 'Castro', '44556677', '1995-03-12', '3109998877', 'elena.castro@gmail.com', '', ''"
        })
        @DisplayName("setear datos al objeto debe mantener toda la informacion original")
        void insertarDatosCorrectos_debeMantenerInformacionCompleta(
                int id,
                String nombres,
                String apellidos,
                String documento,
                String fechaNacimientoStr,
                String telefono,
                String email,
                String eps,
                String veredaBarrio) throws ParseException {

            Paciente paciente = new Paciente();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = sdf.parse(fechaNacimientoStr);

            paciente.setId(id);
            paciente.setNombres(nombres);
            paciente.setApellidos(apellidos);
            paciente.setDocumento(documento);
            paciente.setFechaNacimiento(fechaNacimiento);
            paciente.setTelefono(telefono);
            paciente.setEmail(email);
            paciente.setEps(eps);
            paciente.setVeredaBarrio(veredaBarrio);

            assertAll(
                    () -> assertEquals(id, paciente.getId()),
                    () -> assertEquals(nombres, paciente.getNombres()),
                    () -> assertEquals(apellidos, paciente.getApellidos()),
                    () -> assertEquals(documento, paciente.getDocumento()),
                    () -> assertEquals(fechaNacimiento, paciente.getFechaNacimiento()),
                    () -> assertEquals(telefono, paciente.getTelefono()),
                    () -> assertEquals(email, paciente.getEmail()),
                    () -> assertEquals(eps, paciente.getEps()),
                    () -> assertEquals(veredaBarrio, paciente.getVeredaBarrio())
            );
        }

        @ParameterizedTest
        @CsvSource({"Daniel German, Lopez Aguirre"})
        @DisplayName("getNombreCompleto() debe retornar nombres y apellidos con separacion")
        void getNombreCompleto_debeRetornarNombreYapellidoFormateado(String nombres, String apellidos) {

            Paciente paciente = new Paciente();
            paciente.setNombres(nombres);
            paciente.setApellidos(apellidos);

            String resusltado = paciente.getNombreCompleto();

            assertEquals("Daniel German Lopez Aguirre", resusltado);

        }

        @Test
        @DisplayName("Constructor vacio debe crear objeto correctamente")
        void constructorVacio_debeCrearObjeto() {

            Paciente paciente = new Paciente();

            assertAll(
                    () -> assertEquals(0, paciente.getId()),
                    () -> assertEquals(null, paciente.getNombres()),
                    () -> assertEquals(null, paciente.getApellidos()),
                    () -> assertEquals(null, paciente.getDocumento())
            );
        }

        @Test
        @DisplayName("Constructor completo debe asignar correctamente todos los datos")
        void constructorCompleto_debeAsignarTodosLosDatos() throws ParseException {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = sdf.parse("1990-05-10");

            Paciente paciente = new Paciente(
                    1,
                    "Juan",
                    "Perez",
                    "12345678",
                    fechaNacimiento,
                    "3001234567",
                    "juan.perez@gmail.com",
                    "Sura",
                    "Centro"
            );

            assertAll(
                    () -> assertEquals(1, paciente.getId()),
                    () -> assertEquals("Juan", paciente.getNombres()),
                    () -> assertEquals("Perez", paciente.getApellidos()),
                    () -> assertEquals("12345678", paciente.getDocumento()),
                    () -> assertEquals(fechaNacimiento, paciente.getFechaNacimiento()),
                    () -> assertEquals("3001234567", paciente.getTelefono()),
                    () -> assertEquals("juan.perez@gmail.com", paciente.getEmail()),
                    () -> assertEquals("Sura", paciente.getEps()),
                    () -> assertEquals("Centro", paciente.getVeredaBarrio())
            );
        }

        @Test
        @DisplayName("getNombreCompleto() debe conservar tildes y multiples nombres")
        void getNombreCompleto_debeConservarFormatoOriginal() {

            Paciente paciente = new Paciente();

            paciente.setNombres("José Andrés");
            paciente.setApellidos("Muñoz Gómez");

            String resultado = paciente.getNombreCompleto();

            assertAll(
                    () -> assertEquals("José Andrés Muñoz Gómez", resultado),
                    () -> assertEquals(true, resultado.contains("José")),
                    () -> assertEquals(true, resultado.contains("Muñoz"))
            );
        }
    }
}
