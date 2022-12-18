package it1901.groups2021.gr2141.ui.controllers;

import it1901.groups2021.gr2141.core.domainlogic.FlashcardProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
  * Card Navigation controller of the application UI.
  */
public class CardNavigationController extends Controller {

  /**
  * Flips the card by pressing the button.
  *
  * @param event an ActionEvent.
  */
  @FXML
  public void handleFlipCardButton(ActionEvent event) {
    getFlashcardProvider().flipCard();
  }

  /**
  * Turns to the next card in the deck.
  *
  * @param event an ActionEvent.
  */
  @FXML
  public void handleTurnToNextCard(ActionEvent event) {
    try {
      getFlashcardProvider().turnToNextCard();
    } catch (IllegalArgumentException e) {
      System.out.println("You are on the last card!");
    }
  }

  /**
  * Turns to the previous card in the deck.
  *
  * @param event an ActionEvent.
  */
  @FXML
  public void handleTurnToPreviousCard(ActionEvent event) {
    try {
      getFlashcardProvider().turnToPreviousCard();
    } catch (IllegalArgumentException e) {
      System.out.println("You are on the first card!");
    }
  }

  /**
   * returns a card navigation controller for flashcard provider.
   * @see it1901.groups2021.gr2141.ui.controllers.Controller
   */
  public static Controller withFlashCardProvider(FlashcardProvider flashcardProvider) {
    CardNavigationController controller = new CardNavigationController();
    controller.setFlashCardProvider(flashcardProvider);
    return controller;
  }
}
