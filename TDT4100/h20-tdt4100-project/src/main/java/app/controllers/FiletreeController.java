package app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.ResourceBundle;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import app.events.OpenFileEvent;
import app.events.OpenProjectEvent;
import app.events.SaveFileEvent;
import app.model.Model;
import app.service.DialogBoxes;
import app.service.FiletreeOperations;
import javafx.fxml.Initializable;

/**
 * An FXML controller that controls the Filetree
 */
public class FiletreeController implements Initializable, Controller {

  private EventBus eventBus;

  @FXML
  private TreeView<String> filetree;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {}

  @Override
  public void setEventBus(EventBus eventBus) {
    this.eventBus = eventBus;
    this.eventBus.register(this);
  }

  /**
   * Generate a tree structure of a directory, and set the filetree to
   * show the new tree
   * 
   * @param rootDir Path to the directory to be the root of the tree
   */
  private void showTree(Path rootDir) {
    CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>(rootDir.getFileName().toString());
    filetree.setShowRoot(false);
    File fileInputChosen = rootDir.toFile();

    try {
      FiletreeOperations.generateTree(fileInputChosen, root);

      filetree.setRoot(root);
    } catch (Exception e) {
      Model.setProjectPath(Optional.empty());
      DialogBoxes.showErrorMessage(
        "Could not open directory.\n\n"
        + "Do you have the right permissions for this directory?\n"
        + "Or does the directory contain any shortcut to somewhere within itself?"
      );
    }
  }

  /**
   * Handles opening a file whenever a filetree item is clicked twice. */
  @FXML
  private void handleMouseClick(MouseEvent event) {
    if (event.getClickCount() == 2) {
      TreeItem<String> item = filetree.getSelectionModel().getSelectedItem();

      try {
        Path path = FiletreeOperations.getPathOfTreeItem(item);

        if (!Files.isDirectory(path)) {
          this.eventBus.post(new OpenFileEvent(Optional.ofNullable(path)));
        }
      } catch (FileNotFoundException e) {
        System.err.println("[ERROR] Could not find filepath from filetree");
        System.err.print(e);
      }
    }
  }

  /* ------------------------------------------------------------------------ */
  /*                            EVENT BUS LISTENERS                           */
  /* ------------------------------------------------------------------------ */

  /**
   * Updates the filetree whenever a new project is opened
   * 
   * @param event
   */
  @Subscribe
  private void handle(OpenProjectEvent event) {
    event.getPath().ifPresentOrElse(
      path -> this.showTree(path),
      () -> System.err.println("[ERROR] OpenProjectEvent was empty")
    );
  }

  /**
   * Updates the filetree whenever a new file gets saved
   * 
   * @param event
   */
  @Subscribe
  private void handle(SaveFileEvent event) {
    if (event.getIsNewFile())
      Model
        .getProjectPath()
        .ifPresent(path -> this.showTree(path));
  }

}
