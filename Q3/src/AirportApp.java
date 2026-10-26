import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Startklasse: importiert die CSV-Datei und startet das Menü.
 * <p>
 * Die Datei wird als erstes Argument übergeben. Ohne Argument wird
 * airports.csv an den üblichen Orten gesucht (Projektordner, Q3, Q3/src).
 */
public class AirportApp {

    public static void main(String[] args) {
        AirportRepository repository = new AirportRepository();

        Path file = findCsv(args);
        if (file == null) {
            System.out.println("airports.csv was not found. Starting with an empty airport list.");
        } else {
            importFile(file, repository);
        }

        new UserInterface(repository).run();
    }

    private static void importFile(Path file, AirportRepository repository) {
        try {
            CsvImporter.Result result = CsvImporter.importFrom(file, repository);
            System.out.println("Imported " + result.imported() + " airports from " + file
                    + " (skipped: " + result.skipped() + ", duplicates: " + result.duplicates() + ").");
            result.messages().forEach(message -> System.out.println("  " + message));
        } catch (IOException e) {
            System.out.println("The file could not be imported: " + e.getMessage());
            System.out.println("Starting with the airports imported so far.");
        }
    }

    private static Path findCsv(String[] args) {
        List<Path> candidates = args.length > 0
                ? List.of(Path.of(args[0]))
                : List.of(Path.of("airports.csv"), Path.of("Q3", "airports.csv"), Path.of("..", "airports.csv"));
        for (Path candidate : candidates) {
            if (Files.isRegularFile(candidate)) {
                return candidate;
            }
        }
        return null;
    }
}
