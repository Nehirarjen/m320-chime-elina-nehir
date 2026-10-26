import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/** Zeigt das Konsolenmenü und liest die Eingaben des Benutzers. */
public class UserInterface {

    private static class InputEnded extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    private final AirportRepository repository;
    private final AirportAlgorithms algorithms;
    private final AirportStatistics statistics;
    private final SearchHistory history = new SearchHistory();
    private final Scanner input = new Scanner(System.in);

    public UserInterface(AirportRepository repository) {
        this.repository = repository;
        this.algorithms = new AirportAlgorithms(repository);
        this.statistics = new AirportStatistics(repository);
    }

    public void run() {
        try {
            boolean running = true;
            while (running) {
                printMenu();
                switch (readNumber("Choice: ")) {
                    case 1 -> showAllAirports();
                    case 2 -> searchByCode();
                    case 3 -> addAirport();
                    case 4 -> updateAirport();
                    case 5 -> removeAirport();
                    case 6 -> searchAndFilter();
                    case 7 -> showSorted();
                    case 8 -> showStatistics();
                    case 9 -> showSearchHistory();
                    case 0 -> running = false;
                    default -> System.out.println("Please enter a number from 0 to 9.");
                }
            }
        } catch (InputEnded e) {
            System.out.println();
        }
        System.out.println("Goodbye.");
    }

    private void printMenu() {
        System.out.println("\n=== Airport Management ===");
        System.out.println("1. Show all airports");
        System.out.println("2. Search airport by code");
        System.out.println("3. Add airport");
        System.out.println("4. Update airport");
        System.out.println("5. Remove airport");
        System.out.println("6. Search and filter airports");
        System.out.println("7. Show airports sorted");
        System.out.println("8. Show statistics");
        System.out.println("9. Show search history");
        System.out.println("0. Exit");
    }

    private void showAllAirports() {
        List<Airport> all = repository.getAll();
        if (all.isEmpty()) {
            System.out.println("There are no airports stored.");
            return;
        }
        System.out.println("\n--- All airports (HashMap order, not sorted) ---");
        printList(all);
    }

    private void searchByCode() {
        String code = Airport.normalizeCode(ask("Enter airport code: "));
        if (code.isEmpty()) {
            System.out.println("The code must not be empty.");
            return;
        }
        Optional<Airport> airport = repository.findByCode(code);
        history.record(code, airport.isPresent());
        if (airport.isPresent()) {
            System.out.println("Found: " + airport.get());
        } else {
            System.out.println("No airport with code " + code + " was found.");
        }
    }

    private void addAirport() {
        String code = ask("Code (3 letters): ");
        String name = ask("Name: ");
        String city = ask("City (optional): ");
        String state = ask("State (optional): ");
        try {
            Airport airport = new Airport(code, name, city, state);
            if (repository.addAirport(airport)) {
                System.out.println("Added: " + airport);
            } else {
                System.out.println("An airport with code " + airport.code()
                        + " already exists. Use 'Update airport' to change it.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Not added: " + e.getMessage());
        }
    }

    private void updateAirport() {
        String code = Airport.normalizeCode(ask("Code of the airport to update: "));
        Optional<Airport> current = repository.findByCode(code);
        if (current.isEmpty()) {
            System.out.println("No airport with code " + code + " was found.");
            return;
        }
        Airport old = current.get();
        System.out.println("Current: " + old);
        System.out.println("Press Enter to keep the current value.");
        String name = askOrKeep("New name", old.name());
        String city = askOrKeep("New city", old.city());
        String state = askOrKeep("New state", old.state());
        try {
            repository.updateAirport(code, name, city, state);
            System.out.println("Updated: " + repository.findByCode(code).orElseThrow());
        } catch (IllegalArgumentException e) {
            System.out.println("Not updated: " + e.getMessage());
        }
    }

    private void removeAirport() {
        String code = Airport.normalizeCode(ask("Code of the airport to remove: "));
        if (code.isEmpty()) {
            System.out.println("The code must not be empty.");
        } else if (repository.removeAirport(code)) {
            System.out.println("Airport " + code + " was removed.");
        } else {
            System.out.println("No airport with code " + code + " was found.");
        }
    }

    private void searchAndFilter() {
        System.out.println("\n1. By city");
        System.out.println("2. By state");
        System.out.println("3. By name (contains, ignores case)");
        int choice = readNumber("Choice: ");
        List<Airport> result;
        switch (choice) {
            case 1 -> {
                result = algorithms.findByCity(ask("City: "));
                if (result.isEmpty() && statistics.mostFrequentCity().isEmpty()) {
                    System.out.println("Note: the loaded data contains no city information.");
                }
            }
            case 2 -> {
                result = algorithms.findByState(ask("State: "));
                if (result.isEmpty() && statistics.mostFrequentState().isEmpty()) {
                    System.out.println("Note: the loaded data contains no state information.");
                }
            }
            case 3 -> result = algorithms.findByName(ask("Search term: "));
            default -> {
                System.out.println("Please enter 1, 2 or 3.");
                return;
            }
        }
        if (result.isEmpty()) {
            System.out.println("No airports found.");
        } else {
            System.out.println(result.size() + " airport(s) found:");
            printList(result);
        }
    }

    private void showSorted() {
        System.out.println("\n1. By name");
        System.out.println("2. By city, then by name");
        switch (readNumber("Choice: ")) {
            case 1 -> printList(algorithms.sortedByName());
            case 2 -> printList(algorithms.sortedByCityThenName());
            default -> System.out.println("Please enter 1 or 2.");
        }
    }

    private void showStatistics() {
        System.out.println("\n--- Statistics ---");
        System.out.println("Total airports: " + statistics.totalAirports());

        Optional<AirportStatistics.Count> city = statistics.mostFrequentCity();
        System.out.println("City with most airports: " + (city.isPresent()
                ? city.get().value() + " (" + city.get().count() + ")"
                : "not available (no city information in the data)"));

        Optional<AirportStatistics.Count> state = statistics.mostFrequentState();
        System.out.println("State with most airports: " + (state.isPresent()
                ? state.get().value() + " (" + state.get().count() + ")"
                : "not available (no state information in the data)"));

        Map<String, List<String>> duplicates = statistics.duplicateNames();
        if (duplicates.isEmpty()) {
            System.out.println("Airport names that occur more than once: none");
        } else {
            System.out.println("Airport names that occur more than once:");
            duplicates.forEach((name, codes) -> System.out.println("  " + name + " -> " + codes));
        }

        StringBuilder letters = new StringBuilder();
        statistics.countByFirstLetter().forEach((letter, count) ->
                letters.append(letter).append('=').append(count).append(' '));
        System.out.println("Airports per first letter of the code: " + letters.toString().trim());
    }

    private void showSearchHistory() {
        List<SearchHistory.Entry> entries = history.latestFirst();
        if (entries.isEmpty()) {
            System.out.println("There are no searches yet.");
            return;
        }
        System.out.println("\n--- Last searches (latest first) ---");
        int number = 1;
        for (SearchHistory.Entry entry : entries) {
            System.out.println(number++ + ". " + entry.code() + " - " + (entry.found() ? "found" : "not found"));
        }
    }

    private void printList(List<Airport> airports) {
        for (Airport airport : airports) {
            System.out.println(airport);
        }
    }

    private String ask(String prompt) {
        System.out.print(prompt);
        if (!input.hasNextLine()) {
            throw new InputEnded();
        }
        return input.nextLine().replace("﻿", "").trim();
    }

    private String askOrKeep(String label, String currentValue) {
        String answer = ask(label + " [" + currentValue + "]: ");
        return answer.isEmpty() ? currentValue : answer;
    }

    private int readNumber(String prompt) {
        try {
            return Integer.parseInt(ask(prompt));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
