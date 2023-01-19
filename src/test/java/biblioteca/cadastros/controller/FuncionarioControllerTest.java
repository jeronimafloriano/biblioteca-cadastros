package biblioteca.cadastros.controller;

import biblioteca.cadastros.domain.model.Funcionario;
import biblioteca.cadastros.domain.repository.FuncionarioRepository;
import biblioteca.cadastros.dto.FuncionarioDto;
import biblioteca.cadastros.service.EnderecoService;
import biblioteca.cadastros.service.FuncionarioService;
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
import org.springframework.data.domain.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static biblioteca.cadastros.utils.TestsFactory.*;
import static biblioteca.cadastros.utils.TestesConfig.objectToJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FuncionarioControllerTest {

    @Mock
    FuncionarioService service;

    @Mock
    FuncionarioRepository repository;

    @Mock
    EnderecoService enderecoService;

    @InjectMocks
    FuncionarioController controller;

    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    public static final String PATH = "/funcionarios";

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @DisplayName("Testa a listagem de um funcionario ao informar um ID existente.")
    @Test
    void deveListarFuncionarioPorId() throws Exception {
        //given
        Funcionario funcionario = umFuncionarioDigitado();
        given(service.listarPorId(3L)).willReturn(funcionario);

        //when
        mockMvc.perform(get(PATH + "/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        then(service).should().listarPorId(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(3);
    }

    @DisplayName("Testa a listagem de todos os funcionarios cadastrados.")
    @Test
    void deveListarTodasOsFuncionariosCadastrados() throws Exception {
        //given
        Funcionario funcionario = umFuncionarioDigitado();
        Pageable paginacao = PageRequest.of(0,5, Sort.Direction.ASC, "id");
        given(service.listarTodos(paginacao)).willReturn(List.of(funcionario));

        //when
        mockMvc.perform(get(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("pagina", "0")
                        .param("qtdItens", "5")
                        .param("ordenacao", "id"))
                .andExpect(status().isOk());

        //then
        then(service).should().listarTodos(paginacao);
        assertThat(service.listarTodos(paginacao)).contains(funcionario);
    }

    @DisplayName("Testa o cadastramento de um funcionario.")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveCadastrarFuncionario() throws Exception {
        //given
        var funcionario = new Funcionario("Carlos", "89233687090", umEnderecoDigitado());
        FuncionarioDto dto = new FuncionarioDto(funcionario.getNome(), funcionario.getDocumento(), funcionario.getEndereco().getCep());

        when(service.cadastrar(any(FuncionarioDto.class))).thenReturn(funcionario);

        mockMvc.perform(request(HttpMethod.POST, PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("name", dto.getNome())
                        .param("documento", dto.getDocumento())
                        .param("cep", dto.getCep())
                        .content(objectToJson(dto)))
                .andExpect(status().is2xxSuccessful());

        //then
        verify(service).cadastrar(any(FuncionarioDto.class));
    }

    @DisplayName("Testa a edição de um funcionario cadastrado.")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveEditarFuncionario() throws Exception {
        //given
        var funcionario = new Funcionario("Carlos", "89233687090", umEnderecoDigitado());
        FuncionarioDto dto = new FuncionarioDto(funcionario.getNome(), funcionario.getDocumento(), funcionario.getEndereco().getCep());

        given(service.listarPorId(7L)).willReturn(funcionario);

        // when
        mockMvc.perform(put(PATH + "/7")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("name", funcionario.getNome())
                        .param("documento", funcionario.getDocumento())
                        .param("cep", funcionario.getEndereco().getCep())
                        .content(objectToJson(dto)))
                .andExpect(status().is2xxSuccessful());

        //then
        then(service).should().editar(longArgumentCaptor.capture(), any(FuncionarioDto.class));
        assertThat(longArgumentCaptor.getValue()).isEqualTo(7);
    }

    @DisplayName("Testa a listagem de um funcionario com base em um filtro informado.")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveListarFuncionarioPorFiltroInformado() throws Exception {
        //given
        Funcionario funcionario = umFuncionarioDigitado();
        FuncionarioDto dto = new FuncionarioDto(funcionario.getNome(), funcionario.getDocumento(), funcionario.getEndereco().getCep());

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(funcionario, matcher);

        given(service.buscarPor(any(Funcionario.class))).willReturn(List.of(funcionario));

        //when
        mockMvc.perform(get(PATH + "/filtrar")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("name", funcionario.getNome())
                        .param("documento", funcionario.getDocumento())
                        .param("cep", funcionario.getEndereco().getCep())
                        .content(objectToJson(dto)))
                .andExpect(status().isOk());

        //then
        assertThat(service.buscarPor(funcionario)).contains(funcionario);
    }


    @DisplayName("Testa a remoção de um funcionario.")
    @Test
    void deletar() throws Exception {
        mockMvc.perform(delete(PATH + "/8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }
}