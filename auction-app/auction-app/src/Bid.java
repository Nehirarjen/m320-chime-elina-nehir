import java.time.LocalDateTime;

public class Bid {

    private final Person bidder;       // Aggregation
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
