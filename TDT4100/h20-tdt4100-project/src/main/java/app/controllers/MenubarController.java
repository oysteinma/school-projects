package app.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import app.events.CopyEvent;
import app.events.CutEvent;
import app.events.ExitApplicationEvent;
import app.events.LanguageChangedEvent;
import app.events.OpenFileEvent;
import app.events.OpenLinkInBrowserEvent;
import app.events.OpenProjectEvent;
import app.events.PasteEvent;
import app.events.RedoEvent;
import app.events.SaveFileEvent;
import app.events.ThemeChangedEvent;
import app.events.ToggleCommentEvent;
import app.events.ToggleWrapTextEvent;
import app.events.UndoEvent;
import app.service.DialogBoxes;
import app.service.FileOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * A FXML controller that controls the MenuBar
 */
public class MenubarController implements Initializable, Controller {

  private EventBus eventBus;

  @FXML
  private MenuBar menubar;

  @FXML
  private ToggleGroup languageToggleGroup;

  @FXML
  private ToggleGroup themeToggleGroup;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
  }

  @Override
  public void setEventBus(EventBus eventBus) {
    this.eventBus = eventBus;
    this.eventBus.register(this);
  }

  /* ---------------------------------- File ---------------------------------- */

  /**
   * Handles whenever the New File button is pressed in the menubar
   * 
   * @param event
   */
  @FXML
  private void handleNewFile() {
    this.eventBus.post(new OpenFileEvent(Optional.empty()));
  }

  /**
   * Handles whenever the Open File button is pressed in the menubar
   * 
   * @param event
   */
  @FXML
  private void handleOpenFile() {
    Stage stage = (Stage) menubar.getScene().getWindow();

    try {
      File file = FileOperations.openFileWithDialog(stage);

      this.eventBus.post(new OpenFileEvent(Optional.ofNullable(file.toPath())));
    } catch (FileNotFoundException e) {
      DialogBoxes.showErrorMessage("File not found!");
    }
  }

  /**
   * Handles whenever the Open Project button is pressed in the menubar
   * 
   * @param event
   */
  @FXML
  private void handleOpenProject() {
    Stage stage = (Stage) menubar.getScene().getWindow();

    try {
      File dir = FileOperations.openDirectoryWithDialog(stage);

      this.eventBus.post(new OpenProjectEvent(Optional.of(dir.toPath())));
    } catch (FileNotFoundException e) {}
  }


  /**
   * Handles whenever the Save button is pressed in the menubar
   * 
   * @param event
   */
  @FXML
  private void handleSaveFile() {
    this.eventBus.post(new SaveFileEvent());
  }

  /**
   * Handles whenever the Save as button is pressed in the menubar
   * 
   * @param event
   */
  @FXML
  private void handleSaveAsFile() {
    this.eventBus.post(new SaveFileEvent(true));
  }

  /**
   * Handles whenever the programming language is changed from the menubar.
   * 
   * @param event
   */
  @FXML
  private void handleLanguageChange(ActionEvent event) {
    this.eventBus.post(new LanguageChangedEvent(((RadioMenuItem) event.getSource()).getText()));
  }

  /**
   * Handles whenever the wraptext togglebutton is pressed in the menubar
   * 
   * @param event
   */
  @FXML
  private void handleToggleWraptext(ActionEvent event) {
    var isSelected = ((CheckMenuItem) event.getSource()).selectedProperty().get();
    this.eventBus.post(new ToggleWrapTextEvent(isSelected));
  }

  /**
   * Handles whenever the theme is changed from the menubar
   * 
   * @param event
   */
  @FXML
  private void handleThemeChange(ActionEvent event) {
    this.eventBus.post(new ThemeChangedEvent(((RadioMenuItem) event.getSource()).getText()));
  }

  /**
   * Handles whenever the exit button is pressed in the menubar
   * 
   * @param event
   */
  @FXML
  private void handleExitApplication(ActionEvent event) {
    this.eventBus.post(new ExitApplicationEvent());
  }

  /* ---------------------------------- Edit ---------------------------------- */

  /**
   * Handles whenever the undo button is pressed in the menubar
   * 
   * @param event
   */
  @FXML
  private void handleUndo(ActionEvent event) {
    this.eventBus.post(new UndoEvent());
  }

  /**
   * Handles whenever the redo button is pressed in the menubar
   * 
   * @param event
   */
  @FXML
  private void handleRedo(ActionEvent event) {
    this.eventBus.post(new RedoEvent());
  }

  /**
   * Handles whenever the copy button is pressed in the menubar
   * 
   * @param event
   */
  @FXML
  private void handleCopy(ActionEvent event) {
    this.eventBus.post(new CopyEvent());
  }

  /**
   * Handles whenever the cut button is pressed in the menubar
   * 
   * @param event
   */
  @FXML
  private void handleCut(ActionEvent event) {
    this.eventBus.post(new CutEvent());
  }

  /**
   * Handles whenever the paste button is pressed in the menubar
   * 
   * @param event
   */
  @FXML
  private void handlePaste(ActionEvent event) {
    this.eventBus.post(new PasteEvent());
  }

  /**
   * Handles whenever the Toggle Comment button is pressed in the menubar
   * 
   * @param event
   */
  @FXML
  private void handleToggleComment(ActionEvent event) {
    this.eventBus.post(new ToggleCommentEvent());
  }

  /* ---------------------------------- About --------------------------------- */

  /**
   * Handles whenever the About button is pressed in the menubar
   * 
   * @param event
   */
  @FXML
  private void handleAbout(ActionEvent event) {
    String aboutLink = "https://github.com/oystein-m/school-projects/blob/main/TDT4100/h20-tdt4100-project/README.md";
    this.eventBus.post(new OpenLinkInBrowserEvent(aboutLink));
  }

  /* ------------------------------------------------------------------------ */
  /* SUBSCRIPTIONS */
  /* ------------------------------------------------------------------------ */

  /**
   * Updates menubuttons whenever the language is changed
   * 
   * @param event
   */
  @Subscribe
  public void handle(LanguageChangedEvent event) {
    this.languageToggleGroup
      .getToggles()
      .stream()
      .map(RadioMenuItem.class::cast)
      .filter(t -> t.getId().equals("toggle" + event.getLanguage()))
      .findFirst()
      // This should never happen!
      .orElseThrow(() -> new IllegalStateException("Language button missing: " + event.getLanguage()))
      .setSelected(true);
  }

  /**
   * Updates menubuttons whenever the theme is changed
   * 
   * @param event
   */
  @Subscribe
  public void handle(ThemeChangedEvent event) {
    this.themeToggleGroup
      .getToggles()
      .stream()
      .map(RadioMenuItem.class::cast)
      .filter(t -> t.getId().equals("toggle" + event.getTheme().replace(" ", "_")))
      .findFirst()
      // This should never happen!
      .orElseThrow(() -> new IllegalStateException("Theme button missing: " + event.getTheme()))
      .setSelected(true);
  }
}
