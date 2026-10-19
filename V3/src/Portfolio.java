import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    private final List<Position> positions = new ArrayList<>();

    public void add(Stock stock, int quantity) {
        positions.add(new Position(stock, quantity));
    }

    public List<Position> getPositions() {
        return new ArrayList<>(positions);
    }

    public double getValue(StockExchange exchange) {
        double sum = 0;
        for (Position p : positions) {
            String symbol = p.getStock().getSymbol();
            if (exchange.isListed(symbol)) {
                sum += p.getQuantity() * exchange.getPrice(symbol);
            }
        }
        return sum;
    }

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
