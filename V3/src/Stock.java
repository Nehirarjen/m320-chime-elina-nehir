/** Eine Aktie mit Symbol (z.B. "NESN") und Namen (z.B. "Nestlé"). */
public class Stock {

    private final String symbol;
    private final String name;

    public Stock(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (" + symbol + ")";
    }
}
