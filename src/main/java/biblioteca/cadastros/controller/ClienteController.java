package biblioteca.cadastros.controller;

import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cadastros.dto.ClienteDto;
import biblioteca.cadastros.service.ClienteService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping(value = "/clientes", produces = "application/json")
public class ClienteController {

    private ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @Operation(description = "Lista todos os clientes cadastrados com paginação", tags = {"Clientes"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de clientes retornada com sucesso"),
            @ApiResponse(code = 400, message = "Parâmetros de paginação inválidos")
    })
    @GetMapping
    public ResponseEntity<Page<ClienteDto>> buscar(
            @ParameterObject Pageable pageable,
            @Parameter(description = "Texto para filtro por nome do cliente")
            @RequestParam(required = false) String filtro) {
        Page<ClienteDto> clientes = service.buscarTodosPaginado(filtro, pageable);
        return ResponseEntity.ok(clientes);
    }

    @Operation(
        description = "Obter detalhes do cadastro de um cliente",
        tags = {"Clientes"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> buscarPorId(@PathVariable Long id) {
        ClienteDto cliente = service.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @Operation(description = "Verifica se um cliente existe", tags = {"Clientes"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente existe"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> verificarExistencia(@PathVariable Long id) {
        service.buscarPorId(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
        description = "Lista os endereço vinculados à um cliente",
        tags = {"Clientes"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Endereco encontrado"),
            @ApiResponse(code = 404, message = "Endereco não encontrado para o ID do cliente informado")
    })
    @GetMapping(value = "/{id}/endereco")
    public ResponseEntity<Endereco> buscarEnderecos(@PathVariable Long id) {
        Endereco endereco = service.buscarEnderecosPorCliente(id);
        return ResponseEntity.ok(endereco);
    }

    @Operation(description = "Realiza o cadastro de um cliente", tags = {"Clientes"})
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente criado com sucesso"),
            @ApiResponse(code = 400, message = "Dados inválidos"),
            @ApiResponse(code = 409, message = "Cliente já existe (conflito)")
    })
    @PostMapping
    public ResponseEntity<ClienteDto> cadastrar(@RequestBody @Valid ClienteDto clienteDto) {
        ClienteDto clienteCriado = service.cadastrar(clienteDto);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteCriado.getId())
                .toUri();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        
        return new ResponseEntity<>(clienteCriado, headers, HttpStatus.CREATED);
    }


    @Operation(
        description = "Edita o cadastro de um cliente (atualização completa)",
        tags = {"Clientes"})
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado"),
            @ApiResponse(code = 400, message = "Dados inválidos")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> editar(
            @PathVariable Long id,
            @Parameter(description = "Dados do cliente")
            @RequestBody @Valid ClienteDto clienteDto) {
        service.editar(id, clienteDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        description = "Atualiza parcialmente o cadastro de um cliente",
        tags = {"Clientes"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente atualizado parcialmente"),
            @ApiResponse(code = 404, message = "Cliente não encontrado"),
            @ApiResponse(code = 400, message = "Dados inválidos")
    })
    @PatchMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> atualizarParcial(
            @PathVariable Long id,
            @Parameter(description = "Campos a serem atualizados (apenas os campos enviados serão atualizados)")
            @RequestBody Map<String, Object> campos) {
        ClienteDto clienteAtualizado = service.atualizarParcial(id, campos);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @Operation(description = "Remove o cadastro de um cliente", tags = {"Clientes"})
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente removido com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
