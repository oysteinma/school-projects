package it1901.groups2021.gr2141.ui.controllers;

import it1901.groups2021.gr2141.core.domainlogic.FlashcardProvider;
import it1901.groups2021.gr2141.core.models.Flashcard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
/**
  * Menu controller of the application UI.
  */

public class MenuController extends Controller {

  @FXML
  private TextField writeFront;

  @FXML
  private TextField writeBack;

  /**
   * Creates a new card and adds it to the active deck.
   *
   * @param event an ActionEvent.
   */
  @FXML
  public void handleCreateFlashCard(ActionEvent event) {
    if (writeFront.getText().isEmpty() || writeBack.getText().isEmpty()) {
      return;
    }
    getFlashcardProvider().getCardDeck()
                          .add(Flashcard.of(writeFront.getText(), writeBack.getText()));
    clearTextFields();
  }

  private void clearTextFields() {
    writeFront.clear();
    writeBack.clear();
  }

  /**
   * returns a flashcard provider for menu controller.
   * @see it1901.groups2021.gr2141.ui.controllers.Controller
   */
  public static Controller withFlashCardProvider(FlashcardProvider flashcardProvider) {
    MenuController controller = new MenuController();
    controller.setFlashCardProvider(flashcardProvider);
    return controller;
  }
}
