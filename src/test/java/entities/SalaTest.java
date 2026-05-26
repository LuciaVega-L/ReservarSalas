package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalaTest {

    @Test
    void getId() {
        Sala sala = new Sala("1", "Sala", 200, TipoSala.fromString("AUDITORIO"));
        assertEquals("1", sala.getId());
    }

    @Test
    void getNombre() {
        Sala sala = new Sala("1", "Sala", 200, TipoSala.fromString("AUDITORIO"));
        assertEquals("Sala", sala.getNombre());
    }

    @Test
    void getCapacidad() {
        Sala sala = new Sala("1", "Sala", 200, TipoSala.fromString("AUDITORIO"));
        assertEquals(200, sala.getCapacidad());
    }

    @Test
    void getTipo() {
        Sala sala = new Sala("1", "Sala", 200, TipoSala.fromString("AUDITORIO"));
        TipoSala tipoSala = TipoSala.fromString("AUDITORIO");
        assertEquals(tipoSala, sala.getTipo());
    }

    @Test
    void isDisponible() {
        Sala sala = new Sala("1", "Sala", 200, TipoSala.fromString("AUDITORIO"));
        assertEquals(true, sala.isDisponible());
    }

    @Test
    void setDisponible() {
        Sala sala = new Sala("1", "Sala", 200, TipoSala.fromString("AUDITORIO"));
        sala.setDisponible(false);
        assertEquals(false, sala.isDisponible());
    }

}