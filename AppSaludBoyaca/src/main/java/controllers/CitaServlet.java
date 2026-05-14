package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import dao.CitaDAO;
import dao.EspecialidadDAO;
import dao.PacienteDAO;
import dao.UsuarioDAO;
import dto.Cita;
import dto.Especialidad;
import dto.Paciente;
import dto.Usuario;
import util.PDFGenerator;

@WebServlet("/citas")
public class CitaServlet extends HttpServlet {

    private final CitaDAO citaDAO = new CitaDAO();
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final EspecialidadDAO especialidadDAO = new EspecialidadDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String rol = (String) session.getAttribute("usuarioRol");
        Integer idUsuario = (Integer) session.getAttribute("usuarioId");

        List<Cita> lista;

        if ("MEDICO".equals(rol)) {
            lista = citaDAO.listarCitasPorMedico(idUsuario);
        } else {
            lista = citaDAO.listarCitas();
        }
        List<Especialidad> especialidades = especialidadDAO.listarEspecialidades();
        List<Paciente> pacientes = pacienteDAO.listarPacientes();

        List<Usuario> medicos = usuarioDAO.listarUsuarios()
                .stream()
                .filter(u -> "MEDICO".equals(u.getRol()))
                .collect(Collectors.toList());

        request.setAttribute("rol", rol);
        request.setAttribute("listaCitas", lista);
        request.setAttribute("listaEspecialidades", especialidades);
        request.setAttribute("listaPacientes", pacientes);
        request.setAttribute("listaMedicos", medicos);

        RequestDispatcher rd = request.getRequestDispatcher("/views/citas.jsp");
        rd.forward(request, response);
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
            case "cambiarEstado":
                cambiarEstado(request, response);
                break;
            case "pdf":
                pdf(request, response);
                break;
            case "pdfPaciente":
                pdfPacienteR(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/citas");
                break;
        }
    }

    private void crear(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String rol = (String) session.getAttribute("usuarioRol");

        if ("ENFERMERO".equals(rol)) {
            response.sendRedirect(request.getContextPath() + "/citas");
            return;
        }

        try {
            int idPaciente = Integer.parseInt(request.getParameter("idPaciente"));
            int idMedico = Integer.parseInt(request.getParameter("idMedico"));
            int idEspecialidad = Integer.parseInt(request.getParameter("idEspecialidad"));

            LocalDate fecha = LocalDate.parse(request.getParameter("fecha"));
            LocalTime hora = LocalTime.parse(request.getParameter("hora") + ":00");

            String motivo = request.getParameter("motivo");
            String observaciones = request.getParameter("observaciones");

            boolean disponible = validarDisponibilidad(idMedico, fecha, hora);

            if (!disponible) {
                request.setAttribute("hecho", false);
                request.setAttribute("accion", "creado");
                request.setAttribute("error", "Horario no disponible");
                cargarListas(request);
                request.getRequestDispatcher("/views/citas.jsp").forward(request, response);
                return;
            }

            Cita c = new Cita(
                    0,
                    idPaciente,
                    idMedico,
                    idEspecialidad,
                    fecha,
                    hora,
                    motivo,
                    "PROGRAMADA",
                    observaciones,
                    null,
                    (Integer) session.getAttribute("usuarioId")
            );

            boolean creado = citaDAO.insertarCita(c);

            request.setAttribute("hecho", creado);
            request.setAttribute("accion", "creado");

        } catch (Exception e) {
            request.setAttribute("hecho", false);
            request.setAttribute("accion", "creado");
        }

        cargarListas(request);
        request.getRequestDispatcher("/views/citas.jsp").forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String rol = (String) session.getAttribute("usuarioRol");

        if ("ENFERMERO".equals(rol)) {
            response.sendRedirect(request.getContextPath() + "/citas");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int idPaciente = Integer.parseInt(request.getParameter("idPaciente"));
            int idMedico = Integer.parseInt(request.getParameter("idMedico"));
            int idEspecialidad = Integer.parseInt(request.getParameter("idEspecialidad"));

            LocalDate fecha = LocalDate.parse(request.getParameter("fecha"));
            LocalTime hora = LocalTime.parse(request.getParameter("hora") + ":00");

            String motivo = request.getParameter("motivo");
            String estado = request.getParameter("estado");
            String observaciones = request.getParameter("observaciones");

            boolean disponible = validarDisponibilidad(idMedico, fecha, hora, id);

            if (!disponible) {
                request.setAttribute("hecho", false);
                request.setAttribute("accion", "actualizado");
                request.setAttribute("error", "Horario no disponible");
                cargarListas(request);
                request.getRequestDispatcher("/views/citas.jsp").forward(request, response);
                return;
            }

            Cita c = new Cita(
                    id,
                    idPaciente,
                    idMedico,
                    idEspecialidad,
                    fecha,
                    hora,
                    motivo,
                    estado,
                    observaciones,
                    null,
                    (Integer) session.getAttribute("usuarioId")
            );

            boolean actualizado = citaDAO.actualizarCita(c);

            request.setAttribute("hecho", actualizado);
            request.setAttribute("accion", "actualizado");

        } catch (Exception e) {
            request.setAttribute("hecho", false);
            request.setAttribute("accion", "actualizado");
        }

        cargarListas(request);
        request.getRequestDispatcher("/views/citas.jsp").forward(request, response);
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Integer id = Integer.valueOf(request.getParameter("idEliminar"));

            boolean eliminado = citaDAO.eliminar(id);

            request.setAttribute("hecho", eliminado);
            request.setAttribute("accion", "eliminado");

        } catch (Exception e) {
            request.setAttribute("hecho", false);
            request.setAttribute("accion", "eliminado");
        }

        cargarListas(request);
        request.getRequestDispatcher("/views/citas.jsp").forward(request, response);
    }

    private void cambiarEstado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String estado = request.getParameter("estado");

            Cita c = citaDAO.obtenerCitaPorId(id);
            c.setEstado(estado);

            boolean ok = citaDAO.actualizarCita(c);

            request.setAttribute("hecho", ok);
            request.setAttribute("accion", "estado");

        } catch (Exception e) {
            request.setAttribute("hecho", false);
            request.setAttribute("accion", "estado");
        }

        cargarListas(request);
        request.getRequestDispatcher("/views/citas.jsp").forward(request, response);
    }

    private void cargarListas(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        String rol = (String) session.getAttribute("usuarioRol");
        Integer idUsuario = (Integer) session.getAttribute("usuarioId");

        List<Cita> lista;

        if ("MEDICO".equals(rol)) {
            lista = citaDAO.listarCitasPorMedico(idUsuario);
        } else {
            lista = citaDAO.listarCitas();
        }

        List<Paciente> pacientes = pacienteDAO.listarPacientes();
        List<Especialidad> especialidades = especialidadDAO.listarEspecialidades();

        List<Usuario> medicos = usuarioDAO.listarUsuarios()
                .stream()
                .filter(u -> "MEDICO".equals(u.getRol()))
                .collect(Collectors.toList());

        request.setAttribute("rol", rol);
        request.setAttribute("listaCitas", lista);
        request.setAttribute("listaEspecialidades", especialidades);
        request.setAttribute("listaPacientes", pacientes);
        request.setAttribute("listaMedicos", medicos);
    }

    private boolean validarDisponibilidad(int idMedico, LocalDate fecha, LocalTime hora, int idActual) {
        List<Cita> citas = citaDAO.listarCitasPorMedico(idMedico);

        for (Cita c : citas) {
            if (c.getId() != idActual
                    && c.getFechaCita().equals(fecha)
                    && c.getHoraCita().equals(hora)
                    && !"CANCELADA".equals(c.getEstado())) {
                return false;
            }
        }
        return true;
    }

    private boolean validarDisponibilidad(int idMedico, LocalDate fecha, LocalTime hora) {
        List<Cita> citas = citaDAO.listarCitasPorMedico(idMedico);

        for (Cita c : citas) {
            if (c.getFechaCita().equals(fecha)
                    && c.getHoraCita().equals(hora)
                    && !"CANCELADA".equals(c.getEstado())) {
                return false;
            }
        }
        return true;
    }

    private void pdf(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);

            Usuario usuario = (Usuario) session.getAttribute("usuario");
            String rol = usuario.getRol();
            Integer idUsuario = usuario.getId();
            String titulo = usuario.getNombreCompleto() + ": " + LocalDate.now().toString();

            List<Cita> citasHoy = citaDAO.listarCitasHoy(idUsuario, rol);
            try {
                byte[] pdfBytes = PDFGenerator.generarReporteCitasHoy(citasHoy);

                response.setContentType("application/pdf");
                if (usuario.getNombreCompleto() == null || usuario.getNombreCompleto().isEmpty()) {
                    titulo = "Citas Medicas: " + LocalDate.now().toString();
                }
                response.setHeader("Content-Disposition", "attachment; filename=" + titulo + ".pdf");
                response.setContentLength(pdfBytes.length);

                response.getOutputStream().write(pdfBytes);
                response.getOutputStream().flush();
            } catch (RuntimeException e) {
                request.setAttribute("hecho", false);
                request.setAttribute("accion", "estado");
            }

        } catch (Exception e) {
            request.setAttribute("hecho", false);
            request.setAttribute("accion", "estado");
        }
    }

    private void pdfPacienteR(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String documento = request.getParameter("documentoPaciente");

            if (documento == null || documento.isEmpty()) {
                request.setAttribute("hecho", false);
                request.setAttribute("accion", "estado");
                request.getRequestDispatcher("/views/citas.jsp").forward(request, response);

                return;
            }

            Paciente paciente = pacienteDAO.obtenerPacientePorDocumento(documento);

            if (paciente == null) {
                request.setAttribute("hecho", false);
                request.setAttribute("accion", "estado");
                request.getRequestDispatcher("/views/citas.jsp").forward(request, response);

                return;
            }

            List<Cita> citas = citaDAO.listarCitasPorPaciente(paciente.getId());

            byte[] pdfBytes = PDFGenerator.generarReporteCitasPaciente(paciente, citas);

            String nombreArchivo = "citas_" + documento + "_" + LocalDate.now() + ".pdf";

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);
            response.setContentLength(pdfBytes.length);

            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();

        } catch (Exception e) {
            request.setAttribute("hecho", false);
            request.setAttribute("accion", "estado");
        }

        String documento = request.getParameter("documentoPaciente");
        Paciente paciente = pacienteDAO.obtenerPacientePorDocumento(documento);

        if (documento == null || documento.isEmpty() || paciente == null) {
            request.getRequestDispatcher("/views/citas.jsp").forward(request, response);
        }
    }
}
