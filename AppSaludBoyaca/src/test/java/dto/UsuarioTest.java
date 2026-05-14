package dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class UsuarioTest {

    @DisplayName("Validacion de datos Usuario.dto")
    @Nested
    class ValidacionCamposUsuarioTest {

        @ParameterizedTest
        @CsvSource({
            "1, 'Juan', 'Perez', '123456', 'juan@gmail.com', 'juanp', '123', 'MEDICO', 'Cardiologia', 'es', true",
            "2, 'Maria', 'Lopez', '654321', 'maria@gmail.com', 'marial', '456', 'ENFERMERO', 'General', 'en', false",
            "3, 'Carlos', 'Ramirez', '987654', 'carlos@gmail.com', 'carlitos', '789', 'ADMINISTRADOR', 'Sistemas', 'fr', true"
        })
        @DisplayName("Setters deben mantener correctamente toda la informacion")
        void insertarDatosCorrectos_debeMantenerInformacionCompleta(
                int id,
                String nombres,
                String apellidos,
                String documento,
                String email,
                String username,
                String password,
                String rol,
                String especialidad,
                String langPreferido,
                boolean activo) {

            Usuario usuario = new Usuario();

            usuario.setId(id);
            usuario.setNombres(nombres);
            usuario.setApellidos(apellidos);
            usuario.setDocumento(documento);
            usuario.setEmail(email);
            usuario.setUsername(username);
            usuario.setPassword(password);
            usuario.setRol(rol);
            usuario.setEspecialidad(especialidad);
            usuario.setLangPreferido(langPreferido);
            usuario.setActivo(activo);

            assertAll(
                    () -> assertEquals(id, usuario.getId()),
                    () -> assertEquals(nombres, usuario.getNombres()),
                    () -> assertEquals(apellidos, usuario.getApellidos()),
                    () -> assertEquals(documento, usuario.getDocumento()),
                    () -> assertEquals(email, usuario.getEmail()),
                    () -> assertEquals(username, usuario.getUsername()),
                    () -> assertEquals(password, usuario.getPassword()),
                    () -> assertEquals(rol, usuario.getRol()),
                    () -> assertEquals(especialidad, usuario.getEspecialidad()),
                    () -> assertEquals(langPreferido, usuario.getLangPreferido()),
                    () -> assertEquals(activo, usuario.isActivo())
            );
        }

        @Test
        @DisplayName("Constructor vacio debe crear objeto correctamente")
        void constructorVacio_debeCrearObjeto() {

            Usuario usuario = new Usuario();

            assertAll(
                    () -> assertEquals(0, usuario.getId()),
                    () -> assertNull(usuario.getNombres()),
                    () -> assertNull(usuario.getApellidos()),
                    () -> assertNull(usuario.getDocumento()),
                    () -> assertFalse(usuario.isActivo())
            );
        }

        @Test
        @DisplayName("Constructor completo debe asignar correctamente los datos")
        void constructorCompleto_debeAsignarDatos() {

            Usuario usuario = new Usuario(
                    1,
                    "Juan",
                    "Perez",
                    "123456",
                    "juan@gmail.com",
                    "juanp",
                    "123",
                    "MEDICO",
                    "Cardiologia",
                    "es",
                    true
            );

            assertAll(
                    () -> assertEquals(1, usuario.getId()),
                    () -> assertEquals("Juan", usuario.getNombres()),
                    () -> assertEquals("Perez", usuario.getApellidos()),
                    () -> assertEquals("123456", usuario.getDocumento()),
                    () -> assertEquals("juan@gmail.com", usuario.getEmail()),
                    () -> assertEquals("juanp", usuario.getUsername()),
                    () -> assertEquals("123", usuario.getPassword()),
                    () -> assertEquals("MEDICO", usuario.getRol()),
                    () -> assertEquals("Cardiologia", usuario.getEspecialidad()),
                    () -> assertEquals("es", usuario.getLangPreferido()),
                    () -> assertTrue(usuario.isActivo())
            );
        }

        @Test
        @DisplayName("getNombreCompleto() debe unir nombres y apellidos con formato")
        void getNombreCompleto_debeRetornarNombreCompleto() {

            Usuario usuario = new Usuario();

            usuario.setNombres("Juan David");
            usuario.setApellidos("Perez Gomez");

            assertEquals(
                    "Juan David Perez Gomez",
                    usuario.getNombreCompleto()
            );
        }

    }

    @Nested
    @DisplayName("Validacion de roles Usuario")
    class ValidacionRolesUsuarioTest {

        @ParameterizedTest
        @CsvSource({
            "MEDICO",
            "medico",
            "MeDiCo"
        })
        @DisplayName("esMedico() debe reconocer variaciones de mayusculas")
        void esMedico_debeReconocerConMayusculasYMinusculas(String rol) {

            Usuario usuario = new Usuario();
            usuario.setRol(rol);

            assertTrue(usuario.esMedico());
        }

        @Test
        @DisplayName("Roles invalidos deben retornar false")
        void rolesInvalidos_debenRetornarFalse() {

            Usuario usuario = new Usuario();
            usuario.setRol("PACIENTE");

            assertAll(
                    () -> assertFalse(usuario.esMedico()),
                    () -> assertFalse(usuario.esEnfermero()),
                    () -> assertFalse(usuario.esRecepcionista()),
                    () -> assertFalse(usuario.esAdministrador())
            );
        }

        @Test
        @DisplayName("Rol null no debe lanzar excepciones")
        void rolNull_noDebeLanzarExcepciones() {

            Usuario usuario = new Usuario();
            usuario.setRol(null);

            assertAll(
                    () -> assertFalse(usuario.esMedico()),
                    () -> assertFalse(usuario.esEnfermero()),
                    () -> assertFalse(usuario.esRecepcionista()),
                    () -> assertFalse(usuario.esAdministrador())
            );
        }

        @Test
        @DisplayName("esMedico() debe retornar true para rol medico")
        void esMedico_debeRetornarTrue() {

            Usuario usuario = new Usuario();
            usuario.setRol("MEDICO");

            assertTrue(usuario.esMedico());
        }

        @Test
        @DisplayName("esEnfermero() debe retornar true para rol enfermero")
        void esEnfermero_debeRetornarTrue() {

            Usuario usuario = new Usuario();
            usuario.setRol("ENFERMERO");

            assertTrue(usuario.esEnfermero());
        }

        @Test
        @DisplayName("esRecepcionista() debe retornar true para rol recepcionista")
        void esRecepcionista_debeRetornarTrue() {

            Usuario usuario = new Usuario();
            usuario.setRol("RECEPCIONISTA");

            assertTrue(usuario.esRecepcionista());
        }

        @Test
        @DisplayName("esAdministrador() debe retornar true para rol administrador")
        void esAdministrador_debeRetornarTrue() {

            Usuario usuario = new Usuario();
            usuario.setRol("ADMINISTRADOR");

            assertTrue(usuario.esAdministrador());
        }
    }

}
