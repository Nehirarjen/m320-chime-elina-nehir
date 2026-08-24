import java.util.ArrayList;
import java.util.List;

// Alle Attribute sind private
// Der Zugriff erfolgt nur über öffentliche Methoden (Getter)
class Fahrzeug {
    private final String kennzeichen;
    private final String marke;
    private boolean repariert;
    private double reparaturkosten;

    public Fahrzeug(String kennzeichen, String marke) {
        this.kennzeichen = kennzeichen;
        this.marke = marke;
        this.repariert = false;
        this.reparaturkosten = 0.0;
    }

    // Getter erlauben kontrollierten Lese-Zugriff von aussen
    public String getKennzeichen() { return kennzeichen; }
    public  String getMarke() { return marke; }
    public boolean istRepariert() { return repariert; }
    public double getReparaturkosten() { return reparaturkosten; }

    public void reparaturAbschliessen(double kosten) {
        if (kosten < 0) {
            throw new IllegalArgumentException("Kosten dürfen nicht negativ sein");
        }
        this.reparaturkosten += kosten;
        this.repariert = true;
    }

    @Override
    public String toString() {
        return kennzeichen + " (" + marke + ") - "
                + (repariert ? "repariert, Kosten: CHF " + String.format("%.2f", reparaturkosten)
                : "in Bearbeitung");
    }
}
class Garage {
    private final String name;
    private final List<Fahrzeug> fahrzeuge = new ArrayList<>();

    public Garage(String name) {
        this.name = name;
    }

    public void fahrzeugRegistrieren(Fahrzeug f) {
        fahrzeuge.add(f);
        System.out.println(name + ": Fahrzeug " + f.getKennzeichen() + " wurde registriert.");
    }
    public void fahrzeugReparieren(String kennzeichen, double kosten) {
        Fahrzeug f = fahrzeugSuchen(kennzeichen);
        if (f == null) {
            System.out.println("Fahrzeug nicht gefunden: " + kennzeichen);
            return;
        }
        f.reparaturAbschliessen(kosten);
        System.out.println(name + ": " + kennzeichen + " wurde repariert.");
    }

    private Fahrzeug fahrzeugSuchen(String kennzeichen) {
        for (Fahrzeug f : fahrzeuge) {
            if (f.getKennzeichen().equals(kennzeichen)) {
                return f;
            }
        }
        return null;
    }

    public void reparierteFahrzeugeAnzeigen() {
        System.out.println("--- Reparierte Fahrzeuge in " + name + " ---");
        for (Fahrzeug f : fahrzeuge) {
            if (f.istRepariert()) {
                System.out.println(f);
            }
        }
    }

    public double gesamtkostenBerechnen() {
        double summe = 0;
        for (Fahrzeug f : fahrzeuge) {
            summe += f.getReparaturkosten();
        }
        return summe;
    }
}
public class GarageSimulation {
    public static void main(String[] args) {
        Garage meineGarage = new Garage("AutoFix Zürich");

        Fahrzeug auto1 = new Fahrzeug("ZH 123456", "VW Golf");
        Fahrzeug auto2 = new Fahrzeug("ZH 654321", "Audi A4");
        Fahrzeug auto3 = new Fahrzeug("ZH 111222", "Skoda Octavia");

        meineGarage.fahrzeugRegistrieren(auto1);
        meineGarage.fahrzeugRegistrieren(auto2);
        meineGarage.fahrzeugRegistrieren(auto3);

        System.out.println();
        meineGarage.fahrzeugReparieren("ZH 123456", 450.50);
        meineGarage.fahrzeugReparieren("ZH 654321", 1200.00);

        System.out.println();
        meineGarage.reparierteFahrzeugeAnzeigen();

        System.out.println();
        System.out.println("Gesamtkosten aller Reparaturen: CHF "
                + String.format("%.2f", meineGarage.gesamtkostenBerechnen()));

        // auto1.repariert = true;      // Fehler: repariert hat private Sichtbarkeit
        // auto1.reparaturkosten = 999; // Fehler: reparaturkosten hat private Sichtbarkeit
    }
}