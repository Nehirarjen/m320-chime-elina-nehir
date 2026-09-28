import java.util.ArrayList;
import java.util.List;

/**
 * Das Auktionshaus verwaltet alle {@link Auction Auktionen}.
 * <p>
 * Von aussen (z.B. in {@code Demo}) muss man nur das Auktionshaus kennen.
 * Gebote und das Schliessen einer Auktion laufen über die Methoden dieser
 * Klasse, die den Aufruf an die passende Auktion weitergeben.
 * <p>
 * HAT-Beziehung und Delegation:
 * <ul>
 *   <li>Komposition: {@link Auction}. Das Auktionshaus erzeugt seine
 *       Auktionen selbst (in {@link #createAuction(String, Item, Person)}).
 *       Eine Auktion gibt es nur im Auktionshaus.</li>
 *   <li>Delegation: {@link #placeBid(String, Person, double)} und
 *       {@link #closeAuction(String)} suchen die Auktion und geben den
 *       Aufruf an sie weiter. Die eigentliche Arbeit macht {@link Auction}.</li>
 * </ul>
 *
 * @see Auction
 */
public class AuctionHouse {

    private final String name;
    private final List<Auction> auctions = new ArrayList<>();

    /**
     * Erstellt ein Auktionshaus ohne Auktionen.
     *
     * @param name Name des Auktionshauses, z.B. "TBZ Online Auktionen"
     */
    public AuctionHouse(String name) {
        this.name = name;
    }

    /**
     * Erzeugt eine neue Auktion und speichert sie im Auktionshaus.
     * <p>
     * Die ID sollte eindeutig sein. Das wird nicht geprüft. Bei doppelter ID
     * findet {@link #placeBid(String, Person, double)} nur die erste Auktion.
     *
     * @param auctionId eindeutige Nummer der Auktion, z.B. "A1"
     * @param item      Gegenstand, der versteigert wird
     * @param seller    Person, die den Gegenstand verkauft
     * @return die neue, offene Auktion
     */
    public Auction createAuction(String auctionId, Item item, Person seller) {
        Auction auction = new Auction(auctionId, item, seller); // Komposition: Haus erzeugt Auction selbst
        auctions.add(auction);
        return auction;
    }

    /**
     * Sucht eine Auktion anhand ihrer ID (Hilfsmethode, nur intern benutzt).
     *
     * @param auctionId ID der gesuchten Auktion
     * @return die gefundene Auktion
     * @throws IllegalArgumentException wenn es keine Auktion mit dieser ID gibt
     */
    private Auction findAuction(String auctionId) {
        return auctions.stream()
                .filter(a -> a.getId().equals(auctionId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Auktion " + auctionId + " nicht gefunden."));
    }

    /**
     * Gibt ein Gebot für eine Auktion ab.
     * <p>
     * Delegation: Das Auktionshaus sucht die Auktion und ruft dort
     * {@link Auction#placeBid(Person, double)} auf. Ob das Gebot hoch genug
     * ist, entscheidet die Auktion.
     *
     * @param auctionId ID der Auktion
     * @param bidder    Person, die bietet
     * @param amount    Betrag in CHF
     * @return {@code true}, wenn das Gebot angenommen wurde;
     *         {@code false}, wenn es zu niedrig ist
     * @throws IllegalArgumentException wenn es keine Auktion mit dieser ID gibt
     * @throws IllegalStateException    wenn die Auktion schon geschlossen ist
     */
    public boolean placeBid(String auctionId, Person bidder, double amount) {
        return findAuction(auctionId).placeBid(bidder, amount);
    }

    /**
     * Schliesst eine Auktion.
     * <p>
     * Delegation: Der Aufruf geht an {@link Auction#close()}. Die Auktion
     * bestimmt den Gewinner.
     *
     * @param auctionId ID der Auktion
     * @return Text mit Gewinner und Preis, oder ein Hinweis, dass der
     *         Gegenstand nicht verkauft wurde
     * @throws IllegalArgumentException wenn es keine Auktion mit dieser ID gibt
     */
    public String closeAuction(String auctionId) {
        return findAuction(auctionId).close();
    }

    /**
     * Entfernt eine Auktion aus dem Auktionshaus, auch mit ihren Geboten.
     * Danach gehört die Auktion nicht mehr zum Haus.
     *
     * @param auctionId ID der Auktion
     * @return {@code true}, wenn die Auktion entfernt wurde;
     *         {@code false}, wenn es keine Auktion mit dieser ID gab
     */
    public boolean removeAuction(String auctionId) {
        return auctions.removeIf(a -> a.getId().equals(auctionId));
    }

    /**
     * Gibt alle Auktionen zurück, die noch nicht geschlossen sind.
     * <p>
     * Es wird eine neue Liste geliefert. Änderungen daran wirken sich nicht
     * auf das Auktionshaus aus.
     *
     * @return Liste der offenen Auktionen (leer, wenn keine offen ist)
     */
    public List<Auction> getOpenAuctions() {
        List<Auction> open = new ArrayList<>();
        for (Auction a : auctions) {
            if (!a.isClosed()) {
                open.add(a);
            }
        }
        return open;
    }

    @Override
    public String toString() {
        return "Auktionshaus \"" + name + "\" mit " + auctions.size() + " Auktion(en)";
    }
}
