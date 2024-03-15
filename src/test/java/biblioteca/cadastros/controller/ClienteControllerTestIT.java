package biblioteca.cadastros.controller;

import biblioteca.cadastros.domain.model.Cliente;
import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cadastros.domain.repository.ClienteRepository;
import biblioteca.cadastros.domain.repository.EnderecoRepository;
import biblioteca.cadastros.dto.ClienteDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerTestIT {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost:";

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    private static TestRestTemplate restTemplate;

    private Endereco endereco;

    @BeforeAll
    public static void init(){
        restTemplate = new TestRestTemplate();
    }

    @BeforeEach
    void setUp(){
        baseUrl = baseUrl.concat(port + "/clientes");

        endereco = new Endereco("74370320", "Rua Vinte Um de Junho",
                "Setor Tancredo Neves", "Goiânia", "GO");

        enderecoRepository.saveAndFlush(endereco);

    }

    @Test
    @Sql(statements = "INSERT INTO CLIENTE (id,documento, nome) VALUES (123,11782063005, 'Joao Marcos' )", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM CLIENTE WHERE id in (123) ", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deveListarPessoaPorId() throws Exception {

        var result = restTemplate.getForObject(baseUrl + "/123", Cliente.class);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("Joao Marcos", result.getNome()),
                () -> assertEquals("11782063005", result.getDocumento())
        );

    }

    @Test
    @Sql(statements = "INSERT INTO CLIENTE (id,documento, nome) VALUES (963,89233687090, 'Maria' )", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO CLIENTE (id,documento, nome) VALUES (369,11782063005, 'Marcos Souza' )", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM CLIENTE WHERE id in (963, 369) ", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deveListarTodasOsClientesCadastrados() throws Exception {
        baseUrl = baseUrl.concat("?pagina=0&qtdItens=10&ordenacao=id");
        List<Cliente> result = restTemplate.getForObject(baseUrl, List.class);

        var cliente1 = clienteRepository.findById(963L).get();
        var cliente2 = clienteRepository.findById(369L).get();

        assertNotNull(result);
        assertEquals("Maria", cliente1.getNome());
        assertEquals("Marcos Souza", cliente2.getNome());
    }

    @Test
    void deveCadastrarCliente() throws Exception {
        var dto = new ClienteDto(1l,"Jose", "89233687090", "74370410");
        Cliente response = restTemplate.postForObject(baseUrl, dto, Cliente.class);

        assertEquals("Jose", response.getNome());
        assertNotNull(clienteRepository.findById(response.getId()));
    }

    @Test
    void deveEditarCliente() throws Exception {
        var cliente = new Cliente("Silvania", "10124119077", endereco);
        endereco.setCliente(cliente);

        clienteRepository.saveAndFlush(cliente);
        enderecoRepository.saveAndFlush(endereco);

        var dto = new ClienteDto(1l,"Suzana", "10124119077", "74370320");
        restTemplate.put(baseUrl + "/" + cliente.getId(), dto);

        Cliente result = clienteRepository.findById(cliente.getId()).get();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("Suzana", result.getNome())
        );

    }


}