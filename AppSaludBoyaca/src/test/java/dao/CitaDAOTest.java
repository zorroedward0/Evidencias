package dao;

import config.H2TestDatabase;
import dto.Cita;
import dto.Especialidad;
import dto.Paciente;
import dto.Usuario;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CitaDAOTest {

    private final boolean isTesting = true;

    @BeforeEach
    void setUp() {

        H2TestDatabase.crearTablas();
    }

    @AfterEach
    void tearDown() {

        H2TestDatabase.limpiarTablas();
    }

    
    private int crearPacienteBase() {

        Paciente paciente = new Paciente();

        paciente.setNombres("Juan");
        paciente.setApellidos("Perez");
        paciente.setDocumento("100200300");
        paciente.setFechaNacimiento(Date.valueOf(LocalDate.of(2000, 5, 10)));
        paciente.setTelefono("3100000000");
        paciente.setEmail("juan@test.com");
        paciente.setEps("Nueva EPS");
        paciente.setVeredaBarrio("Centro");

        PacienteDAO dao = new PacienteDAO(isTesting);

        dao.insertarPaciente(paciente);

        return dao.obtenerPacientePorDocumento("100200300").getId();
    }

    private int crearMedicoBase() {

        Usuario medico = new Usuario();

        medico.setNombres("Carlos");
        medico.setApellidos("Lopez");
        medico.setDocumento("999999");
        medico.setEmail("medico@test.com");
        medico.setUsername("medico");
        medico.setPassword("123");
        medico.setRol("MEDICO");
        medico.setEspecialidad("Cardiologia");
        medico.setLangPreferido("es");
        medico.setActivo(true);

        UsuarioDAO dao = new UsuarioDAO(isTesting);

        dao.insertarUsuario(medico);

        return dao.validarLogin("medico", "123").getId();
    }

    private int crearEspecialidadBase() {

        Especialidad especialidad = new Especialidad();

        especialidad.setNombre("Cardiologia");
        especialidad.setDescripcion("Especialidad del corazon");

        EspecialidadDAO dao = new EspecialidadDAO(isTesting);

        dao.insertarEspecialidad(especialidad);

        return dao.obtenerEspecialidadPorNombre("Cardiologia").getId();
    }

    @DisplayName("Pruebas de insercion")
    @Nested
    class InsercionCitaTest {

        @Test
        @DisplayName("Debe insertar cita correctamente")
        void insertarCitaCorrectamente() {

            int idPaciente = crearPacienteBase();
            int idMedico = crearMedicoBase();
            int idEspecialidad = crearEspecialidadBase();

            Cita cita = new Cita();

            cita.setIdPaciente(idPaciente);
            cita.setIdMedico(idMedico);
            cita.setIdEspecialidad(idEspecialidad);
            cita.setFechaCita(LocalDate.now().plusDays(1));
            cita.setHoraCita(LocalTime.of(9, 0));
            cita.setMotivo("Control medico");
            cita.setEstado("PROGRAMADA");
            cita.setObservaciones("Paciente estable");
            cita.setIdRegistradoPor(idMedico);

            CitaDAO dao = new CitaDAO(isTesting);

            boolean resultado = dao.insertarCita(cita);

            assertTrue(resultado);
        }

        @Test
        @DisplayName("No debe insertar cita null")
        void insertarCitaNull() {

            CitaDAO dao = new CitaDAO(isTesting);

            boolean resultado = dao.insertarCita(null);

            assertFalse(resultado);
        }

        @Test
        @DisplayName("No debe insertar cita sin paciente")
        void insertarCitaSinPaciente() {

            Cita cita = new Cita();

            cita.setIdPaciente(0);
            cita.setIdMedico(1);
            cita.setIdEspecialidad(1);
            cita.setFechaCita(LocalDate.now());
            cita.setHoraCita(LocalTime.of(10, 0));

            CitaDAO dao = new CitaDAO(isTesting);

            boolean resultado = dao.insertarCita(cita);

            assertFalse(resultado);
        }
    }

    @DisplayName("Pruebas de consulta")
    @Nested
    class ConsultaCitaTest {

        @Test
        @DisplayName("Debe obtener cita por id")
        void obtenerCitaPorId() {

            int idPaciente = crearPacienteBase();
            int idMedico = crearMedicoBase();
            int idEspecialidad = crearEspecialidadBase();

            Cita cita = new Cita();

            cita.setIdPaciente(idPaciente);
            cita.setIdMedico(idMedico);
            cita.setIdEspecialidad(idEspecialidad);
            cita.setFechaCita(LocalDate.now().plusDays(2));
            cita.setHoraCita(LocalTime.of(11, 30));
            cita.setMotivo("Chequeo");
            cita.setEstado("CONFIRMADA");
            cita.setObservaciones("Sin observaciones");
            cita.setIdRegistradoPor(idMedico);

            CitaDAO dao = new CitaDAO(isTesting);

            dao.insertarCita(cita);

            List<Cita> lista = dao.listarCitas();

            Cita obtenida = dao.obtenerCitaPorId(lista.get(0).getId());

            assertAll(
                    () -> assertNotNull(obtenida),
                    () -> assertEquals("Chequeo", obtenida.getMotivo()),
                    () -> assertEquals("CONFIRMADA", obtenida.getEstado()),
                    () -> assertEquals("Juan Perez", obtenida.getNombrePaciente()),
                    () -> assertEquals("Carlos Lopez", obtenida.getNombreMedico()),
                    () -> assertEquals("Cardiologia", obtenida.getNombreEspecialidad())
            );
        }

        @Test
        @DisplayName("Debe listar citas")
        void listarCitas() {

            int idPaciente = crearPacienteBase();
            int idMedico = crearMedicoBase();
            int idEspecialidad = crearEspecialidadBase();

            CitaDAO dao = new CitaDAO(isTesting);

            for (int i = 1; i <= 3; i++) {

                Cita cita = new Cita();

                cita.setIdPaciente(idPaciente);
                cita.setIdMedico(idMedico);
                cita.setIdEspecialidad(idEspecialidad);
                cita.setFechaCita(LocalDate.now().plusDays(i));
                cita.setHoraCita(LocalTime.of(8 + i, 0));
                cita.setMotivo("Consulta " + i);
                cita.setEstado("PROGRAMADA");
                cita.setObservaciones("Observacion");
                cita.setIdRegistradoPor(idMedico);

                dao.insertarCita(cita);
            }

            List<Cita> lista = dao.listarCitas();

            assertEquals(3, lista.size());
        }

        @Test
        @DisplayName("Debe listar citas por paciente")
        void listarCitasPorPaciente() {

            int idPaciente = crearPacienteBase();
            int idMedico = crearMedicoBase();
            int idEspecialidad = crearEspecialidadBase();

            Cita cita = new Cita();

            cita.setIdPaciente(idPaciente);
            cita.setIdMedico(idMedico);
            cita.setIdEspecialidad(idEspecialidad);
            cita.setFechaCita(LocalDate.now().plusDays(5));
            cita.setHoraCita(LocalTime.of(7, 30));
            cita.setMotivo("Revision");
            cita.setEstado("PROGRAMADA");
            cita.setObservaciones("Ninguna");
            cita.setIdRegistradoPor(idMedico);

            CitaDAO dao = new CitaDAO(isTesting);

            dao.insertarCita(cita);

            List<Cita> lista = dao.listarCitasPorPaciente(idPaciente);

            assertEquals(1, lista.size());
        }
    }

    @DisplayName("Pruebas de actualizacion")
    @Nested
    class ActualizacionCitaTest {

        @Test
        @DisplayName("Debe actualizar estado de cita")
        void actualizarEstadoCita() {

            int idPaciente = crearPacienteBase();
            int idMedico = crearMedicoBase();
            int idEspecialidad = crearEspecialidadBase();

            Cita cita = new Cita();

            cita.setIdPaciente(idPaciente);
            cita.setIdMedico(idMedico);
            cita.setIdEspecialidad(idEspecialidad);
            cita.setFechaCita(LocalDate.now().plusDays(3));
            cita.setHoraCita(LocalTime.of(10, 30));
            cita.setMotivo("Consulta general");
            cita.setEstado("PROGRAMADA");
            cita.setObservaciones("Pendiente");
            cita.setIdRegistradoPor(idMedico);

            CitaDAO dao = new CitaDAO(isTesting);

            dao.insertarCita(cita);

            Cita obtenida = dao.listarCitas().get(0);

            obtenida.setEstado("ATENDIDA");

            boolean actualizado = dao.actualizarCita(obtenida);

            Cita actualizada = dao.obtenerCitaPorId(obtenida.getId());

            assertAll(
                    () -> assertTrue(actualizado),
                    () -> assertEquals("ATENDIDA", actualizada.getEstado())
            );
        }
    }

    @DisplayName("Pruebas de eliminacion")
    @Nested
    class EliminacionCitaTest {

        @Test
        @DisplayName("Debe eliminar cita")
        void eliminarCita() {

            int idPaciente = crearPacienteBase();
            int idMedico = crearMedicoBase();
            int idEspecialidad = crearEspecialidadBase();

            Cita cita = new Cita();

            cita.setIdPaciente(idPaciente);
            cita.setIdMedico(idMedico);
            cita.setIdEspecialidad(idEspecialidad);
            cita.setFechaCita(LocalDate.now().plusDays(4));
            cita.setHoraCita(LocalTime.of(2, 0));
            cita.setMotivo("Control");
            cita.setEstado("PROGRAMADA");
            cita.setObservaciones("Normal");
            cita.setIdRegistradoPor(idMedico);

            CitaDAO dao = new CitaDAO(isTesting);

            dao.insertarCita(cita);

            Cita obtenida = dao.listarCitas().get(0);

            boolean eliminado = dao.eliminar(obtenida.getId());

            Cita consulta = dao.obtenerCitaPorId(obtenida.getId());

            assertAll(
                    () -> assertTrue(eliminado),
                    () -> assertNull(consulta)
            );
        }
    }

    @DisplayName("Pruebas de restricciones")
    @Nested
    class RestriccionesCitaTest {

        @Test
        @DisplayName("No debe permitir cita con medico inexistente")
        void citaConMedicoInexistente() {

            int idPaciente = crearPacienteBase();
            int idEspecialidad = crearEspecialidadBase();

            Cita cita = new Cita();

            cita.setIdPaciente(idPaciente);
            cita.setIdMedico(999);
            cita.setIdEspecialidad(idEspecialidad);
            cita.setFechaCita(LocalDate.now().plusDays(1));
            cita.setHoraCita(LocalTime.of(9, 0));

            CitaDAO dao = new CitaDAO(isTesting);

            assertThrows(RuntimeException.class, () -> {
                dao.insertarCita(cita);
            });
        }

        @Test
        @DisplayName("No debe permitir cita con paciente inexistente")
        void citaConPacienteInexistente() {

            int idMedico = crearMedicoBase();
            int idEspecialidad = crearEspecialidadBase();

            Cita cita = new Cita();

            cita.setIdPaciente(999);
            cita.setIdMedico(idMedico);
            cita.setIdEspecialidad(idEspecialidad);
            cita.setFechaCita(LocalDate.now().plusDays(1));
            cita.setHoraCita(LocalTime.of(11, 0));

            CitaDAO dao = new CitaDAO(isTesting);

            assertThrows(RuntimeException.class, () -> {
                dao.insertarCita(cita);
            });
        }
    }
}
