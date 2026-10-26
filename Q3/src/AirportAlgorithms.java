import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

/** Sucht und sortiert die gespeicherten Flughäfen. */
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

    public List<Airport> findByCity(String city) {
        String wanted = clean(city);
        if (wanted.isEmpty()) {
            return List.of();
        }
        return filter(airport -> airport.city().equalsIgnoreCase(wanted));
    }

    public List<Airport> findByState(String state) {
        String wanted = clean(state);
        if (wanted.isEmpty()) {
            return List.of();
        }
        return filter(airport -> airport.state().equalsIgnoreCase(wanted));
    }

    public List<Airport> findByName(String searchTerm) {
        String term = clean(searchTerm).toLowerCase(Locale.ROOT);
        if (term.isEmpty()) {
            return List.of();
        }
        return filter(airport -> airport.name().toLowerCase(Locale.ROOT).contains(term));
    }

    public List<Airport> sortedByName() {
        List<Airport> list = repository.getAll();
        list.sort(BY_NAME);
        return list;
    }

    public List<Airport> sortedByCityThenName() {
        List<Airport> list = repository.getAll();
        list.sort(BY_CITY_THEN_NAME);
        return list;
    }

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
