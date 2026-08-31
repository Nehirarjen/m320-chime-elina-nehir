package shop;

import java.util.ArrayList;

// Aufgabe 7: Bestellung ist KEIN Produkt (keine IST-EIN-Beziehung).
// Eine Bestellung ENTHAELT Produkte (HAT-EIN-Beziehung / Aggregation),
// ist aber selbst kein Produkt. Deshalb erbt Bestellung NICHT von
// Produkt, sondern haelt eine Liste von Produkt-Objekten als Attribut.
public class Bestellung {

    private String bestellnummer;
    private Kunde kunde;
    private ArrayList<Produkt> produkte = new ArrayList<>();

    public Bestellung(String bestellnummer, Kunde kunde) {
        this.bestellnummer = bestellnummer;
        this.kunde = kunde;
    }

    public void produktHinzufuegen(Produkt produkt) {
        produkte.add(produkt);
    }

    public double getGesamtpreis() {
        double summe = 0;
        for (Produkt p : produkte) {
            summe += p.getPreis();
        }
        return summe;
    }

    public String getBestellnummer() {
        return bestellnummer;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public ArrayList<Produkt> getProdukte() {
        return produkte;
    }

    @Override
    public String toString() {
        return "Bestellung [" + bestellnummer + ", Kunde=" + kunde.getName()
                + ", " + produkte.size() + " Produkt(e), Gesamtpreis=CHF " + getGesamtpreis() + "]";
    }
}
