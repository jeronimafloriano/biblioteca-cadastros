package biblioteca.cadastros.domain.model;


import biblioteca.cadastros.TestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnderecoTest {
    private final String cep = "54310310";
    private final String logradouro = "Avenida Barreto de Menezes";
    private final String bairro = "Prazeres";
    private final String localidade = "Jaboatão dos Guararapes";
    private final String uf = "PE";


    @DisplayName("Teste de cadastro de endereço, informando todos os campos necessários.")
    @Test
    void deveCriarEnderecoComTodosOsCamposInformados(){
        Endereco endereco = new Endereco(cep, logradouro, bairro, localidade, uf);

        assertThat(endereco.getCep()).isEqualTo(cep);
        assertThat(endereco.getLogradouro()).isEqualTo(logradouro);
        assertThat(endereco.getBairro()).isEqualTo(bairro);
        assertThat(endereco.getLocalidade()).isEqualTo(localidade);
        assertThat(endereco.getUf()).isEqualTo(uf);
    }

    @DisplayName("Testa a edição do cadastro de um endereço.")
    @Test
    void deveAlterarOsCamposInformados(){
        Endereco endereco = new Endereco("74370340", "Rua Vinte Um de Junho",
                "Setor Tancredo Neves", "Goiânia", "GO");;

        endereco.setCep(cep);
        endereco.setLogradouro(logradouro);
        endereco.setBairro(bairro);
        endereco.setLocalidade(localidade);
        endereco.setUf(uf);

        assertThat(endereco.getCep()).isEqualTo(cep);
        assertThat(endereco.getLogradouro()).isEqualTo(logradouro);
        assertThat(endereco.getBairro()).isEqualTo(bairro);
        assertThat(endereco.getLocalidade()).isEqualTo(localidade);
        assertThat(endereco.getUf()).isEqualTo(uf);
    }

    @DisplayName("Teste de vinculação de um cliente a um endereço.")
    @Test
    void deveAtribuirUmCliente(){
        Endereco endereco = new Endereco(cep, logradouro, bairro, localidade, uf);
        Cliente cliente = TestFactory.umClienteDigitado();

        endereco.setCliente(cliente);

        assertThat(endereco.getCliente()).isEqualTo(cliente);
    }

    @DisplayName("Teste de vinculação de um funcionário a um endereço.")
    @Test
    void deveAtribuirUmFuncionario(){
        Endereco endereco = new Endereco(cep, logradouro, bairro, localidade, uf);
        Funcionario funcionario = TestFactory.umFuncionarioDigitado();

        endereco.setFuncionario(funcionario);

        assertThat(endereco.getFuncionario()).isEqualTo(funcionario);
    }

}