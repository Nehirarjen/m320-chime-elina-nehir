/** Eine Position im Portfolio: eine Aktie und wie viele Stück davon. */
public class Position {

    private final Stock stock;
    private final int quantity;

    public Position(Stock stock, int quantity) {
        this.stock = stock;
        this.quantity = quantity;
    }

    public Stock getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }
}
