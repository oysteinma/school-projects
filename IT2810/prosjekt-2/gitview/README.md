# Gitview

For å kjøre prosjektet

```bash
$ cd gitview
$ npm install
$ npm start
```

## Dokumentasjon

Siden Gitview lar brukere få tilgang til ulike data om et spesifikt GitLab-prosjekt, og gir en rekke valgmuligheter. Brukere kan lagre sin prosjekt-ID og sin access token lokalt, noe som gjør at samme informasjon ikke må skrives inn hver gang. Når brukeren har lagt inn gyldig informasjon, vises informasjon om issues, users og commits. Commits-siden lar brukeren filtrere på dato, slik at en f.eks kan velge commits opprettet mellom 10. og 20.september. Her kan man også filtrere på hvem som utførte committen, og finne informasjon som dato, beskrivelse og link. Users-siden lar en filtrere på brukere med visse titler, og lar en besøke den enkelte brukerens profil om en trykker på den. Til slutt kan det nevnes at Issues-siden viser et diagram over opprettede issues. Her kan brukeren velge en dato i September, og få søyler som viser hvor mange issues som ble opprettet før, på og etter denne datoen. Sidens utforming og elementer er skalerbare, slik at funksjonaliteten opprettholdes på både større og mindre skjermer.

### Tekniske krav

Prosjektet Gitview er bygd med Node versjon 16.17.x og React versjon 18, og skrevet i Typescript. Vi har dermed brukt interfaces og typedeklarasjoner for å sikre korrekte verdityper. Dette var særlig viktig ved bruk av APIet, og interfacene relatert til dette er samlet i mappen “model”.

Vi har i hovedsak bygd opp prosjektet med diverse funksjonelle komponenter, da disse gir litt mer moderne og lettvint bruk av state management. Vi innførte også en klasse i prosjektet i form av en footer. Oppsettet er bygd rundt App.tsx, hvor vi bruker en hashrouter for å holde en overordnet struktur på sider. Footer- og Navbar-komponentene lastes alltid inn på alle sider. Videre tok vi også bruk av context-Apiet gjennom en “authenticated” boolean, som ga bruker tilbakemelding om et prosjekt hadde blitt loadet riktig eller ikke. Denne settes opp i App.tsx, og blir endret på i Form.tsx, og bestemmer om de ulike komponentene skal loades.

For å lage brukergrensesnitt har vi valgt å ta i bruk Material UI. En på teamet har erfaring med biblioteket fra før, og resten var også interessert i å prøve det ut. Material UI har mye bra komponenter, som lar oss bruke mindre tid på design, og mer på funksjonalitet. Videre er det mange bra eksempler på alle komponentene til mui som står skrevet både i typescript og javascript, og er dermed også en god måte å lære språket på.

Andre pakker som ble brukt var [chartjs2](https://react-chartjs-2.js.org/) som lot oss visualisere issues i form av søylediagram.

For AJAX og innhenting av data har vi valgt å bruke [axios](https://www.npmjs.com/package/axios). Den er veldig mye brukt, og gjør det også enkelt å bygge opp GET-requests. Samtidig var det ikke like relevant i denne innleveringen, ettersom vi har begrenset bruk av http-requests. Hadde vi tatt bruk av POST, PUT og DELETE requests hadde axios i enda større grad nyttiggjort seg.

HTML sin Web Storage er flittig brukt i appen. For local storage har vi valgt å plassere prosjektid, mens i session-storage har vi plassert api-nøkkel. Disse oppdateres ut i fra brukeren sin input. Api-nøkkel var spesielt greit å bruke på session-storage, da det ikke lagres til neste økt.

Sidens responsivitet blir ivaretatt ved hjelp av blant annet media queries og CSS Grid - for eksempel blir innholdet på “Welcome to GitView”-siden midtstilt og skalert. Git-bildet og knappene på siden endrer her størrelse for å tilpasse seg mindre skjermer. Viewport er også brukt for å sikre en fleksibel layout: for eksempel endres innholdet på forsiden til å ta opp en større del av skjermen ved mindre størrelser.

I GitLab har vi lagt inn en simpel CI/CD for å sjekke at formateringen (Prettier) er lik gjennom hele prosjektet. Her sjekker vi også at testene kjøres og samler inn testdekningsgrad for prosjektet.

### Testing

[create-react-app] - kommandoen kommer med et ferdig oppsett av jest sitt testmiljø. Vi har valgt å inkludere en snapshottest (App.tsx) siden dette var minimumskravet og ikke lagt noe mer vekt på dette i denne innlevering. Vi har testet api-ets sine issue- og repo-kall mot et åpent gitlab repository for å sjekke at kallene blir utført på tenkt måte. Form.tsx er komponenten vi har valgt for å teste oppførsel. Her har vi inkludert mocking av en funksjon (alert) og tatt med userEvent for å teste oppførselen. Her har vi erfart hvordan Jest fungerer og føler vi har fått en forståelse av hva man tester i typiske React-applikasjoner.

For å teste applikasjonen

```bash
$ cd gitview
$ npm test
```

For å oppdatere snapshot

```bash
$ cd gitview
$ npm run updateSnapshot
```

### Notater

Det er noen få ting ved funksjonaliteten og utseende som vi gjerne skulle hatt mer tid til å løse.

Statistikk for Issues skulle vi gjerne hatt mer tid til å utvide med for eksempel ulike måneder, eller sortere på “backlog”, “doing”, “for review” og “done”.

Vi har også en snackbar på hjemsiden som dukker opp når man skriver inn prosjektid og token. Denne tar ikke hensyn til at mobile skjermer ofte zoomer inn når man trykker på inputfelt, og er dermed ikke alltid skalert ordentlig på mobile skjermer når man trykker “connect!”

### Avsluttende

I starten av prosjektet satt vi som grunnlag at vi lagde branches ut i fra issues og oppgaver vi skal gjøre. Dette har fungert bra, spesielt når vi har jobbet med litt ulike komponenter, men har også måttet løst litt ulike merge-conflicts, uten at det har vært en stor hindring.
