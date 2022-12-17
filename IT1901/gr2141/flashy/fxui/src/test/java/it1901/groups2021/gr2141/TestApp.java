package it1901.groups2021.gr2141;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javafx.scene.control.Label;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.lang.Thread;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import it1901.groups2021.gr2141.core.storage.LocalCardDeckStorage;
import it1901.groups2021.gr2141.core.domainlogic.FlashcardProvider;
import it1901.groups2021.gr2141.core.models.CardDeck;
import it1901.groups2021.gr2141.core.models.Flashcard;
import it1901.groups2021.gr2141.core.models.serializers.ModelSerializingModule;
import java.util.function.Function;
import it1901.groups2021.gr2141.ui.AppController;
import it1901.groups2021.gr2141.ui.controllers.CardAreaController;
import it1901.groups2021.gr2141.ui.controllers.CardNavigationController;
import it1901.groups2021.gr2141.ui.controllers.Controller;
import it1901.groups2021.gr2141.ui.controllers.MenuController;
import it1901.groups2021.gr2141.ui.controllers.NavbarController;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class TestApp extends ApplicationTest {

  private static final ObjectMapper mapper = ModelSerializingModule.buildMapper();
  private static final LocalCardDeckStorage localCardDeckStorage = new LocalCardDeckStorage();
  private static FlashcardProvider flashcardProvider;

  private static Map<Class<?>, Function<FlashcardProvider, Controller>> controllerConstructors = Map.of(
    AppController.class, (flashcardProvider) -> AppController.withFlashCardProvider(flashcardProvider),
    CardNavigationController.class, (flashcardProvider) -> CardNavigationController.withFlashCardProvider(flashcardProvider),
    MenuController.class, (flashcardProvider) -> MenuController.withFlashCardProvider(flashcardProvider),
    NavbarController.class, (flashcardProvider) -> NavbarController.withFlashCardProvider(flashcardProvider),
    CardAreaController.class, (flashcardProvider) -> CardAreaController.withFlashCardProvider(flashcardProvider)
  );

  @TempDir
  private static File tempDir;

  private static CardDeck exampleDeck;

  private void executeOnFxThread(Runnable runnable) {
    Platform.runLater(runnable);
    WaitForAsyncUtils.waitForFxEvents();
  }

  private static void moveTestDeckFileToTmpDir() throws IOException, URISyntaxException {
    var testDeckResource = new File(TestApp.class.getResource("/test-deck.json").toURI()).toPath();
    Files.copy(testDeckResource, tempDir.toPath().resolve("deck-0.json"), StandardCopyOption.REPLACE_EXISTING);
  }

  private static void setUpCardStorage() throws IOException, URISyntaxException {
    moveTestDeckFileToTmpDir();
    localCardDeckStorage.setUserDataDirectory(tempDir.toPath());
    flashcardProvider.setCardDeck(flashcardProvider.getCardDeckStorage().readDeck(0));
  }

  @Override
  public void start(Stage stage) throws IOException, URISyntaxException {
    flashcardProvider = new FlashcardProvider(localCardDeckStorage);
    setUpCardStorage();
    flashcardProvider.resetModes();

    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/it1901/groups2021/gr2141/ui/FlashyApp.fxml"));
    fxmlLoader.setControllerFactory(controllerClass -> controllerConstructors.get(controllerClass).apply(flashcardProvider));
    Parent parent = fxmlLoader.load();
    Scene scene = new Scene(parent);

    stage.getIcons().add(new Image(getClass().getResourceAsStream("/it1901/groups2021/gr2141/ui/graphics/logo.png")));
    scene.getStylesheets()
        .add(getClass().getResource("/it1901/groups2021/gr2141/ui/styling/Flashy.css").toExternalForm());
    stage.setTitle("Flashy!");
    stage.setScene(scene);

    stage.show();
  }

  @BeforeEach
  public void setup() throws IOException, URISyntaxException {
    moveTestDeckFileToTmpDir();
    executeOnFxThread(
      () -> {
        try {
          flashcardProvider.setCardDeck(flashcardProvider.getCardDeckStorage().readDeck(0));
        } catch (IOException e) {
          fail();
        }
      }
    );
    
    exampleDeck = mapper.readValue(Files.readString(tempDir.toPath().resolve("deck-0.json")), CardDeck.class);
    flashcardProvider.resetModes();
  }

  private TextField lookupTextField(String id) {
    return (TextField) lookup(id).query();
  }

  private Label lookupLabel(String id) {
    return (Label) lookup(id).query();
  }

  private void assertTextFields(String front, String back) {
    assertEquals(front, lookupTextField("#writeFront").getText());
    assertEquals(back, lookupTextField("#writeBack").getText());
  }

  private String getCardText() {
    return lookupLabel("#showCard").getText();
  }

  @Test
  public void testRealInput() {
      
    clickOn("#writeFront").write("front");
    clickOn("#writeBack").write("back");

    assertTextFields("front", "back");

    clickOn("#btnCreateFlashCard");

    assertTextFields("", "");
  }

  @Test
  public void testWrongInputs() {
    clickOn("#btnCreateFlashCard");

    assertTextFields("", "");

    clickOn("#writeFront").write("front");
    clickOn("#btnCreateFlashCard");
    assertTextFields("front", "");

    clickOn("#writeFront").eraseText("front".length());
    clickOn("#writeBack").write("back");
    clickOn("#btnCreateFlashCard");
    assertTextFields("", "back");
  }

  @ParameterizedTest
  @ValueSource(strings = {"#showCard", "#flip"})
  public void testHandleFlipCardMouseAndButton(String id) {
    Flashcard firstCard = exampleDeck.getFlashcards().get(0);
    assertEquals(firstCard.getFront().getText(), getCardText());
    clickOn(id);
    assertEquals(firstCard.getBack().getText(), getCardText());
    clickOn(id);
    assertEquals(firstCard.getFront().getText(), getCardText());
  }

  @Test
  public void testHandleTurnToNextPreviousCard() {
    String cc1 = getCardText();
    clickOn("#nextCard");
    String cc2 = getCardText();
    clickOn("#lastCard");
    String cc3 = getCardText();

    assertNotEquals(cc1, cc2);
    assertEquals(cc1, cc3);
  }

  @Test
  public void testHandleCreateFlashCard() throws IOException {
    clickOn("#writeFront").write("To be created front");
    clickOn("#writeBack").write("To be created back");
    clickOn("#btnCreateFlashCard");

    List<Flashcard> cards = flashcardProvider.getCardDeckStorage().readDeck(0).getFlashcards();
    var last = cards.get(cards.size() - 1).getFront().getText();

    assertEquals("To be created front", last);
  }

  @Test
  public void testHandleToggleShowOtherSide() {
    String cc1 = getCardText();
    clickOn("#btnToggleShowOtherSide");
    String cc2 = getCardText();
    clickOn("#nextCard");
    String cc3 = getCardText();
    clickOn("#btnToggleShowOtherSide");
    String cc4 = getCardText();

    assertEquals(cc1, exampleDeck.getFlashcards().get(0).getFront().getText());
    assertEquals(cc2, exampleDeck.getFlashcards().get(0).getBack().getText());
    assertEquals(cc3, exampleDeck.getFlashcards().get(1).getBack().getText());
    assertEquals(cc4, exampleDeck.getFlashcards().get(1).getFront().getText());
  }

  @Test
  public void testHandleToggleCardOrderIsRandom(){
    clickOn("#btnToggleFirstLastWrapAroundMode");
    clickOn("#btnToggleRandomMode");
    for (int i=0; i < exampleDeck.getNumberOfCards() * 2; i++) {
      clickOn("#nextCard");
    }
  }

  @Test
  public void testHandleToggleFirstLastWrapAroundModeOff(){
    clickOn("#btnToggleFirstLastWrapAroundMode");

    int cardIndex = flashcardProvider.getCardIndex();
    clickOn("#lastCard");
    assertEquals(cardIndex, flashcardProvider.getCardIndex());
    executeOnFxThread(
      () -> flashcardProvider.turnToCard(exampleDeck.getNumberOfCards() - 1)
    );

    cardIndex = flashcardProvider.getCardIndex();
    clickOn("#nextCard");
    assertEquals(cardIndex, flashcardProvider.getCardIndex());
  }


  @Test
  public void testHandleToggleFirstLastWrapAroundModeOn(){
    String cc1 = getCardText();
    clickOn("#lastCard");
    String cc2 = getCardText();
    clickOn("#nextCard");
    String cc3 = getCardText();

    String ccLast = exampleDeck.getFlashcards().get(exampleDeck.getNumberOfCards() - 1).getFront().getText();
    String ccFirst = exampleDeck.getFlashcards().get(0).getFront().getText();

    assertEquals(ccFirst, cc1);
    assertEquals(cc1, cc3);
    assertEquals(ccLast, cc2);
  }
}
