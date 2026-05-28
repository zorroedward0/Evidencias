package sena.adso.parqueadero.dto;

import java.time.LocalDate;
import java.util.Locale;

public class RegistroDTO {
    private int totalDia;
    private int CantidadSalidas;
    private final LocalDate fecha = LocalDate.now();

    public RegistroDTO() {
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getTotalDia() {
        return totalDia;
    }

    public void setTotalDia(int totalDia) {
        this.totalDia = totalDia;
    }

    public RegistroDTO(int totalDia, int cantidadSalidas) {
        this.totalDia = totalDia;
        CantidadSalidas = cantidadSalidas;
    }

    public int getCantidadSalidas() {
        return CantidadSalidas;
    }

    public void setCantidadSalidas(int cantidadSalidas) {
        CantidadSalidas = cantidadSalidas;
    }

    public String toJson() {
        return String.format(Locale.US,
                "{\"totaldia\":%d,\"cantidadsalidas\":%d}",
                                this.totalDia, this.getCantidadSalidas());

    }
}
