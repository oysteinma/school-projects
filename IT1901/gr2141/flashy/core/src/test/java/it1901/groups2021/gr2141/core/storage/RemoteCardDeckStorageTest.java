package it1901.groups2021.gr2141.core.storage;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.WireMockServer;
import it1901.groups2021.gr2141.core.models.CardDeck;
import it1901.groups2021.gr2141.core.models.Flashcard;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class RemoteCardDeckStorageTest {

    private RemoteCardDeckStorage remoteCardDeckStorage;
    private String exampleSerializedFlashcard;
    private String exampleSerializedFlashCard2;
    private String exampleSerializedCardDeck;

    private WireMockConfiguration config;
    private WireMockServer wireMockServer;

    @BeforeEach
    public void startWireMockServerAndSetup() {
        config = WireMockConfiguration.wireMockConfig().port(8089);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        remoteCardDeckStorage = new RemoteCardDeckStorage(URI.create(String.format("http://%s:%s", "localhost", wireMockServer.port())));

        exampleSerializedFlashcard = "[{\"front\": {\"lines\": [\"TestCard1Front\"]},\"back\": {\"lines\": [\"TestCard1Back\"]}}";
        exampleSerializedFlashCard2 = ",{\"front\": {\"lines\": [\"TestCard2Front\"]},\"back\": {\"lines\": [\"TestCard2Back\"]}}";
        exampleSerializedCardDeck = "{ \"flashcards\": " + exampleSerializedFlashcard + exampleSerializedFlashCard2 + "]}";
    }

    @Test
    public void testWriteDeckWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> remoteCardDeckStorage.setServerAddress(null));
        assertThrows(IllegalArgumentException.class, () -> remoteCardDeckStorage.writeDeck(null));
        assertThrows(IllegalArgumentException.class, () -> remoteCardDeckStorage.writeDeck(null, 0));
    }

    @Test
    public void testWriteWithWrongInput() {
        assertThrows(IOException.class, () -> remoteCardDeckStorage.writeDeck(new CardDeck()));
        assertThrows(IOException.class, () -> remoteCardDeckStorage.writeDeck(new CardDeck(), 0)); 
    }

    @Test
    public void testReadDeckWithIndex() throws IOException {
        givenThat(get(urlEqualTo("/flashy/deck/" +  0))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(exampleSerializedCardDeck)
            )
        );

        List<Flashcard> flashCards = remoteCardDeckStorage.readDeck(0).getFlashcards();

        assertEquals(flashCards.size(), 2);

        assertEquals("TestCard1Front", flashCards.get(0).getFront().getText());
        assertEquals("TestCard1Back", flashCards.get(0).getBack().getText());
        assertEquals("TestCard2Front", flashCards.get(1).getFront().getText());
        assertEquals("TestCard2Back", flashCards.get(1).getBack().getText());
    }

    @Test
    public void testReadAllDecks() throws IOException {
        givenThat(get(urlEqualTo("/flashy/deck/"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(String.format("[%s,%s]", exampleSerializedCardDeck, exampleSerializedCardDeck))
            )
        );

        List<CardDeck> cardDecksList = remoteCardDeckStorage.readAllDecks();

        assertEquals(2, cardDecksList.size());
        assertEquals(2, cardDecksList.get(0).getNumberOfCards());
        assertEquals(2, cardDecksList.get(1).getNumberOfCards());

        assertEquals("TestCard1Front", cardDecksList.get(0).getFlashcards().get(0).getFront().getText());
        assertEquals("TestCard1Back", cardDecksList.get(0).getFlashcards().get(0).getBack().getText());
        assertEquals("TestCard1Front", cardDecksList.get(1).getFlashcards().get(0).getFront().getText());
        assertEquals("TestCard1Back", cardDecksList.get(1).getFlashcards().get(0).getBack().getText());
    }

    @AfterEach
    public void stopWireMockServer() {
        wireMockServer.stop();
  }
}
