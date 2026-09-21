import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Eine einzelne Auktion fuer genau ein Item.
 *
 * HAT-Beziehungen in dieser Klasse:
 * - bids (List&lt;Bid&gt;): KOMPOSITION. Die Auktion erzeugt ihre Gebote
 *   selbst (siehe placeBid) und ist fuer deren Lebenszyklus verantwortlich.
 *   Ein Bid ohne "seine" Auction ergibt inhaltlich keinen Sinn.
 * - item, seller (Person): AGGREGATION. Beide werden dem Konstruktor von
 *   aussen uebergeben, Auction erzeugt sie nicht selbst, und beide
 *   existieren unabhaengig weiter (z.B. kann derselbe Verkaeufer mehrere
 *   Auktionen haben).
 *
 * Delegation: placeBid() und close() geben die eigentliche Ermittlung des
 * Hoechstgebots an getHighestBid() weiter, welches wiederum die Arbeit an
 * die bids-Liste delegiert. close() delegiert zusaetzlich an
 * Bid.getBidder(), um den Namen des Gewinners zu ermitteln - so entsteht
 * eine Kette von Objekt-Referenzen: AuctionHouse -> Auction -> Bid -> Person.
 */
public class Auction {

    private final String id;
    private final Item item;                          // Aggregation
    private final Person seller;                       // Aggregation
    private final List<Bid> bids = new ArrayList<>();  // Komposition
    private boolean closed = false;

    Auction(String id, Item item, Person seller) {
        this.id = id;
        this.item = item;
        this.seller = seller;
    }

    public String getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public Person getSeller() {
        return seller;
    }

    public boolean isClosed() {
        return closed;
    }

    /**
     * Nimmt ein Gebot entgegen. Die Auktion ist verantwortlich fuer die
     * Erzeugung des Bid-Objekts (Komposition) und delegiert die Pruefung,
     * ob das Gebot ueberhaupt hoch genug ist, an getHighestBid().
     *
     * @return true, wenn das Gebot angenommen wurde, sonst false.
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
            return false; // Gebot zu niedrig -> abgelehnt
        }
        bids.add(new Bid(bidder, amount)); // Komposition: Auction erzeugt das Bid
        return true;
    }

    /** Ermittelt das aktuell hoechste Gebot - delegiert die Suche an die bids-Liste. */
    public Optional<Bid> getHighestBid() {
        return bids.stream().max((a, b) -> Double.compare(a.getAmount(), b.getAmount()));
    }

    /** Gibt eine Kopie der Gebotsliste zurueck, um die Kapselung zu schuetzen. */
    public List<Bid> getBids() {
        return new ArrayList<>(bids);
    }

    /**
     * Schliesst die Auktion. Delegiert die Ermittlung des Gewinners an
     * getHighestBid(), welches selbst wieder an Bid.getBidder() delegiert.
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
