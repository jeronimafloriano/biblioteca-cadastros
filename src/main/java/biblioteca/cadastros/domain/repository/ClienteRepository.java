package biblioteca.cadastros.domain.repository;

import biblioteca.cadastros.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClienteRepository extends JpaRepository<Cliente, Long>,
    JpaSpecificationExecutor<Cliente> {
}
