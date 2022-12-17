package it1901.groups2021.gr2141.core.domainlogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.function.BooleanSupplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it1901.groups2021.gr2141.core.models.CardContent;
import it1901.groups2021.gr2141.core.models.CardDeck;
import it1901.groups2021.gr2141.core.models.Flashcard;
import it1901.groups2021.gr2141.core.storage.LocalCardDeckStorage;

public class FlashcardProviderTest {

  private FlashcardProvider example;
  private LocalCardDeckStorage storage = new LocalCardDeckStorage();
  private CardDeck exampleDeck1;
  private CardDeck exampleDeck2;

  @BeforeEach
  private void setupExampleObjects() {
    exampleDeck1 = new CardDeck(
      List.of(
        Flashcard.of("asdf", "content"),
        Flashcard.of("no bueno", "the test failed"),
        Flashcard.of("0", "1\n2")
      )
    );

    exampleDeck2 = new CardDeck(
      List.of(
        Flashcard.of("another", "flashcard")
      )
    );

    FlashcardProvider.clearPreferences();

    example = new FlashcardProvider(storage);
    example.setCardDeck(exampleDeck1);
    example.toggleFirstLastWrapAroundMode();
  }
  
  @Test
  public void testConstructor() {
    new FlashcardProvider(storage);
  }

  @Test
  public void testCardDeckSetGetters() {
    assertEquals(exampleDeck1, example.getCardDeck());

    assertThrows(IllegalArgumentException.class, () -> example.setCardDeck(null));

    example.setCardDeck(exampleDeck2);
    assertEquals(exampleDeck2, example.getCardDeck());
  }
  
  @Test
  public void testTurnToCard() {
    assertThrows(IllegalArgumentException.class, () -> example.turnToCard(-1));
    assertThrows(IllegalArgumentException.class, () -> example.turnToCard(exampleDeck1.getNumberOfCards() + 1));  

    example.turnToCard(1);
    assertEquals(exampleDeck1.getFlashcards().get(1).getFront(), example.getCard());

    example.flipCard();
    example.turnToCard(0);
    assertEquals(exampleDeck1.getFlashcards().get(0).getFront(), example.getCard());
  }

  @Test
  public void testTurnToNextPreviousCard() {
    example.turnToNextCard();
    assertEquals(1, example.getCardIndex());

    example.turnToPreviousCard();
    assertEquals(0, example.getCardIndex());
  }

  @Test
  public void testTurnToCardWithRandomMode () {
    example.toggleCardOrderIsRandomMode();
    boolean hasTurnedToAnotherCard = false;

    for (int i=0; i<9999; i++) {
      example.turnToCard(0);
      if (!hasTurnedToAnotherCard && example.getCardIndex() != 0)
        hasTurnedToAnotherCard = true;
    }

    assertTrue(hasTurnedToAnotherCard);
  }

  @Test
  public void testTurnToCardWithRandomModeWithDeckSize1 () {
    example.toggleCardOrderIsRandomMode();
    example.setCardDeck(exampleDeck2);
    boolean hasTurnedToAnotherCard = false;

    for (int i=0; i<9999; i++) {
      example.turnToCard(0);
      if (!hasTurnedToAnotherCard && example.getCardIndex() != 0)
        hasTurnedToAnotherCard = true;
    }

    assertFalse(hasTurnedToAnotherCard);
  }

  @Test
  public void testTurnToCardWithWrapAroundMode() {
    example.toggleFirstLastWrapAroundMode();
    int maxIndex = exampleDeck1.getNumberOfCards() - 1;
    int minIndex = 0;

    example.turnToCard(maxIndex + 1);
    assertEquals(minIndex, example.getCardIndex());

    example.turnToCard(minIndex - 1);
    assertEquals(maxIndex, example.getCardIndex());
  }

  @Test
  public void testFlipCard(){
    CardContent front = example.getCard();
    example.flipCard();
    CardContent back = example.getCard();
    example.flipCard();
    CardContent front2 = example.getCard();

    assertNotEquals(front, back);
    assertEquals(front, front2);
  }

  private void toggleAllModes() {
    example.toggleShowOrderIsFlippedMode();
    example.toggleCardOrderIsRandomMode();
    example.toggleFirstLastWrapAroundMode();
  }

  @Test
  public void testModeTogglers() {
    List<BooleanSupplier> modes = List.of(
      () -> example.isShowOrderIsFlippedMode(),
      () -> example.isCardOrderIsRandomMode(),
      () -> example.isFirstLastWrapAroundMode()
    );

    modes.forEach((b) -> assertFalse(b));
    toggleAllModes();
    modes.forEach((b) -> assertTrue(b));
    toggleAllModes();
    modes.forEach((b) -> assertFalse(b));
  }

  @Test
  public void testClearPreferences() {
    toggleAllModes();
    var anotherFlashcardProvider = new FlashcardProvider(storage);
    
    assertTrue(anotherFlashcardProvider.isShowOrderIsFlippedMode());
    assertTrue(anotherFlashcardProvider.isCardOrderIsRandomMode());
    assertTrue(anotherFlashcardProvider.isFirstLastWrapAroundMode());

    toggleAllModes();
    FlashcardProvider.clearPreferences();
    var yetAnotherFlashcardProvider = new FlashcardProvider(storage);

    assertFalse(yetAnotherFlashcardProvider.isShowOrderIsFlippedMode());
    assertFalse(yetAnotherFlashcardProvider.isCardOrderIsRandomMode());
    assertTrue(yetAnotherFlashcardProvider.isFirstLastWrapAroundMode());
  }
}
