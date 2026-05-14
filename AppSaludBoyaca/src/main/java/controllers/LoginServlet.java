package controllers;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import dao.UsuarioDAO;
import dto.Usuario;
import util.OTPService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (camposInvalidos(username, password)) {
            request.setAttribute("errorKey", "error.requerido");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        Usuario usuario = new UsuarioDAO().validarLogin(username, password);

        if (usuario == null) {
            request.setAttribute("errorKey", "login.error.credenciales");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = crearNuevaSesion(request, usuario);

        try {
            manejarOTP(request, session, usuario);
        } catch (Exception e) {
            request.setAttribute("errorKey", "error.servidor");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/otp");
    }

    private boolean camposInvalidos(String username, String password) {
        return username == null || username.isEmpty()
                || password == null || password.isEmpty();
    }

    private HttpSession crearNuevaSesion(HttpServletRequest request, Usuario usuario) {
      
        HttpSession session = request.getSession(true);

        session.setAttribute("usuario", usuario);
        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("usuarioNombre",
                usuario.getNombres() + " " + usuario.getApellidos());
        session.setAttribute("usuarioRol", usuario.getRol());

        return session;
    }

    private void manejarOTP(HttpServletRequest request, HttpSession session, Usuario usuario) throws Exception {

        String otp = OTPService.generarOTP();
        long timestamp = Instant.now().toEpochMilli();

        session.setAttribute("otpCodigo", otp);
        session.setAttribute("otpTimestamp", timestamp);
        session.setAttribute("otpEmail", usuario.getEmail());
        session.setAttribute("otpVerificado", false);

        Locale locale = (Locale) request.getAttribute("locale");
        ResourceBundle rb = ResourceBundle.getBundle("messages", locale);

        String asunto = rb.getString("otp.email.asunto");
        String cuerpo = MessageFormat.format(
                rb.getString("otp.email.cuerpo"), otp);

        OTPService.enviarOTP(usuario.getEmail(), otp, asunto, cuerpo);
    }
}
