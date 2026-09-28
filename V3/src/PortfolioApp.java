import java.util.List;
import java.util.Scanner;

/** Konsolenmenü: Börse wählen, Portfolio-Wert und Aktienkurse abfragen. */
public class PortfolioApp {

    private static final Scanner EINGABE = new Scanner(System.in);

    public static void main(String[] args) {
        // Alle Börsen stehen als Interface-Typ in einer Liste
        List<StockExchange> exchanges = List.of(
                new ZurichExchange(),
                new LondonExchange(),
                new NewYorkExchange());

        Portfolio portfolio = new Portfolio();
        portfolio.add(new Stock("NESN", "Nestlé"), 10);
        portfolio.add(new Stock("ABBN", "ABB"), 20);
        portfolio.add(new Stock("NOVN", "Novartis"), 5);
        portfolio.add(new Stock("SHEL", "Shell"), 15);
        portfolio.add(new Stock("AAPL", "Apple"), 8);

        // Die aktuelle Börse ist nur vom Typ StockExchange
        StockExchange current = exchanges.get(0);

        boolean laeuft = true;
        while (laeuft) {
            System.out.println("\n=== Aktien-Portfolio ===");
            System.out.println("Aktuelle Börse: " + current.getName() + " (" + current.getCurrency() + ")");
            System.out.println("1. Börse wählen");
            System.out.println("2. Wert des Portfolios anzeigen");
            System.out.println("3. Kurs einer Aktie abfragen");
            System.out.println("4. Portfolio anzeigen");
            System.out.println("5. Beenden");

            switch (liesZahl("Auswahl: ")) {
                case 1 -> current = boerseWaehlen(exchanges, current);
                case 2 -> wertAnzeigen(portfolio, current);
                case 3 -> kursAbfragen(current);
                case 4 -> portfolioAnzeigen(portfolio);
                case 5 -> {
                    laeuft = false;
                    System.out.println("Auf Wiedersehen.");
                }
                default -> System.out.println("Bitte eine Zahl von 1 bis 5 eingeben.");
            }
        }
    }

    private static StockExchange boerseWaehlen(List<StockExchange> exchanges, StockExchange current) {
        System.out.println();
        for (int i = 0; i < exchanges.size(); i++) {
            System.out.println((i + 1) + ". " + exchanges.get(i).getName());
        }
        int nr = liesZahl("Börse: ");
        if (nr < 1 || nr > exchanges.size()) {
            System.out.println("Ungültige Auswahl, die Börse bleibt gleich.");
            return current;
        }
        return exchanges.get(nr - 1);
    }

    private static void wertAnzeigen(Portfolio portfolio, StockExchange exchange) {
        // Das Portfolio bekommt irgendeine Börse. Welche, muss es nicht wissen.
        double wert = portfolio.getValue(exchange);
        System.out.printf("Wert des Portfolios in %s: %.2f %s%n",
                exchange.getName(), wert, exchange.getCurrency());

        List<Stock> nichtKotiert = portfolio.getNotListed(exchange);
        if (!nichtKotiert.isEmpty()) {
            System.out.println("Nicht kotiert (nicht mitgezählt): " + nichtKotiert);
        }
    }

    private static void kursAbfragen(StockExchange exchange) {
        System.out.println("Kotiert in " + exchange.getName() + ": " + exchange.getSymbols());
        System.out.print("Symbol: ");
        String symbol = EINGABE.nextLine().trim().toUpperCase();
        if (exchange.isListed(symbol)) {
            System.out.printf("%s kostet %.2f %s%n", symbol, exchange.getPrice(symbol), exchange.getCurrency());
        } else {
            System.out.println(symbol + " ist an dieser Börse nicht kotiert.");
        }
    }

    private static void portfolioAnzeigen(Portfolio portfolio) {
        System.out.println("\n--- Portfolio ---");
        for (Position p : portfolio.getPositions()) {
            System.out.println(p.getQuantity() + " x " + p.getStock());
        }
    }

    private static int liesZahl(String aufforderung) {
        while (true) {
            System.out.print(aufforderung);
            try {
                return Integer.parseInt(EINGABE.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine ganze Zahl eingeben.");
            }
        }
    }
}
