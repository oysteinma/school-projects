package it1901.groups2021.gr2141.core.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import it1901.groups2021.gr2141.core.models.CardDeck;
import it1901.groups2021.gr2141.core.models.serializers.ModelSerializingModule;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * A {@link it1901.groups2021.gr2141.core.storage.CardDeckStorage CardDeckStorage}
 * which stores {@link it1901.groups2021.gr2141.core.models CardDeck CardDeck}s via HTTP
 * to the {@link it1901.groups2021.gr2141.restserver.Server REST server}.
 */
public class RemoteCardDeckStorage implements CardDeckStorage {
  private static ObjectMapper mapper = ModelSerializingModule.buildMapper();
  private URI serverAddress;

  /**
   * Sets server address.
   * @param serverAddress URI of the REST server, including address and port
   */
  public RemoteCardDeckStorage(URI serverAddress) {
    setServerAddress(serverAddress);
  }

  /**
   * Set the server address of the REST server.
   * @param serverAddress URI of the REST server, including address and port
   */
  public void setServerAddress(URI serverAddress) {
    if (serverAddress == null) {
      throw new IllegalArgumentException("serverAdress can not be null");
    }
    this.serverAddress = serverAddress;
  }

  /**
   * Writes a new deck to next available index.
   */
  public void writeDeck(CardDeck deck) throws IOException {
    if (deck == null) {
      throw new IllegalArgumentException("deck can not be null");
    }
    try {
      final HttpRequest request = HttpRequest
          .newBuilder(serverAddress.resolve("/flashy/deck/new"))
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .PUT(BodyPublishers.ofString(mapper.writeValueAsString(deck)))
          .build();

      final HttpResponse<String> response =
          HttpClient
          .newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());

      if (!Boolean.parseBoolean(response.body())) {
        throw new IOException(Integer.toString(response.statusCode()));
      }
    } catch (InterruptedException e) {
      throw new IOException(e);
    }
  }

  /**
   * Write deck to given index.
   * @param id id of card in deck
   */
  public void writeDeck(CardDeck deck, int id) throws IOException {
    if (deck == null) {
      throw new IllegalArgumentException("deck can not be null");
    }

    try {
      final HttpRequest request = HttpRequest
          .newBuilder(serverAddress.resolve("/flashy/deck/" + id))
          .header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString(mapper.writeValueAsString(deck)))
          .build();

      System.out.println(request.toString());

      final HttpResponse<String> response =
          HttpClient
          .newBuilder()
          .build()
          .send(request, HttpResponse.BodyHandlers.ofString());

      if (!(Boolean.parseBoolean(response.body()))) {
        throw new IOException(Integer.toString(response.statusCode()));
      }
    } catch (InterruptedException e) {
      throw new IOException(e);
    }
  }

  /**
   * Reads a deck.
   * @id id of card
   * @returns a card deck
   */
  public CardDeck readDeck(int id) throws IOException {
    try {
      final HttpRequest request = HttpRequest

          .newBuilder(serverAddress.resolve("/flashy/deck/" + id))
          .header("Accept", "application/json")
          .GET()
          .build();

      final HttpResponse<String> response =
          HttpClient
            .newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());

      return mapper.readValue(response.body(), CardDeck.class);
    } catch (InterruptedException e) {
      throw new IOException(e);
    }
  }

  /**
   * Reads all decks.
   * @returns a list of card decks
   */

  public List<CardDeck> readAllDecks() throws IOException {
    try {
      final HttpRequest request = HttpRequest
          .newBuilder(serverAddress.resolve("/flashy/deck/"))
          .header("Accept", "application/json")
          .GET()
          .build();

      final HttpResponse<String> response =
          HttpClient
            .newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());

      return mapper.readerForListOf(CardDeck.class).readValue(response.body());
    } catch (InterruptedException e) {
      throw new IOException(e);
    }
  }

}