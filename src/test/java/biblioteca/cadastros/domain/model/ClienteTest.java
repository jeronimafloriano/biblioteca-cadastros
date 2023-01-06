package biblioteca.cadastros.domain.model;

import biblioteca.cadastros.TestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteTest {

    private final String nome = "Paulo";
    private final String documento = "19209732065";


    @DisplayName("Teste de cadastro de cliente, informando todos os campos necessários.")
    @Test
    void deveCriarClienteComTodosOsCamposInformados(){
        Endereco endereco = TestFactory.umEnderecoDigitado();
        Cliente cliente = new Cliente(nome, documento, endereco);;

        assertThat(cliente.getNome()).isEqualTo(nome);
        assertThat(cliente.getDocumento()).isEqualTo(documento);
        assertThat(cliente.getEndereco()).isEqualTo(endereco);

    }


    @DisplayName("Testa a edição do cadastro de um cliente.")
    @Test
    void deveAlterarOsCamposInformados(){
        Endereco endereco = TestFactory.umEnderecoDigitado();
        Cliente cliente = new Cliente(nome, documento, endereco);

        Endereco outroEndereco = new Endereco("54310210", "Rua Luiz Eloi de Pontes",
                "Prazeres", "Jaboatão dos Guararapes", "PE");

        cliente.setNome("Maria da Silva");
        cliente.setDocumento("37219023006");
        cliente.setEndereco(outroEndereco);

        assertThat(cliente.getNome()).isEqualTo("Maria da Silva");
        assertThat(cliente.getDocumento()).isEqualTo("37219023006");
        assertThat(cliente.getEndereco()).isEqualTo(outroEndereco);
    }

}