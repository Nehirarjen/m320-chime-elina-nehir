import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Speichert alle Flughäfen in einer HashMap mit dem Code als Schlüssel. */
public class AirportRepository {

    private final Map<String, Airport> airports = new HashMap<>();

    /** Fügt den Flughafen nur hinzu, wenn sein Code noch nicht in der Map ist. */
    public boolean addAirport(Airport airport) {
        if (airport == null) {
            return false;
        }
        return airports.putIfAbsent(airport.code(), airport) == null;
    }

    /** Holt den Flughafen direkt über seinen Code aus der Map. */
    public Optional<Airport> findByCode(String code) {
        return Optional.ofNullable(airports.get(Airport.normalizeCode(code)));
    }

    /** Ersetzt einen bestehenden Flughafen in der Map durch einen neuen mit demselben Code. */
    public boolean updateAirport(String code, String name, String city, String state) {
        String key = Airport.normalizeCode(code);
        return airports.replace(key, new Airport(key, name, city, state)) != null;
    }

    /** Löscht den Flughafen mit diesem Code aus der Map und meldet, ob er existierte. */
    public boolean removeAirport(String code) {
        return airports.remove(Airport.normalizeCode(code)) != null;
    }

    /** Gibt die Anzahl der Einträge in der Map zurück. */
    public int size() {
        return airports.size();
    }

    /** Gibt eine Kopie aller Werte der Map als Liste zurück. */
    public List<Airport> getAll() {
        return new ArrayList<>(airports.values());
    }
}
