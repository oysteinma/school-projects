package it1901.groups2021.gr2141.core.models.serializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it1901.groups2021.gr2141.core.models.CardContent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Deserializer for
 * {@link it1901.groups2021.gr2141.core.models.CardContent CardContent}.
 */

public class CardContentDeserializer extends StdDeserializer<CardContent> {

  /**
   * Constructor sets card content deserializer to null.
   */
  public CardContentDeserializer() {
    this(null);
  }

  /**
   * Initialize super object.
   * @param t Deserialize object type.
  */
  public CardContentDeserializer(Class<CardContent> t) {
    super(t);
  }

  @Override
  public CardContent deserialize(JsonParser jparser, DeserializationContext context)
      throws IOException, JacksonException {
    JsonNode node = jparser.getCodec().readTree(jparser);
    return deserialize(node);
  }

  /**
   * Deserialization logic.
   */
  CardContent deserialize(JsonNode node) {
    List<String> lines = new ArrayList<>();

    for (JsonNode n : node.get("lines")) {
      lines.add(n.asText());
    }
    return new CardContent(lines);
  }
}
