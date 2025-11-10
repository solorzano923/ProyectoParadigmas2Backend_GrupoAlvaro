package cr.ac.una.serviciotareas.service;

import cr.ac.una.serviciotareas.dto.DatosPlanificacionDto;
import cr.ac.una.serviciotareas.dto.PlanOptimizadoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PlannerService {
    private final WebClient client;

    @Value("${prolog.service.name}")
    private String prologServiceName; // "servicio-prolog"

    public PlannerService(WebClient.Builder builder) {
        // Construimos la URL base usando el nombre del servicio en Eureka
        this.client = builder.baseUrl("http://" + prologServiceName).build();
    }

    public PlanOptimizadoDto obtenerPlanOptimizado(DatosPlanificacionDto datos) {
        return client.post()
                .uri("/api/prolog/optimizar-plan")
                .body(Mono.just(datos), DatosPlanificacionDto.class)
                .retrieve()
                .bodyToMono(PlanOptimizadoDto.class)
                .block(); // Usamos block() para una llamada síncrona simple
    }
}