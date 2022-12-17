/* --------------------------------- CANVAS --------------------------------- */

var canvas = document.getElementById("myCanvas");
var ctx = canvas.getContext("2d");

// Start Animation
canvas.addEventListener("mouseover", function () {
  animate();
});

// End Animation
canvas.addEventListener("mouseout", function () {
  cancelAnimationFrame(request);
  defaultSmiley();
});

// Increment Amount Clicked
canvas.addEventListener("click", function () {
  amountClickedCanvas++;
});

// Creates Default Smiley
function defaultSmiley() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);

  // Happy Face
  createCircles(200, 117.5, 90, 0, 2 * Math.PI, null, "#ffd535");

  // Happy Eyes
  createCircles(165, 90, 15, Math.PI, 0, 5, null);
  createCircles(230, 90, 15, Math.PI, 0, 5, null);

  // Happy Smile
  createCircles(198, 140, 35, 0, Math.PI, 5, null);
}

defaultSmiley();

// Variables Used For Animation And Colors
var request;

var y_head = 117.5;
var y_smile = 160;

var y_eye1 = 70;
var y_eye2 = 90;

var y_teeth = 135;

var tear1 = 120;
var tear2 = 100;
var tear3 = 80;
var tear4 = 280;
var tear5 = 300;
var tear6 = 320;

var dy = 1;
var dx = 0.5;

var fps = 0;

var amountClickedCanvas = 0;

// Animation Of The Emoji
function animate() {
  request = requestAnimationFrame(animate);
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  calculateXandY();

  // Angry Face
  createCircles(200, y_head, 90, 0, 2 * Math.PI, null, chooseColor());

  // Angry Smile
  createCircles(198, y_smile, 35, Math.PI, 0, 5, null);

  // Angry Tears
  createCircles(tear1, 100, 10, 0, 2 * Math.PI, null, "#46d4f3");
  createCircles(tear2, 130, 10, 0, 2 * Math.PI, null, "#46d4f3");
  createCircles(tear3, 160, 10, 0, 2 * Math.PI, null, "#46d4f3");

  createCircles(tear4, 100, 10, 0, 2 * Math.PI, null, "#46d4f3");
  createCircles(tear5, 130, 10, 0, 2 * Math.PI, null, "#46d4f3");
  createCircles(tear6, 160, 10, 0, 2 * Math.PI, null, "#46d4f3");

  // Angry Eyes
  createCrossEyes(155, 180, 180, 155, 3);
  createCrossEyes(245, 220, 220, 245, 3);

  // Angry Teeth
  createTeeth(190, 160, 200, 180);
  createTeeth(200, 160, 210, 190);
  createTeeth(210, 160, 220, 200);
}

// Calculating Distance Animation For X And Y
function calculateXandY() {
  fps++;
  if (fps == 41) {
    fps = 0;
  } else if (fps > 20 && fps <= 40) {
    tear1 += dx;
    tear2 += dx;
    tear3 += dx;
    tear4 += dx;
    tear5 += dx;
    tear6 += dx;

    y_head += dy;
    y_smile += dy;

    y_eye1 += dy;
    y_eye2 += dy;

    y_teeth += dy;
  } else {
    tear1 -= dx;
    tear2 -= dx;
    tear3 -= dx;
    tear4 -= dx;
    tear5 -= dx;
    tear6 -= dx;

    y_head -= dy;
    y_smile -= dy;

    y_eye1 -= dy;
    y_eye2 -= dy;

    y_teeth -= dy;
  }
}

// Selecting Next Color
function chooseColor() {
  const colorOfFace = ["#ffd535", "#FF6262", "#FF4C4C", "#FF0000"];

  if (amountClickedCanvas == 4) {
    amountClickedCanvas = 0;
  }

  return colorOfFace[amountClickedCanvas];
}

// Creating The Circles
function createCircles(x, y, r, sAngle, eAngle, line_width, color) {
  if (color != null) {
    ctx.beginPath();
    ctx.arc(x, y, r, sAngle, eAngle);
    ctx.fillStyle = color;
    ctx.fill();
  } else {
    ctx.beginPath();
    ctx.lineWidth = line_width;
    ctx.arc(x, y, r, sAngle, eAngle);
    ctx.stroke();
  }
}

// Creating The Cross Eyes
function createCrossEyes(
  start_x,
  x_first_line,
  x_second_start,
  x_second_line,
  line_width
) {
  ctx.beginPath();
  ctx.lineWidth = line_width;
  ctx.moveTo(start_x, y_eye1);
  ctx.lineTo(x_first_line, y_eye2);
  ctx.moveTo(x_second_start, y_eye1);
  ctx.lineTo(x_second_line, y_eye2);
  ctx.stroke();
}

// Creating The Teeth
function createTeeth(start_x, start_y, x_first_line, x_second_line) {
  ctx.beginPath();
  ctx.moveTo(start_x, start_y);
  ctx.lineTo(x_first_line, y_teeth);
  ctx.lineTo(x_second_line, y_teeth);
  ctx.fillStyle = "#ffffff";
  ctx.fill();
}
