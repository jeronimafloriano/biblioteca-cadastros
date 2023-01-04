package cadastros.domain.model;


import javax.persistence.*;
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 100)
    private String logradouro;

    @Column(length = 8)
    private Cep cep;

    @Column(length = 6)
    private Integer numero;

    @Column(length = 100)
    private String cidade;

    public Endereco(String logradouro, Cep cep, Integer numero, String cidade) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
    }

    protected Endereco(){}


    @Override
    public String toString() {
        return " ID do endereço: " + this.id
                + " Logradouro: " + this.logradouro
                + " CEP: " + this.cep
                + " Numero: " + this.numero
                + "Cidade: " + this.cidade;
    }


}
