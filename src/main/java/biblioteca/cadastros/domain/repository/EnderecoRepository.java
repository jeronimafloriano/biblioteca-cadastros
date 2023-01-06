package biblioteca.cadastros.domain.repository;

import biblioteca.cadastros.domain.model.ClientesPorCep;
import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cadastros.domain.model.FuncionariosPorCep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("SELECT new biblioteca.cadastros.domain.model.FuncionariosPorCep(c.cep, COUNT(c.cep)) "
            + "FROM Endereco AS c where c.funcionario is not null GROUP BY c.cep ORDER BY c.id DESC")
    List<FuncionariosPorCep> totalFuncionariosPorCep();

    @Query("SELECT new biblioteca.cadastros.domain.model.ClientesPorCep(c.cep, COUNT(c.cep)) "
            + "FROM Endereco AS c where c.cliente is not null GROUP BY c.cep ORDER BY c.id DESC")
    List<ClientesPorCep> totalClientesPorCep();


}
