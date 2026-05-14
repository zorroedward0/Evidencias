package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dto.LogAcceso;
import config.Conexion;

public class LogAccesoDAO {

    boolean isTestingD = false;

    public LogAccesoDAO(boolean isTesting) {
        this.isTestingD = isTesting;
    }

    public LogAccesoDAO() {

    }

    public boolean insertarLog(LogAcceso log) {
        if (log == null
                || log.getAccion() == null || log.getAccion().isEmpty()
                || log.getResultado() == null || log.getResultado().isEmpty()) {
            return false;
        }

        String sql = "INSERT INTO log_accesos (id_usuario, username, accion, ip, resultado, fecha) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            if (log.getIdUsuario() > 0) {
                ps.setInt(1, log.getIdUsuario());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }

            ps.setString(2, log.getUsername());
            ps.setString(3, log.getAccion());
            ps.setString(4, log.getIp());
            ps.setString(5, log.getResultado());

            if (log.getFecha() != null) {
                ps.setTimestamp(6, Timestamp.valueOf(log.getFecha()));
            } else {
                ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar log de acceso", e);
        }
    }

    public LogAcceso obtenerPorId(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }

        String sql = "SELECT * FROM log_accesos WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearLog(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener log", e);
        }

        return null;
    }

    public List<LogAcceso> listarLogs() {
        List<LogAcceso> lista = new ArrayList<>();

        String sql = "SELECT * FROM log_accesos ORDER BY fecha DESC";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearLog(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar logs", e);
        }

        return lista;
    }

    public List<LogAcceso> listarPorUsuario(int idUsuario) {
        List<LogAcceso> lista = new ArrayList<>();

        if (idUsuario <= 0) {
            return lista;
        }

        String sql = "SELECT * FROM log_accesos WHERE id_usuario=? ORDER BY fecha DESC";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearLog(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar logs por usuario", e);
        }

        return lista;
    }

    public List<LogAcceso> listarPorFecha(java.time.LocalDateTime desde, java.time.LocalDateTime hasta) {
        List<LogAcceso> lista = new ArrayList<>();

        if (desde == null || hasta == null) {
            return lista;
        }

        String sql = "SELECT * FROM log_accesos WHERE fecha BETWEEN ? AND ? ORDER BY fecha DESC";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(desde));
            ps.setTimestamp(2, Timestamp.valueOf(hasta));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearLog(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar logs por fecha", e);
        }

        return lista;
    }

    private LogAcceso mapearLog(ResultSet rs) throws SQLException {
        return new LogAcceso(
                rs.getInt("id"),
                rs.getInt("id_usuario"),
                rs.getString("username"),
                rs.getString("accion"),
                rs.getString("ip"),
                rs.getString("resultado"),
                rs.getTimestamp("fecha") != null ? rs.getTimestamp("fecha").toLocalDateTime() : null
        );
    }
}
