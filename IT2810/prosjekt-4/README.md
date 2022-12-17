# MovieMix React Native App

Prosjekt 4 i IT2810 - Webutvikling | Høst 2022 | NTNU | Individuell Innlevering

## Innhold

For å se koden til prosjektet klikk [her](moviemix).

## Dokumentasjon

En mobil applikasjonen som lar deg søke/filtrere etter filmer. Du kan også lagre anmeldelser og se detaljer om filmene.

### Universell utforming

<!-- Mye av det samme som forrige prosjekt -->

- Har prøvd å legge inn aria-labels på alle interaktive elementer i applikasjonen.
- Knappene er store (i følge mobil standard) og lett å se (et lite punkt angående trending og rating under notater).
- Har latt meg inspirere av denne NRK [artikkelen](https://www.nrk.no/dokumentar/xl/shakeel-muzaffar-er-tiltalt-for-svindel-og-er-internasjonalt-etterlyst.-nrk-fant-ham.-1.16159860) med tanke på design av applikasjonen. Lener meg litt på at NRK har tenkt på fargeblindhet og fargekontraster.

### Bærekraftig

<!-- Mye av det samme som forrige prosjekt -->

- Lagt inn infinte scrolling og laster bare nye filmer ved å scrolle nedover på hovedsiden. Dette er for
  å begrense antall filmer returnert hver gang. Her blir det også tatt i bruk caching av forrige resultater.
  Bildene er lagret i databasen som lenker for å redusere plass.
- For det meste tatt i bruk mørk modus, som vil være med å spare en minimal mengde strøm.
  react-native-paper vil følge enheten sine systeminstillinger og vil skifte til lys modus hvis enheten
  også er det.

### Tekniske krav, teknologier og -stack

<!-- Mye av det samme som forrige prosjekt -->

Tar i bruk GRAND-stacken. Fra forrige prosjekt ble det satt opp en database i neo4j som også blir
brukt i dette prosjektet også. I frontend blir det brukt Apollo Client som gjør det lett å kommunisere
med backend (Apollo Server). For state management blir det brukt Apollo Client sin state management.

For å simulerer brukere har jeg brukt [AsyncStorage](https://react-native-async-storage.github.io/async-storage/docs/usage). Dette
fungerer svært likt som localStorage og som tilbød det jeg trengte for denne applikasjonen.

Har også tatt i bruk [react-native-paper](https://reactnativepaper.com) som er et bibliotek som tilbyr veldig mye. Har brukt en
del herfra i applikasjonen og som passet til det jeg trengte.

### Testing

Applikasjonen har bare blitt testet på en iPhone 11 som har fungert bra.
Forholder meg til at React Native er plattformuavhengig, og at applikasjonen derfor skal fungere på Android også.

### Notater

Siden jeg var alene på prosjekt 4 tenkte jeg det var smart å initialisere prosjektet med expo typescript template.
Det viste seg nesten å ha den motsatte effekten siden det var mange filer jeg ikke trengte og mye kompleksitet som
jeg synes var unødvendig. Selv om dette tok opp litt for mye tid (å fikse alle filene), kom det med noe veldig grei template for
navigasjon. Jeg har dermed gjenbrukt en del kode som kom med expo template. Filene dette gjelder er [types.tsx](moviemix/types.tsx),
[index.tsx](moviemix/navigation/index.tsx) og [LinkingConfiguration.ts](moviemix/navigation/LinkingConfiguration.ts).

Jeg har ikke brukt GitLab noe særlig i dette prosjektet, hovedsaklig av den grunn til at jeg var alene. Håper
dokumentasjonen og kommentarene i kodebasen er forståelig nok!

Det er noen ting jeg kunne ønsket å forbedre eller valgt å ikke bruke tiden på:

- Sette opp en skikkelig splashscreen.
- Sette opp brukeren på en mer fornuftig måte.
- Komme med en bekreftelse på at anmeldelsen er lagret.
- Se mer på horisontal view.
- Gjort at Trending og Rating er enda tydeligere togglebuttons.
- Bedre skalarbilitet på YourRatings siden.
- Gjøre backend bedre.
- Dra ut queries i searchfield og reviewcard og lagt det inn i hooks mappen.
- Ikke fetche ratings hver gang brukeren går til YourRatings skjermen. Bare fetche når det har blitt lagt inn
  noen nye ratings (ta i bruk refetchqueries, eller noe slik).
- Mer funksjonalitet.
- Fra forrige prosjekt hadde vi noen punkter vi ønsket å forbedre (spesielt på backend). Siden jeg ikke har gjort noen endringer der vil
  de dessverre komme videre til dette prosjektet også (bedre fuzzysearch, bedre datasett, filtrering etter søk).

### Avsluttende

Jeg måtte gjøre noen valg og har i hovedsak satt meg inn i å lære meg React Native. Det var ikke like lett å skrive alt om som jeg
kanskje hadde tenkt og har derfor valgt å prioritere det jeg synes var viktigst.
