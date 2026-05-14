package dto;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CitaTest {

    @DisplayName("Comprobacion de datos Cita.dto")
    @Nested
    class ValidacionCamposCitaTest {

        @ParameterizedTest
        @CsvSource({
            "101, 201, 301, '2026-05-15', '09:00:00', 'Control anual', 'PROGRAMADA', 'Paciente en ayunas', '2026-05-13T10:00:00', 501, 'Juan Perez', 'MEDICO', 'Cardiologia'",
            "102, 202, 302, '2026-05-16', '10:30:00', 'Dolor de cabeza', 'CONFIRMADA', 'Traer examenes', '2026-05-13T11:15:00', 502, 'Maria Gomez', 'medico', 'Neurologia'",
            "103, 203, 303, '2026-05-17', '14:00:00', 'Chequeo general', 'ATENDIDA', 'Ninguna', '2026-05-13T12:30:00', 503, 'Carlos Lopez', 'Medico', 'Medicina General'",
            "104, 204, 304, '2026-05-18', '15:45:00', 'Revision puntos', 'CANCELADA', 'No asistio', '2026-05-13T14:00:00', 504, 'Ana Rodriguez', 'ADMIN', 'Traumatologia'",
            "105, 205, 305, '2026-05-19', '08:15:00', 'Urgencia leve', 'PROGRAMADA', 'Remitido', '2026-05-13T15:20:00', 505, 'Luis Martinez', 'ENFERMERO', 'Pediatria'",
            "106, 206, 306, '2026-05-20', '11:00:00', 'Consulta rutinaria', 'PROGRAMADA', '', '2026-05-13T16:45:00', 506, 'Elena Castro', '', 'Dermatologia'"
        })
        @DisplayName("insertar datos validos debe aceptar informacion y retornar la misma  ")
        void insertarDatosCorrectos_debeMantenerInformacionCompleta(int idPaciente, int idMedico, int idEspecialidad,
                LocalDate fechaCita, LocalTime horaCita, String motivo, String estado,
                String observaciones, LocalDateTime fechaRegistro, int idRegistradoPor,
                String nombrePaciente, String nombreMedico, String nombreEspecialidad) {

            Cita cita = new Cita();

            cita.setIdPaciente(idPaciente);
            cita.setIdMedico(idMedico);
            cita.setIdEspecialidad(idEspecialidad);
            cita.setFechaCita(fechaCita);
            cita.setHoraCita(horaCita);
            cita.setMotivo(motivo);
            cita.setEstado(estado);
            cita.setObservaciones(observaciones);
            cita.setFechaRegistro(fechaRegistro);
            cita.setIdRegistradoPor(idRegistradoPor);
            cita.setNombrePaciente(nombrePaciente);
            cita.setNombreMedico(nombreMedico);
            cita.setNombreEspecialidad(nombreEspecialidad);

            assertAll(
                    () -> assertEquals(idPaciente, cita.getIdPaciente()),
                    () -> assertEquals(idMedico, cita.getIdMedico()),
                    () -> assertEquals(idEspecialidad, cita.getIdEspecialidad()),
                    () -> assertEquals(fechaCita, cita.getFechaCita()),
                    () -> assertEquals(horaCita, cita.getHoraCita()),
                    () -> assertEquals(motivo, cita.getMotivo()),
                    () -> assertEquals(estado, cita.getEstado()),
                    () -> assertEquals(observaciones, cita.getObservaciones()),
                    () -> assertEquals(fechaRegistro, cita.getFechaRegistro()),
                    () -> assertEquals(idRegistradoPor, cita.getIdRegistradoPor()),
                    () -> assertEquals(nombrePaciente, cita.getNombrePaciente()),
                    () -> assertEquals(nombreMedico, cita.getNombreMedico()),
                    () -> assertEquals(nombreEspecialidad, cita.getNombreEspecialidad())
            );

        }
    }

    @DisplayName("Validacion estados permitidos")
    @Nested
    class EstadosCitaTest {

        @Test
        @DisplayName("setEstado() debe aceptar el estado 'PROGRAMADA' ")
        void deberiaAceptarEstadoProgramada() {

            Cita cita = new Cita();

            cita.setEstado("PROGRAMADA");

            assertEquals("PROGRAMADA", cita.getEstado());
        }

        @Test
        @DisplayName("setEstado() debe aceptar el estado 'CONFIRMADA' ")
        void deberiaAceptarEstadoConfirmada() {

            Cita cita = new Cita();

            cita.setEstado("CONFIRMADA");

            assertEquals("CONFIRMADA", cita.getEstado());
        }

        @Test
        @DisplayName("setEstado() debe aceptar el estado 'ATENDIDA' ")
        void deberiaAceptarEstadoAtendida() {

            Cita cita = new Cita();

            cita.setEstado("ATENDIDA");

            assertEquals("ATENDIDA", cita.getEstado());
        }

        @Test
        @DisplayName("setEstado() debe aceptar el estado 'CANCELADA' ")
        void deberiaAceptarEstadoCancelada() {

            Cita cita = new Cita();

            cita.setEstado("CANCELADA");

            assertEquals("CANCELADA", cita.getEstado());
        }

        @Test
        @DisplayName("setEstado() debe lanzar Exepcion si el estado no es valido")
        void deberiaLanzarErrorConEstadoInvalido() {

            Cita cita = new Cita();

            assertThrows(
                    IllegalArgumentException.class,
                    () -> cita.setEstado("PENDIENTE")
            );
        }

        @Test
        @DisplayName("setEstado() debe lanzar Exepcion si el estado es null")
        void deberiaLanzarErrorConEstadoNull() {

            Cita cita = new Cita();

            assertThrows(
                    IllegalArgumentException.class,
                    () -> cita.setEstado(null)
            );
        }
    }

}
