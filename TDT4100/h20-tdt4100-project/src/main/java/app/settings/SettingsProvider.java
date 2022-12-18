package app.settings;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import app.events.LanguageChangedEvent;
import app.events.ThemeChangedEvent;
import app.model.Model;

/**
 * A provider for permanent storage of the settings of the application.
 */
public class SettingsProvider implements SettingsProviderI {

    private EventBus eventBus;

    private String settingsPath =
      (System.getProperty("os.name").startsWith("Windows"))
      ? System.getProperty("user.home") + "\\AppData\\Roaming\\/BNNsettings.dat"
      : System.getProperty("user.home") + System.getProperty("file.separator") + ".BNNsettings.dat";

    private List<String> legalSettings = 
       Arrays.asList("Java", "Markdown", "Monokai", "Solarized Light");
    

    /** Only for testing purposes */
    protected void setSettingsPath(String settingsPath) {
      this.settingsPath = settingsPath;
    }


    public SettingsProvider(EventBus eB) {
        setEventBus(eB);
        Model.setSettingsProvider(this);
    }

    public void setEventBus(EventBus eB) {
        eventBus = eB;
        eventBus.register(this);
    }

    @Override
    public void loadSettings() {
        List<String> settings = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(settingsPath))) {

            while (sc.hasNextLine()) {
                var nextLine = sc.nextLine().trim();
                if (nextLine.isEmpty() || nextLine.startsWith("-")) {
                    continue;
                } else {
                    settings.add(nextLine.substring(nextLine.indexOf("=") + 2));
                }
            }

            if (legalSettings.containsAll(settings)) {
                eventBus.post(new LanguageChangedEvent(settings.get(0)));
                eventBus.post(new ThemeChangedEvent(settings.get(1)));
            } else {
                throw new IOException();
            }

        } catch (IOException e) {
            System.err.println("[WARNING] Couldn't find settings file. Using defaults");
            eventBus.post(new LanguageChangedEvent("Java"));
            eventBus.post(new ThemeChangedEvent("Monokai"));
        }

    }

    @Override
    public void saveSettings() {
        try (PrintWriter writer = new PrintWriter(new File(settingsPath))) {
            writer.println("- Settings:");
            writer.println("Programming Language = " + Model.getLanguage().getName());
            writer.println("Theme = " + Model.getTheme());
        } catch (IOException e) {
            System.err.println("[ERROR] Couldn't write to settings file.");
            System.err.println(e);
        }

    }

    /**
     * Update the settings whenever the theme gets changed
     * 
     * @param event
     */
    @Subscribe
    private void handle(ThemeChangedEvent event) {
        saveSettings();
    }

    /**
     * Update the settings whenever the language gets changed
     * 
     * @param event
     */
    @Subscribe
    private void handle(LanguageChangedEvent event) {
        saveSettings();
    }

}
