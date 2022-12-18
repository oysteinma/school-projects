package it1901.groups2021.gr2141.restserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it1901.groups2021.gr2141.core.models.CardContent;
import it1901.groups2021.gr2141.core.models.CardDeck;
import it1901.groups2021.gr2141.core.models.Flashcard;
import it1901.groups2021.gr2141.core.models.serializers.ModelSerializingModule;
import it1901.groups2021.gr2141.core.storage.LocalCardDeckStorage;
import it1901.groups2021.gr2141.restapi.FlashyModelService;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.glassfish.jersey.test.JerseyTest;

public class ServerTest extends JerseyTest {

  private ObjectMapper objectMapper = ModelSerializingModule.buildMapper();
  private LocalCardDeckStorage localCardDeckStorage;
  private CardDeck exampleCardDeck;
  private Flashcard card1;
  private Flashcard card2;

  @TempDir
  private static File tempDir;

  @Override
  protected ServerConfig configure() {
    localCardDeckStorage = new LocalCardDeckStorage();
    localCardDeckStorage.setUserDataDirectory(tempDir.toPath());
    final ServerConfig sc = new ServerConfig(localCardDeckStorage);
    return sc;
  }

  private static void moveTestDeckFileToTmpDir() throws IOException, URISyntaxException {
    var testDeckResource = new File(ServerTest.class.getResource("/test-deck.json").toURI()).toPath();
    Files.copy(testDeckResource, tempDir.toPath().resolve("deck-0.json"), StandardCopyOption.REPLACE_EXISTING);
  }

  @BeforeAll
  public static void setupAll() throws IOException, URISyntaxException {
    moveTestDeckFileToTmpDir();
  }

  @BeforeEach
  public void setUpExampleDeck() {
      card1 = new Flashcard(new CardContent(List.of("1", "2")), new CardContent(List.of("3")));
      card2 = new Flashcard(new CardContent(List.of("why")), new CardContent(List.of("por qué")));
      List<Flashcard> exampleFlashcardList = List.of(card1, card2);
      exampleCardDeck = new CardDeck(exampleFlashcardList);
  }
  
  @Test
  public void testGetCurrentCardDeck() {
      Response response = target(FlashyModelService.path)
                          .path("0")
                          .request(MediaType.APPLICATION_JSON)
                          .get();
      assertEquals(200, response.getStatus());
      
      try {
        CardDeck cardDeck = objectMapper.readValue(response.readEntity(String.class), CardDeck.class);
        assertEquals(2, cardDeck.getFlashcards().size());

        List<Flashcard> flashCards = cardDeck.getFlashcards();

        assertEquals("front1", flashCards.get(0).getFront().getText());
        assertEquals("back1", flashCards.get(0).getBack().getText());
        assertEquals("front2", flashCards.get(1).getFront().getText());
        assertEquals("back2", flashCards.get(1).getBack().getText());
      } catch (JsonProcessingException e) {
        System.out.println(e);
      }
  }

  @Test
  public void testAddNewCardDeck() throws InterruptedException {
      Entity<CardDeck> cardDeckEntity = Entity.entity(exampleCardDeck, MediaType.APPLICATION_JSON);

      target(FlashyModelService.path)
                      .path("new")
                      .request(MediaType.APPLICATION_JSON)
                      .put(cardDeckEntity);
      
      Response response = target(FlashyModelService.path)
                          .request(MediaType.APPLICATION_JSON)
                          .get();
      assertEquals(200, response.getStatus());
      
      try {
          List<CardDeck> cardDeckList = objectMapper.readValue(response.readEntity(String.class), new TypeReference<List<CardDeck>>(){});
          assertEquals(2, cardDeckList.size());
      } catch (JsonProcessingException e) {
          System.out.println(e);
      }
  }

  @Test
  public void testAddNewFlashCardsToExistingDeck() {
      Response response = target(FlashyModelService.path)
                          .path("0")
                          .request(MediaType.APPLICATION_JSON)
                          .get();
      assertEquals(200, response.getStatus());

      CardDeck cardDeck = new CardDeck();
      try {
          cardDeck = objectMapper.readValue(response.readEntity(String.class), CardDeck.class);
          assertEquals(2, cardDeck.getNumberOfCards());
      } catch (JsonProcessingException e) {
          System.out.println(e);
      }

      cardDeck.add(card1);
      cardDeck.add(card2);
      Entity<CardDeck> cardDeckEntity = Entity.entity(cardDeck, MediaType.APPLICATION_JSON);

      target(FlashyModelService.path)
                      .path("0")
                      .request(MediaType.APPLICATION_JSON)
                      .post(cardDeckEntity);
      
      Response response2 = target(FlashyModelService.path)
                          .path("0")
                          .request(MediaType.APPLICATION_JSON)
                          .get();
      assertEquals(200, response2.getStatus());
      
      CardDeck updatedCardDeck = new CardDeck();
      try {
          updatedCardDeck = objectMapper.readValue(response2.readEntity(String.class), CardDeck.class);
          assertEquals(4, updatedCardDeck.getNumberOfCards());
      } catch (JsonProcessingException e) {
          System.out.println(e);
      }
  }
}

