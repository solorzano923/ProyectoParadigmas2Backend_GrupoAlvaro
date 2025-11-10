package cr.ac.una.servicioprolog.dto;
import lombok.Data;
import java.time.LocalDateTime;
@Data public class Tarea {
    private Long id;
    private String nombre;
    private int tiempoEstimado;
    private LocalDateTime fechaHoraLimite;
    private Prioridad prioridad;
    private Clima climaRequerido;
    private Tarea dependeDe;
    private boolean completada;
}