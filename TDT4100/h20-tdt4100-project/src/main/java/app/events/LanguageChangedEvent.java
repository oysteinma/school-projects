package app.events;

import app.model.languages.*;
import app.model.Model;

/**
 * Event signalizing that the programming language of the editor has changed
 */
public class LanguageChangedEvent extends Event {

  private String language;

  /**
   * Event signalizing that the programming language of the editor has changed
   * @param language The name of the language, capitalized
   */
  public LanguageChangedEvent(String language) {
    this.language = language;

    switch (language.toLowerCase()) {

      case "java":
        Model.setLanguage(new Java());
        break;

      case "markdown":
        Model.setLanguage(new Markdown());
        break;

      case "empty":
        Model.setLanguage(new Empty());
        break;

      default:
        throw new IllegalArgumentException("Could not recognize language: " + language);
    }
  }

  /**
   * @return The name of the language
   */
  public String getLanguage() {
    return language;
  }
  
}
