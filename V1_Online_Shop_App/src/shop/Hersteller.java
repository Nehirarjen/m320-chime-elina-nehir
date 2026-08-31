package shop;

// Aufgabe 7: Hersteller ist KEIN Produkt (keine IST-EIN-Beziehung).
// Ein Hersteller stellt Produkte her, er ist selbst keines. Deshalb erbt
// Hersteller NICHT von Produkt, sondern ist eine eigenstaendige Klasse.
public class Hersteller {

    private String name;
    private String land;

    public Hersteller(String name, String land) {
        this.name = name;
        this.land = land;
    }

    public String getName() {
        return name;
    }

    public String getLand() {
        return land;
    }

    @Override
    public String toString() {
        return "Hersteller [" + name + ", " + land + "]";
    }
}
