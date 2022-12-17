const shakingButtons = [];

function shake(button, magnitude = 10) {
  // Første shake er satt til 1 = høyre
  let tiltAngle = 1;

  // Teller antall shakes
  let counter = 1;

  // Totalt antall shakes per animasjonsframe
  const maxNumberOfShakes = 15;

  // Resetter posisjonen etter man er ferdig med å shake
  let startAngle = 0;

  // Reduserer magnituden for hver gang animasjonen blir kaldt
  let magnitudeUnit = magnitude / maxNumberOfShakes;

  // Legger til knappen i en liste om den ikke finnes allerede
  if (shakingButtons.indexOf(button) === -1) {
    shakingButtons.push(button);
    wiggle();
  }

  function wiggle() {
    if (counter < maxNumberOfShakes) {
      // Resett rotasjonen på knappen
      button.style.transform = "rotate(" + startAngle + "deg)";

      // Reduser magnitude
      magnitude -= magnitudeUnit;

      // Hentet fra https://jsfiddle.net/jnfu8s7c/1/
      // Roter elementet til høyre eller venstre med en mengde
      // gitt i radianer som matcher magnituden
      let angle = Number(magnitude * tiltAngle).toFixed(2);
      button.style.transform = "rotate(" + angle + "deg)";
      counter += 1;

      // Reverser hvilken vei knappen tilter (høyre / venstre)
      tiltAngle *= -1;

      requestAnimationFrame(wiggle);
    }

    // Resetter knappens posisjon tilbake og fjerner den fra listen over knapper som skal ristes når den er ferdig
    if (counter >= maxNumberOfShakes) {
      button.style.transform = "rotate(" + startAngle + "deg)";
      shakingButtons.splice(shakingButtons.indexOf(button), 1);
    }
  }
}

const shakeButtons = document.getElementsByClassName("shake");

//Makes every button with class name= "shake" shake
for (const shakeButton of shakeButtons) {
  shakeButton.addEventListener("mouseenter", (e) => {
    shake(e.currentTarget, 10);
  });
}
