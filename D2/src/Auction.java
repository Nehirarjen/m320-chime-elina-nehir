import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * HAT-Beziehungen in dieser Klasse:
 * - Aggregation: item und seller. Sie werden im Konstruktor übergeben und
 *   existieren auch ohne diese Auktion weiter.
 * - Komposition: bids. Die Auktion erzeugt ihre Gebote selbst (new Bid).
 *   Ein Gebot gibt es nur innerhalb einer Auktion.
 * - Abhängigkeit: placeBid benutzt die Person nur als Parameter.
 */
public class Auction {

    private final String id;
    private final Item item;
    private final Person seller;
    private final List<Bid> bids = new ArrayList<>();
    private boolean closed = false;

    // Aggregation: item und seller werden von aussen übergeben (kein "new")
    Auction(String id, Item item, Person seller) {
        this.id = id;
        this.item = item;
        this.seller = seller;
    }

    public String getId() {
        return id;
    }

    public boolean isClosed() {
        return closed;
    }

    /** Nimmt ein Gebot an, wenn es hoeher ist als das bisherige.
     *  Abhängigkeit: bidder wird nur als Parameter benutzt, nicht gespeichert. */
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

    public Optional<Bid> getHighestBid() {
        return bids.stream().max((a, b) -> Double.compare(a.getAmount(), b.getAmount()));
    }

    /** Gibt eine Kopie der Liste zurueck, damit sie von aussen nicht verändert wird. */
    public List<Bid> getBids() {
        return new ArrayList<>(bids);
    }

    /** Schliesst die Auktion und nennt den Gewinner (Kette: Auction -> Bid -> Person). */
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
