package cr.ac.una.serviciotareas.dto;

import cr.ac.una.serviciotareas.entity.Tarea;
import lombok.Data;
import java.util.List;

@Data
public class PlanOptimizadoDto {
    private String mensaje; // Ej: "Plan calculado" o "Imposible completar" [cite: 32]
    private List<Tarea> tareasOrdenadas;
    private int tiempoTotalEstimado;
}