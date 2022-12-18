package app.events;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import app.model.Model;
import app.model.languages.Empty;
import app.model.languages.Java;
import app.testing.EventTestTemplate;

public class LanguageChangedEventTest extends EventTestTemplate {

  @Test
  @DisplayName("Check that model gets changed on constructor")
  private void checkModel() {
      new LanguageChangedEvent("Java");
      this.mockModel.verify(() -> Model.setLanguage(new Java()));
  }


  @Test
  @DisplayName("Change into every possible language")
  private void checkPossibleLanguages() {
      new LanguageChangedEvent("Java");
      this.mockModel.verify(() -> Model.setLanguage(new Java()));

      new LanguageChangedEvent("Markdown");
      this.mockModel.verify(() -> Model.setLanguage(new Java()));

      new LanguageChangedEvent("Empty");
      this.mockModel.verify(() -> Model.setLanguage(new Empty()));

      assertThrows(IllegalArgumentException.class, () -> new LanguageChangedEvent("")); 
  }
    
}
