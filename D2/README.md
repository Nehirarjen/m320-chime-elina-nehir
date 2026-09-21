# Kompetenznachweis D2 – Auktions-App

Eine Auktions-App mit einer Kette von Objekt-Referenzen
(`AuctionHouse -> Auction -> Bid -> Person`). Sie zeigt Aggregation,
Komposition und Delegation.

## Klassen

| Klasse         | Aufgabe                                             |
|----------------|-----------------------------------------------------|
| `Person`       | Verkäufer oder Bieter (Name, E-Mail)                |
| `Item`         | Auktionsgegenstand mit Startpreis                   |
| `Bid`          | Ein Gebot einer Person mit Betrag                   |
| `Auction`      | Eine Auktion für ein Item, verwaltet die Gebote     |
| `AuctionHouse` | Verwaltet mehrere Auktionen                         |
| `Demo`         | Main-Klasse mit Testszenario (Konsolenausgabe)      |

Ausführen: `javac src/*.java -d out && java -cp out Demo`

## Die HAT-Beziehungen: von lose bis stark

| Stufe | Bedeutung | Beispiel im Code |
|-------|-----------|------------------|
| Abhängigkeit | Objekt wird nur kurz benutzt (Parameter), nicht gespeichert | `Auction.placeBid(Person bidder, ...)` benutzt die Person nur für das Gebot |
| Assoziation | Objekt kennt ein anderes dauerhaft (Referenz als Attribut) | Grundlage von Aggregation und Komposition |
| Aggregation | Teil wird von aussen übergeben und lebt unabhängig weiter | `Auction` → `Item`, `Seller`; `Bid` → `Bidder` |
| Komposition | Ganzes erzeugt den Teil selbst, Teil hat allein keinen Sinn | `Auction` → `Bid`; `AuctionHouse` → `Auction` |

Im Code sichtbar:
- **Aggregation:** Der Konstruktor bekommt das Objekt übergeben
  (`Auction(String id, Item item, Person seller)`).
- **Komposition:** Das Ganze ruft selbst `new` auf (`new Bid(...)` in
  `Auction.placeBid`, `new Auction(...)` in `AuctionHouse.createAuction`).
  Die Konstruktoren von `Bid` und `Auction` sind package-private, damit
  sie nicht von aussen erzeugt werden.

Hinweis: Java erzwingt bei der Komposition keinen gemeinsamen Lebenszyklus.
Sie ist hier eine Design-Entscheidung: Das Haus erzeugt und verwaltet die
Auktionen, eine `Auction` wird nirgends sonst erzeugt.

## Wann wird was verwendet? Szenarien

**Aggregation:** Eine Person meldet sich einmal an und kann bei vielen
Auktionen bieten oder mehrere Dinge verkaufen. Wird eine Auktion gelöscht,
bleibt die Person bestehen. Ein Notebook existiert auch ohne Auktion.
Darum reicht eine lose Referenz.

**Komposition:** Ein Gebot gibt es nur innerhalb einer Auktion. Auktionen
entstehen nur im Auktionshaus. Wird das Haus aufgelöst, sind seine Auktionen
und deren Gebote nicht mehr nötig.

**Abhängigkeit:** Wenn ein Objekt nur für einen einzelnen Aufruf gebraucht
wird, z.B. eine `Person` beim Bieten, genügt ein Parameter. Es wird nichts
gespeichert.

## Wo wird delegiert?

Delegation heisst: Ein Objekt gibt einen Aufruf an ein anderes Objekt weiter.

- `AuctionHouse.placeBid(...)` sucht die Auktion und ruft `Auction.placeBid(...)` auf.
- `AuctionHouse.closeAuction(...)` sucht die Auktion und ruft `Auction.close(...)` auf.

`Demo` kennt nur das `AuctionHouse`. Dass es `Auction`, `Bid` und `Person`
dahinter gibt, muss `Demo` beim Bieten nicht wissen.

Zusätzlich zeigt `Auction.close()` die Objektkette: Es holt sich das
höchste `Bid` und darüber den Namen der `Person`
(`bid.getBidder().getName()`). Das ist eine Aufrufkette und keine
Delegation.
