package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dto.Paciente;
import config.Conexion;

public class PacienteDAO {

    boolean isTestingD = false;

    public PacienteDAO(boolean isTesting) {
        this.isTestingD = isTesting;
    }

    public PacienteDAO() {

    }
    public boolean insertarPaciente(Paciente paciente) {
        if (paciente == null
                || paciente.getNombres() == null
                || paciente.getApellidos() == null
                || paciente.getDocumento() == null
                || paciente.getFechaNacimiento() == null) {
            return false;
        }

        String sql = "INSERT INTO pacientes (nombres, apellidos, documento, fecha_nacimiento, telefono, email, eps, vereda_barrio) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, paciente.getNombres());
            ps.setString(2, paciente.getApellidos());
            ps.setString(3, paciente.getDocumento());
            ps.setDate(4, new Date(paciente.getFechaNacimiento().getTime()));
            ps.setString(5, paciente.getTelefono());
            ps.setString(6, paciente.getEmail());
            ps.setString(7, paciente.getEps());
            ps.setString(8, paciente.getVeredaBarrio());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar paciente", e);
        }
    }

    public int contarPacientes() {
        String sql = "SELECT COUNT(*) FROM pacientes";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error contar pacientes", e);
        }

        return 0;
    }

    public boolean actualizarPaciente(Paciente paciente) {
        if (paciente == null
                || paciente.getId() <= 0
                || paciente.getFechaNacimiento() == null) {
            return false;
        }

        String sql = "UPDATE pacientes SET nombres=?, apellidos=?, documento=?, fecha_nacimiento=?, telefono=?, email=?, eps=?, vereda_barrio=? WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, paciente.getNombres());
            ps.setString(2, paciente.getApellidos());
            ps.setString(3, paciente.getDocumento());
            ps.setDate(4, new Date(paciente.getFechaNacimiento().getTime()));
            ps.setString(5, paciente.getTelefono());
            ps.setString(6, paciente.getEmail());
            ps.setString(7, paciente.getEps());
            ps.setString(8, paciente.getVeredaBarrio());
            ps.setInt(9, paciente.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar paciente", e);
        }
    }

    public boolean eliminarPaciente(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }

        String sql = "DELETE FROM pacientes WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar paciente", e);
        }
    }

    public Paciente obtenerPacientePorId(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }

        String sql = "SELECT * FROM pacientes WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPaciente(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener paciente", e);
        }

        return null;
    }

    public Paciente obtenerPacientePorDocumento(String documento) {
        if (documento == null || documento.isEmpty()) {
            return null;
        }

        String sql = "SELECT * FROM pacientes WHERE documento=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, documento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPaciente(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener paciente por documento", e);
        }

        return null;
    }

    public List<Paciente> listarPacientes() {
        List<Paciente> pacientes = new ArrayList<>();

        String sql = "SELECT * FROM pacientes ORDER BY apellidos, nombres";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pacientes.add(mapearPaciente(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar pacientes", e);
        }

        return pacientes;
    }

    private Paciente mapearPaciente(ResultSet rs) throws SQLException {
        return new Paciente(
                rs.getInt("id"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("documento"),
                rs.getDate("fecha_nacimiento"),
                rs.getString("telefono"),
                rs.getString("email"),
                rs.getString("eps"),
                rs.getString("vereda_barrio")
        );
    }
}
