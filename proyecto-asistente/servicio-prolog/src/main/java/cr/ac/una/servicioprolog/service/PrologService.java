package cr.ac.una.servicioprolog.service;

import cr.ac.una.servicioprolog.dto.DatosPlanificacionDto;
import cr.ac.una.servicioprolog.dto.PlanOptimizadoDto;
import cr.ac.una.servicioprolog.dto.Tarea;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;
import org.jpl7.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PrologService {

    @Value("${prolog.file.path}")
    private String prologFilePath; // "/app/prolog/planificador.pl"

    public PlanOptimizadoDto optimizar(DatosPlanificacionDto datos) {

        // 1. Cargar el archivo Prolog
        Query consultQuery = new Query("consult('" + prologFilePath + "')");
        if (!consultQuery.hasSolution()) {
            System.err.println("Error al cargar el archivo Prolog: " + prologFilePath);
            return crearPlanDeError("Error interno: No se pudo cargar la base de conocimiento Prolog.");
        }

        // 2. Limpiar hechos antiguos (base de conocimiento dinámica)
        new Query("retractall(tarea(_,_,_,_,_,_))").hasSolution();

        // 3. Afirmar (assertz) los nuevos hechos de las tareas
        // Convertimos las tareas Java a hechos Prolog
        // tarea(ID, Nombre, Prioridad, Tiempo, Clima, DependeDeID)
        for (Tarea tarea : datos.getTareas()) {
            String nombre = "'" + tarea.getNombre().replace("'", "''") + "'";
            String prioridad = tarea.getPrioridad() != null ? tarea.getPrioridad().getNombre() : "baja";
            String clima = tarea.getClimaRequerido() != null ? tarea.getClimaRequerido().getNombre() : "cualquiera";
            long dependeDeID = (tarea.getDependeDe() != null) ? tarea.getDependeDe().getId() : 0;

            String hecho = String.format(
                    "assertz(tarea(%d, %s, %s, %d, %s, %d))",
                    tarea.getId(),
                    nombre,
                    prioridad,
                    tarea.getTiempoEstimado(),
                    clima,
                    dependeDeID
            );

            // Ejecutar el assert
            if (!new Query(hecho).hasSolution()) {
                System.err.println("Error al insertar hecho: " + hecho);
            }
        }

        // 4. Ejecutar la consulta principal de planificación
        Variable planOptimizado = new Variable("PlanOptimizadoIDs");
        Variable tiempoTotal = new Variable("TiempoTotalPlan");

        String consulta = String.format(
                "planificar(%d, '%s', %s, %s)",
                datos.getTiempoDisponible(),
                datos.getClimaActual(),
                planOptimizado.name(),
                tiempoTotal.name()
        );

        Query planQuery = new Query(consulta);
        PlanOptimizadoDto resultado = new PlanOptimizadoDto();

        if (planQuery.hasSolution()) {
            Map<String, Term> solucion = planQuery.oneSolution();
            Term listaIdsProlog = solucion.get(planOptimizado.name());

            // Convierte la lista Prolog a array de Terms usando JPL Util
            Term[] ids = Util.listToTermArray(listaIdsProlog);

            Map<Long, Tarea> mapaTareas = datos.getTareas().stream()
                    .collect(Collectors.toMap(Tarea::getId, t -> t));

            List<Tarea> tareasOrdenadas = new ArrayList<>();

            for (Term idTerm : ids) {
                long id;
                if (idTerm.isInteger()) {
                    id = idTerm.longValue();
                } else {
                    id = Long.parseLong(idTerm.toString());
                }

                Tarea t = mapaTareas.get(id);
                if (t != null) tareasOrdenadas.add(t);
            }

            resultado.setMensaje("Plan optimizado generado con éxito.");
            resultado.setTareasOrdenadas(tareasOrdenadas);
            resultado.setTiempoTotalEstimado(solucion.get(tiempoTotal.name()).intValue());
        } else {
            resultado.setMensaje("Alerta de Imposibilidad: No es posible completar todas las tareas con las restricciones dadas.");
            resultado.setTareasOrdenadas(new ArrayList<>());
            resultado.setTiempoTotalEstimado(0);
        }

        return resultado;
    }

    private PlanOptimizadoDto crearPlanDeError(String mensaje) {
        PlanOptimizadoDto errorDto = new PlanOptimizadoDto();
        errorDto.setMensaje(mensaje);
        errorDto.setTareasOrdenadas(new ArrayList<>());
        errorDto.setTiempoTotalEstimado(0);
        return errorDto;
    }
}