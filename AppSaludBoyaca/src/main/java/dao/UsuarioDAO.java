package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Usuario;
import config.Conexion;

public class UsuarioDAO {

    boolean isTestingD = false;

    public UsuarioDAO(boolean isTesting) {
        this.isTestingD = isTesting;
    }

    public UsuarioDAO() {

    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuarios ORDER BY apellidos, nombres";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios", e);
        }

        return usuarios;
    }

    public boolean insertarUsuario(Usuario usuario) {
        if (usuario == null
                || usuario.getNombres() == null
                || usuario.getApellidos() == null
                || usuario.getDocumento() == null
                || usuario.getEmail() == null
                || usuario.getUsername() == null
                || usuario.getPassword() == null
                || usuario.getRol() == null) {
            return false;
        }

        String sql = "INSERT INTO usuarios (nombres, apellidos, documento, email, username, password, rol, especialidad, lang_preferido, activo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getDocumento());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getUsername());
            ps.setString(6, usuario.getPassword());
            ps.setString(7, usuario.getRol());
            ps.setString(8, usuario.getEspecialidad());
            ps.setString(9, usuario.getLangPreferido());
            ps.setBoolean(10, usuario.isActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar usuario", e);
        }
    }

    public boolean actualizarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() <= 0) {
            return false;
        }

        String sql = "UPDATE usuarios SET nombres=?, apellidos=?, documento=?, email=?, username=?, password=?, rol=?, especialidad=?, lang_preferido=?, activo=? WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getDocumento());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getUsername());
            ps.setString(6, usuario.getPassword());
            ps.setString(7, usuario.getRol());
            ps.setString(8, usuario.getEspecialidad());
            ps.setString(9, usuario.getLangPreferido());
            ps.setBoolean(10, usuario.isActivo());
            ps.setInt(11, usuario.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario", e);
        }
    }

    public boolean eliminarUsuario(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }

        String sql = "DELETE FROM usuarios WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }

    public Usuario obtenerUsuarioPorId(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }

        String sql = "SELECT * FROM usuarios WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuario", e);
        }

        return null;
    }

    public Usuario validarLogin(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        String sql = "SELECT * FROM usuarios WHERE username=? AND password=? AND activo=true";

        try (Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al validar login", e);
        }

        return null;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("documento"),
                rs.getString("email"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("rol"),
                rs.getString("especialidad"),
                rs.getString("lang_preferido"),
                rs.getBoolean("activo")
        );
    }
}
