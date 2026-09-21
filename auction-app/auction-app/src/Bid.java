import java.time.LocalDateTime;

/**
 * Ein einzelnes Gebot innerhalb einer Auktion.
 *
 * Ein Bid hat ausserhalb einer Auktion keine eigenstaendige Bedeutung:
 * Er entsteht ausschliesslich in Auction.placeBid(...) und wird auch nur
 * dort verwaltet. Diese starke Abhaengigkeit ist eine KOMPOSITION
 * (deshalb ist der Konstruktor package-private: nur Klassen im selben
 * Package - hier Auction - duerfen ein Bid erzeugen).
 *
 * Der Bieter (Person) dagegen existiert unabhaengig vom Gebot weiter,
 * er wird dem Bid nur als Referenz uebergeben -> AGGREGATION.
 */
public class Bid {

    private final Person bidder;       // Aggregation: Person existiert unabhaengig vom Bid
    private final double amount;
    private final LocalDateTime timestamp;

    Bid(Person bidder, double amount) {
        this.bidder = bidder;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public Person getBidder() {
        return bidder;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return bidder.getName() + " bietet CHF " + amount;
    }
}
