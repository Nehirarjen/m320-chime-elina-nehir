# Q3 – Airport Management System mit HashMap

## Worum geht es?

Ein Konsolenprogramm liest `airports.csv` ein und speichert alle Flughäfen in
einer `HashMap<String, Airport>`. Der Flughafen-Code (z.B. `JFK`) ist der
Schlüssel, das `Airport`-Objekt der Wert. Teil A deckt Import, Map-Operationen,
Menü und Suchverlauf ab. Teil B zeigt Algorithmen, die über die gespeicherten
Objekte laufen: Suchen, Sortieren, Statistik.

## Klassen

| Klasse | Aufgabe |
|--------|---------|
| `Airport` | Ein Flughafen (Code, Name, optional Stadt und Staat). Unveränderlich (`record`) |
| `AirportRepository` | Besitzt die HashMap. Hinzufügen, suchen, ändern, entfernen |
| `CsvImporter` | Liest die CSV-Datei und füllt das Repository |
| `AirportAlgorithms` | Teil B: `findByCity`, `findByState`, `findByName`, Sortieren |
| `AirportStatistics` | Teil B: Statistiken |
| `SearchHistory` | Die letzten 10 Suchen (`ArrayDeque`) |
| `UserInterface` | Konsolenmenü, nur Ein- und Ausgabe |
| `AirportApp` | Startklasse, importiert und startet das Menü |

Daten (`Repository`), Algorithmen und Benutzeroberfläche sind getrennt. Die
HashMap ist privat, nach aussen gibt es nur die nötigen Methoden und
`getAll()` mit einer Kopie der Werte.

## Starten

Im Ordner `Q3\src` in PowerShell:

```powershell
javac -encoding UTF-8 *.java
java AirportApp
```

Die Datei `airports.csv` wird automatisch gefunden (`Q3`, Projektordner oder
`Q3\src`). In IntelliJ: `AirportApp` mit dem grünen Pfeil starten. Eine andere
Datei kann als Argument übergeben werden: `java AirportApp pfad\datei.csv`.

## Menü

1. Show all airports
2. Search airport by code
3. Add airport
4. Update airport
5. Remove airport
6. Search and filter airports (Stadt, Staat, Name)
7. Show airports sorted (nach Name, nach Stadt dann Name)
8. Show statistics
9. Show search history
0. Exit

## Entscheidungen

**Doppelte Codes:** Der bestehende Eintrag bleibt, der neue wird abgelehnt und
gemeldet (`putIfAbsent`). Begründung: Ein Code ist der eindeutige Schlüssel.
Beim Import könnte eine spätere Zeile ein Fehler oder eine Wiederholung sein,
beim Hinzufügen im Menü soll nichts versehentlich überschrieben werden. Zum
Ändern gibt es `updateAirport`.

**Ungültige Datensätze:** Sie brechen den Import nicht ab. Zu wenige Spalten,
ein Code ohne genau 3 Buchstaben oder ein leerer Name werden übersprungen und
mit Zeilennummer gemeldet.

**Stadt und Staat:** Die mitgelieferte `airports.csv` hat nur die Spalten
`AIRPORT CODE` und `AIRPORT`. Darum sind Stadt und Staat dort leer. Der Import
liest die Spalten `CITY` und `STATE` automatisch, falls eine CSV-Datei sie hat.
Ohne diese Daten melden Filter und Statistik das ausdrücklich ("no city
information in the data"), statt Werte zu erfinden. Der Auftrag erlaubt das
("depending on the fields available").

**Codes normalisieren:** Eingaben werden getrimmt und gross geschrieben, `jfk`
findet `JFK`. Leere Eingaben und Text statt Zahl stürzen das Programm nicht ab.

## Warum liefert die HashMap keine alphabetische Reihenfolge?

Eine HashMap legt Einträge anhand des Hash-Werts des Schlüssels in einer
Tabelle ab. Der Hash verteilt die Schlüssel gleichmässig, aber nicht nach ihrer
Reihenfolge, deshalb garantiert die Map keine Iterationsreihenfolge. Das ist
der Preis für den sehr schnellen Zugriff über den Schlüssel. Zum Sortieren
wird darum eine temporäre Liste aus den Werten erzeugt und sortiert
(`List.sort`), die HashMap bleibt unverändert.

## Algorithmen in eigenen Worten

**`findByName(searchTerm)`** (`AirportAlgorithms`)
- *Was wird durchlaufen?* Jeder gespeicherte Flughafen.
- *Welche Bedingung wird geprüft?* Ob der Name in Kleinbuchstaben den
  Suchbegriff in Kleinbuchstaben enthält. Ein leerer Begriff liefert nichts.
- *Was wird gesammelt?* Alle Treffer in einer Liste, sortiert nach Code.

**Doppelte Namen finden: `duplicateNames()`** (`AirportStatistics`)
- *Was wird durchlaufen?* Jeder Flughafen.
- *Welche Bedingung wird geprüft?* Jeder Flughafen kommt unter seinem Namen
  (klein geschrieben) in eine Gruppe. Danach zählen nur Gruppen mit mehr als
  einem Eintrag.
- *Was wird gesammelt?* Der Name und die zugehörigen Codes.

**Häufigste Stadt oder Staat: `mostFrequent`** (`AirportStatistics`)
- *Was wird durchlaufen?* Jeder Flughafen.
- *Welche Bedingung wird geprüft?* Der Feldwert wird in einer Hilfs-Map
  hochgezählt, leere Werte zählen nicht.
- *Was wird berechnet?* Der Wert mit dem grössten Zähler. Bei Gleichstand
  gewinnt der alphabetisch frühere.

Eigene Statistik: Anzahl Flughäfen pro Anfangsbuchstabe des Codes
(`countByFirstLetter`).

## Welche Collections kommen vor?

| Collection | Wo | Warum |
|------------|----|-------|
| `HashMap` | `AirportRepository` | Zugriff über den Code ohne Durchlaufen |
| `ArrayList` | Ergebnislisten, Sortieren | Sortierbare Liste der Werte |
| `ArrayDeque` | `SearchHistory` | Vorne einfügen, hinten entfernen: neueste zuerst, höchstens 10 |
| `TreeMap` | Statistik | Sortierte Ausgabe (Namen, Buchstaben) |

## Getestet

- Import der Original-Datei: 155 Flughäfen, nichts übersprungen, keine Duplikate.
- Import einer Testdatei mit doppeltem Code, zu kurzem Code, leerem Namen,
  fehlender Spalte und Komma in Anführungszeichen: alles korrekt behandelt.
- Suche `jfk` und `JFK`, unbekannter Code, leere Eingabe.
- Suchverlauf: neueste zuerst, keine direkte Wiederholung, höchstens 10.
- Eingabe-Ende (Ctrl+D) und fehlende Datei beenden bzw. starten sauber.
