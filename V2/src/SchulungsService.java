/** Service für Schulungen; Material kann pro Teilnehmer eingerechnet werden. */
public class SchulungsService extends Service {
    private static final double PREIS_PRO_TEILNEHMER = 45;
    private static final double MATERIAL_PRO_TEILNEHMER = 15;

    private final int teilnehmer;
    private final boolean materialInklusive;

    public SchulungsService(String id, String name, double basisPreis, int teilnehmer, boolean materialInklusive) {
        super(id, name, basisPreis);
        if (teilnehmer <= 0) {
            throw new IllegalArgumentException("Die Teilnehmerzahl muss positiv sein.");
        }
        this.teilnehmer = teilnehmer;
        this.materialInklusive = materialInklusive;
    }

    @Override
    public double berechnePreis() {
        double preisProTeilnehmer = PREIS_PRO_TEILNEHMER;
        if (materialInklusive) {
            preisProTeilnehmer += MATERIAL_PRO_TEILNEHMER;
        }
        return getBasisPreis() + teilnehmer * preisProTeilnehmer;
    }

    @Override
    protected String details() {
        return "Schulung: " + teilnehmer + " TN, Material " + (materialInklusive ? "inklusive" : "exklusive");
    }
}
