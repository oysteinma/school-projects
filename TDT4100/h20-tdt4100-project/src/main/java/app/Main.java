package app;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import app.events.FileSaveStateChangedEvent;
import app.model.Model;
import app.service.DialogBoxes;
import app.settings.SettingsProvider;

public class Main extends Application {

  private Scene scene;
  private FXMLLoader fxmlLoader;
  private Parent fxmlRoot;

  private static final String TITLE = "Banana Editor";
  private static final String ICON_PATH = "/graphics/logo.png";

  /**
   * Boilerplate function to launch the application.
   * 
   * @param args Additional arguments from commandline
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Set up a window with title and icon.
   */
  private void setupWindow(Stage window) {
    window.setTitle(TITLE);
    if (window.getIcons().isEmpty())
      window.getIcons().add(new Image(getClass().getResourceAsStream(ICON_PATH)));
  }

  /**
   * Loads all FXML documents of the main UI and initializes all correlated
   * subcontrollers
   * 
   * @throws IOException
   */
  private void loadFXML() {
    try {
      this.fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
      this.fxmlRoot = fxmlLoader.load();
    } catch (Exception e) {
      DialogBoxes.showErrorMessage("There is an error within the program. Please try to reinstall.");
      System.exit(1);
    }

  }

  /**
   * Generates a scene for the window, and adds it to {@link Model}
   */
  private void createScene() {
    this.scene = new Scene(fxmlRoot);
    this.scene.setUserData(this.fxmlLoader);
    Model.setScene(scene);
  }

  /**
   * Set up default values and settings for the editor.
   */
  private void setupDefaults() {
    scene.getStylesheets().setAll("", "");

    MainController mainController = fxmlLoader.getController();
    SettingsProvider SP = new SettingsProvider(mainController.getEventBus());
    SP.loadSettings();
    Model.setActiveFilePath(Optional.empty());
    Model.setProjectPath(Optional.empty());
    mainController.getEventBus().post(new FileSaveStateChangedEvent(true));
    mainController.setHostServices(getHostServices());
  }

  /**
   * The entrypoint of the application.
   * 
   * @param window The primary window of the application
   */
  @Override
  public void start(Stage window) throws IOException {

    setupWindow(window);
    loadFXML();
    createScene();
    setupDefaults();

    window.setScene(scene);
    window.show();
  }

}
