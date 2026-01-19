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
import org.springframework.data.jpa.domain.Specification;

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
        List<ClienteDto> result = clienteService.buscarTodos("", paginacao);

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
        var result = clienteService.buscarPorId(1L);

        //then
        then(repository).should().findById(anyLong());
        then(repository).shouldHaveNoMoreInteractions();

        assertThat(result).isNotNull();
        assertThat(result.getNome()).isEqualTo(cliente.getNome());
        assertThat(result.getDocumento()).isEqualTo(cliente.getDocumento());
        assertThat(result.getCep()).isEqualTo(cliente.getEndereco().getCep());
    }

    @Test
    void listarEnderecosPorCliente() {
        //given
        Cliente cliente = new Cliente("Maria", "89233687090", endereco);
        given(repository.findById(2L)).willReturn(Optional.of(cliente));

        //when
        var result = clienteService.buscarEnderecosPorCliente(2L);

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

        Example example = Example.of(cliente.getNome(), matcher);

        PageImpl<Cliente> page = new PageImpl<>(List.of(cliente));
        given(repository.findAll(any(Specification.class), any(Pageable.class))).willReturn(page);

        //when
        List<ClienteDto> result = clienteService.buscarTodos(cliente.getNome(), Pageable.unpaged());

        //then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNome()).isEqualTo(cliente.getNome());
        assertThat(result.get(0).getDocumento()).isEqualTo(cliente.getDocumento());
        assertThat(result.get(0).getCep()).isEqualTo(cliente.getEndereco().getCep());
    }

    @DisplayName("Teste de cadastro de cliente.")
    @Test
    void deveCadastrarClienteAoInformarTodosOsCamposValidos() {
        //given
        var cliente = this.clientes.get(0);
        ClienteDto dto = new ClienteDto(1l, cliente.getNome(),
                                        cliente.getDocumento(),
                                        cliente.getEndereco().getCep());

        given(repository.save(any(Cliente.class))).willReturn(cliente);

        //when
        var result = clienteService.cadastrar(dto);

        //then
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(result).isNotNull();
        assertThat(result.getNome()).isEqualTo(cliente.getNome());
        assertThat(result.getDocumento()).isEqualTo(cliente.getDocumento());
        assertThat(result.getCep()).isEqualTo(cliente.getEndereco().getCep());
    }

    @DisplayName("Teste de edição de um cliente existente.")
    @Test
    void deveEditarClienteAoSelecionarClientexistente() {
        //given
        var cliente = this.clientes.get(0);
        ClienteDto dto = new ClienteDto(1l, cliente.getNome(),
            cliente.getDocumento(), cliente.getEndereco().getCep());

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