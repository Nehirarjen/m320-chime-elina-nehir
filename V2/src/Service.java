import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Gemeinsame abstrakte Oberklasse aller buchbaren Services. */
public abstract class Service {
    private static final DateTimeFormatter DATUM_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final String id;
    private final String name;
    private final double basisPreis;
    private boolean gebucht;
    private final LocalDate erstelltAm;

    protected Service(String id, String name, double basisPreis) {
        if (id == null || id.isBlank() || name == null || name.isBlank()) {
            throw new IllegalArgumentException("ID und Name dürfen nicht leer sein.");
        }
        if (basisPreis < 0) {
            throw new IllegalArgumentException("Der Basispreis darf nicht negativ sein.");
        }
        this.id = id;
        this.name = name;
        this.basisPreis = basisPreis;
        this.erstelltAm = LocalDate.now();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBasisPreis() {
        return basisPreis;
    }

    public boolean istGebucht() {
        return gebucht;
    }

    public LocalDate getErstelltAm() {
        return erstelltAm;
    }

    public void buchen() throws ServiceException {
        if (gebucht) {
            throw new ServiceException("Service " + id + " ist bereits gebucht.");
        }
        gebucht = true;
    }

    public void stornieren() throws ServiceException {
        if (!gebucht) {
            throw new ServiceException("Service " + id + " ist nicht gebucht und kann nicht storniert werden.");
        }
        gebucht = false;
    }

    /** Jede Unterklasse legt ihre Preisberechnung selbst fest. */
    public abstract double berechnePreis();

    /** Detailinformationen der jeweiligen Unterklasse. */
    protected abstract String details();

    public String info() {
        return "%s | %-20s | Basis: CHF %7.2f | Preis: CHF %7.2f | %s | erstellt: %s | %s"
                .formatted(id, name, basisPreis, berechnePreis(),
                        gebucht ? "gebucht" : "frei", erstelltAm.format(DATUM_FORMAT), details());
    }

    /**
     * Liefert eine formattierte Darstellung; die Benutzeroberfläche entscheidet,
     * ob und wo sie ausgegeben wird.
     */
    public String printOut() {
        return info();
    }
}
