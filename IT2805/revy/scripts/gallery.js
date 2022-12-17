// Denne listen har info rundt hvilke år det finnes bilder for
// og hvor mange bilder det er i mappen. For å legge til
// et nytt år med bilder trenger man kun å legge til et nytt
// element i listen med årstall og antall bilder. Man må også
// legge bildene i riktig mappe med navn på formen "img-n" hvor
// n er indeksen til bildet.
const imageFolders = [
  {
    year: 2020,
    numberOfImages: 21,
  },
  {
    year: 2019,
    numberOfImages: 29,
  },
  {
    year: 2018,
    numberOfImages: 29,
  },
  {
    year: 2017,
    numberOfImages: 25,
  },
];

// Datastrukturer som blir brukt for å lage karusell i galleriet
const maxIndexPrevYear = imageFolders[imageFolders.length - 1].numberOfImages;
const firstYear = imageFolders[0].year;
const lastYear = imageFolders[imageFolders.length - 1].year;

let currentModal = {
  imageIndex: -1,
  year: -1,
};

/**
 * Bygger source string for et bilde gitt en index og år
 */
function getImageSrc(year, index) {
  return `./img/${year}/img-${index}.jpg`;
}

/**
 * Bygger id for et resterende grid gitt et år
 */
function getRemainingGridId(year) {
  return `gallery-grid-remaining-${year}`;
}

/**
 * Lager bilde-element for et gitt år og index på bilden i mappen.
 * Bildet er wrappet i en div som hjelper til å sentrere det samt
 * skjule forskjell i aspect ratios ved hjelp av bakgrunnsfarge.
 */
function createImage(year, index) {
  const image = document.createElement("img");
  image.src = getImageSrc(year, index);
  image.className = "gallery-image";
  // Last bildene kun når de er på vei inn i viewport
  image.loading = "lazy";
  image.alt = `revy ${year}`;

  const wrapper = document.createElement("div");
  wrapper.className = "gallery-image-wrapper";
  wrapper.appendChild(image);

  return wrapper;
}

/**
 * Lager en div som vil ha en css grid layout som vi
 * kan plassere bilder inn i.
 */
function createGalleryGrid() {
  const gallery = document.createElement("div");
  gallery.className = "gallery-grid";

  return gallery;
}

/**
 * Lager preview delen av et årsgalleri. Dette er et grid
 * på 4x4 rader/kolonner som blir populert av de 5 første
 * bildene i en årsmappe. Det første bildet (index 0) blir
 * stort (2x2) og plassert på høyre eller venstre side alt
 * ettesom det er skuddår eller ikke.
 */
function createGalleryYearPreview(year) {
  const grid = createGalleryGrid();
  const remainingGridId = getRemainingGridId(year);

  // Det første bildet i årsmappen vil ta 2x2 plass.
  const yearImage = createImage(year, 0);
  // Event listener på de store bildene som vil åpne/lukke grid med resterende bilder
  yearImage.addEventListener("click", (event) =>
    showRemainingGrid(remainingGridId, event.target)
  );
  grid.appendChild(yearImage);

  // Dette lar oss sortere annethvert år til høyre eller venstre i gridet.
  if (year % 2 === 0) {
    yearImage.classList.add("gallery-grid-left");
  } else {
    yearImage.classList.add("gallery-grid-right");
  }

  // Legg til de neste 4 bildene i mappen til i preview grid.
  addClickableImagesToGrid(year, 1, 4, grid);

  return grid;
}

/**
 * En builder funksjon som legger til bilder i et grid med en funksjon som lar
 * brukeren forstørre bildet og bla i galleriet.
 */
function addClickableImagesToGrid(year, fromIndex, toIndex, addToGrid) {
  for (let i = fromIndex; i <= toIndex; i++) {
    const image = createImage(year, i);
    // Event listener på de resterende bildene som vil åpne de i modal
    image.addEventListener("click", () => setModalImage(year, i));
    addToGrid.appendChild(image);
  }
}

/**
 * Lager resterende delen av et årsgalleri. Dette er et grid
 * på nx4 rader/kolonner som blir populert av alle bildene
 * i årsmappen unntatt de som ble brukt i preview grid.
 */
function createGalleryYearRemaining(year, numberOfImages) {
  const grid = createGalleryGrid();
  grid.classList.add("gallery-grid-remaining");
  grid.id = getRemainingGridId(year);

  // Legg alle, untatt de 4 første, til i resterende grid.
  addClickableImagesToGrid(year, 5, numberOfImages - 1, grid);

  // Wrapper det i en div for å lage en hack som inneholder
  // en "border bottom" for å kunne runde av på alle sider.
  // noe border-radius ikke gjør på border-bottom elementer.
  const wrapper = document.createElement("div");
  wrapper.className = "gallery-grid-remaining-hide";

  const pseudoBorder = document.createElement("div");
  pseudoBorder.className = "gallery-grid-remaining-border";

  wrapper.appendChild(grid);
  wrapper.appendChild(pseudoBorder);

  return wrapper;
}

/**
 * Lager et grid med alle årsbildene i en mappe. Denne er
 * bestående av to deler:
 *
 * 1) Preview grid - dette er de fem første bildene i mappen.
 * 2) Remaining grid - dette er resterende bilder i mappen.
 *
 * Hvor kun (1) vil være synelig til å begynne med så kan
 * man klikke seg inn på årsbildet for å se de resterende
 * bildene.
 */
function createYearGrid(imageFolder) {
  const year = document.createElement("div");
  year.className = "gallery";

  // Lager preview grid.
  const preview = createGalleryYearPreview(imageFolder.year);
  year.appendChild(preview);

  // Lager resterende grid.
  const remaining = createGalleryYearRemaining(
    imageFolder.year,
    imageFolder.numberOfImages
  );
  year.appendChild(remaining);

  return year;
}

/**
 * Event handler som åpner og lukker remaining grids.
 * Den vil passe på at det kun er ett årsgrid som er åpen om gangen
 * ved å lukke alle andre unntatt det som ble klikket på, med mindre
 * man ønsket å skule det også.
 */
function showRemainingGrid(remainingGridId, target) {
  const grids = document.getElementsByClassName("gallery-grid-remaining");
  const hiddenClass = "gallery-grid-remaining-hide";

  for (const grid of grids) {
    const parent = grid.parentNode;
    if (grid.id === remainingGridId) {
      // Bytt på vis og skjul om det er elementet med samme id som oppgitt.
      if (parent.classList.contains(hiddenClass))
        parent.classList.remove(hiddenClass);
      else {
        parent.classList.add(hiddenClass);
      }
    } else {
      // Skjul alle som ikke er den som ble trykket på.
      parent.classList.add(hiddenClass);
    }
  }

  target.scrollIntoView();
}

/**
 * Event handler som åpner og lukker modalen for et gitt bilde.
 * Det vil vises uthevet midt på skjermen med en undertekst som
 * beskriver hvilket år bildet er fra.
 */
function setModalImage(year, index) {
  const modalImage = document.getElementById("gallery-modal-image");
  modalImage.src = getImageSrc(year, index);

  currentModal = {
    imageIndex: index,
    year: year,
  };

  const text = document.getElementById("gallery-modal-text");
  text.innerHTML = `Emil- og smørekopprevyen ${year}`;

  const modal = document.getElementById("gallery-modal");
  modal.style.display = "block";

  // Bestem om next/previous knappene skal skjules eller vises iforhold til hvor i karusellen man befinner seg
  const nxtButton = document.getElementById("gallery-modal-next");
  const prevButton = document.getElementById("gallery-modal-prev");
  prevButton.style.display = firstYear === year && index === 1 ? "none" : "";
  nxtButton.style.display =
    lastYear === year && index === maxIndexPrevYear - 1 ? "none" : "";
}

/**
 * Denne lukker skjuler modalen med det uthevede bildet og knapper som hører til.
 */
function closeModal() {
  modal.style.display = "none";
  currentModalImage = "";
}

/**
 * Funksjon som håndterer å bla framover i bilder i karusellen. Her finner man
 * hvilken index man skal bla til og juster for årstall om man går over til
 * neste år.
 */
function nextImage() {
  const nextIndex = currentModal.imageIndex + 1;
  let maxIndex = 0;

  // Denne finner nåværende år sitt antall bilder. Dette burde nok
  // optimaliseres til å bruke et hashmap som oppslagstabell isteden.
  // Men det er ikke gjort per nå da det er veldig lett for sluttbrukeren
  // å forholde seg til en liste.
  for (const imageFolder of imageFolders) {
    if (imageFolder.year === currentModal.year) {
      maxIndex = imageFolder.numberOfImages;
    }
  }

  if (nextIndex === maxIndex) {
    setModalImage(currentModal.year - 1, 1);
  } else {
    setModalImage(currentModal.year, nextIndex);
  }
}

/**
 * Funksjon som håndterer å bla tilbake i bilder i karusellen. Her finner man
 * hvilken index man skal bla til og juster for årstall om man går over til
 * neste år.
 */
function prevImage() {
  const prevIndex = currentModal.imageIndex - 1;
  const minIndex = 0;
  let maxIndexPrevYear = -1;

  // Denne finner nåværende år sitt antall bilder. Dette burde nok
  // optimaliseres til å bruke et hashmap som oppslagstabell isteden.
  // Men det er ikke gjort per nå da det er veldig lett for sluttbrukeren
  // å forholde seg til en liste.
  for (const imageFolder of imageFolders) {
    if (imageFolder.year === currentModal.year + 1) {
      maxIndexPrevYear = imageFolder.numberOfImages;
    }
  }

  if (prevIndex === minIndex) {
    setModalImage(currentModal.year + 1, maxIndexPrevYear - 1);
  } else {
    setModalImage(currentModal.year, prevIndex);
  }
}

const gallery = document.getElementById("gallery");

// Lager et årsgrid for hvert år med bilder og legger de inn i galleriet.
imageFolders.forEach((imageFolder) =>
  gallery.appendChild(createYearGrid(imageFolder))
);

const modal = document.getElementById("gallery-modal");

// Lukker modalen ved klikk på vinduet. Er lagt på vinduet isteden for
// direkte på modalen da dette førte til at modalen lukkes når
// man navigerer fram/tilbake i karusellen.
window.onclick = (e) => {
  if (e.target == modal) {
    closeModal();
  }
};
