package biblioteca.cadastros.domain.repository;

import biblioteca.cadastros.domain.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
