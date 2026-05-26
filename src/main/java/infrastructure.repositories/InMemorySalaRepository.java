package infrastructure.repositories;

import entities.Sala;
import usecases.ports.SalaRepository;

import java.util.*;

public class InMemorySalaRepository implements SalaRepository {

    private final Map<String, Sala> store = new LinkedHashMap<>();

    @Override
    public void save(Sala sala) {
        store.put(sala.getId(), sala);
    }

    @Override
    public List<Sala> findAll() {
        return new ArrayList<>(store.values());
    }

}
