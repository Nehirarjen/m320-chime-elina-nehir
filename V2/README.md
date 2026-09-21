# V3 – Service-Verwaltung

Konsolenprogramm zur Übung von **Vererbung**, **abstrakten Klassen**, **Methodenüberschreibung**, **Polymorphismus** und **Exception Handling**.

## Klassenstruktur

```
                        Service (abstrakt)
                   /          |           \
    BeratungsService  SchulungsService  WartungsService
                         
                    ServiceVerwaltung
                         |
                  List<Service>
```

`ServiceVerwaltung` speichert alle Unterklassen gemeinsam als `Service`-Objekte.
Beim Aufruf von `berechnePreis()` wird dennoch je nach tatsächlichem Typ die
überschriebene Methode ausgeführt. Das ist Polymorphismus.

## Preisregeln (eigene, dokumentierte Annahmen)

| Service | Preis |
|---|---|
| Beratung | `basisPreis + stunden × Stundensatz`; Junior CHF 80, Professional CHF 120, Expert CHF 160 |
| Schulung | `basisPreis + teilnehmer × CHF 45`; mit Material zusätzlich CHF 15 pro Teilnehmer |
| Wartung | `basisPreis + geraeteAnzahl × CHF 35`; Express mit 25 % Zuschlag |

## Besondere Regel

Ein Wartungsservice kann nur vor seiner Startzeit storniert werden. Eine
Stornierung eines bereits gestarteten Wartungsservices löst eine
`ServiceException` aus.

## Starten

Im Ordner `src`:

```powershell
javac *.java
java ServiceApp
```

Das Menü ermöglicht Services zu erstellen, aufzulisten, zu suchen, zu buchen,
zu stornieren, zu löschen und den Umsatz anzuzeigen.
