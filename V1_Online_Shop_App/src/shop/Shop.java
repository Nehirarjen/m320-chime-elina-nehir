package shop;

import java.util.ArrayList;

public class Shop {

    private ArrayList<Produkt> produkte = new ArrayList<>();

    private ArrayList<Garantieverlaengerung> garantien = new ArrayList<>();

    public Produkt sucheProdukt(String artikelnummer) {
        for (Produkt p : produkte) {
            if (p.getArtikelnummer().equals(artikelnummer)) {
                return p;
            }
        }
        return null;
    }

    public void produktVerkaufen(String artikelnummer) {
        Produkt p = sucheProdukt(artikelnummer);
        if (p != null) {
            p.verkaufen(); // ruft die in Produkt geerbte Methode auf
        } else {
            System.out.println("Kein Produkt mit Art.-Nr. " + artikelnummer + " gefunden.");
        }
    }

    public void produktHinzufuegen(Produkt produkt) {
        produkte.add(produkt);
    }

    public void produktEntfernen(String artikelnummer) {
        Produkt p = sucheProdukt(artikelnummer);
        if (p != null) {
            produkte.remove(p);
        }
    }

    public ArrayList<Produkt> getProdukte() {
        return produkte;
    }

    public void garantieHinzufuegen(Garantieverlaengerung garantie) {
        garantien.add(garantie);
    }

    public Garantieverlaengerung sucheGarantie(String artikelnummer) {
        for (Garantieverlaengerung g : garantien) {
            if (g.getArtikelnummer().equals(artikelnummer)) {
                return g;
            }
        }
        return null;
    }

    public void garantieEntfernen(String artikelnummer) {
        Garantieverlaengerung g = sucheGarantie(artikelnummer);
        if (g != null) {
            garantien.remove(g);
        }
    }

    public ArrayList<Garantieverlaengerung> getGarantien() {
        return garantien;
    }
}