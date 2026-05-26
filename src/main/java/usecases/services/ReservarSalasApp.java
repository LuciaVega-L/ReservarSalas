package usecases.services;

import entities.Sala;
import infrastructure.repositories.InMemorySalaRepository;
import usecases.dto.OperationResult;
import usecases.ports.SalaRepository;

import java.util.List;

public class ReservarSalasApp {

    private final SalaRepository       salaRepository;
    private final RegistrarSalaUseCase registrarSalaUseCase;

    public ReservarSalasApp() {
        this.salaRepository       = new InMemorySalaRepository();
        this.registrarSalaUseCase = new RegistrarSalaUseCase(salaRepository);
    }

    public OperationResult registrarSala(String nombre, int capacidad, String tipo) {
        return registrarSalaUseCase.execute(nombre, capacidad, tipo);
    }

    public List<Sala> listarSalas() {
        return salaRepository.findAll();
    }
}
