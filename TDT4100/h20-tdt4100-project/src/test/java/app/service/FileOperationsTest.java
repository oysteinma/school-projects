package app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.common.io.Files;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import app.model.Model;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

@ExtendWith(MockitoExtension.class)
public class FileOperationsTest {

  // THIS CLASS COULD NOT BE UNITTESTED BECAUSE OF LACKING SUPPORT FOR MOCKING
  // STATIC OBJECTS WITH MOCKITO AND JUNI5

  // @TempDir
  // File tmp;

  // @Mock
  // FileChooser fc = mock(FileChooser.class);

  // @Mock
  // DirectoryChooser dc = mock(DirectoryChooser.class);

  // @Mock
  // Alert error = mock(Alert.class);

  // @InjectMocks
  // MockedStatic<DialogBoxes> db = mockStatic(DialogBoxes.class);

  // @Test
  // @DisplayName("Test openFileWithDialog")
  // public void testOpenFileWithDialog() {
  //   // try (MockedStatic<DialogBoxes> mocked = mockStatic(DialogBoxes.class)) {
  //     Stage stage = mock(Stage.class);

  //     db.when(() -> DialogBoxes.showopenFileWithDialog(any()))
  //       .thenReturn(null);
  //     assertThrows(FileNotFoundException.class, () -> FileOperations.openFileWithDialog(stage));

  //     File file = mock(File.class);
  //     db.when(() -> DialogBoxes.showopenFileWithDialog(any()))
  //       .thenReturn(file);
  //     try {
  //       assertEquals(file, FileOperations.openFileWithDialog(stage));
  //     } catch (FileNotFoundException e) {
  //       fail("Chosen file was null when it was expected to be mock file");
  //     } 
  //   // }
  // }


  // @Test
  // @DisplayName("Test openDirectoryWithDialog")
  // public void testOpenDirectoryWithDialog() {
  //   try (MockedStatic<DialogBoxes> mocked = mockStatic(DialogBoxes.class)) {
  //     Stage stage = mock(Stage.class);

  //     mocked.when(() -> DialogBoxes.showOpenDirectoryWithDialog(any()))
  //       .thenReturn(null);
  //     assertThrows(FileNotFoundException.class, () -> FileOperations.openDirectoryWithDialog(stage));
      
  //     File file = mock(File.class);
  //     mocked.when(() -> DialogBoxes.showOpenDirectoryWithDialog(any()))
  //       .thenReturn(file);
  //     try {
  //       assertEquals(file, FileOperations.openDirectoryWithDialog(stage));
  //     } catch (FileNotFoundException e) {
  //       fail("Chosen file was null when it was expected to be mock file");
  //     } 

  //   }
  // }

  // private File createTemporaryFile() throws IOException {
  //   File f = new File(tmp, "test.txt");
  //   f.createNewFile();
  //   return f;
  // }

  // @Test
  // @DisplayName("Test saveFile")
  // public void testSaveFile() {
  //   String content = "test\ncontent\nfor\nyou";
  //   File f;

  //   try (MockedStatic<DialogBoxes> mocked = mockStatic(DialogBoxes.class)) {
  //     // mocked.when(() -> DialogBoxes.showErrorMessage(anyString()));

  //     f = createTemporaryFile();
  //     assertTrue(FileOperations.saveFile(f.toPath(), content));

  //     List<String> read = Files.readLines(f, StandardCharsets.UTF_8);
  //     String value = String.join("\n", read);
  //     assertEquals(content, value);

  //     Path wrongPath = Paths.get("wrongPath.txt");
  //     assertFalse(FileOperations.saveFile(wrongPath, content));

  //   } catch (IOException e) {
  //     fail("Unexpected temporary file failure");
  //   }
  // }

  // @Test
  // @DisplayName("Test saveFileWithDialog")
  // public void testSaveFileWithDialog() {
  //   String content = "test\ncontent\nfor\nyou";
  //   File f;

  //   try (MockedStatic<DialogBoxes> mocked = mockStatic(DialogBoxes.class)) {
  //     Stage stage = mock(Stage.class);

  //     mocked.when(() -> DialogBoxes.showSaveFileWithDialog(any()))
  //       .thenReturn(false);
  //       assertFalse(FileOperations.saveFileWithDialog(stage, content));
      
  //     mocked.when(() -> DialogBoxes.showSaveFileWithDialog(any()))
  //       .thenReturn(null);
  //       assertFalse(FileOperations.saveFileWithDialog(stage, content));
      
  //     f = createTemporaryFile();
  //     mocked.when(() -> DialogBoxes.showSaveFileWithDialog(any()))
  //       .thenReturn(f);
  //       assertTrue(FileOperations.saveFileWithDialog(stage, content));
  //       assertEquals(Model.getActiveFilePath(), f.toPath());

  //     File wrongFile = new File("Does not exist");
  //     mocked.when(() -> DialogBoxes.showSaveFileWithDialog(any()))
  //       .thenReturn(wrongFile);
  //       assertFalse(FileOperations.saveFileWithDialog(stage, content));
  //   } catch (IOException e) {
  //     fail("Unexpected IOexception when creating temporary file");
  //   }
  // }
  
  // @Test
  // @DisplayName("Test readFile")
  // public void testReadFile() {
  //   File f;

  //   try (MockedStatic<DialogBoxes> mocked = mockStatic(DialogBoxes.class)) {
  //     // mocked.when(() -> DialogBoxes.showErrorMessage(anyString()));

  //     assertEquals("", FileOperations.readFile(null));
    
  //     String content = "test\ncontent\nfor\nyou";
  //     f = createTemporaryFile();

  //     Files.write(content.getBytes(), f);

  //     assertEquals(content, FileOperations.readFile(f.toPath()));

  //     Path wrongPath = Paths.get("wrongPath.txt");
  //     assertThrows(FileNotFoundException.class, () -> FileOperations.readFile(wrongPath));


  //   } catch (IOException e) {
  //     fail("Unexpected temporary file failure");
  //   }
  // }
  
}
