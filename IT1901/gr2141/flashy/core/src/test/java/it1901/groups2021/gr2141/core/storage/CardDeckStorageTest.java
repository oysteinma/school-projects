package it1901.groups2021.gr2141.core.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import it1901.groups2021.gr2141.core.models.CardDeck;
import it1901.groups2021.gr2141.core.models.Flashcard;
import it1901.groups2021.gr2141.core.models.serializers.ModelSerializingModule;

public class CardDeckStorageTest {

  private static final ObjectMapper mapper = new ObjectMapper().registerModule(new ModelSerializingModule());
  private static final LocalCardDeckStorage localCardDeckStorage = new LocalCardDeckStorage();

  @TempDir
  private File tempDir;

  private CardDeck exampleDeck1 = new CardDeck(List.of(Flashcard.of("a", "b"), Flashcard.of("b", "a")));
  private CardDeck exampleDeck2 = new CardDeck(List.of(Flashcard.of("abc", "cba"), Flashcard.of("abc", "cba")));

  @BeforeEach
  public void setup() {
    localCardDeckStorage.setUserDataDirectory(tempDir.toPath());
  }

  @Test
  public void testSetUserDataDir() {
    assertThrows(IllegalArgumentException.class, () -> localCardDeckStorage.setUserDataDirectory(null));
    localCardDeckStorage.setUserDataDirectory(tempDir.toPath());
  }

  @Test
  public void testGetUserDataDir() {
    try (MockedStatic<LocalCardDeckStorage> cdsMock = Mockito.mockStatic(LocalCardDeckStorage.class)) {
      cdsMock.when(() -> LocalCardDeckStorage.getUserDataDirectory()).thenCallRealMethod();
      cdsMock.when(() -> LocalCardDeckStorage.getWindowsDataRootPath()).thenReturn(tempDir.toPath());
      cdsMock.when(() -> LocalCardDeckStorage.getUnixDataRootPath()).thenReturn(tempDir.toPath().resolve("anotherFolder"));

      cdsMock.when(() -> LocalCardDeckStorage.getOsName()).thenReturn("win-10");
      assertEquals(tempDir.toPath().resolve("flashy"), LocalCardDeckStorage.getUserDataDirectory());

      cdsMock.when(() -> LocalCardDeckStorage.getOsName()).thenReturn("linux");
      assertEquals(tempDir.toPath().resolve("anotherFolder/flashy"), LocalCardDeckStorage.getUserDataDirectory());
    }
  }

  private void assertContainsDeck(CardDeck deck, String filename) throws IOException {
    assertEquals(
      mapper.writeValueAsString(deck),
      Files.readString(tempDir.toPath().resolve(filename)).replaceAll("\\s", "")
    );
  }

  @Test
  public void testWriteDeck() throws IOException {
    localCardDeckStorage.writeDeck(exampleDeck1);
    localCardDeckStorage.writeDeck(exampleDeck2);
    localCardDeckStorage.writeDeck(exampleDeck1);

    assertContainsDeck(exampleDeck1, "deck-0.json");
    assertContainsDeck(exampleDeck2, "deck-1.json");
    assertContainsDeck(exampleDeck1, "deck-2.json");
  }

  @Test
  public void testWriteDeckWithID() throws IOException {
    assertThrows(IllegalArgumentException.class, () -> localCardDeckStorage.writeDeck(exampleDeck1, -1));

    localCardDeckStorage.writeDeck(exampleDeck1, 3);
    assertContainsDeck(exampleDeck1, "deck-3.json");

    localCardDeckStorage.writeDeck(exampleDeck2, 3);
    assertContainsDeck(exampleDeck2, "deck-3.json");
  }

  @Test
  public void testReadDeck() throws IOException {
    mapper.writeValue(tempDir.toPath().resolve("deck-0.json").toFile(), exampleDeck1);

    checkFrontAndBackIsEqual(0, exampleDeck1, localCardDeckStorage.readDeck(0), true);
    checkFrontAndBackIsEqual(0, exampleDeck1, localCardDeckStorage.readDeck(0), false);
    checkFrontAndBackIsEqual(1, exampleDeck1, localCardDeckStorage.readDeck(0), true);
    checkFrontAndBackIsEqual(1, exampleDeck1, localCardDeckStorage.readDeck(0), false);
  }

  @Test
  public void testReadWriteEquality() throws IOException {
    localCardDeckStorage.writeDeck(exampleDeck1, 0);

    checkFrontAndBackIsEqual(0, exampleDeck1, localCardDeckStorage.readDeck(0), true);
    checkFrontAndBackIsEqual(0, exampleDeck1, localCardDeckStorage.readDeck(0), false);
    checkFrontAndBackIsEqual(1, exampleDeck1, localCardDeckStorage.readDeck(0), true);
    checkFrontAndBackIsEqual(1, exampleDeck1, localCardDeckStorage.readDeck(0), false);
  }
  private void checkFrontAndBackIsEqual(int index, CardDeck exampleDeck1, CardDeck exampleDeck2, boolean side) {
    if (side) {
      assertEquals(exampleDeck1.getFlashcards().get(index).getFront().getText(),
      exampleDeck2.getFlashcards().get(index).getFront().getText());
    } else {
      assertEquals(exampleDeck1.getFlashcards().get(index).getBack().getText(),
      exampleDeck2.getFlashcards().get(index).getBack().getText());
    }
  }

  @Test
  public void testReadAllDecks() throws IOException {
    mapper.writeValue(tempDir.toPath().resolve("deck-0.json").toFile(), exampleDeck1);
    mapper.writeValue(tempDir.toPath().resolve("deck-1.json").toFile(), exampleDeck2);


    checkFrontAndBackIsEqual(0, exampleDeck1, localCardDeckStorage.readDeck(0), true);
    checkFrontAndBackIsEqual(0, exampleDeck1, localCardDeckStorage.readDeck(0), false);
    checkFrontAndBackIsEqual(1, exampleDeck1, localCardDeckStorage.readDeck(0), true);
    checkFrontAndBackIsEqual(1, exampleDeck1, localCardDeckStorage.readDeck(0), false);

    checkFrontAndBackIsEqual(0, exampleDeck2, localCardDeckStorage.readDeck(1), true);
    checkFrontAndBackIsEqual(0, exampleDeck2, localCardDeckStorage.readDeck(1), false);
    checkFrontAndBackIsEqual(1, exampleDeck2, localCardDeckStorage.readDeck(1), true);
    checkFrontAndBackIsEqual(1, exampleDeck2, localCardDeckStorage.readDeck(1), false);
  }
}