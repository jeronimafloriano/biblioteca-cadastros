package biblioteca.cadastros.service;

import biblioteca.cadastros.domain.model.ClientesPorCep;
import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cadastros.domain.model.FuncionariosPorCep;
import biblioteca.cadastros.domain.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static biblioteca.cadastros.utils.TestsFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    EnderecoRepository repository;


    @InjectMocks
    EnderecoService enderecoService;


    private List<Endereco> enderecos;

    @BeforeEach
    void setUp(){
        Endereco endereco = umEnderecoDigitado();
        Endereco endereco2 = new Endereco("74370340", "Rua Vinte Um de Junho",
                "Setor Tancredo Neves", "Goiânia", "GO");

        this.enderecos = new ArrayList<>();

        enderecos.add(endereco);
        enderecos.add(endereco2);
    }

    @DisplayName("Teste de listagem de endereços cadastrados.")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveListarTodasOsEnderecosCadastrados() {
        //given
        given(repository.findAll()).willReturn(enderecos);

        //when
        List<Endereco> result = enderecoService.listarTodos();

        //then
        then(repository).should(atLeastOnce()).findAll();
        assertThat(result).hasSize(2);
        assertThat(result).contains(enderecos.get(0));
        assertThat(result).contains(enderecos.get(1));
    }
    @DisplayName("Teste de listagem de endereços por ID")
    @Test
    void deveListarEnderecosPorIdAoBuscarIdExistente() {
        //given
        Endereco endereco = umEnderecoDigitado();
        given(repository.findById(1L)).willReturn(Optional.of(endereco));

        //when
        var result = enderecoService.listarEnderecoPorId(1L);

        //then
        then(repository).should().findById(anyLong());
        then(repository).shouldHaveNoMoreInteractions();

        assertThat(result).isNotNull().isEqualTo(endereco);
    }

    @DisplayName("Retorna a quantidade de clientes por CEP")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveListarQtdEnderecosDeClientesAgrupadosPorCep() {
        //given
        ClientesPorCep clientesPorCep = new ClientesPorCep("74370320", 10L);
        ClientesPorCep clientesPorCep2 = new ClientesPorCep("74370310", 5L);

        var lista = List.of(clientesPorCep,clientesPorCep2);

        given(repository.totalClientesPorCep()).willReturn(lista);

        //when
        List<ClientesPorCep> result = enderecoService.qtdClientesPorCep();

        //then
        assertThat(result).hasSize(2).contains(clientesPorCep).contains(clientesPorCep2);
    }

    @DisplayName("Retorna a quantidade de funcionarios por CEP")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveListarQtdEnderecosDeFuncionariosAgrupadosPorCep() {
        //given
        FuncionariosPorCep funcionariosPorCep = new FuncionariosPorCep("74370320", 8L);
        FuncionariosPorCep funcionariosPorCep2 = new FuncionariosPorCep("74370310", 6L);

        var lista = List.of(funcionariosPorCep,funcionariosPorCep2);

        given(repository.totalFuncionariosPorCep()).willReturn(lista);

        //when
        List<FuncionariosPorCep> result = enderecoService.qtdFuncionariosPorCep();

        //then
        assertThat(result).hasSize(2).contains(funcionariosPorCep).contains(funcionariosPorCep2);
    }

    @DisplayName("Teste de cadastro de endereco.")
    @Test
    void deveCadastrarEnderecoAoInformarTodosOsCamposValidos() {
        //given
        Endereco endereco = new Endereco("21853090", "Rua Aurélio Cavalcanti",
                "Bangu", "Rio de Janeiro", "RJ");

        given(repository.save(endereco)).willReturn(endereco);

        //when
        var result = enderecoService.salvar("21853090");

        //then
        then(repository).should().save(endereco);
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(result).isNotNull().isEqualTo(endereco);
    }

    @DisplayName("Teste de edição de um endereço existente.")
    @Test
    void deveEditarEnderecoAoSelecionarClientexistente() {
        //given
        var endereco = this.enderecos.get(0);

        given(repository.findById(1L)).willReturn(Optional.of(endereco));

        //when
        enderecoService.editar(1L, "21853090");


        //then
        then(repository).should().save(endereco);
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(endereco.getCep()).isEqualTo("21853090");
        assertThat(endereco.getLogradouro()).isEqualTo("Rua Aurélio Cavalcanti");
        assertThat(endereco.getBairro()).isEqualTo("Bangu");
        assertThat(endereco.getLocalidade()).isEqualTo("Rio de Janeiro");
        assertThat(endereco.getUf()).isEqualTo("RJ");
    }

    @DisplayName("Teste de vinculação de um cliente à um endereço existente.")
    @Test
    void deveVincularClienteAoEndereco() {
        //given
        var endereco = this.enderecos.get(0);
        var cliente = umClienteDigitado();

        //when
        var result = enderecoService.vincularCliente(endereco, cliente);

        //then
        then(repository).should().save(endereco);
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(endereco.getCliente()).isEqualTo(cliente);
        assertThat(cliente.getEndereco()).isEqualTo(endereco);
    }

    @DisplayName("Teste de vinculação de um funcionario à um endereço existente.")
    @Test
    void deveVincularFuncionarioAoEndereco() {
        //given
        var endereco = this.enderecos.get(0);
        var funcionario = umFuncionarioDigitado();

        //when
        var result = enderecoService.vincularFuncionario(endereco, funcionario);

        //then
        then(repository).should().save(endereco);
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(endereco.getFuncionario()).isEqualTo(funcionario);
        assertThat(funcionario.getEndereco()).isEqualTo(endereco);
    }
}