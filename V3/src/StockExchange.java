import java.util.Set;

/**
 * Das Interface für alle Börsen.
 * <p>
 * Es legt nur fest, WAS eine Börse können muss, aber nicht WIE. Das "Wie"
 * (die Preise, die Währung, die kotierten Aktien) macht jede Börse selbst.
 * Das {@link Portfolio} kennt nur dieses Interface und weiss nicht, ob
 * dahinter Zürich, London oder New York steckt.
 */
public interface StockExchange {

    /** Name der Börse, z.B. "Zürich (SIX)". */
    String getName();

    /** Währung, in der die Preise stehen, z.B. "CHF". */
    String getCurrency();

    /** Alle Symbole, die an dieser Börse kotiert (registriert) sind. */
    Set<String> getSymbols();

    /** Sagt, ob die Aktie mit diesem Symbol an dieser Börse gehandelt wird. */
    boolean isListed(String symbol);

    /**
     * Gibt den Preis einer Aktie zurück.
     *
     * @throws IllegalArgumentException wenn die Aktie hier nicht kotiert ist
     */
    double getPrice(String symbol);
}
