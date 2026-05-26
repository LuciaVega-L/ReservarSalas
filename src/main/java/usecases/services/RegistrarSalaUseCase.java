package usecases.services;

import entities.Sala;
import entities.TipoSala;
import usecases.dto.OperationResult;
import usecases.ports.SalaRepository;

public class RegistrarSalaUseCase {

    private final SalaRepository salaRepository;

    public RegistrarSalaUseCase(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public OperationResult execute(String nombre, int capacidad, String tipoStr) {
        if (nombre == null || nombre.isBlank())
            return OperationResult.fail("El nombre de la sala no puede estar vacío.");
        if (capacidad <= 0)
            return OperationResult.fail("La capacidad debe ser mayor a cero.");

        TipoSala tipo;
        try {
            tipo = TipoSala.fromString(tipoStr);
        } catch (IllegalArgumentException e) {
            return OperationResult.fail(e.getMessage());
        }

        // ID autogenerado
        String id = "SALA-" + (salaRepository.findAll().size() + 1);

        // Paso 7 diagrama secuencia: envía datos → Paso 8: save(sala)
        Sala sala = new Sala(id, nombre, capacidad, tipo);
        salaRepository.save(sala);

        // Paso 9-11: ok → OperationResult.ok
        return OperationResult.ok("Sala registrada exitosamente con ID: " + id);
    }
}
