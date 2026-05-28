package sena.adso.parqueadero.servlet;

import sena.adso.parqueadero.dao.RegistroDAO;
import sena.adso.parqueadero.model.Registro;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * RegistroServlet - API REST para entradas y salidas
 * <p>
 * Rutas disponibles:
 * GET  /api/registros            → lista activos (vehículos dentro)
 * GET  /api/registros?estado=FINALIZADO → historial
 * POST /api/registros            → registrar entrada  {"vehiculoId": 3}
 * PUT  /api/registros/{id}/salida → registrar salida y calcular tarifa
 */
public class RegistroServlet extends HttpServlet {

    private final RegistroDAO dao = new RegistroDAO();


    // ─── GET ──────────────────────────────────────────────────────────────────
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        if (pathInfo != null) {
            try {
                out.print(dao.ObtenerIngresoPorDia().toJson());
            } catch (SQLException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"error\":\"" + e.getMessage() + "\"}");
                return;
            }
        } else {

            try {
                String estado = req.getParameter("estado");
                List<Registro> lista;

                if ("FINALIZADO".equalsIgnoreCase(estado)) {


                    String desde = req.getParameter("desde");
                    String hasta = req.getParameter("hasta");
                    String tipo = req.getParameter("tipo");
                    if (desde != null || hasta != null || tipo != null) {


                        List<Registro> listaF = dao.listarHistorialFiltrado(desde, hasta, tipo);

                        StringBuilder sb = new StringBuilder("[");
                        for (int i = 0; i < listaF.size(); i++) {
                            if (i > 0) sb.append(",");
                            sb.append(listaF.get(i).toJson());
                        }
                        sb.append("]");
                        out.print(sb);
                        return;
                    } else {

                        lista = dao.listarHistorial();
                    }
                } else {
                    lista = dao.listarActivos();
                }

                StringBuilder sb = new StringBuilder("[");
                for (int i = 0; i < lista.size(); i++) {
                    if (i > 0) sb.append(",");
                    sb.append(lista.get(i).toJson());
                }
                sb.append("]");
                out.print(sb);

            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }

    // ─── POST → Registrar ENTRADA ─────────────────────────────────────────────
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            String body = leerBody(req);
            int vehiculoId = Integer.parseInt(extraerValorNumerico(body, "vehiculoId"));
            int nuevoId = dao.registrarEntrada(vehiculoId);

            if (nuevoId > 0) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                out.print("{\"mensaje\":\"Entrada registrada\",\"registroId\":" + nuevoId + "}");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"error\":\"No se pudo registrar la entrada\"}");
            }

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // ─── PUT → Registrar SALIDA  /api/registros/{id}/salida ──────────────────
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            // Extraer ID del path: /5/salida → 5
            String path = req.getPathInfo();  // "/5/salida"
            String[] partes = path.split("/");
            int registroId = Integer.parseInt(partes[1]);

            Registro reg = dao.registrarSalida(registroId);

            if (reg != null) {
                out.print(reg.toJson());
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Registro no encontrado o ya finalizado\"}");
            }

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────
    private String leerBody(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = req.getReader()) {
            String linea;
            while ((linea = br.readLine()) != null) sb.append(linea);
        }
        return sb.toString();
    }

    private String extraerValorNumerico(String json, String clave) {
        String patron = "\"" + clave + "\":";
        int inicio = json.indexOf(patron);
        if (inicio == -1) return "0";
        inicio += patron.length();
        int fin = inicio;
        while (fin < json.length() && (Character.isDigit(json.charAt(fin)))) fin++;
        return json.substring(inicio, fin);
    }
}
