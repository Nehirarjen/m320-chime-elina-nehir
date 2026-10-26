import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/** Merkt sich die letzten 10 Suchen nach Flughafen-Code. */
public class SearchHistory {

    public record Entry(String code, boolean found) {
    }

    private static final int MAX_ENTRIES = 10;

    private final Deque<Entry> entries = new ArrayDeque<>();

    public void record(String code, boolean found) {
        String normalized = Airport.normalizeCode(code);
        if (normalized.isEmpty()) {
            return;
        }
        if (!entries.isEmpty() && entries.peekFirst().code().equals(normalized)) {
            return;
        }
        entries.addFirst(new Entry(normalized, found));
        if (entries.size() > MAX_ENTRIES) {
            entries.removeLast();
        }
    }

    public List<Entry> latestFirst() {
        return List.copyOf(entries);
    }
}
