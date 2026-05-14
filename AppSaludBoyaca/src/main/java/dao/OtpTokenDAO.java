package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dto.OtpToken;
import config.Conexion;

public class OtpTokenDAO {

    boolean isTestingD = false;

    public OtpTokenDAO(boolean isTesting) {
        this.isTestingD = isTesting;
    }

    public OtpTokenDAO() {

    }

    public boolean insertarToken(OtpToken token) {
        if (token == null
                || token.getIdUsuario() <= 0
                || token.getCodigo() == null || token.getCodigo().isEmpty()
                || token.getExpiraEn() == null) {
            return false;
        }

        String sql = "INSERT INTO otp_tokens (id_usuario, codigo, expira_en, usado) VALUES (?, ?, ?, ?)";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, token.getIdUsuario());
            ps.setString(2, token.getCodigo());
            ps.setTimestamp(3, Timestamp.valueOf(token.getExpiraEn()));
            ps.setBoolean(4, token.isUsado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar OTP", e);
        }
    }

    public OtpToken obtenerToken(String codigo) {
        if (codigo == null || codigo.isEmpty()) {
            return null;
        }

        String sql = "SELECT * FROM otp_tokens WHERE codigo=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearToken(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener OTP", e);
        }

        return null;
    }

    public OtpToken obtenerTokenValido(int idUsuario, String codigo) {
        if (idUsuario <= 0 || codigo == null || codigo.isEmpty()) {
            return null;
        }

        String sql = "SELECT * FROM otp_tokens WHERE id_usuario=? AND codigo=? AND usado=false AND expira_en > CURRENT_TIMESTAMP";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setString(2, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearToken(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener OTP válido", e);
        }

        return null;
    }

    public boolean marcarComoUsado(int id) {
        if (id <= 0) {
            return false;
        }

        String sql = "UPDATE otp_tokens SET usado=true WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al marcar OTP como usado", e);
        }
    }

    public boolean eliminar(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }

        String sql = "DELETE FROM otp_tokens WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar OTP", e);
        }
    }

    public List<OtpToken> listarPorUsuario(int idUsuario) {
        List<OtpToken> lista = new ArrayList<>();

        if (idUsuario <= 0) {
            return lista;
        }

        String sql = "SELECT * FROM otp_tokens WHERE id_usuario=? ORDER BY fecha_gen DESC";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearToken(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar OTPs", e);
        }

        return lista;
    }

    private OtpToken mapearToken(ResultSet rs) throws SQLException {
        return new OtpToken(
                rs.getInt("id"),
                rs.getInt("id_usuario"),
                rs.getString("codigo"),
                rs.getTimestamp("fecha_gen") != null ? rs.getTimestamp("fecha_gen").toLocalDateTime() : null,
                rs.getTimestamp("expira_en") != null ? rs.getTimestamp("expira_en").toLocalDateTime() : null,
                rs.getBoolean("usado")
        );
    }
}
