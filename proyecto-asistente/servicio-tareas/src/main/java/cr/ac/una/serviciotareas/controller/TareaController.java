package cr.ac.una.serviciotareas.controller;

import cr.ac.una.serviciotareas.entity.Tarea;
import cr.ac.una.serviciotareas.repository.TareaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tareas")
public class TareaController extends CrudController<Tarea, Long> {
    public TareaController(TareaRepository repository) {
        super(repository);
    }
}