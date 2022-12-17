/* --------------------------------- DOCUMENTATION --------------------------------- */

// Show And Hide Documentation
$("#button").on("click", function () {
  if ($("#button").text() === "Show Documentation") {
    $("#button").text("Hide Documentation");
    $(".documentation").css("display", "block");
  } else {
    $("#button").text("Show Documentation");
    $(".documentation").css("display", "none");
  }
});
