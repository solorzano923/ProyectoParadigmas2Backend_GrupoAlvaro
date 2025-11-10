package cr.ac.una.serviciotareas.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HttpConfig {
    @Bean
    @LoadBalanced // Usará Eureka para resolver el nombre del servicio
    public WebClient.Builder lbWebClientBuilder() {
        return WebClient.builder();
    }
}