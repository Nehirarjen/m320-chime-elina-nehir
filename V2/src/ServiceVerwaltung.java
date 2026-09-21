import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Verwaltet alle Service-Unterklassen polymorph in einer gemeinsamen Liste. */
public class ServiceVerwaltung {
    private final List<Service> services = new ArrayList<>();

    public void add(Service service) {
        if (findById(service.getId()).isPresent()) {
            throw new IllegalArgumentException("Die ID " + service.getId() + " ist bereits vorhanden.");
        }
        services.add(service);
    }

    public boolean removeById(String id) {
        return services.removeIf(service -> service.getId().equalsIgnoreCase(id));
    }

    public Optional<Service> findById(String id) {
        return services.stream()
                .filter(service -> service.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public void printAlle() {
        if (services.isEmpty()) {
            System.out.println("Es sind noch keine Services vorhanden.");
            return;
        }
        System.out.println("\n--- Alle Services ---");
        // Polymorphismus: Das konkrete Objekt bestimmt berechnePreis() und details().
        services.forEach(service -> System.out.println(service.printOut()));
    }

    /** Umsatz zählt nur Services, die momentan gebucht sind. */
    public double gesamtUmsatz() {
        return services.stream()
                .filter(Service::istGebucht)
                .mapToDouble(Service::berechnePreis)
                .sum();
    }
}
