package shop;

public class Monitor extends Produkt {

    private double bildschirmdiagonale; // in Zoll
    private String aufloesung;          // z.B. "1920x1080"

    public Monitor(String produktname, String hersteller, double preis,
                   String artikelnummer, double bildschirmdiagonale, String aufloesung) {
        super(produktname, hersteller, preis, artikelnummer);
        this.bildschirmdiagonale = bildschirmdiagonale;
        this.aufloesung = aufloesung;
    }

    public double getBildschirmdiagonale() {
        return bildschirmdiagonale;
    }

    public String getAufloesung() {
        return aufloesung;
    }

    @Override
    public String toString() {
        return super.toString() + " | Diagonale=" + bildschirmdiagonale + "\", Aufloesung=" + aufloesung;
    }
}