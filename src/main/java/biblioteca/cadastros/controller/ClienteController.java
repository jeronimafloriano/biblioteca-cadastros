package biblioteca.cadastros.controller;

import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cadastros.dto.ClienteDto;
import biblioteca.cadastros.service.ClienteService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/clientes", produces = "application/json")
public class ClienteController {

    private ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @Operation(description = "Lista todos os clientes cadastrados", tags = {"Clientes"})
    @GetMapping
    public ResponseEntity<List<ClienteDto>> buscar(@ParameterObject  Pageable pageable,
                                                   @Parameter(description = "Texto para filtro por nome do cliente")
                                                   @RequestParam(required = false) String filtro){
        List<ClienteDto> clientes = service.buscarTodos(filtro, pageable);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @Operation(
        description = "Obter detalhes do cadastro de um cliente",
        tags = {"Clientes"})
    @ApiResponses({
            @ApiResponse(code  = 200, message = "Cliente encontrado"),
            @ApiResponse(code  = 404, message = "Cliente não encontrado para o ID informado")
    })
    @GetMapping(value = "/{id}")
    public ClienteDto buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @Operation(
        description = "Lista os endereço vinculados à um cliente",
        tags = {"Clientes"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Endereco encontrado"),
            @ApiResponse(code = 404, message = "Endereco não encontrado para o ID do cliente informado")
    })
    @GetMapping(value = "/{id}/endereco")
    public Endereco buscarEnderecos(@PathVariable Long id){
        return service.buscarEnderecosPorCliente(id);
    }

    @Operation(description = "Realiza o cadastro de um cliente", tags = {"Clientes"})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDto cadastrar(@RequestBody @Valid ClienteDto clienteDto){
        return service.cadastrar(clienteDto);
    }

    @Operation(
        description = "Edita o cadastro de um cliente",
        tags = {"Clientes"})
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id,
                                       @Parameter(description = "Dados do cliente")
                                       @RequestBody @Valid ClienteDto clienteDto){
        service.editar(id, clienteDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Remove o cadastro de um cliente", tags = {"Clientes"})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }


}
