package biblioteca.cadastros.controller;

import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cadastros.domain.repository.EnderecoRepository;
import biblioteca.cadastros.service.ClienteService;
import biblioteca.cadastros.service.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static biblioteca.cadastros.utils.TestsFactory.umEnderecoDigitado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
class EnderecoControllerTest {

    @Mock
    EnderecoService service;

    @Mock
    EnderecoRepository repository;

    @Mock
    ClienteService clienteService;

    @InjectMocks
    EnderecoController controller;

    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    List<Endereco> enderecos;

    public static final String PATH = "/enderecos";

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        Endereco endereco = umEnderecoDigitado();

        Endereco endereco2 = new Endereco("21853090", "Rua Aurélio Cavalcanti",
                "Bangu", "Rio de Janeiro", "RJ");


        this.enderecos = new ArrayList<>();

        enderecos.add(endereco);
        enderecos.add(endereco2);
    }

    @DisplayName("Testa a listagem de um endereço ao informar um ID existente.")
    @Test
    void deveListarEderecoPorId() throws Exception {
        //given
        Endereco endereco = this.enderecos.get(0);
        given(service.listarEnderecoPorId(3L)).willReturn(endereco);

        //when
        mockMvc.perform(get(PATH + "/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        then(service).should().listarEnderecoPorId(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(3);
    }

    @DisplayName("Testa a listagem de todos os endereços cadastrados.")
    @Test
    void deveListarTodosOsEnderecosCadastrados() throws Exception {
        //given
        Endereco endereco = this.enderecos.get(0);
        given(service.listarTodos()).willReturn(List.of(endereco));

        //when
        mockMvc.perform(get(PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        then(service).should().listarTodos();
        assertThat(service.listarTodos()).contains(endereco);
    }

    @DisplayName("Retorna a quantidade de funcionarios por CEP")
    @Test
    void deveListarQtdEnderecosDeFuncionariosAgrupadosPorCep() throws Exception {
        mockMvc.perform(get(PATH + "/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(service).should().qtdFuncionariosPorCep();
    }

    @DisplayName("Retorna a quantidade de clientes por CEP")
    @Test
    void deveListarQtdEnderecosDeClientesAgrupadosPorCep() throws Exception {
        mockMvc.perform(get(PATH + "/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(service).should().qtdClientesPorCep();
    }

}