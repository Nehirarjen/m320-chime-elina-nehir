import java.util.Set;

public interface StockExchange {

    String getName();

    String getCurrency();

    Set<String> getSymbols();

    boolean isListed(String symbol);

    double getPrice(String symbol);
}
