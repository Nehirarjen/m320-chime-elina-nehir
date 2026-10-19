import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class NewYorkExchange implements StockExchange {

    private final Map<String, Double> prices = new LinkedHashMap<>();

    public NewYorkExchange() {
        prices.put("AAPL", 225.00);
        prices.put("MSFT", 410.00);
        prices.put("KO", 62.30);
        prices.put("NOVN", 98.50);
    }

    @Override
    public String getName() {
        return "New York (NYSE)";
    }

    @Override
    public String getCurrency() {
        return "USD";
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
            throw new IllegalArgumentException(symbol + " ist in New York nicht kotiert.");
        }
        return prices.get(symbol);
    }
}
