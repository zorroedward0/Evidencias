package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public static Connection establecerConexion(boolean isTesting) throws SQLException {
        Connection conex;
        if (isTesting) {

            final String URL = "jdbc:h2:file:./target/h2test/saludboyaca_test;DB_CLOSE_DELAY=-1";
            final String USER = "sa";
            final String PASSWORD = "";

            try {
                Class.forName("org.h2.Driver");

                return DriverManager.getConnection(URL, USER, PASSWORD);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Error al conectar con H2", e);
            }

        }
        
        final String HOST = "tramway.proxy.rlwy.net";
        final String PORT = "51618";
        final String DATABASE = "railway";
        final String USER = "root";
        final String PASSWORD = "HvxDqrxzpuStBRbNzkLkmBgCVRtniSHA";

        String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conex = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL no encontrado", e);
        }

        return conex;

    }
}
