import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Liest eine CSV-Datei und füllt das {@link AirportRepository}.
 * <p>
 * Die Spalten werden über die Kopfzeile gefunden: Pflicht sind eine Code-Spalte
 * ("AIRPORT CODE", "CODE" oder "IATA") und eine Name-Spalte ("AIRPORT" oder
 * "NAME"). Optional sind "CITY" und "STATE". Ungültige Zeilen brechen den
 * Import nicht ab, sie werden übersprungen und im Ergebnis gemeldet.
 */
public class CsvImporter {

    /** Ergebnis eines Imports mit Zählern und Meldungen zu übersprungenen Zeilen. */
    public record Result(int imported, int skipped, int duplicates, List<String> messages) {
    }

    private CsvImporter() {
    }

    /**
     * @throws IOException wenn die Datei nicht lesbar ist oder die Kopfzeile nicht passt
     */
    public static Result importFrom(Path file, AirportRepository repository) throws IOException {
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

        int headerIndex = 0;
        while (headerIndex < lines.size() && lines.get(headerIndex).isBlank()) {
            headerIndex++;
        }
        if (headerIndex == lines.size()) {
            throw new IOException("The file is empty.");
        }

        List<String> header = splitLine(lines.get(headerIndex).replace("﻿", ""));
        int codeColumn = columnOf(header, "AIRPORT CODE", "CODE", "IATA");
        int nameColumn = columnOf(header, "AIRPORT", "NAME");
        int cityColumn = columnOf(header, "CITY");
        int stateColumn = columnOf(header, "STATE");
        if (codeColumn < 0 || nameColumn < 0) {
            throw new IOException("The header needs an airport code column and an airport name column.");
        }

        int imported = 0;
        int skipped = 0;
        int duplicates = 0;
        List<String> messages = new ArrayList<>();

        for (int i = headerIndex + 1; i < lines.size(); i++) {
            if (lines.get(i).isBlank()) {
                continue;
            }
            int lineNumber = i + 1;
            List<String> fields = splitLine(lines.get(i));

            if (fields.size() <= Math.max(codeColumn, nameColumn)) {
                skipped++;
                messages.add("Line " + lineNumber + " skipped: too few columns.");
                continue;
            }
            try {
                Airport airport = new Airport(
                        fields.get(codeColumn),
                        fields.get(nameColumn),
                        valueAt(fields, cityColumn),
                        valueAt(fields, stateColumn));
                if (repository.addAirport(airport)) {
                    imported++;
                } else {
                    duplicates++;
                    messages.add("Line " + lineNumber + ": duplicate code " + airport.code()
                            + ", kept the first record, ignored '" + airport.name() + "'.");
                }
            } catch (IllegalArgumentException e) {
                skipped++;
                messages.add("Line " + lineNumber + " skipped: " + e.getMessage());
            }
        }
        return new Result(imported, skipped, duplicates, messages);
    }

    private static int columnOf(List<String> header, String... names) {
        for (int i = 0; i < header.size(); i++) {
            for (String name : names) {
                if (header.get(i).equalsIgnoreCase(name)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static String valueAt(List<String> fields, int column) {
        return column >= 0 && column < fields.size() ? fields.get(column) : "";
    }

    /** Teilt eine Zeile an Kommas auf. Kommas in Anführungszeichen trennen nicht. */
    static List<String> splitLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                fields.add(current.toString().trim());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString().trim());
        return fields;
    }
}
