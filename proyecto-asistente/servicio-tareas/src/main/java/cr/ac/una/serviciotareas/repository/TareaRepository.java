package cr.ac.una.serviciotareas.repository;

import cr.ac.una.serviciotareas.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    // Busca tareas que no estén completadas
    List<Tarea> findByCompletadaFalse();
}