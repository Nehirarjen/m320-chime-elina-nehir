# V3 – Aktien-Portfolio mit Interface

## Worum geht es?

Ein Portfolio enthält Aktien. Um seinen Wert zu berechnen, braucht es die
Preise einer Börse. Es gibt mehrere Börsen (Zürich, London, New York), und an
jeder sind andere Aktien kotiert (registriert) und andere Preise gültig.

Das Portfolio soll nicht wissen, welche Börse es benutzt. Darum arbeitet es
nur mit dem **Interface `StockExchange`**.

## Klassen

| Klasse | Aufgabe |
|--------|---------|
| `StockExchange` | **Interface.** Legt fest, was jede Börse können muss |
| `ZurichExchange`, `LondonExchange`, `NewYorkExchange` | Die drei Börsen. Jede setzt das Interface anders um |
| `Stock` | Eine Aktie (Symbol und Name) |
| `Position` | Eine Aktie mit Stückzahl |
| `Portfolio` | Liste von Positionen, berechnet den Wert mit irgendeiner Börse |
| `PortfolioApp` | Konsolenmenü zum Ausprobieren |

```
              Portfolio ---- benutzt ----> «interface» StockExchange
                                                  ▲
                                 ┌────────────────┼────────────────┐
                          ZurichExchange   LondonExchange   NewYorkExchange
```

## Das Interface

```java
public interface StockExchange {
    String getName();
    String getCurrency();
    Set<String> getSymbols();
    boolean isListed(String symbol);
    double getPrice(String symbol);
}
```

Ein Interface enthält nur die Methoden-Köpfe, keinen Code. Jede Börse schreibt
mit `implements StockExchange` den Code dazu.

## Wie zeigt das die Lernziele?

**1. Interface erkennen und umsetzen**
Die Beschreibung sagt: "Das Portfolio muss nicht wissen, welche Börse
aufgerufen wird." Das ist der Hinweis auf ein Interface. `StockExchange` ist
dieses Interface, die drei Börsen setzen es um.

**2. Der Code wird flexibler**
`Portfolio` ruft nur `exchange.getPrice(...)` auf und hängt an keiner
bestimmten Börse. Kommt eine neue Börse dazu (z.B. Tokio), schreibt man eine
neue Klasse mit `implements StockExchange` und trägt sie in `PortfolioApp` in
die Liste ein. Die Klasse `Portfolio` muss dafür **nicht geändert** werden.

**3. Der Code wird polymorph**
In `Portfolio.getValue(...)` steht immer derselbe Aufruf:

```java
sum += p.getQuantity() * exchange.getPrice(symbol);
```

Welche `getPrice`-Methode läuft, hängt davon ab, welches Objekt übergeben
wurde. Bei `ZurichExchange` kommt ein CHF-Preis, bei `NewYorkExchange` ein
USD-Preis. Die Variable hat den Typ `StockExchange`, das Verhalten kommt vom
tatsächlichen Objekt. Das ist Polymorphismus über ein Interface.

## Interface statt Vererbung

Man könnte die Börsen auch über Vererbung lösen: eine Oberklasse `Boerse`,
von der `ZurichExchange`, `LondonExchange` und `NewYorkExchange` erben. Das
Interface ist hier die bessere Wahl:

| | Vererbung (`extends`) | Interface (`implements`) |
|---|---|---|
| Wie viele davon? | Eine Klasse kann nur von **einer** Oberklasse erben | Eine Klasse kann **mehrere** Interfaces umsetzen |
| Was wird festgelegt? | Zustand und Code werden geerbt, die Klassen sind eng verbunden | Nur der Vertrag (welche Methoden), jede Klasse schreibt ihren Code selbst |
| Neue Börse | Muss in die Hierarchie passen und erbt auch Dinge, die sie nicht braucht | Muss nur die fünf Methoden liefern und kann sonst von irgendwo erben |
| `Portfolio` | Wäre an die Oberklasse `Boerse` gebunden | Kennt nur `StockExchange` und bleibt unverändert |

Beispiel: Eine Börse, die ihre Preise aus einer Datei oder einem Webdienst
lädt, könnte schon von einer anderen Klasse erben. Mit Vererbung wäre sie
blockiert, mit dem Interface funktioniert sie trotzdem.

**Faustregel:** Vererbung passt, wenn etwas *ist* ein Spezialfall einer
anderen Klasse (ein Auto ist ein Fahrzeug). Ein Interface passt, wenn Klassen
dasselbe *können* sollen, aber sonst nichts gemeinsam haben müssen (jede
Börse kann Preise liefern).

## Wo im Code?

| Was | Wo |
|-----|----|
| Interface | `StockExchange.java` |
| Interface umgesetzt | `ZurichExchange.java`, `LondonExchange.java`, `NewYorkExchange.java` (`implements StockExchange`) |
| Interface als Parameter | `Portfolio.getValue(StockExchange exchange)` |
| Polymorpher Aufruf | `Portfolio.java`, Zeile mit `exchange.getPrice(symbol)` |
| Liste verschiedener Börsen unter einem Typ | `PortfolioApp.java`: `List<StockExchange> exchanges` |
| Börse wechseln zur Laufzeit | `PortfolioApp.java`: Variable `current` vom Typ `StockExchange` |

## HAT-Beziehungen

- `Portfolio` → `Position`: Komposition. Das Portfolio erzeugt seine
  Positionen selbst (`new Position` in `add`).
- `Position` → `Stock`: Aggregation. Die Aktie wird von aussen übergeben.
- `Portfolio` → `StockExchange`: Abhängigkeit. Die Börse wird nur als
  Parameter benutzt und nicht gespeichert.

## Menü

1. Börse wählen
2. Wert des Portfolios anzeigen (Aktien, die an der Börse nicht kotiert sind, werden nicht mitgezählt und angezeigt)
3. Kurs einer Aktie abfragen
4. Portfolio anzeigen
5. Beenden

Beispiel mit dem eingebauten Portfolio (10 Nestlé, 20 ABB, 5 Novartis,
15 Shell, 8 Apple):

| Börse | Wert | Warum |
|-------|------|-------|
| Zürich | 2331.50 CHF | Nestlé, ABB, Novartis sind kotiert |
| London | 396.00 GBP | Nur Shell ist kotiert |
| New York | 2292.50 USD | Nur Novartis und Apple sind kotiert |

Die Preise sind erfundene Beispielwerte. Das Symbol ist in dieser Simulation
für jede Aktie gleich, z.B. `NOVN` in Zürich und New York.

## Starten

Im Ordner `V3\src` in PowerShell:

```powershell
javac -encoding UTF-8 *.java
java PortfolioApp
```

Falls `*.java` nicht erkannt wird, in IntelliJ `PortfolioApp` mit dem grünen
Pfeil starten.
