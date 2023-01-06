package biblioteca.cadastros.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

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

    public Endereco(String cep, String logradouro, String bairro, String cidade, String uf) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.localidade = cidade;
        this.uf = uf;
    }

    protected Endereco(){}

    @Override
    public String toString() {
        return "Endereco{" +
                " ID: " + id +
                ", CEP: " + cep + '\'' +
                ", LOGRADOURO: " + logradouro + '\'' +
                ", BAIRRO: " + bairro + '\'' +
                ", LOCALIDADE: " + localidade + '\'' +
                ", UF: " + uf + '\'' +
                ", CLIENTE " + cliente +
                ", FUNCIONARIO: " + funcionario +
                '}';
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(id, endereco.id) && Objects.equals(cep, endereco.cep) && Objects.equals(logradouro, endereco.logradouro) && Objects.equals(bairro, endereco.bairro) && Objects.equals(localidade, endereco.localidade) && Objects.equals(uf, endereco.uf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cep, logradouro, bairro, localidade, uf);
    }
}
