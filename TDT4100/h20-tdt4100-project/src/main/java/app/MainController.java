package app;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import app.controllers.*;
import app.events.ExitApplicationEvent;
import app.events.LanguageChangedEvent;
import app.events.OpenLinkInBrowserEvent;
import app.events.ThemeChangedEvent;
import app.model.Model;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * An FXML controller that controls the application and all subcontrollers
 */
public class MainController implements Initializable {

  @FXML
  private EditorController editorController;

  @FXML
  private FiletreeController filetreeController;

  @FXML
  private ModelineController modelineController;

  @FXML
  private MenubarController menubarController;

  private EventBus eventBus;
  private HostServices hostServices;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    this.eventBus = new EventBus();
    this.eventBus.register(this);

    List.of(editorController, filetreeController, modelineController, menubarController)
        .forEach(c -> c.setEventBus(this.eventBus));

  }

  /**
   * Get the global eventbus
   * 
   * @return The EventBus object
   */
  public EventBus getEventBus() {
    return this.eventBus;
  }

  /**
   * Get the global Host Services API
   * 
   * @return The JavaFX HostServices object
   * @see #setHostServices(HostServices)
   */
  public HostServices getHostServices() {
    return hostServices;
  }

  /**
   * @return All subcontrollers of this controller
   */
  public List<Controller> getInnerControllers() {
    return List.of(editorController, filetreeController, modelineController, menubarController);
  }

  /**
   * Set a reference to the global Host Services API
   * 
   * @param hostServices The JavaFX HostServices object
   * @see #getHostServices()
   */
  public void setHostServices(HostServices hostServices) {
    this.hostServices = hostServices;
  }

  /**
   * Replace a CSS file in a specific location in the application CSS array
   * 
   * @param position The position of the CSS file to replace
   * @param cssPath  The path in resources to the new CSS file
   */
  private void setCSSAt(int position, String cssPath) {
    if ((position != 0) && (position != 1)) {
      throw new IllegalArgumentException("Range of position must be either 0 or 1");
    } 
    String nextStyleSheet = getClass().getResource(cssPath).toExternalForm();

    Model.getScene().getStylesheets().set(position, nextStyleSheet);
  }

  /* ------------------------------------------------------------------------ */
  /*                            EVENT BUS LISTENERS                           */
  /* ------------------------------------------------------------------------ */

  /**
   * Change the CSS according to which language is being used
   * 
   * @param event
   */
  @Subscribe
  public void handle(LanguageChangedEvent event) {
    this.setCSSAt(1, "/styling/languages/" + event.getLanguage().toLowerCase() + ".css");
  }

  /**
   * Change the CSS according to which theme the user chooses
   * 
   * @param event
   */
  @Subscribe
  public void handle(ThemeChangedEvent event) {
    this.setCSSAt(0, "/styling/themes/" + event.getTheme().toLowerCase().replace(" ", "-") + ".css");
  }

  /**
   * Open a link in the browser.
   * 
   * @param event
   */
  @Subscribe
  public void handle(OpenLinkInBrowserEvent event) {
    this.getHostServices().showDocument(event.getLink());
  }

  /**
   * Handle an exit request for the whole program. Checking if all is saved before
   * closing the app. The user can either choose to exit or go back to the
   * application and save.
   * 
   * @param event
   */
  @Subscribe
  public void handle(ExitApplicationEvent event) {
    if (!Model.getFileIsSaved()) {
      int g = JOptionPane.showConfirmDialog(null, "Your files are not saved.\nSave before exit?", "Exit",
          JOptionPane.YES_NO_OPTION);

      if (g == JOptionPane.YES_OPTION)
        this.editorController.saveCodeArea(Model.getActiveFilePath().isEmpty());
    }
    Platform.exit();
  }
}
