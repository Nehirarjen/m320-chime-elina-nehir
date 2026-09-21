import java.util.ArrayList;
import java.util.List;

/**
 * Das Auktionshaus verwaltet alle Auktionen.
 *
 * HAT-Beziehung: auctions (List&lt;Auction&gt;) ist eine KOMPOSITION.
 * AuctionHouse erzeugt seine Auction-Objekte selbst in createAuction(...);
 * in diesem Modell gibt es keine Auction ausserhalb eines AuctionHouse.
 *
 * Delegation: placeBid(...) und closeAuction(...) suchen zuerst die
 * passende Auktion und geben den eigentlichen Aufruf dann an das
 * gefundene Auction-Objekt weiter. Der Aufrufer (z.B. die Demo-Klasse)
 * muss die interne Struktur (Bid, Person) gar nicht kennen - das
 * AuctionHouse delegiert die Arbeit einfach weiter.
 */
public class AuctionHouse {

    private final String name;
    private final List<Auction> auctions = new ArrayList<>(); // Komposition

    public AuctionHouse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /** Erzeugt eine neue Auktion und registriert sie im Haus (Komposition). */
    public Auction createAuction(String auctionId, Item item, Person seller) {
        Auction auction = new Auction(auctionId, item, seller);
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

    /** Delegiert direkt an Auction.placeBid(...). */
    public boolean placeBid(String auctionId, Person bidder, double amount) {
        return findAuction(auctionId).placeBid(bidder, amount);
    }

    /** Delegiert an Auction.close(...). */
    public String closeAuction(String auctionId) {
        return findAuction(auctionId).close();
    }

    /** Entfernt eine Auktion vollstaendig aus dem Haus. */
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
