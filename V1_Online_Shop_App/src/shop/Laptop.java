package shop;

public class Laptop extends Produkt {

    private int ram;           // in GB
    private int speicherplatz; // in GB

    public Laptop(String produktname, String hersteller, double preis,
                  String artikelnummer, int ram, int speicherplatz) {
        super(produktname, hersteller, preis, artikelnummer);
        this.ram = ram;
        this.speicherplatz = speicherplatz;
    }

    public int getRam() {
        return ram;
    }

    public int getSpeicherplatz() {
        return speicherplatz;
    }

    @Override
    public String toString() {
        return super.toString() + " | RAM=" + ram + "GB, Speicher=" + speicherplatz + "GB";
    }
}