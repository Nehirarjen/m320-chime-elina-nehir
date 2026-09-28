import java.util.Scanner;
import java.util.Random;


public class RateSpiel {

    // Konstanten ändern sich während der Laufzeit nie -> daher final
    static final int MIN_ZAHL = 1;
    static final int MAX_ZAHL = 100;
    static final int MAX_VERSUCHE = 7;

    // main-Methode: Einstiegspunkt des Programms.
     // Sie ruft die weiteren Methoden auf und steuert den Ablauf.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Willkommen zum Rate-Spiel ===");
        System.out.println("Ich habe mir eine Zahl zwischen " + MIN_ZAHL
                + " und " + MAX_ZAHL + " überlegt.");
        System.out.println("Du hast " + MAX_VERSUCHE + " Versuche, um sie zu erraten.\n");

        // VARIABLE: kann sich während des Spiels ändern
        boolean nochEinmalSpielen = true;

        // wird ausgeführt solange die Bedingung zutrifft
        while (nochEinmalSpielen) {
            spielRunde(scanner);

            System.out.print("Möchtest du nochmal spielen? (j/n): ");
            String antwort = scanner.nextLine();

            // if-Bedingung / Entscheidung
            if (antwort.equalsIgnoreCase("j")) {
                nochEinmalSpielen = true;
            } else {
                nochEinmalSpielen = false;
            }
        }

        System.out.println("\nDanke fürs Spielen. Auf Wiedersehen!");
        scanner.close();
    }

     // Führt eine komplette Spielrunde durch.
    static void spielRunde(Scanner scanner) {
        int gesuchteZahl = zufallszahlGenerieren(MIN_ZAHL, MAX_ZAHL); // int -> Basis-Datentyp
        int versuchNummer = 0;
        boolean spielGewonnen = false; // boolean -> zweiter Basis-Datentyp

        // Fussgesteuerte Schleife (do-while): wird mindestens einmal ausgeführt
        do {
            versuchNummer++;
            System.out.print("Versuch " + versuchNummer + "/" + MAX_VERSUCHE + " – deine Zahl: ");
            int eingabe = Integer.parseInt(scanner.nextLine());

            // die Vergleichslogik ist in eine eigene Methode ausgelagert
            int ergebnis = vergleicheZahl(eingabe, gesuchteZahl);

            // Entscheidung
            if (ergebnis == 0) {
                spielGewonnen = true;
                System.out.println("Richtig! Die Zahl war " + gesuchteZahl + ".");
            } else if (ergebnis < 0) {
                System.out.println("Zu niedrig!");
            } else {
                System.out.println("Zu hoch!");
            }

        } while (!spielGewonnen && versuchNummer < MAX_VERSUCHE);

        // Abschluss der Runde, falls nicht erraten
        if (!spielGewonnen) {
            System.out.println("Leider verloren! Die gesuchte Zahl war " + gesuchteZahl + ".");
        }
    }

    // Erzeugt eine Zufallszahl im angegebenen Bereich (inklusive min und max).
    static int zufallszahlGenerieren(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    // Vergleicht die Eingabe des Spielers mit der gesuchten Zahl.
    static int vergleicheZahl(int eingabe, int gesuchteZahl) {
        if (eingabe == gesuchteZahl) {
            return 0;
        } else if (eingabe < gesuchteZahl) {
            return -1;
        } else {
            return 1;
        }
    }
}