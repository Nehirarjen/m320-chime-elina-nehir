# Q2 – JavaDoc

## Worum geht es?

Q2 verlangt, dass der Code mit einem Dokumentationswerkzeug (JavaDoc)
dokumentiert ist und die Kommentare den Code-Konventionen entsprechen. Aus den
Kommentaren im Code erzeugt `javadoc` automatisch HTML-Seiten. Dabei ist die
Frage wichtig, *was* man kommentiert und wann ein Kommentar überhaupt Sinn macht.

Grundlage ist der Code von Niveau 2 (Ordner `D2`, Auktions-App). Alle sechs
Klassen sind mit JavaDoc dokumentiert: `Auction`, `AuctionHouse`, `Bid`,
`Item`, `Person` und `Demo`.

## Zusammenfassung: Was genau wurde kommentiert?

Bei allen sechs Klassen wurde alles kommentiert, was von aussen sichtbar ist
(Klasse, Konstruktoren und Methoden), mit Zweck, Parametern, Rückgabewert,
Fehlerfällen und der jeweiligen HAT-Beziehung. Nicht kommentiert sind die
privaten Attribute und selbsterklärende Zeilen.

## JavaDoc erzeugen

Im Hauptordner in PowerShell:

```powershell
.\Q2\javadoc-erzeugen.ps1
```

Das Skript erzeugt die Doku in `Q2\docs`. Danach `Q2\docs\index.html` im
Browser öffnen. Ohne das Skript geht es so:

```powershell
javadoc -package -encoding UTF-8 -charset UTF-8 -docencoding UTF-8 -sourcepath D2\src -d Q2\docs (Get-ChildItem D2\src\*.java).FullName
```

`-package` zeigt auch package-private Teile (die Konstruktoren von `Auction`
und `Bid`). Die drei Encoding-Optionen sind nötig, sonst sind die Umlaute
kaputt. Das Skript macht zusätzlich den Menüpunkt "Class" klickbar, JavaDoc
verlinkt ihn von sich aus nicht.

## Was wurde kommentiert?

JavaDoc-Kommentare (`/** ... */`) stehen bei allem, was ein Aufrufer von
aussen sieht: Klasse, Konstruktoren und Methoden.

### Auction
| Stelle | Inhalt des Kommentars |
|--------|-----------------------|
| Klasse | Zweck, Regel für Gebote, HAT-Beziehungen (Aggregation, Komposition, Abhängigkeit) |
| Konstruktor | Package-private, nur `AuctionHouse` darf Auktionen erzeugen |
| `getId()`, `isClosed()` | Ein Satz und `@return` |
| `placeBid(...)` | `@param`, `@return` (true/false), `@throws IllegalStateException` |
| `getHighestBid()` | `@return`, leeres `Optional` ohne Gebote |
| `getBids()` | Es wird eine Kopie geliefert, warum |
| `close()` | Danach keine Gebote mehr, `@return` |

### AuctionHouse
| Stelle | Inhalt des Kommentars |
|--------|-----------------------|
| Klasse | Zweck, Komposition, Delegation |
| Konstruktor | `@param name` |
| `createAuction(...)` | `@param`, `@return`, Hinweis: ID wird nicht auf Eindeutigkeit geprüft |
| `findAuction(...)` | Private Hilfsmethode, `@throws IllegalArgumentException` |
| `placeBid(...)` | Delegation an `Auction.placeBid`, `@param`, `@return`, zwei `@throws` |
| `closeAuction(...)` | Delegation an `Auction.close`, `@throws` |
| `removeAuction(...)` | Auktion samt Geboten entfernen, `@return` |
| `getOpenAuctions()` | Neue Liste, nur offene Auktionen |

### Bid
| Stelle | Inhalt des Kommentars |
|--------|-----------------------|
| Klasse | Zweck, Aggregation (Bieter) und Komposition (Teil der Auktion) |
| Konstruktor | Package-private, nur `Auction` erzeugt Gebote, `@param` |
| `getBidder()`, `getAmount()` | Ein Satz und `@return` |

### Item
| Stelle | Inhalt des Kommentars |
|--------|-----------------------|
| Klasse | Zweck, Aggregation (existiert auch ohne Auktion) |
| Konstruktor | `@param` für alle vier Werte, Startpreis als Untergrenze für Gebote |
| `getId()`, `getTitle()`, `getDescription()`, `getStartingPrice()` | Ein Satz und `@return` |

### Person
| Stelle | Inhalt des Kommentars |
|--------|-----------------------|
| Klasse | Zweck, Aggregation (existiert unabhängig von Auktionen und Geboten) |
| Konstruktor | `@param` für ID, Name und E-Mail |
| `getId()`, `getName()`, `getEmail()` | Ein Satz und `@return` |

### Demo
| Stelle | Inhalt des Kommentars |
|--------|-----------------------|
| Klasse | Ablauf der Demo |
| privater Konstruktor | Warum er leer ist: `Demo` wird nur über `main` gestartet |
| `main(...)` | Startet die Demo, `@param args` |

Verwendete JavaDoc-Elemente: `@param`, `@return`, `@throws`, `@see`,
`{@link ...}` und `{@code ...}`.

Zusätzlich stehen im Code einzelne normale Kommentare (`//`), die erklären,
*warum* etwas so gelöst ist, z.B. `// Komposition: Haus erzeugt Auction selbst`.

## Was wurde bewusst nicht kommentiert?

- **Private Attribute** (`id`, `item`, `bids`, `closed`, ...): Der Name sagt
  schon alles, und JavaDoc zeigt sie nicht an.
- **`toString()`**: Erbt die Beschreibung von `Object`, es gibt nichts
  Besonderes zu sagen.
- **Zeilen wie `this.id = id;`**: Der Code ist selbsterklärend, ein Kommentar
  würde nur wiederholen, was dort steht.

## Wann macht ein Kommentar Sinn?

- **JavaDoc:** Wenn jemand die Klasse benutzt, ohne den Code zu lesen. Es
  beschreibt, *was* eine Methode verspricht: Parameter, Rückgabe, Fehler.
- **Kommentar im Code (`//`):** Wenn nicht klar ist, *warum* etwas so
  gelöst ist.
- **Kein Kommentar:** Wenn der Code selbst klar lesbar ist. Gute Namen
  ersetzen viele Kommentare.
