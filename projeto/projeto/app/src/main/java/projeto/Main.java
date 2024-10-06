package projeto;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static double[] geocode(String endereco) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "https://nominatim.openstreetmap.org/search?q=" + endereco + "&format=json";
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseBody = response.body().string();
            if (responseBody.equals("[]")) {
                throw new IOException("não foi encontrada nenhuma coordenada para esse endereço.");
            }
            JSONArray jsonArray = new JSONArray(responseBody);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            double latitude = jsonObject.getDouble("lat");
            double longitude = jsonObject.getDouble("lon");
            return new double[]{latitude, longitude};
        }
    }

    public static void main(String[] args) {
        Estacionamento estacionamento = new Estacionamento();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("escolha uma opção:");
            System.out.println("1 adicionar veículo");
            System.out.println("2 registrar saída");
            System.out.println("3 listar veículos");
            System.out.println("4 encerrar programa");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("digite a placa do veículo:");
                    String placa = scanner.nextLine();
                    System.out.println("digite o modelo do veículo:");
                    String modelo = scanner.nextLine();
                    System.out.println("digite o endereço:");
                    String endereco = scanner.nextLine();
                    double[] coordenadas;
                    try {
                        coordenadas = geocode(endereco);
                        Veiculo veiculo = new Veiculo(placa, modelo, LocalDateTime.now(), endereco, coordenadas[0], coordenadas[1]);
                        estacionamento.adicionarVeiculo(veiculo);
                    } catch (IOException e) {
                        System.out.println("não foram encontradas coordenadas: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("digite a placa do veículo que saiu:");
                    String placaSaida = scanner.nextLine();
                    estacionamento.registrarSaida(placaSaida);
                    break;

                case 3:
                    estacionamento.listarVeiculosEstacionados();
                    break;

                case 4:
                    System.out.println("encerrando");
                    scanner.close();
                    return;

                default:
                    System.out.println("opção inválida");
            }
        }
    }
}
