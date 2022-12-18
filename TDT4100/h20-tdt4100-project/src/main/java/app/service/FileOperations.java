package app.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

import app.model.Model;
import javafx.stage.Stage;

/**
 * A class containing operations and logic for choosing, reading and writing to files.
 */
public class FileOperations {

  private FileOperations() {}

  /**
   * A function to get a file through a dialog
   * 
   * @param stage A JavaFX stage is required to show the dialog
   * @return The chosen file
   * @throws FileNotFoundException if the dialog was canceled
   */
  public static File openFileWithDialog(Stage stage) throws FileNotFoundException {
    File chosenFile = DialogBoxes.showopenFileWithDialog(stage);

    if (chosenFile == null)
      throw new FileNotFoundException();

    return chosenFile;

  }

  /**
   * A function to get directory through a dialog
   * 
   * @param stage A JavaFX stage is required to show the dialog
   * @return The chosen directory
   * @throws FileNotFoundException if the dialog was canceled
   */
  public static File openDirectoryWithDialog(Stage stage) throws FileNotFoundException {
    File dir = DialogBoxes.showOpenDirectoryWithDialog(stage);

    if (dir == null)
      throw new FileNotFoundException();

    return dir;
  }

  /**
   * Saves a file a the specified filepath with the specified content.
   * Shows an error message to the user if the file was not found at the specified location.
   * 
   * @param filepath The path of the file to save the content into
   * @param content The text content to be saved
   * @return Whether the file was sucessfully saved
   */
  public static boolean saveFile(Path filepath, String content) {
    try (PrintWriter writer = new PrintWriter(filepath.toFile())) {
      writer.println(content);
      return true;

    } catch (FileNotFoundException ex) {
      DialogBoxes.showErrorMessage("Could not save file at \n" + filepath.toString());
      return false;
    }
  }

  /**
   * Lets the user choose a file to save the specified content into
   * 
   * @param stage A JavaFX stage is needed in order to show the file chooser
   * @param content The content to be saved
   * 
   * @return Whether the file was sucessfully saved
   */
  public static boolean saveFileWithDialog(Stage stage, String content) {
    File chosenLocation;

    try {
      chosenLocation = DialogBoxes.showSaveFileWithDialog(stage);
    } catch (NoSuchElementException e) {
      return false;
    }

    if (chosenLocation == null) return false;

    if (saveFile(chosenLocation.toPath(), content)) {
      Model.setActiveFilePath(Optional.of(chosenLocation.toPath()));
      return true;
    }

    return false;
  }

  /**
   * Tries to read the contents of a file in the specified filepath.
   * If it fails, it shows the user a corresponding error message.
   * 
   * @param filePath The path of the file to be read
   * 
   * @return The text content of the file
   */
  public static String readFile(Path filePath) {

    if (filePath == null)
      return "";

    String result = "";

    try (Scanner sc = new Scanner(filePath.toFile())) {
      while (sc.hasNextLine()) {
        result += (sc.nextLine() + "\n");
      }

    } catch (FileNotFoundException e) {
      DialogBoxes.showErrorMessage("This file could not be opened!");
      System.err.println("[ERROR] File could not be opened: " + filePath.toString());
      System.err.print(e);
    }

    return result;
  }
}
