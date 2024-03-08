package biblioteca.cep.config;

import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cep.exception.CepException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CepClientRestTemplate {

    RestTemplate restTemplate;

    private static final String url = "https://viacep.com.br/ws/";

    public CepClientRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Endereco buscarCep(String cep) {
        validarCep(cep);

        String cepUrl = url + cep + "/json";

        return restTemplate.getForObject(cepUrl, Endereco.class);
    }

    private static void validarCep(String cep){
        if (cep.isBlank()){
            throw new CepException("O cep informado não pode ser nulo ou vazio:" + cep);
        }

        if (cep.length() > 8 || cep.length() < 8) {
            throw new CepException("CEP " + cep + " inválido! Informe o CEP com 8 dígitos, informando apenas números.");
        }

    }


}
