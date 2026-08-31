package shop;

public class Drucker extends Produkt {

    private boolean farbdruck;
    private boolean duplex;
    private int seitenProMinute;

    public Drucker(String produktname, String hersteller, double preis,
                   String artikelnummer, boolean farbdruck, boolean duplex, int seitenProMinute) {
        super(produktname, hersteller, preis, artikelnummer);
        this.farbdruck = farbdruck;
        this.duplex = duplex;
        this.seitenProMinute = seitenProMinute;
    }

    public boolean isFarbdruck() {
        return farbdruck;
    }

    public boolean isDuplex() {
        return duplex;
    }

    public int getSeitenProMinute() {
        return seitenProMinute;
    }

    @Override
    public String toString() {
        return super.toString() + " | Farbdruck=" + farbdruck + ", Duplex=" + duplex
                + ", Seiten/min=" + seitenProMinute;
    }
}
