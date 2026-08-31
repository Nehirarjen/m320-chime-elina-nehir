package shop;

public class Smartphone extends Produkt {

    private double displaygroesse; // in Zoll
    private boolean dualSim;

    public Smartphone(String produktname, String hersteller, double preis,
                      String artikelnummer, double displaygroesse, boolean dualSim) {
        super(produktname, hersteller, preis, artikelnummer);
        this.displaygroesse = displaygroesse;
        this.dualSim = dualSim;
    }

    public double getDisplaygroesse() {
        return displaygroesse;
    }

    public boolean isDualSim() {
        return dualSim;
    }

    @Override
    public String toString() {
        return super.toString() + " | Display=" + displaygroesse + "\", DualSIM=" + dualSim;
    }
}