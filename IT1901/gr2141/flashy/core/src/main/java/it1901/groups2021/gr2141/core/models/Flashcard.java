package it1901.groups2021.gr2141.core.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import it1901.groups2021.gr2141.core.models.serializers.FlashcardDeserializer;
import java.util.List;


/**
 * Data model representing a flashcard.
 * Has a frontside and a backside, which both consist of {@link CardContent CardContent}.
 */
@JsonDeserialize(using = FlashcardDeserializer.class)
public class Flashcard {
  private CardContent front;
  private CardContent back;

  /**
   * Default constructor to generate a new flashcard.
   *
   * @param front The front side of the card
   * @param back The back side of the card
   */
  public Flashcard(CardContent front, CardContent back) {
    if (front == null || back == null) {
      throw new IllegalArgumentException("CardContent can not be null");
    }
    this.front = front;
    this.back = back;
  }

  /**
   * A helping constructor to generate Flashcards from lines of text.
   *
   * @param front The (text) lines on the front side of the card
   * @param back The (text) lines on the back side of the card
   * @return A new flashcard
   *
   * @see CardContent#CardContent(List) to check whether the lists provided are valid.
   */
  public static Flashcard of(List<String> front, List<String> back) {
    if (
        front == null
        || back == null
        || front.stream().allMatch((e) -> e.equals(""))
        || back.stream().allMatch((e) -> e.equals(""))
    ) {
      throw new IllegalArgumentException("Front and back can not be null");
    }
    return new Flashcard(new CardContent(front), new CardContent(back));
  }

  /**
   * A helping constructor to generate Flashcards from strings.
   *
   * @param front The text on the front side of the card
   * @param back The text on the back side of the card
   * @return A new flashcard
   */
  public static Flashcard of(String front, String back) {
    if (front == null || back == null) {
      throw new IllegalArgumentException("Front and back can not be null");
    }
    return new Flashcard(new CardContent(front), new CardContent(back));
  }

  @Override
  public String toString() {
    return String.format("[%s] [%s]", String.join("/", this.front.getLines()),
      String.join("/", this.back.getLines()));
  }

  /**
   * Returns the front of card.
   * @return front
   */
  public CardContent getFront() {
    return front;
  }

  /**
   * Returns back of card.
   * @return back
   */
  public CardContent getBack() {
    return back;
  }

  /**
   * Sets the front side of card.
   * @param front Front side of the card
   */
  public void setFront(CardContent front) {
    this.front = front;
  }

  /**
   * Sets the back side of card.
   * @param back Back side of the card
   */
  public void setBack(CardContent back) {
    this.back = back;
  }

}
