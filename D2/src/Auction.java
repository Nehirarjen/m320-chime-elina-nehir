import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Eine Auktion für genau ein {@link Item}.
 * <p>
 * Personen können Gebote abgeben, solange die Auktion offen ist. Ein Gebot
 * wird nur angenommen, wenn es höher ist als das bisher höchste Gebot
 * (bzw. höher als der Startpreis des Items). Mit {@link #close()} wird die
 * Auktion beendet und der Gewinner bestimmt.
 * <p>
 * HAT-Beziehungen:
 * <ul>
 *   <li>Aggregation: {@link Item} und {@link Person} (Verkäufer). Sie werden
 *       von aussen übergeben und existieren auch ohne diese Auktion weiter.</li>
 *   <li>Komposition: {@link Bid}. Die Auktion erzeugt ihre Gebote selbst,
 *       ein Gebot gibt es nur innerhalb einer Auktion.</li>
 *   <li>Abhängigkeit: Die bietende {@link Person} wird nur als Parameter von
 *       {@link #placeBid(Person, double)} benutzt und nicht gespeichert.</li>
 * </ul>
 * Auktionen werden nicht direkt erzeugt, sondern über
 * {@link AuctionHouse#createAuction(String, Item, Person)}.
 *
 * @see AuctionHouse
 * @see Bid
 */
public class Auction {

    private final String id;
    private final Item item;
    private final Person seller;
    private final List<Bid> bids = new ArrayList<>();
    private boolean closed = false;

    /**
     * Erstellt eine offene Auktion ohne Gebote.
     * <p>
     * Der Konstruktor ist package-private: Nur {@link AuctionHouse} darf
     * Auktionen erzeugen (Komposition).
     *
     * @param id     eindeutige Nummer der Auktion, z.B. "A1"
     * @param item   Gegenstand, der versteigert wird
     * @param seller Person, die den Gegenstand verkauft
     */
    Auction(String id, Item item, Person seller) {
        this.id = id;
        this.item = item;
        this.seller = seller;
    }

    /**
     * Gibt die Nummer der Auktion zurück.
     *
     * @return die eindeutige ID, z.B. "A1"
     */
    public String getId() {
        return id;
    }

    /**
     * Sagt, ob die Auktion schon beendet wurde.
     *
     * @return {@code true}, wenn {@link #close()} aufgerufen wurde
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Gibt ein Gebot für diese Auktion ab.
     * <p>
     * Das Gebot muss höher sein als das bisher höchste Gebot. Gibt es noch
     * keines, muss es höher sein als der Startpreis des Items.
     *
     * @param bidder Person, die bietet (darf nicht {@code null} sein)
     * @param amount Betrag in CHF
     * @return {@code true}, wenn das Gebot angenommen wurde;
     *         {@code false}, wenn es zu niedrig ist
     * @throws IllegalStateException wenn die Auktion schon geschlossen ist
     */
    public boolean placeBid(Person bidder, double amount) {
        if (closed) {
            throw new IllegalStateException(
                    "Auktion \"" + item.getTitle() + "\" ist bereits geschlossen.");
        }
        double minRequired = getHighestBid()
                .map(Bid::getAmount)
                .orElse(item.getStartingPrice());
        if (amount <= minRequired) {
            return false;
        }
        bids.add(new Bid(bidder, amount)); // Komposition: Auction erzeugt das Bid
        return true;
    }

    /**
     * Sucht das höchste Gebot.
     *
     * @return das höchste Gebot, oder ein leeres {@link Optional}, wenn
     *         noch niemand geboten hat
     */
    public Optional<Bid> getHighestBid() {
        return bids.stream().max((a, b) -> Double.compare(a.getAmount(), b.getAmount()));
    }

    /**
     * Gibt alle Gebote in der Reihenfolge zurück, in der sie abgegeben wurden.
     * <p>
     * Es wird eine Kopie der Liste geliefert. Änderungen daran wirken sich
     * nicht auf die Auktion aus, so bleibt die Komposition geschützt.
     *
     * @return Kopie der Gebotsliste (leer, wenn es keine Gebote gibt)
     */
    public List<Bid> getBids() {
        return new ArrayList<>(bids);
    }

    /**
     * Schliesst die Auktion. Danach sind keine Gebote mehr möglich.
     * <p>
     * Der Gewinner ist die Person mit dem höchsten Gebot (Kette:
     * Auction, Bid, Person).
     *
     * @return Text mit Gewinner und Preis, oder ein Hinweis, dass der
     *         Gegenstand nicht verkauft wurde
     */
    public String close() {
        closed = true;
        return getHighestBid()
                .map(bid -> bid.getBidder().getName() + " gewinnt \"" + item.getTitle()
                        + "\" fuer CHF " + bid.getAmount())
                .orElse("Keine Gebote fuer \"" + item.getTitle() + "\" - nicht verkauft.");
    }

    @Override
    public String toString() {
        return "Auktion[" + id + "] " + item + ", Verkaeufer: " + seller.getName()
                + ", Gebote: " + bids.size();
    }
}
