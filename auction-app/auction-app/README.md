# Kompetenznachweis D2 – Auktions-App

Umsetzung der Auktions-App-Idee: eine Kette von Objekt-Referenzen
(`AuctionHouse -> Auction -> Bid -> Person`), die Aggregation, Komposition
und Delegation zeigt.

## Klassenübersicht

| Klasse         | Aufgabe                                                        |
|----------------|-----------------------------------------------------------------|
| `Person`       | Verkäufer oder Bieter (Name, E-Mail)                            |
| `Item`         | Auktionsgegenstand mit Startpreis                                |
| `Bid`          | Einzelnes Gebot einer Person mit Betrag und Zeitstempel          |
| `Auction`      | Eine Auktion für ein Item, verwaltet die Gebote                  |
| `AuctionHouse` | Verwaltet mehrere Auktionen, erzeugt und schliesst sie            |
| `Demo`         | Main-Klasse mit Testszenario (Konsolenausgabe)                   |

Ausführen: `javac src/*.java -d out && java -cp out Demo`

## Welche HAT-Beziehungen werden verwendet?

| Beziehung                  | Typ         | Begründung |
|-----------------------------|-------------|------------|
| `Auction` → `Item`, `Seller` | Aggregation | Werden dem Konstruktor von aussen übergeben, existieren unabhängig weiter (Person/Item leben ohne die Auktion problemlos weiter) |
| `Bid` → `Bidder` (Person)    | Aggregation | Gleicher Grund: der Bieter existiert unabhängig vom einzelnen Gebot |
| `Auction` → `Bid`            | Komposition | `Auction.placeBid()` erzeugt die `Bid`-Objekte selbst; ein `Bid` hat ohne "seine" Auktion keinen Sinn (`Bid`-Konstruktor ist deshalb package-private) |
| `AuctionHouse` → `Auction`   | Komposition | `AuctionHouse.createAuction()` erzeugt die `Auction`-Objekte selbst und verwaltet ihren Lebenszyklus |

Die Faustregel dahinter: Wird ein Objekt vom Container **selbst erzeugt**
und hat ausserhalb davon keinen eigenständigen Sinn, ist es Komposition
(starke Abhängigkeit). Wird nur eine **Referenz auf ein von aussen
übergebenes, unabhängig existierendes Objekt** gehalten, ist es Aggregation
(lose Kopplung).

## Wann werden welche Beziehungen eingesetzt? Szenarien

**Aggregation (`Auction`–`Person`, `Bid`–`Person`):** Eine Person meldet
sich einmal im System an und kann danach an beliebig vielen Auktionen
teilnehmen oder selbst mehrere Gegenstände verkaufen. Würde man die
Auktion löschen, bleibt die Person bestehen – genauso wie ein Notebook
physisch weiter existiert, auch wenn seine Auktion beendet wird. Deshalb
reicht hier eine lose Referenz statt einer festen Bindung.

**Komposition (`Auction`–`Bid`, `AuctionHouse`–`Auction`):** Ein einzelnes
Gebot ist nur im Kontext einer bestimmten Auktion sinnvoll – ein "Gebot an
sich", losgelöst von einer Auktion, gibt es im Modell nicht. Genauso
entstehen Auktionen ausschliesslich über das `AuctionHouse`; würde man das
Haus komplett auflösen, ergäben die einzelnen `Auction`-Objekte in diesem
Modell keinen Sinn mehr. Die Erzeugung liegt jeweils beim "Besitzer"
(`Auction` erzeugt `Bid`, `AuctionHouse` erzeugt `Auction`), was die
Komposition auch im Code sichtbar macht (package-private Konstruktoren).

## Wo wird delegiert?

- `AuctionHouse.placeBid(...)` sucht nur die passende Auktion und
  delegiert den eigentlichen Gebotsvorgang an `Auction.placeBid(...)`.
- `AuctionHouse.closeAuction(...)` delegiert analog an `Auction.close(...)`.
- `Auction.placeBid(...)` und `Auction.close(...)` delegieren die Frage
  "Wer bietet gerade am meisten?" an `getHighestBid()`.
- `Auction.close(...)` delegiert zusätzlich an `Bid.getBidder()`, um den
  Namen des Gewinners zu ermitteln.

So entsteht die Aufrufkette `AuctionHouse -> Auction -> Bid -> Person`:
Der Aufrufer (z.B. `Demo`) kennt nur das `AuctionHouse` und muss die
interne Struktur (dass es überhaupt `Bid`- und `Person`-Objekte gibt)
nicht kennen.
