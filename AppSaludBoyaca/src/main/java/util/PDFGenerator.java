package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import dto.Cita;
import dto.Paciente;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PDFGenerator {

    private static final Font TITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
    private static final Font SUBTITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
    private static final Font TEXTO_NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 12);
    private static final Font TEXTO_NEGRITA = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
    private static final Font TEXTO_PEQUENO = FontFactory.getFont(FontFactory.HELVETICA, 10);

    public static byte[] generarReporteCitasPaciente(Paciente paciente, List<Cita> citas)
            throws DocumentException, IOException {

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        try {
            java.net.URL logoUrl = PDFGenerator.class.getClassLoader().getResource("logo.png");

            if (logoUrl != null) {
                Image logo = Image.getInstance(logoUrl);
                logo.scaleToFit(120, 120);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo");
        }

        Paragraph titulo = new Paragraph("REPORTE DE CITAS MÉDICAS", TITULO);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingBefore(10);
        titulo.setSpacingAfter(15);
        document.add(titulo);

        document.add(new Paragraph("INFORMACIÓN DEL PACIENTE", SUBTITULO));
        document.add(new Paragraph("Nombre: " + paciente.getNombreCompleto(), TEXTO_NORMAL));
        document.add(new Paragraph("Documento: " + paciente.getDocumento(), TEXTO_NORMAL));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (paciente.getFechaNacimiento() != null) {
            document.add(new Paragraph("Fecha de nacimiento: "
                    + sdf.format(paciente.getFechaNacimiento()), TEXTO_NORMAL));
        }

        document.add(new Paragraph(" "));

        document.add(new Paragraph("HISTORIAL DE CITAS", SUBTITULO));
        document.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);

        agregarCeldaEncabezado(tabla, "FECHA");
        agregarCeldaEncabezado(tabla, "HORA");
        agregarCeldaEncabezado(tabla, "MOTIVO");
        agregarCeldaEncabezado(tabla, "ESTADO");
        agregarCeldaEncabezado(tabla, "OBSERVACIONES");

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        for (Cita c : citas) {

            agregarCeldaContenido(tabla,
                    c.getFechaCita() != null ? c.getFechaCita().format(formatoFecha) : "");

            agregarCeldaContenido(tabla,
                    c.getHoraCita() != null ? c.getHoraCita().format(formatoHora) : "");

            agregarCeldaContenido(tabla,
                    c.getMotivo() != null ? c.getMotivo() : "");

            agregarCeldaContenido(tabla,
                    c.getEstado() != null ? c.getEstado() : "");

            agregarCeldaContenido(tabla,
                    c.getObservaciones() != null ? c.getObservaciones() : "");
        }

        document.add(tabla);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Documento generado automáticamente", TEXTO_PEQUENO));
        document.add(new Paragraph("Fecha de generación: "
                + sdf.format(new java.util.Date()), TEXTO_PEQUENO));

        document.close();

        return baos.toByteArray();
    }

    private static void agregarCeldaEncabezado(PdfPTable tabla, String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, TEXTO_NEGRITA));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(5);
        tabla.addCell(celda);
    }

    private static void agregarCeldaContenido(PdfPTable tabla, String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, TEXTO_NORMAL));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda.setPadding(5);
        tabla.addCell(celda);
    }

    public static byte[] generarReporteCitasHoy(List<Cita> citas)
            throws DocumentException, IOException {

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        try {
            java.net.URL logoUrl = PDFGenerator.class.getClassLoader().getResource("logo.png");
            if (logoUrl != null) {
                Image logo = Image.getInstance(logoUrl);
                logo.scaleToFit(100, 100);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo");
        }

        Paragraph titulo = new Paragraph("CITAS DEL DÍA", TITULO);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(15);
        document.add(titulo);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        document.add(new Paragraph("Fecha: " + sdf.format(new java.util.Date()), TEXTO_NORMAL));
        document.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);

        agregarCeldaEncabezado(tabla, "HORA");
        agregarCeldaEncabezado(tabla, "PACIENTE");
        agregarCeldaEncabezado(tabla, "ESPECIALIDAD");
        agregarCeldaEncabezado(tabla, "MOTIVO");
        agregarCeldaEncabezado(tabla, "ESTADO");
        agregarCeldaEncabezado(tabla, "OBSERVACIONES");

        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        for (Cita c : citas) {

            agregarCeldaContenido(tabla,
                    c.getHoraCita() != null ? c.getHoraCita().format(formatoHora) : "");

            agregarCeldaContenido(tabla,
                    c.getNombrePaciente() != null ? c.getNombrePaciente() : "");

            agregarCeldaContenido(tabla,
                    c.getNombreEspecialidad() != null ? c.getNombreEspecialidad() : "");

            agregarCeldaContenido(tabla,
                    c.getMotivo() != null ? c.getMotivo() : "");

            agregarCeldaContenido(tabla,
                    c.getEstado() != null ? c.getEstado() : "");

            agregarCeldaContenido(tabla,
                    c.getObservaciones() != null ? c.getObservaciones() : "Ninguna");
        }

        document.add(tabla);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Documento generado automáticamente", TEXTO_PEQUENO));

        document.close();

        return baos.toByteArray();
    }

    public static byte[] generarComprobanteCita(Cita c)
            throws DocumentException, IOException {

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        try {
            java.net.URL logoUrl = PDFGenerator.class.getClassLoader().getResource("logo.png");

            if (logoUrl != null) {
                Image logo = Image.getInstance(logoUrl);
                logo.scaleToFit(120, 120);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Paragraph titulo = new Paragraph("CERTIFICACIÓN DE PROGRAMACIÓN DE CITA MÉDICA", TITULO);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(15);
        document.add(titulo);

        Paragraph intro = new Paragraph(
                "A QUIEN PUEDA INTERESAR\n\n"
                + "La presente certificación se expide en calidad de constancia institucional por parte del sistema "
                + "de gestión de servicios de salud, con el fin de dejar evidencia formal de la programación de una "
                + "cita médica dentro de la red de atención.\n\n"
                + "Este documento tiene carácter estrictamente informativo y administrativo, y no constituye diagnóstico "
                + "médico, incapacidad laboral ni orden clínica de tratamiento.",
                TEXTO_NORMAL
        );
        intro.setSpacingAfter(15);
        document.add(intro);

        document.add(new Paragraph("IDENTIFICACIÓN DEL USUARIO", SUBTITULO));
        document.add(new Paragraph(
                "Nombre completo: " + (c.getNombrePaciente() != null ? c.getNombrePaciente() : ""),
                TEXTO_NORMAL
        ));

        document.add(new Paragraph(" "));

        document.add(new Paragraph("DETALLE DE LA ATENCIÓN PROGRAMADA", SUBTITULO));

        document.add(new Paragraph(
                "Fecha de la cita: "
                + (c.getFechaCita() != null
                ? c.getFechaCita().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : ""),
                TEXTO_NORMAL
        ));

        document.add(new Paragraph(
                "Hora programada: "
                + (c.getHoraCita() != null
                ? c.getHoraCita().format(DateTimeFormatter.ofPattern("HH:mm"))
                : ""),
                TEXTO_NORMAL
        ));

        document.add(new Paragraph(" "));

        document.add(new Paragraph("PROFESIONAL ASIGNADO", SUBTITULO));
        document.add(new Paragraph(
                "Médico tratante: " + (c.getNombreMedico() != null ? c.getNombreMedico() : ""),
                TEXTO_NORMAL
        ));

        document.add(new Paragraph(" "));

        document.add(new Paragraph("SERVICIO MÉDICO", SUBTITULO));
        document.add(new Paragraph(
                "Especialidad: " + (c.getNombreEspecialidad() != null ? c.getNombreEspecialidad() : ""),
                TEXTO_NORMAL
        ));

        document.add(new Paragraph(" "));

        document.add(new Paragraph("MOTIVO DE CONSULTA", SUBTITULO));
        document.add(new Paragraph(
                c.getMotivo() != null ? c.getMotivo() : "",
                TEXTO_NORMAL
        ));

        document.add(new Paragraph(" "));

        document.add(new Paragraph("OBSERVACIONES CLÍNICAS / ADMINISTRATIVAS", SUBTITULO));
        document.add(new Paragraph(
                (c.getObservaciones() != null && !c.getObservaciones().isEmpty())
                ? c.getObservaciones()
                : "Sin observaciones registradas.",
                TEXTO_NORMAL
        ));

        document.add(new Paragraph(" "));

        Paragraph cierre = new Paragraph(
                "La presente certificación se expide a solicitud del interesado y para los fines administrativos "
                + "o institucionales a que haya lugar.\n\n"
                + "Este documento hace parte del registro interno del sistema de atención en salud y puede ser verificado "
                + "por la entidad correspondiente en caso de ser requerido.\n\n"
                + "Dado en la fecha de generación indicada en el presente documento.",
                TEXTO_NORMAL
        );

        cierre.setSpacingBefore(10);
        document.add(cierre);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Documento generado electrónicamente por el sistema de gestión médica.", TEXTO_PEQUENO));
        document.add(new Paragraph("Fecha de emisión: " + sdf.format(new java.util.Date()), TEXTO_PEQUENO));

        document.close();

        return baos.toByteArray();
    }

}
