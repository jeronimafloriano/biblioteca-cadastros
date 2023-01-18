package biblioteca.cadastros.dto;


import javax.validation.constraints.NotNull;

public class ClienteDto {

    @NotNull(message = "Obrigatório informar o nome.")
    private String nome;

    @NotNull(message = "Obrigatório informar o documento de identificação do cliente.")
    private String documento;

    @NotNull(message = "Obrigatório informar o CEP para o cadastro do endereço do cliente.")
    private String cep;


    public ClienteDto(String nome, String documento, String cep) {
        this.nome = nome;
        this.documento = documento;
        //Endereco endereco = CepClient.buscarCep(cep);
        this.cep = cep;
    }

    protected ClienteDto(){}

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
