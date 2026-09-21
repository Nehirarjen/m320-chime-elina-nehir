import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

/** Einstiegspunkt mit dem im Aufgabenblatt verlangten Konsolenmenü. */
public class ServiceApp {
    private static final Scanner EINGABE = new Scanner(System.in);
    private static final DateTimeFormatter ZEIT_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final ServiceVerwaltung VERWALTUNG = new ServiceVerwaltung();

    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("de-CH"));
        boolean laeuft = true;
        while (laeuft) {
            menueAnzeigen();
            switch (liesGanzzahl("Auswahl: ")) {
                case 1 -> serviceHinzufuegen();
                case 2 -> VERWALTUNG.printAlle();
                case 3 -> serviceSuchen();
                case 4 -> serviceBuchen();
                case 5 -> serviceStornieren();
                case 6 -> serviceLoeschen();
                case 7 -> System.out.printf("Aktueller Umsatz: CHF %.2f%n", VERWALTUNG.gesamtUmsatz());
                case 8 -> {
                    laeuft = false;
                    System.out.println("Auf Wiedersehen.");
                }
                default -> System.out.println("Bitte eine Zahl von 1 bis 8 eingeben.");
            }
        }
    }

    private static void menueAnzeigen() {
        System.out.println("\n=== Service-Verwaltung ===");
        System.out.println("1. Service hinzufügen");
        System.out.println("2. Alle Services anzeigen");
        System.out.println("3. Service suchen");
        System.out.println("4. Service buchen");
        System.out.println("5. Service stornieren");
        System.out.println("6. Service löschen");
        System.out.println("7. Umsatz anzeigen");
        System.out.println("8. Beenden");
    }

    private static void serviceHinzufuegen() {
        System.out.println("\nTyp: 1 = Beratung, 2 = Schulung, 3 = Wartung");
        int typ = liesGanzzahl("Typ: ");
        String id = liesText("ID: ");
        String name = liesText("Name: ");
        double basisPreis = liesPositiveZahlOderNull("Basispreis: ");

        try {
            Service service = switch (typ) {
                case 1 -> neueBeratung(id, name, basisPreis);
                case 2 -> new SchulungsService(id, name, basisPreis,
                        liesPositiveGanzzahl("Teilnehmerzahl: "), liesJaNein("Material inklusive (j/n): "));
                case 3 -> new WartungsService(id, name, basisPreis,
                        liesPositiveGanzzahl("Anzahl Geräte: "), liesJaNein("Express (j/n): "), liesStartZeit());
                default -> null;
            };
            if (service == null) {
                System.out.println("Unbekannter Service-Typ.");
                return;
            }
            VERWALTUNG.add(service);
            System.out.println("Service wurde hinzugefügt.");
        } catch (IllegalArgumentException e) {
            System.out.println("Service konnte nicht erstellt werden: " + e.getMessage());
        }
    }

    private static BeratungsService neueBeratung(String id, String name, double basisPreis) {
        double stunden = liesPositiveZahl("Stunden: ");
        System.out.println("Level: 1 = JUNIOR, 2 = PROFESSIONAL, 3 = EXPERT");
        BeratungsService.Level level = switch (liesGanzzahl("Level: ")) {
            case 1 -> BeratungsService.Level.JUNIOR;
            case 2 -> BeratungsService.Level.PROFESSIONAL;
            case 3 -> BeratungsService.Level.EXPERT;
            default -> throw new IllegalArgumentException("Ungültiges Level.");
        };
        return new BeratungsService(id, name, basisPreis, stunden, level);
    }

    private static void serviceSuchen() {
        Optional<Service> service = VERWALTUNG.findById(liesText("ID suchen: "));
        if (service.isPresent()) {
            System.out.println(service.get().printOut());
        } else {
            System.out.println("Kein Service mit dieser ID gefunden.");
        }
    }

    private static void serviceBuchen() {
        Optional<Service> service = VERWALTUNG.findById(liesText("ID buchen: "));
        if (service.isEmpty()) {
            System.out.println("Kein Service mit dieser ID gefunden.");
            return;
        }
        try {
            service.get().buchen();
            System.out.println("Service wurde gebucht.");
        } catch (ServiceException e) {
            System.out.println("Buchung nicht möglich: " + e.getMessage());
        }
    }

    private static void serviceStornieren() {
        Optional<Service> service = VERWALTUNG.findById(liesText("ID stornieren: "));
        if (service.isEmpty()) {
            System.out.println("Kein Service mit dieser ID gefunden.");
            return;
        }
        try {
            service.get().stornieren();
            System.out.println("Service wurde storniert.");
        } catch (ServiceException e) {
            System.out.println("Stornierung nicht möglich: " + e.getMessage());
        }
    }

    private static void serviceLoeschen() {
        boolean geloescht = VERWALTUNG.removeById(liesText("ID löschen: "));
        System.out.println(geloescht ? "Service wurde gelöscht." : "Kein Service mit dieser ID gefunden.");
    }

    private static String liesText(String aufforderung) {
        while (true) {
            System.out.print(aufforderung);
            String text = EINGABE.nextLine().trim();
            if (!text.isEmpty()) {
                return text;
            }
            System.out.println("Die Eingabe darf nicht leer sein.");
        }
    }

    private static int liesGanzzahl(String aufforderung) {
        while (true) {
            try {
                return Integer.parseInt(liesText(aufforderung));
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine ganze Zahl eingeben.");
            }
        }
    }

    private static int liesPositiveGanzzahl(String aufforderung) {
        while (true) {
            int zahl = liesGanzzahl(aufforderung);
            if (zahl > 0) return zahl;
            System.out.println("Die Zahl muss grösser als 0 sein.");
        }
    }

    private static double liesPositiveZahlOderNull(String aufforderung) {
        while (true) {
            try {
                double zahl = Double.parseDouble(liesText(aufforderung).replace(',', '.'));
                if (zahl >= 0) return zahl;
                System.out.println("Die Zahl darf nicht negativ sein.");
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine gültige Zahl eingeben.");
            }
        }
    }

    private static double liesPositiveZahl(String aufforderung) {
        while (true) {
            double zahl = liesPositiveZahlOderNull(aufforderung);
            if (zahl > 0) return zahl;
            System.out.println("Die Zahl muss grösser als 0 sein.");
        }
    }

    private static boolean liesJaNein(String aufforderung) {
        while (true) {
            String antwort = liesText(aufforderung).toLowerCase();
            if (antwort.equals("j") || antwort.equals("ja")) return true;
            if (antwort.equals("n") || antwort.equals("nein")) return false;
            System.out.println("Bitte j oder n eingeben.");
        }
    }

    private static LocalDateTime liesStartZeit() {
        while (true) {
            try {
                return LocalDateTime.parse(liesText("Startzeit (TT.MM.JJJJ HH:MM): "), ZEIT_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Ungültiges Format, z. B. 05.10.2026 09:30.");
            }
        }
    }
}
