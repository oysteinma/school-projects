/* --------------------------------- SVG --------------------------------- */

// Change Color
var svg = document.getElementById("emoji");
var amountClicked = 1;
svg.onclick = () => {
  switch (amountClicked) {
    case 0:
      document.documentElement.style.cssText = "--head-color: #ffd535;";
      amountClicked++;
      break;
    case 1:
      document.documentElement.style.cssText = "--head-color: #FF6262;";
      amountClicked++;
      break;
    case 2:
      document.documentElement.style.cssText = "--head-color: #FF4C4C;";
      amountClicked++;
      break;
    case 3:
      document.documentElement.style.cssText = "--head-color: #FF0000";
      amountClicked = 0;
      break;
  }
};
