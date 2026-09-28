import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/** Die Börse Zürich. Preise in CHF (erfundene Beispielwerte). */
public class ZurichExchange implements StockExchange {

    private final Map<String, Double> prices = new LinkedHashMap<>();

    public ZurichExchange() {
        prices.put("NESN", 92.50);   // Nestlé
        prices.put("ABBN", 48.30);   // ABB
        prices.put("NOVN", 88.10);   // Novartis
        prices.put("SREN", 105.40);  // Swiss Re
    }

    @Override
    public String getName() {
        return "Zürich (SIX)";
    }

    @Override
    public String getCurrency() {
        return "CHF";
    }

    @Override
    public Set<String> getSymbols() {
        return prices.keySet();
    }

    @Override
    public boolean isListed(String symbol) {
        return prices.containsKey(symbol);
    }

    @Override
    public double getPrice(String symbol) {
        if (!isListed(symbol)) {
            throw new IllegalArgumentException(symbol + " ist in Zürich nicht kotiert.");
        }
        return prices.get(symbol);
    }
}
