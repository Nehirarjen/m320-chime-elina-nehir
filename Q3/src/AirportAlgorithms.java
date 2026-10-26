import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

/**
 * Such- und Sortieralgorithmen (Teil B). Sie laufen über die gespeicherten
 * Airport-Objekte und geben Listen zurück, sie drucken nichts.
 */
public class AirportAlgorithms {

    private static final Comparator<Airport> BY_NAME =
            Comparator.comparing(Airport::name, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Airport::code);

    private static final Comparator<Airport> BY_CITY_THEN_NAME =
            Comparator.comparing(Airport::city, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(BY_NAME);

    private final AirportRepository repository;

    public AirportAlgorithms(AirportRepository repository) {
        this.repository = repository;
    }

    /** Alle Flughäfen einer Stadt (Gross-/Kleinschreibung egal). */
    public List<Airport> findByCity(String city) {
        String wanted = clean(city);
        if (wanted.isEmpty()) {
            return List.of();
        }
        return filter(airport -> airport.city().equalsIgnoreCase(wanted));
    }

    /** Alle Flughäfen eines Staats (Gross-/Kleinschreibung egal). */
    public List<Airport> findByState(String state) {
        String wanted = clean(state);
        if (wanted.isEmpty()) {
            return List.of();
        }
        return filter(airport -> airport.state().equalsIgnoreCase(wanted));
    }

    /**
     * Algorithmus: Teilstring-Suche im Namen.
     * Durchlaufen wird jeder gespeicherte Flughafen. Geprüft wird, ob sein
     * Name (klein geschrieben) den Suchbegriff (klein geschrieben) enthält.
     * Gesammelt werden alle Treffer in einer Liste, sortiert nach Code.
     * Ein leerer Suchbegriff liefert nichts, sonst würde jeder Flughafen passen.
     */
    public List<Airport> findByName(String searchTerm) {
        String term = clean(searchTerm).toLowerCase(Locale.ROOT);
        if (term.isEmpty()) {
            return List.of();
        }
        return filter(airport -> airport.name().toLowerCase(Locale.ROOT).contains(term));
    }

    /**
     * Sortiert alphabetisch nach Name. Die HashMap bleibt wie sie ist: Es wird
     * eine temporäre Liste aus ihren Werten sortiert (List.sort, dahinter
     * steckt TimSort).
     */
    public List<Airport> sortedByName() {
        List<Airport> list = repository.getAll();
        list.sort(BY_NAME);
        return list;
    }

    /** Sortiert zuerst nach Stadt, bei gleicher Stadt nach Name. Ohne Stadtdaten ergibt das die Namensreihenfolge. */
    public List<Airport> sortedByCityThenName() {
        List<Airport> list = repository.getAll();
        list.sort(BY_CITY_THEN_NAME);
        return list;
    }

    /** Gemeinsame Schleife für alle Filter: prüft die Bedingung bei jedem Flughafen. */
    private List<Airport> filter(Predicate<Airport> condition) {
        List<Airport> result = new ArrayList<>();
        for (Airport airport : repository.getAll()) {
            if (condition.test(airport)) {
                result.add(airport);
            }
        }
        result.sort(Comparator.comparing(Airport::code));
        return result;
    }

    private static String clean(String text) {
        return text == null ? "" : text.trim();
    }
}
