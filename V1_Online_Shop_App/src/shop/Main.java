package shop;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Shop shop = new Shop();

    public static void main(String[] args) {
        seedDaten();
        demoAufgaben();
        menuSchleife();
        scanner.close();
    }

    private static void seedDaten() {
        shop.produktHinzufuegen(new Laptop("ThinkPad X1", "Lenovo", 1899.00, "L-001", 32, 1000));
        shop.produktHinzufuegen(new Laptop("MacBook Air", "Apple", 1399.00, "L-002", 16, 512));
        shop.produktHinzufuegen(new Smartphone("Galaxy S25", "Samsung", 999.00, "S-001", 6.2, true));
        shop.produktHinzufuegen(new Smartphone("iPhone 17", "Apple", 1099.00, "S-002", 6.1, false));
        shop.produktHinzufuegen(new Monitor("UltraSharp 27", "Dell", 349.00, "M-001", 27.0, "3840x2160"));
        shop.produktHinzufuegen(new Monitor("Odyssey G7", "Samsung", 599.00, "M-002", 32.0, "2560x1440"));
        shop.produktHinzufuegen(new Drucker("LaserJet Pro", "HP", 249.00, "D-001", true, true, 30));
        shop.produktHinzufuegen(new Drucker("EcoTank ET-4850", "Epson", 329.00, "D-002", true, false, 15));

        shop.garantieHinzufuegen(new Garantieverlaengerung("Garantieverlaengerung 1 Jahr", 49.00, "G-001"));
        shop.garantieHinzufuegen(new Garantieverlaengerung("Garantieverlaengerung 2 Jahre", 79.00, "G-002"));
    }


    private static void demoAufgaben() {
        Produkt l1 = shop.sucheProdukt("L-001");

        // Aufgabe 5: geerbte Methoden am Beispiel Laptop.
        System.out.println("--- Demo: geerbte Methoden (Aufgabe 5) ---");
        System.out.println("Name: " + l1.getProduktname() + ", Preis: " + l1.getPreis()
                + ", An Lager? " + l1.isAnLager());

        // Aufgabe 7: Kunde/Bestellung/Hersteller erben bewusst nicht von Produkt.
        System.out.println("\n--- Demo: Kunde/Bestellung/Hersteller (Aufgabe 7) ---");
        Kunde k1 = new Kunde("Anna Muster", "anna.muster@example.com", "K-001");
        Hersteller h1 = new Hersteller("Lenovo", "China");
        Bestellung b1 = new Bestellung("B-1001", k1);
        b1.produktHinzufuegen(l1);
        System.out.println(k1);
        System.out.println(h1);
        System.out.println(b1);

        // Aufgabe 9: ArrayList<Laptop> akzeptiert nur Laptops.
        System.out.println("\n--- Demo: falsche Liste (Aufgabe 9) ---");
        ArrayList<Laptop> laptops = new ArrayList<>();
        laptops.add((Laptop) l1);
        System.out.println("laptops enthaelt " + laptops.size() + " Laptop(s).");

        // Aufgabe 12: Garantieverlaengerung erbt bewusst nicht von Produkt, wird ueber eine eigene Liste in Shop verwaltet (siehe Menuepunkte 6-8). So bleibt sie nutzbar, ohne die Produkt-Hierarchie zu verfaelschen.
        System.out.println("\n--- Demo: Garantieverlaengerung (Aufgabe 12) ---");
        System.out.println(shop.getGarantien().size() + " Garantieverlaengerung(en) im Shop erfasst.");
        System.out.println();
    }

    private static void menuSchleife() {
        boolean weiter = true;
        while (weiter) {
            zeigeMenu();
            int auswahl = liesInt("Auswahl: ");
            switch (auswahl) {
                case 1 -> alleProdukteAnzeigen();
                case 2 -> produktSuchen();
                case 3 -> produktVerkaufen();
                case 4 -> produktHinzufuegen();
                case 5 -> produktEntfernen();
                case 6 -> alleGarantienAnzeigen();
                case 7 -> garantieHinzufuegen();
                case 8 -> garantieEntfernen();
                case 0 -> {
                    weiter = false;
                    System.out.println("Programm beendet.");
                }
                default -> System.out.println("Ungueltige Auswahl.");
            }
        }
    }

    private static void zeigeMenu() {
        System.out.println("\n===== Online Shop =====");
        System.out.println("1) Alle Produkte anzeigen");
        System.out.println("2) Produkt suchen");
        System.out.println("3) Produkt verkaufen");
        System.out.println("4) Produkt hinzufuegen");
        System.out.println("5) Produkt entfernen");
        System.out.println("6) Alle Garantieverlaengerungen anzeigen");
        System.out.println("7) Garantieverlaengerung hinzufuegen");
        System.out.println("8) Garantieverlaengerung entfernen");
        System.out.println("0) Beenden");
    }

    private static void alleProdukteAnzeigen() {
        System.out.println("\n--- Alle Produkte im Shop ---");
        if (shop.getProdukte().isEmpty()) {
            System.out.println("Keine Produkte vorhanden.");
            return;
        }
        for (Produkt p : shop.getProdukte()) {
            System.out.println(p);
        }
    }

    private static void produktSuchen() {
        String artikelnummer = liesString("Artikelnummer: ");
        Produkt p = shop.sucheProdukt(artikelnummer);
        if (p != null) {
            System.out.println("Gefunden: " + p);
        } else {
            System.out.println("Kein Produkt mit Art.-Nr. " + artikelnummer + " gefunden.");
        }
    }

    private static void produktVerkaufen() {
        String artikelnummer = liesString("Artikelnummer: ");
        shop.produktVerkaufen(artikelnummer);
    }

    private static void produktEntfernen() {
        String artikelnummer = liesString("Artikelnummer: ");
        Produkt p = shop.sucheProdukt(artikelnummer);
        if (p == null) {
            System.out.println("Kein Produkt mit Art.-Nr. " + artikelnummer + " gefunden.");
            return;
        }
        shop.produktEntfernen(artikelnummer);
        System.out.println("Produkt entfernt: " + p);
    }

    private static void produktHinzufuegen() {
        System.out.println("\nProdukttyp waehlen:");
        System.out.println("1) Laptop");
        System.out.println("2) Smartphone");
        System.out.println("3) Monitor");
        System.out.println("4) Drucker");
        int typ = liesInt("Auswahl: ");

        String produktname = liesString("Produktname: ");
        String hersteller = liesString("Hersteller: ");
        double preis = liesDouble("Preis: ");
        String artikelnummer = liesString("Artikelnummer: ");

        Produkt produkt = switch (typ) {
            case 1 -> {
                int ram = liesInt("RAM (GB): ");
                int speicherplatz = liesInt("Speicherplatz (GB): ");
                yield new Laptop(produktname, hersteller, preis, artikelnummer, ram, speicherplatz);
            }
            case 2 -> {
                double displaygroesse = liesDouble("Displaygroesse (Zoll): ");
                boolean dualSim = liesBoolean("Dual-SIM (ja/nein): ");
                yield new Smartphone(produktname, hersteller, preis, artikelnummer, displaygroesse, dualSim);
            }
            case 3 -> {
                double bildschirmdiagonale = liesDouble("Bildschirmdiagonale (Zoll): ");
                String aufloesung = liesString("Aufloesung (z.B. 1920x1080): ");
                yield new Monitor(produktname, hersteller, preis, artikelnummer, bildschirmdiagonale, aufloesung);
            }
            case 4 -> {
                boolean farbdruck = liesBoolean("Farbdruck (ja/nein): ");
                boolean duplex = liesBoolean("Duplex (ja/nein): ");
                int seitenProMinute = liesInt("Seiten pro Minute: ");
                yield new Drucker(produktname, hersteller, preis, artikelnummer, farbdruck, duplex, seitenProMinute);
            }
            default -> null;
        };

        if (produkt == null) {
            System.out.println("Ungueltiger Produkttyp, nichts hinzugefuegt.");
            return;
        }
        shop.produktHinzufuegen(produkt);
        System.out.println("Hinzugefuegt: " + produkt);
    }

    private static void alleGarantienAnzeigen() {
        System.out.println("\n--- Alle Garantieverlaengerungen im Shop ---");
        if (shop.getGarantien().isEmpty()) {
            System.out.println("Keine Garantieverlaengerungen vorhanden.");
            return;
        }
        for (Garantieverlaengerung g : shop.getGarantien()) {
            System.out.println(g);
        }
    }

    private static void garantieHinzufuegen() {
        String name = liesString("Name: ");
        double preis = liesDouble("Preis: ");
        String artikelnummer = liesString("Artikelnummer: ");
        Garantieverlaengerung garantie = new Garantieverlaengerung(name, preis, artikelnummer);
        shop.garantieHinzufuegen(garantie);
        System.out.println("Hinzugefuegt: " + garantie);
    }

    private static void garantieEntfernen() {
        String artikelnummer = liesString("Artikelnummer: ");
        Garantieverlaengerung g = shop.sucheGarantie(artikelnummer);
        if (g == null) {
            System.out.println("Keine Garantieverlaengerung mit Art.-Nr. " + artikelnummer + " gefunden.");
            return;
        }
        shop.garantieEntfernen(artikelnummer);
        System.out.println("Entfernt: " + g);
    }

    private static String liesString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int liesInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String eingabe = scanner.nextLine().trim();
            try {
                return Integer.parseInt(eingabe);
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine ganze Zahl eingeben.");
            }
        }
    }

    private static double liesDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String eingabe = scanner.nextLine().trim();
            try {
                return Double.parseDouble(eingabe);
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine Zahl eingeben (z.B. 199.90).");
            }
        }
    }

    private static boolean liesBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            String eingabe = scanner.nextLine().trim().toLowerCase();
            if (eingabe.equals("ja") || eingabe.equals("j")) {
                return true;
            } else if (eingabe.equals("nein") || eingabe.equals("n")) {
                return false;
            }
            System.out.println("Bitte 'ja' oder 'nein' eingeben.");
        }
    }
}
