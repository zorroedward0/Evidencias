package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dto.Especialidad;
import config.Conexion;

public class EspecialidadDAO {

    boolean isTestingD = false;

    public EspecialidadDAO(boolean isTesting) {
        this.isTestingD = isTesting;
    }

    public EspecialidadDAO() {

    }

    public boolean insertarEspecialidad(Especialidad especialidad) {
        if (especialidad == null || especialidad.getNombre() == null || especialidad.getNombre().isEmpty()) {
            return false;
        }

        String sql = "INSERT INTO especialidades (nombre, descripcion) VALUES (?, ?)";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, especialidad.getNombre());
            ps.setString(2, especialidad.getDescripcion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar especialidad", e);
        }
    }

    public boolean actualizarEspecialidad(Especialidad especialidad) {
        if (especialidad == null || especialidad.getId() <= 0
                || especialidad.getNombre() == null || especialidad.getNombre().isEmpty()) {
            return false;
        }

        String sql = "UPDATE especialidades SET nombre=?, descripcion=? WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, especialidad.getNombre());
            ps.setString(2, especialidad.getDescripcion());
            ps.setInt(3, especialidad.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar especialidad", e);
        }
    }

    public boolean eliminar(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }

        String sql = "DELETE FROM especialidades WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar especialidad", e);
        }
    }

    public Especialidad obtenerEspecialidadPorId(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }

        String sql = "SELECT * FROM especialidades WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearEspecialidad(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener especialidad", e);
        }

        return null;
    }

    public Especialidad obtenerEspecialidadPorNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return null;
        }

        String sql = "SELECT * FROM especialidades WHERE nombre=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearEspecialidad(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener especialidad por nombre", e);
        }

        return null;
    }

    public List<Especialidad> listarEspecialidades() {
        List<Especialidad> lista = new ArrayList<>();

        String sql = "SELECT * FROM especialidades ORDER BY nombre";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearEspecialidad(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar especialidades", e);
        }

        return lista;
    }

    private Especialidad mapearEspecialidad(ResultSet rs) throws SQLException {
        return new Especialidad(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("descripcion")
        );
    }
}
