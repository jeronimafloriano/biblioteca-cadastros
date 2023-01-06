package biblioteca.cadastros.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 10)
    private String cep;

    @Column(length = 150)
    private String logradouro;

    @Column(length = 100)
    private String bairro;

    @Column(length = 100)
    private String localidade;

    @Column(length = 2)
    private String uf;

    @OneToOne
    private Cliente cliente;

    @OneToOne
    @JsonIgnore
    private Funcionario funcionario;

    public Endereco(String cep, String logradouro, String bairro, String cidade, String uf, Cliente cliente) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.localidade = cidade;
        this.uf = uf;
        this.cliente = cliente;
    }

    public Endereco(String cep, String logradouro, String bairro, String cidade, String uf, Funcionario funcionario) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.localidade = cidade;
        this.uf = uf;
        this.funcionario = funcionario;
    }

    protected Endereco(){}

    @Override
    public String toString() {
        return " ID do endereço: " + this.id
                + " CEP: " + this.cep
                + " Logradouro: " + this.logradouro
                + " Bairro: " + this.bairro
                + " Cidade: " + this.localidade
                + "UF: " + this.uf;
    }

    public Long getId() {
        return id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}
