package dao;

import config.Conexion;
import config.H2TestDatabase;
import dto.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDAOTest {

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
    class InsercionUsuarioTest {

        @Test
        @DisplayName("Debe insertar usuario correctamente")
        void insertarUsuarioCorrectamente() {

            Usuario usuario = new Usuario();

            usuario.setNombres("Andres");
            usuario.setApellidos("Martinez");
            usuario.setDocumento("123456");
            usuario.setEmail("andres@test.com");
            usuario.setUsername("andres");
            usuario.setPassword("123");
            usuario.setRol("ADMIN");
            usuario.setEspecialidad("General");
            usuario.setLangPreferido("es");
            usuario.setActivo(true);

            UsuarioDAO dao = new UsuarioDAO(isTesting);

            boolean resultado = dao.insertarUsuario(usuario);

            assertTrue(resultado);
        }

        @Test
        @DisplayName("No debe insertar usuario null")
        void insertarUsuarioNull() {

            UsuarioDAO dao = new UsuarioDAO(isTesting);

            boolean resultado = dao.insertarUsuario(null);

            assertFalse(resultado);
        }

        @Test
        @DisplayName("No debe insertar usuario con documento duplicado")
        void documentoDuplicado() {

            Usuario usuario1 = new Usuario();

            usuario1.setNombres("Carlos");
            usuario1.setApellidos("Perez");
            usuario1.setDocumento("999");
            usuario1.setEmail("correo1@test.com");
            usuario1.setUsername("user1");
            usuario1.setPassword("123");
            usuario1.setRol("MEDICO");
            usuario1.setEspecialidad("Cardiologia");
            usuario1.setLangPreferido("es");
            usuario1.setActivo(true);

            Usuario usuario2 = new Usuario();

            usuario2.setNombres("Pedro");
            usuario2.setApellidos("Lopez");
            usuario2.setDocumento("999");
            usuario2.setEmail("correo2@test.com");
            usuario2.setUsername("user2");
            usuario2.setPassword("123");
            usuario2.setRol("MEDICO");
            usuario2.setEspecialidad("Pediatria");
            usuario2.setLangPreferido("es");
            usuario2.setActivo(true);

            UsuarioDAO dao = new UsuarioDAO(isTesting);

            dao.insertarUsuario(usuario1);

            assertThrows(RuntimeException.class, () -> {
                dao.insertarUsuario(usuario2);
            });
        }
    }

    @DisplayName("Pruebas de consulta")
    @Nested
    class ConsultaUsuarioTest {

        @Test
        @DisplayName("Debe validar login")
        void validarLogin() {

            Usuario usuario = new Usuario();

            usuario.setNombres("Laura");
            usuario.setApellidos("Gomez");
            usuario.setDocumento("123123");
            usuario.setEmail("laura@test.com");
            usuario.setUsername("laura");
            usuario.setPassword("123");
            usuario.setRol("RECEPCIONISTA");
            usuario.setEspecialidad("N/A");
            usuario.setLangPreferido("es");
            usuario.setActivo(true);

            UsuarioDAO dao = new UsuarioDAO(isTesting);

            dao.insertarUsuario(usuario);

            Usuario login = dao.validarLogin("laura", "123");

            assertAll(
                    () -> assertNotNull(login),
                    () -> assertEquals("Laura", login.getNombres()),
                    () -> assertEquals("RECEPCIONISTA", login.getRol())
            );
        }

        @Test
        @DisplayName("Debe listar usuarios")
        void listarUsuarios() {

            UsuarioDAO dao = new UsuarioDAO(isTesting);

            for (int i = 1; i <= 3; i++) {

                Usuario usuario = new Usuario();

                usuario.setNombres("Usuario");
                usuario.setApellidos("Numero" + i);
                usuario.setDocumento("DOC" + i);
                usuario.setEmail("correo" + i + "@test.com");
                usuario.setUsername("user" + i);
                usuario.setPassword("123");
                usuario.setRol("ADMIN");
                usuario.setEspecialidad("General");
                usuario.setLangPreferido("es");
                usuario.setActivo(true);

                dao.insertarUsuario(usuario);
            }

            List<Usuario> lista = dao.listarUsuarios();

            assertEquals(3, lista.size());
        }
    }

    @DisplayName("Pruebas de actualizacion y eliminacion")
    @Nested
    class ActualizacionEliminacionUsuarioTest {

        @Test
        @DisplayName("Debe actualizar usuario")
        void actualizarUsuario() {

            Usuario usuario = new Usuario();

            usuario.setNombres("Mario");
            usuario.setApellidos("Ruiz");
            usuario.setDocumento("7777");
            usuario.setEmail("mario@test.com");
            usuario.setUsername("mario");
            usuario.setPassword("123");
            usuario.setRol("MEDICO");
            usuario.setEspecialidad("Neurologia");
            usuario.setLangPreferido("es");
            usuario.setActivo(true);

            UsuarioDAO dao = new UsuarioDAO(isTesting);

            dao.insertarUsuario(usuario);

            Usuario obtenido = dao.validarLogin("mario", "123");

            obtenido.setEspecialidad("Pediatria");

            boolean actualizado = dao.actualizarUsuario(obtenido);

            Usuario actualizadoUsuario = dao.obtenerUsuarioPorId(obtenido.getId());

            assertAll(
                    () -> assertTrue(actualizado),
                    () -> assertEquals("Pediatria", actualizadoUsuario.getEspecialidad())
            );
        }

        @Test
        @DisplayName("Debe eliminar usuario")
        void eliminarUsuario() {

            Usuario usuario = new Usuario();

            usuario.setNombres("Eliminar");
            usuario.setApellidos("Usuario");
            usuario.setDocumento("8888");
            usuario.setEmail("eliminar@test.com");
            usuario.setUsername("eliminar");
            usuario.setPassword("123");
            usuario.setRol("ADMIN");
            usuario.setEspecialidad("N/A");
            usuario.setLangPreferido("es");
            usuario.setActivo(true);

            UsuarioDAO dao = new UsuarioDAO(isTesting);

            dao.insertarUsuario(usuario);

            Usuario obtenido = dao.validarLogin("eliminar", "123");

            boolean eliminado = dao.eliminarUsuario(obtenido.getId());

            Usuario consulta = dao.obtenerUsuarioPorId(obtenido.getId());

            assertAll(
                    () -> assertTrue(eliminado),
                    () -> assertNull(consulta)
            );
        }
    }
}
