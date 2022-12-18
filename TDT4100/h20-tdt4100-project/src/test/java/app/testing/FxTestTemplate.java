package app.testing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeoutException;

import app.Main;
import app.MainController;

public class FxTestTemplate extends ApplicationTest {

  private Stage stage;
  private Application application;

  @BeforeEach
  public void runAppToTests() throws Exception {
    FxToolkit.registerPrimaryStage();
    this.application = FxToolkit.setupApplication(Main::new);

    FxToolkit.showStage();
    WaitForAsyncUtils.waitForFxEvents(100);
  }

  @AfterEach
  public void stopApp() throws TimeoutException {
    FxToolkit.cleanupStages();
    FxToolkit.cleanupApplication(this.application);
  }

  @Override
  public void start(Stage primaryStage){
    this.stage = primaryStage;
    primaryStage.toFront();
  }

  public Stage getStage() {
    return stage;
  }

  public MainController getMainController() {
    return ((FXMLLoader) this.stage.getScene().getUserData()).getController();
  }

  public <T extends Node> T find(final String query) {
    /** TestFX provides many operations to retrieve elements from the loaded GUI. */
    return lookup(query).query();
  }
}