# Antworten zu den Fragen (Auftrag V1 Serie E)

## 2. Gemeinsame Eigenschaften erkennen

**1. Welche Nachteile entstehen, wenn diese Attribute in jeder Klasse separat programmiert werden?**
Der gleiche Code steht mehrfach da (Laptop, Smartphone, Monitor). Bei einer Änderung muss man es an mehreren Stellen anpassen. Das erhöht das Risiko für Fehler.

**2. Welche Attribute gehören in eine gemeinsame Superklasse?**
Produktname, Hersteller, Preis, Artikelnummer und anLager. Diese hat jedes Produkt.

**3. Welche Attribute dürfen nicht in die Superklasse verschoben werden?**
Die typspezifischen Attribute wie RAM, Speicherplatz, Displaygrösse, Dual-SIM, Bildschirmdiagonale und Auflösung. Diese gibt es nicht bei jedem Produkt.

## 5. Geerbte Methoden

**4. Warum kann ein Laptop die Methode verkaufen() verwenden?**
Weil Laptop von Produkt erbt. Alles, was public in Produkt ist, kann Laptop auch nutzen, auch ohne eigene Implementierung.

**5. Wo ist diese Methode implementiert?**
In der Superklasse `Produkt` (Datei `Produkt.java`).

**6. Was passiert mit anLager, wenn verkaufen() aufgerufen wird?**
`anLager` wird von `true` auf `false` gesetzt. Ist das Produkt schon verkauft (`anLager = false`), passiert nichts, es kommt nur eine Meldung.

## 6. Was geht nicht?

**7. Kompiliert der Code?**
Nein. `System.out.println(preis);` in `Laptop` führt zu einem Compile-Fehler.

**8. Warum ist der direkte Zugriff nicht möglich?**
`preis` ist in `Produkt` als `private` deklariert. Private Attribute sind nur innerhalb der eigenen Klasse sichtbar, auch Subklassen dürfen nicht direkt darauf zugreifen.

**9. Wie kann der Preis trotzdem sauber abgefragt werden?**
Über die öffentliche Methode `getPreis()` aus der Superklasse.

## 7. Ist jede Beziehung eine Vererbung?

**Kunde extends Produkt**
Nicht sinnvoll. Ein Kunde ist kein Produkt (keine IST-EIN-Beziehung). Ein Kunde kauft Produkte, er ist keines.

**Bestellung extends Produkt**
Nicht sinnvoll. Eine Bestellung besteht aus Produkten, ist aber selbst kein Produkt. Das ist eine HAT-Beziehung, keine IST-EIN-Beziehung.

**Hersteller extends Produkt**
Nicht sinnvoll. Ein Hersteller stellt Produkte her, ist aber selbst kein Produkt.

**Fazit:** Kunde, Bestellung und Hersteller sollten eigene, unabhängige Klassen sein und Produkte höchstens als Attribut referenzieren (z.B. eine Bestellung enthält eine Liste von Produkten).

## 8. Produktverwaltung

**Warum können Laptop-, Smartphone- und Monitor-Objekte in einer ArrayList\<Produkt\> gespeichert werden?**
Weil alle drei Klassen von `Produkt` erben. Jedes Laptop-, Smartphone- und Monitor-Objekt ist also auch ein `Produkt` (Polymorphie). Eine Liste vom Typ der Superklasse kann deshalb Objekte aller Subklassen aufnehmen.

## 9. Eine falsche Liste

**10. Welche Zeile verursacht einen Fehler?**
`laptops.add(phone);`

**11. Warum kann das Smartphone nicht hinzugefügt werden?**
Die Liste ist als `ArrayList<Laptop>` deklariert. Ein Smartphone ist kein Laptop, deshalb lehnt der Compiler es ab.

**12. Warum wäre ArrayList\<Produkt\> an dieser Stelle flexibler?**
Weil `Produkt` die gemeinsame Superklasse ist. Eine `ArrayList<Produkt>` kann Laptop-, Smartphone-, Monitor- und Drucker-Objekte gleichzeitig speichern, eine `ArrayList<Laptop>` nur Laptops.

## 11. Neue Subklasse hinzufügen

**Musste die Klasse Shop grundlegend verändert werden? Was zeigt das bezüglich der Erweiterbarkeit der Hierarchie?**
Nein. Es musste nur die neue Klasse `Drucker` erstellt und im `Main` der Liste hinzugefügt werden. `Shop` selbst (Suchen, Verkaufen, Hinzufügen, Entfernen) blieb unverändert, weil sie mit `Produkt` arbeitet statt mit einzelnen Produkttypen. Das zeigt, dass die Hierarchie gut erweiterbar ist: neue Produktarten lassen sich ergänzen, ohne bestehenden Code anzupassen.

## 12. Herausforderung – Grenzfall

**Welche Eigenschaften würden passen?**
Name, Preis und Artikelnummer passen, weil jedes Produkt im Shop diese Angaben hat.

**Welche geerbten Eigenschaften oder Methoden würden keinen Sinn ergeben?**
`anLager`, `verkaufen()` (im Sinn von Lagerbestand ändern) und `einlagern()` ergeben keinen Sinn, weil eine Garantieverlängerung kein physisches Produkt ist und nicht ein- oder ausgelagert werden kann.

**Ist die IST-EIN-Beziehung ausreichend, um hier automatisch Vererbung zu verwenden?**
Nein. Eine Garantieverlängerung teilt zwar ein paar Attribute mit Produkt, aber nicht das Verhalten. Nur gemeinsame Attribute reichen nicht für eine sinnvolle IST-EIN-Beziehung.

**Welche Probleme entstehen, wenn eine Subklasse geerbte Funktionen gar nicht sinnvoll verwenden kann?**
Die Subklasse erbt Methoden, die für sie falsch oder bedeutungslos sind (z.B. `einlagern()` bei einer Garantieverlängerung). Das kann zu falscher Nutzung, unnötigem Code zum Abfangen dieser Fälle oder verwirrenden Objekten führen.

**Beispiel aus dieser Aufgabe, bei dem Vererbung nicht sinnvoll ist:**
`Garantieverlaengerung extends Produkt` selbst ist der Grenzfall: gemeinsame Attribute ja, aber `anLager`/`verkaufen`/`einlagern` passen nicht. Genauso wie `Kunde extends Produkt` (siehe Aufgabe 7) wäre das eine Vererbung ohne echte IST-EIN-Beziehung beim Verhalten.
