import java.util.ArrayList;
import java.util.List;

/**
 * Ein Portfolio: eine Liste von Aktien-Positionen.
 * <p>
 * Zum Berechnen des Werts braucht das Portfolio eine Börse. Es arbeitet aber
 * nur mit dem Interface {@link StockExchange}. Welche Börse wirklich dahinter
 * steckt, weiss und braucht es nicht. So funktioniert dieselbe Methode mit
 * Zürich, London, New York und mit jeder Börse, die später dazukommt.
 */
public class Portfolio {

    private final List<Position> positions = new ArrayList<>();

    public void add(Stock stock, int quantity) {
        positions.add(new Position(stock, quantity));
    }

    /** Gibt eine Kopie der Positionen zurück. */
    public List<Position> getPositions() {
        return new ArrayList<>(positions);
    }

    /**
     * Berechnet den Wert des Portfolios an der übergebenen Börse.
     * Aktien, die dort nicht kotiert sind, werden nicht mitgezählt.
     *
     * @param exchange irgendeine Börse (das Interface, nicht eine bestimmte Klasse)
     * @return der Wert in der Währung der Börse
     */
    public double getValue(StockExchange exchange) {
        double sum = 0;
        for (Position p : positions) {
            String symbol = p.getStock().getSymbol();
            if (exchange.isListed(symbol)) {
                // Polymorphismus: Je nach Börse wird ein anderer Preis geliefert
                sum += p.getQuantity() * exchange.getPrice(symbol);
            }
        }
        return sum;
    }

    /** Gibt die Aktien zurück, die an der Börse nicht kotiert sind. */
    public List<Stock> getNotListed(StockExchange exchange) {
        List<Stock> notListed = new ArrayList<>();
        for (Position p : positions) {
            if (!exchange.isListed(p.getStock().getSymbol())) {
                notListed.add(p.getStock());
            }
        }
        return notListed;
    }
}
