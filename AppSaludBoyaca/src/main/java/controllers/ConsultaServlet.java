package controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import dao.CitaDAO;
import dto.Cita;
import util.CaptchaGenerator;
import util.PDFGenerator;

@WebServlet("/consulta-cita")
public class ConsultaServlet extends HttpServlet {

    private final CitaDAO citaDAO = new CitaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        generarCaptcha(request);

        request.getRequestDispatcher("/views/consulta_cita.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("pdfComprobante".equals(accion)) {
            generarPDF(request, response);
            return;
        }

        consultarCitas(request, response);
    }


    private void consultarCitas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String documento = request.getParameter("documento");
        String captchaIngresado = request.getParameter("captchaIngresado");
        String captchaSesion = (String) session.getAttribute("captchaTexto");

        try {

            if (captchaSesion == null || captchaIngresado == null
                    || !captchaSesion.equalsIgnoreCase(captchaIngresado)) {

                request.setAttribute("hecho", false);
                request.setAttribute("accion", "consulta");
                request.setAttribute("error", "Captcha incorrecto");

                generarCaptcha(request);
                request.getRequestDispatcher("/views/consulta_cita.jsp").forward(request, response);
                return;
            }

            List<Cita> citas = citaDAO.obtenerCitasPorDocumentoPaciente(documento);

            if (citas.isEmpty()) {
                request.setAttribute("hecho", false);
                request.setAttribute("accion", "consulta");
                request.setAttribute("error", "No se encontraron citas");
            } else {
                request.setAttribute("hecho", true);
                request.setAttribute("accion", "consulta");
            }

            request.setAttribute("listaCitasConsulta", citas);

            session.removeAttribute("captchaTexto");

        } catch (Exception e) {
            request.setAttribute("hecho", false);
            request.setAttribute("accion", "consulta");
        }

        generarCaptcha(request);
        request.getRequestDispatcher("/views/consulta_cita.jsp").forward(request, response);
    }

    private void generarPDF(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Cita cita = citaDAO.obtenerCitaPorId(id);

            byte[] pdf = PDFGenerator.generarComprobanteCita(cita);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=comprobante.pdf");
            response.setContentLength(pdf.length);

            response.getOutputStream().write(pdf);
            response.getOutputStream().flush();

        } catch (Exception e) {
            request.setAttribute("hecho", false);
            request.setAttribute("accion", "pdf");

            generarCaptcha(request);
            request.getRequestDispatcher("/views/consulta_cita.jsp").forward(request, response);
        }
    }

    private void generarCaptcha(HttpServletRequest request) {

        String textoCaptcha = CaptchaGenerator.generarTextoCaptcha();
        String imagenCaptcha = CaptchaGenerator.generarImagenCaptcha(textoCaptcha);

        HttpSession session = request.getSession();
        session.setAttribute("captchaTexto", textoCaptcha);

        request.setAttribute("captchaImagen", imagenCaptcha);
    }
}
