/** Service für Beratungen mit levelabhängigem Stundensatz. */
public class BeratungsService extends Service {
    public enum Level {
        JUNIOR(80), PROFESSIONAL(120), EXPERT(160);

        private final double stundenSatz;

        Level(double stundenSatz) {
            this.stundenSatz = stundenSatz;
        }

        public double getStundenSatz() {
            return stundenSatz;
        }
    }

    private final double stunden;
    private final Level level;

    public BeratungsService(String id, String name, double basisPreis, double stunden, Level level) {
        super(id, name, basisPreis);
        if (stunden <= 0 || level == null) {
            throw new IllegalArgumentException("Stunden müssen positiv sein und ein Level ist erforderlich.");
        }
        this.stunden = stunden;
        this.level = level;
    }

    @Override
    public double berechnePreis() {
        return getBasisPreis() + stunden * level.getStundenSatz();
    }

    @Override
    protected String details() {
        return "Beratung: " + stunden + " h, " + level + " (CHF " + level.getStundenSatz() + "/h)";
    }
}
