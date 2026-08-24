import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Alle Attribute sind private -> von aussen nicht direkt veränderbar.
// Der Kontostand kann nur über kontrollierte Methoden verändert werden.
class Konto {
    private final String kontonummer;
    private final String inhaber;
    private double kontostand;

    public Konto(String kontonummer, String inhaber, double startguthaben) {
        this.kontonummer = kontonummer;
        this.inhaber = inhaber;
        this.kontostand = startguthaben;
    }

    // Getter erlauben kontrollierten Lese-Zugriff von aussen
    public String getKontonummer() { return kontonummer; }
    public String getInhaber() { return inhaber; }
    public double getKontostand() { return kontostand; }

    // Es gibt bewusst keinen direkten Setter für kontostand - nur diese
    // validierte Methode darf den Wert verändern.
    public void einzahlen(double betrag) {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Einzahlungsbetrag muss positiv sein");
        }
        this.kontostand += betrag;
    }

    // Zustandsänderung: verringert den Kontostand, aber nur wenn genug
    // Guthaben vorhanden ist. Gibt zurück, ob die Abhebung erfolgreich war.
    public boolean abheben(double betrag) {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Abhebungsbetrag muss positiv sein");
        }
        if (betrag > kontostand) {
            System.out.println("Abhebung fehlgeschlagen: nicht genug Guthaben auf " + kontonummer);
            return false;
        }
        this.kontostand -= betrag;
        return true;
    }

    @Override
    public String toString() {
        return kontonummer + " (" + inhaber + "): CHF " + String.format("%.2f", kontostand);
    }
}

// verwaltet mehrere Konto-Objekte und ermöglicht Kommunikation
// zwischen zwei Konto-Objekten (Transfer).
class Bank {
    private final String name;
    private final List<Konto> konten = new ArrayList<>();

    public Bank(String name) {
        this.name = name;
    }

    public void kontoEroeffnen(Konto k) {
        konten.add(k);
        System.out.println(name + ": Konto eröffnet -> " + k);
    }

    private Konto kontoSuchen(String nummer) {
        for (Konto k : konten) {
            if (k.getKontonummer().equals(nummer)) {
                return k;
            }
        }
        return null;
    }
    // Kommunikation Bank -> Konto: Bank sucht das Konto und ruft dessen Methode auf
    public void einzahlenAuf(String nummer, double betrag) {
        Konto k = kontoSuchen(nummer);
        if (k == null) {
            System.out.println("Konto nicht gefunden: " + nummer);
            return;
        }
        k.einzahlen(betrag);
        System.out.println("Eingezahlt auf " + nummer + ": CHF " + String.format("%.2f", betrag));
    }

    public void abhebenVon(String nummer, double betrag) {
        Konto k = kontoSuchen(nummer);
        if (k == null) {
            System.out.println("Konto nicht gefunden: " + nummer);
            return;
        }
        if (k.abheben(betrag)) {
            System.out.println("Abgehoben von " + nummer + ": CHF " + String.format("%.2f", betrag));
        }
    }

    // Methode Änderung
    public void transferieren(String vonNummer, String zuNummer, double betrag) {
        Konto von = kontoSuchen(vonNummer);
        Konto zu = kontoSuchen(zuNummer);

        if (von == null || zu == null) {
            System.out.println("Transfer fehlgeschlagen: Konto nicht gefunden.");
            return;
        }

        if (von.abheben(betrag)) {   // Methodenaufruf auf Objekt "von", Wert wird übergeben
            zu.einzahlen(betrag);    // Methodenaufruf auf Objekt "zu", derselbe Wert wird übergeben
            System.out.println(name + ": Transfer von " + vonNummer + " zu " + zuNummer
                    + " über CHF " + String.format("%.2f", betrag) + " erfolgreich.");
        }
    }

    public void kontostandAnzeigen(String nummer) {
        Konto k = kontoSuchen(nummer);
        if (k != null) {
            System.out.println(k);
        } else {
            System.out.println("Konto nicht gefunden: " + nummer);
        }
    }

    public void alleKontenAnzeigen() {
        System.out.println("--- Konten bei " + name + " ---");
        for (Konto k : konten) {
            System.out.println(k);
        }
    }
}
public class BankSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank("Kantonalbank Zürich");

        bank.kontoEroeffnen(new Konto("CH01", "Chime Sengel", 1000.0));
        bank.kontoEroeffnen(new Konto("CH02", "Elina Single", 200.0));

        boolean laufen = true;
        while (laufen) {
            menuAnzeigen();
            String eingabe = scanner.nextLine().trim();

            switch (eingabe) {
                case "1" -> {
                    System.out.print("Kontonummer: ");
                    String nummer = scanner.nextLine().trim();
                    System.out.print("Inhaber: ");
                    String inhaber = scanner.nextLine().trim();
                    double start = betragEinlesen(scanner, "Startguthaben: ");
                    bank.kontoEroeffnen(new Konto(nummer, inhaber, start));
                }
                case "2" -> {
                    System.out.print("Kontonummer: ");
                    String nummer = scanner.nextLine().trim();
                    double betrag = betragEinlesen(scanner, "Einzahlungsbetrag: ");
                    bank.einzahlenAuf(nummer, betrag);
                }
                case "3" -> {
                    System.out.print("Kontonummer: ");
                    String nummer = scanner.nextLine().trim();
                    double betrag = betragEinlesen(scanner, "Abhebungsbetrag: ");
                    bank.abhebenVon(nummer, betrag);
                }
                case "4" -> {
                    System.out.print("Von Kontonummer: ");
                    String von = scanner.nextLine().trim();
                    System.out.print("Zu Kontonummer: ");
                    String zu = scanner.nextLine().trim();
                    double betrag = betragEinlesen(scanner, "Betrag: ");
                    bank.transferieren(von, zu, betrag);
                }
                case "5" -> {
                    System.out.print("Kontonummer: ");
                    String nummer = scanner.nextLine().trim();
                    bank.kontostandAnzeigen(nummer);
                }
                case "6" -> bank.alleKontenAnzeigen();
                case "0" -> {
                    laufen = false;
                    System.out.println("Programm beendet.");
                }
                default -> System.out.println("Ungültige Eingabe, bitte erneut versuchen.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void menuAnzeigen() {
        System.out.println("=== Bank-Simulation ===");
        System.out.println("1 - Konto eröffnen");
        System.out.println("2 - Einzahlen");
        System.out.println("3 - Abheben");
        System.out.println("4 - Transferieren");
        System.out.println("5 - Kontostand anzeigen");
        System.out.println("6 - Alle Konten anzeigen");
        System.out.println("0 - Beenden");
        System.out.print("Auswahl: ");
    }

    // liest wiederholt nach, bis eine gültige Zahl eingegeben wird
    private static double betragEinlesen(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String eingabe = scanner.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(eingabe);
            } catch (NumberFormatException e) {
                System.out.println("Ungültiger Betrag, bitte eine Zahl eingeben.");
            }
        }
    }
}