package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import dao.CitaDAO;
import dao.PacienteDAO;
import dto.Cita;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String rol = (String) session.getAttribute("usuarioRol");
        Integer idUsuario = (Integer) session.getAttribute("usuarioId");

        if (rol == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        CitaDAO citaDAO = new CitaDAO();
        PacienteDAO pacienteDAO = new PacienteDAO();

        int citasHoy;
        int citasPendientes;
        int citasMes;
        int totalPacientes;

        List<Cita> proximasCitas;

        switch (rol) {
            case "MEDICO":
                citasHoy = citaDAO.contarCitasHoy(idUsuario, rol);
                citasPendientes = citaDAO.contarCitasPorEstado("PROGRAMADA", idUsuario, rol);
                citasMes = citaDAO.contarCitasMes(idUsuario, rol);
                totalPacientes = pacienteDAO.contarPacientes();
                proximasCitas = citaDAO.listarProximasCitas(5, idUsuario, rol);
                break;

            default:
                citasHoy = citaDAO.contarCitasHoy(0, rol);
                citasPendientes = citaDAO.contarCitasPorEstado("PROGRAMADA", 0, rol);
                citasMes = citaDAO.contarCitasMes(0, rol);
                totalPacientes = pacienteDAO.contarPacientes();
                proximasCitas = citaDAO.listarProximasCitas(5, 0, rol);
                break;
        }

        request.setAttribute("citasHoy", citasHoy);
        request.setAttribute("citasPendientes", citasPendientes);
        request.setAttribute("citasMes", citasMes);
        request.setAttribute("totalPacientes", totalPacientes);
        request.setAttribute("proximasCitas", proximasCitas);
        request.setAttribute("fechaActual", LocalDate.now());
        request.setAttribute("rol", rol);

        request.getRequestDispatcher("/views/dashboard.jsp")
                .forward(request, response);
    }
}
