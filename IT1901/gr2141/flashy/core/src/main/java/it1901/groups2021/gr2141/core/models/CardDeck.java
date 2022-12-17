package it1901.groups2021.gr2141.core.models;

import it1901.groups2021.gr2141.core.state.Observable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data model representing a deck/collection of {@link Flashcard Flashcard}s.
 */
public class CardDeck extends Observable<CardDeck> {
  private List<Flashcard> flashcards;

  /**
   * Create an empty new instance.
   */
  public CardDeck() {
    this.flashcards = new ArrayList<>();
  }

  /**
   * Create an instance from a list of {@link Flashcard Flashcards}.
   *
   * @param flashcards A list of {@link Flashcard Flashcard} instances
   */
  public CardDeck(List<Flashcard> flashcards) {
    if (flashcards == null) {
      throw new IllegalArgumentException("Flashcards can not be null");
    }
    // By wrapping the cards into a new list, the list becomes mutable
    this.flashcards = new ArrayList<>(flashcards);
  }

  private void updateSubscribers() {
    super.updateSubscribers(this);
  }

  @Override
  public String toString() {
    return "{\n" + String.join("\n", flashcards.stream().map((f) -> "  "
    + f.toString()).collect(Collectors.toList())) + "\n}";
  }

  /**
   * returns list of the flashcards in the deck.
   * @return flashcards
   */
  public List<Flashcard> getFlashcards() {
    return flashcards;
  }

  /**
   * returns the amount of cards in the deck.
   * @return int of flashcards
   */
  public int getNumberOfCards() {
    return flashcards.size();
  }

  /**
   * Checks if flashcards exist.
   * @return Whether there are no cards
   */
  public boolean isEmpty() {
    return flashcards.size() == 0;
  }

  /**
   * Add a new flashcard to the deck. Has the bieffect of updating all listeners.
   *
   * @param card The new flashcard to add.
   */
  public void add(Flashcard card) {
    if (card == null) {
      throw new IllegalArgumentException("Flashcard can not be null");
    }
    flashcards.add(card);
    updateSubscribers();
  }

  /**
   * Add a new flashcard to the deck at a specified index. Has the bieffect of
   * updating all listeners.
   *
   * @param index The index of where to add the card.
   * @param card  The new flashcard to add.
   */
  public void add(int index, Flashcard card) {
    if (card == null) {
      throw new IllegalArgumentException("Flashcard can not be null");
    }
    if (index < 0 || flashcards.size() < index) {
      throw new IllegalArgumentException("Index is out of bounds");
    }
    flashcards.add(index, card);
    updateSubscribers();
  }

  /**
   * Update a specified flashcard to a new flashcard. Has the bieffect of updating
   * all listeners.
   *
   * @param index The index of the card to update.
   * @param card  The updated flashcard.
   */
  public void update(int index, Flashcard card) {
    if (card == null) {
      throw new IllegalArgumentException("Flashcard can not be null");
    }
    if (index < 0 || index > flashcards.size()) {
      throw new IllegalArgumentException("Can not update card at index: " + index);
    }
    flashcards.remove(index);
    flashcards.add(index, card);
    updateSubscribers();
  }

  /**
   * Remove a flashcard at the specified index. Has the bieffect of updating all
   * listeners.
   *
   * @param index The index of the card to remove
   */
  public void remove(int index) {
    if (index < 0 || index > flashcards.size()) {
      throw new IllegalArgumentException("Can not remove card at index: " + index);
    }
    flashcards.remove(index);
    updateSubscribers();
  }

}
