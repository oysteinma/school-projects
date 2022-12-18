package it1901.groups2021.gr2141.core.models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it1901.groups2021.gr2141.core.models.CardContent;
import java.io.IOException;
/**
 * Serializer for {@link it1901.groups2021.gr2141.core.models.CardContent CardContent}.
 */

public class CardContentSerializer extends StdSerializer<CardContent> {

  /**
   * Constructor sets card content serializer to null.
   */
  public CardContentSerializer() {
    this(null);
  }

  /**
   * Initialize super object.
   * @param t Serialize object type.
  */
  public CardContentSerializer(Class<CardContent> t) {
    super(t);
  }

  @Override
  public void serialize(CardContent cardContent,
                        JsonGenerator jgenerator,
                        SerializerProvider provider)
                        throws IOException {

    jgenerator.writeStartObject();
    jgenerator.writeFieldName("lines");

    jgenerator.writeStartArray();

    for (String line : cardContent.getLines()) {
      jgenerator.writeString(line);
    }

    jgenerator.writeEndArray();

    jgenerator.writeEndObject();
  }
}
