package biblioteca.cadastros.service;

import biblioteca.cadastros.domain.model.Cliente;
import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cadastros.domain.repository.ClienteRepository;
import biblioteca.cadastros.dto.ClienteDto;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    ClienteRepository repository;

    @Mock
    EnderecoService enderecoService;

    @InjectMocks
    ClienteService clienteService;

    private Endereco endereco;

    private List<Cliente> clientes;

    @BeforeEach
    void setUp(){
        this.endereco = umEnderecoDigitado();

        Cliente cliente = new Cliente("Marcos", "27281029020", endereco);
        Cliente cliente2 = new Cliente("Gertrudes", "87191863048", endereco);

        this.clientes = new ArrayList<>();

        clientes.add(cliente);
        clientes.add(cliente2);
    }

    @DisplayName("Teste de listagem de clientes cadastrados.")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveListarTodasOsClientesCadastradas() {
        //given
        Pageable paginacao = PageRequest.of(0,5, Sort.Direction.ASC, "id");
        Page<Cliente> page = new PageImpl<>(clientes);
        given(repository.findAll(paginacao)).willReturn(page);

        //when
        List<Cliente> result = clienteService.listarTodos(paginacao);

        //then
        then(repository).should(atLeastOnce()).findAll(paginacao);
        assertThat(result).hasSize(2);
    }
    @DisplayName("Teste de listagem de cliente por ID")
    @Test
    void deveListarClientePorIdAoBuscarIdExistente() {
        //given
        Cliente cliente = new Cliente("Maria", "89233687090", endereco);
        given(repository.findById(1L)).willReturn(Optional.of(cliente));

        //when
        var result = clienteService.listarPorId(1L);

        //then
        then(repository).should().findById(anyLong());
        then(repository).shouldHaveNoMoreInteractions();

        assertThat(result).isNotNull().isEqualTo(cliente);
    }

    @Test
    void listarEnderecosPorCliente() {
        //given
        Cliente cliente = new Cliente("Maria", "89233687090", endereco);
        given(repository.findById(2L)).willReturn(Optional.of(cliente));

        //when
        var result = clienteService.listarEnderecosPorCliente(2L);

        //then
        then(repository).should().findById(anyLong());
        then(repository).shouldHaveNoMoreInteractions();

        assertThat(result).isNotNull().isEqualTo(endereco);
    }

    @DisplayName("Retorna uma lista de cliente com base em um filtro informado.")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveListarClientesPorFiltroInformado() {
        //given
        Cliente cliente = new Cliente("Paulo", "37712885095", endereco);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(cliente, matcher);

        given(repository.findAll(example, Sort.by("id").ascending())).willReturn(List.of(cliente));

        //when
        List<Cliente> result = clienteService.buscarPor(cliente);

        //then
        assertThat(result).hasSize(1).contains(cliente);
    }

    @DisplayName("Teste de cadastro de cliente.")
    @Test
    void deveCadastrarClienteAoInformarTodosOsCamposValidos() {
        //given
        var cliente = this.clientes.get(0);
        ClienteDto dto = new ClienteDto(cliente.getNome(), cliente.getDocumento(), cliente.getEndereco().getCep());

        given(repository.save(any(Cliente.class))).willReturn(cliente);
        given(enderecoService.salvar(endereco.getCep())).willReturn(endereco);

        //when
        var result = clienteService.cadastrar(dto);

        //then
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(result).isNotNull().isEqualTo(cliente);
    }

    @DisplayName("Teste de edição de um cliente existente.")
    @Test
    void deveEditarClienteAoSelecionarClientexistente() {
        //given
        var cliente = this.clientes.get(0);
        ClienteDto dto = new ClienteDto(cliente.getNome(), cliente.getDocumento(), cliente.getEndereco().getCep());

        given(repository.findById(1L)).willReturn(Optional.of(cliente));

        //when
        clienteService.editar(1L, dto);

        //then
        then(repository).should().save(cliente);
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(cliente.getNome()).isEqualTo("Marcos");
        assertThat(cliente.getDocumento()).isEqualTo("27281029020");
    }
}