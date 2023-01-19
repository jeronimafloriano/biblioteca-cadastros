package biblioteca.cadastros.service;

import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cadastros.domain.model.Funcionario;
import biblioteca.cadastros.domain.repository.FuncionarioRepository;
import biblioteca.cadastros.dto.FuncionarioDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static biblioteca.cadastros.utils.TestsFactory.umEnderecoDigitado;
import static biblioteca.cadastros.utils.TestsFactory.umFuncionarioDigitado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @Mock
    FuncionarioRepository repository;

    @Mock
    EnderecoService enderecoService;

    @InjectMocks
    FuncionarioService funcionarioService;

    private Endereco endereco;

    private List<Funcionario> funcionarios;

    @BeforeEach
    void setUp(){
        this.endereco = umEnderecoDigitado();

        Funcionario funcionario = new Funcionario("Poliana", "25420051095", endereco);
        Funcionario funcionario2 = new Funcionario("Maria", "30976771000180", endereco);

        this.funcionarios = new ArrayList<>();

        funcionarios.add(funcionario);
        funcionarios.add(funcionario2);
    }

    @DisplayName("Teste de listagem de funcionarios cadastrados.")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveListarTodosOsFuncionariosCadastrados() {
        //given
        Pageable paginacao = PageRequest.of(0,5, Sort.Direction.ASC, "id");
        Page<Funcionario> page = new PageImpl<>(funcionarios);
        given(repository.findAll(paginacao)).willReturn(page);

        //when
        List<Funcionario> result = funcionarioService.listarTodos(paginacao);

        //then
        then(repository).should(atLeastOnce()).findAll(paginacao);
        assertThat(result).hasSize(2);
    }
    @DisplayName("Teste de listagem de funcionário por ID")
    @Test
    void deveListarFuncionarioPorIdAoBuscarIdExistente() {
        //given
        Funcionario funcionario = new Funcionario("Suzana", "61948111000119", endereco);
        given(repository.findById(1L)).willReturn(Optional.of(funcionario));

        //when
        var result = funcionarioService.listarPorId(1L);

        //then
        then(repository).should().findById(anyLong());
        then(repository).shouldHaveNoMoreInteractions();

        assertThat(result).isNotNull().isEqualTo(funcionario);
    }

    @Test
    void listarEnderecosPorCliente() {
        //given
        Funcionario funcionario = new Funcionario("Silvania", "19012083000167", endereco);
        given(repository.findById(2L)).willReturn(Optional.of(funcionario));

        //when
        var result = funcionarioService.listarEnderecosPorFuncionario(2L);

        //then
        then(repository).should().findById(anyLong());
        then(repository).shouldHaveNoMoreInteractions();

        assertThat(result).isNotNull().isEqualTo(endereco);
        assertThat(funcionario.getEndereco()).isNotNull().isEqualTo(endereco);
    }

    @DisplayName("Retorna uma lista de funcionários com base em um filtro informado.")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveListarFuncionariosPorFiltroInformado() {
        //given
        Funcionario funcionario = new Funcionario("Paulo", "37712885095", endereco);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(funcionario, matcher);

        given(repository.findAll(example, Sort.by("id").ascending())).willReturn(List.of(funcionario));

        //when
        List<Funcionario> result = funcionarioService.buscarPor(funcionario);

        //then
        assertThat(result).hasSize(1).contains(funcionario);
    }

    @DisplayName("Teste de cadastro de funcionário.")
    @Test
    void deveCadastrarFuncionarioAoInformarTodosOsCamposValidos() {
        //given
        var funcionario = this.funcionarios.get(0);
        FuncionarioDto dto = new FuncionarioDto(funcionario.getNome(), funcionario.getDocumento(), funcionario.getEndereco().getCep());

        given(repository.save(any(Funcionario.class))).willReturn(funcionario);
        given(enderecoService.salvar(endereco.getCep())).willReturn(endereco);

        //when
        var result = funcionarioService.cadastrar(dto);

        //then
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(result).isNotNull().isEqualTo(funcionario);
    }

    @DisplayName("Teste de edição de um funcionario existente.")
    @Test
    void deveEditarFuncionarioAoSelecionarClientexistente() {
        //given
        var funcionario = this.funcionarios.get(0);
        FuncionarioDto dto = new FuncionarioDto(funcionario.getNome(), funcionario.getDocumento(), funcionario.getEndereco().getCep());

        given(repository.findById(1L)).willReturn(Optional.of(funcionario));

        //when
        funcionarioService.editar(1L, dto);

        //then
        then(repository).should().save(funcionario);
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(funcionario.getNome()).isEqualTo("Poliana");
        assertThat(funcionario.getDocumento()).isEqualTo("25420051095");
    }

    @Test
    @DisplayName("Testa a remoção de um funcionario.")
    void deletar() {
        Funcionario funcionario = umFuncionarioDigitado();
        given(repository.findById(5L)).willReturn(Optional.of(funcionario));
        funcionarioService.remover(5L);

        verify(repository).delete(funcionario);
    }
}