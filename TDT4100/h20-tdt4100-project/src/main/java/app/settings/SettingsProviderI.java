package app.settings;

public interface SettingsProviderI {

  /**
   * Load settings from disk, and fire events to update the program state
   */
  void loadSettings();

  /**
   * Save the state from {@link app.model.Model Model} to disk.
   */
  void saveSettings();

}
