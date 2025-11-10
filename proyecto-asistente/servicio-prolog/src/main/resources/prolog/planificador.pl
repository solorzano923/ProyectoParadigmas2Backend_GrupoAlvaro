% Archivo: planificador.pl

% --- HECHOS DINÁMICOS ---
% Estos hechos serán insertados (assertz) desde Java.
% tarea(ID, Nombre, Prioridad(alta,media,baja), Tiempo(min), ClimaReq, DependeDeID)
:- dynamic tarea/6.

% --- REGLAS AUXILIARES ---

% Mapeo de prioridades a valores numéricos para facilitar la comparación
prioridad_valor(alta, 3).
prioridad_valor(media, 2).
prioridad_valor(baja, 1).
prioridad_valor(desconocida, 0). % Default

% Regla para verificar si una tarea es ejecutable según el clima
% Si el clima requerido es 'cualquiera', siempre es ejecutable.
clima_permite(ClimaActual, ClimaRequerido) :-
    (ClimaRequerido == 'cualquiera' ; ClimaActual == ClimaRequerido).

% Regla para verificar si todas las dependencias de una tarea están completas
% (en la lista de tareas ya planificadas)
dependencias_cumplidas(_, []). % Caso base: no hay dependencias (ID = 0)
dependencias_cumplidas(PlanActual, TareaDependeID) :-
    TareaDependeID \= 0,
    member(TareaDependeID, PlanActual). % La tarea ID ya debe estar en el plan

% Regla para obtener el tiempo total de una lista de tareas (por sus IDs)
tiempo_total([], 0).
tiempo_total([TareaID | RestoIDs], TiempoTotal) :-
    tarea(TareaID, _, _, Tiempo, _, _), % Busca el tiempo de la TareaID
    tiempo_total(RestoIDs, TiempoResto),
    TiempoTotal is Tiempo + TiempoResto.

% --- LÓGICA DE PLANIFICACIÓN (NÚCLEO) ---

% Predicado principal que será llamado desde Java
% planificar(TiempoDisponible, ClimaActual, PlanOptimizadoIDs, TiempoTotalPlan)
planificar(TiempoDisponible, ClimaActual, PlanOptimizadoIDs, TiempoTotalPlan) :-
    % 1. Encontrar todas las tareas candidatas
    findall(ID, tarea(ID, _, _, _, _, _), TareasCandidatas),

    % 2. Intentar encontrar una permutación (un orden) que sea válida
    % 'permutation' prueba todos los órdenes posibles.
    permutation(TareasCandidatas, PlanPosible),

    % 3. Validar esta permutación
    validar_plan(PlanPosible, ClimaActual, []), % [] es el plan inicial (vacío)

    % 4. Si el plan es válido, calcular su tiempo
    tiempo_total(PlanPosible, TiempoTotalPlan),

    % 5. Verificar si el tiempo total cabe en el tiempo disponible
    TiempoTotalPlan =< TiempoDisponible,

    % 6. Asignar el resultado. El '!' corta la búsqueda, devolviendo la primera solución válida.
    PlanOptimizadoIDs = PlanPosible,
    !. % Corte: Encontramos la primera permutación válida que cabe en el tiempo.

% Si no se encuentra ningún plan que quepa en el tiempo, falla y devuelve 'false'.

% validar_plan(TareasPorValidar, ClimaActual, PlanValidadoParcial)
% Caso base: no hay más tareas por validar
validar_plan([], _, _).

% Caso recursivo:
validar_plan([TareaID | TareasRestantes], ClimaActual, PlanParcial) :-
    % Obtener datos de la tarea actual
    tarea(TareaID, _, PrioridadStr, _, ClimaReq, DependeDeID),

    % 1. Validar Clima
    clima_permite(ClimaActual, ClimaReq),

    % 2. Validar Dependencias (verifica si 'DependeDeID' está en PlanParcial)
    (DependeDeID == 0 ; member(DependeDeID, PlanParcial)),

    % 3. Validar Prioridad (Regla: no puede planificar una 'baja' si aún quedan 'altas' pendientes)
    prioridad_valor(PrioridadStr, ValorPrioridadActual),
    \+ (
        % Esto significa: "No debe existir una tarea en TareasRestantes..."
        member(OtraTareaID, TareasRestantes),
        tarea(OtraTareaID, _, OtraPrioridadStr, _, _, _),
        prioridad_valor(OtraPrioridadStr, ValorOtraPrioridad),
        % "...cuya prioridad sea mayor que la actual"
        ValorOtraPrioridad > ValorPrioridadActual
    ),

    % Si todo es válido, añadir TareaID al plan parcial y continuar validando el resto
    validar_plan(TareasRestantes, ClimaActual, [TareaID | PlanParcial]).