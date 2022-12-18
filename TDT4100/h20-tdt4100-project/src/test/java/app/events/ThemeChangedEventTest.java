package app.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import app.model.Model;
import app.testing.EventTestTemplate;

public class ThemeChangedEventTest extends EventTestTemplate {

  @Test
  @DisplayName("Check that model gets changed on constructor")
  private void checkModel() {
      new ThemeChangedEvent("Monokai");
      this.mockModel.verify(() -> Model.setTheme("Monokai"));
  }
    
}
