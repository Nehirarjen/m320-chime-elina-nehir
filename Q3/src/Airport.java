import java.util.Locale;
import java.util.regex.Pattern;

/** Ein Flughafen mit Code, Name und optional Stadt und Staat. */
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
