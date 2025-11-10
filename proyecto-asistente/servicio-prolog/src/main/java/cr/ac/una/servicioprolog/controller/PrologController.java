package cr.ac.una.servicioprolog.controller;

import cr.ac.una.servicioprolog.dto.DatosPlanificacionDto;
import cr.ac.una.servicioprolog.dto.PlanOptimizadoDto;
import cr.ac.una.servicioprolog.service.PrologService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prolog")
public class PrologController {

    @Autowired
    private PrologService prologService;

    // Este es el endpoint que llama servicio-tareas
    @PostMapping("/optimizar-plan")
    public PlanOptimizadoDto optimizarPlan(@RequestBody DatosPlanificacionDto datos) {
        return prologService.optimizar(datos);
    }
}