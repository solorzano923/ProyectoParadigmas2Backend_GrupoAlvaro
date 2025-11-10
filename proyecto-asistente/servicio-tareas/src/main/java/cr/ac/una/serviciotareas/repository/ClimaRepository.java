package cr.ac.una.serviciotareas.repository;

import cr.ac.una.serviciotareas.entity.Clima;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClimaRepository extends JpaRepository<Clima, Long> {
}