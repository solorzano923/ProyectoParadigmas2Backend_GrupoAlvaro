package cr.ac.una.serviciotareas.dto;

import cr.ac.una.serviciotareas.entity.Tarea;
import lombok.Data;
import java.util.List;

@Data
public class DatosPlanificacionDto {
    private List<Tarea> tareas;
    private int tiempoDisponible; //
    private String climaActual; //
}