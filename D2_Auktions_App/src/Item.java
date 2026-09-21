
public class Item {

    private final String id;
    private final String title;
    private final String description;
    private final double startingPrice;

    public Item(String id, String title, String description, double startingPrice) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startingPrice = startingPrice;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    @Override
    public String toString() {
        return title + " (Startpreis: CHF " + startingPrice + ")";
    }
}
