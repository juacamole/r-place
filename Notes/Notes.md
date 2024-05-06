## BootCamp Dokumentation Abschlussprojekt

### Ziel

Mein Ziel ist es eine funktionale Website zu erstellen, die die Funktionen des Events
r/Place auf Reddit besitzt.
Diese Funktionen wären das Platzieren von einzelnen Pixeln mit
einem vorgegebenen Zeitabstand von ca. 15 Sekunden.

So ergibt sich mit mehreren Menschen auf einer einzelnen Leinwand ein grösseres,
oder sogar mehrere Bilder. Da die Leinwand nur begrenzt Platz hat entstehen
kleine "Kriege"
um die Pixel, da jede Person die Pixel von anderen Personen übermalen kann.

Aus Sicht der Planung gibt es also mehrere grosse Bereiche:

#### Nutzersystem mit Berechtigungen

wenn es mehrere Leute gibt, die auf die Leinwand zugreifen, muss es ein Nutzersystem
geben, in dem klare Rollen zur bearbeitung feststehen. So kann, zum Beispiel der
Administrator mehr Pixel platzieren und einfacher pixel entfernen als normale Benutzer.
Zusätzlich steht eine Bestenliste auf dem Plan, in der man sehen kann, welcher Benutzer,
die meisten Pixel platziert hat.

#### Login System

Um diese Nutzerdaten zu schützen, darf ein Login System mit E-Mail und Passwort nicht
fehlen. Dies ist allerdings eine der weniger priorisierten Funktionen, da dies sehr
Zeitaufwändig und schwer sein kann. Falls es mir allerdings reicht, werde ich ein E-Mail-Token-System erstellen, welches
mit Mehrfaktorauthentifizierung für absoluten Datenschutz sorgt.

#### Design und Mobile First

Ein sehr wichtiger Teil ist das Design des Frontends und die Zugänglichkeit von Geräten mit anderer Auflösung.
Das Design ist einer der wichtigsten Aspekte des Frontends, da es dem Benutzer direkt einen ersten Eindruck
verschafft. Darum kümmere ich mich so schnell, wie möglich um Mockups.
Mockups sind Skizzen des Designs, das verwendet wird.
Darin werden alle möglichen Buttons, Input-Felder und Formen, die verwendet werden
sollen, aufgezeichnet.

#### Backend und Requests

Um dem Frontend mehr Funktion zu verleihen, gibt es das Backend,
wo alle möglichen Berechnungen durchgeführt werden. Mit Hilfe von Spring kann
ich die beiden verknüpfen und per HTTP-Anfragen, wie Get, Put, Post und Delete,
auf Daten zugreifen. Dazu werde ich ein Modell skizzieren,
indem klar steht, welche Wege zu welchen Endpunkten leiten und welche Daten
preisgegeben werden.

#### Datenbank

Die Datenbank ist das Lager des ganzen. In ihr sind alle verwendeten Daten gespeichert.
Für mein Projekt muss diese allerdings nicht sonderlich gross sein,
da ich nur die Leinwand und die Nutzerdaten speichern muss.
Speziell weil sie so klein ist, ist es gut, sie geordnet zu haben,
um die wartung einfacher zu gestalten.
Um diese Datenbank effizient und einfach gestalten zu können, werde ich ein ERM (Entity Relationship Model) erstellen,
welches die Beziehungen zwischen Tabellen und Daten zeigt.

#### Tests

Um mein Projekt sowohl Benutzerfreundlich als auch Bearbeiter freundlich zu gestalten,
werde ich Unit-tests und Integrationstests verwenden, welche
bei jeder änderung des Codes, das Programm auf Fehler Testen und
überprüfen, ob die wichtigsten Funktionen noch Funktionieren.
Da wir in unserer Theorie keine Frontend-Tests angeschaut haben, widme ich mich
am Anfang nur den Backendtests, wo ich hauptsächlich mit Mockito,
Integrationstests erstellen werde.
Integrationstests überprüfen das Programm, indem sie eine Situation Simulieren,
dies wird auch als Mocking bezeichnet (to Mock = etwas nachmachen).
So kann mit einem Test das ganze Backend und auch die Datenbank getestet werden.

#### Bonus

Da ich mir sehr viele Gedanken mache, wie ich mein Programm
verbessern könnte, habe ich schon einige Ideen gesammelt, die
Zeitaufwändig sind und deshalb nur vielleicht integriert werden.

### Alternativen

Um sicherzugehen, dass ich ein funktionales Produkt erhalte, Plane ich Alternativen für Funktionen, die ich
möglicherweise nicht fertigstellen kann.

#### Login System

Falls ich die Funktion per JWT Token mit Nutzer generierung und RBAC (Role Based Access Control) nicht fertigstellen
kann, werde ich die Website zugänglich für alle Nutzer machen
und die Login und Register Seite nur für "Statistik zwecke", oder gar nicht brauchen.

#### Mobile First

Ich möchte Mobile First, Bootstrap verwenden, um die Website zugänglicher zu gestalten. Falls mir dies nicht Gelingen
sollte,
erstelle ich einfach eine Version für die standard Desktop grösse und belasse es dabei.

#### Tests

Ich möchte mit Integration und Unit-Tests mein Backend Testen, um die Nötigen funktionen, wie das speichern und abholen
des Canvas,
(Optional) das Generieren von Nutzern und Login/Logout/Register funktionen, wie auch die Websocket Session (Wenn
möglich) zu Testen.