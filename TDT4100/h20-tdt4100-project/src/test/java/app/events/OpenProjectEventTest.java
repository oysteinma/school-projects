package app.events;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import app.model.Model;
import app.testing.EventTestTemplate;

public class OpenProjectEventTest extends EventTestTemplate {

  @Test
  @DisplayName("Check that model gets changed on constructor")
  private void checkModel() {
      new OpenProjectEvent(Optional.empty());
      this.mockModel.verify(() -> Model.setProjectPath(Optional.empty()));
  }
    
}

