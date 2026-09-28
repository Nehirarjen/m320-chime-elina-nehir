# Q2 – JavaDoc

Grundlage ist der Code von Niveau 2 (Ordner `D2`, Auktions-App). Kommentiert
und dokumentiert sind die Klassen **`Auction`** und **`AuctionHouse`**.

## JavaDoc erzeugen

Im Hauptordner in PowerShell:

```powershell
.\Q2\javadoc-erzeugen.ps1
```

Das Skript erzeugt die Doku in `Q2\docs`. Danach `Q2\docs\index.html` im
Browser öffnen. Ohne das Skript geht es so:

```powershell
javadoc -package -encoding UTF-8 -charset UTF-8 -docencoding UTF-8 -sourcepath D2\src -d Q2\docs D2\src\Auction.java D2\src\AuctionHouse.java
```

`-package` zeigt auch package-private Teile (der Konstruktor von `Auction`).
Die drei Encoding-Optionen sind nötig, sonst sind die Umlaute kaputt.
Das Skript macht zusätzlich den Menüpunkt "Class" klickbar. JavaDoc
verlinkt ihn von sich aus nicht.

## Was wurde kommentiert?

JavaDoc-Kommentare (`/** ... */`) stehen bei allem, was ein Aufrufer von
aussen sieht: Klasse, Konstruktor und Methoden.

| Stelle | Inhalt des Kommentars |
|--------|-----------------------|
| Klasse `Auction` | Zweck, Regel für Gebote, HAT-Beziehungen (Aggregation, Komposition, Abhängigkeit) |
| Konstruktor `Auction` | Package-private, nur `AuctionHouse` darf Auktionen erzeugen |
| `Auction.getId()`, `isClosed()` | Ein Satz und `@return` |
| `Auction.placeBid(...)` | `@param`, `@return` (true/false), `@throws IllegalStateException` |
| `Auction.getHighestBid()` | `@return`, leeres `Optional` ohne Gebote |
| `Auction.getBids()` | Es wird eine Kopie geliefert, warum |
| `Auction.close()` | Danach keine Gebote mehr, `@return` |
| Klasse `AuctionHouse` | Zweck, Komposition, Delegation |
| Konstruktor `AuctionHouse` | `@param name` |
| `AuctionHouse.createAuction(...)` | `@param`, `@return`, Hinweis: ID wird nicht auf Eindeutigkeit geprüft |
| `AuctionHouse.findAuction(...)` | Private Hilfsmethode, `@throws IllegalArgumentException` |
| `AuctionHouse.placeBid(...)` | Delegation an `Auction.placeBid`, `@param`, `@return`, zwei `@throws` |
| `AuctionHouse.closeAuction(...)` | Delegation an `Auction.close`, `@throws` |
| `AuctionHouse.removeAuction(...)` | Auktion samt Geboten entfernen, `@return` |
| `AuctionHouse.getOpenAuctions()` | Neue Liste, nur offene Auktionen |

Verwendete JavaDoc-Elemente: `@param`, `@return`, `@throws`, `@see`,
`{@link ...}` und `{@code ...}`.

## Was wurde bewusst nicht kommentiert?

- **Private Attribute** (`id`, `item`, `bids`, `closed`, ...): Der Name sagt
  schon alles, und JavaDoc zeigt sie nicht an.
- **`toString()`**: Erbt die Beschreibung von `Object`, es gibt nichts
  Besonderes zu sagen.
- **Zeilen wie `this.id = id;`**: Der Code ist selbsterklärend, ein Kommentar
  würde nur wiederholen, was dort steht.
- **Die anderen Klassen** (`Bid`, `Item`, `Person`, `Demo`): Sie sind für Q2
  nicht dokumentiert. Bei `Item` und `Person` erscheinen sie in den Verweisen
  deshalb als normaler Text und nicht als Link.

## Wann macht ein Kommentar Sinn?

- **JavaDoc:** Wenn jemand die Klasse benutzt, ohne den Code zu lesen. Es
  beschreibt, *was* eine Methode verspricht: Parameter, Rückgabe, Fehler.
- **Kommentar im Code (`//`):** Wenn nicht klar ist, *warum* etwas so
  gelöst ist, z.B. `// Komposition: Haus erzeugt Auction selbst`.
- **Kein Kommentar:** Wenn der Code selbst klar lesbar ist. Gute Namen
  ersetzen viele Kommentare.
