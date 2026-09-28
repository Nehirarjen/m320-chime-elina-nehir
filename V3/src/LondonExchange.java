import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/** Die Börse London. Preise in GBP (erfundene Beispielwerte). */
public class LondonExchange implements StockExchange {

    private final Map<String, Double> prices = new LinkedHashMap<>();

    public LondonExchange() {
        prices.put("SHEL", 26.40);   // Shell
        prices.put("HSBA", 6.80);    // HSBC
        prices.put("AZN", 108.20);   // AstraZeneca
    }

    @Override
    public String getName() {
        return "London (LSE)";
    }

    @Override
    public String getCurrency() {
        return "GBP";
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
            throw new IllegalArgumentException(symbol + " ist in London nicht kotiert.");
        }
        return prices.get(symbol);
    }
}
