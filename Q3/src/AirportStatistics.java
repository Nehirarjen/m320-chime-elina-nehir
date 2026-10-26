import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;

/** Berechnet Statistiken über alle gespeicherten Flughäfen. */
public class AirportStatistics {

    public record Count(String value, int count) {
    }

    private final AirportRepository repository;

    public AirportStatistics(AirportRepository repository) {
        this.repository = repository;
    }

    public int totalAirports() {
        return repository.size();
    }

    public Optional<Count> mostFrequentCity() {
        return mostFrequent(Airport::city);
    }

    public Optional<Count> mostFrequentState() {
        return mostFrequent(Airport::state);
    }

    private Optional<Count> mostFrequent(Function<Airport, String> field) {
        Map<String, Integer> counts = new HashMap<>();
        for (Airport airport : repository.getAll()) {
            String value = field.apply(airport);
            if (!value.isEmpty()) {
                counts.merge(value, 1, Integer::sum);
            }
        }
        Count best = null;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            boolean moreOften = best == null || entry.getValue() > best.count();
            boolean tieButEarlier = best != null && entry.getValue() == best.count()
                    && entry.getKey().compareToIgnoreCase(best.value()) < 0;
            if (moreOften || tieButEarlier) {
                best = new Count(entry.getKey(), entry.getValue());
            }
        }
        return Optional.ofNullable(best);
    }

    public Map<String, List<String>> duplicateNames() {
        Map<String, List<Airport>> groups = new TreeMap<>();
        for (Airport airport : repository.getAll()) {
            groups.computeIfAbsent(airport.name().toLowerCase(Locale.ROOT), key -> new ArrayList<>())
                    .add(airport);
        }
        Map<String, List<String>> result = new TreeMap<>();
        for (List<Airport> group : groups.values()) {
            if (group.size() > 1) {
                group.sort(Comparator.comparing(Airport::code));
                List<String> codes = new ArrayList<>();
                for (Airport airport : group) {
                    codes.add(airport.code());
                }
                Collections.sort(codes);
                result.put(group.get(0).name(), codes);
            }
        }
        return result;
    }

    public Map<Character, Integer> countByFirstLetter() {
        Map<Character, Integer> counts = new TreeMap<>();
        for (Airport airport : repository.getAll()) {
            counts.merge(airport.code().charAt(0), 1, Integer::sum);
        }
        return counts;
    }
}
