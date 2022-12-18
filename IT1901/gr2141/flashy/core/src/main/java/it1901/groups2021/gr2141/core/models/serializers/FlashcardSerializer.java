package it1901.groups2021.gr2141.core.models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it1901.groups2021.gr2141.core.models.Flashcard;
import java.io.IOException;
/**
 * Serializer for {@link it1901.groups2021.gr2141.core.models.Flashcard Flashcard}.
 */

public class FlashcardSerializer extends StdSerializer<Flashcard> {

  /**
   * Constructor sets flashcard serializer to null.
   */
  public FlashcardSerializer() {
    this(null);
  }

  /**
   * Initialize super object.
   * @param t Serialize object type.
   */
  public FlashcardSerializer(Class<Flashcard> t) {
    super(t);
  }

  @Override
  public void serialize(Flashcard flashcard,
                        JsonGenerator jgenerator,
                        SerializerProvider provider)
                        throws IOException {

    jgenerator.writeStartObject();
    jgenerator.writeFieldName("front");
    jgenerator.writeObject(flashcard.getFront());
    jgenerator.writeFieldName("back");
    jgenerator.writeObject(flashcard.getBack());
    jgenerator.writeEndObject();
  }
}
