package it1901.groups2021.gr2141.core.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import it1901.groups2021.gr2141.core.models.CardDeck;
import it1901.groups2021.gr2141.core.models.serializers.ModelSerializingModule;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A {@link it1901.groups2021.gr2141.core.storage.CardDeckStorage CardDeckStorage}
 * which stores {@link it1901.groups2021.gr2141.core.models CardDeck CardDeck}s locally.
 */
public final class LocalCardDeckStorage implements CardDeckStorage {

  private static final ObjectMapper mapper = ModelSerializingModule.buildMapper();

  private Path dataDirectory = getUserDataDirectory();

  /**
   * Override the default data directory with a new one.
   *
   * @param dataDirectory The path to the new data directory.
   */
  public void setUserDataDirectory(Path dataDirectory) {
    if (dataDirectory == null) {
      throw new IllegalArgumentException("dataDirectory can not be null");
    }
    this.dataDirectory = dataDirectory;
  }

  /**
   * gets the operating system name for local storage.
   * @return Name of operating system
   */
  protected static String getOsName() {
    return System.getProperty("os.name").toLowerCase();
  }

  /**
   * returns the path to appdata on windows.
   * @return Data directory for all Windows systems.
   */
  protected static Path getWindowsDataRootPath() {
    return Paths.get(System.getenv("AppData"));
  }

  /**
   * Data directory for all UNIX-like systems. This is the default
   * location according to the XDG-specification.
   * @return path
   * @see <a href=
   *      "https://specifications.freedesktop.org/basedir-spec/basedir-spec-latest.html">specifications.freedesktop.org</a>
   */
  protected static Path getUnixDataRootPath() {
    return Paths.get(System.getProperty("user.home"), ".local/share");
  }

  /**
   * Sets a directory where the user data is saved.
   *
   * @return the directory
   * @see #getDataFiles()
   * @see #writeDeck(CardDeck)
   * @see #readDeck(int)
   */
  public static Path getUserDataDirectory() {
    String os = getOsName();
    Path dataDir;

    if (os.contains("win")) {
      dataDir = getWindowsDataRootPath();
    } else {
      dataDir = getUnixDataRootPath();
    }
    dataDir = dataDir.resolve("flashy");
    if (!dataDir.toFile().exists()) {
      dataDir.toFile().mkdirs();
    }
    return dataDir;
  }

  private CardDeck readDeckFromFile(File file) throws IOException {
    return mapper.readValue(file, CardDeck.class);
  }

  private void writeDeckToFile(File file, CardDeck deck) throws IOException {
    mapper.writerWithDefaultPrettyPrinter().writeValue(file, deck);
  }

  private List<File> getDataFiles() {
    return Arrays.asList(dataDirectory.toFile().listFiles());
  }

  private int getNextDeckID() {
    var i = 0;
    while (dataDirectory.resolve("deck-" + i + ".json").toFile().exists()) {
      i++;
    }
    return i;
  }

  /**
   * Writes to deck and stores in local path.
   */
  public void writeDeck(CardDeck deck) throws IOException {
    writeDeck(deck, getNextDeckID());
  }

  /**
   * Write card deck to given index.
   * @param deck the deck
   * @param id id of deck
   */
  public void writeDeck(CardDeck deck, int id) throws IOException {
    if (id < 0) {
      throw new IllegalArgumentException("ID can not be negative!");
    }
    File file = dataDirectory.resolve("deck-" + Integer.toString(id) + ".json").toFile();

    if (!file.exists()) {
      file.createNewFile();
    }
    writeDeckToFile(file, deck);
  }

  /**
   * Reads the stored deck.
   * @id id of deck
   * @return card deck.
   */
  public CardDeck readDeck(int id) throws IOException {
    File file = dataDirectory.resolve("deck-" + Integer.toString(id) + ".json").toFile();
    return readDeckFromFile(file);
  }

  /**
   * Reads all decks and returns result.
   * @return list of card decks.
   */
  public List<CardDeck> readAllDecks() throws IOException {
    List<CardDeck> result = new ArrayList<>();
    for (File f : getDataFiles()) {
      result.add(readDeckFromFile(f));
    }
    return result;
  }
}
