package app.events;

import java.nio.file.Path;
import java.util.Optional;

import app.model.Model;

/**
 * Event signalizing that a directory is supposed to be opened in the filetree.
 */
public class OpenProjectEvent extends Event {

    private Optional<Path> path;

    /**
     * Event signalizing that a directory is supposed to be opened in the filetree.
     * @param path The path of the directory to be opened
     */
    public OpenProjectEvent(Optional<Path> path) {
        this.path = path;
        Model.setProjectPath(path);
    }

    /**
     * @return The path of the directory to be opened
     */
    public Optional<Path> getPath() {
        return this.path;
    }
}
