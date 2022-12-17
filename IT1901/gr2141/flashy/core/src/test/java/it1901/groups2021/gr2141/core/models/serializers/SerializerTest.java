package it1901.groups2021.gr2141.core.models.serializers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it1901.groups2021.gr2141.core.models.CardContent;
import it1901.groups2021.gr2141.core.models.CardDeck;
import it1901.groups2021.gr2141.core.models.Flashcard;

public class SerializerTest {

  private static ObjectMapper mapper;
  private CardDeck exampleCardDeck;
  private Flashcard exampleFlashcard;
  private CardContent exampleCardContent;
  private String exampleSerializedCardDeck;
  private String exampleSerializedFlashcard;
  private String exampleSerializedCardContent;

  @BeforeAll
  public static void setUpMapper() {
    mapper = ModelSerializingModule.buildMapper();
  }

  @BeforeEach
  public void setUpExampleData() {
    exampleCardContent = new CardContent("A cow");
    exampleFlashcard = new Flashcard(exampleCardContent, new CardContent("Nǎiniú\n奶牛"));
    exampleCardDeck = new CardDeck(List.of(exampleFlashcard));

    exampleSerializedCardContent = "{\"lines\":[\"A cow\"]}";
    exampleSerializedFlashcard = "{\"front\":" + exampleSerializedCardContent + ",\"back\":{\"lines\":[\"Nǎiniú\",\"奶牛\"]}}";
    exampleSerializedCardDeck = "{\"flashcards\":[" + exampleSerializedFlashcard + "]}";
  }

  @Test
  public void testSerializeExampleObject() {
    String serializedCardDeck = null;
    try {
      serializedCardDeck = mapper.writeValueAsString(exampleCardDeck);
    } catch (JsonProcessingException e) {
      fail(e);
    }
    assertEquals(exampleSerializedCardDeck, serializedCardDeck);
  }

  @Test
  public void testDeserializeExampleCardDeck() {
    CardDeck cardDeck = null;
    try {
      cardDeck = mapper.readValue(exampleSerializedCardDeck, CardDeck.class);
    } catch (JsonProcessingException e) {
      fail(e);
    }
    assertEquals(exampleCardDeck.getFlashcards().get(0).getFront().getLines(), 
                 cardDeck.getFlashcards().get(0).getFront().getLines());
    assertEquals(exampleCardDeck.getFlashcards().get(0).getBack().getLines(), 
                 cardDeck.getFlashcards().get(0).getBack().getLines());
  }

  @Test
  public void testDeserializeExampleFlashcard() {
    Flashcard card = null;
    try {
      card = mapper.readValue(exampleSerializedFlashcard, Flashcard.class);
    } catch (JsonProcessingException e) {
      fail(e);
    }
    assertEquals(exampleFlashcard.getFront().getLines(), 
                 card.getFront().getLines());
    
    assertEquals(exampleFlashcard.getBack().getLines(), 
                 card.getBack().getLines());
    
  }

  @Test
  public void testDeserializeExampleCardContent() {
    CardContent content = null;
    try {
      content = mapper.readValue(exampleSerializedCardContent, CardContent.class);
    } catch (JsonProcessingException e) {
      fail(e);
    }
    assertEquals(exampleCardContent.getLines(), content.getLines());
  }

  @Test
  public void testSerializeDeserializeEquals() {
    CardDeck cardDeck2 = null;
    try {
      String deserializedCardDeck = mapper.writeValueAsString(exampleCardDeck);
      cardDeck2 = mapper.readValue(deserializedCardDeck, CardDeck.class);
    } catch (JsonProcessingException e) {
      fail(e);
    }
    assertEquals(exampleCardDeck.getFlashcards().get(0).getFront().getLines(), 
                 cardDeck2.getFlashcards().get(0).getFront().getLines());

    assertEquals(exampleCardDeck.getFlashcards().get(0).getBack().getLines(),
                 cardDeck2.getFlashcards().get(0).getBack().getLines());
  }
}
