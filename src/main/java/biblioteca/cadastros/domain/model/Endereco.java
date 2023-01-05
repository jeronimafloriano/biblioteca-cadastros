package biblioteca.cadastros.domain.model;


import javax.persistence.*;
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 8)
    private String cep;

    @Column(length = 100)
    private String logradouro;

    @Column(length = 6)
    private String bairro;

    @Column(length = 100)
    private String cidade;

    @Column(length = 2)
    private String uf;

    public Endereco(String cep, String logradouro, String bairro, String cidade, String uf) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    protected Endereco(){}


    @Override
    public String toString() {
        return " ID do endereço: " + this.id
                + " CEP: " + this.cep
                + " Logradouro: " + this.logradouro
                + " Bairro: " + this.bairro
                + " Cidade: " + this.cidade
                + "UF: " + this.uf;
    }


}
