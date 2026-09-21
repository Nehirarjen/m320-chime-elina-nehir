import java.util.ArrayList;
import java.util.List;

/**
 * HAT-Beziehung in dieser Klasse:
 * - Komposition: auctions. Das Auktionshaus erzeugt seine Auktionen selbst
 *   (new Auction in createAuction). Eine Auktion gibt es nur im Haus.
 * -Delegation: placeBid und closeAuction geben den Aufruf an Auction weiter.
 */
public class AuctionHouse {

    private final String name;
    private final List<Auction> auctions = new ArrayList<>();

    public AuctionHouse(String name) {
        this.name = name;
    }

    /** Erzeugt eine neue Auktion (Komposition) und speichert sie im Haus. */
    public Auction createAuction(String auctionId, Item item, Person seller) {
        Auction auction = new Auction(auctionId, item, seller); // Komposition: Haus erzeugt Auction selbst
        auctions.add(auction);
        return auction;
    }

    private Auction findAuction(String auctionId) {
        return auctions.stream()
                .filter(a -> a.getId().equals(auctionId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Auktion " + auctionId + " nicht gefunden."));
    }

    /** Delegation: gibt den Aufruf an Auction.placeBid weiter. */
    public boolean placeBid(String auctionId, Person bidder, double amount) {
        return findAuction(auctionId).placeBid(bidder, amount);
    }

    /** Delegation: gibt den Aufruf an Auction.close weiter. */
    public String closeAuction(String auctionId) {
        return findAuction(auctionId).close();
    }

    public boolean removeAuction(String auctionId) {
        return auctions.removeIf(a -> a.getId().equals(auctionId));
    }

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
