package util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Properties;

public class OTPService {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;

    private static final String SMTP_USER = "zorroedward7@gmail.com";
    private static final String SMTP_PASS = "oscp xram mmue ibnx";

    private static final String EMAIL_FROM = "zorroedward7@gmail.com";

    private static final int OTP_LONGITUD = 6;
    private static final long OTP_EXPIRA_MS = 5 * 60 * 1000;

    public static String generarOTP() {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(OTP_LONGITUD);

        for (int i = 0; i < OTP_LONGITUD; i++) {
            sb.append(rnd.nextInt(10));
        }

        return sb.toString();
    }

    public static boolean esValido(String ingresado, String guardado, long timestamp) {

        if (ingresado == null || guardado == null) {
            return false;
        }

        long ahora = Instant.now().toEpochMilli();

        boolean noExpirado = (ahora - timestamp) <= OTP_EXPIRA_MS;
        boolean coincide = ingresado.trim().equals(guardado);

        return noExpirado && coincide;
    }

    public static void enviarOTP(
            String destinatario,
            String codigoOTP,
            String asunto,
            String cuerpo)
            throws MessagingException, UnsupportedEncodingException {

        Properties props = new Properties();

        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", SMTP_HOST);

        Session mailSession = Session.getInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASS);
            }
        });

        Message mensaje = new MimeMessage(mailSession);

        mensaje.setFrom(new InternetAddress(
                EMAIL_FROM,
                "Salud Boyacá"
        ));

        mensaje.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(destinatario)
        );

        mensaje.setSubject(asunto);
        mensaje.setContent(cuerpo, "text/html; charset=UTF-8");

        Transport.send(mensaje);
    }
}
