package app.events;

import app.model.Model;

/**
 * Event signalizing that the current file either has been modified or saved
 */
public class FileSaveStateChangedEvent extends Event {

  private boolean isSaved;

  /**
   * Event signalizing that the current file either has been modified or saved
   * @param isSaved Whether or not the file has been modified or saved
   */
  public FileSaveStateChangedEvent(boolean isSaved) {
    this.isSaved = isSaved;
    Model.setFileIsSaved(isSaved);
  }

  /**
   * @return Whether or not the file has been modified or saved
   */
  public boolean getIsSaved() {
    return this.isSaved;
  }
  
}
