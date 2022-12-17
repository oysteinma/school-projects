package app.controllers;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.TwoDimensional.Bias;
import org.fxmisc.richtext.model.TwoDimensional.Position;

import app.events.CopyEvent;
import app.events.CutEvent;
import app.events.EditorChangedEvent;

import app.events.LanguageChangedEvent;
import app.events.OpenFileEvent;
import app.events.PasteEvent;
import app.events.RedoEvent;
import app.events.SaveFileEvent;
import app.events.ToggleCommentEvent;
import app.events.ToggleWrapTextEvent;
import app.events.UndoEvent;
import app.events.FileSaveStateChangedEvent;
import app.model.Model;
import app.service.FileOperations;
import app.service.LanguageOperations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * An FXML controller that controls the CodeArea
 */
public class EditorController implements Initializable, Controller {

  @FXML
  private CodeArea editor;

  private EventBus eventBus;

  /**
   * Initializes the controller, and binds the event of change in editor content
   * to {@link #editorChanged() editorChanged}
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    editor.setParagraphGraphicFactory(LineNumberFactory.get(editor));

    editor.textProperty().addListener((obs, oldV, newV) -> this.editorChanged());
  }

  @Override
  public void setEventBus(EventBus eventBus) {
    this.eventBus = eventBus;
    this.eventBus.register(this);
  }

  /**
   * Applies highlighting to the editor.
   * 
   * @param highlighting Syntax highlighting data
   */
  private void setHighlighting(StyleSpans<Collection<String>> highlighting) {
    this.editor.setStyleSpans(0, highlighting);
  }

  /**
   * Recalculates and refreshes the syntax highlighting of the editor.
   */
  private void refreshHighlighting() {
    this.setHighlighting(LanguageOperations.syntaxHighlight(this.editor.getText(), Model.getLanguage()));
  }

  /**
   * Uses the {@link app.model.ProgrammingLanguage ProgrammingLanguage} in
   * {@link app.model.Model Model} to determine whether the current line/selection
   * is commented or not, and toggles the comment.
   * 
   * @see app.model.ProgrammingLanguage#commentLine(String)
   *      ProgrammingLanguage.commentLine(line)
   */
  private void toggleComment() {
    if (editor.getSelectedText().equals("")) {
      String currentLine = editor.getText(editor.getCurrentParagraph());

      String newText;
      if (Model.getLanguage().isCommentedLine(currentLine))
        newText = Model.getLanguage().unCommentLine(currentLine);
      else
        newText = Model.getLanguage().commentLine(currentLine);

      editor.replaceText(editor.getCurrentParagraph(), 0, editor.getCurrentParagraph(), currentLine.length(), newText);

    } else { // Comment selection

      String newText;
      if (Model.getLanguage().isCommentedSelection(editor.getSelectedText()))
        newText = Model.getLanguage().unCommentSelection(editor.getSelectedText());
      else
        newText = Model.getLanguage().commentSelection(editor.getSelectedText());

      editor.replaceSelection(newText);

    }
  }

  /**
   * Updates the wraptext setting of the code area
   * 
   * @param isWrapText The updated setting value
   */
  private void setWrapText(boolean isWrapText) {
    this.editor.setWrapText(isWrapText);
  }

  /**
   * Handles the event whenever the content of the editor is changed.
   */
  private void editorChanged() {
    int offset = this.editor.getCaretPosition();
    Position pos = this.editor.offsetToPosition(offset, Bias.Forward);
    this.eventBus.post(new EditorChangedEvent(pos.getMajor() + 1, pos.getMinor()));

    if (Model.getFileIsSaved())
      this.eventBus.post(new FileSaveStateChangedEvent(false));

    this.refreshHighlighting();
  }

  /**
   * Updates the content of the editor. 
   * 
   * @param newContent The String to be inserted into the editor
   */
  private void setEditorContent(String newContent) {
      editor.clear();
      editor.appendText(newContent);
  }

  /**
   * Saving/Writing to the file based on the active filepath in {@link app.model.Model Model}
   * if it is a new File. Otherwise it will open a dialog to ask the user where to save the file.
   * 
   * @param isNewFile Whether or not the file already has a path
   */
  public void saveCodeArea(boolean isNewFile) {
    Stage stage = (Stage) editor.getScene().getWindow();

    if (isNewFile && FileOperations.saveFileWithDialog(stage, editor.getText())) {
      this.eventBus.post(new OpenFileEvent(Model.getActiveFilePath()));
      this.eventBus.post(new FileSaveStateChangedEvent(true));
    }
    else if (FileOperations.saveFile(Model.getActiveFilePath().orElseThrow(), editor.getText())) {
      this.eventBus.post(new FileSaveStateChangedEvent(true));
    }    
  }

  /* ------------------------------------------------------------------------ */
  /*                            EVENT BUS LISTENERS                           */
  /* ------------------------------------------------------------------------ */

  /**
   * Updates the CodeArea whenever a new file is opened.
   * 
   * @param event
   */
  @Subscribe
  public void handle(OpenFileEvent event) {
    String newContent =
      event
        .getPath()
        .map(path -> FileOperations.readFile(path))
        .orElse("");
    this.setEditorContent(newContent);
  }

  /**
   * Saves the editor content to a file
   * 
   * @param event
   */
  @Subscribe
  public void handle(SaveFileEvent event) {
    this.saveCodeArea(event.getIsNewFile());
  }

  /**
   * Refreshes the syntax highlighting when the Programming language is changed
   * 
   * @param event
   */
  @Subscribe
  public void handle(LanguageChangedEvent event) {
    this.refreshHighlighting();
  }

  /**
   * Toggles a comment based on the editor state
   * 
   * @param event
   */
  @Subscribe
  public void handle(ToggleCommentEvent event) {
    this.toggleComment();
  }

  /**
   * Toggles the WrapText setting
   * 
   * @param event
   */
  @Subscribe
  public void handle(ToggleWrapTextEvent event) {
    this.setWrapText(event.getIsWrapped());
  }

  /**
   * Undo if focused
   * 
   * @param event
   */
  @Subscribe
  public void handle(UndoEvent event) {
    if (this.editor.isFocused())
      this.editor.undo();
  }

  /**
   * Redo if focused
   * 
   * @param event
   */
  @Subscribe
  public void handle(RedoEvent event) {
    if (this.editor.isFocused())
      this.editor.redo();
  }

  /**
   * Copy selected content if focused
   * 
   * @param event
   */
  @Subscribe
  public void handle(CopyEvent event) {
    if (this.editor.isFocused())
      this.editor.copy();
  }

  /**
   * Cut selected content if focused
   * 
   * @param event
   */
  @Subscribe
  public void handle(CutEvent event) {
    if (this.editor.isFocused())
      this.editor.cut();
  }

  /**
   * Paste from clipboard if focused
   * 
   * @param event
   */
  @Subscribe
  public void handle(PasteEvent event) {
    if (this.editor.isFocused())
      this.editor.paste();
  }

}
