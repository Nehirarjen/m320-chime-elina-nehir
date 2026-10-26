import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Datenhaltung: Hier liegt die HashMap mit allen Flughäfen (Schlüssel = Code).
 * <p>
 * Die Map ist privat. Nach aussen gibt es nur die nötigen Operationen und
 * {@link #getAll()}, das eine Kopie der Werte liefert. Für Suchen, Sortieren
 * und Statistik ist {@link AirportAlgorithms} bzw. {@link AirportStatistics}
 * zuständig.
 */
public class AirportRepository {

    private final Map<String, Airport> airports = new HashMap<>();

    /**
     * Fügt einen Flughafen hinzu, aber nur wenn der Code noch frei ist.
     * Entscheid bei doppeltem Code: Der bestehende Eintrag bleibt, der neue
     * wird abgelehnt. So überschreibt ein versehentliches Hinzufügen nie
     * Daten. Zum Ändern gibt es {@link #updateAirport}.
     *
     * @return true, wenn hinzugefügt; false bei null oder bereits vorhandenem Code
     */
    public boolean addAirport(Airport airport) {
        if (airport == null) {
            return false;
        }
        return airports.putIfAbsent(airport.code(), airport) == null;
    }

    /** Sucht über den Schlüssel der HashMap (direkter Zugriff, kein Durchlaufen). */
    public Optional<Airport> findByCode(String code) {
        return Optional.ofNullable(airports.get(Airport.normalizeCode(code)));
    }

    /**
     * Ersetzt Name, Stadt und Staat eines bestehenden Flughafens.
     *
     * @return true, wenn der Flughafen existierte und geändert wurde
     * @throws IllegalArgumentException wenn die neuen Werte ungültig sind (die Map bleibt dann unverändert)
     */
    public boolean updateAirport(String code, String name, String city, String state) {
        String key = Airport.normalizeCode(code);
        return airports.replace(key, new Airport(key, name, city, state)) != null;
    }

    /** @return true, wenn ein Flughafen mit diesem Code entfernt wurde */
    public boolean removeAirport(String code) {
        return airports.remove(Airport.normalizeCode(code)) != null;
    }

    public int size() {
        return airports.size();
    }

    /** Kopie aller Flughäfen in beliebiger Reihenfolge (die HashMap garantiert keine). */
    public List<Airport> getAll() {
        return new ArrayList<>(airports.values());
    }
}
