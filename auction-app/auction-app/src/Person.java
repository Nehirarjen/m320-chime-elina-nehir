/**
 * Repraesentiert eine Person, die in der Auktions-App als Verkaeufer
 * oder als Bieter auftreten kann.
 *
 * Eine Person ist ein eigenstaendiges Objekt: Sie wird unabhaengig von
 * einer konkreten Auktion oder einem Gebot erzeugt und existiert auch
 * dann weiter, wenn die Auktion oder das Gebot nicht mehr existiert.
 * Deshalb wird sie in Auction und Bid nur per AGGREGATION eingebunden
 * (lose Kopplung, kein Besitzanspruch, keine Erzeugung durch das
 * einbindende Objekt).
 */
public class Person {

    private final String id;
    private final String name;
    private final String email;

    public Person(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}
