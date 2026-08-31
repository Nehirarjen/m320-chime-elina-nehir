package shop;

// Aufgabe 12: Garantieverlaengerung erbt bewusst nicht von Produkt, wird ueber
// eine eigene Liste in Shop verwaltet (siehe Menuepunkte 6-8). So bleibt sie
// nutzbar, ohne die Produkt-Hierarchie zu verfaelschen.

public abstract class Produkt {

    private String produktname;
    private String hersteller;
    private double preis;
    private String artikelnummer;
    private boolean anLager;

    public Produkt(String produktname, String hersteller, double preis, String artikelnummer) {
        this.produktname = produktname;
        this.hersteller = hersteller;
        this.preis = preis;
        this.artikelnummer = artikelnummer;
        this.anLager = true;
    }

    public String getProduktname() {
        return produktname;
    }

    public String getHersteller() {
        return hersteller;
    }

    public double getPreis() {
        return preis;
    }

    public String getArtikelnummer() {
        return artikelnummer;
    }

    public boolean isAnLager() {
        return anLager;
    }

    public void verkaufen() {
        if (anLager) {
            anLager = false;
            System.out.println(produktname + " (Art.-Nr. " + artikelnummer + ") wurde verkauft.");
        } else {
            System.out.println(produktname + " ist nicht an Lager und kann nicht verkauft werden.");
        }
    }

    public void einlagern() {
        anLager = true;
        System.out.println(produktname + " wurde eingelagert.");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + produktname + ", " + hersteller
                + ", CHF " + preis + ", Art.-Nr. " + artikelnummer
                + ", anLager=" + anLager + "]";
    }
}