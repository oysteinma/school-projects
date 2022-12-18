package it1901.groups2021.gr2141.core.models.serializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it1901.groups2021.gr2141.core.models.CardDeck;
import it1901.groups2021.gr2141.core.models.Flashcard;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Deserializer for {@link it1901.groups2021.gr2141.core.models.CardDeck CardDeck}.
 */

public class CardDeckDeserializer extends StdDeserializer<CardDeck> {

  private FlashcardDeserializer flashcardDeserializer = new FlashcardDeserializer();

  /**
   * Constructor sets card deck deserializer to null.
   */
  public CardDeckDeserializer() {
    this(null);
  }

  /**
   * Initialize super object.
   * @param t Deserialize object type.
  */
  public CardDeckDeserializer(Class<CardDeck> t) {
    super(t);
  }

  @Override
  public CardDeck deserialize(JsonParser jparser,
                              DeserializationContext context)
                              throws IOException, JacksonException {

    JsonNode node = jparser.getCodec().readTree(jparser);
    return deserialize(node);
  }

  /**
   * Deserialization logic.
   */
  CardDeck deserialize(JsonNode node) {
    List<Flashcard> cards = new ArrayList<>();

    for (JsonNode n : node.get("flashcards")) {
      cards.add(flashcardDeserializer.deserialize(n));
    }
    return new CardDeck(cards);
  }
}