package cr.ac.una.serviciotareas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ServicioTareasApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicioTareasApplication.class, args);
    }

}