package cr.ac.una.serviciotareas.controller;

import cr.ac.una.serviciotareas.dto.DatosPlanificacionDto;
import cr.ac.una.serviciotareas.dto.PlanOptimizadoDto;
import cr.ac.una.serviciotareas.repository.TareaRepository;
import cr.ac.una.serviciotareas.service.PlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plan")
public class PlannerController {

    @Autowired
    private PlannerService plannerService;

    @Autowired
    private TareaRepository tareaRepository;

    // Este endpoint es el que llama el Frontend para obtener el plan
    // Cumple Rúbrica y
    @GetMapping("/optimizar")
    public PlanOptimizadoDto getPlanOptimizado(
            @RequestParam int tiempoDisponible, //
            @RequestParam String climaActual, //
            @RequestParam String horaInicio) { //

        // En un futuro, horaInicio se usaría, por ahora lo omitimos

        DatosPlanificacionDto datos = new DatosPlanificacionDto();
        // Enviamos solo las tareas no completadas
        datos.setTareas(tareaRepository.findByCompletadaFalse());
        datos.setTiempoDisponible(tiempoDisponible);
        datos.setClimaActual(climaActual);

        // Llamamos al servicio de Prolog
        return plannerService.obtenerPlanOptimizado(datos);
    }
}