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

/** Statistische Auswertungen über alle gespeicherten Flughäfen (Teil B, Punkt 7). */
public class AirportStatistics {

    /** Ein Wert und wie oft er vorkommt. */
    public record Count(String value, int count) {
    }

    private final AirportRepository repository;

    public AirportStatistics(AirportRepository repository) {
        this.repository = repository;
    }

    public int totalAirports() {
        return repository.size();
    }

    /** Stadt mit den meisten Flughäfen. Leer, wenn die Daten keine Städte enthalten. */
    public Optional<Count> mostFrequentCity() {
        return mostFrequent(Airport::city);
    }

    /** Staat mit den meisten Flughäfen. Leer, wenn die Daten keine Staaten enthalten. */
    public Optional<Count> mostFrequentState() {
        return mostFrequent(Airport::state);
    }

    /**
     * Algorithmus: häufigster Wert eines Felds.
     * Durchlaufen wird jeder Flughafen. Sein Feldwert (Stadt oder Staat) wird
     * in einer Hilfs-Map hochgezählt, leere Werte zählen nicht. Danach wird
     * der Eintrag mit dem grössten Zähler gesucht. Bei Gleichstand gewinnt
     * der alphabetisch frühere Wert, damit das Ergebnis immer gleich ist.
     */
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

    /**
     * Algorithmus: doppelte Flughafen-Namen finden.
     * Durchlaufen wird jeder Flughafen. Er wird unter seinem Namen (klein
     * geschrieben) in eine Gruppe einsortiert. Danach bleiben nur Gruppen mit
     * mehr als einem Flughafen übrig. Ergebnis: Name und die zugehörigen
     * Codes (alphabetisch). Die Codes selbst sind nie doppelt, das verhindert
     * die HashMap.
     */
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

    /**
     * Eigene Statistik: Wie viele Flughäfen beginnen mit welchem Buchstaben
     * im Code? Durchlaufen wird jeder Flughafen, der erste Buchstabe des
     * Codes wird hochgezählt. Die TreeMap sortiert die Buchstaben.
     */
    public Map<Character, Integer> countByFirstLetter() {
        Map<Character, Integer> counts = new TreeMap<>();
        for (Airport airport : repository.getAll()) {
            counts.merge(airport.code().charAt(0), 1, Integer::sum);
        }
        return counts;
    }
}
