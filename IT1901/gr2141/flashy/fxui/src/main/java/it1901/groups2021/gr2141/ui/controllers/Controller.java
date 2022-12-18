package it1901.groups2021.gr2141.ui.controllers;

import it1901.groups2021.gr2141.core.domainlogic.FlashcardProvider;

/**
 * Common elements for all JavaFX Controllers.
 */
public abstract class Controller {

  private FlashcardProvider flashcardProvider;

  /**
   * Returns flashcard provider.
   * @return The FlashCardProvider for the application state
   */
  public FlashcardProvider getFlashcardProvider() {
    return flashcardProvider;
  }

  /**
   * Sets flashcard provider.
   * @param flashcardProvider The FlashCardProvider for the application state
   */
  public void setFlashCardProvider(FlashcardProvider flashcardProvider) {
    if (flashcardProvider == null) {
      throw new IllegalArgumentException("flashcardProvider can not be null");
    }
    this.flashcardProvider = flashcardProvider;
  }

  /**
   * This is supposed to be in all Controller classes, although Java doesn't allow
   * abstract static methods.
   */
  // public abstract static Controller withFlashCardProvider(FlashcardProvider flashcardProvider);
}