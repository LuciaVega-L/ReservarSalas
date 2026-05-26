package entities;

public class Sala {

    private String   id;
    private String   nombre;
    private int      capacidad;
    private TipoSala tipo;
    private boolean  disponible;

    public Sala(String id, String nombre, int capacidad, TipoSala tipo) {
        this.id        = id;
        this.nombre    = nombre;
        this.capacidad = capacidad;
        this.tipo      = tipo;
        this.disponible = true;
    }

    public String   getId()          { return id; }
    public String   getNombre()      { return nombre; }
    public int      getCapacidad()   { return capacidad; }
    public TipoSala getTipo()        { return tipo; }
    public boolean  isDisponible()   { return disponible; }

    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return "{id='" + id + "', nombre='" + nombre +
                "', capacidad=" + capacidad +
                ", tipo=" + tipo +
                ", disponible=" + disponible + "}";
    }
}
