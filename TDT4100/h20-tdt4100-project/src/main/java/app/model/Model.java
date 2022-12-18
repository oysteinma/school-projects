package app.model;

import java.nio.file.Path;
import java.util.Optional;

import app.settings.SettingsProvider;
import javafx.scene.Scene;

/**
 * Data model of the application.
 * 
 * Contains a static reference to state that has to be accessed by multiple
 * pieces in the application, including the state like primary scene.
 */
public class Model {
  private static boolean fileIsSaved;
  private static Optional<Path> activeFilePath;
  private static Optional<Path> activeProjectPath;
  private static ProgrammingLanguage activeProgrammingLanguage;
  private static String theme;
  private static Scene scene;
  private static SettingsProvider settings;

  public static Optional<Path> getActiveFilePath() {
    return activeFilePath;
  }

  public static void setActiveFilePath(Optional<Path> path) {
    if (path == null)
      throw new IllegalArgumentException("path can not be null");
    Model.activeFilePath = path;
  }

  public static Optional<Path> getProjectPath() {
    return activeProjectPath;
  }

  public static void setProjectPath(Optional<Path> path) {
    if (path == null)
      throw new IllegalArgumentException("path can not be null");
    Model.activeProjectPath = path;
  }

  public static ProgrammingLanguage getLanguage() {
    return activeProgrammingLanguage;
  }

  public static Scene getScene() {
    return scene;
  }

  public static String getTheme() {
    return theme;
  }

  public static boolean getFileIsSaved() {
    return fileIsSaved;
  }

  public static SettingsProvider getSettingsProvider() {
    return settings;
  }

  public static void setTheme(String theme) {
    if (theme == null)
      throw new IllegalArgumentException("theme can not be null");
    Model.theme = theme;
  }

  public static void setLanguage(ProgrammingLanguage language) {
    if (language == null)
      throw new IllegalArgumentException("language can not be null");
    Model.activeProgrammingLanguage = language;
  }

  public static void setScene(Scene scene) {
    if (scene == null)
      throw new IllegalArgumentException("scene can not be null");
    Model.scene = scene;
  }

  public static void setFileIsSaved(boolean fileIsSaved) {
    Model.fileIsSaved = fileIsSaved;
  }

  public static void setSettingsProvider(SettingsProvider settings) {
    if (settings == null)
      throw new IllegalArgumentException("settings can not be null");
    Model.settings = settings;
  }

}