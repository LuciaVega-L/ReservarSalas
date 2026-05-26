package entities;

public enum TipoSala {
    AULA,
    LABORATORIO,
    AUDITORIO;

    public static TipoSala fromString(String tipo) {
        switch (tipo.trim().toUpperCase()) {
            case "AULA":        return AULA;
            case "LABORATORIO": return LABORATORIO;
            case "AUDITORIO":   return AUDITORIO;
            default: throw new IllegalArgumentException("Tipo de sala no válido: " + tipo);
        }
    }

    @Override
    public String toString() {
        String name = this.name();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}
