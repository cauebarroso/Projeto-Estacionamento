package projeto;

import java.time.LocalDateTime;

public class Veiculo {
    private String placa;
    private String modelo;
    private LocalDateTime horaEntrada;
    private String endereco;
    private double latitude;
    private double longitude;

    public Veiculo(String placa, String modelo, LocalDateTime horaEntrada, String endereco, double latitude, double longitude) {
        this.placa = placa;
        this.modelo = modelo;
        this.horaEntrada = horaEntrada;
        this.endereco = endereco;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public String getEndereco() {
        return endereco;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return String.format("Veiculo{placa='%s', modelo='%s', horaEntrada=%s, endereco='%s', latitude=%s, longitude=%s}", 
            placa, modelo, horaEntrada, endereco, latitude, longitude);
    }
}
