/**
 * Ein Auktionsgegenstand (z.B. ein Notebook, ein Bild, ein Sammlerstueck).
 *
 * Wie Person ist auch Item ein eigenstaendiges Objekt: Der physische
 * Gegenstand existiert unabhaengig davon, ob er gerade in einer Auktion
 * angeboten wird oder nicht. Auction haelt daher nur eine Referenz auf
 * ein bereits existierendes Item (AGGREGATION), erzeugt es aber nicht
 * selbst.
 */
public class Item {

    private final String id;
    private final String title;
    private final String description;
    private final double startingPrice;

    public Item(String id, String title, String description, double startingPrice) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startingPrice = startingPrice;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    @Override
    public String toString() {
        return title + " (Startpreis: CHF " + startingPrice + ")";
    }
}
