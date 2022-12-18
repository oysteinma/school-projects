/**
 * Lager et generisk link element med et definerte
 * properties.
 */
function createLink(href, id, target, className) {
  const link = document.createElement("a");
  link.href = href;
  link.id = id;

  if (className) {
    link.className = className;
  }

  if (target) {
    link.target = target;
  }

  return link;
}

/**
 * Lager header logo element med link
 */
function createHeaderLogo(src, iconClassName, hrefTo, linkId, target) {
  const icon = document.createElement("img");
  icon.src = src;
  icon.className = iconClassName;

  const link = createLink(hrefTo, linkId, target);
  link.appendChild(icon);

  return link;
}

/**
 * Lager header text elementet med link
 */
function createHeaderText(text, hrefTo, id) {
  const link = createLink(hrefTo, id);
  const textNode = document.createTextNode(text);

  link.appendChild(textNode);

  return link;
}

/**
 * Lager knappen for kjøp av billetter til en forestilling. Denne vil linke til
 * en ekstern side.
 */
function createTicketButton(hrefTo, target, linkId, text, textId, wrapperId) {
  const link = createLink(hrefTo, linkId, target);

  const textNode = document.createTextNode(text);

  const billetter = document.createElement("div");
  billetter.id = textId;
  billetter.appendChild(textNode);

  const wrapper = document.createElement("div");
  wrapper.id = wrapperId;

  wrapper.appendChild(billetter);
  link.appendChild(wrapper);

  return link;
}

/**
 * Lager sosialemedier knapper til headeren. Disse er lagd med
 * FontAwesome ikoner henter fra https://fontawesome.com (importert i style.css)
 */
function createSocialButton(
  faClassName,
  linkClassName,
  hrefTo,
  linkId,
  target
) {
  const ikon = document.createElement("i");
  ikon.className = faClassName;

  const link = createLink(hrefTo, linkId, target, linkClassName);
  link.appendChild(ikon);

  return link;
}

/**
 * Fyller ut banneren som ligger definert i htmlen med et gitt banner bilde.
 */
function populateBanner(src) {
  const bannerid = document.getElementById("banner");

  const banner = document.createElement("img");
  banner.src = src;
  banner.alt = "banner";

  bannerid.appendChild(banner);
}

// Her kjøres scriptet når det lastes inn på slutten av et html dokument.
// Det vil bygges forskjellige elementer som legges til å headeren
const contentWrapper = document.createElement("div");
contentWrapper.className = "content fill headerparent";

const logo = createHeaderLogo(
  "img/imgheader/emil.png",
  "headerimg",
  "index.html",
  "header1"
);
contentWrapper.appendChild(logo);

const logoText = createHeaderText(
  "Emil- og smørekoppenrevyen",
  "index.html",
  "headertext"
);
contentWrapper.appendChild(logoText);

const ticketButton = createTicketButton(
  "http://byscenen.no/event/emil-smorekoppenrevyen-refleksjon-midtforestilling/",
  "_blank",
  "billettlink",
  "Billetter",
  "billetter",
  "billettbox"
);
contentWrapper.appendChild(ticketButton);

const facebookButton = createSocialButton(
  "fab fa-facebook-f",
  "social-link facebook",
  "https://www.facebook.com/emsmorevyen",
  "header3",
  "_blank"
);
contentWrapper.appendChild(facebookButton);

const instagramButton = createSocialButton(
  "fab fa-instagram",
  "social-link instagram",
  "https://www.instagram.com/emsmorevyen/",
  "header4",
  "_blank"
);
contentWrapper.appendChild(instagramButton);

const header = document.getElementById("header");
header.appendChild(contentWrapper);

populateBanner("img/forsidebilder/banner.png");
