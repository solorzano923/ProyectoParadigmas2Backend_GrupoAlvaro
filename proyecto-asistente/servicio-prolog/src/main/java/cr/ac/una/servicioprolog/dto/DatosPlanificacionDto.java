package cr.ac.una.servicioprolog.dto;
import lombok.Data;
import java.util.List;
@Data public class DatosPlanificacionDto {
    private List<Tarea> tareas;
    private int tiempoDisponible;
    private String climaActual;
}