package it1901.groups2021.gr2141.core.models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it1901.groups2021.gr2141.core.models.CardDeck;
import java.io.IOException;
/**
 * Serializer for {@link it1901.groups2021.gr2141.core.models.CardDeck CardDeck}.
 */

public class CardDeckSerializer extends StdSerializer<CardDeck> {

  /**
   * Constructor sets card deck serializer to null.
   */
  public CardDeckSerializer() {
    this(null);
  }

  /**
   * Initialize super object.
   * @param t Serialize object type.
   */
  public CardDeckSerializer(Class<CardDeck> t) {
    super(t);
  }

  @Override
  public void serialize(CardDeck cardDeck, 
                        JsonGenerator jgenerator,
                        SerializerProvider provider)
                        throws IOException {

    jgenerator.writeStartObject();
    jgenerator.writeFieldName("flashcards");
    jgenerator.writeStartArray();

    for (var flashcard : cardDeck.getFlashcards()) {
      jgenerator.writeObject(flashcard);
    }
    jgenerator.writeEndArray();
    jgenerator.writeEndObject();
  }
}