package it1901.groups2021.gr2141.ui.controllers;

import it1901.groups2021.gr2141.core.domainlogic.FlashcardProvider;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
/**
  * CardArea controller of the application UI.
  */

public class CardAreaController extends Controller implements Initializable {

  @FXML
  private Label showCard;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    showCurrentCardContent();
    getFlashcardProvider().subscribe((newFlashCardModel) -> showCurrentCardContent());
  }

  /**
   * Updates the card shown in the UI.
   */
  public void showCurrentCardContent() {
    var content = getFlashcardProvider().getCard();
    showCard.setText(content.getText());
  }

  /**
   * Flips the card. This is connected to an FXML element.
   *
   * @param event a MouseEvent.
   */
  @FXML
  public void handleFlipCardMouse(MouseEvent event) {
    getFlashcardProvider().flipCard();
  }

  /**
   * returns a Card area controller for flashcard provider.
   * @see it1901.groups2021.gr2141.ui.controllers.Controller
   */
  public static Controller withFlashCardProvider(FlashcardProvider flashcardProvider) {
    CardAreaController controller = new CardAreaController();
    controller.setFlashCardProvider(flashcardProvider);
    return controller;
  }
}
