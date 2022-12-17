package it1901.groups2021.gr2141.ui.controllers;

import it1901.groups2021.gr2141.core.domainlogic.FlashcardProvider;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
/**
  * Navbar controller of the application UI.
  */

public class NavbarController extends Controller implements Initializable {

  @FXML
  private ToggleButton btnToggleFirstLastWrapAroundMode;

  @FXML
  private ToggleButton btnToggleRandomMode;

  @FXML
  private ToggleButton btnToggleShowOtherSide;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    setToggleButtonValues();
  }

  private void setToggleButtonValues() {
    btnToggleRandomMode.setSelected(getFlashcardProvider().isCardOrderIsRandomMode());
    btnToggleShowOtherSide.setSelected(getFlashcardProvider().isShowOrderIsFlippedMode());
    btnToggleFirstLastWrapAroundMode.setSelected(getFlashcardProvider()
                                                 .isFirstLastWrapAroundMode());
  }

  /**
   * Calls function that flips card on touch.
   * @see it1901.groups2021.gr2141.core.domainlogic.FlashcardProvider#isShowOrderIsFlippedMode
   * @param event an ActionEvent.
   */
  @FXML
  public void handleToggleShowOtherSide(ActionEvent event) {
    getFlashcardProvider().toggleShowOrderIsFlippedMode();
  }

  /**
   * Calls fuction that toggles random order.
   * @see it1901.groups2021.gr2141.core.domainlogic.FlashcardProvider#isCardOrderIsRandomMode
   * for more details.
   * @param event an ActionEvent.
   */
  @FXML
  public void handleToggleCardOrderIsRandom(ActionEvent event) {
    getFlashcardProvider().toggleCardOrderIsRandomMode();
  }

  /**
   * Calls function that toggles ability to loop cards..
   * @see it1901.groups2021.gr2141.core.domainlogic.FlashcardProvider#isFirstLastWrapAroundMode
   * for more details.
   * @param event an ActionEvent.
   */
  @FXML
  public void handleToggleFirstLastWrapAroundMode(ActionEvent event) {
    getFlashcardProvider().toggleFirstLastWrapAroundMode();
  }

  /**
   * Returns a navbar controller for flashcard provider.
   * @return controller
   */
  public static Controller withFlashCardProvider(FlashcardProvider flashcardProvider) {

    NavbarController controller = new NavbarController();

    controller.setFlashCardProvider(flashcardProvider);
    return controller;
  }

}
