package app.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import app.events.EditorChangedEvent;
import app.events.LanguageChangedEvent;
import app.events.OpenFileEvent;
import app.events.FileSaveStateChangedEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * A FXML controller that controls the Modeline
 */
public class ModelineController implements Initializable, Controller {

  @FXML
  private Label filename; 
  
  @FXML
  private Label saveState;

  @FXML
  private Label columnrow;

  @FXML
  private Label language;
    
  private EventBus eventBus;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    setColumnRow(0, 1);
  }

  @Override
  public void setEventBus(EventBus eventBus) {
    this.eventBus = eventBus;
    this.eventBus.register(this);
  }

  /**
   * Update the colum row counter
   * @param column The column number
   * @param row The row number
   */
  public void setColumnRow(int column, int row) {
    this.columnrow.setText(String.format("[%d:%d]", row, column));
  }

  /* ------------------------------------------------------------------------ */
  /* SUBSCRIPTIONS */
  /* ------------------------------------------------------------------------ */

  /**
   * Updates the column-row number display whenever the editor cursor
   * changes position.
   * 
   * @param event
   */
  @Subscribe
  public void handle(EditorChangedEvent event) {
    this.setColumnRow(event.getColumn(), event.getLine());
  }

  /**
   * Updates the saveState label whenever the file either is saved or modified
   * 
   * @param event
   */
  @Subscribe
  public void handle(FileSaveStateChangedEvent event) {
    this.saveState.setText(event.getIsSaved() ? "Saved!" : "Modified");
  }

  /**
   * Updates the modeline to display a new language when changed.
   * 
   * @param event
   */
  @Subscribe
  private void handle(LanguageChangedEvent event) {
    this.language.setText(event.getLanguage());
  }

  /**
   * Updates the modeline to display the name of the current file when changed
   * 
   * @param event
   */
  @Subscribe
  private void handle(OpenFileEvent event) {
    this.filename.setText(
      event.getPath().map(path -> path.getFileName().toString()).orElse("New file")
    );
  }
}