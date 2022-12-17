// Liste over hvilke sider som skal være med i navigasjonsmenyen.
// Her representer ett element i listen en link bestående av en tekst
// og en url/endpoint hvor man skal ende opp.
//
// For å endre hva som er i navigasjonsmenyen trenger man kun å endre
// innholdet i denne listen.
const pages = [
  {
    text: "Om revyen",
    endpoint: "omrevyen.html",
  },
  {
    text: "Verv",
    endpoint: "verv.html",
  },
  {
    text: "Bildegalleri",
    endpoint: "bildegalleri.html",
  },
  {
    text: "Styret",
    endpoint: "styret.html",
  },
  {
    text: "Kontakt",
    endpoint: "kontakt.html",
  },
];

let currentEndpoint = "";

/**
 * Sjekker om man et endepunkt er nåværende endepunkt. Denne er
 * brukt for å kunne sette highlight på linken i menyen for den
 * nåværende siden.
 */
function isCurrentEndpoint(endpoint) {
  return endpoint === currentEndpoint;
}

/**
 * Denne funksjonen lager ett link element med tekst og href
 * spesifisert fra et element i pages listen.
 */
function createLinkToPage(page) {
  const anchor = document.createElement("a");
  const text = document.createTextNode(page.text);

  if (isCurrentEndpoint(page.endpoint)) {
    anchor.classList.add("navbar-current-item");
  }

  anchor.href = page.endpoint;
  anchor.appendChild(text);

  return anchor;
}

/**
 * Legger til en link i navigasjonsmenyen
 */
function addLinkToElement(page, element) {
  const link = createLinkToPage(page);
  element.appendChild(link);
}

/**
 * Finner nåværende side ved å sjekke hvilken url man befinner seg på.
 */
function getCurrentEndpoint() {
  const url = window.location.href;

  return url.split("/").pop();
}

/**
 * Bygger navigasjonsmenyen ved å først legge til de nødvendige wrapper
 * elementene for å begrense innholdet. Deretter blir linkene definert
 * i pages listen lagt til.
 */
function buildNavbar() {
  currentEndpoint = getCurrentEndpoint();

  const content = document.createElement("div");
  content.classList.add("content");
  const navbar = document.getElementById("navbar");
  navbar.appendChild(content);

  const items = document.createElement("div");
  items.classList.add("navbar-items");
  content.appendChild(items);

  pages.forEach((page) => addLinkToElement(page, items));
}

// Ettersom scriptet blir importert nederst på siden er det safe å kjøre dette
// direkte uten å ha en listener for når vinduet er klart.
buildNavbar();
