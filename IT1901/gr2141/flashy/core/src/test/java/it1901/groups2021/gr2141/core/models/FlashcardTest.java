package it1901.groups2021.gr2141.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import java.util.AbstractMap.SimpleEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class FlashcardTest {

  CardContent exampleCardContent1;
  CardContent exampleCardContent2;

  @BeforeEach
  public void setUpExampleData() {
    exampleCardContent1 = new CardContent(List.of("1"));
    exampleCardContent2 = new CardContent(List.of("2", "3"));
  }

  @Test
  public void testConstructors() {
    Flashcard card = new Flashcard(exampleCardContent1, exampleCardContent2);
    Flashcard card2 = Flashcard.of(List.of("1"), List.of("2", "3"));
    Flashcard card3 = Flashcard.of("1", "2\n3");

    assertEquals(card.getFront(), exampleCardContent1);
    assertEquals(card.getBack(), exampleCardContent2);

    assertEquals(card2.getFront().getLines(), exampleCardContent1.getLines());
    assertEquals(card2.getBack().getLines(), exampleCardContent2.getLines());

    assertEquals(card3.getFront().getLines(), exampleCardContent1.getLines());
    assertEquals(card3.getBack().getLines(), exampleCardContent2.getLines());
  }

  @ParameterizedTest
  @MethodSource
  public void testNullInput(CardContent c1, CardContent c2) {
    assertThrows(IllegalArgumentException.class, () -> new Flashcard(c1, c2));
  }

  private static Stream<Arguments> testNullInput() {
    return Stream.of(
            Arguments.of(null, null),
            Arguments.of(null, new CardContent(List.of("1"))),
            Arguments.of(new CardContent(List.of("1")), null)
        );

  }

  private static Entry<String, String> e(String a, String b) {
    return new SimpleEntry<>(a, b);
  }


  private static Stream<Entry<String, String>> getCardContentCreationArguments() {
    return Stream.of(
      e("", ""),
      e("1", ""),
      e("", "1"),
      e(null, "1"),
      e("1", null),
      e("1", null)
    );
  }

  @ParameterizedTest
  @MethodSource
  public void testFlashCardOfList(List<String> front, List<String> back) {

    assertThrows(IllegalArgumentException.class, () -> Flashcard.of(front, back)); 
  }

  private static Stream<Arguments> testFlashCardOfList() {
    return getCardContentCreationArguments().map(
      (e) -> Arguments.of(
        e.getKey() == null ? null : List.of(e.getKey()),
        e.getValue() == null ? null : List.of(e.getValue())
        )
      );
  }

  @ParameterizedTest
  @MethodSource
  public void testFlashCardOfString(String front, String back) {
    assertThrows(IllegalArgumentException.class, () -> Flashcard.of(front, back));
  }

  private static Stream<Arguments> testFlashCardOfString() {
    return getCardContentCreationArguments().map((e) -> Arguments.of(e.getKey(), e.getValue()));
  }

  @Test
  public void testEquality() {
    Flashcard card1 = new Flashcard(exampleCardContent1, exampleCardContent2);
    Flashcard card2 = new Flashcard(exampleCardContent1, exampleCardContent2);
    Flashcard card3 = new Flashcard(exampleCardContent1, exampleCardContent1);
    Flashcard card4 = new Flashcard(exampleCardContent2, exampleCardContent2);

    assertNotEquals(card1, new Object());

    assertNotEquals(card1.hashCode(), card2.hashCode());
    assertEquals(card1.getFront().getLines(), card2.getFront().getLines());

    Map.of(
      card1, card3, 
      card4, card1, 
      card3, card4  
    ).entrySet().forEach((e) -> {
      var c1 = e.getKey();
      var c2 = e.getValue();
      assertNotEquals(c1, c2);
    });
  }

  @Test
  public void testSetFront() {
    Flashcard card = new Flashcard(exampleCardContent1, exampleCardContent2);
    CardContent exampleCardContentFront = new CardContent(List.of("15"));

    card.setFront(exampleCardContentFront);
    assertEquals(card.getFront(), exampleCardContentFront);
  }

  @Test
  public void testSetBack() {
    Flashcard card = new Flashcard(exampleCardContent1, exampleCardContent2);
    CardContent exampleCardContentFront = new CardContent(List.of("15"));

    card.setBack(exampleCardContentFront);
    assertEquals(card.getBack(), exampleCardContentFront);
  }

  @Test
  public void testToString() {
    Flashcard card = new Flashcard(exampleCardContent1, exampleCardContent2);
    String defaultToString = card.getClass().getName() + "@" + Integer.toHexString(card.hashCode());
    assertNotEquals(defaultToString, card.toString());
  }  
}
