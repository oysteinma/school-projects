package app.events;

import app.model.Model;

/**
 * Event signalizing that the current file should be saved to disk
 */
public class SaveFileEvent extends Event {

    private boolean isNewFile;

    /**
     * Event signalizing that a file is to be saved.
     */
    public SaveFileEvent() {
        this.isNewFile = Model.getActiveFilePath().isEmpty();
    }

    /**
     * Event signalizing that a file is to be saved.
     * 
     * @param isNewFile The path of the selected file
     */
    public SaveFileEvent(boolean isNewFile) {
        this.isNewFile = isNewFile;
    }

    /**
     * @return Whether or not 
     */
    public boolean getIsNewFile() {
        return this.isNewFile;
    }

}
