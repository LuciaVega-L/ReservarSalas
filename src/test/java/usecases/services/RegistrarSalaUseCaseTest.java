package usecases.services;

import infrastructure.repositories.InMemorySalaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecases.dto.OperationResult;
import usecases.ports.SalaRepository;

import static org.junit.jupiter.api.Assertions.*;

class RegistrarSalaUseCaseTest {

    private SalaRepository repository;
    private RegistrarSalaUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new InMemorySalaRepository();
        useCase = new RegistrarSalaUseCase(repository);
    }

    @Test
    void execute_conDatosValidos_retornaMensajeExito() {
        OperationResult result = useCase.execute("Aula 101", 30, "AULA");

        assertTrue(result.isSuccess());
        assertEquals("Sala registrada exitosamente con ID: SALA-1", result.getMessage());
    }

    @Test
    void UbicacionDuplicada_retornaMensajeError() {
        useCase.execute("Aula 101", 30, "AULA");

        OperationResult result = useCase.execute("Aula 101", 20, "AULA");

        assertEquals("Nombre de la sala debe ser unico", result.getMessage());
    }
}