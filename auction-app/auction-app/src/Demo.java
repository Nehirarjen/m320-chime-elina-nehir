/**
 * Demo-Szenario fuer den Kompetenznachweis D2 (HAT-Beziehungen).
 * Zeigt Aggregation, Komposition und Delegation im Zusammenspiel.
 */
public class Demo {

    public static void main(String[] args) {
        AuctionHouse house = new AuctionHouse("TBZ Online Auktionen");

        // Personen und Items existieren unabhaengig -> werden nur referenziert (Aggregation)
        Person seller = new Person("P1", "Elina Meier", "elina@example.com");
        Person bidder1 = new Person("P2", "Tom Frei", "tom@example.com");
        Person bidder2 = new Person("P3", "Sara Keller", "sara@example.com");

        Item laptop = new Item("I1", "Gebrauchtes Notebook", "ThinkPad, 3 Jahre alt", 200.0);

        // AuctionHouse erzeugt die Auction selbst -> Komposition
        Auction auction = house.createAuction("A1", laptop, seller);

        System.out.println(house);
        System.out.println(auction);
        System.out.println();

        // Alle Aufrufe gehen ueber das AuctionHouse und werden an Auction delegiert
        System.out.println("Gebot 220 CHF akzeptiert? " + house.placeBid("A1", bidder1, 220));
        System.out.println("Gebot 150 CHF akzeptiert? " + house.placeBid("A1", bidder1, 150));
        System.out.println("Gebot 300 CHF akzeptiert? " + house.placeBid("A1", bidder2, 300));
        System.out.println("Gebot 280 CHF akzeptiert? " + house.placeBid("A1", bidder1, 280));

        System.out.println("\nAlle Gebote fuer \"" + laptop.getTitle() + "\":");
        for (Bid b : auction.getBids()) {
            System.out.println("  " + b);
        }

        System.out.println("\n" + house.closeAuction("A1"));

        // Nach dem Schliessen sind keine weiteren Gebote mehr moeglich
        try {
            house.placeBid("A1", bidder1, 500);
        } catch (IllegalStateException e) {
            System.out.println("\nErwarteter Fehler: " + e.getMessage());
        }
    }
}
