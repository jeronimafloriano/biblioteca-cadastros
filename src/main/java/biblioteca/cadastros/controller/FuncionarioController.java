package biblioteca.cadastros.controller;

import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cadastros.domain.model.Funcionario;
import biblioteca.cadastros.dto.FuncionarioDto;
import biblioteca.cadastros.service.EnderecoService;
import biblioteca.cadastros.service.FuncionarioService;
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
@RequestMapping(value = "/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Obter detalhes do cadastro de um funcionário")
    @ApiResponses({
            @ApiResponse(code  = 200, message = "Funcionário encontrado"),
            @ApiResponse(code  = 404, message = "Funcionário não encontrado para o ID informado")
    })
    public Funcionario listarPorId(@PathVariable Long id){
        return service.listarPorId(id);
    }

    @GetMapping(value = "/filtrar", produces = "application/json")
    @ApiOperation("Obter detalhes do cadastro de um funcionário baseado no filtro informado.")
    @ApiResponses({
            @ApiResponse(code  = 200, message = "Funcionário encontrado"),
            @ApiResponse(code  = 404, message = "Funcionário não encontrado para o filtro informado")
    })
    public List<Funcionario> listarPorFiltro(Funcionario filtro){
        return service.buscarPor(filtro);
    }


    @GetMapping(produces = "application/json")
    @ApiOperation("Listar todas os funcionários cadastrados")
    public List<Funcionario> listarTodos(@RequestParam int pagina,
                                    @RequestParam int qtdItens, @RequestParam String ordenacao){

        Pageable paginacao = PageRequest.of(pagina,qtdItens, Sort.Direction.ASC, ordenacao);
        return service.listarTodos(paginacao);

    }

    @GetMapping(value = "/{id}/endereco", produces = "application/json")
    @ApiOperation("Listar endereço cadastrado por funcionário")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Endereco encontrado"),
            @ApiResponse(code = 404, message = "Endereco não encontrado para o ID do funcionário informado")
    })
    public Endereco listarEnderecos(@PathVariable Long id){
        return service.listarEnderecosPorFuncionario(id);
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Funcionario cadastrar(@RequestBody @Valid FuncionarioDto funcionarioDto){
        Funcionario funcionario = service.cadastrar(funcionarioDto);
        enderecoService.vincularFuncionario(funcionario.getEndereco(), funcionario);
        return funcionario;
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Editar o cadastro de um funcionário")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editar(@PathVariable Long id, @RequestBody @Valid FuncionarioDto funcionarioDto){
        service.editar(id, funcionarioDto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){
        service.remover(id);
    }


}
