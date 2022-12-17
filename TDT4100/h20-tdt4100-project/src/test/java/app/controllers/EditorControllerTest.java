package app.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;

import com.google.common.eventbus.EventBus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import app.testing.FxTestTemplate;
import app.model.Model;
import app.model.ProgrammingLanguage;
import app.service.FileOperations;
import app.service.LanguageOperations;
import app.events.CopyEvent;
import app.events.CutEvent;
import app.events.OpenFileEvent;
import app.events.LanguageChangedEvent;
import app.events.PasteEvent;
import app.events.RedoEvent;
import app.events.ToggleCommentEvent;
import app.events.ToggleWrapTextEvent;
import app.events.UndoEvent;

@ExtendWith(MockitoExtension.class)
public class EditorControllerTest extends FxTestTemplate {


  @Captor
  private ArgumentCaptor<String> captor;

  @Mock
  private CodeArea editor;

  private EventBus eventBus;

  @InjectMocks
  private EditorController controller;

  private String mockContent = """
  class HelloWorld {
    private String message = \"Hello world\";

    public String getMessage() {
      return message;
    }
  }
  """;

  private String mockLine = "private String message = \"Hello world\";";

  @BeforeEach
  public void insertEventBus() {
    this.eventBus = new EventBus();
    this.controller.setEventBus(eventBus);
  }

  @Test
  @DisplayName("Test handling of OpenFileEvent with a real file")
  public void testOpenFileEventWithRealFile() throws IOException {

    String resourcePath = "/testfile.txt";
    String filePath = getClass().getResource(resourcePath).getPath();
    File file = new File(filePath);

    try (MockedStatic<FileOperations> mocked = mockStatic(FileOperations.class)) {
      mocked.when(() -> FileOperations.readFile(any()))
        .thenReturn("");

      eventBus.post(new OpenFileEvent(Optional.of(file.toPath())));

      mocked.verify(() -> FileOperations.readFile(any()));
    }
  }

  @Test
  @DisplayName("Test handling of OpenFileEvent with a file that doesn't exist")
  public void testOpenFileEventWithUnrealFile() throws IOException {
    try (MockedStatic<FileOperations> mocked = mockStatic(FileOperations.class)) {
      mocked.when(() -> FileOperations.readFile(any()))
        .thenReturn(null);

      String brokenFilePath = "/doesNotExist.txt";
      eventBus.post(new OpenFileEvent(Optional.ofNullable(Paths.get(brokenFilePath))));

      verify(editor).appendText("");
    }
  }

  @Test
  @DisplayName("Test handling of LanguageChangedEvent")
  public void testLanguageChangedEvent(){

    when(editor.getText()).thenReturn(mockContent);

    try (MockedStatic<LanguageOperations> mocked = mockStatic(LanguageOperations.class)) {
      mocked.when(() -> LanguageOperations.syntaxHighlight(anyString(), any()))
        .thenReturn(StyleSpans.singleton(null, 0));

      eventBus.post(new LanguageChangedEvent("markdown"));
      mocked.verify(() -> LanguageOperations.syntaxHighlight(anyString(), any()));
    }
  }

  @Test
  @DisplayName("Test handling of ToggleCommentEvent when not selected")
  public void testToggleCommentEventNotSelect(){

    ProgrammingLanguage lang = mock(ProgrammingLanguage.class);
    when(editor.getSelectedText()).thenReturn("");
    when(editor.getText(anyInt())).thenReturn(mockLine);

    try (MockedStatic<Model> mocked = mockStatic(Model.class)) {
      mocked.when(() -> Model.getLanguage()).thenReturn(lang);

      when(lang.isCommentedLine(anyString())).thenReturn(false);
      eventBus.post(new ToggleCommentEvent());
      verify(lang).commentLine(anyString());

      when(lang.isCommentedLine(anyString())).thenReturn(true);
      eventBus.post(new ToggleCommentEvent());
      verify(lang).unCommentLine(anyString());
    }
  }

  @Test
  @DisplayName("Test handling of ToggleCommentEvent when selected")
  public void testToggleCommentEventSelect(){

    ProgrammingLanguage lang = mock(ProgrammingLanguage.class);
    when(editor.getSelectedText()).thenReturn("Selected Text");

    try (MockedStatic<Model> mocked = mockStatic(Model.class)) {
      mocked.when(() -> Model.getLanguage()).thenReturn(lang);

      when(lang.isCommentedSelection(anyString())).thenReturn(false);
      eventBus.post(new ToggleCommentEvent());
      verify(lang).commentSelection(anyString());

      when(lang.isCommentedSelection(anyString())).thenReturn(true);
      eventBus.post(new ToggleCommentEvent());
      verify(lang).unCommentSelection(anyString());
    }
  }

  @Test
  @DisplayName("Test handling of ToggleWrapTextEvent")
  public void testToggleWrapTextEvent(){
    eventBus.post(new ToggleWrapTextEvent(true));
    verify(editor).setWrapText(true);
    eventBus.post(new ToggleWrapTextEvent(false));
    verify(editor).setWrapText(false);
  }

  @Test
  @DisplayName("Test handling of UndoEvent")
  public void testUndoEvent(){
    when(editor.isFocused()).thenReturn(true);
    eventBus.post(new UndoEvent());
    verify(editor, times(1)).undo();

    when(editor.isFocused()).thenReturn(false);
    eventBus.post(new UndoEvent());
    // Should not have been called one more time
    verify(editor, times(1)).undo();
  }

  @Test
  @DisplayName("Test handling of RedoEvent")
  public void testRedoEvent(){
    when(editor.isFocused()).thenReturn(true);
    eventBus.post(new RedoEvent());
    verify(editor, times(1)).redo();

    when(editor.isFocused()).thenReturn(false);
    eventBus.post(new RedoEvent());
    verify(editor, times(1)).redo();
  }
  
  @Test
  @DisplayName("Test handling of CopyEvent")
  public void testCopyEvent(){
    when(editor.isFocused()).thenReturn(true);
    eventBus.post(new CopyEvent());
    verify(editor, times(1)).copy();

    when(editor.isFocused()).thenReturn(false);
    eventBus.post(new CopyEvent());
    verify(editor, times(1)).copy();
  }

  @Test
  @DisplayName("Test handling of CutEvent")
  public void testCutEvent(){
    when(editor.isFocused()).thenReturn(true);
    eventBus.post(new CutEvent());
    verify(editor, times(1)).cut();

    when(editor.isFocused()).thenReturn(false);
    eventBus.post(new CutEvent());
    verify(editor, times(1)).cut();
  }

  @Test
  @DisplayName("Test handling of PasteEvent")
  public void testPasteEvent(){
    when(editor.isFocused()).thenReturn(true);
    eventBus.post(new PasteEvent());
    verify(editor, times(1)).paste();

    when(editor.isFocused()).thenReturn(false);
    eventBus.post(new PasteEvent());
    verify(editor, times(1)).paste();
  }
}