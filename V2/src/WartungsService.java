import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Wartung mit Sonderregel: Nach Beginn ist keine Stornierung mehr erlaubt. */
public class WartungsService extends Service {
    private static final double PREIS_PRO_GERAET = 35;
    private static final double EXPRESS_FAKTOR = 1.25;
    private static final DateTimeFormatter ZEIT_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final int geraeteAnzahl;
    private final boolean express;
    private final LocalDateTime startZeit;

    public WartungsService(String id, String name, double basisPreis, int geraeteAnzahl,
                           boolean express, LocalDateTime startZeit) {
        super(id, name, basisPreis);
        if (geraeteAnzahl <= 0 || startZeit == null) {
            throw new IllegalArgumentException("Geräteanzahl muss positiv sein und die Startzeit ist erforderlich.");
        }
        this.geraeteAnzahl = geraeteAnzahl;
        this.express = express;
        this.startZeit = startZeit;
    }

    @Override
    public double berechnePreis() {
        double preis = getBasisPreis() + geraeteAnzahl * PREIS_PRO_GERAET;
        return express ? preis * EXPRESS_FAKTOR : preis;
    }

    @Override
    public void stornieren() throws ServiceException {
        if (!LocalDateTime.now().isBefore(startZeit)) {
            throw new ServiceException("Wartungsservice " + getId() + " hat bereits begonnen und kann nicht storniert werden.");
        }
        super.stornieren();
    }

    @Override
    protected String details() {
        return "Wartung: " + geraeteAnzahl + " Gerät(e), " + (express ? "Express" : "Standard")
                + ", Start: " + startZeit.format(ZEIT_FORMAT);
    }
}
