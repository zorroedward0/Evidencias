package config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class H2TestDatabase {


    public static void crearTablas() {
        ejecutarScript("db/schema.sql");
    }

    public static void limpiarTablas() {
        ejecutarScript("db/clean.sql");
    }

    private static void ejecutarScript(String rutaArchivo) {

        try (
                Connection con = Conexion.establecerConexion(true); Statement st = con.createStatement()) {

            InputStream is = H2TestDatabase.class
                    .getClassLoader()
                    .getResourceAsStream(rutaArchivo);

            if (is == null) {
                throw new RuntimeException(
                        "No se encontró el archivo: " + rutaArchivo);
            }

            String sql = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .collect(Collectors.joining("\n"));

            String[] sentencias = sql.split(";");

            for (String sentencia : sentencias) {

                if (!sentencia.trim().isEmpty()) {
                    st.execute(sentencia);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Error ejecutando script SQL: " + rutaArchivo,
                    e
            );
        }
    }
}
