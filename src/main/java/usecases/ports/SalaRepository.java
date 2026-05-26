package usecases.ports;

import entities.Sala;

import java.util.List;
import java.util.Optional;

public interface SalaRepository {

    void           save(Sala sala);
    List<Sala>     findAll();
}
