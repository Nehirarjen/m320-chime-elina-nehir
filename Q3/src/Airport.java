import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Ein Flughafen. Das Objekt ist unveränderlich (record): Zum Ändern wird ein
 * neues Airport-Objekt erzeugt und in der HashMap ersetzt.
 * <p>
 * Der Code wird immer getrimmt und in Grossbuchstaben gespeichert ("jfk" wird
 * zu "JFK"). Stadt und Staat sind optional, weil nicht jede CSV-Datei sie hat.
 * Fehlt ein Wert, ist er ein leerer String (nie null).
 */
public record Airport(String code, String name, String city, String state) {

    private static final Pattern CODE_FORMAT = Pattern.compile("[A-Z]{3}");

    public Airport {
        code = normalizeCode(code);
        if (!isValidCode(code)) {
            throw new IllegalArgumentException("Invalid airport code '" + code + "' (3 letters expected).");
        }
        name = clean(name);
        if (name.isEmpty()) {
            throw new IllegalArgumentException("The airport name must not be empty.");
        }
        city = clean(city);
        state = clean(state);
    }

    /** Macht aus der Eingabe einen Schlüssel: trimmen und gross schreiben. null wird zu "". */
    public static String normalizeCode(String code) {
        return code == null ? "" : code.trim().toUpperCase(Locale.ROOT);
    }

    public static boolean isValidCode(String code) {
        return CODE_FORMAT.matcher(normalizeCode(code)).matches();
    }

    private static String clean(String text) {
        return text == null ? "" : text.trim();
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder(code).append(" | ").append(name);
        if (!city.isEmpty()) {
            text.append(" | ").append(city);
        }
        if (!state.isEmpty()) {
            text.append(" | ").append(state);
        }
        return text.toString();
    }
}
