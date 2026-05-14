package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import dto.Horario;
import config.Conexion;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class HorarioDAO {

    boolean isTestingD = false;

    public HorarioDAO(boolean isTesting) {
        this.isTestingD = isTesting;
    }

    public HorarioDAO() {

    }

    public boolean insertarHorario(Horario horario) {
        if (horario == null
                || horario.getIdMedico() <= 0
                || horario.getHoraInicio() == null
                || horario.getHoraFin() == null) {
            return false;
        }

        String sql = "INSERT INTO horarios (id_medico, dia_semana, hora_inicio, hora_fin, max_citas) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, horario.getIdMedico());
            ps.setInt(2, horario.getDiaSemana());
            ps.setTime(3, Time.valueOf(horario.getHoraInicio()));
            ps.setTime(4, Time.valueOf(horario.getHoraFin()));
            ps.setInt(5, horario.getMaxCitas());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar horario", e);
        }
    }

    public boolean actualizarHorario(Horario horario) {
        if (horario == null
                || horario.getId() <= 0
                || horario.getIdMedico() <= 0
                || horario.getHoraInicio() == null
                || horario.getHoraFin() == null) {
            return false;
        }

        String sql = "UPDATE horarios SET id_medico=?, dia_semana=?, hora_inicio=?, hora_fin=?, max_citas=? WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, horario.getIdMedico());
            ps.setInt(2, horario.getDiaSemana());
            ps.setTime(3, Time.valueOf(horario.getHoraInicio()));
            ps.setTime(4, Time.valueOf(horario.getHoraFin()));
            ps.setInt(5, horario.getMaxCitas());
            ps.setInt(6, horario.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar horario", e);
        }
    }

    public boolean eliminar(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }

        String sql = "DELETE FROM horarios WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar horario", e);
        }
    }

    public Horario obtenerHorarioPorId(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }

        String sql = "SELECT * FROM horarios WHERE id=?";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearHorario(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener horario", e);
        }

        return null;
    }

    public List<Horario> listarHorarios() {
        List<Horario> lista = new ArrayList<>();

        String sql = "SELECT h.*, CONCAT(u.nombres, ' ', u.apellidos) AS nombre_medico "
                + "FROM horarios h "
                + "JOIN usuarios u ON h.id_medico = u.id "
                + "ORDER BY h.dia_semana, h.hora_inicio";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearHorario(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar horarios", e);
        }

        return lista;
    }

    public List<Horario> listarHorariosPorMedico(int idMedico) {
        List<Horario> lista = new ArrayList<>();

        if (idMedico <= 0) {
            return lista;
        }

        String sql = "SELECT h.*, CONCAT(u.nombres, ' ', u.apellidos) AS nombre_medico "
                + "FROM horarios h "
                + "JOIN usuarios u ON h.id_medico = u.id "
                + "WHERE h.id_medico = ? "
                + "ORDER BY h.dia_semana, h.hora_inicio";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idMedico);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearHorario(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar horarios por médico", e);
        }

        return lista;
    }

    public List<Time> horasDisponibles(LocalDate fecha, int idMedico) {
        List<Time> disponibles = new ArrayList<>();

        if (fecha == null || idMedico <= 0) {
            return disponibles;
        }

        int diaSemana = fecha.getDayOfWeek().getValue();

        String sqlHorarios = "SELECT * FROM horarios WHERE id_medico=? AND dia_semana=?";
        String sqlCitas = "SELECT hora_cita, COUNT(*) as total FROM citas "
                + "WHERE id_medico=? AND fecha_cita=? AND estado <> 'CANCELADA' "
                + "GROUP BY hora_cita";

        try (
                Connection con = Conexion.establecerConexion(isTestingD); PreparedStatement psHorarios = con.prepareStatement(sqlHorarios); PreparedStatement psCitas = con.prepareStatement(sqlCitas)) {

            psHorarios.setInt(1, idMedico);
            psHorarios.setInt(2, diaSemana);

            List<Horario> horarios = new ArrayList<>();
            try (ResultSet rs = psHorarios.executeQuery()) {
                while (rs.next()) {
                    horarios.add(mapearHorario(rs));
                }
            }

            psCitas.setInt(1, idMedico);
            psCitas.setDate(2, java.sql.Date.valueOf(fecha));

            Map<Time, Integer> ocupadas = new HashMap<>();
            try (ResultSet rs = psCitas.executeQuery()) {
                while (rs.next()) {
                    ocupadas.put(rs.getTime("hora_cita"), rs.getInt("total"));
                }
            }

            for (Horario h : horarios) {
                LocalTime inicio = h.getHoraInicio();
                LocalTime fin = h.getHoraFin();

                while (inicio.isBefore(fin)) {
                    Time horaActual = Time.valueOf(inicio);

                    int usadas = ocupadas.getOrDefault(horaActual, 0);

                    if (usadas < h.getMaxCitas()) {
                        disponibles.add(horaActual);
                    }

                    inicio = inicio.plusMinutes(30);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener horas disponibles", e);
        }

        return disponibles;
    }

    private Horario mapearHorario(ResultSet rs) throws SQLException {
        Horario h = new Horario(
                rs.getInt("id"),
                rs.getInt("id_medico"),
                rs.getInt("dia_semana"),
                rs.getTime("hora_inicio").toLocalTime(),
                rs.getTime("hora_fin").toLocalTime(),
                rs.getInt("max_citas")
        );

        h.setNombreMedico(rs.getString("nombre_medico"));

        return h;
    }
}
