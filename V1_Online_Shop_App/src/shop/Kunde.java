package shop;

// Aufgabe 7: Kunde ist KEIN Produkt (keine IST-EIN-Beziehung).
// Ein Kunde kauft Produkte, er ist selbst keines. Deshalb erbt
// Kunde NICHT von Produkt, sondern ist eine eigenstaendige Klasse.
public class Kunde {

    private String name;
    private String email;
    private String kundennummer;

    public Kunde(String name, String email, String kundennummer) {
        this.name = name;
        this.email = email;
        this.kundennummer = kundennummer;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getKundennummer() {
        return kundennummer;
    }

    @Override
    public String toString() {
        return "Kunde [" + name + ", " + email + ", Kd.-Nr. " + kundennummer + "]";
    }
}
