package app.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import app.settings.SettingsProvider;
import javafx.scene.Scene;

public class ModelTest {

  @Test
  @DisplayName("Test get/set fileIsSaved")
  public void testFileIsSaved() {
    boolean fileIsSaved = false;
    Model.setFileIsSaved(fileIsSaved);
    assertEquals(fileIsSaved, Model.getFileIsSaved());
  }

  @Test
  @DisplayName("Test get/set activeFilePath")
  public void testActiveFilePath() {
    Optional<Path> activeFilePath = Optional.empty();
    Model.setActiveFilePath(activeFilePath);
    assertEquals(activeFilePath, Model.getActiveFilePath());
    assertThrows(IllegalArgumentException.class, () -> Model.setActiveFilePath(null));
  }

  @Test
  @DisplayName("Test get/set projectPath")
  public void testActiveProjectPath() {
    Optional<Path> projectPath = Optional.empty();
    Model.setProjectPath(projectPath);
    assertEquals(projectPath, Model.getProjectPath());
    assertThrows(IllegalArgumentException.class, () -> Model.setProjectPath(null));
  }

  @Test
  @DisplayName("Test get/set programmingLanguage")
  public void testActiveProgrammingLanguage() {
    ProgrammingLanguage lang = mock(ProgrammingLanguage.class);
    Model.setLanguage(lang);
    assertEquals(lang, Model.getLanguage());
    assertThrows(IllegalArgumentException.class, () -> Model.setLanguage(null));
  }


  @Test
  @DisplayName("Test get/set theme")
  public void testTheme() {
    String theme = "Monokai";
    Model.setTheme(theme);
    assertEquals(theme, Model.getTheme());
    assertThrows(IllegalArgumentException.class, () -> Model.setTheme(null));
  }

  @Test
  @DisplayName("Test get/set scene")
  public void testScene() {
    Scene scene = mock(Scene.class);

    Model.setScene(scene);
    assertEquals(scene, Model.getScene());
    assertThrows(IllegalArgumentException.class, () -> Model.setScene(null));
  }

  @Test
  @DisplayName("Test get/set settingsProvider")
  public void testSettingsProvider() {
    SettingsProvider settings = mock(SettingsProvider.class);
    Model.setSettingsProvider(settings);
    assertEquals(settings, Model.getSettingsProvider());
    assertThrows(IllegalArgumentException.class, () -> Model.setSettingsProvider(null));
  }

}
