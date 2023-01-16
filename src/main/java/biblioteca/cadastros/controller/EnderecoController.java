package biblioteca.cadastros.controller;

import biblioteca.cadastros.domain.model.ClientesPorCep;
import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cadastros.domain.model.FuncionariosPorCep;
import biblioteca.cadastros.service.EnderecoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService service;


    @GetMapping(value = "{id}", produces = "application/json")
    @ApiOperation("Obter detalhes de um endereco")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Endereço encontrado"),
            @ApiResponse(code = 404, message = "Endereço não encontrado para o ID informado")
    })
    public Endereco listarPorId(@PathVariable Long id){
        return service.listarEnderecoPorId(id);
    }

    @GetMapping(produces = "application/json")
    @ApiOperation("Listar todos os enderecos cadastrados")
    public List<Endereco> listarTodos(){
        return service.listarTodos();
    }

    @GetMapping(value = "/funcionarios", produces = "application/json")
    public List<FuncionariosPorCep> qtdFuncionariosPorCep(){
        return service.qtdFuncionariosPorCep();
    }

    @GetMapping(value = "/clientes", produces = "application/json")
    public List<ClientesPorCep> qtdClientesPorCep(){
        return service.qtdClientesPorCep();
    }


}
