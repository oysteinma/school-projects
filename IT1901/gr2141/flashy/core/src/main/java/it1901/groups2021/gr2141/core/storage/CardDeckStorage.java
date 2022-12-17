package it1901.groups2021.gr2141.core.storage;

import it1901.groups2021.gr2141.core.models.CardDeck;
import java.io.IOException;
import java.util.List;


/**
 * A class which handles saving/loading
 * {@link it1901.groups2021.gr2141.core.models.CardDeck CardDecks}.
 */
public interface CardDeckStorage {

  /**
   * Write a card deck to the next available id.
   *
   * @param deck the CardDeck.
   * @throws IOException if deck is not found.
   */
  void writeDeck(CardDeck deck) throws IOException;

  /**
   * Write a card deck with a specified. This will overwrite the deck already
   * saved, if it exists.
   *
   * @param deck The deck to be saved.
   * @param id   The id to write the deck to.
   * @see #writeDeckToFile(File, CardDeck)
   * @throws IOException if deck is not found
   * @throws IllegalArgumentException when id is less than 0
   */
  void writeDeck(CardDeck deck, int id) throws IOException;

  /**
   * Reads a CardDeck from disk with specified id.
   *
   * @param id The id of the CardDeck to read.
   * @return The specified CardDeck.
   * @throws IOException if deck id is not found
   */
  CardDeck readDeck(int id) throws IOException;

  /**
   * Reads all saved CardDecks.
   *
   * @see #readDeck(int)
   * @return A list of all the CardDecks on disk
   * @throws IOException if deck is not found
   */
  List<CardDeck> readAllDecks() throws IOException;
}