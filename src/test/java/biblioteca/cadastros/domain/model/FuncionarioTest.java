package biblioteca.cadastros.domain.model;

import biblioteca.cadastros.TestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FuncionarioTest {

    private final String nome = "José";
    private final String documento = "39761193000139";


    @DisplayName("Teste de cadastro de funcionário, informando todos os campos necessários.")
    @Test
    void deveCriarFuncionarioComTodosOsCamposInformados(){
        Endereco endereco = TestFactory.umEnderecoDigitado();
        Funcionario funcionario = new Funcionario(nome, documento, endereco);

        assertThat(funcionario.getNome()).isEqualTo(nome);
        assertThat(funcionario.getDocumento()).isEqualTo(documento);
        assertThat(funcionario.getEndereco()).isEqualTo(endereco);

    }


    @DisplayName("Testa a edição do cadastro de um funcionário.")
    @Test
    void deveAlterarOsCamposInformados(){
        Endereco endereco = TestFactory.umEnderecoDigitado();
        Funcionario funcionario = new Funcionario(nome, documento, endereco);

        Endereco outroEndereco = new Endereco("54310210", "Rua Luiz Eloi de Pontes",
                "Prazeres", "Jaboatão dos Guararapes", "PE");

        funcionario.setNome("Marcos Oliveira");
        funcionario.setDocumento("61999242000125");
        funcionario.setEndereco(outroEndereco);

        assertThat(funcionario.getNome()).isEqualTo("Marcos Oliveira");
        assertThat(funcionario.getDocumento()).isEqualTo("61999242000125");
        assertThat(funcionario.getEndereco()).isEqualTo(outroEndereco);
    }

}