package app.events;

import java.nio.file.Path;
import java.util.Optional;

import app.model.Model;

/**
 * Event signalizing that a file outside the current project is supposed to be opened in the editor.
 */
public class OpenFileEvent extends Event {

  private Optional<Path> path;

  /**
   * Event signalizing that a file outside the current project is supposed to be opened in the editor.
   * @param path The path of the file to be opened
   */
  public OpenFileEvent(Optional<Path> path) {
    this.path = path;
    Model.setActiveFilePath(path);
  }

  /**
   * @return The path of the file to be opened
   */
  public Optional<Path> getPath() {
    return this.path;
  }

}
