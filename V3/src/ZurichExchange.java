import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ZurichExchange implements StockExchange { // interface in einer bärse umsetzen

    private final Map<String, Double> prices = new LinkedHashMap<>();

    public ZurichExchange() {
        prices.put("NESN", 92.50);
        prices.put("ABBN", 48.30);
        prices.put("NOVN", 88.10);
        prices.put("SREN", 105.40);
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
