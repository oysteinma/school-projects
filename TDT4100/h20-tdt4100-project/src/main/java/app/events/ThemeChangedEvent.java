package app.events;

import app.model.Model;

/**
 * Event signalizing that the theme of the applicaton has been changed
 */
public class ThemeChangedEvent extends Event {

  private String theme;

  /**
   * Event signalizing that the theme of the applicaton has been changed
   * 
   * @param theme The name of the theme
   */
  public ThemeChangedEvent(String theme) {
    Model.setTheme(theme);
    this.theme = theme;
  }

  /**
   * @return The name of the theme
   */
  public String getTheme() {
    return theme;
  }

}
