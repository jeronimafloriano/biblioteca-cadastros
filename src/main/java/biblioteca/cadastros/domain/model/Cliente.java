package biblioteca.cadastros.domain.model;


import biblioteca.documentoidentificacao.domain.model.DocumentoIdentificacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.persistence.*;

@Entity
@Table(name = "cliente")
@Tag(name = "API", description = "API REST CADASTRO DE CLIENTES")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String nome;

    //@OneToOne
    private String documento;

    @OneToOne(mappedBy = "cliente")
    @JsonIgnore
    private Endereco endereco;

    public Cliente(String nome, String documento, Endereco endereco) {
        this.nome = nome;
        this.endereco = endereco;
        DocumentoIdentificacao.from(documento);
    }

    protected Cliente(){}


    @Override
    public String toString() {
        return " ID do cliente: " + this.id
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
