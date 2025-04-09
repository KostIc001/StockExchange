#Stock Exchange#

#Opis Projekta
Projekat implementira backend sistem za jednostavno obrađivanje i skladištenje naloga na berzi za kupovinu i prodaju hartija od vrednosti. Sistem koristi REST API i WebSocket komunikaciju za interakciju sa korisnicima i pružanje real-time informacija o statusu tržišta.

Ključne Funkcionalnosti
Postavljanje Naloga: Korisnici mogu slati naloge za kupovinu (BUY) ili prodaju (SELL) određenih akcija. Nalozi se automatski uparuju ukoliko postoje odgovarajući parovi po ceni.

Praćenje Tržišta: Korisnici mogu dobiti pregled top 10 naloga za kupovinu i prodaju preko REST API-ja, što im omogućava da prate trenutne najbolje ponude i potražnje na tržištu.

Real-time Ažuriranja: Kroz WebSocket komunikaciju, korisnici se mogu pretplatiti da primaju obaveštenja o izvršenim transakcijama i promenama u Order Book-u u realnom vremenu, što omogućava aktivno učestvovanje u tržišnim dešavanjima.

Upravljanje Korisničkim Sredstvima: Sistem omogućava upravljanje bilansom i akcijama korisnika, podržavajući operacije kao što su transfer akcija i ažuriranje bilansa korisnika.

#Tehnička Implementacija
Sistem koristi dva prioriteta reda (priority queues) za organizaciju kupovnih i prodajnih naloga, optimizujući proces uparivanja. Nalozi se sortiraju po ceni, sa najvišim cenama za kupovinu i najnižim za prodaju, osiguravajući efikasno i brzo uparivanje.

Projekat ne koristi bazu podataka, umesto toga, korisnički podaci su zakucani u aplikaciji za demonstraciju, što olakšava pokretanje i testiranje sistema bez potrebe za eksternim zavisnostima.

#Instalacija
Projekat koristi Maven kao alat za upravljanje projektom, što omogućava jednostavnu konfiguraciju i izgradnju.

Preduslovi:
Pre nego što pokrenete projekat, uverite se da su sledeći uslovi ispunjeni:

    Java JDK 11 ili novija
    Maven 3.6 ili noviji

#Korišćenje API-a
Postavljanje nove narudžbine
Request:
POST /api/orders
Content-Type: application/json

        {
            "userId": 1,
            "amount": 10,
            "price": 50.0,
            "type": "BUY",
            "share": "Apple"
        }
        Ovaj zahtev stvara nalog za kupovinu 100 akcija kompanije Apple po ceni od $50 po akciji.

        Response:
        200 OK
        Content-Type: application/json

        {
            "message": "Order placed successfully"
        }
    Dobijanje top 10 kupovnih naloga
        Request:
        GET /api/orders/top-buy


        200 OK

Content-Type: application/json

        Response:
        [
            {
                "userId": 1,
                "amount": 10,
                "price": 55.0,
                "type": "BUY",
                "share": "Apple"
            },
            ...
        ]
        Ovaj zahtev vraća listu top 10 naloga za kupovinu sortiranih po ceni, od najviše ka najnižoj.

#WebSocket Komunikacija
Da biste pratili real-time promene u Order Book-u, možete se povezati preko WebSocket-a koristeći URL ws://localhost:8080/ws i pretplatiti se na kanal /topic/orderbook.  
Konkretno je korišćen ovaj tester: https://piehost.com/websocket-tester
1.ws://localhost:8080/ws //connect
2.{ "destination": "/topic/orderbook" } //send
3.SUBSCRIBE // send
id:sub-0
destination:/topic/trade

        ^@

#Testiranje
Projekat sadrži skup automatizovanih testova koji proveravaju različite aspekte aplikacije, uključujući funkcionalnosti API-ja i interne servisne operacije. Testovi su implementirani koristeći JUnit i Spring Boot Test.
Pokretanje Testova
Da biste pokrenuli testove, koristite Maven. Maven omogućava jednostavno pokretanje testova kroz komandnu liniju. Evo kako to možete uraditi:

Otvorite terminal:
Navigirajte do root direktorijuma vašeg projekta u terminalu.
Izvršite sledeću Maven komandu: mvn test

Kostić Stefan
kontakt: kostic.stefan001@gmail.com
