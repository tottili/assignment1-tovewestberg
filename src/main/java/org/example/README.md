Hej!

Lösningen ska beskrivas kort, men det känns nästan omöjligt att hålla det kortfattat
när projektet har vuxit så mycket på en under arbetets gång.

Main-metoden är faktiskt bara en rad kod, den som kör Bibliotekshanteraren. Jag valde
att behålla det så eftersom jag tyckte det var tydligare att menyn kom högre upp i klassen
och sen switch-satsen som kopplade ihop menyn med alla metoder. Därefter har jag försökt
att lägga metoderna i den ordning som de dyker upp i menyn, men det förekommer hjälpmetoder
som skrivits i kronologisk ordning.

lendBook() är en väldigt lång metod, jag funderade på att koppla det med findBook() men
mitt lösningsalternativ var då att skicka användaren direkt från knappval 3 till 5. Hade
användaren velat söka upp en bok hade de valt knappval 5 från början. Dessutom
tycker jag att ISBN är en mer "säker" identifierare för att koppla ett lån på än den
begränsade söknings-funktion som är byggd.

Det är en klass mer än vad som specificerades i uppgiften, för att kunna få ihop ett
låneregister som kombinerar egenskaperna med Member och Book. Det stod mellan att skapa
Loan som en record eller en klass, där jag valde klass eftersom att fälten i en record
är immutable. Då tänkte jag att jag skulle stöta på problem längre fram ifall man behöver 
ändra en låntagares namn under tiden de har ett aktivt lån. Med tanke på uppgiftens scope
kanske en record hade varit smidigare då det inte krävs att funktionaliteten "ändra låntagares
efternamn" testas, men jag har gjort designval baserat på hur jag tänker programmet ska
appliceras i verkligheten.

Det finns tydliga begränsningar i mitt program: varken ISBN eller personnummer kontrolleras
för dubbletter. findBook() slutar söka vid första träffen så läggs en bokserie till av samma
författare kommer man bara få träff vid första matchningen. Samma princip med att skriva ut
låntagaren med flest lån, ifall flera låntagare har samma antal lån visar sökresultatet den
första som hittas i arrayen. Detta går att jobba vidare på.

Collections Framework: Hade det varit tillåtet i denna uppgift hade antalet rader kod decimerats.
Jag har 3st arrayer som hanteras med räknare och måste kontrolleras så de inte är fulla innan
nya element läggs till. En ArrayList hade löst det helt själv (alltså växt dynamiskt, samt att 
borttagning av element i returnBook() hade en ArrayList också hanterat att undvika hål och kanske
i förlängningen nullPointerException eller indexOutOfBounds. Abstraktionsnivån hade höjts,
men nu har uppgiften varit mer fostrande och gett en bättre förståelse för hur programmet körs samt
hur operander, funktioner och allt anropas i Java.