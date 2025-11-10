package cr.ac.una.serviciotareas.controller;

import cr.ac.una.serviciotareas.entity.Clima;
import cr.ac.una.serviciotareas.repository.ClimaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/climas")
public class ClimaController extends CrudController<Clima, Long> {
    public ClimaController(ClimaRepository repository) {
        super(repository);
    }
}