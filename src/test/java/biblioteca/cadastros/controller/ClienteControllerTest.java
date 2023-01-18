package biblioteca.cadastros.controller;

import biblioteca.cadastros.domain.model.Cliente;
import biblioteca.cadastros.domain.repository.ClienteRepository;
import biblioteca.cadastros.dto.ClienteDto;
import biblioteca.cadastros.service.ClienteService;
import biblioteca.cadastros.service.EnderecoService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static biblioteca.cadastros.TestFactory.umClienteDigitado;
import static biblioteca.cadastros.TestFactory.umEnderecoDigitado;
import static biblioteca.cadastros.config.TestesConfig.objectToJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    ClienteService service;

    @Mock
    ClienteRepository repository;

    @Mock
    EnderecoService enderecoService;

    @InjectMocks
    ClienteController controller;

    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    public static final String PATH = "/clientes";

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @DisplayName("Testa a listagem de um cliente ao informar um ID existente.")
    @Test
    void deveListarPessoaPorId() throws Exception {
        //given
        Cliente cliente = umClienteDigitado();
        given(service.listarPorId(3L)).willReturn(cliente);

        //when
        mockMvc.perform(get(PATH + "/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        then(service).should().listarPorId(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(3);
    }

    @DisplayName("Testa a listagem de todos os clientes cadastrados.")
    @Test
    void deveListarTodasOsClientesCadastrados() throws Exception {
        //given
        Cliente cliente = umClienteDigitado();
        Pageable paginacao = PageRequest.of(0,5, Sort.Direction.ASC, "id");
        given(service.listarTodos(paginacao)).willReturn(List.of(cliente));

        //when
        mockMvc.perform(get(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("pagina", "0")
                        .param("qtdItens", "5")
                        .param("ordenacao", "id"))
                .andExpect(status().isOk());

        //then
        then(service).should().listarTodos(paginacao);
        assertThat(service.listarTodos(paginacao)).contains(cliente);
    }

    @DisplayName("Testa o cadastramento de um cliente.")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveCadastrarCliente() throws Exception {
        //given
        var cliente = new Cliente("Carlos", "89233687090", umEnderecoDigitado());
        ClienteDto dto = new ClienteDto(cliente.getNome(), cliente.getDocumento(), cliente.getEndereco().getCep());

        when(service.cadastrar(any(ClienteDto.class))).thenReturn(cliente);

        var result = mockMvc.perform(request(HttpMethod.POST, PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("name", dto.getNome())
                        .param("documento", dto.getDocumento())
                        .param("cep", dto.getCep())
                        .content(objectToJson(dto)))
                .andExpect(status().is2xxSuccessful());

        String conteudo = result.andReturn().getResponse().getContentAsString();
        //Cliente cliente2 = new Gson().fromJson(conteudo, Cliente.class);

        //then
        verify(service).cadastrar(any(ClienteDto.class));
        assertTrue(conteudo.contains("Carlos"));

    }

    @DisplayName("Testa a edição de um cliente cadastrado.")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveEditarCliente() throws Exception {
        //given
        var cliente = new Cliente("Camila", "19898236027", umEnderecoDigitado());
        ClienteDto dto = new ClienteDto(cliente.getNome(), cliente.getDocumento(), cliente.getEndereco().getCep());

        given(service.listarPorId(7L)).willReturn(cliente);

        // when
        mockMvc.perform(put(PATH + "/7")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("name", cliente.getNome())
                        .param("documento", cliente.getDocumento())
                        .param("cep", cliente.getEndereco().getCep())
                        .content(objectToJson(dto)))
                .andExpect(status().is2xxSuccessful());

        //then
        then(service).should().editar(longArgumentCaptor.capture(), any(ClienteDto.class));
        assertThat(longArgumentCaptor.getValue()).isEqualTo(7);
    }

    @DisplayName("Testa a listagem de um cliente com base em um filtro informado.")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveListarClientePorFiltroInformado() throws Exception {
        //given
        Cliente cliente = umClienteDigitado();
        ClienteDto dto = new ClienteDto(cliente.getNome(), cliente.getDocumento(), cliente.getEndereco().getCep());

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(cliente, matcher);

        given(service.buscarPor(cliente)).willReturn(List.of(cliente));

        //when
        mockMvc.perform(get(PATH + "/filtrar")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("name", cliente.getNome())
                        .param("documento", cliente.getDocumento())
                        .param("cep", cliente.getEndereco().getCep())
                        .content(objectToJson(dto)))
                .andExpect(status().isOk());

        //then
        assertThat(service.buscarPor(cliente)).contains(cliente);
    }

}