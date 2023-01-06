package biblioteca.cadastros.dto;


import javax.validation.constraints.NotNull;

public class FuncionarioDto {

    @NotNull(message = "Obrigatório informar o nome.")
    private String nome;

    @NotNull(message = "Obrigatório informar o documento de identificação do cliente.")
    private String documento;

    @NotNull(message = "Obrigatório informar o CEP para o cadastro do endereço.")
    private String cep;


    public FuncionarioDto(String nome, String documento, String cep) {
        this.nome = nome;
        this.cep = cep;
        this.documento = documento;
    }

    protected FuncionarioDto(){}

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
