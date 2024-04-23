## BootCamp Dokumentation Abschlussprojekt

### Ziel

Mein Ziel ist es eine Funktionale Website zu erstellen, die die Funktionen des Events 
r/Place auf Reddit besitzt.
Diese Funktionen wären das Plazieren von einzelnen Pixeln mit
einem vorgegebenen Zeitabstand von ca. 15 Sekunden. 

So Ergibt sich mit mehreren Menschen auf einer einzelnen Leinwand ein grösseres,
oder sogar mehrere Bilder. Da die Leinwand nur begrenzt Platz hat entstehen
kleine "Kriege" 
um die Pixel, da jede Person die Pixel von anderen Personen übermalen kann.

Aus Sicht der Planung gibt es also mehrere grosse Brereiche:

#### Nutzersystem mit Berechtigungen

wenn es mehrere Leute gibt, die auf die Leinwand zugreifen, muss es ein Nutzersystem 
geben, indem klare Rollen zur bearbeitung feststehen. So kann zum beispiel der 
Administrator mehr Pixel plazieren und einfacher pixel entfernen als normale Benutzer.
Zusäzlich steht eine Bestenliste auf dem Plan, wo man sehen kann, welcher Benutzer,
die meisten Pixel plaziert hat.

#### Login System

Um diese Nutzerdaten zu schützen, darf ein Login System mit E-Mail und Passwort nicht 
fehlen. Dies ist allerdings eine der weniger Priorisierten Funktionen, da dies sehr 
Zeitaufwändig und schwer sein kann. Falls es mir allerdings reicht, werde ich ein E-Mail Token System erstellen, welches
mit Mehrfaktorauthentifizierung für absoluten Datenschutz sorgt.

#### Design und Mobile First

Ein sehr wichtiger Teil ist das Design des Frontends und die Axessibilität von Geräten mit anderer Auflösung.
Das Design ist einer der Wichtigsten Aspekte des Frontends, da es dem Benutzer direkt einen ersten Eindruch 
verschafft. Darum kümmere ich mich so schnell, wie möglich um Mockups.
Mockups sind Skizzen des Designs, das verwendet wird. 
Darin werden alle Möglichen Buttons, Input-Felder und Formen, die verwendet werden 
sollen, aufgezeichnet.

#### Backend und Requests

Um dem Frontend mehr Funktion zu verleien, gibt es das Backend,
wo alle möglichen berechnungen vollendet werden. Mit hilfe von Spring kann
ich die beiden verknüpfen und per HTTP-Anfragen, wie Get, Put, Post und Delete, 
auf Daten zugreifen. Dazu werde ich ein Modell skizzieren,
indem klar steht, welche Wege zu welchen Endpunkten leiten und Welche Daten
so preisgegeben werden.

#### Datenbank

Die Datenbank ist das Lager des ganzen. In ihr sind alle verwendeten Daten gespeichert.
Für mein Projekt muss diese allerdings nicht sonderlich gross sein,
da ich nur die Leinwand und die Nutzerdaten speichern muss. 
Speziell weil sie so klein ist, ist es gut, sie geordnet zu haben, 
um die wartung einfacher zu gestalten.
Um diese Datenbank effizient und einfach gestalten zu können, werde ich ein ERM (Entity Relationship Model) erstellen,
welches die Beziehungen zwischen Tabellen und Daten zeigt.

#### Tests

Um mein Projekt sowohl Nutzerfreundlich, als auch Bearbeiterfreundlich zu gestalten,
werde ich Uni-Tests und Integration-Tests verwenden, welche 
bei jeder änderung des Codes, das Programm auf Fehler Testen und 
überprüfen, ob die wichtigsten Funktionen noch Funktionieren.
Da wir in unserer Theorie keine Frontend-Tests angeschaut haben, widme ich mich
am Anfang nur den Backend Tests, wo ich hauptsätzlich mit Mockito, 
Integration-Tests erstellen werde.
Integration-Tests überprüfen das Programm, indem sie eine Situation Simulieren, 
deshalb nennt man es auch Mocking (to Mock = etwas nachmachen).
So kann mit einem Test das ganze Backend, sowohl auch die Datenbank getestet werden.

#### Bonus

Da ich mir sehr viele Gedanken mache, wie ich mein Programm 
verbessern könnte, habe ich schon einige Ideen gesammelt, die
Zeitaufwändig sind und deshalb nur vielleicht integriert werden.