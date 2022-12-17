package it1901.groups2021.gr2141.ui;

import it1901.groups2021.gr2141.core.domainlogic.FlashcardProvider;
import it1901.groups2021.gr2141.core.storage.LocalCardDeckStorage;
import it1901.groups2021.gr2141.core.storage.RemoteCardDeckStorage;
import it1901.groups2021.gr2141.ui.controllers.CardAreaController;
import it1901.groups2021.gr2141.ui.controllers.CardNavigationController;
import it1901.groups2021.gr2141.ui.controllers.Controller;
import it1901.groups2021.gr2141.ui.controllers.MenuController;
import it1901.groups2021.gr2141.ui.controllers.NavbarController;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



/**
 * Root of the application.
 */
public class Flashy extends Application {

  private static boolean remoteMode;
  private static String address = "localhost";
  private static String port = "8080";

  private static Map<Class<?>, Function<FlashcardProvider, Controller>> controllerConstructors
      = Map.of(AppController.class, (flashcardProvider) -> AppController
                                                      .withFlashCardProvider(flashcardProvider),
      CardNavigationController.class, (flashcardProvider) -> CardNavigationController
                                                      .withFlashCardProvider(flashcardProvider),
      MenuController.class, (flashcardProvider) -> MenuController
                                                      .withFlashCardProvider(flashcardProvider),
      NavbarController.class, (flashcardProvider) -> NavbarController
                                                      .withFlashCardProvider(flashcardProvider),
      CardAreaController.class, (flashcardProvider) -> CardAreaController
                                                      .withFlashCardProvider(flashcardProvider)
    );

  /**
   * Start the application.
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    parseArgs(Arrays.asList(args));
    launch(args);
  }

  private static void parseArgs(List<String> args) {
    remoteMode = args.contains("-r") || args.contains("--remote");

    if (args.contains("-a")) {
      address = args.get(args.indexOf("-a") + 1);
    }
    if (args.contains("--address")) {
      address = args.get(args.indexOf("--address") + 1);
    }

    if (args.contains("-p")) {
      port = args.get(args.indexOf("-p") + 1);
    }
    if (args.contains("--port")) {
      port = args.get(args.indexOf("-port") + 1);
    }
  }

  @Override
  public void start(Stage stage) throws IOException {

    var cardDeckStorage = remoteMode ? new RemoteCardDeckStorage(URI.create(
                                       String.format("http://%s:%s", address, port)))
                                       : new LocalCardDeckStorage();
    var flashcardProvider = new FlashcardProvider(cardDeckStorage);

    FXMLLoader fxmlLoader = new FXMLLoader(
        this.getClass().getResource("FlashyApp.fxml"));
    fxmlLoader.setControllerFactory(controllerClass -> controllerConstructors
                                                       .get(controllerClass)
                                                       .apply(flashcardProvider));
    Parent parent = fxmlLoader.load();
    Scene scene = new Scene(parent);

    stage.getIcons().add(new Image(getClass()
                    .getResourceAsStream("/it1901/groups2021/gr2141/ui/graphics/logo.png")));
    scene.getStylesheets().add(getClass().getResource("styling/Flashy.css").toExternalForm());
    stage.setTitle("Flashy!");
    stage.setScene(scene);

    stage.show();
  }
}
