package cadastros.domain.model;


import cadastros.documentoidentificacao.domain.model.DocumentoIdentificacao;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "funcionario")
@Tag(name = "API", description = "API REST CADASTRO DE FUNCIONARIOS")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String nome;

    @NotNull(message = "Cliente.documento.NotNull")
    private DocumentoIdentificacao documento;

    @OneToOne
    private Endereco endereco;

    public Funcionario(String nome, DocumentoIdentificacao documento, Endereco endereco) {
        this.nome = nome;
        this.documento = documento;
        this.endereco = endereco;
    }

    protected Funcionario(){}


    @Override
    public String toString() {
        return " ID do funcionario: " + this.id
                + " Nome: " + this.nome
                + " Documento: " + this.documento;
    }

}
