/**
 * Module including everything relevant to the UI
 */
module it1901.groups2021.gr2141.fxui {
  requires it1901.groups2021.gr2141.core;

  requires transitive javafx.graphics;
  requires javafx.fxml;
  requires javafx.controls;

  requires org.junit.jupiter.api;

  exports it1901.groups2021.gr2141.ui;
  opens it1901.groups2021.gr2141.ui to javafx.graphics, javafx.fxml;
}