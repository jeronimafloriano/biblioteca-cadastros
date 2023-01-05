package biblioteca.cadastros.domain.repository;

import biblioteca.cadastros.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
