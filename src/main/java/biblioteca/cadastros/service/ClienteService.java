package biblioteca.cadastros.service;

import biblioteca.cadastros.domain.model.Cliente;
import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cadastros.domain.repository.ClienteRepository;
import biblioteca.cadastros.dto.ClienteDto;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final EnderecoService enderecoService;

    public ClienteService(ClienteRepository repository, EnderecoService enderecoService) {
        this.repository = repository;
        this.enderecoService = enderecoService;
    }

    @Transactional(readOnly = true)
    public List<ClienteDto> buscarTodos(String filtro, Pageable paginacao){
        Page<Cliente> clientes = filtro!= null && !filtro.isBlank()
            ? repository.findAll(criarFiltroDeNome(filtro), paginacao)
            : repository.findAll(paginacao);

        return clientes.stream()
            .map(ClienteDto::map)
            .collect(toList());
    }

    @Transactional(readOnly = true)
    public ClienteDto buscarPorId(Long id){
        Cliente cliente = buscarCliente(id);
        return ClienteDto.map(cliente);
    }

    public Endereco buscarEnderecosPorCliente(Long id){
        Cliente cliente = buscarCliente(id);
        return cliente.getEndereco();
    }

    @Transactional
    public ClienteDto cadastrar(ClienteDto dto){
        Endereco endereco = enderecoService.salvarComCepClient2(dto.getCep());
        Cliente cliente = repository.save(new Cliente(dto.getNome(), dto.getDocumento(), endereco));
        enderecoService.vincularCliente(cliente.getEndereco(), cliente);
        return ClienteDto.map(cliente);
    }

    @Transactional
    public void editar(Long id, ClienteDto dto){
        Cliente cliente = buscarCliente(id);
        var enderecoId = cliente.getEndereco().getId();
        Endereco endereco = enderecoService.editar(enderecoId, dto.getCep());

        cliente.setNome(dto.getNome());
        cliente.setDocumento(dto.getDocumento());
        cliente.setEndereco(endereco);

        enderecoService.vincularCliente(endereco, cliente);
        repository.save(cliente);
    }

    @Transactional
    public void excluir(Long id) {
        repository.findById(id).ifPresentOrElse(repository::delete,
            () -> {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Cliente não encontrado.");
            });
    }

    private Specification<Cliente> criarFiltroDeNome(String filtro){
        return ((root, query, criteriaBuilder) ->
            criteriaBuilder.like(root.get("nome"), filtro + "%"));
    }

    private Cliente buscarCliente(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Cliente não encontrado com o ID informado."));
    }
}
