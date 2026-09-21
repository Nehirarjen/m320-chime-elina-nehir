import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /** Nimmt ein Gebot an, wenn es hoeher ist als das bisherige. */
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

    /** Gibt eine Kopie der Liste zurueck, damit sie von aussen nicht veraendert wird. */
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
