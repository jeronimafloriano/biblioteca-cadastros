package biblioteca.cep.config;

import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cep.exception.ApiCepException;
import biblioteca.cep.exception.CepException;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CepClient {

    private static final String cepUrl = "https://viacep.com.br/ws/";
    private static final Gson gson = new Gson();

    public static Endereco buscarCep(String cep) {
        validarCep(cep);

        HttpClient client = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest.newBuilder().GET()
                                            .uri(URI.create(cepUrl + cep + "/json"))
                                            .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return gson.fromJson(response.body(), Endereco.class);

    }

    private static void validarCep(String cep){
        if (cep.isBlank()){
            throw new CepException("O cep informado não pode ser nulo ou vazio:" + cep);
        }

        if (cep.length() > 8 || cep.length() < 8) {
            throw new CepException("CEP " + cep + " inválido! Informe o CEP com 8 dígitos, informando apenas números.");
        }

    }

    public static String removerHifen(String cep){
        return cep.replaceAll("-","");
    }

}
