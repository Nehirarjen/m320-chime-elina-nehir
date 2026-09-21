/**
 * HAT-Beziehungen in dieser Klasse:
 * - Aggregation: bidder. Die Person wird übergeben und existiert auch ohne
 *   das Gebot weiter.
 * - Komposition (dieses Gebot ist Teil einer Auktion): Nur Auction darf ein
 *   Bid erzeugen, darum ist der Konstruktor package-private.
 */
public class Bid {

    private final Person bidder;
    private final double amount;

    // Aggregation: bidder wird von aussen übergeben. Der Konstruktor ist
    // package-private, nur Auction darf ein Bid erzeugen (Komposition).
    Bid(Person bidder, double amount) {
        this.bidder = bidder;
        this.amount = amount;
    }

    public Person getBidder() {
        return bidder;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return bidder.getName() + " bietet CHF " + amount;
    }
}
