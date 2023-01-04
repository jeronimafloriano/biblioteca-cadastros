package cadastros.domain.model;


import cadastros.documentoidentificacao.domain.model.DocumentoIdentificacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cliente")
@Tag(name = "API", description = "API REST CADASTRO DE CLIENTES")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String nome;

    @NotNull(message = "Cliente.documento.NotNull")
    private DocumentoIdentificacao documento;

    @OneToOne
    private Endereco endereco;

    public Cliente(String nome, DocumentoIdentificacao documento, Endereco endereco) {
        this.nome = nome;
        this.documento = documento;
        this.endereco = endereco;
    }

    protected Cliente(){}


    @Override
    public String toString() {
        return " ID do cliente: " + this.id
                + " Nome: " + this.nome
                + " Documento: " + this.documento;
    }

}
