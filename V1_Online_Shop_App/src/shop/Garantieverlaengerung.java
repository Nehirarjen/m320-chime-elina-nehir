package shop;

// Aufgabe 12: erbt bewusst nicht von Produkt, weil anLager/verkaufen()/
public class Garantieverlaengerung {

    private String name;
    private double preis;
    private String artikelnummer;

    public Garantieverlaengerung(String name, double preis, String artikelnummer) {
        this.name = name;
        this.preis = preis;
        this.artikelnummer = artikelnummer;
    }

    public String getName() {
        return name;
    }

    public double getPreis() {
        return preis;
    }

    public String getArtikelnummer() {
        return artikelnummer;
    }

    @Override
    public String toString() {
        return "Garantieverlaengerung [" + name + ", CHF " + preis + ", Art.-Nr. " + artikelnummer + "]";
    }
}
