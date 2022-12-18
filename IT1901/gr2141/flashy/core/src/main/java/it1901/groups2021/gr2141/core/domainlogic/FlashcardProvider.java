package it1901.groups2021.gr2141.core.domainlogic;

import it1901.groups2021.gr2141.core.models.CardContent;
import it1901.groups2021.gr2141.core.models.CardDeck;
import it1901.groups2021.gr2141.core.models.Flashcard;
import it1901.groups2021.gr2141.core.state.Observable;
import it1901.groups2021.gr2141.core.storage.CardDeckStorage;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.prefs.Preferences;

/**
 * A class handling card navigation from a CardDeck.
 */
public class FlashcardProvider extends Observable<FlashcardProvider> {

  private static final Preferences preferences = Preferences.userRoot()
      .node(FlashcardProvider
      .class.getName());

  private CardDeckStorage cardDeckStorage;

  private CardDeck deck;
  private int cardIndex;
  private boolean cardShowsFront;

  private boolean showOrderIsFlippedMode;
  private boolean cardOrderIsRandomMode;
  private boolean firstLastWrapAroundMode;

  /**
   * Default constructor.
   *
   * @param cardDeckStorage the initial cardDeck to register to the provider.
   */
  public FlashcardProvider(CardDeckStorage cardDeckStorage) {
    setCardDeckStorage(cardDeckStorage);
    initializeCardDeck();
    initializeModes();
  }

  private void initializeCardDeck() {
    CardDeck deck;

    try {
      deck = cardDeckStorage.readDeck(0);
    } catch (IOException e) {
      deck = new CardDeck(List.of(Flashcard.of(
        "Welcome to Flashy", "E N J O Y !")));
    }

    this.setCardDeck(deck);
    this.cardIndex = 0;
    this.cardShowsFront = true;
  }

  private void initializeModes() {
    this.showOrderIsFlippedMode = preferences.getBoolean(
      "showOrderIsFlippedMode", false);
    this.cardOrderIsRandomMode = preferences.getBoolean(
      "cardOrderIsRandomMode", false);
    this.firstLastWrapAroundMode = preferences.getBoolean(
      "firstLastWrapAroundMode", true);
  }

  private void updateSubscribers() {
    super.updateSubscribers(this);
  }

  /**
   * Gets card deck storage.
   * {@link it1901.groups2021.gr2141.core.storage.CardDeckStorage
   *  CardDeckStorage}
   * instance which provides storage for the FlashcardProvider.
   */
  public CardDeckStorage getCardDeckStorage() {
    return cardDeckStorage;
  }

  /**
   * Set the.
   * {@link it1901.groups2021.gr2141.core.storage.CardDeckStorage
   *  CardDeckStorage}
   * instance which provides storage for the FlashcardProvider.
   * @param cardDeckStorage The new cardDeckStorage.
   */
  public void setCardDeckStorage(CardDeckStorage cardDeckStorage) {
    if (cardDeckStorage == null) {
      throw new IllegalArgumentException(
        "cardDeckStorage can not be null.");
    }
    this.cardDeckStorage = cardDeckStorage;
  }

  /**
   * gets the deck.
   * @return The active deck
   */
  public CardDeck getCardDeck() {
    return this.deck;
  }

  public void setCardDeck(int index) throws IOException {
    setCardDeck(cardDeckStorage.readDeck(index));
  }

  /**
   * Replace the active deck with a new one.
   *
   * @param deck The new deck.
   */
  public void setCardDeck(CardDeck deck) {
    if (deck == null) {
      throw new IllegalArgumentException("deck can not be null.");
    }

    if (this.deck != null) {
      this.deck.clearSubscribers();
    }

    setupStorageListener(deck);

    this.deck = deck;
    this.deck.subscribe((newDeck) -> updateSubscribers());

    turnToCard(0);

    updateSubscribers();
  }

  private void setupStorageListener(CardDeck deck) {
    deck.subscribe((newCardDeck) -> {
      try {
        cardDeckStorage.writeDeck(newCardDeck, 0);
      } catch (IOException e) {
        System.err.println("Could not save cardDeck");
      }
    });
  }

  /**
   * returns index of card in deck.
   * @return the number of the currently chosen card.
   */
  public int getCardIndex() {
    return this.cardIndex;
  }

  /**
   * Finds card, then returns content displaying.
   * @return the showing side of the currently chosen card.
   */
  public CardContent getCard() {
    var card = this.deck.getFlashcards().get(cardIndex);
    return cardShowsFront ^ showOrderIsFlippedMode
      ? card.getFront() : card.getBack();
  }

  /**
   * Turn to a specified card. This will also turn the card so that it shows the
   * front. If randomMode is activated, this will choose a random card,
   * no matter what cardIndex is given.
   *
   * @param cardIndex The number of the card to turn to.
   */
  public void turnToCard(int cardIndex) throws IllegalArgumentException {
    if (!(firstLastWrapAroundMode || cardOrderIsRandomMode)
        && (cardIndex < 0 || this.deck.getNumberOfCards() <= cardIndex)) {
      throw new IllegalArgumentException(
        "This card does not exist: " + cardIndex);
    }

    if (cardOrderIsRandomMode) {
      cardIndex = generateRandomCardIndex();
    }

    this.cardIndex = cardIndex;

    if (firstLastWrapAroundMode) {
      this.cardIndex = Math.floorMod(this.cardIndex,
      this.deck.getNumberOfCards());
    }
    cardShowsFront = true;
    updateSubscribers();
  }

  /**
   * Turn to the next card in the stack.
   *
   * @see #turnToCard(int) for more details.
   */
  public void turnToNextCard() {
    turnToCard(this.cardIndex + 1);
  }

  /**
   * Turn to the previous card in the stack.
   *
   * @see #turnToCard(int) for more details.
   */
  public void turnToPreviousCard() {
    turnToCard(this.cardIndex - 1);
  }

  private int generateRandomCardIndex() {
    if (deck.getNumberOfCards() <= 1) {
      return 0;
    }

    Random randomGenerator = new Random();
    int nextCard = randomGenerator.nextInt(deck.getNumberOfCards() - 1);
    nextCard = nextCard + (nextCard >= cardIndex ? 1 : 0);
    return nextCard;

  }

  /**
   * Flip the current card to show the other side.
   */
  public void flipCard() {
    this.cardShowsFront = !this.cardShowsFront;
    updateSubscribers();
  }

  /**
   * returns bool front is shown.
   * @return Whether the backside is show first by default.
   */
  public boolean isShowOrderIsFlippedMode() {
    return this.showOrderIsFlippedMode;
  }

  /**
   * tells if card order is random or not.
   * @return Whether turning the card results in a random card.
   */
  public boolean isCardOrderIsRandomMode() {
    return this.cardOrderIsRandomMode;
  }

  /**
   * This is turned on by default.
   *
   * @return Whether the provider shows the first card when executing
   * {@link #turnToNextCard turnToNextCard} at the last card in the deck,
   *     and the same for the other way around.
   */
  public boolean isFirstLastWrapAroundMode() {
    return this.firstLastWrapAroundMode;
  }

  /**
   * Turn on/off ShowOrderIsFlippedMode. This will also save the state to disk.
   *
   * @see #isShowOrderIsFlippedMode()
   */
  public void toggleShowOrderIsFlippedMode() {
    this.showOrderIsFlippedMode = !this.showOrderIsFlippedMode;
    preferences.putBoolean("showOrderIsFlippedMode",
          this.showOrderIsFlippedMode);
    updateSubscribers();
  }

  /**
   * Turn on/off CardOrderIsRandomMode. This will also save the state to disk.
   *
   * @see #isCardOrderIsRandomMode
   */
  public void toggleCardOrderIsRandomMode() {
    this.cardOrderIsRandomMode = !this.cardOrderIsRandomMode;
    preferences.putBoolean("cardOrderIsRandomMode", this.cardOrderIsRandomMode);
    updateSubscribers();
  }

  /**
   * Turn on/off FirstLastWrapAroundMode. This will also save the state to disk.
   *
   * @see #isFirstLastWrapAroundMode()
   */
  public void toggleFirstLastWrapAroundMode() {
    this.firstLastWrapAroundMode = !this.firstLastWrapAroundMode;
    preferences.putBoolean("firstLastWrapAroundMode",
        this.firstLastWrapAroundMode);
    updateSubscribers();
  }

  /**
   * Set all modes to their default values.
   */
  public void resetModes() {
    this.showOrderIsFlippedMode = false;
    this.cardOrderIsRandomMode = false;
    this.firstLastWrapAroundMode = true;
    updateSubscribers();
  }

  /**
   * Clear the stored values from disk.
   */
  public static void clearPreferences() {
    List.of(
      "showOrderIsFlippedMode",
      "cardOrderIsRandomMode",
      "firstLastWrapAroundMode"
    ).forEach(preferences::remove);
  }
}
