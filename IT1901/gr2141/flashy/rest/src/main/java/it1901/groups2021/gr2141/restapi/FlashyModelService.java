package it1901.groups2021.gr2141.restapi;

import it1901.groups2021.gr2141.core.models.CardDeck;
import it1901.groups2021.gr2141.core.storage.LocalCardDeckStorage;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

/**
 * Main module for the REST API.
 *
 * @see it1901.groups2021.gr2141.core.storage.RemoteCardDeckStorage
 */
@Path("/flashy/deck")
@Produces(MediaType.APPLICATION_JSON)
public class FlashyModelService {

  public static String path = "/flashy/deck";

  @Context
  private LocalCardDeckStorage localCardDeckStorage;

  /**
   * Gets all decks and reads.
   * @return a deck
   * @throws IOException if none deck to be found
   */

  @GET
  public List<CardDeck> getAllDecks() throws IOException {
    var deck = localCardDeckStorage.readAllDecks();
    return deck;
  }

  /**
   * Reads a single deck from index.
   * @param index index of deck
   * @return the deck
   * @throws IOException if index doesn't exist
   */

  @GET
  @Path("/{index}")
  public CardDeck getCurrentCardDeck(@PathParam("index") String index) throws IOException {
    var deck = localCardDeckStorage.readDeck(Integer.parseInt(index));
    return deck;
  }

  /**
   * saves deck in path, if index already exisist it overwrites old deck.
   * @param deck deck to save
   * @param index to specify where
   * @return bool
   */

  @POST
  @Path("/{index}")
  @Consumes(MediaType.APPLICATION_JSON)
  public boolean saveCurrentCardDeck(CardDeck deck, @PathParam("index") String index) {
    try {
      localCardDeckStorage.writeDeck(deck, Integer.parseInt(index));
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * saves deck to next free slot/index.
   * @param deck deck to save
   * @return bool
   */

  @PUT
  @Path("/new")
  @Consumes(MediaType.APPLICATION_JSON)
  public boolean saveCurrentCardDeck(CardDeck deck) {
    try {
      localCardDeckStorage.writeDeck(deck);
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}