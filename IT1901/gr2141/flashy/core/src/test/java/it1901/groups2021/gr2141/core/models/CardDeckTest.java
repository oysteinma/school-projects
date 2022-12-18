package it1901.groups2021.gr2141.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardDeckTest {

  List<Flashcard> exampleFlashcardList;
  CardDeck exampleCardDeck;

  @BeforeEach
  public void setUpExampleData() {
    Flashcard card1 = new Flashcard(new CardContent(List.of("1", "2")), new CardContent(List.of("3")));
    Flashcard card2 = new Flashcard(new CardContent(List.of("why")), new CardContent(List.of("por qué")));
    exampleFlashcardList = List.of(card1, card2);

    exampleCardDeck = new CardDeck(exampleFlashcardList);
  }

  @Test
  public void testCardDeck() {
    var cardDeck = new CardDeck();
    assertEquals(new ArrayList<>(), cardDeck.getFlashcards());
  }

  @Test
  public void testNullInput() {
    assertThrows(IllegalArgumentException.class, () -> new CardDeck(null));
  }

  @Test
  public void testEmptyListInput() {
    var cardDeck = new CardDeck(List.of());
    assertEquals(0, cardDeck.getNumberOfCards());
  }

  @Test
  public void testRealInput() {
    CardDeck cardDeck = new CardDeck(exampleFlashcardList);
    assertEquals(exampleFlashcardList, cardDeck.getFlashcards());
  }

  @Test
  public void testEquality() {
    var cardDeck1 = new CardDeck(exampleFlashcardList);
    var cardDeck2 = new CardDeck(exampleFlashcardList);

    assertNotEquals(cardDeck1, new Object());
    assertNotEquals(cardDeck1.hashCode(), cardDeck2.hashCode());

    checkFrontAndBackIsEqual(0, cardDeck1, cardDeck2, true);
    checkFrontAndBackIsEqual(0, cardDeck1, cardDeck2, false);
    checkFrontAndBackIsEqual(1, cardDeck1, cardDeck2, true);
    checkFrontAndBackIsEqual(1, cardDeck1, cardDeck2, false);
  }

  private void checkFrontAndBackIsEqual(int index, CardDeck cardDeck1, CardDeck cardDeck2, boolean side) {
    if (side) {
      assertEquals(cardDeck1.getFlashcards().get(index).getFront().getText(),
      cardDeck2.getFlashcards().get(index).getFront().getText());
    } else {
      assertEquals(cardDeck1.getFlashcards().get(index).getBack().getText(),
      cardDeck2.getFlashcards().get(index).getBack().getText());
    }
  }
  
  @Test
  public void testIsEmpty() {
    CardDeck exampleCardDeck2 = new CardDeck(); 
    assertFalse(exampleCardDeck.isEmpty());
    assertTrue(exampleCardDeck2.isEmpty());
  }

  @Test
  public void testAdd() {
    assertThrows(IllegalArgumentException.class, () -> exampleCardDeck.add(null));

    var newCard = new  Flashcard(new CardContent(List.of("Yes", "No")), new CardContent(List.of("Maybe")));
    exampleCardDeck.add(newCard);

    var addedCard = exampleCardDeck.getFlashcards().get(exampleCardDeck.getNumberOfCards()- 1);
    assertEquals(newCard, addedCard);
  }

  @Test
  public void testAddWithIndex() {

    assertThrows(IllegalArgumentException.class, () -> exampleCardDeck.add(0, null));

    var newCard = new  Flashcard(new CardContent(List.of("Yes", "No")), new CardContent(List.of("Maybe")));
    assertThrows(IllegalArgumentException.class, () -> exampleCardDeck.add(-1, newCard));
    assertThrows(IllegalArgumentException.class, () -> exampleCardDeck.add(exampleCardDeck.getNumberOfCards() + 1, newCard));

    int previousSize = exampleCardDeck.getNumberOfCards();
    exampleCardDeck.add(1, newCard);

    var addedCard = exampleCardDeck.getFlashcards().get(1);
    assertEquals(newCard, addedCard);
    assertEquals(previousSize + 1, exampleCardDeck.getNumberOfCards());
  }

  @Test
  public void testRemove() {
    assertThrows(IllegalArgumentException.class, () -> exampleCardDeck.remove(-1));
    assertThrows(IllegalArgumentException.class, () -> exampleCardDeck.remove(exampleCardDeck.getNumberOfCards() + 1));

    var removeCard = exampleCardDeck.getFlashcards().get(0);
    exampleCardDeck.remove(0);

    assertNotEquals(removeCard, exampleCardDeck.getFlashcards().get(0));
  }

  @Test
  public void testUpdate() {
    var exampleFlashCard = new Flashcard(new CardContent(List.of("1", "3")), new CardContent(List.of("3")));

    assertThrows(IllegalArgumentException.class, () -> exampleCardDeck.update(exampleCardDeck.getNumberOfCards() - 1, null));
    assertThrows(IllegalArgumentException.class, () -> exampleCardDeck.update(exampleCardDeck.getNumberOfCards() + 1, exampleFlashCard));
    assertThrows(IllegalArgumentException.class, () -> exampleCardDeck.update(-1, exampleFlashCard));

    var oldCard = exampleCardDeck.getFlashcards().get(0);
    exampleCardDeck.update(0, exampleFlashCard);
    var updatedCard = exampleCardDeck.getFlashcards().get(0);

    assertNotEquals(updatedCard, oldCard);
  }

  @Test
  public void testToString() {
    String defaultToString = exampleCardDeck.getClass().getName() + "@" + Integer.toHexString(exampleCardDeck.hashCode());
    assertNotEquals(defaultToString, exampleCardDeck.toString());
  }

}

