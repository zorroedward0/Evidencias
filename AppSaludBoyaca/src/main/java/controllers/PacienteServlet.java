package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import dao.PacienteDAO;
import dto.Paciente;

@WebServlet("/pacientes")
public class PacienteServlet extends HttpServlet {

    private final PacienteDAO pacienteDAO = new PacienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String rol = (String) session.getAttribute("usuarioRol");

        List<Paciente> lista = pacienteDAO.listarPacientes();
        request.setAttribute("listaPacientes", lista);
        request.setAttribute("rol", rol);

        request.getRequestDispatcher("/views/pacientes.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        switch (accion) {
            case "crear":
                crear(request, response);
                break;
            case "editar":
                editar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            default:
                response.sendRedirect("pacientes");
        }
    }

    private void crear(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String rol = (String) session.getAttribute("usuarioRol");

        if ("ENFERMERO".equals(rol)) {
            response.sendRedirect("pacientes");
            return;
        }

        try {
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String documento = request.getParameter("documento");
            LocalDate fecha = LocalDate.parse(request.getParameter("fechaNacimiento"));

            String telefono = request.getParameter("telefono");
            String email = request.getParameter("email");
            String eps = request.getParameter("eps");
            String vereda = request.getParameter("veredaBarrio");

            Paciente p = new Paciente(
                    0,
                    nombres,
                    apellidos,
                    documento,
                    java.util.Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    telefono,
                    email,
                    eps,
                    vereda
            );

            boolean creado = pacienteDAO.insertarPaciente(p);

            request.setAttribute("hecho", creado);
            request.setAttribute("accion", "creado");

        } catch (Exception e) {
            request.setAttribute("hecho", false);
            request.setAttribute("accion", "creado");
        }

        List<Paciente> lista = pacienteDAO.listarPacientes();
        request.setAttribute("listaPacientes", lista);
        request.setAttribute("rol", session.getAttribute("usuarioRol"));

        request.getRequestDispatcher("/views/pacientes.jsp")
                .forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String rol = (String) session.getAttribute("usuarioRol");

        if ("ENFERMERO".equals(rol)) {
            response.sendRedirect("pacientes");
            return;
        }

        try {
            Integer id = Integer.valueOf(request.getParameter("id"));

            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String documento = request.getParameter("documento");
            LocalDate fecha = LocalDate.parse(request.getParameter("fechaNacimiento"));

            String telefono = request.getParameter("telefono");
            String email = request.getParameter("email");
            String eps = request.getParameter("eps");
            String vereda = request.getParameter("veredaBarrio");

            Paciente p = new Paciente(
                    id,
                    nombres,
                    apellidos,
                    documento,
                    java.util.Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    telefono,
                    email,
                    eps,
                    vereda
            );

            boolean actualizado = pacienteDAO.actualizarPaciente(p);

            request.setAttribute("hecho", actualizado);
            request.setAttribute("accion", "actualizado");

        } catch (Exception e) {
            request.setAttribute("hecho", false);
            request.setAttribute("accion", "actualizado");
        }

        List<Paciente> lista = pacienteDAO.listarPacientes();
        request.setAttribute("listaPacientes", lista);
        request.setAttribute("rol", session.getAttribute("usuarioRol"));

        request.getRequestDispatcher("/views/pacientes.jsp")
                .forward(request, response);
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String rol = (String) session.getAttribute("usuarioRol");

        if ("ENFERMERO".equals(rol)) {
            response.sendRedirect("pacientes");
            return;
        }

        try {
            Integer id = Integer.valueOf(request.getParameter("idEliminar"));

            boolean eliminado = pacienteDAO.eliminarPaciente(id);

            request.setAttribute("hecho", eliminado);
            request.setAttribute("accion", "eliminado");

        } catch (Exception e) {
            request.setAttribute("hecho", false);
            request.setAttribute("accion", "eliminado");
        }

        List<Paciente> lista = pacienteDAO.listarPacientes();
        request.setAttribute("listaPacientes", lista);
        request.setAttribute("rol", session.getAttribute("usuarioRol"));

        request.getRequestDispatcher("/views/pacientes.jsp")
                .forward(request, response);
    }
}
