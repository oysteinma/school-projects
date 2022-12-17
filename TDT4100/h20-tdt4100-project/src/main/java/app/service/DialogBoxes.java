package app.service;

import java.io.File;

import app.model.Model;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Class containing static methods for different kinds of popup window interactions
 * with the user.
 */
public class DialogBoxes {

  private DialogBoxes() {}
  private static FileChooser fc = new FileChooser();
  private static DirectoryChooser dc = new DirectoryChooser();
  private static Alert error = new Alert(AlertType.ERROR);

  /**
   * Shows a specified message to the user with an error icon.
   * 
   * @param errorMessage The message to show the user
   */
  public static void showErrorMessage(String errorMessage) {
      error.setContentText(errorMessage);
      error.showAndWait();
  }

  /**
   * Shows an OS specific file chooser to choose a file on the disk
   * 
   * @param stage The JavaFX stage to connect to the dialog box. This is needed
   *              for the window to be able to run on the JavaFX thread.
   * 
   * @return The file chosen through the dialog window
   */
  public static File showopenFileWithDialog(Stage stage) {
    fc.setTitle("Open File");
    File chosenFile = fc.showOpenDialog(stage);

    return chosenFile; 
  }

  /**
   * Shows an OS specific directory chooser to choose a directory on the disk
   * 
   * @param stage The JavaFX stage to connect to the dialog box. This is needed
   *              for the window to be able to run on the JavaFX thread.
   * 
   * @return The file chosen through the dialog window
   */
  public static File showOpenDirectoryWithDialog(Stage stage) {
    dc.setTitle("Open Project");
    File dir = dc.showDialog(stage);

    return dir;
  }

  /**
   * Shows an OS specific file chooser to specifyy a new path on the disk
   * 
   * @param stage The JavaFX stage to connect to the dialog box. This is needed
   *              for the window to be able to run on the JavaFX thread.
   * 
   * @return The filepath chosen through the dialog window
   */
  public static File showSaveFileWithDialog(Stage stage) {
    FileChooser fc = new FileChooser();
    fc.setTitle("Save as");

    Model
      .getProjectPath()
      .ifPresent(path -> fc.setInitialDirectory(path.toFile()));

    File chosenLocation = fc.showSaveDialog(stage);

    return chosenLocation;
  }
  
}
