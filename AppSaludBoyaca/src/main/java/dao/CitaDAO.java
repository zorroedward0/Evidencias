package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dto.Cita;
import config.Conexion;

public class CitaDAO {

    boolean isTestingD = false;

    public CitaDAO(boolean isTesting) {
        this.isTestingD = isTesting;
    }

    public CitaDAO() {

    }

    public boolean insertarCita(Cita cita) {
        if (cita == null
                || cita.getIdPaciente() <= 0
                || cita.getIdMedico() <= 0
                || cita.getIdEspecialidad() <= 0
                || cita.getFechaCita() == null
                || cita.getHoraCita() == null) {
            return false;
        }

        String sql = "INSERT INTO citas (id_paciente, id_medico, id_especialidad, fecha_cita, hora_cita, motivo, estado, observaciones, id_registrado_por) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cita.getIdPaciente());
            ps.setInt(2, cita.getIdMedico());
            ps.setInt(3, cita.getIdEspecialidad());
            ps.setDate(4, Date.valueOf(cita.getFechaCita()));
            ps.setTime(5, Time.valueOf(cita.getHoraCita()));
            ps.setString(6, cita.getMotivo());
            ps.setString(7, cita.getEstado() != null ? cita.getEstado() : "PROGRAMADA");
            ps.setString(8, cita.getObservaciones());

            if (cita.getIdRegistradoPor() > 0) {
                ps.setInt(9, cita.getIdRegistradoPor());
            } else {
                ps.setNull(9, Types.INTEGER);
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar cita", e);
        }
    }

    public boolean actualizarCita(Cita cita) {
        if (cita == null
                || cita.getId() <= 0
                || cita.getIdPaciente() <= 0
                || cita.getIdMedico() <= 0
                || cita.getFechaCita() == null
                || cita.getHoraCita() == null) {
            return false;
        }

        String sql = "UPDATE citas SET id_paciente=?, id_medico=?, id_especialidad=?, fecha_cita=?, hora_cita=?, motivo=?, estado=?, observaciones=?, id_registrado_por=? WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cita.getIdPaciente());
            ps.setInt(2, cita.getIdMedico());
            ps.setInt(3, cita.getIdEspecialidad());
            ps.setDate(4, Date.valueOf(cita.getFechaCita()));
            ps.setTime(5, Time.valueOf(cita.getHoraCita()));
            ps.setString(6, cita.getMotivo());
            ps.setString(7, cita.getEstado());
            ps.setString(8, cita.getObservaciones());

            if (cita.getIdRegistradoPor() > 0) {
                ps.setInt(9, cita.getIdRegistradoPor());
            } else {
                ps.setNull(9, Types.INTEGER);
            }

            ps.setInt(10, cita.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar cita", e);
        }
    }

    public boolean eliminar(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }

        String sql = "DELETE FROM citas WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar cita", e);
        }
    }

    public Cita obtenerCitaPorId(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }

        String sql = "SELECT c.*, "
                + "COALESCE(CONCAT(p.nombres, ' ', p.apellidos), '') AS nombre_paciente, "
                + "COALESCE(CONCAT(u.nombres, ' ', u.apellidos), '') AS nombre_medico, "
                + "COALESCE(e.nombre, '') AS nombre_especialidad "
                + "FROM citas c "
                + "LEFT JOIN pacientes p ON c.id_paciente = p.id "
                + "LEFT JOIN usuarios u ON c.id_medico = u.id AND u.rol = 'MEDICO' "
                + "LEFT JOIN especialidades e ON c.id_especialidad = e.id "
                + "WHERE c.id = ?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCitaConDetalles(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener cita", e);
        }

        return null;
    }

    public List<Cita> listarCitas() {
        List<Cita> lista = new ArrayList<>();

        String sql = "SELECT c.*, "
                + "CONCAT(p.nombres, ' ', p.apellidos) AS nombre_paciente, "
                + "CONCAT(u.nombres, ' ', u.apellidos) AS nombre_medico "
                + "FROM citas c "
                + "LEFT JOIN pacientes p ON c.id_paciente = p.id "
                + "LEFT JOIN usuarios u ON c.id_medico = u.id AND u.rol = 'MEDICO' "
                + "ORDER BY c.fecha_cita, c.hora_cita";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearCita(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar citas", e);
        }

        return lista;
    }

    public List<Cita> listarCitasPorMedico(int idMedico) {
        List<Cita> lista = new ArrayList<>();

        if (idMedico <= 0) {
            return lista;
        }

        String sql = "SELECT c.*, "
                + "CONCAT(p.nombres, ' ', p.apellidos) AS nombre_paciente, "
                + "CONCAT(u.nombres, ' ', u.apellidos) AS nombre_medico, "
                + "e.nombre AS nombre_especialidad "
                + "FROM citas c "
                + "JOIN pacientes p ON c.id_paciente = p.id "
                + "JOIN usuarios u ON c.id_medico = u.id AND u.rol = 'MEDICO' "
                + "JOIN especialidades e ON c.id_especialidad = e.id "
                + "WHERE c.id_medico=? "
                + "ORDER BY c.fecha_cita, c.hora_cita";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idMedico);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearCita(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar citas por médico", e);
        }

        return lista;
    }

    public List<Cita> obtenerCitasPorDocumentoPaciente(String documento) {
        List<Cita> lista = new ArrayList<>();

        if (documento == null || documento.trim().isEmpty()) {
            return lista;
        }

        String sql = "SELECT c.*, "
                + "CONCAT(p.nombres, ' ', p.apellidos) AS nombre_paciente, "
                + "CONCAT(u.nombres, ' ', u.apellidos) AS nombre_medico, "
                + "e.nombre AS nombre_especialidad "
                + "FROM citas c "
                + "JOIN pacientes p ON c.id_paciente = p.id "
                + "JOIN usuarios u ON c.id_medico = u.id AND u.rol = 'MEDICO' "
                + "JOIN especialidades e ON c.id_especialidad = e.id "
                + "WHERE p.documento = ? AND estado IN ('PROGRAMADA','CONFIRMADA') "
                + "ORDER BY c.fecha_cita, c.hora_cita";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, documento);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearCitaConDetalles(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener citas por documento del paciente", e);
        }

        return lista;
    }

    public List<Cita> listarCitasPorPaciente(int idPaciente) {
        List<Cita> lista = new ArrayList<>();

        if (idPaciente <= 0) {
            return lista;
        }

        String sql = "SELECT c.*, "
                + "CONCAT(p.nombres, ' ', p.apellidos) AS nombre_paciente, "
                + "CONCAT(u.nombres, ' ', u.apellidos) AS nombre_medico, "
                + "e.nombre AS nombre_especialidad "
                + "FROM citas c "
                + "JOIN pacientes p ON c.id_paciente = p.id "
                + "JOIN usuarios u ON c.id_medico = u.id AND u.rol = 'MEDICO' "
                + "JOIN especialidades e ON c.id_especialidad = e.id "
                + "WHERE c.id_paciente=? "
                + "ORDER BY c.fecha_cita, c.hora_cita";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearCita(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar citas por paciente", e);
        }

        return lista;
    }

    public int contarCitasHoy(int idMedico, String rol) {
        String sql = "SELECT COUNT(*) FROM citas WHERE fecha_cita = CURRENT_DATE";

        if ("MEDICO".equals(rol)) {
            sql += " AND id_medico = ?";
        }

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            if ("MEDICO".equals(rol)) {
                ps.setInt(1, idMedico);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error contar citas hoy", e);
        }

        return 0;
    }

    public int contarCitasPorEstado(String estado, int idMedico, String rol) {
        String sql = "SELECT COUNT(*) FROM citas WHERE estado = ?";

        if ("MEDICO".equals(rol)) {
            sql += " AND id_medico = ?";
        }

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);

            if ("MEDICO".equals(rol)) {
                ps.setInt(2, idMedico);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error contar citas por estado", e);
        }

        return 0;
    }

    public int contarCitasMes(int idMedico, String rol) {
        String sql = "SELECT COUNT(*) FROM citas WHERE EXTRACT(MONTH FROM fecha_cita) = EXTRACT(MONTH FROM CURRENT_DATE)";

        if ("MEDICO".equals(rol)) {
            sql += " AND id_medico = ?";
        }

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            if ("MEDICO".equals(rol)) {
                ps.setInt(1, idMedico);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error contar citas mes", e);
        }

        return 0;
    }

    public List<Cita> listarCitasHoy(int idMedico, String rol) {
        List<Cita> lista = new ArrayList<>();

        String sql = "SELECT c.*, "
                + "CONCAT(p.nombres, ' ', p.apellidos) AS nombre_paciente, "
                + "CONCAT(u.nombres, ' ', u.apellidos) AS nombre_medico, "
                + "e.nombre AS nombre_especialidad "
                + "FROM citas c "
                + "JOIN pacientes p ON c.id_paciente = p.id "
                + "JOIN usuarios u ON c.id_medico = u.id AND u.rol = 'MEDICO' "
                + "JOIN especialidades e ON c.id_especialidad = e.id "
                + "WHERE c.fecha_cita = CURRENT_DATE ";

        if ("MEDICO".equals(rol)) {
            sql += "AND c.id_medico = ? ";
        }

        sql += "ORDER BY c.hora_cita";

        try (Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            if ("MEDICO".equals(rol)) {
                ps.setInt(1, idMedico);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cita c = mapearCita(rs);
                c.setNombrePaciente(rs.getString("nombre_paciente"));
                c.setNombreEspecialidad(rs.getString("nombre_especialidad"));
                lista.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error listar citas del día", e);
        }

        return lista;
    }

    public List<Cita> listarProximasCitas(int limite, int idMedico, String rol) {
        List<Cita> lista = new ArrayList<>();

        String sql = "SELECT c.*, "
                + "CONCAT(p.nombres, ' ', p.apellidos) AS nombre_paciente, "
                + "CONCAT(u.nombres, ' ', u.apellidos) AS nombre_medico, "
                + "e.nombre AS nombre_especialidad "
                + "FROM citas c "
                + "JOIN pacientes p ON c.id_paciente = p.id "
                + "JOIN usuarios u ON c.id_medico = u.id AND u.rol = 'MEDICO' "
                + "JOIN especialidades e ON c.id_especialidad = e.id "
                + "WHERE c.fecha_cita >= CURRENT_DATE ";

        if ("MEDICO".equals(rol)) {
            sql += "AND c.id_medico = ? ";
        }

        sql += "ORDER BY c.fecha_cita, c.hora_cita LIMIT ?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            int index = 1;

            if ("MEDICO".equals(rol)) {
                ps.setInt(index++, idMedico);
            }

            ps.setInt(index, limite);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cita c = mapearCita(rs);
                c.setNombrePaciente(rs.getString("nombre_paciente"));
                c.setNombreEspecialidad(rs.getString("nombre_especialidad"));
                lista.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error listar próximas citas", e);
        }

        return lista;
    }

    private Cita mapearCita(ResultSet rs) throws SQLException {
        return new Cita(
                rs.getInt("id"),
                rs.getInt("id_paciente"),
                rs.getInt("id_medico"),
                rs.getInt("id_especialidad"),
                rs.getDate("fecha_cita") != null ? rs.getDate("fecha_cita").toLocalDate() : null,
                rs.getTime("hora_cita") != null ? rs.getTime("hora_cita").toLocalTime() : null,
                rs.getString("motivo"),
                rs.getString("estado"),
                rs.getString("observaciones"),
                rs.getTimestamp("fecha_registro") != null ? rs.getTimestamp("fecha_registro").toLocalDateTime() : null,
                rs.getInt("id_registrado_por"), rs.getString("nombre_paciente"), rs.getString("nombre_medico")
        );
    }

    private Cita mapearCitaConDetalles(ResultSet rs) throws SQLException {
        return new Cita(
                rs.getInt("id"),
                rs.getInt("id_paciente"),
                rs.getInt("id_medico"),
                rs.getInt("id_especialidad"),
                rs.getDate("fecha_cita") != null ? rs.getDate("fecha_cita").toLocalDate() : null,
                rs.getTime("hora_cita") != null ? rs.getTime("hora_cita").toLocalTime() : null,
                rs.getString("motivo"),
                rs.getString("estado"),
                rs.getString("observaciones"),
                rs.getTimestamp("fecha_registro") != null ? rs.getTimestamp("fecha_registro").toLocalDateTime() : null,
                rs.getInt("id_registrado_por"),
                rs.getString("nombre_paciente"),
                rs.getString("nombre_medico"),
                rs.getString("nombre_especialidad")
        );
    }
}
