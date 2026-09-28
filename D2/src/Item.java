/**
 * Ein Gegenstand, der in einer {@link Auction} versteigert wird.
 * <p>
 * HAT-Beziehung: Ein {@code Item} existiert auch ohne Auktion. {@link Auction}
 * speichert nur eine Referenz darauf und erzeugt es nicht selbst (Aggregation).
 *
 * @see Auction
 */
public class Item {

    private final String id;
    private final String title;
    private final String description;
    private final double startingPrice;

    /**
     * Erstellt einen Gegenstand.
     *
     * @param id            eindeutige Nummer, z.B. "I1"
     * @param title         kurzer Name, z.B. "Gebrauchtes Notebook"
     * @param description   Beschreibung des Gegenstands
     * @param startingPrice Startpreis in CHF, ein Gebot muss höher sein
     */
    public Item(String id, String title, String description, double startingPrice) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startingPrice = startingPrice;
    }

    /**
     * Gibt die Nummer des Gegenstands zurück.
     *
     * @return die eindeutige ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gibt den Namen des Gegenstands zurück.
     *
     * @return der Titel
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gibt die Beschreibung des Gegenstands zurück.
     *
     * @return die Beschreibung
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gibt den Startpreis zurück.
     *
     * @return der Startpreis in CHF
     */
    public double getStartingPrice() {
        return startingPrice;
    }

    @Override
    public String toString() {
        return title + " (Startpreis: CHF " + startingPrice + ")";
    }
}
