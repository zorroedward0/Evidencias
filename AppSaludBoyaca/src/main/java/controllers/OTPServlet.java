package controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import util.OTPService;

@WebServlet("/otp")
public class OTPServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("otpCodigo") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String email = (String) session.getAttribute("otpEmail");
        request.setAttribute("emailMasked", enmascararEmail(email));

        request.getRequestDispatcher("/views/otp_verificacion.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("otpCodigo") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String codigoIngresado = request.getParameter("otpCodigo");
        String codigoSesion = (String) session.getAttribute("otpCodigo");
        Long timestamp = (Long) session.getAttribute("otpTimestamp");

        if (codigoSesion == null || timestamp == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (OTPService.esValido(codigoIngresado, codigoSesion, timestamp)) {

            session.setAttribute("otpVerificado", true);
            session.removeAttribute("otpCodigo");
            session.removeAttribute("otpTimestamp");

            response.sendRedirect(request.getContextPath() + "/dashboard");

        } else {

            request.setAttribute("errorKey", "otp.error");

            String email = (String) session.getAttribute("otpEmail");
            request.setAttribute("emailMasked", enmascararEmail(email));

            request.setAttribute("volver", true);

            request.getRequestDispatcher("/views/otp_verificacion.jsp")
                    .forward(request, response);
        }
    }

    private String enmascararEmail(String email) {
        if (email == null || !email.contains("@")) {
            return "***";
        }

        String[] partes = email.split("@");
        String local = partes[0];
        String dominio = partes[1];

        if (local.length() <= 3) {
            return local + "***@" + dominio;
        }

        return local.substring(0, 3) + "***@" + dominio;
    }
}
