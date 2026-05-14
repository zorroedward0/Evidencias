package dao;

import config.H2TestDatabase;
import dto.Paciente;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PacienteDAOTest {

    private final boolean isTesting = true;

    @BeforeEach
    void setUp() {

        H2TestDatabase.crearTablas();
    }

    @AfterEach
    void tearDown() {

        H2TestDatabase.limpiarTablas();
    }

    @DisplayName("Pruebas de insercion")
    @Nested
    class InsercionPacienteTest {

        @Test
        @DisplayName("Debe insertar paciente correctamente")
        void insertarPacienteCorrectamente() {

            Paciente paciente = new Paciente();

            paciente.setNombres("Juan");
            paciente.setApellidos("Perez");
            paciente.setDocumento("100100100");
            paciente.setFechaNacimiento(Date.valueOf(LocalDate.of(2000, 5, 10)));
            paciente.setTelefono("3100000000");
            paciente.setEmail("juan@test.com");
            paciente.setEps("Nueva EPS");
            paciente.setVeredaBarrio("Centro");

            PacienteDAO dao = new PacienteDAO(isTesting);

            boolean resultado = dao.insertarPaciente(paciente);

            assertTrue(resultado);
        }

        @Test
        @DisplayName("No debe insertar paciente null")
        void insertarPacienteNull() {

            PacienteDAO dao = new PacienteDAO(isTesting);

            boolean resultado = dao.insertarPaciente(null);

            assertFalse(resultado);
        }

        @Test
        @DisplayName("No debe insertar paciente con documento duplicado")
        void insertarPacienteDocumentoDuplicado() {

            Paciente paciente1 = new Paciente();
            paciente1.setNombres("Carlos");
            paciente1.setApellidos("Lopez");
            paciente1.setDocumento("999");
            paciente1.setFechaNacimiento(Date.valueOf(LocalDate.of(1998, 1, 1)));
            paciente1.setTelefono("300000000");
            paciente1.setEmail("carlos@test.com");
            paciente1.setEps("Sanitas");
            paciente1.setVeredaBarrio("Norte");

            Paciente paciente2 = new Paciente();
            paciente2.setNombres("Pedro");
            paciente2.setApellidos("Ramirez");
            paciente2.setDocumento("999");
            paciente2.setFechaNacimiento(Date.valueOf(LocalDate.of(1995, 2, 2)));
            paciente2.setTelefono("311111111");
            paciente2.setEmail("pedro@test.com");
            paciente2.setEps("Compensar");
            paciente2.setVeredaBarrio("Sur");

            PacienteDAO dao = new PacienteDAO(isTesting);

            dao.insertarPaciente(paciente1);

            assertThrows(RuntimeException.class, () -> {
                dao.insertarPaciente(paciente2);
            });
        }

        @Test
        @DisplayName("No debe insertar paciente con email duplicado")
        void insertarPacienteEmailDuplicado() {

            Paciente paciente1 = new Paciente();
            paciente1.setNombres("Laura");
            paciente1.setApellidos("Diaz");
            paciente1.setDocumento("12345");
            paciente1.setFechaNacimiento(Date.valueOf(LocalDate.of(1990, 3, 3)));
            paciente1.setTelefono("320000000");
            paciente1.setEmail("correo@test.com");
            paciente1.setEps("Sura");
            paciente1.setVeredaBarrio("Occidente");

            Paciente paciente2 = new Paciente();
            paciente2.setNombres("Andrea");
            paciente2.setApellidos("Torres");
            paciente2.setDocumento("67890");
            paciente2.setFechaNacimiento(Date.valueOf(LocalDate.of(1992, 4, 4)));
            paciente2.setTelefono("321111111");
            paciente2.setEmail("correo@test.com");
            paciente2.setEps("Famisanar");
            paciente2.setVeredaBarrio("Oriente");

            PacienteDAO dao = new PacienteDAO(isTesting);

            dao.insertarPaciente(paciente1);

            assertThrows(RuntimeException.class, () -> {
                dao.insertarPaciente(paciente2);
            });
        }
    }

    @DisplayName("Pruebas de consulta")
    @Nested
    class ConsultaPacienteTest {

        @Test
        @DisplayName("Debe obtener paciente por id")
        void obtenerPacientePorId() {

            Paciente paciente = new Paciente();

            paciente.setNombres("Maria");
            paciente.setApellidos("Gomez");
            paciente.setDocumento("55555");
            paciente.setFechaNacimiento(Date.valueOf(LocalDate.of(2001, 7, 20)));
            paciente.setTelefono("300222222");
            paciente.setEmail("maria@test.com");
            paciente.setEps("Aliansalud");
            paciente.setVeredaBarrio("Centro");

            PacienteDAO dao = new PacienteDAO(isTesting);

            dao.insertarPaciente(paciente);

            Paciente obtenido = dao.obtenerPacientePorDocumento("55555");

            assertAll(
                    () -> assertNotNull(obtenido),
                    () -> assertEquals("Maria", obtenido.getNombres()),
                    () -> assertEquals("Gomez", obtenido.getApellidos()),
                    () -> assertEquals("55555", obtenido.getDocumento())
            );
        }

        @Test
        @DisplayName("Debe listar pacientes")
        void listarPacientes() {

            PacienteDAO dao = new PacienteDAO(isTesting);

            for (int i = 1; i <= 3; i++) {

                Paciente paciente = new Paciente();

                paciente.setNombres("Paciente" + i);
                paciente.setApellidos("Test");
                paciente.setDocumento("DOC" + i);
                paciente.setFechaNacimiento(Date.valueOf(LocalDate.of(2000, 1, i)));
                paciente.setTelefono("30000000" + i);
                paciente.setEmail("paciente" + i + "@test.com");
                paciente.setEps("EPS");
                paciente.setVeredaBarrio("Barrio");

                dao.insertarPaciente(paciente);
            }

            List<Paciente> lista = dao.listarPacientes();

            assertEquals(3, lista.size());
        }

        @Test
        @DisplayName("Debe contar pacientes correctamente")
        void contarPacientes() {

            PacienteDAO dao = new PacienteDAO(isTesting);

            for (int i = 1; i <= 5; i++) {

                Paciente paciente = new Paciente();

                paciente.setNombres("Paciente");
                paciente.setApellidos("Numero" + i);
                paciente.setDocumento("CC" + i);
                paciente.setFechaNacimiento(Date.valueOf(LocalDate.of(1999, 1, 1)));
                paciente.setTelefono("30000000" + i);
                paciente.setEmail("correo" + i + "@test.com");
                paciente.setEps("EPS");
                paciente.setVeredaBarrio("Zona");

                dao.insertarPaciente(paciente);
            }

            int total = dao.contarPacientes();

            assertEquals(5, total);
        }
    }

    @DisplayName("Pruebas de actualizacion y eliminacion")
    @Nested
    class ActualizacionEliminacionPacienteTest {

        @Test
        @DisplayName("Debe actualizar paciente")
        void actualizarPaciente() {

            Paciente paciente = new Paciente();

            paciente.setNombres("Luis");
            paciente.setApellidos("Martinez");
            paciente.setDocumento("777");
            paciente.setFechaNacimiento(Date.valueOf(LocalDate.of(1995, 8, 15)));
            paciente.setTelefono("300777777");
            paciente.setEmail("luis@test.com");
            paciente.setEps("Salud Total");
            paciente.setVeredaBarrio("Centro");

            PacienteDAO dao = new PacienteDAO(isTesting);

            dao.insertarPaciente(paciente);

            Paciente obtenido = dao.obtenerPacientePorDocumento("777");

            obtenido.setTelefono("311999999");

            boolean actualizado = dao.actualizarPaciente(obtenido);

            Paciente actualizadoPaciente = dao.obtenerPacientePorId(obtenido.getId());

            assertAll(
                    () -> assertTrue(actualizado),
                    () -> assertEquals("311999999", actualizadoPaciente.getTelefono())
            );
        }

        @Test
        @DisplayName("Debe eliminar paciente")
        void eliminarPaciente() {

            Paciente paciente = new Paciente();

            paciente.setNombres("Eliminar");
            paciente.setApellidos("Paciente");
            paciente.setDocumento("888");
            paciente.setFechaNacimiento(Date.valueOf(LocalDate.of(1990, 1, 1)));
            paciente.setTelefono("300888888");
            paciente.setEmail("eliminar@test.com");
            paciente.setEps("EPS");
            paciente.setVeredaBarrio("Barrio");

            PacienteDAO dao = new PacienteDAO(isTesting);

            boolean inserto = dao.insertarPaciente(paciente);
            System.out.println("El ins4erto fue : " + inserto);

            Paciente obtenido = dao.obtenerPacientePorDocumento("888");

            boolean eliminado = dao.eliminarPaciente(obtenido.getId());

            Paciente consulta = dao.obtenerPacientePorId(obtenido.getId());

            assertAll(
                    () -> assertTrue(eliminado),
                    () -> assertNull(consulta)
            );

        }
    }
}
