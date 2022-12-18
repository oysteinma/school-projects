package it1901.groups2021.gr2141.core.models.serializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it1901.groups2021.gr2141.core.models.CardContent;
import it1901.groups2021.gr2141.core.models.Flashcard;
import java.io.IOException;
/**
 * Deserializer for {@link it1901.groups2021.gr2141.core.models.Flashcard Flashcard}.
 */

public class FlashcardDeserializer extends StdDeserializer<Flashcard> {

  private CardContentDeserializer cardContentDeserializer = new CardContentDeserializer();

  /**
   * Constructor sets flashcard deserializer to null.
   */
  public FlashcardDeserializer() {
    this(null);
  }

  /**
   * Initialize super object.
   * @param t Deserialize object type.
   */
  public FlashcardDeserializer(Class<Flashcard> t) {
    super(t);
  }

  @Override
  public Flashcard deserialize(JsonParser jparser,
                               DeserializationContext context)
                               throws IOException, JacksonException {

    JsonNode node = jparser.getCodec().readTree(jparser);

    return deserialize(node);
  }

  /**
   * Deserialization logic.
   */
  Flashcard deserialize(JsonNode node) {
    CardContent front = cardContentDeserializer.deserialize(node.get("front"));
    CardContent back = cardContentDeserializer.deserialize(node.get("back"));

    return new Flashcard(front, back);
  }

}
