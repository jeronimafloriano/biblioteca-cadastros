package biblioteca.cadastros.service;

import biblioteca.cadastros.domain.model.Cliente;
import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cadastros.domain.repository.ClienteRepository;
import biblioteca.cadastros.dto.ClienteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class ClienteService {

    @Autowired
    ClienteRepository repository;

    @Autowired
    EnderecoService enderecoService;

    public List<Cliente> listarTodos(Pageable paginacao){
        Page<Cliente> pessoas = repository.findAll(paginacao);
        return pessoas.stream().collect(toList());
    }

    public Cliente listarPorId(Long id){
        return repository.findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Pessoa não encontrada com o ID informado."));
    }

    public Endereco listarEnderecosPorCliente(Long id){
        Cliente cliente = this.listarPorId(id);
        return cliente.getEndereco();
    }

    public List<Cliente> buscarPor(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        return repository.findAll(example, Sort.by("id").ascending());
    }

    public Cliente cadastrar(ClienteDto dto){
        Endereco endereco = enderecoService.salvar(dto.getCep());
        Cliente cliente = new Cliente(dto.getNome(), dto.getDocumento(), endereco);
        return repository.save(cliente);
    }

    public void editar(Long id, ClienteDto dto){
        Cliente cliente = this.listarPorId(id);
        var enderecoId = cliente.getEndereco().getId();
        Endereco endereco = enderecoService.editar(enderecoId, dto.getCep());

        cliente.setNome(dto.getNome());
        cliente.setDocumento(dto.getDocumento());
        cliente.setEndereco(endereco);

        repository.save(cliente);
    }


}
