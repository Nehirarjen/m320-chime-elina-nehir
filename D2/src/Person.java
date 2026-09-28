/**
 * Eine Person, die in der Auktions-App als Verkäufer oder Bieter auftritt.
 * <p>
 * HAT-Beziehung: Eine {@code Person} existiert unabhängig von Auktionen und
 * Geboten. {@link Auction} und {@link Bid} speichern nur eine Referenz darauf
 * (Aggregation).
 *
 * @see Auction
 * @see Bid
 */
public class Person {

    private final String id;
    private final String name;
    private final String email;

    /**
     * Erstellt eine Person.
     *
     * @param id    eindeutige Nummer, z.B. "P1"
     * @param name  vollständiger Name
     * @param email E-Mail-Adresse
     */
    public Person(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Gibt die Nummer der Person zurück.
     *
     * @return die eindeutige ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gibt den Namen der Person zurück.
     *
     * @return der Name
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die E-Mail-Adresse der Person zurück.
     *
     * @return die E-Mail-Adresse
     */
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}
