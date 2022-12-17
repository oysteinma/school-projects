// Linker som vi ønsker å ha i footeren
links = [
  {
    text: "Kontakt",
    href: "kontakt.html",
  },
  {
    text: "Facebook",
    href: "https://facebook.com/emsmorevyen",
    target: "_blank",
  },
  {
    text: "Instagram",
    href: "https://instagram.com/emsmorevyen",
    target: "_blank",
  },
];

// Lag en link med text og legg den til på det gitte elementet
function createLink(addToElement, text, href, target) {
  const textNode = document.createTextNode(text);

  const link = document.createElement("a");
  link.href = href;

  if (target) {
    link.target = target;
  }

  link.appendChild(textNode);

  addToElement.appendChild(link);
}

// Henter footer elementet på siden
const footer = document.getElementById("footer");

// Legg til content wrapperen på footer så alt av
// content blir innenfor den samme margen på siden
const content = document.createElement("div");
content.className = "content footer-items";
footer.appendChild(content);

// For hver av linkene i listen over lages en link
// som blir lagt til på footer.content diven
links.forEach((link) => createLink(content, link.text, link.href, link.target));
