package cr.ac.una.servicioprolog.dto;
import lombok.Data;
import java.util.List;
@Data public class PlanOptimizadoDto {
    private String mensaje;
    private List<Tarea> tareasOrdenadas;
    private int tiempoTotalEstimado;
}