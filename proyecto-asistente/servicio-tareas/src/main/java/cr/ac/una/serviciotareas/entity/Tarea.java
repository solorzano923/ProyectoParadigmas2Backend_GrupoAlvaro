package cr.ac.una.serviciotareas.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int tiempoEstimado; // En minutos
    private LocalDateTime fechaHoraLimite;

    @ManyToOne
    @JoinColumn(name = "prioridad_id")
    private Prioridad prioridad;

    @ManyToOne
    @JoinColumn(name = "clima_id")
    private Clima climaRequerido;

    // Para la dependencia
    @ManyToOne
    @JoinColumn(name = "depende_de_tarea_id")
    private Tarea dependeDe; // Nulo si no depende de ninguna

    private boolean completada = false;
}