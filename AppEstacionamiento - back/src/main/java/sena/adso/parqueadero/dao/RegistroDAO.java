package sena.adso.parqueadero.dao;

import sena.adso.parqueadero.dto.RegistroDTO;
import sena.adso.parqueadero.model.Registro;
import sena.adso.parqueadero.util.ConexionDB;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO Registro - Entradas y salidas del parqueadero
 * Tarifas: Carro $3.000/hora | Moto $1.500/hora | Camión $5.000/hora
 */
public class RegistroDAO {

    // Tarifas por hora en COP
    private static final double TARIFA_CARRO = 3000.0;
    private static final double TARIFA_MOTO = 1500.0;
    private static final double TARIFA_CAMION = 5000.0;

    // ─── Registrar ENTRADA ─────────────────────────────────────────────────────
    public int registrarEntrada(int vehiculoId) throws SQLException {
        String ahora = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String sql = "INSERT INTO registros (vehiculo_id, entrada, estado) VALUES (?, ?, 'ACTIVO')";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, vehiculoId);
            ps.setString(2, ahora);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    // ─── Registrar SALIDA y calcular tarifa ────────────────────────────────────
    public Registro registrarSalida(int registroId) throws SQLException {
        // 1. Obtener datos del registro activo
        Registro reg = buscarPorId(registroId);
        if (reg == null || !"ACTIVO".equals(reg.getEstado())) return null;

        // 2. Calcular tarifa según tiempo transcurrido
        LocalDateTime ahora = LocalDateTime.now();
        long minutos = java.time.Duration.between(reg.getEntrada(), ahora).toMinutes();
        long horasCompletas = Math.max(1, (long) Math.ceil(minutos / 60.0)); // mínimo 1 hora
        double tarifaHora = getTarifaPorTipo(reg.getTipo());
        double total = horasCompletas * tarifaHora;

        // 3. Actualizar en BD
        String sql = "UPDATE registros SET salida=NOW(), tarifa=?, estado='FINALIZADO' WHERE id=?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, total);
            ps.setInt(2, registroId);
            ps.executeUpdate();
        }

        // 4. Retornar registro actualizado
        return buscarPorId(registroId);
    }

    // ─── Listar registros ACTIVOS (vehículos dentro) ──────────────────────────
    public List<Registro> listarActivos() throws SQLException {
        return listarPorEstado("ACTIVO");
    }

    // ─── Listar HISTORIAL (finalizados) ───────────────────────────────────────
    public List<Registro> listarHistorial() throws SQLException {
        return listarPorEstado("FINALIZADO");
    }

    private List<Registro> listarPorEstado(String estado) throws SQLException {
        List<Registro> lista = new ArrayList<>();
        String sql = "SELECT r.*, v.placa, v.tipo FROM registros r " +
                "JOIN vehiculos v ON r.vehiculo_id = v.id " +
                "WHERE r.estado = ? ORDER BY r.entrada DESC";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }


    public RegistroDTO ObtenerIngresoPorDia() throws SQLException {
        RegistroDTO registroDto = new RegistroDTO();

        String sql = "select sum(tarifa) as totalDia, count(*) as cantidadSalidas from registros where DATE(salida) =" +
                "  CURDATE() and estado='FINALIZADO'";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    registroDto.setTotalDia(rs.getInt("totalDia"));
                    registroDto.setCantidadSalidas(rs.getInt("cantidadSalidas"));
                    return registroDto;
                }
            }

        } catch (SQLException e) {
            registroDto = null;
        }
        return registroDto;
    }

    // ─── Buscar por ID ────────────────────────────────────────────────────────
    public Registro buscarPorId(int id) throws SQLException {
        String sql = "SELECT r.*, v.placa, v.tipo FROM registros r " +
                "JOIN vehiculos v ON r.vehiculo_id = v.id WHERE r.id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Registro> listarHistorialFiltrado(String desde, String hasta, String tipo) {

        List<Registro> lista = new ArrayList<>();

        String sql = "SELECT r.id,v.placa,v.tipo,r.entrada,r.salida,r.tarifa FROM registros r " +
                "INNER JOIN vehiculos v ON r.vehiculo_id = v.id WHERE r.estado = 'FINALIZADO'";

        List<Object> parametros = new ArrayList<>();

        if (desde != null && !desde.isEmpty()) {

            sql += " AND DATE(r.salida) >= ? ";
            parametros.add(desde);

        }

        if (hasta != null && !hasta.isEmpty()) {

            sql += " AND DATE(r.salida) <= ? ";
            parametros.add(hasta);

        }

        if (tipo != null && !tipo.isEmpty()) {

            sql += " AND v.tipo = ? ";
            parametros.add(tipo);

        }

        sql += " ORDER BY r.salida DESC ";

        try (

                Connection con = ConexionDB.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)

        ) {

            for (int i = 0; i < parametros.size(); i++) {

                ps.setObject(i + 1, parametros.get(i));

            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Registro r = new Registro();

                r.setId(rs.getInt("id"));
                r.setPlaca(rs.getString("placa"));
                r.setTipo(rs.getString("tipo"));
                r.setEntrada(rs.getTimestamp("entrada").toLocalDateTime());

                if (rs.getTimestamp("salida") != null) {

                    r.setSalida(rs.getTimestamp("salida").toLocalDateTime());

                }

                r.setTarifa(rs.getDouble("tarifa"));

                lista.add(r);

            }

        } catch (Exception e) {

            return null;

        }

        return lista;

    }

    // ─── Helpers ──────────────────────────────────────────────────────────────
    private double getTarifaPorTipo(String tipo) {
        if (tipo == null) return TARIFA_CARRO;
        switch (tipo.toUpperCase()) {
            case "MOTO":
                return TARIFA_MOTO;
            case "CAMION":
                return TARIFA_CAMION;
            default:
                return TARIFA_CARRO;
        }
    }

    private Registro mapear(ResultSet rs) throws SQLException {
        Registro r = new Registro();
        r.setId(rs.getInt("id"));
        r.setVehiculoId(rs.getInt("vehiculo_id"));
        r.setPlaca(rs.getString("placa"));
        r.setTipo(rs.getString("tipo"));
        r.setEntrada(rs.getTimestamp("entrada").toLocalDateTime());
        Timestamp sal = rs.getTimestamp("salida");
        if (sal != null) r.setSalida(sal.toLocalDateTime());
        r.setTarifa(rs.getDouble("tarifa"));
        r.setEstado(rs.getString("estado"));
        return r;
    }
}
