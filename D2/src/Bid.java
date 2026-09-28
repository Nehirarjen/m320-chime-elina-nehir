/**
 * Ein Gebot einer {@link Person} in einer {@link Auction}.
 * <p>
 * HAT-Beziehungen:
 * <ul>
 *   <li>Aggregation: {@link Person} (Bieter). Sie wird übergeben und
 *       existiert auch ohne das Gebot weiter.</li>
 *   <li>Komposition: Ein Gebot ist Teil einer Auktion. Nur {@link Auction}
 *       darf es erzeugen, darum ist der Konstruktor package-private.</li>
 * </ul>
 *
 * @see Auction
 */
public class Bid {

    private final Person bidder;
    private final double amount;

    /**
     * Erstellt ein Gebot.
     * <p>
     * Der Konstruktor ist package-private: Nur {@link Auction} erzeugt Gebote
     * (Komposition). Der Bieter wird von aussen übergeben (Aggregation).
     *
     * @param bidder Person, die bietet
     * @param amount Betrag in CHF
     */
    Bid(Person bidder, double amount) {
        this.bidder = bidder;
        this.amount = amount;
    }

    /**
     * Gibt die Person zurück, die dieses Gebot abgegeben hat.
     *
     * @return der Bieter
     */
    public Person getBidder() {
        return bidder;
    }

    /**
     * Gibt den Betrag des Gebots zurück.
     *
     * @return der Betrag in CHF
     */
    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return bidder.getName() + " bietet CHF " + amount;
    }
}
