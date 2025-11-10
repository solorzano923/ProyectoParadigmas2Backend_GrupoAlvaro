package cr.ac.una.serviciotareas.controller;

import cr.ac.una.serviciotareas.entity.Prioridad;
import cr.ac.una.serviciotareas.repository.PrioridadRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prioridades")
public class PrioridadController extends CrudController<Prioridad, Long> {
    public PrioridadController(PrioridadRepository repository) {
        super(repository);
    }
}