package app.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.google.common.eventbus.EventBus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;

import app.model.Model;
import app.model.languages.Java;
import app.model.languages.Markdown;

@ExtendWith(MockitoExtension.class)
public class SettingsProviderTest {

  @TempDir
  File tmp;

  private EventBus eventBus = new EventBus();

  private SettingsProvider sp = new SettingsProvider(eventBus);

  @BeforeEach
  private void initializeSettingsPath() {
    sp.setSettingsPath(Paths.get(tmp.toPath().toString(), "BNNsettings.dat").toString());
  }

  @Test
  @DisplayName("Test loadSettings with pre-existing settings file")
  public void testLoadSettings() throws IOException {
    File f = new File(tmp, "BNNsettings.dat");
    f.createNewFile();
    
    Files.writeString(
      f.toPath(),
      "- Settings:\n"
      + "Programming Language = Markdown\n"
      + "Theme = Solarized Light",
      StandardOpenOption.WRITE
    );

    sp.loadSettings();
    assertTrue(Model.getLanguage() instanceof Markdown);
    assertEquals("Solarized Light", Model.getTheme());
  }

  @Test
  @DisplayName("Test loadSettings without pre-existing settings file")
  public void testLoadSettingsWithoutFile() throws IOException {

    sp.loadSettings();
    assertTrue(Model.getLanguage() instanceof Java);
    assertEquals("Monokai", Model.getTheme());
  }

  @Test
  @DisplayName("Test loadSettings with broken settings file")
  public void testLoadSettingsWithErrorFile() throws IOException {
    File f = new File(tmp, "BNNsettings.dat");
    f.createNewFile();
    
    Files.writeString(
      f.toPath(),
      "- Settings:\n"
      + "Programming Language = Nonexisting Language\n"
      + "Theme = Solarized Light",
      StandardOpenOption.WRITE
    );

    sp.loadSettings();
    assertTrue(Model.getLanguage() instanceof Java);
    assertEquals("Monokai", Model.getTheme());
  }

  @Test
  @DisplayName("Test save settings")
  public void testSaveSettings() {
    Model.setLanguage(new Markdown());
    Model.setTheme("Solarized Light");
    
    sp.saveSettings();

    try {
      assertEquals(
        "- Settings:\n"
        + "Programming Language = Markdown\n"
        + "Theme = Solarized Light\n",
        Files.readString(Paths.get(tmp.toString(), "BNNsettings.dat"))
      );
    } catch (IOException e) {
      fail("Couldn't read settings file");
    }
  }
    
}
