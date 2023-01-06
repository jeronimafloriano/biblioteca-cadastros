package biblioteca.cadastros.domain.model;


import biblioteca.documentoidentificacao.domain.model.DocumentoIdentificacao;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.persistence.*;

@Entity
@Table(name = "funcionario")
@Tag(name = "API", description = "API REST CADASTRO DE FUNCIONARIOS")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String nome;

    private String documento;

    @OneToOne(mappedBy = "funcionario")
    private Endereco endereco;

    public Funcionario(String nome, String documento, Endereco endereco) {
        this.nome = nome;
        this.endereco = endereco;
        endereco.setFuncionario(this);
        var documentoIdentificacao = DocumentoIdentificacao.from(documento);
        this.documento = documentoIdentificacao.getNumero();
    }

    protected Funcionario(){}

    @Override
    public String toString() {
        return " ID do funcionario: " + this.id
                + " Nome: " + this.nome
                + " Documento: " + this.documento;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
