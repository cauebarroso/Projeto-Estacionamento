package projeto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    private List<Veiculo> veiculos;
    private static final String CSV_ENTRADA_FILE = "veiculos_entrada.csv";
    private static final String CSV_SAIDA_FILE = "veiculos_saida.csv";
    private static final double TARIFA_HORA = 5.0;

    public Estacionamento() {
        veiculos = new ArrayList<>();
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        if (veiculos.size() < 50) {
            veiculos.add(veiculo);
            salvarVeiculoEntrada(veiculo);
            double tarifa = calcularTarifa(veiculo.getHoraEntrada());
            System.out.println("veículo adicionado: " + veiculo + ". tarifa por hora: R$" + TARIFA_HORA + ". tarifa total: R$" + tarifa);
        } else {
            System.out.println("estacionamento lotado");
        }
    }

    private double calcularTarifa(LocalDateTime horaEntrada) {
        Duration duracao = Duration.between(horaEntrada, LocalDateTime.now());
        long horas = duracao.toHours();
        return horas * TARIFA_HORA;
    }

    private void salvarVeiculoEntrada(Veiculo veiculo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_ENTRADA_FILE, true))) {
            String linha = String.format("%s,%s,%s,%s,%s,%s\n", 
                veiculo.getPlaca(),
                veiculo.getModelo(),
                veiculo.getHoraEntrada(),
                veiculo.getEndereco(),
                veiculo.getLatitude(),
                veiculo.getLongitude());
            writer.write(linha);
        } catch (IOException e) {
            System.out.println("erro ao salvar veículo no CSV de entrada: " + e.getMessage());
        }
    }

    public void registrarSaida(String placa) {
        Veiculo veiculoSaindo = null;

        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                veiculoSaindo = veiculo;
                break;
            }
        }

        if (veiculoSaindo != null) {
            veiculos.remove(veiculoSaindo);
            salvarVeiculoSaida(veiculoSaindo);
            System.out.println("veículo saiu do estacionamento: " + veiculoSaindo);
        } else {
            System.out.println("veículo não cadastrado.");
        }
    }

    private void salvarVeiculoSaida(Veiculo veiculo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_SAIDA_FILE, true))) {
            LocalDateTime horaSaida = LocalDateTime.now();
            Duration duracao = Duration.between(veiculo.getHoraEntrada(), horaSaida);
            long horas = duracao.toHours();
            long minutos = duracao.toMinutes() % 60; // Calcula os minutos restantes após calcular as horas
            double tarifa = horas * TARIFA_HORA;

            String linha = String.format("%s,%s,%s,%s,%s,%s,R$%.2f,%02d:%02d\n", 
                veiculo.getPlaca(),
                veiculo.getModelo(),
                veiculo.getHoraEntrada(),
                horaSaida,
                veiculo.getLatitude(),
                veiculo.getLongitude(),
                tarifa,
                horas,
                minutos); // Adiciona horas e minutos ao CSV
            writer.write(linha);
        } catch (IOException e) {
            System.out.println("erro ao salvar veículo no CSV de saida: " + e.getMessage());
        }
    }

    public void listarVeiculosEstacionados() {
        if (veiculos.isEmpty()) {
            System.out.println("não há veículos no estacionamento.");
        } else {
            System.out.println("veículos no estacionamento:");
            for (Veiculo veiculo : veiculos) {
                System.out.println(veiculo);
            }
        }
    }
}
