# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Ylli Gashi, s364574, s364574@oslomet.no


# Oppgavebeskrivelse

I oppgave 1 så fikset jeg på legginn metoden. først trengte vi å ha rot til en node. Når vi legger inn en ny node så sammanligner vi foreldre noden med den nye veriden om verdien til den nye noden (barnet) er større så blir den høyre-barn ellers så blir den venstre-barn. om det ikke er noen node fra før så blir nye noden rotnoden. om det er eksisterende foreldre node så blir noden barnet til noden og barnet får koblingen med sin forelder ved q.venstre.forelder = q; når alt er gjort så økes antallet og returner true. 

I oppgave 2 så fikset jeg på antall(T verdi). vi starter på rotnoden blir varabel p som skal søke gjennom treet og så lenge p ikke er null så skal den bevege seg rundt. Det sammenlignes hvor mange forekomster det finnes av verdien som søkes. om tallet er større enn rotnoden så bevegs den til høyre eller til venstre om verdien er mindre enn rotnoden. Om en node er lik verdien så har vi funnet en forekomst, men for å se om det finnes flere av verdien så brukes p=p.høyre hvis en node forekommer så blir den plassert som høyre-barn som er grunnen at p=p.høyre blir brukt. Når hele treet er søkt så returnerer antall forekomster.  

I oppgave 3 så fikset jeg 