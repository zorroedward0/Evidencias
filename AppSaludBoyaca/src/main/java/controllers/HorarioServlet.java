package controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import dao.HorarioDAO;
import dto.Horario;

@WebServlet("/horarios")
public class HorarioServlet extends HttpServlet {

    private final HorarioDAO horarioDAO = new HorarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String rol = (String) session.getAttribute("usuarioRol");
        Integer idUsuario = (Integer) session.getAttribute("usuarioId");

        if (rol == null || "ENFERMERO".equals(rol)) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        List<Horario> lista;

        if ("MEDICO".equals(rol)) {
            lista = horarioDAO.listarHorariosPorMedico(idUsuario);
        } else {
            lista = horarioDAO.listarHorarios();
        }

        request.setAttribute("listaHorarios", lista);
        request.setAttribute("rol", rol);

        request.getRequestDispatcher("/views/horarios.jsp")
                .forward(request, response);
    }
}
