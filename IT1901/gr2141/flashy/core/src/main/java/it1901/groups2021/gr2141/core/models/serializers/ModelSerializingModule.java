package it1901.groups2021.gr2141.core.models.serializers;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import it1901.groups2021.gr2141.core.models.CardContent;
import it1901.groups2021.gr2141.core.models.CardDeck;
import it1901.groups2021.gr2141.core.models.Flashcard;
/**
 * Uses simple module from the jackson library, to serialize and deserialize data.
*/

public class ModelSerializingModule extends SimpleModule {

  private static final String NAME = "FlashcardModule";

  /**
   * Model that initializes serializing and deserializing.
   * 
   */
  public ModelSerializingModule() {
    super(NAME, Version.unknownVersion());
    addSerializer(CardContent.class, new CardContentSerializer());
    addSerializer(Flashcard.class, new FlashcardSerializer());
    addSerializer(CardDeck.class, new CardDeckSerializer());
    addDeserializer(CardContent.class, new CardContentDeserializer());
    addDeserializer(Flashcard.class, new FlashcardDeserializer());
    addDeserializer(CardDeck.class, new CardDeckDeserializer());
  }

  public static ObjectMapper buildMapper() {
    return new ObjectMapper().registerModule(new ModelSerializingModule());
  }
}
