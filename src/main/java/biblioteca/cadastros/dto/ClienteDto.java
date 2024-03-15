package biblioteca.cadastros.dto;


import biblioteca.cadastros.domain.model.Cliente;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import javax.validation.constraints.NotNull;

public class ClienteDto {

    @JsonProperty(access = Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Obrigatório informar o nome.")
    private String nome;

    @NotNull(message = "Obrigatório informar o documento de identificação do cliente.")
    private String documento;

    @NotNull(message = "Obrigatório informar o CEP para o cadastro do endereço do cliente.")
    private String cep;

    public ClienteDto(Long id, String nome, String documento, String cep) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.cep = cep;
    }

    protected ClienteDto(){}

    public static ClienteDto map(Cliente cliente){
        return new ClienteDto(cliente.getId(),
            cliente.getNome(),
            cliente.getDocumento(),
            cliente.getEndereco().getCep());
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getCep() {
        return cep;
    }
}
