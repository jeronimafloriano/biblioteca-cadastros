package biblioteca.cadastros.service;

import biblioteca.cadastros.domain.model.*;
import biblioteca.cadastros.domain.repository.EnderecoRepository;
import biblioteca.cep.config.CepClientGson;
import biblioteca.cep.config.CepClientRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository repository;

    @Autowired
    CepClientRestTemplate cepClient2;


    public Endereco listarEnderecoPorId(Long id){
        return repository.findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Endereco não encontrada com o ID informado."));
    }

    public List<Endereco> listarTodos(){
        return repository.findAll();
    }



    public Endereco salvar(String cep){
        Endereco endereco = CepClientGson.buscarCep(cep);
        endereco.setCep(CepClientGson.removerHifen(cep));
        return repository.save(endereco);
    }

    public Endereco salvarComCepClient2(String cep){
        Endereco endereco = cepClient2.buscarCep(cep);
        endereco.setCep(CepClientGson.removerHifen(cep));
        return repository.save(endereco);
    }

    public Endereco vincularCliente(Endereco endereco, Cliente cliente){
        endereco.setCliente(cliente);
        return repository.save(endereco);
    }

    public Endereco vincularFuncionario(Endereco endereco, Funcionario funcionario){
        endereco.setFuncionario(funcionario);
        return repository.save(endereco);
    }

    public Endereco editar(Long id, String cep){
        Endereco endereco = this.listarEnderecoPorId(id);
        Endereco buscarPorCep = CepClientGson.buscarCep(cep);
        var cepSemHifen = CepClientGson.removerHifen(buscarPorCep.getCep());

        endereco.setCep(cepSemHifen);
        endereco.setLogradouro(buscarPorCep.getLogradouro());
        endereco.setBairro(buscarPorCep.getBairro());
        endereco.setLocalidade(buscarPorCep.getLocalidade());
        endereco.setUf(buscarPorCep.getUf());

        return repository.save(endereco);
    }


    public List<FuncionariosPorCep> qtdFuncionariosPorCep(){
        return repository.totalFuncionariosPorCep();
    }

    public List<ClientesPorCep> qtdClientesPorCep(){
        return repository.totalClientesPorCep();
    }


}
