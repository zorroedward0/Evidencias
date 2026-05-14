package dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidadorDocumentoTest {

    private boolean validarDocumento(String documento) {

        if (documento == null || documento.trim().isEmpty()) {
            return false;
        }

        return documento.matches("\\d{6,12}");
    }

    @DisplayName("Validacion documentos correctos")
    @Nested
    class DocumentosValidosTest {

        @ParameterizedTest
        @ValueSource(strings = {
            "123456",
            "1020304050",
            "987654321",
            "123456789012",
            "777888999"
        })
        void documentoValido_debeRetornarTrue(String documento) {

            assertTrue(validarDocumento(documento));

        }
    }

    @DisplayName("Validacion documentos incorrectos")
    @Nested
    class DocumentosInvalidosTest {

        @ParameterizedTest
        @ValueSource(strings = {
            "abc123",
            "12",
            "1234567890123456",
            "123-456",
            "documento"
        })
        void documentoInvalido_debeRetornarFalse(String documento) {

            assertFalse(validarDocumento(documento));

        }

        @ParameterizedTest
        @NullAndEmptySource
        void documentoNullOVacio_debeRetornarFalse(String documento) {

            assertFalse(validarDocumento(documento));

        }
    }
}
