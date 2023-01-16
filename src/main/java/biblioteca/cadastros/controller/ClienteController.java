package biblioteca.cadastros.controller;

import biblioteca.cadastros.domain.model.Cliente;
import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cadastros.dto.ClienteDto;
import biblioteca.cadastros.service.ClienteService;
import biblioteca.cadastros.service.EnderecoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired //remover
    private ClienteService service;

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Obter detalhes do cadastro de um cliente")
    @ApiResponses({
            @ApiResponse(code  = 200, message = "Cliente encontrado"),
            @ApiResponse(code  = 404, message = "Cliente não encontrado para o ID informado")
    })
    public Cliente listarPorId(@PathVariable Long id){
        return service.listarPorId(id);
    }

    @GetMapping(value = "/filtrar", produces = "application/json")
    @ApiOperation("Obter detalhes do cadastro de um cliente baseado no filtro informado.")
    @ApiResponses({
            @ApiResponse(code  = 200, message = "Cliente encontrado"),
            @ApiResponse(code  = 404, message = "Cliente não encontrado para o filtro informado")
    })
    public List<Cliente> listarPorFiltro(Cliente filtro){
        return service.buscarPor(filtro);
    }


    @GetMapping(produces = "application/json")
    @ApiOperation("Listar todas os clientes cadastrados")
    public List<Cliente> listarTodos(@RequestParam int pagina,
                                    @RequestParam int qtdItens, @RequestParam String ordenacao){

        Pageable paginacao = PageRequest.of(pagina,qtdItens, Sort.Direction.ASC, ordenacao);
        return service.listarTodos(paginacao);

    }

    @GetMapping(value = "/{id}/endereco", produces = "application/json")
    @ApiOperation("Listar endereço cadastrado por cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Endereco encontrado"),
            @ApiResponse(code = 404, message = "Endereco não encontrado para o ID do cliente informado")
    })
    public Endereco listarEnderecos(@PathVariable Long id){
        return service.listarEnderecosPorCliente(id);
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente cadastrar(@RequestBody @Valid ClienteDto clienteDto){
        Cliente cliente = service.cadastrar(clienteDto);
        enderecoService.vincularCliente(cliente.getEndereco(), cliente);
        return cliente;
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Editar o cadastro de um cliente")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editar(@PathVariable Long id, @RequestBody @Valid ClienteDto clienteDto){
        service.editar(id, clienteDto);
    }


}
